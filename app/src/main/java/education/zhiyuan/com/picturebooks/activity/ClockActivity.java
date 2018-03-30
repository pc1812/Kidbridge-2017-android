package education.zhiyuan.com.picturebooks.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.adpter.AdapterVP;
import education.zhiyuan.com.picturebooks.utils.TablayoutLine;

/**
 * 我是老师item点击跳入 打卡情况 ，回复
 */
public class ClockActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    @BindView(R.id.et_replay)
    EditText etReplay;
    @BindView(R.id.ll_replay)
    LinearLayout llReplay;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<RecyclerView> viewList;
    private List<String> clockList;
    private List<String> replyList;

    private List<String> titleList;
    private AdapterVP adapterVP;
    private List<List<String>> datalist;
    private int pos;
    private boolean isShowShoftInput = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout_vp);
        ButterKnife.bind(this);
        tvRight.setVisibility(View.INVISIBLE);
        tvTitle.setText("我的课程");
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        initData();
        initViewPager();
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                llReplay.setVisibility(View.GONE);
                closeSoftInput();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initData() {
        titleList = new ArrayList<>();
        titleList.add("打卡情况");
        titleList.add("回复");
        datalist = new ArrayList<>();
        clockList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            clockList.add("");
        }
        replyList = new ArrayList<>();
        replyList.addAll(clockList);
        datalist.add(clockList);
        datalist.add(replyList);
    }

    private void initViewPager() {
        viewList = new ArrayList<>();
        for (int i = 0; i < datalist.size(); i++) {
            if (i == 0) {   //打卡
                final RecyclerView recyclerView = new RecyclerView(this);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new CommonAdapter<String>(this, R.layout.item_clock, datalist.get(i)) {
                    @Override
                    protected void convert(ViewHolder holder, String s, int position) {
                        pos = position;
                        if (position == 3) {  //未打卡
                            holder.setVisible(R.id.tv_notClock, true);
                            holder.setVisible(R.id.ll_read, false);
                        }

                        holder.setOnClickListener(R.id.iv_read, new View.OnClickListener() {  //播放
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        holder.setOnClickListener(R.id.iv_reply, new View.OnClickListener() {  //提交后的评价，会显示在个人中心-我的评价
                            @Override
                            public void onClick(View view) {
                                llReplay.setVisibility(View.VISIBLE);
                                showSoftInput();
                            }
                        });
                        holder.setOnClickListener(R.id.re_clock, new View.OnClickListener() {  //TODO 点击item关闭软键盘，，回退键监听不到？
                            @Override
                            public void onClick(View view) {
                                llReplay.setVisibility(View.GONE);
                                closeSoftInput();
                            }
                        });

                    }
                });

                viewList.add(recyclerView);
            } else {  //回复  老师的评价+学生的回复
                RecyclerView recyclerView = new RecyclerView(this);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new CommonAdapter<String>(this, R.layout.item_evaluate, datalist.get(i)) {
                    @Override
                    protected void convert(ViewHolder holder, String s, int position) {
                        LinearLayout ll = holder.getView(R.id.ll_msg);
                        if (position == 2) {
                            for (int i = 0; i < 3; i++) {
                                TextView tv = new TextView(getApplicationContext());
                                tv.setText("小艾老师，读的很用心加油");
                                tv.setTextColor(Color.BLACK);
                                tv.setTextSize(14);
                                ll.addView(tv);
                            }
                        } else {
                            TextView tv = new TextView(getApplicationContext());
                            tv.setText("小艾老师，读的很用心加油");
                            tv.setTextColor(Color.BLACK);
                            ll.addView(tv);
                        }

                    }
                });
                viewList.add(recyclerView);
            }
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
     * 显示输入框
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void closeSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etReplay.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick({R.id.iv_back, R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_send:
                closeSoftInput();
                llReplay.setVisibility(View.GONE);
                break;
        }
    }

}
