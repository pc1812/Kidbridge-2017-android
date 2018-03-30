package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.bean.LessonDetailBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CarouselBanner;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CusDialog;
import education.zhiyuan.com.picturebooks.view.commodity.custom.StateDialog;

/**
 * 课程详情   未开课，未购买
 */
public class LessonDetailActivity extends BaseActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.lanceBanner)
    CarouselBanner lanceBanner;
    @BindView(R.id.tv_boom)
    TextView tvBoom;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.rootLayout)
    LinearLayout rootLayout;

    private int id, ID; //课程id
    private boolean refresh = false;
    private List<String> imagePath; //轮播图片
    private LessonDetailBean lessonDetailBean;
    private LessonDetailBean.DataBean.CourseBean courseBean;
    private int tag; //0:免费可解锁 1:付费可解锁  ，去打卡

    private String detailUrl = Api.HOST + "/course/richtext/";
    private Dialog pBar;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dis();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWebView();
        getData();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_lesson_detail);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("id", -1);
        }
        imagePath = new ArrayList<>();
    }

    @Override
    protected void initView() {
        tvRight.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webview.onResume();
    }

    public void initWebView() {
        handler.sendEmptyMessageDelayed(100, 2000);
        pBar = new Dialog(LessonDetailActivity.this, R.style.Dialog);
        pBar.setContentView(R.layout.progress);
        if (!isFinishing()) {
            pBar.show();
        }
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webview.loadUrl(detailUrl + id);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dis();
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    dis();
                }
            }
        });
    }

    private void getData() {
        String url = "/course/get";
        Map param = new HashMap();
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("timestamp", System.currentTimeMillis());
        param.put("id", id);
        new MyAsyncTaskN(LessonDetailActivity.this, 0, url, this).execute(param);
    }

    @OnClick({R.id.iv_back, R.id.tv_boom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_boom:
//                if (tag == 0) {
//                    toUnlock();
//                }
                if (tag == 1) {
                    initDialog("是否支付" + getInt(courseBean.getPrice()) + "\tH币购买课程", "取消", "购买", 1);
                }
                if (tag == 2) {
                    Intent intent = new Intent(getApplicationContext(), StartLessonDetailActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("ID", ID);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 解锁
     */
    private boolean isLocking = false;
    private StateDialog stateDialog;

    private void toUnlock() {
        if (isLocking) {
            ToastUtil.showShort(getApplicationContext(), "购买课程中，请稍后！");
            return;
        }
        isLocking = true;
        if (stateDialog == null) {
            stateDialog = new StateDialog(LessonDetailActivity.this, R.style.ProgressDialog_State);
            stateDialog.setContent("购买课程中，请稍后");
            stateDialog.show();
        }
        String url = "/course/unlock";
        final Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("id", id);
        new MyAsyncTaskN(LessonDetailActivity.this, 1, url, this).execute(param);
    }

    private void initView2() {
        if (refresh) {
            ID = lessonDetailBean.getData().getBelong();
//            if (tag == 0) { //免费解锁
//                Intent intent = new Intent(getApplicationContext(), StartLessonDetailActivity.class);
//                intent.putExtra("id", id);
//                intent.putExtra("ID", ID);
//                startActivity(intent);
//            }
            if (tag == 1) { //花钱解锁成功
                ToastUtil.showShort(getApplicationContext(), getInt(courseBean.getPrice()) + "\tH币购买课程成功");
                tvBoom.setVisibility(View.VISIBLE);
//                if (courseBean.getStatus() == 0) {
                tvBoom.setBackgroundColor(Color.GRAY);
                tvBoom.setClickable(false);
                tvBoom.setText("未开课");
//                tag = 2;
//                }
//                else if (lessonDetailBean.getData().getCourse().getStatus() == 1) {
//                    tvBoom.setBackgroundColor(Color.parseColor("#15CF30"));
//                    tvBoom.setClickable(true);
//                    tvBoom.setText("去打卡");
//                }
            }

        } else {
            //belong -1=未解锁，>-1=已解锁， >-1的值就是用户的绘本或课程的ID值
            // status 枚举类型，0：未开课(报名中)，1：已开课，2：已结束，条件判断：若状态为1、2则不可以进行解锁
            if (lessonDetailBean.getData().getBelong() == -1) {  //TODO 未解锁
                if (courseBean.getStatus() == 0) {      //未开课，可以解锁
                    if (courseBean.getLimit() >= courseBean.getEnter()) {     //报名人数未满，可以解锁
                        tvBoom.setVisibility(View.VISIBLE);
                        tvBoom.setClickable(true);
                        tvBoom.setBackgroundColor(Color.parseColor("#15CF30"));
                        tvBoom.setText(getInt(courseBean.getPrice()) + "\tH币解锁");
                        tag = 1;  //付费解锁
//                        else {
//                            tvBoom.setVisibility(View.VISIBLE);
//                            tvBoom.setClickable(false);
//                            tvBoom.setBackgroundColor(Color.GRAY);
//                            tvBoom.setText("未开课");
//                            tag = 0;  //免费解锁
//                        }
                    } else {
                        tvBoom.setVisibility(View.VISIBLE);
                        tvBoom.setClickable(false);
                        tvBoom.setBackgroundColor(Color.GRAY);
                        tvBoom.setText("已截止报名");
                    }
                }

                if (courseBean.getStatus() == 1) {
                    tvBoom.setVisibility(View.VISIBLE);
                    tvBoom.setClickable(false);
                    tvBoom.setText("正在开课");
                    tvBoom.setBackgroundColor(Color.GRAY);
                }
                if (courseBean.getStatus() == 2) {
                    tvBoom.setVisibility(View.VISIBLE);
                    tvBoom.setClickable(false);
                    tvBoom.setText("已结束");
                    tvBoom.setBackgroundColor(Color.GRAY);
                }
            } else if (lessonDetailBean.getData().getBelong() > -1) { //TODO 已解锁
                if (courseBean.getStatus() == 0) {         //未开课
                    tvBoom.setVisibility(View.VISIBLE);
                    tvBoom.setClickable(false);
                    tvBoom.setText("未开课");
                    tvBoom.setBackgroundColor(Color.GRAY);
                } else if (courseBean.getStatus() == 2) {  //已结束
                    tvBoom.setVisibility(View.VISIBLE);
                    tvBoom.setClickable(false);
                    tvBoom.setText("已结束");
                    tvBoom.setBackgroundColor(Color.GRAY);
                } else {                                   //去打卡
                    ID = lessonDetailBean.getData().getBelong();
                    tvBoom.setVisibility(View.VISIBLE);
                    tvBoom.setClickable(true);
                    tvBoom.setBackgroundColor(Color.parseColor("#15CF30"));
                    tvBoom.setText("去打卡");
                    tag = 2;
                }
            }
            tvTitle.setText(courseBean.getName());
            for (int i = 0; i < courseBean.getIcon().size(); i++) {
                imagePath.add(Api.QN + courseBean.getIcon().get(i));
            }
            //绑定轮播数据
            lanceBanner.setImagesUrl(imagePath);
        }
    }

    /**
     * 提示框
     */
    public void initDialog(String content, String positiveStr, String negativeStr, final int flag) {

        //弹出提示框
        new CusDialog(this, R.style.Dialog, content, new CusDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                //继续
                if (flag == 1) {  //解锁
                    if (!confirm) {
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

    @Override
    public void onSuccess(int ty, String str) {
        switch (ty) {
            case 0:
                lessonDetailBean = new Gson().fromJson(str, LessonDetailBean.class);
                courseBean = lessonDetailBean.getData().getCourse();
                initView2();
                break;
            case 1:
                isLocking = false;
                dis();
                Map toUnlockMap = new Gson().fromJson(str, Map.class);
                if (toUnlockMap.get("event").equals("INSUFFICIENT_BALANCE")) {
                    initDialog("H币不足，是否充值？", "取消", "充值", 2);
                } else {
                    refresh = true;
                    //重新获取数据，刷新
                    lanceBanner.stopAutoPlay();
                    imagePath.clear();
                    getData();
                    EventBus.getDefault().post("unlock_course");
                }
                break;
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showShort(getApplicationContext(), msg);
    }


    @Override
    protected void onPause() {
        super.onPause();
        webview.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dis();
        rootLayout.removeView(webview);
        webview.destroy();
        if (imagePath.size() > 0) {
            lanceBanner.stopAutoPlay();
        }
    }

    public void dis() {
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
        if (stateDialog != null) {
            if (stateDialog.isShowing()) {
                stateDialog.dismiss();
            }
        }
    }

    public int getInt(double number) {
        BigDecimal bd = new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }
}
