package education.zhiyuan.com.picturebooks.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import education.zhiyuan.com.picturebooks.adpter.MyBridgeAdapter;
import education.zhiyuan.com.picturebooks.bean.MyHuiBenBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CustomLoadMoreView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemHbFragment extends Fragment implements HttpCallBackN {

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
    private MyBridgeAdapter bridgeAdapter;
    private List<MyHuiBenBean.DataBean> dataBeanList;
    private View emptyView;
    private MyHuiBenBean hbenBean;

    public ItemHbFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_hb, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type", -1);
            url = bundle.getString("url");
        }
        initData();
        return view;
    }

    private void initData() {
        dataBeanList = new ArrayList<>();
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        emptyView = getActivity().getLayoutInflater().inflate(R.layout.view_empty, null);
        bridgeAdapter = new MyBridgeAdapter(R.layout.item_card_books, dataBeanList, type);
        bridgeAdapter.setEmptyView(emptyView);
        bridgeAdapter.setLoadMoreView(new CustomLoadMoreView());
        bridgeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                    bridgeAdapter.loadMoreComplete();
                } else {
                    getData(dataBeanList.size(), limit, type);
                }
            }
        }, recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(bridgeAdapter);
        bridgeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), BridgeDetail.class);
                intent.putExtra("name", dataBeanList.get(position).getName());
                intent.putExtra("id", dataBeanList.get(position).getId());
                startActivity(intent);
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

    private void getData(final int offset, int limit, final int free) {
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
        param.put("free", free);
        new MyAsyncTaskN(getActivity(), free, url, this).execute(param);
    }


    @Override
    public void onSuccess(int type, String str) {
        if (swipeRefreshLayout.isRefreshing()) {
            dataBeanList.clear();
        }
        dis();
        hbenBean = new Gson().fromJson(str, MyHuiBenBean.class);
        if (hbenBean.getData().size() > 0) {
            bridgeAdapter.addData(hbenBean.getData());
            bridgeAdapter.loadMoreComplete();
        } else {
            bridgeAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onError(String msg) {
        dis();
        ToastUtil.showShort(getActivity(), msg);
        bridgeAdapter.loadMoreFail();
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