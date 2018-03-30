package education.zhiyuan.com.picturebooks.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import butterknife.Unbinder;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.activity.LessonDetailActivity;
import education.zhiyuan.com.picturebooks.activity.StartLessonDetailActivity;
import education.zhiyuan.com.picturebooks.adpter.CourseAdapter;
import education.zhiyuan.com.picturebooks.bean.MyLessonBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CustomLoadMoreView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemLessonFragment extends Fragment implements HttpCallBackN {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;

    private int type;
    private String url;
    private Dialog pBar;
    private boolean isLoading = false; //是否在加载数据
    private int offset = 0, limit = 10;
    private CourseAdapter courseAdapter;
    private List<MyLessonBean.DataBean> dataBeanList;
    private View emptyView;

    public ItemLessonFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_lesson, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type", -1);
            url = bundle.getString("url");
        }
        initData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /**
     * 热门刷新
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("unlock_course")) {
            if (url.equals("/course/hot/list")){
                dataBeanList.clear();
                getData(offset, limit, type);
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        dataBeanList = new ArrayList<>();
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        emptyView = getActivity().getLayoutInflater().inflate(R.layout.view_empty, null);
        if (url.equals("/user/course") || url.equals("/teacher/course/list")) {
            courseAdapter = new CourseAdapter(R.layout.item_lesson, dataBeanList, 0);
        } else {
            courseAdapter = new CourseAdapter(R.layout.item_lesson, dataBeanList, -1);
        }
        courseAdapter.setEmptyView(emptyView);
        courseAdapter.setLoadMoreView(new CustomLoadMoreView());
        courseAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                    courseAdapter.loadMoreComplete();
                } else {
                    getData(dataBeanList.size(), limit, type);
                }
            }
        }, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (type == 0) {
                    ToastUtil.showShort(getContext(), "该课程未到开课时间，暂不能查看用户打卡记录");
                }
                if (type == 1) {
                    Intent intent = new Intent(getContext(), StartLessonDetailActivity.class);
                    intent.putExtra("id", dataBeanList.get(position).getId());
                    intent.putExtra("isTeacher", true);
                    startActivity(intent);
                }
                if (type > 1) { //都跳转到LessonDetailActivity
                    Intent intent = new Intent(getContext(), LessonDetailActivity.class);
                    intent.putExtra("id", dataBeanList.get(position).getId());
                    startActivity(intent);
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    getData(offset, limit, type);
                }
            }
        });
        getData(offset, limit, type);
    }

    private void getData(final int offset, int limit, final int status) {
        pBar = new Dialog(getActivity(), R.style.Dialog);
        pBar.setContentView(R.layout.progress);
        if (!getActivity().isFinishing()) {
            if (!swipeRefreshLayout.isRefreshing()) {
                pBar.show();
            }
        }
        isLoading = true;
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getActivity()).getToken());
        param.put("offset", offset);
        param.put("limit", limit);
        if (type == 0 || type == 3 || type == 5) {
            param.put("status", 0);
        }
        if (type == 1 || type == 4 || type == 6) {
            param.put("status", 1);
        }
        if (type == 7) {
            param.put("status", 2);
        }
        new MyAsyncTaskN(getActivity(), status, url, this).execute(param);
    }


    @Override
    public void onSuccess(int type, String str) {
        if (swipeRefreshLayout.isRefreshing()) {
            dataBeanList.clear();
        }
        dis();
        MyLessonBean lessonBean = new Gson().fromJson(str, MyLessonBean.class);
        if (lessonBean.getData().size() > 0) {
            courseAdapter.addData(lessonBean.getData());
            courseAdapter.loadMoreComplete();
        } else {
            courseAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showShort(getActivity(), msg);
        courseAdapter.loadMoreFail();
    }

    public void dis() {
        isLoading = false;
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);   //停止刷新
        }
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
