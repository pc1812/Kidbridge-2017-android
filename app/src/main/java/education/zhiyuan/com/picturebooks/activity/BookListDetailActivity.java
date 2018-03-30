package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.adpter.BookListDetailAdapter;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.bean.BookDetial;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * 书单详情---多个绘本  书单名称+绘本列表
 */
public class BookListDetailActivity extends BaseActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_todayclock)
    ImageView ivTodayclock;

    private List<BookDetial.DataBean.BookListBean> bookList;
    private int id;
    private BookDetial bookDetial;
    private Dialog pBar;
    private boolean isLoading = false;
    private String name;
    private BookListDetailAdapter listAdapter;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDate();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_book_list_detail);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        emptyView = getLayoutInflater().inflate(R.layout.view_empty, null);
        if (MyApp.resolutionW != -1) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MyApp.resolutionW, MyApp.resolutionW / 9 * 5);
            ivTodayclock.setLayoutParams(lp);
        }
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("id", -1);
            name = intent.getStringExtra("name");
        }
        bookList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        tvRight.setVisibility(View.GONE);
        tvTitle.setText(name);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        listAdapter = new BookListDetailAdapter(R.layout.item_card_books, bookList);
        listAdapter.setEmptyView(emptyView);
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //绘本详情
                Intent intent = new Intent(getApplicationContext(), BridgeDetail.class);
                intent.putExtra("id", bookList.get(position).getId());
                intent.putExtra("name", bookList.get(position).getName());
                if (bookList.get(position).getPrice() > 0) {
                    intent.putExtra("isFree", false);
                } else {
                    intent.putExtra("isFree", true);
                }
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(listAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("unlock_hb")) {
            bookList.clear();
            getDate();
        }
    }

    private void getDate() {
        isLoading = true;
        pBar = new Dialog(BookListDetailActivity.this, R.style.Dialog);
        pBar.setContentView(R.layout.progress);
        if (!isFinishing()) {
            pBar.show();
        }
        String url = "/bookshelf/get";
        Map param = new HashMap();
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("timestamp", System.currentTimeMillis());
        param.put("id", id);
        new MyAsyncTaskN(this, 0, url, this).execute(param);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onSuccess(int type, String str) {
        Log.e("unun", "onSuccess: ");
        dis();
        bookDetial = new Gson().fromJson(str, BookDetial.class);
        GlideUtils.GlideNormal(getApplicationContext(), Api.QN + bookDetial.getData().getCover().getIcon(), ivTodayclock, R.drawable.iv_replace_les);
        ivTodayclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BannerDetailActivity.class);
                intent.putExtra("url", bookDetial.getData().getCover().getLink());
                startActivity(intent);
            }
        });
        if (bookDetial.getData().getBookList().size() > 0) {
            bookList.addAll(bookDetial.getData().getBookList());
            if (recyclerView.getAdapter() != null) {
                recyclerView.getAdapter().notifyDataSetChanged();
            }
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
        isLoading = false;
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
                pBar = null;
            }
        }
    }
}
