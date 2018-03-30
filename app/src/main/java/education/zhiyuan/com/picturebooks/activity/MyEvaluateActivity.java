package education.zhiyuan.com.picturebooks.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.CourseTeacher;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.TimeTools;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * 老师评价
 */
public class MyEvaluateActivity extends AppCompatActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private List<CourseTeacher.DataBean> evaluateList;
    private View disVoice, disVoiceTv, disTv;
    private android.media.MediaPlayer commentPlayer;
    private String playPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_evaluate);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        tvTitle.setText("老师评价");
        tvRight.setVisibility(View.INVISIBLE);
        evaluateList = new ArrayList<>();
        getData();
        initRecyclerView();
    }


    //刷新评论
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String map) {
        if (map.equals("comment_refresh")) {
            evaluateList.clear();
            getData();
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CommonAdapter<CourseTeacher.DataBean>(getApplicationContext(), R.layout.item_lesson_comment, evaluateList) {
            @Override
            protected void convert(ViewHolder holder, final CourseTeacher.DataBean dataBean, int position) {
                holder.setVisible(R.id.view, position == evaluateList.size() - 1);
                GlideUtils.GlideCircle(getApplicationContext(), Api.QN + dataBean.getUser().getHead(), (ImageView) holder.getView(R.id.iv_pic), R.drawable.iv_login_logo);
                if (TextUtils.isEmpty(dataBean.getUser().getTeacher().getRealname())) {
                    holder.setText(R.id.tv_name, "匿名老师");
                } else {
                    holder.setText(R.id.tv_name, dataBean.getUser().getTeacher().getRealname());
                }
                holder.setText(R.id.tv_time, TimeTools.getStrTimeS(dataBean.getCreateTime() + ""));
                LinearLayout ll = holder.getView(R.id.ll_comment);
                ll.removeAllViews();
                if (!TextUtils.isEmpty(dataBean.getContent().getText())) {
                    //评论-文字
                    holder.setVisible(R.id.tv_comment, true);
                    holder.setText(R.id.tv_comment, dataBean.getContent().getText());
                } else {
                    holder.setVisible(R.id.tv_comment, false);
                }
                if (!TextUtils.isEmpty(dataBean.getContent().getAudio().getSource())) {
                    //评论-语音
                    ll.addView(addViewVoice(Api.QN + dataBean.getContent().getAudio().getSource(), dataBean.getContent().getAudio().getTime() + "")); //TODO 语音时间
                } else {
                    holder.setVisible(R.id.tv_comment, true);
                }
                holder.setOnClickListener(R.id.re, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stopPlay();
                        Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
                        intent.putExtra("id", dataBean.getUserCourseRepeat().getId());
                        intent.putExtra("flag", "MyCourseRead");
                        startActivity(intent);
                    }
                });
            }
        });
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private View addViewTv(String value) {
        TextView tv = new TextView(getApplicationContext());
        tv.setText("发音很标准，哈哈哈哈哈哈");
        tv.setTextSize(14);
        tv.setTextColor(Color.GRAY);
        return tv;
    }

    private View addViewTvTwo(String name, String info) {
        disTv = getLayoutInflater().inflate(R.layout.item_dis_tv, null);
        TextView tv_name = disTv.findViewById(R.id.tv_name);
        tv_name.setVisibility(View.GONE);
        TextView tv_info = disTv.findViewById(R.id.tv_info);
        tv_info.setText(name + "：" + info);
        TextViewUtils.setTextColor(tv_info, tv_info.getText().toString(), 0, name.length() + 1, Color.parseColor("#15CF30"));
        return disTv;
    }

    private View addViewVoice(final String voicePath, String voiceTime) {
        disVoice = getLayoutInflater().inflate(R.layout.item_discuss_voice, null);
        RelativeLayout re = disVoice.findViewById(R.id.re);
        final ImageView iv_play = disVoice.findViewById(R.id.iv_play);
        if (voicePath.equals(playPath)) {
            if (commentPlayer != null) {
                if (commentPlayer.isPlaying()) {
                    iv_play.setImageResource(R.drawable.iv_isplay_white);
                } else {
                    iv_play.setImageResource(R.drawable.iv_play_white);
                }
            }
        }
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPlay(voicePath, iv_play);
            }
        });
        TextView tv_time = disVoice.findViewById(R.id.tv_time);
        tv_time.setText(TimeTools.timeParse(Integer.valueOf(voiceTime)));
        return disVoice;
    }

    private View addViewVoiceTv(final String voicePath, String voiceTime, String name) {
        disVoiceTv = getLayoutInflater().inflate(R.layout.item_dis_voicetv, null);
        RelativeLayout re = disVoiceTv.findViewById(R.id.re);
        TextView tv_name = disVoiceTv.findViewById(R.id.tv_name);
        tv_name.setText(name + "：");
        final ImageView iv_play = disVoiceTv.findViewById(R.id.iv_play);
        if (voicePath.equals(playPath)) {
            if (commentPlayer != null) {
                if (commentPlayer.isPlaying()) {
                    iv_play.setImageResource(R.drawable.iv_isplay_white);
                } else {
                    iv_play.setImageResource(R.drawable.iv_play_white);
                }
            }
        }
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPlay(voicePath, iv_play);
            }
        });
        TextView tv_time = disVoiceTv.findViewById(R.id.tv_time);
        tv_time.setText(TimeTools.timeParse(Integer.valueOf(voiceTime)));
        return disVoiceTv;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    public void getData() {
        String url = "/user/course/repeat/teacher/comment";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        new MyAsyncTaskN(MyEvaluateActivity.this, 0, url, this).execute(param);
    }

    @Override
    public void onSuccess(int type, String str) {
        CourseTeacher com = new Gson().fromJson(str, CourseTeacher.class);
        evaluateList.addAll(com.getData());
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showShort(getApplicationContext(), msg);

    }

    /**
     * 评论语音播放
     */
    public void doPlay(String voicePath, ImageView iv_play) {
        if (playPath.equals(voicePath)) { //点击的是同一个
            if (commentPlayer != null) {
                if (commentPlayer.isPlaying()) {
                    commentPlayer.pause();
                    iv_play.setImageResource(R.drawable.iv_play_white);
                } else {
                    commentPlayer.start();
                    iv_play.setImageResource(R.drawable.iv_isplay_white);
                }
            }
        } else {  //播放的是另一个
            if (commentPlayer != null) {
                if (commentPlayer.isPlaying()) {
                    commentPlayer.stop();
                    commentPlayer.release();
                    commentPlayer = null;
                }
            }
            commentPlayer = new android.media.MediaPlayer();
            try {
                try {
                    commentPlayer.setDataSource(voicePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                commentPlayer.prepare();
                commentPlayer.start();
                commentPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(android.media.MediaPlayer mediaPlayer) {
                        commentPlayer.stop();
                        commentPlayer.release();
                        commentPlayer = null;
                        playPath = "";
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
                iv_play.setImageResource(R.drawable.iv_isplay_white);
                playPath = voicePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        stopPlay();
    }

    public void stopPlay() {
        if (commentPlayer != null) {
            if (commentPlayer.isPlaying()) {
                commentPlayer.stop();
                commentPlayer.release();
                commentPlayer = null;
                playPath = "";
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }
}
