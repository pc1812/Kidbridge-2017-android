package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.adpter.BookListAdapter;
import education.zhiyuan.com.picturebooks.bean.BookListBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CustomLoadMoreView;

/**
 * Created by Lance on 2017/6/20.
 * Email : COCOINUT@163.com
 * Introduce : 今日打卡 / 热门书单 --> 更多  书单  书单集合
 */

public class ToDayClockActivity extends AppCompatActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    private List<BookListBean.DataBean> bookList;  //书单列表
    private String Type;
    private String url;
    private int offset = 0, limit = 10;
    private Dialog pBar;
    private boolean isLoading = false; //是否正在加载数据

    private BookListAdapter bookListAdapter;
    private View emptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_todayclock);
        ButterKnife.bind(this);
        tvRight.setVisibility(View.GONE);
        Type = getIntent().getStringExtra("type");
        if (Type.equals("today")) {
            tvTitle.setText("今日打卡");
            url = "/bookshelf/recommend/list";
        } else {
            tvTitle.setText("系列书单");
            url = "/bookshelf/hot/list";
        }
        bookList = new ArrayList<>();
        initView();
        getDate(offset, limit);
    }

    /**
     * 初始化数据
     */
    private void getDate(int offset, int limit) {
        tvNoData.setVisibility(View.GONE);
        if (!isLoading) {
            isLoading = true; //正在加载
            pBar = new Dialog(ToDayClockActivity.this, R.style.Dialog);
            pBar.setContentView(R.layout.progress);
            if (!isFinishing()) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    pBar.show();
                }
            }
        }
        Map param = new HashMap();
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("timestamp", System.currentTimeMillis());
        param.put("offset", offset);
        param.put("limit", limit);
        new MyAsyncTaskN(ToDayClockActivity.this, 0, url, this).execute(param);
    }

    private void initView() {
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    getDate(offset, limit);
                }
            }
        });
        emptyView = getLayoutInflater().inflate(R.layout.view_empty, null);
        bookListAdapter = new BookListAdapter(R.layout.item_card_books, bookList);
        bookListAdapter.setEmptyView(emptyView);
        bookListAdapter.setLoadMoreView(new CustomLoadMoreView());
        bookListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (!swipeRefreshLayout.isRefreshing()) {
                    getDate(bookList.size(), limit);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    bookListAdapter.loadMoreComplete();
                }
            }
        }, mRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(bookListAdapter);
        bookListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), BookListDetailActivity.class);
                intent.putExtra("id", bookList.get(position).getId());
                intent.putExtra("name", bookList.get(position).getName());
                startActivity(intent);
            }
        });
    }

    public static void newInstance(Context context, String Type) {
        Intent intent = new Intent(context, ToDayClockActivity.class);
        intent.putExtra("type", Type);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(int type, String str) {
        if (swipeRefreshLayout.isRefreshing()) {
            bookList.clear();
        }
        dis();
        BookListBean bookListBean = new Gson().fromJson(str, BookListBean.class);
        if (bookListBean.getData().size() > 0) {
            bookListAdapter.addData(bookListBean.getData());
            bookListAdapter.loadMoreComplete();
        } else {
            bookListAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onError(String msg) {
        dis();
        bookListAdapter.loadMoreFail();
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    public void dis() {
        isLoading = false;  //加载完成
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dis();
    }
}
