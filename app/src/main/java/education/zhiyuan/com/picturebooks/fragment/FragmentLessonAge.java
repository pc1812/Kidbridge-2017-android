package education.zhiyuan.com.picturebooks.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.activity.LessonDetailActivity;
import education.zhiyuan.com.picturebooks.adpter.LessonAgeAdapter;
import education.zhiyuan.com.picturebooks.base.BaseFragment;
import education.zhiyuan.com.picturebooks.bean.MyLessonBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CustomLoadMoreView;

/**
 * Created by Lance on 2017/6/16.
 * Email : COCOINUT@163.com
 * Introduce :   课程馆age   课程馆更多age
 */

public class FragmentLessonAge extends BaseFragment implements HttpCallBackN {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.ll_mes)
    LinearLayout llMes;

    Unbinder unbinder;
    private List<MyLessonBean.DataBean> list;
    private String Type;
    private int pos;
    private int offset = 0, limit = 10;  //数据起始值
    private boolean isLoading;
    private Dialog pBar;
    private int hight;
    private LessonAgeAdapter lessonAgeAdapter;
    private View emptyView;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Type = bundle.getString("type");
            pos = bundle.getInt("pos");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recyclerview, null);
        unbinder = ButterKnife.bind(this, view);
        list = new ArrayList<>();
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        emptyView = getActivity().getLayoutInflater().inflate(R.layout.view_empty, null);
        if (Type.equals("lesson")) {
            initData(0, 2);
            swipeRefreshLayout.setNestedScrollingEnabled(false);
        } else {
            initData(offset, limit);
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    initData(offset, limit);
                }
            }
        });
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO 获取控件高度
        ViewTreeObserver vto = ll.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (MyApp.llHeight == -1) {
                    hight = ll.getHeight();
                    MyApp.llHeight = hight;
                } else {
                    hight = MyApp.llHeight;
                }
                ll.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    private void initData(final int offset, final int limit) {

        pBar = new Dialog(getContext(), R.style.Dialog);
        pBar.setContentView(R.layout.progress);
        if (!getActivity().isFinishing()) {
            if (!swipeRefreshLayout.isRefreshing()) {
                pBar.show();
            }
        }
        isLoading = true;
        String url = "/course/list";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
        param.put("fit", pos);
        param.put("offset", offset);
        param.put("limit", limit);
        new MyAsyncTaskN(getActivity(), 0, url, this).execute(param);
    }

    private void initView() {
        lessonAgeAdapter = new LessonAgeAdapter(R.layout.item_lesson, list);
        if (Type.equals("lesson")) {
            lessonAgeAdapter.setEnableLoadMore(false);
        } else {
            lessonAgeAdapter.setEnableLoadMore(true);
            lessonAgeAdapter.setEmptyView(emptyView);
            lessonAgeAdapter.setLoadMoreView(new CustomLoadMoreView());
            lessonAgeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        initData(list.size(), limit);
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        lessonAgeAdapter.loadMoreComplete();
                    }
                }
            }, mRecyclerView);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(lessonAgeAdapter);
        lessonAgeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), LessonDetailActivity.class);
                intent.putExtra("id", list.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void ShowView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        dis();
    }

    public static FragmentLessonAge newInstance2(String str, int pos) {
        FragmentLessonAge fragment = new FragmentLessonAge();
        Bundle b = new Bundle();
        b.putString("type", str);
        b.putInt("pos", pos);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onSuccess(int type, String str) {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                list.clear();
            }
        }
        dis();
        MyLessonBean data = new Gson().fromJson(str, MyLessonBean.class);
        if (lessonAgeAdapter != null) {
            if (data.getData().size() > 0) {
                lessonAgeAdapter.addData(data.getData());
                lessonAgeAdapter.loadMoreComplete();
                lessonAgeAdapter.notifyDataSetChanged();
            } else {
                lessonAgeAdapter.loadMoreEnd();
            }
            if (Type.equals("lesson")) {
                //设置高度 满足自适应
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hight * list.size()));
            } else {
                //更多里面，不用管
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            }
        }
    }

    @Override
    public void onError(String msg) {
        dis();
        lessonAgeAdapter.loadMoreFail();
        ToastUtil.showShort(getContext(), msg);
    }

    public void dis() {
        isLoading = false;
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);   //停止刷新
            }
        }
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
    }
}
