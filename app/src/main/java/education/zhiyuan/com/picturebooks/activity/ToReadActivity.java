package education.zhiyuan.com.picturebooks.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;

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
import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.EventAudio;
import education.zhiyuan.com.picturebooks.bean.EventPlay;
import education.zhiyuan.com.picturebooks.bean.EventRecord;
import education.zhiyuan.com.picturebooks.bean.HuiBenRepeatBean;
import education.zhiyuan.com.picturebooks.bean.Option;
import education.zhiyuan.com.picturebooks.bean.PutDataBean;
import education.zhiyuan.com.picturebooks.bean.QnToken;
import education.zhiyuan.com.picturebooks.bean.ReadPutBean;
import education.zhiyuan.com.picturebooks.bean.ReadTokenBean;
import education.zhiyuan.com.picturebooks.bean.RecordBean;
import education.zhiyuan.com.picturebooks.fragment.FragmentToRead;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.CacheUtils;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.QnUtils;
import education.zhiyuan.com.picturebooks.utils.RecoderMp3Utils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TimeTools;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.utils.Util;
import education.zhiyuan.com.picturebooks.utils.WxShareUtils;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CusDialog;
import education.zhiyuan.com.picturebooks.view.commodity.custom.PermissionDialog;
import education.zhiyuan.com.picturebooks.view.commodity.custom.StateDialog;


/**
 * Created by Lance on 2017/6/24.
 * Email : COCOINUT@163.com
 * 跟读界面
 */

public class ToReadActivity extends AppCompatActivity implements FragmentToRead.Submit, RecoderMp3Utils.OnAudioStatusUpdateListener, HttpCallBackN, QnUtils.QnCallBack {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.mViewPage)
    ViewPager mViewPage;
    @BindView(R.id.ll_read)
    RelativeLayout llRead;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_bg_image)
    ImageView ivBgImage;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.tv_readFinish)
    TextView tvReadFinish;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.re)
    RelativeLayout re;
    @BindView(R.id.layout_main)
    LinearLayout layoutMain;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter adapter;
    private boolean isReadFinish = false;  //跟读是否完成
    private boolean isTiming = true; //是否继续倒计时
    private long minRecordTime = 1000 * 1; //录音最短时间
    private long maxRecordTime = 1000 * 60 * 10; //录音最长时间
    private CountDownTimer countDownTimer;  //倒计时控件
    private static CusDialog cusDialog;  //提示框
    private static String FLAG;  //标记状态

    private List<HuiBenRepeatBean.DataBean.BookSegmentListBean> bookSegmentListBeanList; //音频集合
    private HuiBenRepeatBean huiBenRepeatBean;
    private List<PutDataBean.RepeatBean.SegmentBean> segmentBeanList = new ArrayList<>(); //传到服务器时，拼参
    private ReadTokenBean readTokenBean;
    private QnToken qnToken;
    private ReadPutBean readPutBean;
    private String readToken;

    private String readTokenUrl; //跟读token url
    private int typeRead; // 0 ：跟读 1;课程打卡  0：用户绘本跟读上传，1：绘本赏析评论/回复上传，2：绘本跟读详情评论/回复上传，3：用户课程跟读上传
    private int ID; //belong
    private int huiBenId, lessonId; //绘本id(课程中)、课程id
    private int id; //绘本id

    private MediaPlayer mediaPlayer, recordPlay; //播放绘本、 播放录音
    private RecoderMp3Utils recoderMp3Utils;
    private int playIndex = -1, recordPlayIndex = -1, recordIndex = -1; //播放绘本 、播放录音 、录音index
    private List<String> allRecordList; //所有录音文件list
    private Map<Integer, RecordBean> mapRecoder;  //有效录音map
    private boolean keepRecord = true, isRecording = false; //是否保存录音、是否有录音权限、是否正在录音
    private static int PERMISSION_WRITE = 100, PERMISSION_AUDIO = 200; //读写、录音、全部

    private WxShareUtils wxShareUtils;
    private Dialog pBar;
    private StateDialog stateDialog;
    private int index; //上传七牛index
    private boolean loadSuccess = false, isPutSuccess = false; //是否加载完数据、是个上传成功数据

    private String flag = "ha"; //是不是从赏析区-我也要跟读跳转的
    private String shareUrl;
    private String title;

    private String[] requestPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
    private int[] requestCode = {PERMISSION_WRITE, PERMISSION_AUDIO};
    private boolean allPermission = true;
    private String tokenQn;
    private QnUtils qnUtils;
    private LinearLayout.LayoutParams lp;
    private List<String> iconList;

    private boolean isHint = false; //开始进入提示，
    private long overTime;
    private boolean isShare = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_to_read);
        ButterKnife.bind(this);
        if (MyApp.resolutionW != -1) {
            lp = new LinearLayout.LayoutParams(MyApp.resolutionW, MyApp.resolutionW / 9 * 5);
            re.setLayoutParams(lp);
        }
        GlideUtils.GlideCircle(getApplicationContext(), Api.QN + SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getHeadIcon(), ivLeft, R.mipmap.iv_login_logo);
        qnUtils = new QnUtils(this);
        Intent intent = getIntent();
        EventBus.getDefault().register(this);
        if (intent != null) {
            id = intent.getIntExtra("id", -1);
            ID = intent.getIntExtra("ID", -1);
            typeRead = intent.getIntExtra("type", -1);
            huiBenId = intent.getIntExtra("hbId", -1);
            lessonId = intent.getIntExtra("lessonId", -1);
            flag = intent.getStringExtra("flag");
        }
        mapRecoder = new HashMap<>();  //有效录音map
        allRecordList = new ArrayList<>(); //所有录音list
        iconList = new ArrayList<>();
        checkPermission(requestPermissions, requestCode, 0); //只检查权限
        getReadToken(); //获取跟读token
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isShare) {
            Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
            intent.putExtra("id", readPutBean.getData().getRepeat());
            intent.putExtra("uid", id);
            if (typeRead == 1) { //课程跟读，发布
                intent.putExtra("flag", "CourseRelease");
            } else { //绘本跟读发布
                intent.putExtra("flag", "HbRelease");
            }
            intent.putExtra("name", huiBenRepeatBean.getData().getName());
            startActivity(intent);
            finish();
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_play, R.id.tv_readFinish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (loadSuccess) {
                    if (isTiming && !isReadFinish) {
                        if (cusDialog != null) {
                            cusDialog.dismiss();
                        }
                        FLAG = "未完成";
                        initDialog("跟读未完成时退出将不会保存草稿！", "继续跟读", "退出跟读");
                    } else if (isReadFinish) {
                        FLAG = "完成但退出";
                        initDialog("绘本跟读完成，退出将不会保存草稿", "确定", "取消");
                    }
                }
                break;
            case R.id.iv_play:  //播放录音
                handler.removeMessages(100);
                handler.removeMessages(200);
                handler.removeMessages(300);
                Message message = Message.obtain();
                message.what = 200;
                handler.sendMessageDelayed(message, 300);

                break;
            case R.id.tv_readFinish:
                //跟读完成，发布/预览
                isTiming = false;
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                initDialog("绘本跟读完成！", "预览", "发布");
                break;
        }
    }

    /***
     * 检查录音所需权限
     * */
    public void checkPermission(String[] permissions, int[] requestCode, int type) {
        allPermission = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                if (!(ContextCompat.checkSelfPermission(this, permissions[i]) == PackageManager.PERMISSION_GRANTED)) {
                    allPermission = false;
                    if (type != 0) {
                        requestPermissions(new String[]{permissions[i]}, requestCode[i]);
                    }
                }
            }
        }
    }

    /**
     * 权限结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_WRITE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                    allPermission = true;
                }
            } else {
                new PermissionDialog(ToReadActivity.this, "手机读写权限").show();
            }
        }
        if (requestCode == PERMISSION_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    allPermission = true;
                }
            } else {
                new PermissionDialog(ToReadActivity.this, "手机录音权限").show();
            }
        }
    }

    /**
     * 获取跟读token
     */
    public void getReadToken() {
        pBar = new Dialog(this, R.style.Dialog);  //WindowManager$BadTokenException
        pBar.setContentView(R.layout.progress);
        if (!isFinishing()) { //避免调用已销毁的activity
            pBar.show();
        }
        if (typeRead == 1) { //课程
            shareUrl = Api.HOST + "/share/course/repeat/";
            readTokenUrl = "/user/course/book/repeat/token";
            Map param = new HashMap();
            param.put("timestamp", System.currentTimeMillis());
            param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
            param.put("course", ID);
            param.put("book", huiBenId);
            new MyAsyncTaskN(ToReadActivity.this, 0, readTokenUrl, this).execute(param);
        } else { //绘本
            shareUrl = Api.HOST + "/share/book/repeat/";
            readTokenUrl = "/user/book/repeat/token";
            Map param = new HashMap();
            param.put("timestamp", System.currentTimeMillis());
            param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
            param.put("id", ID);
            new MyAsyncTaskN(ToReadActivity.this, 0, readTokenUrl, this).execute(param);
        }
    }

    /**
     * 获取音频数据
     */
    private void InitDate() {
        bookSegmentListBeanList = new ArrayList<>();
        String url = "/book/segment";
        Map param = new HashMap();
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("timestamp", System.currentTimeMillis());
        param.put("repeat", readToken);
        new MyAsyncTaskN(ToReadActivity.this, 1, url, this).execute(param);
    }

    /**
     * 获取数据处理
     */
    public void initView(int type) {
        switch (type) {
            case 0:
                readToken = readTokenBean.getData().getToken();
                InitDate();  //获取数据
                break;
            case 1:
                tvTime.setVisibility(View.VISIBLE);
                tvIndex.setVisibility(View.VISIBLE);
                bookSegmentListBeanList.addAll(huiBenRepeatBean.getData().getBookSegmentList());
                InitView();
                break;
            case 2: //上传到七牛
                index = 0;
                tokenQn = qnToken.getData().getToken();
                qnUtils.upload(mapRecoder.get(index).getFilePath(), null, tokenQn);
                break;
            case 3:  //上传到后台  repeat：发布成功的跟读ID type：跟读类别，0=绘本跟读，1=课程跟读
                //通知刷新
                if (typeRead == 1) {
                    //通知开课界面，打卡记录刷新
                    EventBus.getDefault().post("courseClock");
                } else {
                    EventBus.getDefault().post("huibenClock");
                }
                //水滴变化
                EventBus.getDefault().post("water");
                //删除录音
                isPutSuccess = true;
                if (deleteRecord() == 0) {
                    //删除完录音
                    initDialogShare("发布成功，是否分享？", "分享", "不分享", readPutBean.getData().getRepeat());
                }
                break;
        }
    }

    /**
     * UI界面填充
     */
    private void InitView() {
        if (!isHint) {
            ToastUtil.showLayoutLong(getApplicationContext(), "当前绘本限时打卡" + TimeTools.dateFormat(huiBenRepeatBean.getData().getRepeatActiveTime() * 1000 + "", "mm分钟"));
            isHint = true;
        }
        //最上方图片
        GlideUtils.GlideNormal(getApplicationContext(), Api.QN + bookSegmentListBeanList.get(0).getIcon(), ivBgImage, R.drawable.iv_replace_les);
        title = huiBenRepeatBean.getData().getName();
        tvTitle.setText(huiBenRepeatBean.getData().getName());
        tvRight.setVisibility(View.GONE);
        tvIndex.setText("1/" + bookSegmentListBeanList.size());
        //倒计时
        maxRecordTime = huiBenRepeatBean.getData().getRepeatActiveTime() * 1000;
        countDownTimer = new CountDownTimer(maxRecordTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                overTime = millisUntilFinished;
                tvTime.setText("倒计时\t" + TimeTools.getCountTimeByLong(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                tvTime.setText("倒计时\t00:00:00");  //会停留在00:00:01
                isTiming = false;
                if (isRecording) {
                    recoderMp3Utils.stopRecord();
                    recordIndex = -1;
                    SharedPreferencesUtil.putHuibenRecord(getApplicationContext(), new EventRecord(-1, false));
                    EventBus.getDefault().post(new EventRecord(-1, true));
                }
                FLAG = "超时";
                initDialog("已超时，打卡失败！是否重新打卡？", "重新打卡", "退出打卡");
                countDownTimer.cancel();
            }
        }.start();
        recoderMp3Utils = new RecoderMp3Utils();
        recoderMp3Utils.setOnAudioStatusUpdateListener(this);
        mFragments = new ArrayList<>();
        for (int i = 0; i < bookSegmentListBeanList.size(); i++) {
            mFragments.add(FragmentToRead.newInstance(i, bookSegmentListBeanList.size(), bookSegmentListBeanList.get(i)));
            iconList.add(Api.QN + bookSegmentListBeanList.get(i).getIcon());
        }
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            /**
             * 页面宽度所占ViewPager测量宽度的权重比例，默认为1
             */
            @Override
            public float getPageWidth(int position) {
                if (bookSegmentListBeanList.size() > 1) {
                    return (float) 0.8;
                }
                return super.getPageWidth(position);
            }


        };
        mViewPage.setAdapter(adapter);
        EventBus.getDefault().post("readPos0");
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                EventBus.getDefault().post("readPos" + position);
                //改变上方图片
                GlideUtils.GlideNormal(getApplicationContext(), Api.QN + bookSegmentListBeanList.get(position).getIcon(), ivBgImage, R.drawable.iv_replace_les);
                tvIndex.setText(position + 1 + "/" + bookSegmentListBeanList.size());
                if (mapRecoder.containsKey(position)) {
                    llRead.setVisibility(View.VISIBLE);
                    if (recordPlayIndex == position) {
                        if (recordPlay.isPlaying()) {
                            ivPlay.setImageResource(R.drawable.iv_play_pause);
                        } else {
                            ivPlay.setImageResource(R.drawable.iv_play_white);
                        }
                    } else {
                        ivPlay.setImageResource(R.drawable.iv_play_white);
                    }
                    tvContent.setText(TimeTools.timeParse(Integer.valueOf(mapRecoder.get(position).getTime())));
                } else {
                    llRead.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 点击播放绘本
     * handler避免音频频繁切换延迟
     */
    private ImageView hanIv;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //TODO  播放录音
            if (msg.what == 200) {
                handler.removeMessages(200);
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        playIndex = -1;
                        SharedPreferencesUtil.putHuibenRead(getApplicationContext(), new EventPlay(-1, true));
                        EventBus.getDefault().post(new EventPlay(-1, true));
                    }
                }
                //是否在录音
                if (isRecording) {
                    recoderMp3Utils.stopRecord();
                    recordIndex = -1;
                    recordPlayIndex = -1;
                    SharedPreferencesUtil.putHuibenRecord(getApplicationContext(), new EventRecord(-1, false));
                    EventBus.getDefault().post(new EventRecord(-1, true));
                }
                if (recordPlayIndex == mViewPage.getCurrentItem()) {  //录音播放  ==同
                    if (recordPlay.isPlaying()) {
                        recordPlay.pause(); //暂停
                        ivPlay.setImageResource(R.drawable.iv_play_white);
                    } else {
                        recordPlay.start();
                        ivPlay.setImageResource(R.drawable.iv_play_pause);
                    }
                } else { //换
                    recordPlayIndex = mViewPage.getCurrentItem();
                    if (recordPlay != null) {
                        if (recordPlay.isPlaying()) {
                            recordPlay.stop();
                            recordPlay.release();
                            recordPlay = null;
                        }
                    }
                    try {
                        recordPlay = new MediaPlayer();
                        recordPlay.setDataSource(mapRecoder.get(mViewPage.getCurrentItem()).getFilePath());
                        recordPlay.prepareAsync();
                        recordPlay.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                recordPlay.start();
                                ivPlay.setImageResource(R.drawable.iv_play_pause);
                            }
                        });
                        recordPlay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                recordPlay.stop();
                                recordPlay.release();
                                recordPlay = null;
                                recordPlayIndex = -1;
                                ivPlay.setImageResource(R.drawable.iv_play_white);
                            }
                        });
                        recordPlay.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                            @Override
                            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                                ToastUtil.showShort(getApplicationContext(), "play-erroe");
                                return false;
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //TODO 播放原音
            if (msg.what == 100) {
                handler.removeMessages(100);
                final int pos = msg.arg1;
                if (playIndex == pos) {  //播放的是同一片段
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();  //暂停
                        SharedPreferencesUtil.putHuibenRead(getApplicationContext(), new EventPlay(pos, false));
                        hanIv.setImageResource(R.drawable.iv_to_read_play);
                    } else {  //播放
                        mediaPlayer.start();
                        SharedPreferencesUtil.putHuibenRead(getApplicationContext(), new EventPlay(pos, true));
                        hanIv.setImageResource(R.drawable.iv_reading);
                    }
                } else {
                    try {
                        if (mediaPlayer != null) {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.pause();
                                mediaPlayer.reset();
                            }
                        } else {
                            mediaPlayer = new MediaPlayer();
                        }
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(Api.QN + bookSegmentListBeanList.get(pos).getAudio());
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                playIndex = pos;
                                mediaPlayer.start();
                                EventBus.getDefault().post(new EventPlay(pos, true));
                                SharedPreferencesUtil.putHuibenRead(getApplicationContext(), new EventPlay(pos, true));
                                hanIv.setImageResource(R.drawable.iv_reading);
                            }
                        });
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mediaPlayer.pause();
                                mediaPlayer.reset();
                                playIndex = -1;
                                EventBus.getDefault().post(new EventPlay(-1, true));
                                SharedPreferencesUtil.putHuibenRead(getApplicationContext(), new EventPlay(-1, true));
                                hanIv.setImageResource(R.drawable.iv_to_read_play);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (msg.what == 300) {
                handler.removeMessages(300);
                int pos = msg.arg1;
                ImageView ivRecord = (ImageView) msg.obj;
                isRecording = true;
                recordIndex = pos;
                ToastUtil.showLayoutShort(getApplicationContext(), "跟读录制开始~");
                recoderMp3Utils.startRecord();
                Glide.with(getApplicationContext()).load(R.drawable.record).into(ivRecord);
                EventBus.getDefault().post(new EventRecord(pos, true));
                SharedPreferencesUtil.putHuibenRecord(getApplicationContext(), new EventRecord(pos, true));
            }
        }
    };

    /**
     * 播放原音
     */
    @Override
    public void clickPlay(View view, final int pos) {
        handler.removeMessages(200);
        handler.removeMessages(100);
        handler.removeMessages(300);
        final ImageView iv = (ImageView) view;
        hanIv = iv;
        if (recordPlay != null) {
            if (recordPlay.isPlaying()) {
                recordPlay.pause();
                ivPlay.setImageResource(R.drawable.iv_play_white);
            }
        }
        if (isRecording) {
            recoderMp3Utils.stopRecord();
            recordIndex = -1;
            SharedPreferencesUtil.putHuibenRecord(getApplicationContext(), new EventRecord(-1, true));
            EventBus.getDefault().post(new EventRecord(-1, true));
        }
        Message message = Message.obtain();
        message.what = 100;
        message.arg1 = pos;
        handler.sendMessageDelayed(message, 200);
    }

    /**
     * 点击录音
     */
    private ImageView ivRec;
    private int posRec;

    @Override
    public void clickRecord(View view, int pos) {
        ivRec = (ImageView) view;
        posRec = pos;
        handler.removeMessages(100);
        handler.removeMessages(200);
        handler.removeMessages(300);
        if (!allPermission) {
            checkPermission(requestPermissions, requestCode, 1);
            return;
        }
        final ImageView ivRecord = (ImageView) view;
        if (recordIndex == pos) {
            if (isRecording) {
                ToastUtil.showLayoutShort(getApplicationContext(), "跟读录制结束~");
                recoderMp3Utils.stopRecord();
                ivRecord.setImageResource(R.drawable.iv_recording);
                SharedPreferencesUtil.putHuibenRecord(getApplicationContext(), new EventRecord(-1, false)); //否则保留的还是之前的状态
            }
        } else {
            if (recordPlay != null) {
                if (recordPlay.isPlaying()) {
                    recordPlay.pause();
                    ivPlay.setImageResource(R.drawable.iv_play_white);
                }
            }
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    EventBus.getDefault().post(new EventPlay(-1, true)); //切换时，其他按钮，统一
                    SharedPreferencesUtil.putHuibenRead(getApplicationContext(), new EventPlay(pos, false));
                }
            }
            if (isRecording) {
                //结束录音
                recoderMp3Utils.stopRecord();
            }
            Message message = Message.obtain();
            message.obj = ivRecord;
            message.what = 300;
            message.arg1 = pos;// 必须要延迟一下，否则生成的文件不正常
            handler.sendMessageDelayed(message, 200);
        }
    }

    /**
     * 结束播放-
     */
    public void stopPlay() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
        if (recordPlay != null) {
            if (recordPlay.isPlaying()) {
                recordPlay.stop();
                recordPlay.release();
                recordPlay = null;
            }
        }
        if (isRecording) {
            keepRecord = false;
            recoderMp3Utils.stopRecord();
        }
    }

    /**
     * 录音结束
     */
    @Override
    public void onStop(long time, String filePath, String fileName) {
        if (keepRecord) {
            if (time > minRecordTime && time < maxRecordTime) {
                mapRecoder.put(recordIndex, new RecordBean(time + "", filePath, fileName));
                allRecordList.add(filePath);
                if (recordIndex == mViewPage.getCurrentItem()) {
                    llRead.setVisibility(View.VISIBLE);
                    tvReadFinish.setClickable(false);
                    tvContent.setText(TimeTools.timeParse(time));
                }
                recordIndex = -1;
                recordPlayIndex = -1;
                isRecording = false;
                if (mapRecoder.size() == bookSegmentListBeanList.size() && isTiming) {  //TODO 时间之内跟读完成
                    isReadFinish = true;
                    tvReadFinish.setClickable(true);
                    tvReadFinish.setBackgroundResource(R.drawable.shape_circle_main);
                    FLAG = "完成";
                    //TODO 计时结束
//                    isTiming = false;
//                    if (countDownTimer != null) {
//                        countDownTimer.cancel();
//                    }
                }
                return;
            }
        } else {
            //删除
             CacheUtils.deleteDir(new File(filePath));
        }
        if (time < minRecordTime) {
            ToastUtil.showShort(getApplicationContext(), "录音时间过短，请重新录音");
            isRecording = false;
            recordIndex = -1;
            recoderMp3Utils.deleteRecord(filePath);
        }
    }

    /**
     * 录音错误
     */
    @Override
    public void onError(Boolean isError, String error) {
        isRecording = false;
        recordIndex = -1;
        if (isError) {
            ToastUtil.showShort(getApplicationContext(), "录音时间过短，请重新录音");
        }
    }

    /**
     * 获取七牛Token
     */
    public void putFileQn() {
        String url = "/file/upload/token";
        Map param = new HashMap();
        param.put("version", "1.0.0");
        param.put("token", SharedPreferencesUtil.getLoginInfo(this).getToken());
        param.put("timestamp", System.currentTimeMillis());
        param.put("option", new Option(readToken));
        if (typeRead == 1) {
            param.put("type", 3); //  3：用户课程跟读上传, 0：用户绘本跟读上传，
        } else {
            param.put("type", 0);
        }
        new MyAsyncTaskN(ToReadActivity.this, 2, url, this).execute(param);
    }

    /**
     * 提交绘本跟读信息  向后台发送数据
     */
    public void putData() {
        PutDataBean.RepeatBean repeatBean = new PutDataBean.RepeatBean();
        repeatBean.setSegment(segmentBeanList);
        repeatBean.setToken(readToken);
        String url = "/user/book/repeat";
        Map param = new HashMap();
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("timestamp", System.currentTimeMillis());
        param.put("sign", Util.sign(param));
        param.put("repeat", repeatBean);
        new MyAsyncTaskN(ToReadActivity.this, 3, url, this).execute(param);
    }

    /**
     * 重新打卡，初始化所有值
     */
    private void initState() {
        isReadFinish = false;
        isTiming = true;
        countDownTimer.cancel();
        countDownTimer = null;
        isRecording = false;
        countDownTimer = new CountDownTimer(maxRecordTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                overTime = millisUntilFinished;
                tvTime.setText("倒计时\t" + TimeTools.getCountTimeByLong(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                tvTime.setText("倒计时\t00:00:00");  //会停留在00:00:01
                isTiming = false;
                isTiming = false;
                if (isRecording) {
                    recoderMp3Utils.stopRecord();
                    recordIndex = -1;
                    SharedPreferencesUtil.putHuibenRecord(getApplicationContext(), new EventRecord(-1, false));
                    EventBus.getDefault().post(new EventRecord(-1, true));
                }
                FLAG = "超时";
                initDialog("已超时，打卡失败！是否重新打卡？", "重新打卡", "退出打卡");
                countDownTimer.cancel();
            }
        }.start();
        mapRecoder.clear();
        llRead.setVisibility(View.INVISIBLE);
    }

    /**
     * 分享后finish此界面，跳转至赏析区（不管分享结果）
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("wxshare")) {
            Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
            intent.putExtra("id", readPutBean.getData().getRepeat());
            intent.putExtra("uid", id);
            if (typeRead == 1) { //课程跟读，发布
                intent.putExtra("flag", "CourseRelease");
            } else { //绘本跟读发布
                intent.putExtra("flag", "HbRelease");
            }
            intent.putExtra("name", huiBenRepeatBean.getData().getName());
            startActivity(intent);
            finish();
        }
    }

    /**
     * 状态提示框
     */
    public void initDialog(String content, String positiveStr, String negativeStr) {
        //避免dialog重叠
        if (cusDialog != null) {
            cusDialog.dismiss();
            cusDialog = null;
        }
        cusDialog = new CusDialog(this, R.style.Dialog, content, new CusDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                switch (FLAG) {
                    case "超时":
                        deleteRecord();   //删除之前的录音文件
                        mapRecoder.clear();
                        allRecordList.clear();
                        if (confirm) {  //重新打卡
                            stopPlay();
                            initState();
                        } else { //退出打卡
                            stopPlay();
                            finish();
                        }
                        break;
                    case "未完成":
                        if (!confirm) {  //退出跟读
                            stopPlay();
                            deleteRecord();
                            finish();
                        } else {  //点击继续跟读后，还要判断当前时间
                            if (!isTiming) {
                                FLAG = "超时";
                                initDialog("已超时，打卡失败！是否重新打卡？", "重新打卡", "退出打卡");
                            }
                        }
                        break;
                    case "完成":
                        if (confirm) {   //TODO 预览
                            stopPlay();
                            Intent intent = new Intent(getApplicationContext(), PreviewActivity.class);
                            List<Integer> sid = new ArrayList<>();
                            for (int i = 0; i < bookSegmentListBeanList.size(); i++) {
                                sid.add(bookSegmentListBeanList.get(i).getId());
                            }
                            intent.putExtra("video", new EventAudio(iconList, id, sid, mapRecoder));
                            intent.putExtra("ID", ID);
                            intent.putExtra("lessonId", lessonId);
                            intent.putExtra("huiBenId", huiBenId);
                            intent.putExtra("name", title);
                            intent.putExtra("uid", id);
                            intent.putExtra("time", overTime);
                            //TODO
                            intent.putExtra("type", typeRead);
                            intent.putExtra("flag", flag);
                            startActivity(intent);
                            finish();
                        } else {
                            //发布停止倒计时
                            isTiming = false;
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            stopPlay();
                            //TODO 发布，上传到七牛
                            stateDialog = new StateDialog(ToReadActivity.this, R.style.ProgressDialog_State);
                            stateDialog.show();
                            putFileQn();
                        }
                        break;
                    case "完成但退出":
                        if (confirm) {
                            //删除之前的录音文件
                            stopPlay();
                            deleteRecord();
                            finish();
                        }
                        break;
                }
            }
        });
        cusDialog.setPositiveButton(positiveStr)
                .setNegativeButton(negativeStr)
                .show();
    }

    /**
     * 分享提示提示框
     */
    public void initDialogShare(String content, String positiveStr, String negativeStr, final int pid) {
        CusDialog cusDialog = new CusDialog(this, R.style.Dialog, content, new CusDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    showForwardDialog(pid);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
                    intent.putExtra("id", pid);
                    intent.putExtra("uid", id);
                    intent.putExtra("name", huiBenRepeatBean.getData().getName());
                    if (typeRead == 1) { //课程跟读，发布
                        intent.putExtra("flag", "CourseRelease");
                    } else { //绘本跟读发布
                        intent.putExtra("flag", "HbRelease");
                    }
                    startActivity(intent);
                    finish();
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
    private void showForwardDialog(final int pid) {
        wxShareUtils = new WxShareUtils(ToReadActivity.this);
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_forward, null);
        //取消
        dialogView.findViewById(R.id.pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
                intent.putExtra("id", pid);
                intent.putExtra("uid", id);
                intent.putExtra("name", huiBenRepeatBean.getData().getName());
                if (typeRead == 1) { //课程跟读，发布
                    intent.putExtra("flag", "CourseRelease");
                } else { //绘本跟读发布
                    intent.putExtra("flag", "HbRelease");
                }
                startActivity(intent);
                finish();
                bottomSheetDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.re_wChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShare = true;
                String content = "我在HS英文绘本课堂朗读了" + huiBenRepeatBean.getData().getName() + "绘本，快来听听吧！";
                if (typeRead == 1) { //课程跟读，发布
                    wxShareUtils.shareHtml(false, 0, 1, pid, shareUrl + readPutBean.getData().getRepeat(), "HS英文绘本课堂", content);
                } else { //绘本跟读发布
                    wxShareUtils.shareHtml(false, 0, 0, pid, shareUrl + readPutBean.getData().getRepeat(), "HS英文绘本课堂", content);
                }
                bottomSheetDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.re_w_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShare = true;
                String content = "我在HS英文绘本课堂朗读了" + huiBenRepeatBean.getData().getName() + "绘本，快来听听吧！";
                if (typeRead == 1) {
                    wxShareUtils.shareHtml(true, 1, 1, pid, shareUrl + readPutBean.getData().getRepeat(), content, content);
                } else {
                    wxShareUtils.shareHtml(true, 1, 0, pid, shareUrl + readPutBean.getData().getRepeat(), content, content);
                }
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.show();
    }

    /***
     * 获取接口数据回调
     * */
    @Override
    public void onSuccess(int type, String str) {
        loadSuccess = true;
        switch (type) {
            case 0:
                readTokenBean = new Gson().fromJson(str, ReadTokenBean.class);
                break;
            case 1:
                if (pBar != null) {
                    if (pBar.isShowing()) {
                        pBar.dismiss();
                    }
                }
                huiBenRepeatBean = new Gson().fromJson(str, HuiBenRepeatBean.class);
                break;
            case 2:
                qnToken = new Gson().fromJson(str, QnToken.class);
                break;
            case 3:
                readPutBean = new Gson().fromJson(str, ReadPutBean.class);
                break;
        }
        initView(type);
    }

    @Override
    public void onError(String msg) {
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
        if (stateDialog != null) {
            if (stateDialog.isShowing()) {
                stateDialog.dismiss();
            }
        }
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    /**
     * 上传到七牛结果
     */
    @Override
    public void uploadSuccess(String key, ResponseInfo info, JSONObject res) {
        PutDataBean.RepeatBean.SegmentBean segmentBean = new PutDataBean.RepeatBean.SegmentBean();
        try {
            segmentBean.setId(bookSegmentListBeanList.get(index).getId());
            PutDataBean.RepeatBean.SegmentBean.AudioBean audio = new PutDataBean.RepeatBean.SegmentBean.AudioBean();
            audio.setSource(res.getString("key"));
            audio.setTime(Integer.valueOf(mapRecoder.get(index).getTime()));
            segmentBean.setAudio(audio);
            segmentBeanList.add(segmentBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        index += 1;
        if (index < mapRecoder.size()) {
            qnUtils.upload(mapRecoder.get(index).getFilePath(), null, tokenQn);
        } else {
            //向后台发送提交数据
            putData();
        }
    }

    @Override
    public void uploadFail(String key, ResponseInfo info, JSONObject res) {
        if (stateDialog != null) {
            if (stateDialog.isShowing()) {
                stateDialog.dismiss();
            }
        }
        ToastUtil.showShort(getApplicationContext(), "Upload Fail");
    }

    /**
     * 删除所有录音文件
     */
    public int deleteRecord() {
        if (allPermission) {
            //TODO   //删除之前创建的 /hb/
            File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/hb");
            if (file1.exists()) {
                CacheUtils.deleteDir(file1);
            }
            //删除根目录下所有mp3，amr类型的文件,避免文件的删除不及时
            File fileT = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            File[] fileList = fileT.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].getName().contains("mp3") || fileList[i].getName().contains("amr")) {
                    CacheUtils.deleteDir(fileList[i]);
                }
            }
            //删除 /huib/record/文件夹下，所有录音文件，避免按home键后，又重启app造成的录音文件没有及时删除
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/huib/record");
            if (file.exists()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    CacheUtils.deleteDir(files[i]);
                }
            }
            return 0;
        }
        return -1;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        handler.removeMessages(100);
        handler.removeMessages(200);
        handler.removeMessages(300);
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
        isTiming = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (cusDialog != null) {
            cusDialog = null;
        }
        stopPlay();
        recoderMp3Utils = null;
        SharedPreferencesUtil.putHuibenRecord(getApplicationContext(), new EventRecord(-1, false));
        SharedPreferencesUtil.putHuibenRead(getApplicationContext(), new EventPlay(-1, false));
    }

    /**
     * 返回键的监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (loadSuccess) {
                //有效时间内 跟读未完成
                if (isTiming && !isReadFinish) {
                    FLAG = "未完成";
                    initDialog("跟读未完成时退出将不会保存草稿！", "继续跟读", "退出跟读");
                    return true;
                } else if (isReadFinish) {
                    FLAG = "完成但退出";
                    initDialog("绘本跟读完成,退出将不会保存草稿", "确定", "取消");
                    return true;
                }
            }
            return super.onKeyDown(keyCode, event);
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}