package education.zhiyuan.com.picturebooks.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.activity.AgeMoreActivity;
import education.zhiyuan.com.picturebooks.activity.BannerDetailActivity;
import education.zhiyuan.com.picturebooks.activity.HotCourseMoreActivity;
import education.zhiyuan.com.picturebooks.activity.LessonDetailActivity;
import education.zhiyuan.com.picturebooks.activity.MyCourseMoreActivity;
import education.zhiyuan.com.picturebooks.adpter.CourseAdapter;
import education.zhiyuan.com.picturebooks.adpter.FragmentAgeAdapter;
import education.zhiyuan.com.picturebooks.bean.MyLessonBean;
import education.zhiyuan.com.picturebooks.bean.WhirligigBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CarouselBanner;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CustomViewpager;

/**
 * Created by Lance on 2017/6/16.
 * Email : COCOINUT@163.com
 * Introduce : 课程Fragment
 */

public class LessonFragment extends Fragment implements HttpCallBackN {

    Unbinder unbinder;
    @BindView(R.id.banner)
    CarouselBanner banner;
    @BindView(R.id.lessonRecycler)
    RecyclerView lessonRecycler;
    @BindView(R.id.ageTabLayout)
    TabLayout ageTabLayout;
    @BindView(R.id.ageViewPage)
    CustomViewpager ageViewPage;
    @BindView(R.id.hotRecyclerView)
    RecyclerView hotRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * 图片轮播相关
     */
    private List<String> bannerList;
    private List<Fragment> AgeFragmentList = new ArrayList<>();
    private List<String> TabTitle = new ArrayList<>();
    private FragmentAgeAdapter fragmentAgeAdapter;
    private List<MyLessonBean.DataBean> hotList = new ArrayList<>();
    private List<MyLessonBean.DataBean> LessonList;
    private Dialog pBar;
    private List<String> urlList;
    private String url; //接口
    private Map param; //参数
    private CourseAdapter myLessonAdapter, hotLessonAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, null);
        unbinder = ButterKnife.bind(this, view);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hotList.clear();
                LessonList.clear();
                getData(true);
                initAge(true);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /**
     * 热门刷新
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("unlock_course")) {
            hotList.clear();
            url = "/course/hot/list";
            param = new HashMap();
            param.put("timestamp", System.currentTimeMillis());
            param.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
            param.put("offset", 0);
            param.put("limit", 4);
            new MyAsyncTaskN(getActivity(), 2, url, this).execute(param);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        getData(false);
        InitView();
    }

    private void initData() {
        //最上层banner
        urlList = new ArrayList<>();
        bannerList = new ArrayList<>();
        //年龄
        TabTitle.add("3-5岁\t\t");
        TabTitle.add("6-8岁\t\t");
        TabTitle.add("9-12岁");

        for (int i = 0; i < 3; i++) {
            FragmentLessonAge fragmentLessonAge = FragmentLessonAge.newInstance2("lesson", i);
            AgeFragmentList.add(fragmentLessonAge);
        }
        //我的课程
        LessonList = new ArrayList<>();
        //热门课程
        hotList = new ArrayList<>();
    }

    private void getData(boolean isRefresh) {
        if (!isRefresh) {
            pBar = new Dialog(getContext(), R.style.Dialog);
            pBar.setContentView(R.layout.progress);
            if (!getActivity().isFinishing()) {
                pBar.show();
            }
        }
        // 图片轮播
        url = "/whirligig/list";
        param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
        new MyAsyncTaskN(getActivity(), 0, url, this).execute(param);

        url = "/user/course";
        param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
        param.put("offset", 0);
        param.put("limit", 2);
        new MyAsyncTaskN(getActivity(), 1, url, this).execute(param);

        url = "/course/hot/list";
        param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
        param.put("offset", 0);
        param.put("limit", 4);
        new MyAsyncTaskN(getActivity(), 2, url, this).execute(param);
    }

    private void InitView() {
        myLessonAdapter = new CourseAdapter(R.layout.item_lesson, LessonList,0);
        hotLessonAdapter = new CourseAdapter(R.layout.item_lesson, hotList,-1);

        //我的课程
        lessonRecycler.setNestedScrollingEnabled(false);
        lessonRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        lessonRecycler.setAdapter(myLessonAdapter);
        myLessonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), LessonDetailActivity.class);
                intent.putExtra("id", LessonList.get(position).getId());
                startActivity(intent);
            }
        });
        //热门课程
        hotRecyclerView.setNestedScrollingEnabled(false);
        hotRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        hotRecyclerView.setAdapter(hotLessonAdapter);
        hotLessonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), LessonDetailActivity.class);
                intent.putExtra("id", hotList.get(position).getId());
                startActivity(intent);
            }
        });
        //年龄数据
        initAge(false);
    }

    public void initAge(boolean refresh) {
        if (refresh) {
            AgeFragmentList.clear();
            for (int i = 0; i < 3; i++) {
                FragmentLessonAge fragmentLessonAge = FragmentLessonAge.newInstance2("lesson", i);
                AgeFragmentList.add(fragmentLessonAge);
            }
        }
        fragmentAgeAdapter = new FragmentAgeAdapter(getChildFragmentManager(), AgeFragmentList, TabTitle);
        ageViewPage.setOffscreenPageLimit(3);
        ageViewPage.setAdapter(fragmentAgeAdapter);
        ageTabLayout.setupWithViewPager(ageViewPage);
        ageViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ageViewPage.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ageViewPage.resetHeight(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        setAutoPlay(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        setAutoPlay(false);
    }

    /**
     * 设置是否轮播
     */
    public void setAutoPlay(boolean isAutoPlay) {
        if (bannerList.size() > 0) {
            if (isAutoPlay) {
                banner.startAutoPlay();
            } else {
                banner.stopAutoPlay();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        dis();
    }

    @OnClick({R.id.tv_mylesson_more, R.id.tv_age_more, R.id.tv_hotCourse_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_mylesson_more:
                //我的课程 更多
                startActivity(new Intent(getContext(), MyCourseMoreActivity.class));
                break;
            case R.id.tv_age_more:
                //年龄分段 更多
                Intent intent = new Intent(getContext(), AgeMoreActivity.class);
                intent.putExtra("str", "lesson");
                intent.putExtra("pos", ageViewPage.getCurrentItem());
                startActivity(intent);
                break;
            case R.id.tv_hotCourse_more:
                //热门课程 更多
                startActivity(new Intent(getContext(), HotCourseMoreActivity.class));
                break;
        }
    }

    @Override
    public void onSuccess(int type, String str) {
        dis();
        switch (type) {
            case 0:
                WhirligigBean whirligigBean = new Gson().fromJson(str, WhirligigBean.class);
                banner.stopAutoPlay();
                bannerList.clear();
                urlList.clear();
                for (int i = 0; i < whirligigBean.getData().size(); i++) {
                    bannerList.add(Api.QN + whirligigBean.getData().get(i).getIcon());
                    urlList.add(whirligigBean.getData().get(i).getLink());
                }
                banner.setmPageMargin(30);
                banner.setCardElevation(60);
                banner.setRadius(20);
                banner.setImagesUrl(bannerList);
                banner.setOnItemClickListener(new CarouselBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getContext(), BannerDetailActivity.class);
                        intent.putExtra("url", urlList.get(position));
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                MyLessonBean lessonBean = new Gson().fromJson(str, MyLessonBean.class);
                LessonList.addAll(lessonBean.getData());
                if (lessonRecycler.getAdapter() != null) {
                    lessonRecycler.getAdapter().notifyDataSetChanged();
                }
                break;
            case 2:
                MyLessonBean lessonBeans = new Gson().fromJson(str, MyLessonBean.class);
                hotList.addAll(lessonBeans.getData());
                if (hotRecyclerView.getAdapter() != null) {
                    hotRecyclerView.getAdapter().notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onError(String msg) {
        dis();
        ToastUtil.showShort(getContext(), msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        dis();
    }

    public void dis() {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
    }

}
