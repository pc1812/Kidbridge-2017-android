package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import education.zhiyuan.com.picturebooks.adpter.AdapterVP;
import education.zhiyuan.com.picturebooks.bean.CourseRepeat;
import education.zhiyuan.com.picturebooks.bean.RepeatBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TablayoutLine;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * Created by Lance on 2017/6/22.
 * Email : COCOINUT@163.com
 * Introduce :我的--我的跟读  绘本跟读、课程跟读
 */

public class MyToReadActivity extends AppCompatActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll_select_delete)
    LinearLayout llSelectDelete;
    @BindView(R.id.cb_selectAll)
    CheckBox cbSelectAll;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    private List<RecyclerView> viewList;
    private List<String> titleList;
    private AdapterVP adapterVP;
    private List<RepeatBean.DataBean> huibenList;  //绘本
    private List<CourseRepeat.DataBean> courseBeanList; //课程
    private boolean isEdit = false;  //是否点击编辑
    private boolean isDeleteAll = false; //是否点击全选
    private Map<Integer, Boolean> deleteMap;
    private Dialog pBar;
    private boolean isLoading = false;
    private Map<Integer, Boolean> map = new HashMap<>(); //数据是否加载过
    private int offset = 0, limit = 10;
    private RepeatBean repeatBean;
    private CourseRepeat courseRepeat;
    private List<RepeatBean.DataBean> deleteList;  //需要删除的list

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mytoread);
        ButterKnife.bind(this);
        init();
        initViewPager();
        initDateHuiben(offset, limit, 0); //绘本跟读
        swipeRefreshLayout.setPadding(6, 0, 6, 0);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    if (viewPage.getCurrentItem() == 0) {
                        huibenList.clear();
                    } else {
                        courseBeanList.clear();
                    }
                    initDateHuiben(offset, limit, viewPage.getCurrentItem());
                }
            }
        });
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    isEdit = false;
                    tvRight.setVisibility(View.INVISIBLE);
                    llSelectDelete.setVisibility(View.GONE);
                } else {
                    tvRight.setVisibility(View.VISIBLE);
                    tvRight.setText("编辑");
                    isEdit = false;
                    isDeleteAll = false;
                    viewList.get(0).getAdapter().notifyDataSetChanged();
                }
                if (map.get(position)) { //加载过
                    if (position == 0) {
                        if (huibenList.size() == 0) {
                            tvNoData.setVisibility(View.VISIBLE);
                        } else {
                            tvNoData.setVisibility(View.GONE);
                        }
                    } else {
                        if (courseBeanList.size() == 0) {
                            tvNoData.setVisibility(View.VISIBLE);
                        } else {
                            tvNoData.setVisibility(View.GONE);
                        }
                    }
                } else {
                    initDateHuiben(offset, limit, position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        swipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        swipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
    }

    private void init() {
        tvTitle.setText("我的跟读");
        tvRight.setText("编辑");
        titleList = new ArrayList<>();
        titleList.add("绘本跟读");
        titleList.add("课程跟读");
        huibenList = new ArrayList<>();
        deleteMap = new HashMap<>();
        for (int i = 0; i < huibenList.size(); i++) {
            deleteMap.put(i, false);
        }
        courseBeanList = new ArrayList<>();
        map.put(0, false);
        map.put(1, false);
        viewList = new ArrayList<>();
    }

    /**
     * 数据
     */
    private void initDateHuiben(int offset, int limit, int pos) {
        if (!isLoading) {
            isLoading = true; //正在加载
            pBar = new Dialog(MyToReadActivity.this, R.style.Dialog);
            pBar.setContentView(R.layout.progress);
            if (!isFinishing()) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    pBar.show();
                }
            }
        }
        if (pos == 0) {//绘本
            String huiBenUrl = "/user/book/repeat/list";
            Map huibParam = new HashMap();
            huibParam.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
            huibParam.put("timestamp", System.currentTimeMillis());
            huibParam.put("offset", offset);
            huibParam.put("limit", limit);
            new MyAsyncTaskN(MyToReadActivity.this, 0, huiBenUrl, this).execute(huibParam);
        } else {
            tvNoData.setVisibility(View.GONE);
            String courseUrl = "/user/course/repeat/list";
            Map courseParam = new HashMap();
            courseParam.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
            courseParam.put("timestamp", System.currentTimeMillis());
            courseParam.put("offset", offset);
            courseParam.put("limit", limit);
            new MyAsyncTaskN(MyToReadActivity.this, 1, courseUrl, this).execute(courseParam);
        }
    }

    private void initViewPager() {
        for (int i = 0; i < titleList.size(); i++) {
            RecyclerView recyclerView = new RecyclerView(this);
            if (i == 0) {   //绘本跟读
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerView.setPadding(10, 0, 10, 0);
                recyclerView.setAdapter(new CommonAdapter<RepeatBean.DataBean>(this, R.layout.item_to_read_books, huibenList) {
                    @Override
                    protected void convert(ViewHolder holder, final RepeatBean.DataBean dataBean, final int position) {
                        RepeatBean.DataBean.UserBookBean.BookBean bookBean = dataBean.getUserBook().getBook();
                        holder.setVisible(R.id.iv_charge, false);
                        GlideUtils.GlideNormal(getApplicationContext(), Api.QN + bookBean.getIcon().get(0), (ImageView) holder.getView(R.id.iv_image), R.drawable.iv_replace_hb);
                        holder.setText(R.id.tv_title, bookBean.getName());
                        holder.setVisible(R.id.tv_left, bookBean.getTag().size() > 0);
                        holder.setText(R.id.tv_left, bookBean.getTag().size() > 0 ? bookBean.getTag().get(0) : "");
                        holder.setVisible(R.id.tv_cen, false);
                        if (bookBean.getFit() == 0) {
                            holder.setText(R.id.tv_right, "3-5岁");
                        } else if (bookBean.getFit() == 1) {
                            holder.setText(R.id.tv_right, "6-8岁");
                        } else if (bookBean.getFit() == 2) {
                            holder.setText(R.id.tv_right, "9-12岁");
                        } else if (bookBean.getFit() == 3) {
                            holder.setText(R.id.tv_right, "4-7岁");
                        } else if (bookBean.getFit() == 4) {
                            holder.setText(R.id.tv_right, "8-10岁");
                        }
                        final CheckBox cb = holder.getView(R.id.cb_select);
                        //点击编辑，item出现选择框
                        if (isEdit) {
                            cb.setVisibility(View.VISIBLE);
                            if (isDeleteAll) {
                                cb.setChecked(true);
                            } else {
                                cb.setChecked(false);
                            }
                            deleteMap.put(position, cb.isChecked());
                        } else {
                            cb.setVisibility(View.GONE);
                        }
                        LinearLayout llCb = holder.getView(R.id.ll_cb);

                        cb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                deleteMap.put(position, cb.isChecked());
                                int index = 0;
                                for (int i = 0; i < deleteMap.size(); i++) {
                                    if (deleteMap.get(i) == true) {
                                        index += 1;
                                    }
                                }
                                if (index == huibenList.size()) {
                                    isDeleteAll = true;
                                    cbSelectAll.setChecked(true);
                                } else {
                                    isDeleteAll = false;
                                    cbSelectAll.setChecked(false);
                                }
                            }
                        });
                        holder.setOnClickListener(R.id.ll, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
                                intent.putExtra("id", dataBean.getId());
                                intent.putExtra("uid", dataBean.getUserBook().getBook().getId());
                                intent.putExtra("flag", "MyHbRead");
                                intent.putExtra("name", dataBean.getUserBook().getBook().getName());
                                intent.putExtra("encourageId", dataBean.getId());
                                startActivity(intent);
                                //跳转到其他界面时候要清除当前选择
                                resetState();
                            }
                        });
                    }
                });
            } else {  //课程跟读  正在开课
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new CommonAdapter<CourseRepeat.DataBean>(this, R.layout.item_lesson, courseBeanList) {
                    @Override
                    protected void convert(ViewHolder holder, final CourseRepeat.DataBean dataBean, int position) {
                        final CourseRepeat.DataBean.UserCourseBean.CourseBean courseBean = dataBean.getUserCourse().getCourse();
                        GlideUtils.GlideNormal(getApplicationContext(), Api.QN + courseBean.getIcon().get(0), (ImageView) holder.getView(R.id.iv_today_left), R.drawable.iv_replace_les);
                        holder.setText(R.id.tv_lessonName, courseBean.getName());
                        if (courseBean.getFit() == 0) {
                            holder.setText(R.id.tv_fit, "3-5岁");
                        } else if (courseBean.getFit() == 1) {
                            holder.setText(R.id.tv_fit, "6-8岁");
                        } else if (courseBean.getFit() == 2) {
                            holder.setText(R.id.tv_fit, "9-12岁");
                        } else if (courseBean.getFit() == 3) {
                            holder.setText(R.id.tv_fit, "4-7岁");
                        } else if (courseBean.getFit() == 4) {
                            holder.setText(R.id.tv_fit, "8-10岁");
                        }
                        if (courseBean.getStatus() == 0) {  //，0：未开课(报名中)，1：已开课，2：已结束
                            holder.setText(R.id.tv_lessonFlag, "未开课");
                        } else if (courseBean.getStatus() == 1) {
                            holder.setText(R.id.tv_lessonFlag, "已开课");
                        } else {
                            holder.setText(R.id.tv_lessonFlag, "已结束");
                        }
                        holder.setText(R.id.tv_cycle, courseBean.getCycle() + "天");
                        LinearLayout ll = holder.getView(R.id.ll_tag);  //标签
                        ll.removeAllViews();
                        if (courseBean.getTag().size() > 0) {
                            for (int i = 0; i < 1; i++) {
                                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_lesson_tag, null);
                                TextView textView = view.findViewById(R.id.tv_tag);
                                textView.setText(courseBean.getTag().get(i));
                                ll.addView(view);
                            }
                        }
                        holder.setOnClickListener(R.id.cardview, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), MyToReadLessHbActivity.class);
                                intent.putExtra("id", dataBean.getUserCourse().getId());
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                                if (viewPage.getCurrentItem() == 0) {
                                    initDateHuiben(huibenList.size(), limit, 0);
                                } else {
                                    initDateHuiben(courseBeanList.size(), limit, 1);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
            viewList.add(recyclerView);
        }
        adapterVP = new AdapterVP(titleList, viewList);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                TablayoutLine.setIndicator(tabLayout, 30, 30);
            }
        });
        tabLayout.setupWithViewPager(viewPage);
        viewPage.setAdapter(adapterVP);
    }

    /**
     * TODO 跳转到其他界面时候要清除当前选择
     */
    public void resetState() {
        isEdit = false;
        isDeleteAll = false;
        cbSelectAll.setChecked(false);
        tvRight.setText("编辑");
        deleteMap.clear();
        llSelectDelete.setVisibility(View.GONE);
        viewList.get(0).getAdapter().notifyDataSetChanged();
    }

    @OnClick({R.id.iv_back, R.id.tv_right,R.id.cb_selectAll,   R.id.ll_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                isEdit = !isEdit;
                //编辑
                if (isEdit) {
                    llSelectDelete.setVisibility(View.VISIBLE);
                    viewList.get(0).getAdapter().notifyDataSetChanged();
                    tvRight.setText("完成");
                } else {
                    llSelectDelete.setVisibility(View.GONE);
                    viewList.get(0).getAdapter().notifyDataSetChanged();
                    tvRight.setText("编辑");
                }
                break;
            case R.id.cb_selectAll:
//                //全选
                isDeleteAll = cbSelectAll.isChecked();
                viewList.get(0).getAdapter().notifyDataSetChanged();
                break;

            case R.id.ll_delete:
                //删除
                deleteList = new ArrayList<>();
                if (deleteMap.size() > 0) {
                    for (int i = 0; i < deleteMap.size(); i++) {
                        if (deleteMap.get(i) == true) {
                            deleteList.add(huibenList.get(i));
                        }
                    }
                }
                if (deleteList.size() > 0) {
                    //TODO  删除数据
                    deleteHb();
                }
                break;
        }
    }

    /**
     * 删除绘本
     */
    public void deleteHb() {
        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < deleteList.size(); i++) {
            idList.add(deleteList.get(0).getId());
        }
        String url = "/user/book/repeat/delete";
        Map param = new HashMap();
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("timestamp", System.currentTimeMillis());
        param.put("id", idList);
        new MyAsyncTaskN(MyToReadActivity.this, 2, url, this).execute(param);
    }

    @Override
    public void onSuccess(int type, String str) {
        dis();
        switch (type) {
            case 0:
                map.put(viewPage.getCurrentItem(), true); //添加刷新标记
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);   //停止刷新
                }
                repeatBean = new Gson().fromJson(str, RepeatBean.class);
                if (repeatBean.getData().size() > 0) {
                    huibenList.addAll(repeatBean.getData());
                } else {
                    if (huibenList.size() > 0) {
                    } else {
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                }
                viewList.get(0).getAdapter().notifyDataSetChanged();
                break;
            case 1:
                map.put(viewPage.getCurrentItem(), true); //添加刷新标记
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);   //停止刷新
                }
                courseRepeat = new Gson().fromJson(str, CourseRepeat.class);
                if (courseRepeat.getData().size() > 0) {
                    courseBeanList.addAll(courseRepeat.getData());
                } else {
                    if (courseBeanList.size() > 0) {
                    } else {
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                }
                viewList.get(1).getAdapter().notifyDataSetChanged();
                break;
            case 2:
                ToastUtil.showShort(getApplicationContext(), "删除成功");
                huibenList.removeAll(deleteList);
                viewList.get(0).getAdapter().notifyDataSetChanged();
                deleteMap.clear();
                cbSelectAll.setChecked(false);
                break;
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
        dis();
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
}
