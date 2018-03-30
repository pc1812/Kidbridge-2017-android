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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

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
import education.zhiyuan.com.picturebooks.activity.BookListDetailActivity;
import education.zhiyuan.com.picturebooks.activity.SearchActivity;
import education.zhiyuan.com.picturebooks.activity.ToDayClockActivity;
import education.zhiyuan.com.picturebooks.adpter.BookListAdapter;
import education.zhiyuan.com.picturebooks.adpter.FragmentAgeAdapter;
import education.zhiyuan.com.picturebooks.bean.BookListBean;
import education.zhiyuan.com.picturebooks.bean.WhirligigBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CarouselBanner;

/**
 * Created by Lance on 2017/6/16.
 * Email : COCOINUT@163.com
 * Introduce :  绘本馆
 */

public class HomeFragment extends Fragment implements HttpCallBackN {

    @BindView(R.id.banner)
    CarouselBanner banner;
    @BindView(R.id.ageTabLayout)
    TabLayout ageTabLayout;
    @BindView(R.id.ageViewPage)
    ViewPager ageViewPage;
    @BindView(R.id.hotRecyclerView)
    RecyclerView hotRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.ivBarLeft)
    ImageView ivBarLeft;
    @BindView(R.id.iv_today_clock)
    ImageView ivTodayClock;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.li_today_more)
    LinearLayout liTodayMore;
    @BindView(R.id.li_age_more)
    LinearLayout liAgeMore;
    @BindView(R.id.iv_home_hot)
    ImageView ivHomeHot;
    @BindView(R.id.hot_more)
    ImageView hotMore;
    @BindView(R.id.li_hot_more)
    LinearLayout liHotMore;
    @BindView(R.id.todayRecycler)
    RecyclerView todayRecycler;
    @BindView(R.id.today_ll)
    LinearLayout todayLl;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_today)
    TextView tvToday;
    @BindView(R.id.tv_more_today)
    TextView tvMoreToday;

    //TabLayout相关 年龄
    private List<Fragment> AgeFragmentList = new ArrayList<>();
    private List<String> TabTitle = new ArrayList<>();
    private FragmentAgeAdapter fragmentAgeAdapter;
    //图片轮播相关
    private Dialog pBar;   //网络加载
    private List<String> bannerList;
    private List<String> pathList;
    private WhirligigBean whirligigBean; //轮播图
    private BookListBean hotBean, todayBean; //热门，今日 书单
    private List<BookListBean.DataBean> hotBookList, todayBookList; //热门书单  今日打卡书单
    private Map param;
    private String url;
    private BookListAdapter todayBookListAdapter, hotBookListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hotBookList.clear();
                todayBookList.clear();
                InitDate(true);
                initAge(true);
            }
        });
        init();
        InitDate(false);
        InitView();
        return view;
    }

    public void init() {
        //图片轮播
        bannerList = new ArrayList<>();
        pathList = new ArrayList<>(); //轮播图path
        //年龄
        TabTitle.add("3-5岁\t\t");
        TabTitle.add("6-8岁\t\t");
        TabTitle.add("9-12岁");
        for (int i = 0; i < 3; i++) {
            FragmentHuibenAge fragmentAgePhaseOne = FragmentHuibenAge.newInstance2("home", i);
            AgeFragmentList.add(fragmentAgePhaseOne);
        }
        //今日打卡
        todayBookList = new ArrayList<>();
        //热门书单
        hotBookList = new ArrayList<>();
        //参
        param = new HashMap();
        param.put("version", "1.0.0");
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
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

    /**
     * 初始化数据
     */
    private void InitDate(boolean isRefresh) {

        url = "/whirligig/list";
        Map whParam = new HashMap();
        whParam.put("timestamp", System.currentTimeMillis());
        whParam.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
        new MyAsyncTaskN(getActivity(), 0, url, this).execute(whParam);  //TODO   图片轮播

        url = "/bookshelf/hot/list";
        Map hotParam = new HashMap();
        hotParam.put("timestamp", System.currentTimeMillis());
        hotParam.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
        hotParam.put("offset", 0);
        hotParam.put("limit", 4);
        new MyAsyncTaskN(getActivity(), 1, url, this).execute(hotParam);  //TODO 热门书单

        url = "/bookshelf/recommend/list";
        Map todeyParam = new HashMap<>();
        todeyParam.put("timestamp", System.currentTimeMillis());
        todeyParam.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
        todeyParam.put("offset", 0);
        todeyParam.put("limit", 2);
        new MyAsyncTaskN(getActivity(), 2, url, this).execute(todeyParam);  //TODO 今日书单
    }

    private void InitView() {
        todayBookListAdapter = new BookListAdapter(R.layout.item_card_books, todayBookList);
        hotBookListAdapter = new BookListAdapter(R.layout.item_card_books, hotBookList);

        //今日书单
        todayRecycler.setNestedScrollingEnabled(false);
        todayRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        todayRecycler.setAdapter(todayBookListAdapter);
        todayBookListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), BookListDetailActivity.class);
                intent.putExtra("name", todayBookList.get(position).getName());
                intent.putExtra("id", todayBookList.get(position).getId());
                startActivity(intent);
            }
        });

        //热门书单
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2) {
            @Override
            public boolean canScrollVertically() {//是否能在竖直方向上滑动
                return false;
            }
        };
        hotRecyclerView.setLayoutManager(linearLayoutManager);
        hotRecyclerView.setAdapter(hotBookListAdapter);
        hotBookListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), BookListDetailActivity.class);
                intent.putExtra("name", hotBookList.get(position).getName());
                intent.putExtra("id", hotBookList.get(position).getId());
                startActivity(intent);
            }
        });
        initAge(false);
    }

    public void initAge(boolean refresh) {
        if (refresh) {
            AgeFragmentList.clear();
            for (int i = 0; i < 3; i++) {
                FragmentHuibenAge fragmentAgePhaseOne = FragmentHuibenAge.newInstance2("home", i);
                AgeFragmentList.add(fragmentAgePhaseOne);
            }
        }
        // TODO 年龄
        fragmentAgeAdapter = new FragmentAgeAdapter(getChildFragmentManager(), AgeFragmentList, TabTitle);
        ageViewPage.setOffscreenPageLimit(3);
        ageViewPage.setAdapter(fragmentAgeAdapter);
        ageTabLayout.setTabMode(TabLayout.MODE_FIXED);
        ageTabLayout.setupWithViewPager(ageViewPage);

    }

    @OnClick({R.id.ivBarLeft, R.id.li_today_more, R.id.li_age_more, R.id.li_hot_more, R.id.tvSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSearch:
            case R.id.ivBarLeft:
                //搜索
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.li_today_more:
                //今日打卡更多
                ToDayClockActivity.newInstance(getContext(), "today");
                break;
            case R.id.li_age_more:
                //年龄更多
                Intent intent = new Intent(getContext(), AgeMoreActivity.class);
                intent.putExtra("str", "home");
                intent.putExtra("pos", ageViewPage.getCurrentItem());
                startActivity(intent);
                break;
            case R.id.li_hot_more:
                //热门更多
                ToDayClockActivity.newInstance(getContext(), "hot");
                break;
        }
    }

    private void initView(int type) {
        switch (type) {
            case 0:
                banner.stopAutoPlay();
                bannerList.clear();
                pathList.clear();
                for (int i = 0; i < whirligigBean.getData().size(); i++) {
                    bannerList.add(Api.QN + whirligigBean.getData().get(i).getIcon());
                    pathList.add(whirligigBean.getData().get(i).getLink());
                }
                banner.setmPageMargin(20);
                banner.setCardElevation(60);
                banner.setRadius(20);
                banner.setImagesUrl(bannerList);
                banner.setOnItemClickListener(new CarouselBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getContext(), BannerDetailActivity.class);
                        intent.putExtra("url", pathList.get(position));
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                hotBookList.addAll(hotBean.getData());
                if (hotRecyclerView.getAdapter() != null) {
                    hotRecyclerView.getAdapter().notifyDataSetChanged();
                }
                break;
            case 2:
                todayBookList.addAll(todayBean.getData());
                if (todayRecycler.getAdapter() != null) {
                    todayRecycler.getAdapter().notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onSuccess(int type, String str) {
        dis();
        switch (type) {
            case 0:
                whirligigBean = new Gson().fromJson(str, WhirligigBean.class);
                break;
            case 1:
                hotBean = new Gson().fromJson(str, BookListBean.class);
                break;
            case 2:
                todayBean = new Gson().fromJson(str, BookListBean.class);
                break;
        }
        initView(type);
    }


    @Override
    public void onError(String msg) {
        dis();
        ToastUtil.showShort(getContext(), msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                pBar = null;
            }
        }
    }

}
