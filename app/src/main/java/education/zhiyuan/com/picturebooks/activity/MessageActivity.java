package education.zhiyuan.com.picturebooks.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.adpter.AdapterVP;
import education.zhiyuan.com.picturebooks.bean.JGCommentBean;
import education.zhiyuan.com.picturebooks.bean.JGPushBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TablayoutLine;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.TimeTools;

/**
 * 消息通知
 */
public class MessageActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    @BindView(R.id.view_gray)
    View viewGray;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<RecyclerView> viewList;
    private List<String> titleList;
    private AdapterVP adapterVP;
    private List<JGPushBean> pushBeanList; //推送消息
    private List<JGCommentBean> commentBeanList; //评论消息
    private boolean isLoad = false;
    private int comNew = -2, pushNew = -2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout_vp);
        ButterKnife.bind(this);
        comNew = SharedPreferencesUtil.getInfoStateC(this, SharedPreferencesUtil.getIdInfo(this), 0);
        pushNew = SharedPreferencesUtil.getInfoStateC(this, SharedPreferencesUtil.getIdInfo(this), 1);
        LitePal.initialize(this);
        tvRight.setVisibility(View.GONE);
        tvTitle.setText("消息通知");
        viewGray.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        initData();
        initViewPager();
        getData(0);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoad) {
                    if (viewPage.getCurrentItem() == 0) {
                        comNew = -1;
                        SharedPreferencesUtil.putInfoStateC(getApplicationContext(), SharedPreferencesUtil.getIdInfo(getApplicationContext()), 0, -1);
                        commentBeanList.clear();
                    } else {
                        pushNew = -1;
                        SharedPreferencesUtil.putInfoStateC(getApplicationContext(), SharedPreferencesUtil.getIdInfo(getApplicationContext()), 1, -1);
                        pushBeanList.clear();
                    }
                    getData(viewPage.getCurrentItem());
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
                if (position == 1) { //推送消息
                    comNew = -1;
                    viewList.get(0).getAdapter().notifyDataSetChanged();
                    SharedPreferencesUtil.putInfoStateC(getApplicationContext(), SharedPreferencesUtil.getIdInfo(getApplicationContext()), 0, -1);
                    if (pushBeanList.size() == 0) {
                        //获取从数据库
                        getData(1);
                    }
                } else {
                    pushNew = -1;
                    viewList.get(1).getAdapter().notifyDataSetChanged();
                    SharedPreferencesUtil.putInfoStateC(getApplicationContext(), SharedPreferencesUtil.getIdInfo(getApplicationContext()), 1, -1);
                    if (commentBeanList.size() == 0) {
                        getData(0);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 从数据库获取数据
     */
    public void getData(int type) {
        isLoad = true;
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (type == 0) {
            List<JGCommentBean> comList = DataSupport.where("tag = ?", SharedPreferencesUtil.getIdInfo(this)).order("createTime desc").find(JGCommentBean.class);
            commentBeanList.addAll(comList);
            if (viewList.get(0).getAdapter() != null) {
                viewList.get(0).getAdapter().notifyDataSetChanged();
                isLoad = false;
            }
        } else {
            List<JGPushBean> list = DataSupport.where("tag = ?", SharedPreferencesUtil.getIdInfo(this)).order("createTime desc").find(JGPushBean.class);
            pushBeanList.addAll(list);
            if (viewList.get(1).getAdapter() != null) {
                viewList.get(1).getAdapter().notifyDataSetChanged();
                isLoad = false;
            }
        }
    }

    private void initData() {
        titleList = new ArrayList<>();
        titleList.add("评论消息");
        titleList.add("推送消息");
        commentBeanList = new ArrayList<>();
        pushBeanList = new ArrayList<>();
    }

    private void initViewPager() {
        viewPage.setBackgroundColor(getResources().getColor(R.color.graywhite));
        viewList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            if (i == 0) {
                recyclerView.setAdapter(new CommonAdapter<JGCommentBean>(this, R.layout.item_message_comment, commentBeanList) {

                    @Override
                    protected void convert(ViewHolder holder, JGCommentBean jgCommentBean, int position) {
                        holder.setVisible(R.id.view, position != commentBeanList.size() - 1);
                        holder.setVisible(R.id.view_top, position == 0);
                        if (position <= comNew) {
                            holder.setVisible(R.id.redPoint, true);
                        } else {
                            holder.setVisible(R.id.redPoint, false);
                        }
                        holder.setText(R.id.tv_name, TextUtils.isEmpty(jgCommentBean.getNickname()) ? "匿名用户" : jgCommentBean.getNickname());
                        holder.setText(R.id.tv_time, TimeTools.getStrTime(jgCommentBean.getCreateTime() + "", "yyyy-MM-dd\tHH:mm"));
                        holder.setText(R.id.tv_comment, TextViewUtils.replaceBlank(jgCommentBean.getText()));
                        GlideUtils.GlideCircle(getApplicationContext(), Api.QN + jgCommentBean.getHead(), (ImageView) holder.getView(R.id.iv_pic),  R.drawable.default_head);
                    }
                });
            } else {  //推送消息  动态添加msg条数
                recyclerView.setAdapter(new CommonAdapter<JGPushBean>(this, R.layout.item_message_push, pushBeanList) {
                    @Override
                    protected void convert(ViewHolder holder, JGPushBean pushBean, int position) {
                        holder.setVisible(R.id.view_top, position == 0);
                        RequestOptions options = new RequestOptions();
                        options.apply(RequestOptions.circleCropTransform());
                        Glide.with(getApplicationContext())
                                .load(R.mipmap.iv_login_logo)
                                .apply(options)
                                .into((ImageView) holder.getView(R.id.iv));
                        if (position <= pushNew) {
                            holder.setVisible(R.id.redPoint, true);
                        } else {
                            holder.setVisible(R.id.redPoint, false);
                        }
                        holder.setText(R.id.tv_time, TimeTools.getStrTime(pushBean.getCreateTime() + "", "yyyy-MM-dd\tHH:mm"));
                        holder.setText(R.id.tv_message, pushBean.getText());
                    }
                });
            }
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

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (comNew != -2 && comNew != -1) { //加载过，有数据
            SharedPreferencesUtil.putInfoStateC(getApplicationContext(), SharedPreferencesUtil.getIdInfo(this), 0, -1);
            SharedPreferencesUtil.putInfoState(getApplicationContext(), SharedPreferencesUtil.getIdInfo(this), false);
        }
        if (pushNew != -2 && pushNew != -1) { //加载过，有数据
            SharedPreferencesUtil.putInfoStateC(getApplicationContext(), SharedPreferencesUtil.getIdInfo(this), 0, -1);
            SharedPreferencesUtil.putInfoState(getApplicationContext(), SharedPreferencesUtil.getIdInfo(this), false);
        }
        if (comNew == -1 && pushNew == -1) {
            SharedPreferencesUtil.putInfoState(getApplicationContext(), SharedPreferencesUtil.getIdInfo(this), false);
        }
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}


