package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UploadManager;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.EventAudio;
import education.zhiyuan.com.picturebooks.bean.Option;
import education.zhiyuan.com.picturebooks.bean.PutDataBean;
import education.zhiyuan.com.picturebooks.bean.QnToken;
import education.zhiyuan.com.picturebooks.bean.ReadPutBean;
import education.zhiyuan.com.picturebooks.bean.ReadTokenBean;
import education.zhiyuan.com.picturebooks.bean.RecordBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.CacheUtils;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.QnUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TimeTools;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.utils.WxShareUtils;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CarouselBanner;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CusDialog;
import education.zhiyuan.com.picturebooks.view.commodity.custom.StateDialog;

/**
 * 跟读完成后预览
 */
public class PreviewActivity extends AppCompatActivity implements HttpCallBackN, QnUtils.QnCallBack {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.audio_recycler)
    RecyclerView audioRecycler;
    @BindView(R.id.lanceBanner)
    CarouselBanner lanceBanner;
    @BindView(R.id.btn_release)
    Button btnRelease;

    private boolean canClick = true;
    private int id;
    private int ID;
    private String title;
    private List<Integer> sid;
    private int lessonId, huiBenId;
    private int uid;
    private List<RecordBean> audioList;
    private MediaPlayer player;
    private int index = 0; //当前播放
    private StateDialog stateDialog;
    private UploadManager uploadManager;
    private String read_token;  //跟读的token

    private ReadTokenBean bean;
    private QnToken qnToken;
    private ReadPutBean response;
    private EventAudio eventAudio;
    private List<PutDataBean.RepeatBean.SegmentBean> segmentationBeanList = new ArrayList<>(); //传到服务器时，拼参
    private Map<Integer, RecordBean> mapRecoder;

    private WxShareUtils wxShareUtils;
    private String shareUrl;
    private QnUtils qnUtils;

    private String playPath = "";
    private View itemView;
    private long overTime;
    //    private CountDownTimer countDownTimer;
    private CusDialog cusDialog;

    private String flag;
    private int typeRead;
    private boolean isShare = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        qnUtils = new QnUtils(this);
        tvRight.setVisibility(View.INVISIBLE);
        audioList = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            uid = intent.getIntExtra("uid", -1);
            ID = intent.getIntExtra("ID", -1);
            lessonId = intent.getIntExtra("lessonId", -1);
            huiBenId = intent.getIntExtra("huiBenId", -1);
            title = intent.getStringExtra("name");
            eventAudio = (EventAudio) intent.getSerializableExtra("video");
            overTime = intent.getLongExtra("time", 0);

            typeRead = intent.getIntExtra("type", -1);
            flag = intent.getStringExtra("flag");

            id = eventAudio.getId();  //绘本id
            sid = eventAudio.getsId(); //跟读段落 id集合
            lanceBanner.setImagesUrl(eventAudio.getIconList());
            mapRecoder = eventAudio.getMapRecoder(); //录音map
            for (int i = 0; i < mapRecoder.size(); i++) {
                audioList.add(mapRecoder.get(i));
            }
            if (lessonId == -1) {
                shareUrl = Api.HOST + "/share/book/repeat/";
            } else {
                shareUrl = Api.HOST + "/share/course/repeat/";
            }
        }
        tvTitle.setText(title);
        initRecycler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isShare) {
            Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
            intent.putExtra("id", response.getData().getRepeat());
            if (lessonId != -1) { //课程跟读，发布
                intent.putExtra("flag", "CourseRelease");
            } else { //绘本跟读发布
                intent.putExtra("flag", "HbRelease");
            }
            intent.putExtra("name", title);
            intent.putExtra("uid", uid);
            startActivity(intent);
            finish();
        }
    }

    public void initDialog(String content, String positiveStr, String negativeStr) {
        //避免dialog重叠
        if (cusDialog != null) {
            cusDialog.dismiss();
            cusDialog = null;
        }
        cusDialog = new CusDialog(this, R.style.Dialog, content, new CusDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {  //重新打卡
                    Intent intent = new Intent(PreviewActivity.this, ToReadActivity.class);
                    intent.putExtra("id", uid);
                    intent.putExtra("ID", ID);
                    intent.putExtra("type", typeRead);
                    intent.putExtra("hbId", huiBenId);
                    intent.putExtra("lessonId", lessonId);
                    intent.putExtra("flag", flag);
                    startActivity(intent);
                    finish();
                } else { //退出打卡
                    finish();
                }
                deleteRecord();
            }
        });
        cusDialog.setPositiveButton(positiveStr)
                .setNegativeButton(negativeStr)
                .show();
    }

    //分享后
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("wxshare")) {
            Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
            intent.putExtra("id", response.getData().getRepeat());
            if (lessonId != -1) { //课程跟读，发布
                intent.putExtra("flag", "CourseRelease");
            } else { //绘本跟读发布
                intent.putExtra("flag", "HbRelease");
            }
            intent.putExtra("name", title);
            intent.putExtra("uid", uid);
            startActivity(intent);
            finish();
        }
    }

    private void initRecycler() {
        audioRecycler.setLayoutManager(new LinearLayoutManager(this));
        audioRecycler.setAdapter(new CommonAdapter<RecordBean>(getApplicationContext(), R.layout.item_pre_ll, audioList) {
            @Override
            protected void convert(final ViewHolder holder, RecordBean recordBean, final int position) {
                LinearLayout ll = holder.getView(R.id.ll);
                ll.removeAllViews();
                ll.addView(addViewL(audioList.get(position).getFilePath(), mapRecoder.get(position).getTime()));
            }
        });
    }

    public View addViewL(final String voicePath, String voiceTime) {
        itemView = getLayoutInflater().inflate(R.layout.item_preview_audio, null);
        final ImageView iv_play = itemView.findViewById(R.id.iv_read);
        ImageView ivHead = itemView.findViewById(R.id.iv_left);

        GlideUtils.GlideCircle(getApplicationContext(), Api.QN + SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getHeadIcon(), ivHead, R.mipmap.iv_login_logo);
        if (voicePath.equals(playPath)) {
            if (player != null) {
                if (player.isPlaying()) {
                    iv_play.setImageResource(R.drawable.iv_reading);
                } else {
                    iv_play.setImageResource(R.drawable.iv_to_read_play);
                }
            }
        }
        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPlay(voicePath, iv_play);
            }
        });
        TextView tv_time = itemView.findViewById(R.id.tv_content);
        tv_time.setText(TimeTools.timeParse(Integer.valueOf(voiceTime)));
        return itemView;
    }

    /**
     * TODO 语音播放
     */
    public void doPlay(String voicePath, ImageView iv_play) {
        if (playPath.equals(voicePath)) { //点击的是同一个
            if (player != null) {
                if (player.isPlaying()) {
                    player.pause();
                    iv_play.setImageResource(R.drawable.iv_to_read_play);
                } else {
                    player.start();
                    iv_play.setImageResource(R.drawable.iv_reading);
                }
            }
        } else {  //播放的是另一个
            if (player != null) {
                if (player.isPlaying()) {
                    player.stop();
                    player.release();
                    player = null;
                }
            }
            player = new MediaPlayer();
            try {
                try {
                    player.setDataSource(voicePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.prepare();
                player.start();
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        player.stop();
                        player.release();
                        player = null;
                        playPath = "";
                        audioRecycler.getAdapter().notifyDataSetChanged();
                    }
                });
                iv_play.setImageResource(R.drawable.iv_isplay_white);
                playPath = voicePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        audioRecycler.getAdapter().notifyDataSetChanged();
    }

    /**
     * 发布后所跟读的   音频草稿将会成为一整段发布到所跟读的绘本详情也的跟读榜中，
     * 成为赏析区，所跟读的音频也会同步到个人中心-我的跟读中
     */
    @OnClick({R.id.iv_back, R.id.btn_release})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                initDialog("跟读未发布,退出将不会保存草稿", "确定", "取消", 1);
                break;
            case R.id.btn_release:
                //发布
//                countDownTimer.cancel();
                stopPlay();
                stateDialog = new StateDialog(PreviewActivity.this, R.style.ProgressDialog_State);
                stateDialog.show();
                getReadToken();
                break;
        }
    }

    /**
     * TODO 获取跟读token
     */
    public void getReadToken() {
        String readTokenUrl = "";
        if (lessonId != -1) {
            readTokenUrl = "/user/course/book/repeat/token";
            Map param = new HashMap();
            param.put("timestamp", System.currentTimeMillis());
            param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
            param.put("course", ID);
            param.put("book", huiBenId);
            new MyAsyncTaskN(PreviewActivity.this, 0, readTokenUrl, this).execute(param);
        } else {
            readTokenUrl = "/user/book/repeat/token";
            Map param = new HashMap();
            param.put("timestamp", System.currentTimeMillis());
            param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
            param.put("id", ID);
            new MyAsyncTaskN(PreviewActivity.this, 0, readTokenUrl, this).execute(param);
        }
    }

    /**
     * 获取七牛token
     */
    public void putFileQn() {
        String url = "/file/upload/token";
        Map param = new HashMap();
        param.put("token", SharedPreferencesUtil.getLoginInfo(this).getToken());
        param.put("timestamp", System.currentTimeMillis());
        param.put("option", new Option(read_token));
        if (lessonId == -1) {
            param.put("type", 0);
        } else {
            param.put("type", 3);
        }
        new MyAsyncTaskN(PreviewActivity.this, 1, url, this).execute(param);
    }

    /**
     * 提交绘本跟读信息
     */
    public void putData() {
        PutDataBean.RepeatBean repeatBean = new PutDataBean.RepeatBean();
        repeatBean.setSegment(segmentationBeanList);
        repeatBean.setToken(read_token);
        String url = "/user/book/repeat";
        Map param = new HashMap();
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("timestamp", System.currentTimeMillis());
        param.put("repeat", repeatBean);
        new MyAsyncTaskN(PreviewActivity.this, 2, url, this).execute(param);
    }

    /**
     * 删除所有录音文件
     */
    public void deleteRecord() {
        if (mapRecoder != null) {
            Object[] pathList = mapRecoder.values().toArray();
            for (int i = 0; i < mapRecoder.values().size(); i++) {
                RecordBean recordBean = (RecordBean) pathList[i];
                CacheUtils.deleteDir(new File(recordBean.getFilePath()));
            }
        }
    }

    /***
     * 提示框
     * */
    public void initDialog(String content, String positiveStr, String negativeStr, final int type) {
        CusDialog cusDialog = new CusDialog(this, R.style.Dialog, content, new CusDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (type == 1) {  //退出
                    if (confirm) {
                        deleteRecord();
                        finish();
                    }
                } else if (type == 0) {  //分享
                    if (confirm) {
                        showForwardDialog();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
                        intent.putExtra("id", response.getData().getRepeat());
                        if (lessonId != -1) { //课程跟读，发布
                            intent.putExtra("flag", "CourseRelease");
                        } else { //绘本跟读发布
                            intent.putExtra("flag", "HbRelease");
                        }
                        intent.putExtra("name", title);
                        intent.putExtra("uid", uid);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        cusDialog.setPositiveButton(positiveStr)
                .setNegativeButton(negativeStr)
                .show();
        if (stateDialog != null) {
            if (stateDialog.isShowing()) {
                stateDialog.dismiss();
            }
        }
    }

    /**
     * 分享对话框
     */
    private void showForwardDialog() {
        wxShareUtils = new WxShareUtils(PreviewActivity.this);
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_forward, null);
        //取消
        dialogView.findViewById(R.id.pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
                intent.putExtra("id", response.getData().getRepeat());
                if (lessonId != -1) { //课程跟读，发布
                    intent.putExtra("flag", "CourseRelease");
                } else { //绘本跟读发布
                    intent.putExtra("flag", "HbRelease");
                }
                intent.putExtra("name", title);
                intent.putExtra("uid", uid);
                startActivity(intent);
                bottomSheetDialog.dismiss();
                finish();
            }
        });
        dialogView.findViewById(R.id.re_wChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShare = true;
                String content = "我在HS英文绘本课堂朗读了" + (TextUtils.isEmpty(title) ? "未知" : title) + "绘本，快来听听吧！";
                if (lessonId != -1) { //课程跟读，发布
                    wxShareUtils.shareHtml(false, 0, 1,response.getData().getRepeat(), shareUrl + response.getData().getRepeat(), "HS英文绘本课堂", content);
                } else { //绘本跟读发布
                    wxShareUtils.shareHtml(false, 0, 0, response.getData().getRepeat(),shareUrl + response.getData().getRepeat(), "HS英文绘本课堂", content);
                }
                bottomSheetDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.re_w_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShare = true;
                String content = "我在HS英文绘本课堂朗读了" + (TextUtils.isEmpty(title) ? "未知" : title) + "绘本，快来听听吧！";
                if (lessonId != -1) { //课程跟读，发布
                    wxShareUtils.shareHtml(true, 1, 1,response.getData().getRepeat(), shareUrl + response.getData().getRepeat(), content, content);
                } else { //绘本跟读发布
                    wxShareUtils.shareHtml(true, 1, 0, response.getData().getRepeat(),shareUrl + response.getData().getRepeat(), content, content);
                }
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.show();
    }

    @Override
    public void onSuccess(int type, String str) {
        switch (type) {
            case 0:
                bean = new Gson().fromJson(str, ReadTokenBean.class);
                read_token = bean.getData().getToken();
                putFileQn();
                break;
            case 1:
                qnToken = new Gson().fromJson(str, QnToken.class);
                qnUtils.upload(mapRecoder.get(index).getFilePath(), null, qnToken.getData().getToken());
                break;
            case 2: //发布成功
                //通知刷新
                if (lessonId == -1) {
                    EventBus.getDefault().post("huibenClock");
                } else {
                    //通知开课界面，打卡记录刷新
                    EventBus.getDefault().post("courseClock");
                }
                EventBus.getDefault().post("water");
                deleteRecord();
                response = new Gson().fromJson(str, ReadPutBean.class);
                initDialog("发布成功，是否分享？", "分享", "不分享", 0);
                break;
        }
    }

    @Override
    public void onError(String msg) {
        dis();
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    public void dis() {
        if (stateDialog != null) {
            if (stateDialog.isShowing()) {
                stateDialog.dismiss();
            }
        }
    }

    public void stopPlay() {
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
                player.release();
                player = null;
                playPath = "";
                audioRecycler.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//            countDownTimer = null;
//        }
        dis();
        stopPlay();
        if (eventAudio != null) {
            if (eventAudio.getIconList().size() > 0) {
                lanceBanner.stopAutoPlay();
            }
        }
    }

    /**
     * 返回键的监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            initDialog("跟读未发布,退出将不会保存草稿", "确定", "取消", 1);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 上传七牛结果
     */
    @Override
    public void uploadSuccess(String key, ResponseInfo info, JSONObject res) {
        PutDataBean.RepeatBean.SegmentBean segmentBean = new PutDataBean.RepeatBean.SegmentBean();
        try {
            segmentBean.setId(sid.get(index));
            PutDataBean.RepeatBean.SegmentBean.AudioBean audio = new PutDataBean.RepeatBean.SegmentBean.AudioBean();
            audio.setSource(res.getString("key"));
            audio.setTime(Integer.valueOf(mapRecoder.get(index).getTime()));
            segmentBean.setAudio(audio);
            segmentationBeanList.add(segmentBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        index += 1;
        if (index < mapRecoder.size()) {
            qnUtils.upload(mapRecoder.get(index).getFilePath(), null, qnToken.getData().getToken());
        } else {
            //向后台发送提交数据
            putData();
        }
    }

    @Override
    public void uploadFail(String key, ResponseInfo info, JSONObject res) {
        ToastUtil.showShort(getApplicationContext(), "发布失败，请稍后重试！" + info);
        if (stateDialog != null) {
            if (stateDialog.isShowing()) {
                stateDialog.dismiss();
            }
        }
    }
}
