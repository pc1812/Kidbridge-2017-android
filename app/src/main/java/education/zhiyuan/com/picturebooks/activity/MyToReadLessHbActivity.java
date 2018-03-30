package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.CourseBookRepeat;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TimeTools;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * 我的跟读，课程跟读-item点击，单个课程的绘本列表
 */
public class MyToReadLessHbActivity extends AppCompatActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.huiben_recycler)
    RecyclerView huibenRecycler;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<CourseBookRepeat.DataBean> bookList;  //绘本
    private int id; //课程id
    private int offset = 0, limit = 10;
    private CourseBookRepeat courseBookRepeat;
    private Dialog pBar;
    private boolean isLoading = false;
    private String chooseDate; //选择的日期
    private List<String> dataList; //日期


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_to_read_less_hb);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        tvRight.setVisibility(View.GONE);
        tvTitle.setText("跟读记录");
        bookList = new ArrayList<>();
        dataList = new ArrayList<>();
        initRecycler();
        getData(offset, limit);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    bookList.clear();
                    getData(offset, limit);
                }
            }
        });
    }

    private void getData(int offset, int limit) {
        if (!isLoading) {
            isLoading = true;
            pBar = new Dialog(MyToReadLessHbActivity.this, R.style.Dialog);
            pBar.setContentView(R.layout.progress);
            if (!isFinishing()) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    pBar.show();
                }
            }
        }
        String huiBenUrl = "/user/course/book/repeat/list";
        Map huibParam = new HashMap();
        huibParam.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        huibParam.put("timestamp", System.currentTimeMillis());
        huibParam.put("id", id);
        huibParam.put("offset", offset);
        huibParam.put("limit", limit);
        new MyAsyncTaskN(MyToReadLessHbActivity.this, 0, huiBenUrl, this).execute(huibParam);
    }

    private void initRecycler() {
        huibenRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        huibenRecycler.setAdapter(new CommonAdapter<CourseBookRepeat.DataBean>(this, R.layout.item_to_read_books, bookList) {

            @Override
            protected void convert(ViewHolder holder, final CourseBookRepeat.DataBean dataBean, final int position) {
                holder.setVisible(R.id.iv_charge, false);
                GlideUtils.GlideNormal(getApplicationContext(), Api.QN + dataBean.getIcon().get(0), (ImageView) holder.getView(R.id.iv_image), R.drawable.iv_replace_hb);
                holder.setText(R.id.tv_title, dataBean.getName());
                holder.setVisible(R.id.tv_left, dataBean.getTag().size() > 0);
                holder.setText(R.id.tv_left, dataBean.getTag().size() > 0 ? dataBean.getTag().get(0) : "");
                holder.setVisible(R.id.tv_cen, false);
                if (dataBean.getFit() == 0) {
                    holder.setText(R.id.tv_right, "3-5岁");
                } else if (dataBean.getFit() == 1) {
                    holder.setText(R.id.tv_right, "6-8岁");
                } else if (dataBean.getFit()==2){
                    holder.setText(R.id.tv_right, "9-12岁");
                }else if (dataBean.getFit()==3){
                    holder.setText(R.id.tv_right, "4-7岁");
                }else if (dataBean.getFit()==4){
                    holder.setText(R.id.tv_right, "8-10岁");
                }
                holder.setOnClickListener(R.id.ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showChooseDate(position, bookList.get(position).getRepeat());
                    }
                });
            }
        });
        huibenRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0) { //如果滑动停止
                    if (!swipeRefreshLayout.isRefreshing()) {  //如果没在刷新数据
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        int totalItemCount = recyclerView.getAdapter().getItemCount();
                        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                        if (lastVisibleItem + 1 == totalItemCount && lastVisibleItem > 0
                                && recyclerView.computeVerticalScrollOffset() > 0) { //当前屏幕之前滑过的距离，
                            getData(bookList.size(), limit);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    /**
     * 选择日期，可选时间只有给出的打卡时间
     */
    public void showChooseDate(final int pos, final List<CourseBookRepeat.DataBean.RepeatBean> repeatBeanList) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.item_data_choice, null);
        final RecyclerView recyclerView = dialogView.findViewById(R.id.recycler_data);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    bottomSheetDialog.setCancelable(false);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    bottomSheetDialog.setCancelable(true);
                }
                return true;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommonAdapter<CourseBookRepeat.DataBean.RepeatBean>(this, R.layout.item_clock_date, repeatBeanList) {
            @Override
            protected void convert(ViewHolder holder, final CourseBookRepeat.DataBean.RepeatBean repeatBean, final int position) {
                holder.setText(R.id.tv, TimeTools.parseTime(repeatBeanList.get(position).getDate() + ""));
                holder.setOnClickListener(R.id.tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
                        intent.putExtra("id", repeatBeanList.get(position).getId());
                        intent.putExtra("uid", courseBookRepeat.getData().get(position).getId());
                        intent.putExtra("lessonId", courseBookRepeat.getData().get(pos).getId());
                        intent.putExtra("name", courseBookRepeat.getData().get(pos).getName());
                        intent.putExtra("flag", "MyCourseRead");
                        startActivity(intent);
                        bottomSheetDialog.dismiss();
                    }
                });
            }
        });
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.show();
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onSuccess(int type, String str) {
        dis();
        courseBookRepeat = new Gson().fromJson(str, CourseBookRepeat.class);
        if (courseBookRepeat.getData().size() > 0) {
            bookList.addAll(courseBookRepeat.getData());
            huibenRecycler.getAdapter().notifyDataSetChanged();
        } else {
            if (bookList.size() > 0) {
                ToastUtil.showShort(getApplicationContext(), R.string.no_more_data);
            } else {
                ToastUtil.showShort(getApplicationContext(), R.string.no_data);
            }
        }
    }

    @Override
    public void onError(String msg) {
        dis();
        ToastUtil.showShort(getApplicationContext(), msg);
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
    protected void onDestroy() {
        super.onDestroy();
        dis();
    }
}
