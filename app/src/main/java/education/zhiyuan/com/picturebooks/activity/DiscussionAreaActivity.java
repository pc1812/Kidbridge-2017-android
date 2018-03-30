package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import education.zhiyuan.com.picturebooks.bean.RepeatComment;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.TimeTools;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * 讨论区
 */
public class DiscussionAreaActivity extends AppCompatActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private View disVoice, disVoiceTv, disTv;
    private RepeatComment repeatComment;
    private int id; //绘本id;
    private List<RepeatComment.DataBean.CommentBean> commentList; //绘本评论
    private Dialog pBar;
    private String playPath = "";
    private android.media.MediaPlayer commentPlayer;
    private int offset = 0, limit = 10;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_area);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        tvRight.setVisibility(View.GONE);
        tvTitle.setText("Comments to class");
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        commentList = new ArrayList<>();
        initCommentRecycler();
        getData(offset, limit);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    stopPlay();
                    getData(offset, limit);
                }
            }
        });
    }

    //刷新评论
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String map) {
        if (map.equals("comment_refresh")) {
            commentList.clear();
            getData(offset, limit);
        }
    }

    /**
     * 获取数据
     */
    private void getData(int offset, int limit) {
        if (!isLoading) {
            isLoading = true;
            pBar = new Dialog(DiscussionAreaActivity.this, R.style.Dialog);
            pBar.setContentView(R.layout.progress);
            if (!isFinishing()) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    pBar.show();
                }
            }
        }
        String commentUrl = "/course/appreciation/comment/list";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("id", id);
        param.put("offset", offset);
        param.put("limit", limit);
        new MyAsyncTaskN(DiscussionAreaActivity.this, 0, commentUrl, this).execute(param);
    }

    private void initCommentRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommonAdapter<RepeatComment.DataBean.CommentBean>(this, R.layout.item_lesson_comment, commentList) {

            @Override
            protected void convert(ViewHolder holder, final RepeatComment.DataBean.CommentBean commentBean, int position) {
                GlideUtils.GlideCircle(getApplicationContext(), Api.QN + commentBean.getUser().getHead(), (ImageView) holder.getView(R.id.iv_pic), R.mipmap.iv_login_logo);
                if (TextUtils.isEmpty(commentBean.getUser().getNickname())) {
                    holder.setText(R.id.tv_name, "匿名用户");
                } else {
                    holder.setText(R.id.tv_name, commentBean.getUser().getNickname());
                }
                holder.setText(R.id.tv_time, TimeTools.getStrTimeS(commentBean.getCreateTime() + ""));
                LinearLayout ll = holder.getView(R.id.ll_comment);
                ll.removeAllViews();
                if (!TextUtils.isEmpty(TextViewUtils.replaceBlank(commentBean.getContent().getText()))) {
                    //评论-文字
                    holder.setVisible(R.id.tv_comment, true);
                    holder.setText(R.id.tv_comment, TextViewUtils.replaceBlank(commentBean.getContent().getText()));
                } else {
                    holder.setVisible(R.id.tv_comment, false);
                }
                if (!TextUtils.isEmpty(commentBean.getContent().getAudio().getSource())) {
                    //评论-语音
                    ll.addView(addViewVoice(Api.QN + commentBean.getContent().getAudio().getSource(), commentBean.getContent().getAudio().getTime() + "")); //TODO 语音时间
                } else {
                    holder.setVisible(R.id.tv_comment, true);
                }
                if (commentBean.getReplyList().size() > 0) {
                    //回复
                    for (int i = 0; i < commentBean.getReplyList().size(); i++) {
                        RepeatComment.DataBean.CommentBean.ReplyListBean.ContentBeanX content = commentBean.getReplyList().get(i).getContent();
                        if (!TextUtils.isEmpty(TextViewUtils.replaceBlank(content.getText()))) {
                            ll.addView(addViewTvTwo(commentBean.getReplyList().get(i).getUser().getNickname(), TextViewUtils.replaceBlank(content.getText())));
                        }
                        if (!TextUtils.isEmpty(content.getAudio().getSource())) {
                            ll.addView(addViewVoiceTv(Api.QN + content.getAudio().getSource(), content.getAudio().getTime() + "", commentBean.getReplyList().get(i).getUser().getNickname()));
                        }
                    }
                }
                //点击回复
                holder.setOnClickListener(R.id.re, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ComActivity.class); //绘本赏析评论回复
                        intent.putExtra("id", id);
                        intent.putExtra("quote", commentBean.getId()); //被回复的评论编号
                        intent.putExtra("flag", "course_reply"); //课程回复
                        startActivity(intent);
                    }
                });

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) { //如果滑动停止
                    if (!swipeRefreshLayout.isRefreshing()) {  //如果没在刷新数据
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        int totalItemCount = recyclerView.getAdapter().getItemCount();
                        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                        if (lastVisibleItem + 1 == totalItemCount
                                && lastVisibleItem > 0
                                && recyclerView.computeVerticalScrollOffset() > 0) {
                            getData(commentList.size(), limit);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
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
        if (TextUtils.isEmpty(name)) {
            tv_info.setText("匿名用户：" + info);
            TextViewUtils.setTextColor(tv_info, tv_info.getText().toString(), 0, 5, Color.parseColor("#15CF30"));
        } else {
            tv_info.setText(name + "：" + info);
            TextViewUtils.setTextColor(tv_info, tv_info.getText().toString(), 0, name.length() + 1, Color.parseColor("#15CF30"));
        }
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


    @OnClick({R.id.iv_back, R.id.tv_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_comment:
                Intent intent = new Intent(getApplicationContext(), ComActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("flag", "course_comment");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onSuccess(int type, String str) {
        if (swipeRefreshLayout.isRefreshing()) {
            commentList.clear();
        }
        dis();
        repeatComment = new Gson().fromJson(str, RepeatComment.class);
        if (repeatComment.getData().getComment().size() > 0) {
            commentList.addAll(repeatComment.getData().getComment());
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String msg) {
        dis();
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
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
                iv_play.setImageResource(R.drawable.iv_isplay_white);
                playPath = voicePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void dis() {
        isLoading = false;
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
    }

    public void stopPlay() {
        if (commentPlayer != null) {
            if (commentPlayer.isPlaying()) {
                commentPlayer.stop();
                commentPlayer.release();
                commentPlayer = null;
                playPath = "";
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dis();
        stopPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
