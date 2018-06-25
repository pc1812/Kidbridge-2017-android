package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.adpter.BridgeRankAdapter;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.bean.HuiBenDetialBean;
import education.zhiyuan.com.picturebooks.bean.RepeatRankBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.MScrollview;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CarouselBanner;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CusDialog;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CustomLoadMoreView;
import education.zhiyuan.com.picturebooks.view.commodity.custom.StateDialog;

/**
 * Created by Lance on 2017/6/20.
 * Email : COCOINUT@163.com
 * Introduce : 绘本详情     免费、收费
 */

public class BridgeDetail extends BaseActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.lanceBanner)
    CarouselBanner lanceBanner;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_story)
    TextView tvStory;
    @BindView(R.id.tv_felling)
    TextView tvFelling;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_keyword)
    TextView tvKeyword;
    @BindView(R.id.tv_Read)
    TextView tvRead;
    @BindView(R.id.tv_Appreciation)
    TextView tvAppreciation;
    @BindView(R.id.ll_free)
    LinearLayout llFree;

    @BindView(R.id.ll_ls)
    LinearLayout llLS;
    @BindView(R.id.tv_nlock)
    TextView tvNlock;


    @BindView(R.id.tv_ls)
    TextView tvLs;
    @BindView(R.id.myScroll)
    MScrollview myScroll;
    @BindView(R.id.re_bom)
    RelativeLayout reBom;

    private HuiBenDetialBean huiBenDetialBeen;
    private RepeatRankBean repeatRankBean; //跟读
    private List<RepeatRankBean.DataBean.RankBean> rankBeanList; //跟读榜
    private Map toUnlockMap; //解锁结果

    private List<String> bannerList;   //最上方图片轮播
    private Double price;   //绘本价格
    private boolean IsFree = false;//标识是否付费
    private int flag = -1;  //状态 ，未解锁+免费=0，未解锁+付费=1，解锁=2

    private boolean isAppreciation = false;
    private boolean refresh = false;
    private int offset = 0, limit = 10;
    private int id; //绘本id
    private int ID; //belong
    private Dialog pBar;   //网络加载
    private String name;
    private BridgeRankAdapter rankAdapter;
    private View emptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getDate();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.layout_bridgedetail);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("id", -1);
            name = intent.getStringExtra("name");
            tvTitle.setText(name);
        }
        bannerList = new ArrayList<>();
        rankBeanList = new ArrayList<>();
        emptyView = getLayoutInflater().inflate(R.layout.view_empty_rank, null);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rankAdapter = new BridgeRankAdapter(R.layout.item_to_read, rankBeanList);
        rankAdapter.setEmptyView(emptyView);
        rankAdapter.setLoadMoreView(new CustomLoadMoreView());
        rankAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getDataRank(rankBeanList.size(), limit);
            }
        }, mRecyclerView);
        rankAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
                intent.putExtra("flag", "BridgeDetail");
                intent.putExtra("id", rankBeanList.get(position).getId());
                intent.putExtra("uid", id);
                if (huiBenDetialBeen != null) {
                    intent.putExtra("name", huiBenDetialBeen.getData().getBook().getName());
                }
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(rankAdapter);
    }

    @Override
    protected void initView() {
        tvRight.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAppreciation = false;
    }

    /**
     * 获取数据
     */
    private void getDate() {
        pBar = new Dialog(this, R.style.Dialog);
        pBar.setContentView(R.layout.progress);
        if (!isFinishing()) {
            pBar.show();
        }
        getDataBanner();
        getDataRank(offset, limit);
    }

    /**
     * 获取banner数据
     */
    public void getDataBanner() {
        String url = "/book/get";
        final Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("id", id);
        new MyAsyncTaskN(BridgeDetail.this, 0, url, this).execute(param);
    }

    /**
     * 获取跟读榜
     */
    public void getDataRank(int offset, int limit) {
        String rankUrl = "/user/book/repeat/rank";
        Map rankParam = new HashMap();
        rankParam.put("timestamp", System.currentTimeMillis());
        rankParam.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        rankParam.put("id", id);
        rankParam.put("offset", offset);
        rankParam.put("limit", limit);
        new MyAsyncTaskN(BridgeDetail.this, 1, rankUrl, this).execute(rankParam);
    }

    /**
     * 刷新跟读榜
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("huibenClock")) {
            rankBeanList.clear();
            getDataRank(offset, limit);
        }
    }

    /**
     * 避免重复点击
     */
    private boolean isLocking = false; //是否正在解锁
    private StateDialog stateDialog;

    @OnClick({R.id.iv_back, R.id.tv_Read, R.id.tv_Appreciation, R.id.tv_nlock})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_Read:  //只有是未解锁，免费状态下，需要解锁  （免费跟读，我要跟读）
                if (flag == 0) { //免费跟读
                    toUnlock();  //去解锁
                } else { //我要跟读
                    Intent intent = new Intent(getApplicationContext(), ToReadActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("ID", ID);
                    startActivity(intent);
                }
                break;
            case R.id.tv_Appreciation:  //赏析区
                if (flag == 0) {
                    isAppreciation = true;
                    toUnlock();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
                    intent.putExtra("id", id); //id
                    intent.putExtra("ID", ID);
                    intent.putExtra("encourageId", id);
                    intent.putExtra("flag", "HbAppreciation");
                    intent.putExtra("name", huiBenDetialBeen.getData().getBook().getName());
                    startActivity(intent);
                }
            case R.id.tv_nlock:
                //弹出解锁框
                if (!IsFree) {
                    initDialog("是否支付" + getInt(price) + "\tH币解锁", "取消", "解锁", 1);
                }
                break;
        }
    }

    /**
     * 解锁
     */
    private void toUnlock() {
        if (isLocking) {
            ToastUtil.showShort(getApplicationContext(), "解锁中，请稍后！");
            return;
        }
        isLocking = true;
        if (stateDialog == null) {
            stateDialog = new StateDialog(BridgeDetail.this, R.style.ProgressDialog_State);
            stateDialog.setContent("解锁中，请稍后...");
            stateDialog.show();
        }
        String url = "/book/unlock";
        final Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("id", id);
        new MyAsyncTaskN(BridgeDetail.this, 2, url, this).execute(param);
    }

    /**
     * 解锁、充值提示框
     */
    public void initDialog(String content, String positiveStr, String negativeStr, final int flag) {
        //弹出提示框
        new CusDialog(this, R.style.Dialog, content, new CusDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (flag == 1) { //解锁
                    if (!confirm) {
                        //去解锁
                        toUnlock();
                    }
                }
                if (flag == 2) { //充值
                    if (!confirm) {
                        startActivity(new Intent(getApplicationContext(), MyBalanceActivity.class));
                        finish();
                    }
                }
            }
        })
                .setPositiveButton(positiveStr)
                .setNegativeButton(negativeStr)
                .show();
    }

    /**
     * 数据 UI
     */
    public void initView(int type) {
        if(TextUtils.isEmpty(huiBenDetialBeen.getData().getBook().getLexile())){
            tvLs.setVisibility(View.GONE);
            llLS.setVisibility(View.GONE);
        }else {
            tvLs.setVisibility(View.VISIBLE);
            llLS.setVisibility(View.VISIBLE);
            tvLs.setText("\t\t\t\t"+huiBenDetialBeen.getData().getBook().getLexile());
        }
        switch (type) {
            case 0:
                if (refresh) { //解锁后，刷新的数据
                    flag = -1;
                    ID = huiBenDetialBeen.getData().getBelong();
                    if (isAppreciation) { //赏析区跳转
                        Intent intent = new Intent(getApplicationContext(), ToReadDetailActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("ID", ID);
                        intent.putExtra("copyright", huiBenDetialBeen.getData().getBook().getCopyright().getUser().getId());
                        intent.putExtra("flag", "HbAppreciation");
                        intent.putExtra("name", huiBenDetialBeen.getData().getBook().getName());
                        startActivity(intent);
                    } else {
                        if (price > 0) {
                            IsFree = true;
                            EventBus.getDefault().post(1); //TODO余额发生改变
                            Toast.makeText(getApplicationContext(), getInt(price) + "\tH币解锁成功", Toast.LENGTH_SHORT).show();
                            tvNlock.setVisibility(View.GONE);
                            tvNlock.setClickable(false);
                            llFree.setVisibility(View.VISIBLE);
                            tvRead.setVisibility(View.VISIBLE);
                            tvRead.setText("我要跟读");
                            tvAppreciation.setVisibility(View.VISIBLE);
                        } else {
                            //免费的，跳转到跟读界面
                            tvRead.setText("我要跟读");
                            Intent intent = new Intent(getApplicationContext(), ToReadActivity.class);
                            intent.putExtra("id", id);
                            intent.putExtra("ID", ID);
                            startActivity(intent);
                        }
                    }
                } else {
                    reBom.setVisibility(View.VISIBLE);
                    HuiBenDetialBean.DataBean.BookBean bean = huiBenDetialBeen.getData().getBook();
                    for (int i = 0; i < bean.getIcon().size(); i++) {  //轮播图
                        bannerList.add(Api.QN + bean.getIcon().get(i));
                    }
                    lanceBanner.setImagesUrl(bannerList);
                    tvLs.setText("\t\t\t\t" + bean.getLexile());
                    tvStory.setText("\t\t\t\t" + bean.getOutline()); //梗概
                    tvFelling.setText("\t\t\t\t" + bean.getFeeling()); //感悟
                    if (bean.getFit() == 0) {  //适龄
                        tvAge.setText("\t\t\t\t3-5岁");
                    } else if (bean.getFit() == 1) {
                        tvAge.setText("\t\t\t\t6-8岁");
                    } else if (bean.getFit() == 2) {
                        tvAge.setText("\t\t\t\t9-12岁");
                    } else if (bean.getFit() == 3) {
                        tvAge.setText("\t\t\t\t4-7岁");
                    } else if (bean.getFit() == 4) {
                        tvAge.setText("\t\t\t\t8-10岁");
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < bean.getTag().size(); i++) {  //关键词标签
                        if (i != bean.getTag().size() - 1) {
                            stringBuilder.append(bean.getTag().get(i) + "、");
                        } else {
                            stringBuilder.append(bean.getTag().get(i));
                        }
                    }
                    tvKeyword.setText("\t\t\t\t" + stringBuilder.toString());
                    price = bean.getPrice(); //价格
                    //  解锁状态  -1=未解锁，>-1=已解锁， >-1的值就是用户的绘本或课程的ID值
                    if (huiBenDetialBeen.getData().getBelong() == -1) { //未解锁
                        if (price == 0) {  // 通过免费跟读去解锁
                            IsFree = true;
                            llFree.setVisibility(View.VISIBLE);
                            tvRead.setVisibility(View.VISIBLE);
                            tvAppreciation.setVisibility(View.VISIBLE);
                            flag = 0;
                        } else {  // 花钱解锁
                            IsFree = false;
                            llFree.setVisibility(View.GONE);
                            tvRead.setVisibility(View.GONE);
                            tvAppreciation.setVisibility(View.GONE);
                            tvNlock.setVisibility(View.VISIBLE);
                            tvNlock.setText(getInt(price) + "\tH币解锁");
                        }
                    } else {   //已经解锁
                        ID = huiBenDetialBeen.getData().getBelong();
                        IsFree = true;
                        tvNlock.setVisibility(View.GONE);
                        tvNlock.setClickable(false);
                        llFree.setVisibility(View.VISIBLE);
                        tvRead.setVisibility(View.VISIBLE);
                        tvRead.setText("我要跟读");
                        tvAppreciation.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 1:
                if (repeatRankBean.getData().getRank().size() > 0) {
                    rankAdapter.addData(repeatRankBean.getData().getRank());
                    rankAdapter.loadMoreComplete();
                } else {
                    rankAdapter.loadMoreEnd();
                }
                break;
            case 2:
                if (toUnlockMap.get("event").equals("INSUFFICIENT_BALANCE")) {
                    IsFree = false;
                    initDialog("H币不足，是否充值？", "取消", "充值", 2);
                } else {
                    //解锁成功重新刷新数据，获取ID
                    refresh = true;
                    EventBus.getDefault().post("unlock_hb");
                    lanceBanner.stopAutoPlay();
                    bannerList.clear();
                    getDataBanner();
                }
                break;
        }
    }

    @Override
    public void onSuccess(int type, String str) {
        dis();
        switch (type) {
            case 0:
                huiBenDetialBeen = new Gson().fromJson(str, HuiBenDetialBean.class);
                break;
            case 1:
                repeatRankBean = new Gson().fromJson(str, RepeatRankBean.class);
                break;
            case 2:
                isLocking = false;
                IsFree = true;
                toUnlockMap = new Gson().fromJson(str, Map.class);
                break;
        }
        initView(type);
    }

    @Override
    public void onError(String msg) {
        dis();
        isLocking = false;
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (bannerList.size() > 0) {
            lanceBanner.stopAutoPlay();
        }
        dis();
    }

    public void dis() {
        if (stateDialog != null) {
            if (stateDialog.isShowing()) {
                stateDialog.dismiss();
            }
        }
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
    }

    public int getInt(double number) {
        BigDecimal bd = new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }
}
