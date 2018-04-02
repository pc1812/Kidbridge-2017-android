package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import education.zhiyuan.com.picturebooks.adpter.BookListRecyclerViewAdapter;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.bean.BookDetial;
import education.zhiyuan.com.picturebooks.bean.DataBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * 书单详情---多个绘本  书单名称+绘本列表
 */
public class BookListDetailActivity extends BaseActivity implements HttpCallBackN,BookListRecyclerViewAdapter.ItemOnClick {

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
    private BookListRecyclerViewAdapter listAdapter;
    private View emptyView;
    private List<DataBean>  dataBeanList=new ArrayList<>();

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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(
                new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int postion) {
                        if (listAdapter.getItemViewType(postion) == 1) {
                            return 2;
                        } else {
                            return 1;
                        }
                    }
                });
        recyclerView.setLayoutManager(gridLayoutManager);
        listAdapter = new BookListRecyclerViewAdapter(this,dataBeanList);
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
        if(dataBeanList!=null){
            dataBeanList.clear();
        }
        bookDetial = new Gson().fromJson(str, BookDetial.class);
        DataBean dataBean=new DataBean();
        dataBean.setType(1);
        dataBean.setIcon(bookDetial.getData().getCover().getIcon());
        dataBeanList.add(dataBean);
        if (bookDetial.getData().getBookList().size() > 0) {
            for (int i=0;i<bookDetial.getData().getBookList().size();i++){
                DataBean data=new DataBean();
                data.setType(2);
                data.setBookList(bookDetial.getData().getBookList().get(i));
                dataBeanList.add(data);
            }
            if (listAdapter != null) {
                listAdapter.notifyDataSetChanged();
            }
        }else {
            if(bookList.size()==0){
                emptyView.setVisibility(View.VISIBLE);
            }else {
                emptyView.setVisibility(View.GONE);
            }
            if (listAdapter != null) {
                listAdapter.notifyDataSetChanged();
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

    @Override
    public void itemClick(int position) {
        //绘本详情
        Intent intent = new Intent(getApplicationContext(), BridgeDetail.class);
        intent.putExtra("id",dataBeanList.get(position).getBookList().getId());
        intent.putExtra("name", dataBeanList.get(position).getBookList().getName());
        if (dataBeanList.get(position).getBookList().getPrice() > 0) {
            intent.putExtra("isFree", false);
        } else {
            intent.putExtra("isFree", true);
        }
        startActivity(intent);
    }

    @Override
    public void imageClick(int position) {
        Intent intent = new Intent(getApplicationContext(), BannerDetailActivity.class);
        intent.putExtra("url", bookDetial.getData().getCover().getLink());
        startActivity(intent);
    }
}
