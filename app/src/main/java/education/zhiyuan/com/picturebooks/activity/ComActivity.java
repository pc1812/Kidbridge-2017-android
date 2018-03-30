package education.zhiyuan.com.picturebooks.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.adpter.AdapterCommentVoice;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.base.CheckPermissionsListener;
import education.zhiyuan.com.picturebooks.bean.Option;
import education.zhiyuan.com.picturebooks.bean.PutComBean;
import education.zhiyuan.com.picturebooks.bean.QnToken;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.CacheUtils;
import education.zhiyuan.com.picturebooks.utils.QnUtils;
import education.zhiyuan.com.picturebooks.utils.RecoderMp3Utils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.PermissionDialog;
import education.zhiyuan.com.picturebooks.view.commodity.custom.RecordDialog;
import education.zhiyuan.com.picturebooks.view.commodity.custom.StateDialog;

/**
 * 评论  录音
 * 发布评论，回复评论
 */
public class ComActivity extends BaseActivity implements HttpCallBackN, QnUtils.QnCallBack, CheckPermissionsListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.voiceRecyclerView)
    RecyclerView voiceRecyclerView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.chat_record)
    ImageView chatRecord;
    @BindView(R.id.chat_tv_sound_notice)
    TextView chatTvSoundNotice;
    @BindView(R.id.et)
    EditText et;

    private List<Map<String, Object>> recordDataList; //保存录音时长和路径
    private long minRecordTime = 1000 * 1;  //最短时间
    private long maxRecordTime = 1000 * 60 * 2; //最长时间
    private int downY, moveY; //手指按下坐标
    private String flag; //评论、回复
    private String url;
    private int id, quote;
    private String recordPath; //录音文件地址
    private RecoderMp3Utils recoderMp3Utils;
    private int releaseFlag = -1;  //标记，提交的时候，mediaplay释放
    private boolean canCommit = true; //是否可以提交
    private List<String> recordList;
    private RecordDialog recordDialog;
    private StateDialog stateDialog;
    private String[] requestPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
    private boolean allPermission = true;
    private int requestType;
    private AdapterCommentVoice adapterCommentVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.layout_comments);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("id", -1); //绘本id
            flag = intent.getStringExtra("flag");
        }
        if (!TextUtils.isEmpty(flag)) {
            if (flag.equals("repeat_comment")) { //绘本跟读。评论
                url = "/user/book/repeat/comment";
            } else if (flag.equals("repeat_comment_reply")) { //绘本跟读。回复
                quote = intent.getIntExtra("quote", -1);
                url = "/user/book/repeat/comment/reply";
            } else if (flag.equals("appreciation_comment")) {  //绘本赏析，发布评论  appreciation_comment_reply  appreciationcomment
                url = "/book/appreciation/comment";
            } else if (flag.equals("appreciation_comment_reply")) {   // 绘本赏析回复  appreciation_comment_reply
                quote = intent.getIntExtra("quote", -1);
                url = "/book/appreciation/comment/reply";
            } else if (flag.equals("course_reply")) { //课程回复  讨论区
                quote = intent.getIntExtra("quote", -1);
                url = "/course/appreciation/comment/reply";
            } else if (flag.equals("course_comment")) { //课程评论  讨论区
                url = "/course/appreciation/comment";
            } else if (flag.equals("course_comment_comment")) { //课程跟读评论
                url = "/user/course/repeat/comment";
            } else if (flag.equals("course_comment_reply")) { //课程跟读回复
                url = "/user/course/repeat/comment/reply";
                quote = intent.getIntExtra("quote", -1);
            }
        }
        recordList = new ArrayList<>();
        recoderMp3Utils = new RecoderMp3Utils();
        recordDataList = new ArrayList<>();
        requestPermissionsC(ComActivity.this, requestPermissions, this);
        requestType = 100;
        record();
    }

    @Override
    protected void initView() {
        tvRight.setVisibility(View.INVISIBLE);
        tvTitle.setText("评论");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recoderMp3Utils == null) {
            recoderMp3Utils = new RecoderMp3Utils();
        }
    }

    /**
     * 录音
     */
    private void record() {
        voiceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterCommentVoice = new AdapterCommentVoice(getApplicationContext(), recordDataList, releaseFlag);
        voiceRecyclerView.setAdapter(adapterCommentVoice);
        //录音按钮手势监听
        chatRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下
                        if (!allPermission) {
                            requestType = 200;
                            requestPermissionsC(ComActivity.this, requestPermissions, ComActivity.this);
                            return true;
                        }
                        recordDialog = new RecordDialog(ComActivity.this, R.style.ProgressDialog_State);
                        Window dialogWindow = recordDialog.getWindow();
                        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                        lp.x = 0; // 新位置X坐标
                        lp.y = -300; // 新位置Y坐标
                        dialogWindow.setAttributes(lp);
                        recordDialog.show();
                        downY = (int) event.getY();
                        recoderMp3Utils.startRecord();
                        if (recoderMp3Utils != null) {
                            //录音结束后获取到的信息
                            recoderMp3Utils.setOnAudioStatusUpdateListener(new RecoderMp3Utils.OnAudioStatusUpdateListener() {
                                @Override
                                public void onStop(long time, String filePath, String fileName) {
                                    if (time > minRecordTime && time < maxRecordTime) {
                                        recordList.add(filePath);
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("time", time);
                                        map.put("path", filePath);
                                        recordPath = filePath;
                                        recordDataList.clear();
                                        recordDataList.add(map);
                                        voiceRecyclerView.getAdapter().notifyDataSetChanged();
                                        return;
                                    }
                                    if (time >= maxRecordTime) {
                                        ToastUtil.showShort(getApplicationContext(), "录音时间过长，请重新录制");
                                    }
                                    if (time <= minRecordTime) {
                                        ToastUtil.showShort(getApplicationContext(), "录音时间过短");
                                    }
                                    recordDataList.clear();
                                    recoderMp3Utils.deleteRecord(filePath);
                                }

                                @Override
                                public void onError(Boolean isError, String error) {
                                    if (isError) {
                                        recordDataList.clear();
                                        ToastUtil.showShort(getApplicationContext(), "错误" + error);
                                    }
                                }
                            });
                        }
                        recordDialog.setMsg("向上滑动取消录音");
                        break;
                    case MotionEvent.ACTION_UP://抬起
                        if (!allPermission) {
                            return true;
                        }
                        if (recordDialog != null) {
                            if (recordDialog.isShowing()) {
                                recordDialog.dismiss();
                            }
                        }
                        if (moveY > 100) {
                            recoderMp3Utils.cancelRecord();
                        } else {
                            recoderMp3Utils.stopRecord();
                        }
                        moveY = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!allPermission) {
                            return true;
                        }
                        moveY = (int) (downY - event.getY());
                        if (moveY > 100) {
                            recordDialog.setMsg("松开手指可取消录音");
                        }
                        if (moveY < 20) {
                            recordDialog.setMsg("向上滑动取消录音");
                        }
                        break;
                }
                return true;
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_commit:
                if (canCommit) {
                    canCommit = false;
                    doCommit();
                } else {
                    ToastUtil.showShort(getApplicationContext(), "提交中，请稍后~");
                }
                break;
        }
    }

    public void stopPlay() {
        releaseFlag = -1;
        if (adapterCommentVoice != null) {
            adapterCommentVoice.setFlag(releaseFlag);
            adapterCommentVoice.notifyDataSetChanged();
        }
    }

    /**
     * 提交
     */
    private void doCommit() {
        stopPlay();
        //上传之前先判断是否有音频
        if (recordDataList.size() > 0) {
            //有音频
            getQnToken();
        } else {
            if (!TextUtils.isEmpty(TextViewUtils.replaceBlankHa(et.getText().toString()))) {
                putData(et.getText().toString(), "");
            } else {
                ToastUtil.showShort(getApplicationContext(), "请至少输入一点内容~");
                canCommit = true;
            }
        }
    }

    /**
     * 上传到后台
     */
    public void putData(String text, String audio) {
        if (stateDialog == null) {
            stateDialog = new StateDialog(ComActivity.this, R.style.ProgressDialog_State);
            stateDialog.show();
        }
        final Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("id", id);
        if (flag.equals("appreciation_comment_reply") || flag.equals("repeat_comment_reply") || flag.equals("course_reply") || flag.equals("course_comment_reply")) {
            param.put("quote", quote);  //回复参数
        }
        if (!TextUtils.isEmpty(text)) {
            param.put("text", text);  //文字
        }
        if (!TextUtils.isEmpty(audio)) {
            PutComBean.AudioBean audioBean = new PutComBean.AudioBean();
            audioBean.setSource(audio);
            audioBean.setTime(Integer.valueOf(recordDataList.get(0).get("time").toString()));
            param.put("audio", audioBean);
        }
        new MyAsyncTaskN(ComActivity.this, 0, url, this).execute(param);
    }

    /**
     * 获取七牛token
     */
    public void getQnToken() {
        stateDialog = new StateDialog(ComActivity.this, R.style.ProgressDialog_State);
        stateDialog.show();
        String url = "/file/upload/token";
        Map param = new HashMap();
        param.put("token", SharedPreferencesUtil.getLoginInfo(this).getToken());
        param.put("timestamp", System.currentTimeMillis());
        param.put("option", new Option(id));  //绘本或用户绘本跟读编号
        new MyAsyncTaskN(ComActivity.this, 1, url, this).execute(param);
    }

    /**
     * 删除录音
     */
    public void deleteRecord() {
        if (allPermission){
            for (int i = 0; i < recordList.size(); i++) {
                CacheUtils.deleteDir(new File(recordList.get(i)));
            }
        }
    }

    /**
     * 网络请求结果
     */
    @Override
    public void onSuccess(int type, String str) {
        switch (type) {
            case 0:
                stateDialog.dismiss();
                canCommit = true;
                //删除语音
                Map<Integer, String> m = new HashMap<>();
                m.put(0, flag);
                EventBus.getDefault().post("comment_refresh");
                ToastUtil.showShort(getApplicationContext(), "发布成功");
                finish();
                break;
            case 1:
                QnToken qnToken = new Gson().fromJson(str, QnToken.class);
                new QnUtils(this).upload(recordPath, null, qnToken.getData().getToken());
                break;
        }
    }

    @Override
    public void onError(String msg) {
        canCommit = true;
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    /**
     * 上传到七牛的结果
     */
    @Override
    public void uploadSuccess(String key, ResponseInfo info, JSONObject res) {
        if (!TextUtils.isEmpty(TextViewUtils.replaceBlankHa(et.getText().toString()))) {
            try {
                putData(et.getText().toString().trim(), res.getString("key"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                putData("", res.getString("key"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void uploadFail(String key, ResponseInfo info, JSONObject res) {
        stateDialog.dismiss();
        stateDialog = null;
        ToastUtil.showShort(getApplicationContext(), "上传失败，请稍后重试");
        canCommit = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlay();
        //删除语音
        deleteRecord();
        recoderMp3Utils = null;
        canCommit = true;
    }

    @Override
    public void onGranted() {
        allPermission = true;
    }

    @Override
    public void onDenied(List<String> permissions) {
        allPermission = false;
        if (requestType == 200) {
            if (permissions.size() == requestPermissions.length) {
                new PermissionDialog(ComActivity.this, "手机录音、读写权限").show();
                return;
            }
            new PermissionDialog(ComActivity.this, "手机" + getPermissionName(permissions.get(0)) + "权限").show();
        }
    }


}
