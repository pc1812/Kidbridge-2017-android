package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.adpter.DetailBanner;
import education.zhiyuan.com.picturebooks.bean.AdmireBean;
import education.zhiyuan.com.picturebooks.bean.Appreciation;
import education.zhiyuan.com.picturebooks.bean.CourseCom;
import education.zhiyuan.com.picturebooks.bean.CourseComment;
import education.zhiyuan.com.picturebooks.bean.CourseRepeatDetail;
import education.zhiyuan.com.picturebooks.bean.ReadBean;
import education.zhiyuan.com.picturebooks.bean.RepeatComment;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.CacheUtils;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.MediaPlayer;
import education.zhiyuan.com.picturebooks.utils.MoneyUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.TimeTools;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.utils.WxShareUtils;
import education.zhiyuan.com.picturebooks.view.commodity.MScrollview;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CusDialog;
import education.zhiyuan.com.picturebooks.view.commodity.custom.RewardDialog;

/**
 * Created by Lance on 2017/6/21.
 * Email : COCOINUT@163.com
 * Introduce : 跟读详情 绘本赏析
 * 绘本赏析、绘本跟读，绘本评论、绘本跟读评论
 * //fromBridgeDetial 绘本详情-跟读榜点击-跟读者赏析区
 * //formRelease  录音发布后-我的赏析区  没有评论按钮
 * //fromMyBridgeRead我的跟读-绘本跟读
 */

public class ToReadDetailActivity extends AppCompatActivity implements HttpCallBackN, SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.comment_recycler)
    RecyclerView commentRecycler;
    @BindView(R.id.iv_admire)
    ImageView ivAdmire;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_toRead)
    TextView tvToRead;
    @BindView(R.id.scrollView)
    MScrollview myScrollView;
    @BindView(R.id.iv_start)
    ImageView ivStart;
    @BindView(R.id.time_length)
    TextView timeLength;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.tv_matchComment)
    TextView tvMatchComment;
    @BindView(R.id.tv_playTime)
    TextView tvPlayTime;
    @BindView(R.id.iv_dz)
    ImageView ivDz;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.ll_no_comment)
    LinearLayout llNoComment;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_encourage)
    LinearLayout llEncourage;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.rootLayout)
    LinearLayout rootLayout;
    @BindView(R.id.tv_load_finish)
    TextView tvLoadFinish;
    @BindView(R.id.iv_play_left)
    ImageView ivPlayLeft;
    @BindView(R.id.iv_play_right)
    ImageView ivPlayRight;

    private ReadBean readBean;
    private Appreciation appreciation; //绘本赏析
    private CourseRepeatDetail courseRepeatDetail; //我的跟读-课程详情
    private AdmireBean admireBean;
    private List<String> iconList;
    private List<CourseCom.CoursecomBean> cComList; //课程评论列表
    private List<RepeatComment.DataBean.CommentBean> commentList; //其他评论
    private List<ReadBean.DataBean.RepeatBean.SegmentBean> segmentBeanList = new ArrayList<>(); //音频段
    private List<Appreciation.DataBean.BookBean.BookSegmentListBean> bookSegmentListBeen = new ArrayList<>(); //音频段
    private List<CourseRepeatDetail.DataBean.RepeatBean.SegmentBean> bookSegmentListBean = new ArrayList<>(); //音频段

    private String flag = "";
    private int id = -1;//绘本id
    private String fl;
    private int ID; //belong
    private String hbName;
    private int lessonId;

    private View disVoice, disVoiceTv, disTv;
    private List<String> audioList; //音频段
    private Map<Integer, String> loadAudioMap; //音频集合   audioMap,
    private String filePath; //下载的音频文件夹，地址
    private Map param, commentParam;

    private double fee;
    private String playPath = ""; //当前播放的com的path
    private android.media.MediaPlayer commentPlayer; //播放评论音频
    private MediaPlayer player; //播放整音频
    private WxShareUtils wxShareUtils;

    private boolean permissionRead = false;

    private int encourageId; // 鼓励id
    private int offset = 0, limit = 10;

    private String url, commentUrl;
    private String rewardUrl; //打赏
    private String admireUrl; //点赞
    private String shareUrl;  //分享

    private Map rewardMap; //打赏结果
    private Dialog pBar;
    private String detailUrl;
    private int uid;

    private DetailBanner adapter;
    private int startIndex = 0;
    private String userName = "";

    private boolean isOffication = false; //是否为官方
    private String totalAudio = ""; //总段音频
    private Boolean isWebLoadFinish = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 101:
                    init();
                    break;
                case 201:
                    isWebLoadFinish = true;
                    dis();
                    doLoadFinish();
                    break;
                case 100: //播放结束
                    if (isOffication) { //如果是官方的，就直接结束掉
                        seekBar.setProgress(0);
                        tvPlayTime.setText("00:00");
                        initPlayer(totalAudio);
                        return;
                    }
                    ivPlayRight.setImageResource(audioList.size() == 1 ? R.drawable.iv_right_gray : R.drawable.iv_play_right);
                    if (startIndex == audioList.size() - 1) { //播放完最后一个，换到第一个
                        seekBar.setProgress(0);
                        tvPlayTime.setText("00:00");
                        startIndex = 0;
                        ivPlayLeft.setImageResource(R.drawable.iv_left_gray);
                        initPlayer(audioList.get(startIndex));
                    } else { //继续播放
                        ivPlayLeft.setImageResource(R.drawable.iv_play_left);
                        startIndex += 1;
                        ivPlayRight.setImageResource(startIndex == audioList.size() - 1 ? R.drawable.iv_right_gray : R.drawable.iv_play_right);
                        if (player != null) {
                            player.playUrl(Api.QN + audioList.get(startIndex));
                            timeLength.setText(TimeTools.timeParseMs(player.mediaPlayer.getDuration()));
                            seekBar.setProgress(0);
                            player.start();
                            ivStart.setImageResource(R.drawable.iv_isplay_white);
                        } else {
                            initPlayer(audioList.get(startIndex));
                        }
                    }
                    break;
                case 110: //播放上一个
                    doPlay();
                    if (startIndex == 1) {
                        ivPlayLeft.setImageResource(R.drawable.iv_left_gray);
                    }
                    if (startIndex > 1) {
                        ivPlayLeft.setImageResource(R.drawable.iv_play_left);
                        ivPlayRight.setImageResource(R.drawable.iv_play_right);
                    }
                    if (startIndex != 0) {
                        startIndex -= 1;
                        if (player != null) {
                            if (player.mediaPlayer.isPlaying()) {
                                player.pause();
                            }
                            player.playUrl(Api.QN + audioList.get(startIndex));
                            timeLength.setText(TimeTools.timeParseMs(player.mediaPlayer.getDuration()));
                            player.start();
                            ivStart.setImageResource(R.drawable.iv_isplay_white);
                        } else {
                            initPlayer(audioList.get(startIndex));
                        }
                    }
                    break;
                case 220:  //播放下一个
                    doPlay();
                    if (audioList.size() > 1) {
                        ivPlayLeft.setImageResource(R.drawable.iv_play_left);
                        ivPlayRight.setImageResource(startIndex == audioList.size() - 1 ? R.drawable.iv_right_gray : R.drawable.iv_play_right);
                    }
                    if (startIndex != audioList.size() - 1) {
                        startIndex += 1;
                        if (player != null) {
                            if (player.mediaPlayer.isPlaying()) {
                                player.pause();
                            }
                            player.playUrl(Api.QN + audioList.get(startIndex));
                            timeLength.setText(TimeTools.timeParseMs(player.mediaPlayer.getDuration()));
                            player.start();
                            ivStart.setImageResource(R.drawable.iv_isplay_white);
                        } else {
                            initPlayer(audioList.get(startIndex));
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_toreaddetail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Intent intent = getIntent();
        if (intent != null) {
            flag = intent.getStringExtra("flag");
            id = intent.getIntExtra("id", -1);
            ID = intent.getIntExtra("ID", -1);
            lessonId = intent.getIntExtra("lessonId", -1);
            hbName = intent.getStringExtra("name");
            uid = intent.getIntExtra("uid", -1);
        }
        tvTitle.setText(TextUtils.isEmpty(hbName) ? "" : hbName);
        wxShareUtils = new WxShareUtils(this);
        pBar = new Dialog(ToReadDetailActivity.this, R.style.Dialog);
        pBar.setContentView(R.layout.progress);
        handler.sendEmptyMessage(101);
        if (flag.equals("startCourse") || flag.equals("HbAppreciation")) {  //官方
            isOffication = true;
            ivPlayLeft.setVisibility(View.GONE);
            ivPlayRight.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        webview.onResume();
    }

    public void initWebView() {
        if (!isFinishing()) {
            if (pBar != null) {
                if (!pBar.isShowing()) {
                    pBar.show();
                }
            }
        }
        handler.sendEmptyMessageDelayed(201, 3000);
        WebSettings webSettings = webview.getSettings();
        // 设置与Js交互的权限   // 只需设置支持JS就自动打开IndexedDB存储机制
        webSettings.setJavaScriptEnabled(true);
        //设置WebViewClient类
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webview.setWebViewClient(new WebViewClient() {   //onPageFinished有时候无法监听到，，未知，所以再通过onProgressChanged监听
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                isWebLoadFinish = true;
                dis();
                doLoadFinish();
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress > 98) {
                    isWebLoadFinish = true;
                    dis();
                    doLoadFinish();
                }
            }
        });

        webview.loadUrl(detailUrl);
    }

    /**
     * webview加载完成后，再确定是否展示鼓励按钮和评论区域
     */
    public void doLoadFinish() {
        //TODO 官方
        if (appreciation != null) {
            if (flag.equals("startCourse")) { //官方课程
                llComment.setVisibility(View.GONE);
            } else { //官方绘本
                llComment.setVisibility(View.VISIBLE);
            }
            encourageId = appreciation.getData().getBook().getCopyright().getUser().getId();
            if (encourageId == -1) {
                llEncourage.setVisibility(View.GONE);
            } else {
                llEncourage.setVisibility(View.VISIBLE);
            }
        }
        //TODO 用户课程
        if (courseRepeatDetail != null) {
            llEncourage.setVisibility(View.VISIBLE);
            llComment.setVisibility(View.VISIBLE);
        }
        //TODO 用户绘本
        if (readBean != null) {
            llEncourage.setVisibility(View.VISIBLE);
            llComment.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 刷新评论
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String map) {
        //刷新评论,重新获取数据
        if (map.equals("comment_refresh")) {
            tvLoadFinish.setVisibility(View.GONE);
            commentList.clear();
            cComList.clear();
            getCommentData(offset, limit);
        }
    }

    /**
     * 初始化
     */
    private void init() {
        loadAudioMap = new HashMap<>();
        audioList = new ArrayList<>();
        param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("id", id);

        iconList = new ArrayList<>();
        cComList = new ArrayList<>();
        commentList = new ArrayList<>();

        adapter = new DetailBanner(getApplicationContext(), iconList);
        adapter.setAdapterOnclickListener(new DetailBanner.AdapterOnClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                if (position == startIndex) {
                    if (player.mediaPlayer.isPlaying()) {
                        player.pause();
                        ivStart.setImageResource(R.drawable.iv_play_white);
                    } else {
                        ivStart.setImageResource(R.drawable.iv_isplay_white);
                        player.start();
                    }
                } else {
                    if (position != audioList.size()) {
                        startIndex = position;
                        player.pause();
                        player.playUrl(Api.QN + audioList.get(startIndex));
                        player.start();
                        timeLength.setText(TimeTools.timeParseMs(player.mediaPlayer.getDuration()));
                        ivStart.setImageResource(R.drawable.iv_isplay_white);
                    }
                }
            }
        });
        detailUrl = Api.HOST + "/book/richtext/";
        if (flag != null) {
            detailUrl = Api.HOST + "/book/richtext/" + uid;
            if (flag.equals("startCourse")) {  //正在开课的绘本赏析  1    TODO 官方课程
                initOfficialCourse(false);
                initWebView();
            } else if (flag.equals("HbRelease")) {  //发布跟读录音后 绘本跟读   TODO 用户绘本
                initUserHb(true);
                initWebView();
            } else if (flag.equals("CourseRelease")) {    //发布跟读录音 课程跟读  TODO 用户课程
                initUserCourse(true);
                initWebView();
            } else if (flag.equals("BridgeDetail")) {  //绘本详情->跟读榜跳转  3  TODO 用户绘本
                initUserHb(true);
                initWebView();
            } else if (flag.equals("HbAppreciation")) {  //绘本详情->赏析区    TODO 官方绘本
                uid = id;
                initOfficialHb();
                detailUrl = Api.HOST + "/book/richtext/" + uid;
                initWebView();
            } else if (flag.equals("MyHbRead")) { //我的跟读-绘本跟读-item点击  5   TODO 用户绘本
                initUserHb(true);
                initWebView();
            } else if (flag.equals("MyCourseRead")) { // 我的跟读-课程跟读  8 TODO  用户课程
                initUserCourse(true);
            }
            if (!TextUtils.isEmpty(fl)) {
                if (fl.equals("course_comment_reply")) {
                    initCourseComRecycler(fl);
                } else {
                    initCommentRecycler(fl);
                }
            }
        }
        myScrollView.setScrollListener(new MScrollview.ScrollListener() {
            @Override
            public void onScrollToAddMore() {
                if (fl != null) {
                    if (fl.equals("course_comment_reply")) {
                        if (cComList.size() > 0) {
                            getCommentData(cComList.size(), limit);
                        }
                    } else {
                        if (commentList.size() > 0) {
                            getCommentData(commentList.size(), limit);
                        }
                    }
                }
            }

        });
    }

    /**
     * 官方课程
     */
    public void initOfficialCourse(boolean showCom) {
        ivRight.setVisibility(View.GONE);
        ivDz.setVisibility(View.GONE);
        llInfo.setVisibility(View.GONE);
        //TODO
//        if (!showCom) {
//            llComment.setVisibility(View.GONE);
//        } else {
//            llComment.setVisibility(View.VISIBLE);
//        }
        llBottom.setVisibility(View.GONE);
        url = "/book/appreciation";
        rewardUrl = "/book/reward";
        getData(1, param, false);
    }

    /**
     * 官方绘本
     */
    public void initOfficialHb() {
        ivRight.setVisibility(View.GONE);
        ivDz.setVisibility(View.GONE);
        llInfo.setVisibility(View.GONE);
        fl = "appreciation_comment_reply";
        url = "/book/appreciation";
        commentUrl = "/book/appreciation/comment/list";
        rewardUrl = "/book/reward";
        getData(1, param, true);
    }

    /**
     * 用户课程
     */
    public void initUserCourse(boolean showCom) {
        llBottom.setVisibility(View.GONE);
        tvMatchComment.setText("Comments to student");
        tvMatchComment.setVisibility(View.VISIBLE);  //最下方只显示评论
        fl = "course_comment_reply";
        url = "/user/course/repeat/detail";
        commentUrl = "/user/course/repeat/comment/list";
        admireUrl = "/user/course/repeat/like";
        rewardUrl = "/user/course/repeat/reward";
        shareUrl = Api.HOST + "/share/course/repeat/" + id;
        if (!showCom) {
            llNoComment.setVisibility(View.VISIBLE);
        }
        getData(2, param, showCom);
    }

    /**
     * 用户绘本
     */
    public void initUserHb(boolean showCom) {
        tvMatchComment.setVisibility(View.VISIBLE);
        llBottom.setVisibility(View.GONE);
        tvTitle.setText(hbName);
        fl = "repeat_comment_reply";
        url = "/user/book/repeat/detail";
        commentUrl = "/user/book/repeat/comment/list";
        admireUrl = "/user/book/repeat/like";
        rewardUrl = "/user/book/repeat/reward";
        shareUrl = Api.HOST + "/share/book/repeat/" + id;
        if (!showCom) {
            llNoComment.setVisibility(View.VISIBLE);
        }
        getData(3, param, showCom);
    }

    @OnClick({R.id.iv_back, R.id.iv_right, R.id.iv_start, R.id.iv_play_left, R.id.iv_play_right, R.id.btn_encourage, R.id.tv_comment, R.id.tv_toRead, R.id.iv_dz, R.id.tv_matchComment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_start:
                if (audioList.size() > 0) {
                    if (player != null) {
                        if (!player.mediaPlayer.isPlaying()) {
                            //播放
                            player.start();
                            ivStart.setImageResource(R.drawable.iv_isplay_white);
                        } else {
                            player.pause();
                            ivStart.setImageResource(R.drawable.iv_play_white);
                        }
                    } else {
                        initPlayer(audioList.get(0));
                    }
                }
                break;
            case R.id.iv_play_left: //播放上一个
                if (audioList.size() > 0) {
                    handler.removeMessages(110);
                    handler.removeMessages(220);
                    handler.sendEmptyMessageDelayed(110, 500);
                }
                break;
            case R.id.iv_play_right: //播放下一个
                if (audioList.size() > 0) {
                    handler.removeMessages(110);
                    handler.removeMessages(220);
                    handler.sendEmptyMessageDelayed(220, 500);
                }
                break;
            case R.id.iv_back:
                if (player != null) {
                    player.stop();
                }
                finish();
                break;
            case R.id.iv_right:
                //转发
                showForwardDialog();
                break;
            case R.id.btn_encourage:
                //鼓励
                payDialog();
                break;
            case R.id.iv_dz:
                toAdmire(); //点赞操作
                break;
            case R.id.tv_comment:  //评论
                Intent intent_a = new Intent(getApplicationContext(), ComActivity.class);
                intent_a.putExtra("id", id);
                intent_a.putExtra("flag", "appreciation_comment"); //绘本赏析评论
                startActivity(intent_a);
                break;
            case R.id.tv_matchComment: //评论
                Intent intent = new Intent(getApplicationContext(), ComActivity.class);
                if (fl != null) {
                    if (fl.equals("repeat_comment_reply")) {
                        intent.putExtra("id", id);
                        intent.putExtra("flag", "repeat_comment"); //绘本跟读评论
                    } else if (fl.equals("course_comment_reply")) {
                        intent.putExtra("id", id);
                        intent.putExtra("flag", "course_comment_comment"); //课程跟读评论
                    }
                    startActivity(intent);
                }
                break;
            case R.id.tv_toRead:
                Intent intentR = new Intent(getApplicationContext(), ToReadActivity.class);
                intentR.putExtra("id", id);
                intentR.putExtra("ID", ID);
                intentR.putExtra("flag", "toRead");
                startActivity(intentR);
                break;
        }
    }

    /**
     * 获取网络数据
     */
    private void getData(int type, Map param, boolean getComment) {
        if (!isFinishing()) {
            if (pBar != null) {
                if (!pBar.isShowing()) {
                    pBar.show();
                }
            }
        }
        new MyAsyncTaskN(ToReadDetailActivity.this, type, url, this).execute(param);
        if (getComment) {
            getCommentData(offset, limit);
        }
    }

    /**
     * 评论数据
     **/
    public void getCommentData(int offset, int limit) {
        commentParam = new HashMap();
        commentParam.put("timestamp", System.currentTimeMillis());
        commentParam.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        commentParam.put("id", id);
        commentParam.put("offset", offset);
        commentParam.put("limit", limit);
        new MyAsyncTaskN(ToReadDetailActivity.this, 0, commentUrl, this).execute(commentParam);
    }

    /**
     * 充值dialog
     */
    public void initDialog(String content, String positiveStr, String negativeStr) {
        //弹出提示框
        new CusDialog(this, R.style.Dialog, content, new CusDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    startActivity(new Intent(getApplicationContext(), MyBalanceActivity.class));
                }
            }
        })
                .setPositiveButton(positiveStr)
                .setNegativeButton(negativeStr)
                .show();
    }

    /**
     * 初始化音频，，拿到时间
     */
    public void initPlayer(String url) {
        if (player == null) {
            player = new MediaPlayer(seekBar, tvPlayTime);
        }
        player.playUrl(Api.QN + url);
        timeLength.setText(TimeTools.timeParseMs(player.mediaPlayer.getDuration()));
        seekBar.setOnSeekBarChangeListener(this);
        player.setPlayListener(new MediaPlayer.PlayListener() {
            @Override
            public void play() {

            }

            @Override
            public void stop() {

            }

            @Override
            public void complete() {
                tvPlayTime.setText(player.getRuration());
                player.pause();
                ivStart.setImageResource(R.drawable.iv_play_white);
                seekBar.setProgress(100);
                handler.sendEmptyMessageDelayed(100, 300);
            }
        });
    }

    /**
     * 其他评论recycler
     */
    public void initCommentRecycler(final String fl) {
        commentRecycler.setNestedScrollingEnabled(false);
        commentRecycler.setLayoutManager(new LinearLayoutManager(this));
        commentRecycler.setAdapter(new CommonAdapter<RepeatComment.DataBean.CommentBean>(this, R.layout.item_lesson_comment, commentList) {
            @Override
            protected void convert(ViewHolder holder, final RepeatComment.DataBean.CommentBean commentBean, int position) {
                GlideUtils.GlideCircle(getApplicationContext(), Api.QN + commentBean.getUser().getHead(), (ImageView) holder.getView(R.id.iv_pic), R.drawable.iv_login_logo);
                if (TextUtils.isEmpty(commentBean.getUser().getNickname())) {
                    holder.setText(R.id.tv_name, "匿名用户");
                } else {
                    holder.setText(R.id.tv_name, commentBean.getUser().getNickname());
                }
                holder.setText(R.id.tv_time, TimeTools.getStrTimeS(commentBean.getCreateTime() + ""));
                LinearLayout ll = holder.getView(R.id.ll_comment);
                ll.removeAllViews();
                if (!TextUtils.isEmpty(commentBean.getContent().getText())) {
                    //评论-文字
                    holder.setVisible(R.id.tv_comment, true);
                    holder.setText(R.id.tv_comment, TextViewUtils.replaceBlank(commentBean.getContent().getText()));
                } else {
                    holder.setVisible(R.id.tv_comment, false);
                }
                if (!TextUtils.isEmpty(commentBean.getContent().getAudio().getSource())) {
                    //评论-语音
                    ll.addView(addViewVoice(Api.QN + commentBean.getContent().getAudio().getSource(), commentBean.getContent().getAudio().getTime() + "")); //TODO 语音时间
                }
//                else {
//                    Log.e("opieqwoew", "convert: ");
//                    holder.setVisible(R.id.tv_comment, true);
//                }
                if (commentBean.getReplyList().size() > 0) {
                    //回复
                    for (int i = 0; i < commentBean.getReplyList().size(); i++) {
                        RepeatComment.DataBean.CommentBean.ReplyListBean.ContentBeanX content = commentBean.getReplyList().get(i).getContent();
                        if (!TextUtils.isEmpty(content.getText())) {
                            ll.addView(addViewTvTwo(commentBean.getReplyList().get(i).getUser().getNickname(), content.getText(), false));
                        }
                        if (!TextUtils.isEmpty(content.getAudio().getSource())) {
                            ll.addView(addViewVoiceTv(Api.QN + content.getAudio().getSource(), content.getAudio().getTime() + "", commentBean.getReplyList().get(i).getUser().getNickname(), false));
                        }
                    }
                }

                tvComment.setVisibility(View.VISIBLE);
                tvToRead.setVisibility(View.VISIBLE);
                holder.setOnClickListener(R.id.re, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ComActivity.class); //绘本赏析评论回复
                        intent.putExtra("id", id);
                        intent.putExtra("quote", commentBean.getId()); //被回复的评论编号
                        intent.putExtra("flag", fl); //绘本赏析评论回复
                        startActivity(intent);
                    }
                });
            }
        });
    }

    /**
     * 课程跟读评论
     */
    public void initCourseComRecycler(final String fl) {
        commentRecycler.setNestedScrollingEnabled(false);
        commentRecycler.setLayoutManager(new LinearLayoutManager(this));
        commentRecycler.setAdapter(new CommonAdapter<CourseCom.CoursecomBean>(this, R.layout.item_lesson_comment, cComList) {
            @Override
            protected void convert(ViewHolder holder, final CourseCom.CoursecomBean coursecomBean, int position) {
                GlideUtils.GlideCircle(getApplicationContext(), Api.QN + coursecomBean.getUser().getHead(), (ImageView) holder.getView(R.id.iv_pic), R.drawable.iv_login_logo);
                boolean isTeacher = (coursecomBean.getUser().getTeacher().getId() != -1); //TODO   老师
                if (isTeacher) {
                    holder.setTextColor(R.id.tv_name, Color.RED);
                    if (TextUtils.isEmpty(coursecomBean.getUser().getTeacher().getRealname())) {
                        holder.setText(R.id.tv_name, "匿名老师"); //显示真实name
                    } else {
                        holder.setText(R.id.tv_name, coursecomBean.getUser().getTeacher().getRealname()); //显示真实name
                    }
                } else {
                    holder.setTextColor(R.id.tv_name, Color.BLACK);
                    if (TextUtils.isEmpty(coursecomBean.getUser().getNickname())) {
                        holder.setText(R.id.tv_name, "匿名用户");
                    } else {
                        holder.setText(R.id.tv_name, coursecomBean.getUser().getNickname());
                    }
                }
                holder.setText(R.id.tv_time, TimeTools.getStrTimeS(coursecomBean.getCreateTime() + ""));
                LinearLayout ll = holder.getView(R.id.ll_comment);
                ll.removeAllViews();
                if (!TextUtils.isEmpty(TextViewUtils.replaceBlank(coursecomBean.getContent().getText()))) {
                    //评论-文字
                    holder.setVisible(R.id.tv_comment, true);
                    holder.setText(R.id.tv_comment, TextViewUtils.replaceBlank(coursecomBean.getContent().getText()));
                } else {
                    holder.setVisible(R.id.tv_comment, false);
                }
                if (!TextUtils.isEmpty(coursecomBean.getContent().getAudio().getSource())) {
                    //评论-语音
                    ll.addView(addViewVoice(Api.QN + coursecomBean.getContent().getAudio().getSource(), coursecomBean.getContent().getAudio().getTime() + "")); //TODO 语音时间
                } else {
                    holder.setVisible(R.id.tv_comment, true);
                }
                if (coursecomBean.getReplyList().size() > 0) {
                    //回复
                    for (int i = 0; i < coursecomBean.getReplyList().size(); i++) {
                        boolean isTea = (coursecomBean.getReplyList().get(i).getUser().getTeacher().getId() != -1);
                        CourseCom.CoursecomBean.ReplyListBean.UserBeanX userBean = coursecomBean.getReplyList().get(i).getUser();
                        //如果是老师的评论 ,高亮显示
                        String name = "";
                        if (isTea) {
                            name = userBean.getTeacher().getRealname();
                        } else {
                            name = userBean.getNickname();
                        }
                        CourseCom.CoursecomBean.ReplyListBean.ContentBeanX con = coursecomBean.getReplyList().get(i).getContent();
                        if (con != null) {
                            if (!TextUtils.isEmpty(TextViewUtils.replaceBlank(con.getText()))) {
                                ll.addView(addViewTvTwo(name, TextViewUtils.replaceBlank(con.getText()), isTea));
                            }
                            if (!TextUtils.isEmpty(con.getAudio().getSource())) {
                                ll.addView(addViewVoiceTv(Api.QN + con.getAudio().getSource(), con.getAudio().getTime() + "", name, isTea));
                            }
                        }
                    }
                }
                tvComment.setVisibility(View.VISIBLE);
                tvToRead.setVisibility(View.VISIBLE);
                holder.setOnClickListener(R.id.re, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ComActivity.class); //绘本赏析评论回复
                        intent.putExtra("id", id);
                        intent.putExtra("quote", coursecomBean.getId()); //被回复的评论编号
                        intent.putExtra("flag", fl); //绘本赏析评论回复
                        startActivity(intent);
                    }
                });
            }
        });
    }

    /**
     * 获取数据后处理
     */
    public void initView(int type) {
        if (isWebLoadFinish) { //如果webview最先加载完成
            doLoadFinish();
        }
        switch (type) {
            case 0:
                break;
            case 1:
//                encourageId = appreciation.getData().getBook().getCopyright().getUser().getId();
//                if (encourageId == -1) {
//                    llEncourage.setVisibility(View.GONE);
//                }else{
//                    llEncourage.setVisibility(View.GONE);
//                }
                Appreciation.DataBean.BookBean bookBean = appreciation.getData().getBook();
                if (bookBean.getBookSegmentList() != null) {
                    if (bookBean.getBookSegmentList().size() > 0) {
                        //TODO 排序
                        Collections.sort(bookBean.getBookSegmentList(), new Comparator<Appreciation.DataBean.BookBean.BookSegmentListBean>() {
                            @Override
                            public int compare(Appreciation.DataBean.BookBean.BookSegmentListBean bookSegmentListBean, Appreciation.DataBean.BookBean.BookSegmentListBean t1) {
                                if (bookSegmentListBean.getId() > t1.getId()) {
                                    return 1;
                                } else if (bookSegmentListBean.getId() == t1.getId()) {
                                    return 0;
                                } else {
                                    return -1;
                                }
                            }
                        });
                        bookSegmentListBeen.addAll(bookBean.getBookSegmentList()); //音频段
                        for (int i = 0; i < bookBean.getBookSegmentList().size(); i++) {
                            iconList.add(Api.QN + bookBean.getBookSegmentList().get(i).getIcon());
                            audioList.add(bookSegmentListBeen.get(i).getAudio());
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                tvTitle.setText(appreciation.getData().getBook().getName());
                if (audioList.size() == 1) {
                    ivPlayRight.setImageResource(R.drawable.iv_right_gray);
                }
                totalAudio = bookBean.getAudio();
                initPlayer(totalAudio);
                break;
            case 2:
                List<CourseRepeatDetail.DataBean.RepeatBean.BookBean.BookSegmentListBean> bList = courseRepeatDetail.getData().getRepeat().getBook().getBookSegmentList();
                CourseRepeatDetail.DataBean.RepeatBean.UserCourseBean userCourseBean = courseRepeatDetail.getData().getRepeat().getUserCourse();
                bookSegmentListBean.addAll(courseRepeatDetail.getData().getRepeat().getSegment()); //音频段
                //TODO 排序
                Collections.sort(bookSegmentListBean, new Comparator<CourseRepeatDetail.DataBean.RepeatBean.SegmentBean>() {
                    @Override
                    public int compare(CourseRepeatDetail.DataBean.RepeatBean.SegmentBean segmentBean, CourseRepeatDetail.DataBean.RepeatBean.SegmentBean t1) {
                        if (segmentBean.getId() > t1.getId()) {
                            return 1;
                        } else if (segmentBean.getId() == t1.getId()) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                });
                Collections.sort(bList, new Comparator<CourseRepeatDetail.DataBean.RepeatBean.BookBean.BookSegmentListBean>() {

                    @Override
                    public int compare(CourseRepeatDetail.DataBean.RepeatBean.BookBean.BookSegmentListBean bookSegmentListBean, CourseRepeatDetail.DataBean.RepeatBean.BookBean.BookSegmentListBean t1) {
                        if (bookSegmentListBean.getId() > t1.getId()) {
                            return 1;
                        } else if (bookSegmentListBean.getId() == t1.getId()) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                });

                for (int i = 0; i < bList.size(); i++) {
                    iconList.add(Api.QN + bList.get(i).getIcon());
                }
                for (int i = 0; i < bookSegmentListBean.size(); i++) {
                    audioList.add(bookSegmentListBean.get(i).getAudio().getSource());
                }
                userName = TextUtils.isEmpty(userCourseBean.getUser().getNickname()) ? "我" : userCourseBean.getUser().getNickname();
                tvName.setText(userName);
                tvSign.setText(TextUtils.isEmpty(userCourseBean.getUser().getSignature()) ? "暂无签名" : userCourseBean.getUser().getSignature());
                ivDz.setImageResource(courseRepeatDetail.getData().getUser().getLike() == 0 ? R.drawable.admire_white : R.drawable.admire_pink);
                tvCount.setText(courseRepeatDetail.getData().getRepeat().getLike() + ""); //当前点赞数量
                tvTime.setText(TimeTools.getStrTime(courseRepeatDetail.getData().getRepeat().getCreateTime() + ""));
                adapter.notifyDataSetChanged();
                if (audioList.size() == 1) {
                    ivPlayRight.setImageResource(R.drawable.iv_right_gray);
                }
                initPlayer(audioList.get(0));
                detailUrl = Api.HOST + "/book/richtext/" + courseRepeatDetail.getData().getRepeat().getBook().getId();
                tvTitle.setText(courseRepeatDetail.getData().getRepeat().getBook().getName());
                initWebView();
                break;
            case 3:
                List<ReadBean.DataBean.RepeatBean.BookBeanX.BookSegmentListBean> bookBeanList = readBean.getData().getRepeat().getBook().getBookSegmentList();
                ReadBean.DataBean.RepeatBean.UserBookBean userBook = readBean.getData().getRepeat().getUserBook();
                segmentBeanList.addAll(readBean.getData().getRepeat().getSegment()); //音频段
                //TODO 排序
                Collections.sort(segmentBeanList, new Comparator<ReadBean.DataBean.RepeatBean.SegmentBean>() {
                    @Override
                    public int compare(ReadBean.DataBean.RepeatBean.SegmentBean segmentBean, ReadBean.DataBean.RepeatBean.SegmentBean t1) {
                        if (segmentBean.getId() > t1.getId()) {
                            return 1;
                        } else if (segmentBean.getId() == t1.getId()) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                });

                Collections.sort(bookBeanList, new Comparator<ReadBean.DataBean.RepeatBean.BookBeanX.BookSegmentListBean>() {

                    @Override
                    public int compare(ReadBean.DataBean.RepeatBean.BookBeanX.BookSegmentListBean bookSegmentListBean, ReadBean.DataBean.RepeatBean.BookBeanX.BookSegmentListBean t1) {
                        if (bookSegmentListBean.getId() > t1.getId()) {
                            return 1;
                        } else if (bookSegmentListBean.getId() == t1.getId()) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                });
                for (int i = 0; i < bookBeanList.size(); i++) {
                    iconList.add(Api.QN + bookBeanList.get(i).getIcon());
                }
                for (int i = 0; i < segmentBeanList.size(); i++) {
                    audioList.add(segmentBeanList.get(i).getAudio().getSource());
                }
                userName = TextUtils.isEmpty(userBook.getUser().getNickname()) ? "我" : userBook.getUser().getNickname();
                tvName.setText(userName);
                tvSign.setText(TextUtils.isEmpty(userBook.getUser().getSignature()) ? "暂无签名" : userBook.getUser().getSignature());
                ivDz.setImageResource(readBean.getData().getUser().getLike() == 0 ? R.drawable.admire_white : R.drawable.admire_pink);
                tvCount.setText(readBean.getData().getRepeat().getLike() + ""); //当前点赞数量
                tvTime.setText(TimeTools.getStrTime(readBean.getData().getRepeat().getCreateTime() + ""));
                adapter.notifyDataSetChanged();
                if (audioList.size() == 1) {
                    ivPlayRight.setImageResource(R.drawable.iv_right_gray);
                }
                initPlayer(audioList.get(0));
                break;
            case 100:
                tvCount.setText(admireBean.getData().getCount() + ""); //点赞数量
                ivDz.setImageResource(admireBean.getData().getStatus() == 0 ? R.drawable.admire_white : R.drawable.admire_pink);
                EventBus.getDefault().post("huibenClock"); //刷新跟读榜
                break;
            case 200:
                if (rewardMap.get("event").equals("INSUFFICIENT_BALANCE")) {
                    initDialog("余额不足，是否充值？", "充值", "取消");
                } else {
                    ToastUtil.showShort(getApplicationContext(), "打赏成功");
                    //刷新余额
                    EventBus.getDefault().post("pay");
                }
                break;
        }
    }

    @Override
    public void onSuccess(int type, String str) {
        if (isWebLoadFinish) {
            dis();
        }
        switch (type) {
            case 0:  //评论数据
                llNoComment.setVisibility(View.GONE);
                if (fl.equals("course_comment_reply")) {
                    CourseComment courseComment = new Gson().fromJson(str, CourseComment.class);
                    List<CourseComment.DataBean.ImportantBean> teacherBeanXXXXList = courseComment.getData().getImportant();
                    List<CourseComment.DataBean.NormalBean> normalList = courseComment.getData().getNormal();
                    if (teacherBeanXXXXList.size() > 0) {  //截取老师json
                        String teacherStr = ("{" + str.substring(str.indexOf("\"important\""), str.indexOf(",\"normal\"")) + "}").replace("important", "coursecom");
                        CourseCom courseCom = new Gson().fromJson(teacherStr, CourseCom.class);
                        cComList.addAll(courseCom.getCoursecom());
                    }
                    if (normalList.size() > 0) {  //有评论-普通
                        String normalStr = ("{" + str.substring(str.indexOf("\"normal\""), str.indexOf(",\"describe\""))).replace("normal", "coursecom");
                        CourseCom courseCom = new Gson().fromJson(normalStr, CourseCom.class);
                        cComList.addAll(courseCom.getCoursecom());
                    }
                    if (normalList.size() == 0 && teacherBeanXXXXList.size() == 0) {
                        if (cComList.size() > 0) {
                            tvLoadFinish.setVisibility(View.VISIBLE);
                        } else {
                            llNoComment.setVisibility(View.VISIBLE);
                        }
                    }
                    commentRecycler.getAdapter().notifyDataSetChanged();
                } else {
                    RepeatComment repeatComment = new Gson().fromJson(str, RepeatComment.class);
                    if (repeatComment.getData().getComment().size() > 0) {
                        llNoComment.setVisibility(View.GONE);
                        commentList.addAll(repeatComment.getData().getComment());
                        commentRecycler.getAdapter().notifyDataSetChanged();
                    } else {
                        if (commentList.size() > 0) {
                            tvLoadFinish.setVisibility(View.VISIBLE);
                        } else {
                            //没有评论消息
                            llNoComment.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
            case 1:

                appreciation = new Gson().fromJson(str, Appreciation.class);
                break;
            case 2:
                courseRepeatDetail = new Gson().fromJson(str, CourseRepeatDetail.class);
                break;
            case 3:
                readBean = new Gson().fromJson(str, ReadBean.class);
                break;
            case 100:
                admireBean = new Gson().fromJson(str, AdmireBean.class);
                break;
            case 200:
                rewardMap = new Gson().fromJson(str, Map.class);
                break;
        }
        initView(type);
    }

    @Override
    public void onError(String msg) {
        if (isWebLoadFinish) {
            dis();
        }
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    int pro;

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (player != null) {
            if (!player.mediaPlayer.isPlaying()) {
                player.start();
            }
        }
        String pos = seekBar.getProgress() * 1.0 / seekBar.getMax() * player.mediaPlayer.getDuration() + "";
        pro = Integer.valueOf(pos.substring(0, pos.indexOf(".")));
        player.mediaPlayer.seekTo(pro);
        if (seekBar.getProgress() == seekBar.getMax()) {
            tvPlayTime.setText(player.getRuration());
            ivStart.setImageResource(R.drawable.iv_play_white);
            player.pause();
            handler.sendEmptyMessageDelayed(100, 300);
        }
    }

    /**
     * 鼓励dialog
     */
    private void payDialog() {
        new RewardDialog(this, R.style.Dialog, new RewardDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, EditText et) {
                if (confirm) {
                    String text = et.getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        ToastUtil.showLayoutShort(getApplicationContext(), "不可为空");
                    } else {
                        if (new MoneyUtils().isZs(text)) {
                            //输入规范 整数
                            fee = Double.valueOf(text);
                            doReward(fee);
                            dialog.dismiss();
                        } else {
                            ToastUtil.showLayoutShort(getApplicationContext(), "请输入整数金额");
                            et.setText("");
                        }
                    }
                }
            }
        })
                .setTitle("请输入打赏\tH币数")
                .setEText("请输入整数数字")
                .show();
    }

    /**
     * 打赏
     */
    public void doReward(double fee) {
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("id", id);
        param.put("fee", fee);
        new MyAsyncTaskN(ToReadDetailActivity.this, 200, rewardUrl, this).execute(param);
    }

    /***
     * 播放
     */
    public void doPlay() {
        if (commentPlayer != null) {
            if (commentPlayer.isPlaying()) {
                commentPlayer.stop();
                commentPlayer.release();
                commentPlayer = null;
                playPath = "";
                commentRecycler.getAdapter().notifyDataSetChanged();
            }
        }
    }

    /**
     * 点赞操作
     */
    private void toAdmire() {
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("id", id);
        new MyAsyncTaskN(ToReadDetailActivity.this, 100, admireUrl, this).execute(param);
    }

    /**
     * 分享对话框
     */
    private void showForwardDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_forward, null);
        //取消
        dialogView.findViewById(R.id.pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.re_wChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (userName.equals(SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getNickname())) {
//                    userName = "我";
//                }
                userName = "我";
                String content = userName + "在HS英文绘本课堂朗读了" + (TextUtils.isEmpty(hbName) ? "未知" : hbName) + "绘本，快来听听吧！";
                if (flag.equals("HbRelease") || flag.equals("BridgeDetail") || flag.equals("MyHbRead")) { //用户绘本
                    wxShareUtils.shareHtml(false, 0, 0, id, shareUrl, "HS英文绘本课堂", content);
                } else if (flag.equals("CourseRelease") || flag.equals("MyCourseRead")) {
                    wxShareUtils.shareHtml(false, 0, 1, id, shareUrl, "HS英文绘本课堂", content);
                }
                bottomSheetDialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.re_w_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (userName.equals(SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getNickname())) {
//                    userName = "我";
//                }
                userName = "我";
                String content = userName + "在HS英文绘本课堂朗读了" + (TextUtils.isEmpty(hbName) ? "未知" : hbName) + "绘本，快来听听吧！";
                if (flag.equals("HbRelease") || flag.equals("BridgeDetail") || flag.equals("MyHbRead")) { //用户绘本
                    wxShareUtils.shareHtml(true, 1, 0, id, shareUrl, content, content);
                } else if (flag.equals("CourseRelease") || flag.equals("MyCourseRead")) {
                    wxShareUtils.shareHtml(true, 1, 1, id, shareUrl, content, content);
                }
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.show();
    }

    private View addViewTv(String value) {
        TextView tv = new TextView(getApplicationContext());
        tv.setText("发音很标准，哈哈哈哈哈哈");
        tv.setTextSize(14);
        tv.setTextColor(Color.GRAY);
        return tv;
    }

    private View addViewTvTwo(String name, String info, boolean isTeacher) {
        disTv = getLayoutInflater().inflate(R.layout.item_dis_tv, null);
        TextView tv_name = disTv.findViewById(R.id.tv_name);
        tv_name.setVisibility(View.GONE);
//        if (isTeacher) {
//            tv_name.setTextColor(Color.RED);
//        } else {
//            tv_name.setTextColor(Color.GREEN);
//        }
//        if (TextUtils.isEmpty(name)) {
//            if (isTeacher) {
//                tv_name.setText("匿名老师：");
//            } else {
//                tv_name.setText("匿名用户：");
//            }
//        } else {
//            tv_name.setText(name + "：");
//        }
        TextView tv_info = disTv.findViewById(R.id.tv_info);
        if (TextUtils.isEmpty(name)) {
            if (isTeacher) {
//                tv_name.setText("匿名老师：");
                tv_info.setText("匿名老师：" + info);
                TextViewUtils.setTextColor(tv_info, tv_info.getText().toString(), 0, 5, Color.RED);
            } else {
//                tv_name.setText("匿名用户：");
                tv_info.setText("匿名用户：" + info);
                TextViewUtils.setTextColor(tv_info, tv_info.getText().toString(), 0, 5, Color.parseColor("#15CF30"));
            }
        } else {
//            tv_name.setText(name + "：");
            tv_info.setText(name + "：" + info);
            TextViewUtils.setTextColor(tv_info, tv_info.getText().toString(), 0, name.length() + 1, Color.parseColor("#15CF30"));
        }
//        tv_info.setText(info);
        return disTv;
    }


    private View addViewVoice(final String voicePath, String voiceTime) {
        disVoice = getLayoutInflater().inflate(R.layout.item_discuss_voice, null);
        RelativeLayout re = disVoice.findViewById(R.id.re);
        final ImageView ivPlay = disVoice.findViewById(R.id.iv_play);
        handler.removeMessages(110);
        handler.removeMessages(220);
        if (player != null) {
            if (player.mediaPlayer.isPlaying()) {
                player.mediaPlayer.pause();
            }
        }
        if (voicePath.equals(playPath)) {
            if (commentPlayer != null) {
                if (commentPlayer.isPlaying()) {
                    ivPlay.setImageResource(R.drawable.iv_isplay_white);
                } else {
                    ivPlay.setImageResource(R.drawable.iv_play_white);
                }
            }
        }
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPlay(voicePath, ivPlay);
            }
        });
        TextView tv_time = disVoice.findViewById(R.id.tv_time);
        tv_time.setText(TimeTools.timeParse(Integer.valueOf(voiceTime)));
        return disVoice;
    }

    private View addViewVoiceTv(final String voicePath, String voiceTime, String name, boolean isTeacher) {
        disVoiceTv = getLayoutInflater().inflate(R.layout.item_dis_voicetv, null);
        RelativeLayout re = disVoiceTv.findViewById(R.id.re);
        TextView tv_name = disVoiceTv.findViewById(R.id.tv_name);
        if (isTeacher) {
            tv_name.setTextColor(Color.RED);
        } else {
            tv_name.setTextColor(Color.GREEN);
        }
        if (TextUtils.isEmpty(name)) {
            if (isTeacher) {
                tv_name.setText("匿名老师：");
            } else {
                tv_name.setText("匿名用户：");
            }
        } else {
            tv_name.setText(name + "：");
        }
        final ImageView ivPlay = disVoiceTv.findViewById(R.id.iv_play);
        handler.removeMessages(110);
        handler.removeMessages(220);
        if (player != null) {
            if (player.mediaPlayer.isPlaying()) {
                player.mediaPlayer.pause();
            }
        }
        if (voicePath.equals(playPath)) {
            if (commentPlayer != null) {
                if (commentPlayer.isPlaying()) {
                    ivPlay.setImageResource(R.drawable.iv_isplay_white);
                } else {
                    ivPlay.setImageResource(R.drawable.iv_play_white);
                }
            }
        }
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPlay(voicePath, ivPlay);
            }
        });
        TextView tv_time = disVoiceTv.findViewById(R.id.tv_time);
        tv_time.setText(TimeTools.timeParse(Integer.valueOf(voiceTime)));
        return disVoiceTv;
    }

    /**
     * 评论语音播放
     */
    public void doPlay(String voicePath, ImageView iv_play) {
        if (player != null) {
            if (player.mediaPlayer.isPlaying()) {
                player.pause();
                ivStart.setImageResource(R.drawable.iv_play_white);
            }
        }
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
                        commentRecycler.getAdapter().notifyDataSetChanged();
                    }
                });
                iv_play.setImageResource(R.drawable.iv_isplay_white);
                playPath = voicePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        commentRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webview.onPause();
        if (player != null) {
            if (player.mediaPlayer != null) {
                if (player.mediaPlayer.isPlaying()) {
                    player.pause();
                }
                ivStart.setImageResource(R.drawable.iv_play_white);
            }
        }
        if (commentPlayer != null) {
            if (commentPlayer.isPlaying()) {
                commentPlayer.stop();
                commentPlayer.release();
                commentPlayer = null;
                playPath = "";
                commentRecycler.getAdapter().notifyDataSetChanged();
            }
        }
    }

    /**
     * 删除文件
     */
    public void deleteFile() {
        if (permissionRead) {
            //删除根目录下所有mp3，amr类型的文件,避免文件的删除不及时
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].getName().contains("mp3") || fileList[i].getName().contains("amr")) {
                    CacheUtils.deleteDir(fileList[i]);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(100);
        handler.removeMessages(201);
        rootLayout.removeView(webview);
        webview.destroy();
        EventBus.getDefault().unregister(this);
        if (player != null) {
            if (player.mediaPlayer != null) {
                if (player.mediaPlayer.isPlaying()) {
                    player.stop();
                    player = null;
                }
            }
        }
        if (commentPlayer != null) {
            if (commentPlayer.isPlaying()) {
                commentPlayer.stop();
                commentPlayer.release();
                commentPlayer = null;
                playPath = "";
                commentRecycler.getAdapter().notifyDataSetChanged();
            }
        }
        dis();
        //删除全部下载过的文件
        deleteFile();
    }

    public void dis() {
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
    }
}
