package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.CourseScheduleBean;
import education.zhiyuan.com.picturebooks.bean.CourseSignBean;
import education.zhiyuan.com.picturebooks.bean.SignTodayBean;
import education.zhiyuan.com.picturebooks.calenders.MonthCalendar;
import education.zhiyuan.com.picturebooks.clicklisten.OnClickMonthCalendarListener;
import education.zhiyuan.com.picturebooks.clicklisten.OnMonthCalendarPageChangeListener;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TimeTools;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.MScrollview;

import static education.zhiyuan.com.picturebooks.utils.TimeTools.getDateStrNext;
import static education.zhiyuan.com.picturebooks.utils.TimeTools.getStr;
import static education.zhiyuan.com.picturebooks.utils.TimeTools.getStrTime;

/**
 * 我的课程-已开课 课程详情
 */
public class StartLessonDetailActivity extends AppCompatActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.clock_recycler)
    RecyclerView clockRecycler;
    @BindView(R.id.lesson_Recycler)
    RecyclerView lessonRecycler;
    @BindView(R.id.monthCalendar)
    MonthCalendar monthCalendar;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_toDaka)
    TextView tvToDaka;
    @BindView(R.id.myScroll)
    MScrollview myScroll;
    @BindView(R.id.ll_teacher)
    LinearLayout llTeacher;

    private int width, hight;
    private WindowManager wm;
    private CourseSignBean courseSignBean;
    private List<CourseSignBean.DataBean.CourseDetailListBean> courseDetailList;
    private List<CourseScheduleBean.DataBean.ScheduleBean> scheduleBeanList; //课程打卡记录
    private List<String> iconList; //最上方图片
    private CourseSignBean.DataBean.CourseDetailListBean ourseDetailListBean;
    private List<SignTodayBean.DataBean.SignBean> singBeanList; //打卡记录
    private int ID;  //belong
    private int huiBenId = -2; //绘本id
    private int lessonId; //课程id
    private SignTodayBean signTodayBean; //打卡记录
    private CourseScheduleBean courseScheduleBean;
    private long cloclStartTime; //开课时间
    private int clockCycle;//周期
    private Map<DateTime, Integer> clockMap;
    private Map<Integer, CourseSignBean.DataBean.CourseDetailListBean> huiBenMap;
    private Dialog pBar;

    private Long courseStart;
    private int courseCycle, courseTotal;
    private int offset = 0, limit = 10;
    private String clickDate; //选中的需要显示的打卡列表日期
    private boolean isTeacher = false;
    private Map<Integer, Integer> topMap = new HashMap<>();

    /**
     * 已经结束的，去打卡不可点击，讨论区跳转后，不可评论    /user/course/schedule/list  id正常
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_lesson_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        tvRight.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        if (intent != null) {
            lessonId = intent.getIntExtra("id", -1);
            ID = intent.getIntExtra("ID", -1);
            isTeacher = intent.getBooleanExtra("isTeacher", false);
        }
        wm = (WindowManager) StartLessonDetailActivity.this.getSystemService(Context.WINDOW_SERVICE);
        //初始化数据
        clickDate = TimeTools.getStr(System.currentTimeMillis() + "");
        init();
        initData();
        initLessonRecycler();
        initClockRecycler();
        monthCalendar.setOnMonthCalendarPageChangeListener(new OnMonthCalendarPageChangeListener() {
            @Override
            public void onMonthCalendarPageSelected(DateTime dateTime) {
                tvTime.setText(dateTime.getYear() + "年" + dateTime.getMonthOfYear() + "月");
            }
        });

        monthCalendar.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                monthCalendar.setChangeDate(clockMap);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //点击日期，获取时间
        monthCalendar.setOnClickMonthCalendarListener(new OnClickMonthCalendarListener() {
            @Override
            public void onClickMonthCalendar(DateTime dateTime) {
                clickDate = dateTime.toLocalDate().toString().replace("-", "");
                //刷新下面的
                singBeanList.clear();
                getClockRecording(offset, limit, clickDate);
            }
        });

        myScroll.setScrollListener(new MScrollview.ScrollListener() {
            @Override
            public void onScrollToAddMore() {
                if (singBeanList.size() > 0) {
                    //最下方打卡情况
                    getClockRecording(singBeanList.size(), limit, clickDate);
                }
            }
        });

    }

    /**
     * 最下方打卡记录
     */
    public void getClockRecording(int offset, int limit, String date) {
        String clockUrl = "/course/sign/today/list";
        Map clockParam = new HashMap();
        clockParam.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        clockParam.put("timestamp", System.currentTimeMillis());
        clockParam.put("date", date);
        clockParam.put("id", lessonId);
        clockParam.put("offset", offset);
        clockParam.put("limit", limit);
        new MyAsyncTaskN(StartLessonDetailActivity.this, 1, clockUrl, this).execute(clockParam);
    }

    /**
     * 刷新
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("courseClock")) {
            //刷新最下方打卡列表
            singBeanList.clear();
            clickDate = TimeTools.getStr(System.currentTimeMillis() + "");
            getClockRecording(offset, limit, clickDate);
            scheduleBeanList.clear();
            //刷新用户打卡情况，中间
            String urlDa = "/user/course/schedule/list";
            Map paramDa = new HashMap();
            paramDa.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
            paramDa.put("timestamp", System.currentTimeMillis());
            paramDa.put("id", ID);
            paramDa.put("date", TimeTools.getStrTimeN(System.currentTimeMillis() + ""));
            new MyAsyncTaskN(StartLessonDetailActivity.this, 2, urlDa, this).execute(paramDa);
        }
    }


    private void init() {
        singBeanList = new ArrayList<>();
        courseDetailList = new ArrayList<>();
        iconList = new ArrayList<>();
        scheduleBeanList = new ArrayList<>();
        clockMap = new HashMap<>();
        huiBenMap = new HashMap<>();
        if (isTeacher) {
            llTeacher.setVisibility(View.GONE);
        }
    }

    /**
     * 课程  最上方图片
     */
    private void initLessonRecycler() {
        width = wm.getDefaultDisplay().getWidth() - lessonRecycler.getPaddingLeft() * 2;
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lessonRecycler.setLayoutManager(manager);
        lessonRecycler.setAdapter(new CommonAdapter<CourseSignBean.DataBean.CourseDetailListBean>(getApplicationContext(), R.layout.item_simple_iv, courseDetailList) {

            @Override
            protected void convert(ViewHolder holder, final CourseSignBean.DataBean.CourseDetailListBean courseDetailListBean, final int position) {
                huiBenMap.put(courseDetailListBean.getId(), courseDetailListBean);
                // 未开课的灰色显示
                long startTime = courseDetailListBean.getStartTime();
                int cycle = courseDetailListBean.getCycle();
                int totalTime = Integer.valueOf(getStr(getDateStrNext(TimeTools.getStrTime(startTime + ""), cycle - 1) + ""));
                Glide.with(getApplicationContext()).load(Api.QN + courseDetailListBean.getBook().getIcon().get(0)).into((ImageView) holder.getView(R.id.iv_lesson));
                ImageView iv = holder.getView(R.id.iv_lesson);
                //totalTime < Integer.valueOf(getStrTime(System.currentTimeMillis() + "", "yyyyMMdd")) ||
                if (startTime > System.currentTimeMillis()) {
                    //  未开课  灰色
                    iv.setColorFilter(Color.parseColor("#5F5F5F"), PorterDuff.Mode.DARKEN); //通过滤镜的方式获取
                } else {
                    iv.clearColorFilter();
                }
                // 正在开课的才可以点击
                if (totalTime >= Integer.valueOf(getStrTime(System.currentTimeMillis() + "", "yyyyMMdd")) && startTime <= System.currentTimeMillis()) {
                    holder.setOnClickListener(R.id.ll, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
                            intent.putExtra("id", courseDetailListBean.getBook().getId());
                            intent.putExtra("uid", courseSignBean.getData().getCourseDetailList().get(position).getBook().getId());
                            intent.putExtra("ID", ID);
                            intent.putExtra("flag", "startCourse");
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }

    /**
     * 今日打卡记录
     */
    private void initClockRecycler() {
        clockRecycler.setLayoutManager(new LinearLayoutManager(this));
        clockRecycler.setNestedScrollingEnabled(false);
        clockRecycler.setAdapter(new CommonAdapter<SignTodayBean.DataBean.SignBean>(getApplicationContext(), R.layout.item_clock_record, singBeanList) {
            @Override
            protected void convert(ViewHolder holder, final SignTodayBean.DataBean.SignBean signBean, int position) {
                //头像
                GlideUtils.GlideCircle(getApplicationContext(), Api.QN + signBean.getHead(), (ImageView) holder.getView(R.id.iv_pic), R.mipmap.iv_login_logo);
                if (TextUtils.isEmpty(signBean.getNickname())) {
                    holder.setText(R.id.tv_name, "匿名用户");
                } else {
                    holder.setText(R.id.tv_name, signBean.getNickname());
                }
                if (signBean.getRepeat() == -1) {
                    //未打卡
                    holder.setVisible(R.id.tv_time, false);
                    holder.setTextColor(R.id.clock_state, Color.RED);
                    holder.setText(R.id.clock_state, "未打卡");
                } else {
                    holder.setVisible(R.id.tv_time, true);
                    holder.setText(R.id.tv_time, TimeTools.getStrTimeF(signBean.getCreateTime() + ""));
                    holder.setText(R.id.clock_state, "已打卡");
                    holder.setTextColor(R.id.clock_state, Color.parseColor("#15CF30"));
                }
                holder.setOnClickListener(R.id.re_click, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (signBean.getRepeat() != -1) {
                            Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
                            intent.putExtra("id", signBean.getRepeat());
                            intent.putExtra("flag", "MyCourseRead");
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private void initData() {
        pBar = new Dialog(StartLessonDetailActivity.this, R.style.Dialog);
        pBar.setContentView(R.layout.progress);
        if (!isFinishing()) {
            pBar.show();
        }
        //最上方  图片
        String url = "/course/sign";
        Map param = new HashMap();
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("timestamp", System.currentTimeMillis());
        param.put("id", lessonId);
        new MyAsyncTaskN(StartLessonDetailActivity.this, 0, url, this).execute(param);
        //最下方，打卡情况
        getClockRecording(offset, limit, clickDate);
    }

    /**
     * 获取当前时间
     */
    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = calendar.get(Calendar.MONTH) + 1 + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        return year + month + day;
    }

    @OnClick({R.id.iv_back, R.id.tv_toDiscussion, R.id.tv_toDaka, R.id.iv_left, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_toDiscussion:
                //评论区
                Intent intentD = new Intent(this, DiscussionAreaActivity.class);
                intentD.putExtra("id", lessonId);
                startActivity(intentD);
                break;
            case R.id.tv_toDaka:
                //去打卡
                if (huiBenId == -1) {  //无效的绘本
                    ToastUtil.showShort(getApplicationContext(), "不能打卡喽");
                } else {
                    if (huiBenId != -2) {  //说明加载完数据
                        //判断是否已经打卡
                        boolean canClock = true;
                        for (int i = 0; i < scheduleBeanList.size(); i++) {
                            if (TimeTools.getStrTime(scheduleBeanList.get(i).getCreateTime() + "").equals(TimeTools.getStrTime(System.currentTimeMillis() + ""))) {
                                canClock = false;
                            }
                        }
                        if (canClock) {
                            Intent intent = new Intent(this, ToReadActivity.class);
                            intent.putExtra("type", 1);
                            intent.putExtra("lessonId", lessonId);
                            intent.putExtra("ID", ID);
                            intent.putExtra("hbId", huiBenId);
                            intent.putExtra("uid", huiBenId);
                            startActivity(intent);
                        } else {
                            ToastUtil.showShort(getApplicationContext(), "今天已经打过卡喽，请明天继续~");
                        }
                    }
                }
                break;
            case R.id.iv_left:
                monthCalendar.setCurrentItem(monthCalendar.getCurrentItem() - 1);
                break;
            case R.id.iv_right:
                monthCalendar.setCurrentItem(monthCalendar.getCurrentItem() + 1);
                break;
        }
    }

    public void getClockHb() {
        for (int i = 0; i < courseDetailList.size(); i++) {
            if (i == 0) {
                courseStart = courseDetailList.get(0).getStartTime();
            }
            CourseSignBean.DataBean.CourseDetailListBean courseDetailListBean = courseDetailList.get(i);
            //TODO  已经结束 和未开课的都灰色显示
            long startTime = courseDetailListBean.getStartTime();
            int cycle = courseDetailListBean.getCycle();
            int totalTime = Integer.valueOf(getStr(getDateStrNext(TimeTools.getStrTime(startTime + ""), cycle - 1) + ""));
            if (!(totalTime < Integer.valueOf(getStrTime(System.currentTimeMillis() + "", "yyyyMMdd")) || startTime > System.currentTimeMillis())) {
                cloclStartTime = courseDetailListBean.getStartTime();
                clockCycle = courseDetailListBean.getCycle();
                huiBenId = courseDetailListBean.getBook().getId();
            }
            courseCycle += cycle;
        }
        if (huiBenId == -1) {
            tvToDaka.setBackgroundColor(Color.GRAY);
        }
        initClock(courseStart, courseCycle);
    }

    /**
     * 刷新日历
     */
    public void initClock(Long startTime, int cycle) {
        if (!isTeacher) {
            if (huiBenId != -2) {
                int today = Integer.valueOf(TimeTools.getStr(System.currentTimeMillis() + "")); //明天的时间
                List<Integer> clockStr = new ArrayList<>();
                for (int i = 0; i < courseScheduleBean.getData().getSchedule().size(); i++) {
                    for (int j = 0; j < scheduleBeanList.size(); j++) {
                        clockStr.add(Integer.valueOf(getStr(scheduleBeanList.get(j).getCreateTime() + ""))); //已经打卡的时间
                        clockMap.put(new DateTime(scheduleBeanList.get(j).getCreateTime()), 1);
                    }
                }
                for (int i = 0; i < cycle; i++) {
                    int date = Integer.valueOf(getStr(getDateStrNext(getStrTime(startTime + ""), i) + ""));
                    if (!clockStr.contains(date)) {
                        if (date >= today) {
                            //未到打卡时间
                            clockMap.put(new DateTime(getDateStrNext(getStrTime(startTime + ""), i)), 0);
                        } else {
                            //未打卡
                            clockMap.put(new DateTime(getDateStrNext(getStrTime(startTime + ""), i)), -1);
                        }
                    }
                }
                monthCalendar.setChangeDate(clockMap);
            }
        } else {
            //老师界面。不存在打卡情况
            for (int i = 0; i < cycle; i++) {
                clockMap.put(new DateTime(getDateStrNext(getStrTime(startTime + ""), i)), 0);
            }
        }
        monthCalendar.setChangeDate(clockMap);

    }


    @Override
    public void onSuccess(int type, String str) {
        dis();
        switch (type) {
            case 0:
                courseSignBean = new Gson().fromJson(str, CourseSignBean.class);
                tvTitle.setText(courseSignBean.getData().getName());
                courseDetailList.addAll(courseSignBean.getData().getCourseDetailList());
                //TODO  排序!!!
                if (courseDetailList.size() > 0) {
                    Collections.sort(courseDetailList, new Comparator<CourseSignBean.DataBean.CourseDetailListBean>() {
                        @Override
                        public int compare(CourseSignBean.DataBean.CourseDetailListBean courseDetailListBean, CourseSignBean.DataBean.CourseDetailListBean t1) {
                            if (courseDetailListBean.getStartTime() > t1.getStartTime()) {
                                return 1;
                            } else if (courseDetailListBean.getStartTime() == t1.getStartTime()) {
                                return 0;
                            } else {
                                return -1;
                            }
                        }
                    });
                }
                lessonRecycler.getAdapter().notifyDataSetChanged();
                // 正在开课的绘本居中显示 每次只有一本正在开课
                int nowStartPos = -1;
                for (int i = 0; i < courseDetailList.size(); i++) {
                    int totalTime = Integer.valueOf(getStr(getDateStrNext(TimeTools.getStrTime(courseDetailList.get(i).getStartTime() + ""), courseDetailList.get(i).getCycle() - 1) + ""));
                    if (courseDetailList.get(i).getStartTime() <= System.currentTimeMillis() && totalTime >= Integer.valueOf(getStrTime(System.currentTimeMillis() + "", "yyyyMMdd"))) {
                        nowStartPos = i;
                    }
                }
                if (courseDetailList.size() > 5) {
                    if (nowStartPos > 1) {
                        int offset = 130;
                        if (MyApp.resolutionW > 0) {
                            offset = MyApp.resolutionW / 10 + lessonRecycler.getPaddingLeft();
                        }
                        ((LinearLayoutManager) lessonRecycler.getLayoutManager()).scrollToPositionWithOffset(nowStartPos - 1, offset);
                    }
                }
                //打卡，日期
                String urlDa = "/user/course/schedule/list";
                Map paramDa = new HashMap();
                paramDa.put("version", "1.0.0");
                paramDa.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
                paramDa.put("timestamp", System.currentTimeMillis());
                paramDa.put("id", ID);
                paramDa.put("date", TimeTools.getStrTimeN(System.currentTimeMillis() + ""));
                new MyAsyncTaskN(StartLessonDetailActivity.this, 2, urlDa, this).execute(paramDa);
                break;
            case 1:
                signTodayBean = new Gson().fromJson(str, SignTodayBean.class);
                if (signTodayBean.getData().getSign().size() > 0) {
                    singBeanList.addAll(signTodayBean.getData().getSign());
                    clockRecycler.getAdapter().notifyDataSetChanged();
                } else {
                    if (singBeanList.size() > 0) {
                        ToastUtil.showShort(getApplicationContext(), R.string.no_more_data);
                    } else {
                        ToastUtil.showShort(getApplicationContext(), R.string.no_data);
                    }
                }
                break;
            case 2:
                courseScheduleBean = new Gson().fromJson(str, CourseScheduleBean.class);
                if (courseScheduleBean.getData().getSchedule().size() > 0) {
                    scheduleBeanList.addAll(courseScheduleBean.getData().getSchedule());
                }
                huiBenId = -1;
                getClockHb();
                break;
        }
    }

    @Override
    public void onError(String msg) {
        dis();
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        dis();
    }

    public void dis() {
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
    }
}
