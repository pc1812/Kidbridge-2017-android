package education.zhiyuan.com.picturebooks.activity;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.utils.MediaPlayUtil;
import education.zhiyuan.com.picturebooks.utils.StringUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import me.xiaopan.android.widget.ToastUtils;

/**
 * Created by Lance on 2017/6/17.
 * Email : COCOINUT@163.com
 * Introduce : 评论区
 * 发布评论，回复评论
 */

public class CommentsActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.chat_record)
    ImageView mIvRecord;
    @BindView(R.id.chat_tv_sound_notice)
    TextView mTvNotice;

    // 语音相关
    private ScaleAnimation mScaleBigAnimation;
    private ScaleAnimation mScaleLittleAnimation;
    private String mSoundData = "";//语音数据的本地路径
    private String dataPath;
    private boolean isStop;  // 录音是否结束的标志 超过两分钟停止
    private boolean isCanceled = false; // 是否取消录音
    private float downY;
    private MediaRecorder mRecorder;
    private MediaPlayUtil mMediaPlayUtil;
    private long mStartTime;
    private long mEndTime;
    private int mTime;
    private String mVoiceData;


    //保存录音时长和路径
    private List<Map<String, Object>> pathList = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x12:
                    mHandler.removeMessages(0x12);
                    //  mIvVoice.setImageResource(R.drawable.iv_play_white);
                    break;
                case 0x13:  //录音成功，刷新，显示录音列表
//                    voiceRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
//                    voiceRecyclerView.setAdapter(new AdapterCommentVoice(getApplicationContext(), pathList));
//                    voiceRecyclerView.getAdapter().notifyDataSetChanged();
                    // TODO: 2017/6/17 进行数据绑定
                    //   Map<String, Object> map = new HashMap<>();
//                    map.put("time", mTime);
//                    map.put("path", mSoundData);
//                    pathList.add(map);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comments);
        ButterKnife.bind(this);
        tvRight.setVisibility(View.INVISIBLE);
        tvTitle.setText("评论");
        initSoundData();
        initView();
    }

    public void initView() {
        mIvRecord.setOnTouchListener(new VoiceTouch());
        // TODO 播放录音
/*
        mRlVoiceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaPlayUtil.isPlaying()) {
                    ToastUtils.toastS(getApplicationContext(), "if");
                    mMediaPlayUtil.stop();
                } else {
                    ToastUtils.toastS(getApplicationContext(), "else");
                    //  mIvVoice.setImageResource(R.drawable.iv_play_white);
                    mIvVoice.setImageResource(R.drawable.iv_isplay_white);
                    mMediaPlayUtil.play(StringUtil.decoderBase64File(mVoiceData));
                    // Logger.d(mVoiceData);
                    mHandler.sendEmptyMessageDelayed(0x12, mTime * 1000);
                }
            }
        });*/
    }

    /**
     * 录音存放路径
     */
    public void initSoundData() {
        dataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AsRecrod/Sounds/";
        File folder = new File(dataPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        mMediaPlayUtil = MediaPlayUtil.getInstance();
    }

    /**
     * 录音的触摸监听
     */
    class VoiceTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downY = motionEvent.getY();
                    mTvNotice.setText("向上滑动取消发送");
                    mSoundData = dataPath + getRandomFileName() + ".amr";
                    // TODO 防止开权限后崩溃
                    if (mRecorder != null) {
                        mRecorder.reset();
                    } else {
                        mRecorder = new MediaRecorder();
                    }
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                    mRecorder.setOutputFile(mSoundData);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    try {
                        mRecorder.prepare();
                    } catch (IOException e) {
                        Log.i("recoder", "prepare() failed-Exception-" + e.toString());
                    }
                    try {
                        mRecorder.start();
                        mStartTime = System.currentTimeMillis();
                        // TODO 开启定时器
                        mHandler.postDelayed(runnable, 1000);
                    } catch (Exception e) {
                        Log.i("recoder", "prepare() failed-Exception-" + e.toString());
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    //松手
                    if (!isStop) {
                        mEndTime = System.currentTimeMillis();
                        mTime = (int) ((mEndTime - mStartTime) / 1000);  //录音时长
                        stopRecord();
                        //  ToastUtils.toastS(getApplicationContext(), "录音成功的");
                        try {
                            mVoiceData = StringUtil.encodeBase64File(mSoundData);
                            ToastUtils.toastS(getApplicationContext(), mSoundData + "路径");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // TODO: 2017/6/17 标识中途是否取消了录音
                        if (isCanceled) {
                            deleteSoundFileUnSend();
                            mTvNotice.setText("录音取消");
                            ToastUtils.toastS(getApplicationContext(), "已取消录音");
                        } else {
                            ToastUtils.toastS(getApplicationContext(), "录音成功的");
                            // TODO: 2017/6/17 这里应该是录音已经完成了
                            Map<String, Object> map = new HashMap<>();
                            map.put("time", mTime);
                            map.put("path", mSoundData);
                            pathList.add(map);
                            mHandler.sendEmptyMessage(0x13);
                        }
                    } else {
                        mTvNotice.setText("重新录音");
                    }
                    break;
                case MotionEvent.ACTION_CANCEL: // 首次开权限时会走这里，录音取消
                    Toast.makeText(getApplicationContext(), "ACTION_CANCEL", Toast.LENGTH_SHORT).show();
                    Log.i("record_test", "权限影响录音录音");
                    mHandler.removeCallbacks(runnable);
                    // TODO 这里一定注意，先release，还要置为null，否则录音会发生错误，还有可能崩溃
                    if (mRecorder != null) {
                        mRecorder.release();
                        mRecorder = null;
                        System.gc();
                    }
                    mTvNotice.setText("按住说话");
                    isCanceled = true;
                    mScaleBigAnimation = null;
                    mScaleLittleAnimation = null;
                    break;

                case MotionEvent.ACTION_MOVE: // 滑动手指
                    float moveY = motionEvent.getY();
                    if (downY - moveY > 100) {
                        isCanceled = true;
                        mTvNotice.setText("松开手指可取消录音");
                    }
                    if (downY - moveY < 20) {
                        isCanceled = false;
                        mTvNotice.setText("向上滑动取消发送");
                    }
                    break;

            }
            return true;
        }

    }

    /**
     * 结束录音
     */
    public void stopRecord() {
        mScaleBigAnimation = null;
        mScaleLittleAnimation = null;
        //设置最低时间
        if (mTime < 1) {
            deleteSoundFileUnSend();
            isCanceled = true;
            Toast.makeText(getApplicationContext(), "录音时间太短，长按开始录音", Toast.LENGTH_SHORT).show();
        } else {
            mTvNotice.setText("录音成功");
            // 不加  "" 空串 会出  Resources$NotFoundException 错误
        }
        //mRecorder.setOnErrorListener(null);
        try {
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
        } catch (Exception e) {
            isCanceled = true;
            Toast.makeText(getApplicationContext(), "录音发生错误,请重新录音", Toast.LENGTH_LONG).show();
        }
        mHandler.removeCallbacks(runnable);
        if (mRecorder != null) {
            mRecorder = null;
            System.gc();
        }
    }

    // 定时器
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                long endTime = System.currentTimeMillis();
                int time = (int) ((endTime - mStartTime) / 1000);
                //mRlSoundLengthLayout.setVisibility(View.VISIBLE);
                // 限制录音时间不长于1分钟
                if (time > 4) {
                    isStop = true;
                    mTime = time;
                    stopRecord();
                    ToastUtil.showShort(getApplicationContext(), "时间过长");
//                    Toast.makeText(getApplicationContext(), "时间过长", Toast.LENGTH_SHORT).show();
                } else {
                    mHandler.postDelayed(this, 1000);
                    isStop = false;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    /**
     * 生成一个随机的文件名
     *
     * @return
     */
    public String getRandomFileName() {
        String rel = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        rel = formatter.format(curDate);
        rel = rel + new Random().nextInt(1000);
        return rel;
    }

    /**
     * 录音完毕后，若不发送，则删除文件
     */
    public void deleteSoundFileUnSend() {
        mTime = 0;
        if (!"".equals(mSoundData)) {
            try {
                File file = new File(mSoundData);
                file.delete();
                mSoundData = "";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
