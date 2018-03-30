package education.zhiyuan.com.picturebooks.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.activity.BridgeDetail;
import education.zhiyuan.com.picturebooks.adpter.HbAgeAdapter;
import education.zhiyuan.com.picturebooks.base.BaseFragment;
import education.zhiyuan.com.picturebooks.bean.HuiBenBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CustomLoadMoreView;

/**
 * Created by Lance on 2017/6/16.
 * Email : COCOINUT@163.com
 * Introduce :3-5岁Fragment      绘本馆age  / 绘本馆更多age
 */

public class FragmentHuibenAge extends BaseFragment implements HttpCallBackN {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.ll_m)
    LinearLayout llM;

    Unbinder unbinder;
    String Type;//标识
    int pos;
    private int[] fit = {0, 1, 2};
    private List<HuiBenBean.DataBean> dataBeanList;
    private int offset = 0, limit = 10;  //数据起始值
    private Map<Integer, String> fitMap = new HashMap<>();
    private Dialog pBar;
    private boolean isLoading = false;
    private HuiBenBean data;
    private HbAgeAdapter hbAgeAdapter;
    private View emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Type = bundle.getString("type");
            pos = bundle.getInt("pos");
        }
        fitMap.put(0, "3-5岁");
        fitMap.put(1, "6-8岁");
        fitMap.put(2, "9-12岁");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_huiben_age, null);
        unbinder = ButterKnife.bind(this, view);
        dataBeanList = new ArrayList<>();
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        if (Type.equals("home")) {
            initData(0, 2);
            swipeRefreshLayout.setNestedScrollingEnabled(false);
        } else {
            initData(offset, limit);
        }
        initRecycler();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    initData(offset, limit);
                }
            }
        });
        return view;
    }

    private void initData(int offset, int limit) {
        if (!isLoading) {
            isLoading = true;
            pBar = new Dialog(getContext(), R.style.Dialog);
            pBar.setContentView(R.layout.progress);
            if (!getActivity().isFinishing()) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    pBar.show();
                }
            }
        }
        String url = "/book/list";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
        param.put("fit", fit[pos]);
        param.put("offset", offset);
        param.put("limit", limit);
        new MyAsyncTaskN(getActivity(), 0, url, this).execute(param);
    }

    public void initView() {
        if (data.getData().size() > 0) {
            hbAgeAdapter.addData(data.getData());
            hbAgeAdapter.loadMoreComplete();
        } else {
            hbAgeAdapter.loadMoreEnd();
        }
    }

    /**
     * 绘本馆
     */
    private void initRecycler() {
        hbAgeAdapter = new HbAgeAdapter(R.layout.item_card_books, dataBeanList);
        emptyView = getActivity().getLayoutInflater().inflate(R.layout.view_empty, null);
        if (Type.equals("home")) {
            hbAgeAdapter.setEnableLoadMore(false);
        } else {
            hbAgeAdapter.setEnableLoadMore(true);
            hbAgeAdapter.setEmptyView(emptyView);
            hbAgeAdapter.setLoadMoreView(new CustomLoadMoreView());
            hbAgeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                        hbAgeAdapter.loadMoreComplete();
                    } else {
                        initData(dataBeanList.size(), limit);
                    }
                }
            }, mRecyclerView);

        }
        hbAgeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), BridgeDetail.class);
                intent.putExtra("id", dataBeanList.get(position).getId());
                intent.putExtra("name", dataBeanList.get(position).getName());
                if (dataBeanList.get(position).getPrice() > 0) {
                    intent.putExtra("isFree", false);
                } else {
                    intent.putExtra("isFree", true);
                }
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(hbAgeAdapter);
    }

    @Override
    protected void ShowView() {

    }

    public static FragmentHuibenAge newInstance2(String str, int pos) {
        FragmentHuibenAge fragment = new FragmentHuibenAge();
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putString("type", str);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onSuccess(int type, String str) {
        if (swipeRefreshLayout.isRefreshing()) {
            dataBeanList.clear();
        }
        dis();
        data = new Gson().fromJson(str, HuiBenBean.class);
        initView();
    }

    @Override
    public void onError(String msg) {
        dis();
        hbAgeAdapter.loadMoreFail();
        ToastUtil.showShort(getContext(), msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        dis();
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
