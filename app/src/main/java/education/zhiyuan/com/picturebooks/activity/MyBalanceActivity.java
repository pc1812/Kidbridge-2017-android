package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.bean.BalanceBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.MoneyUtils;
import education.zhiyuan.com.picturebooks.utils.PayUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.RewardDialog;

/**
 * 我的H币
 */
public class MyBalanceActivity extends BaseActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    private BalanceBean balanceBean;
    private String balance = "";
    private PayUtils payUtils;
    private double fee;  //充值金额
    private Dialog pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_my_balance);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            balance = intent.getStringExtra("balance");
            if (TextUtils.isEmpty(balance)) { //如果之前没有获取到。再获取
                getData();
            } else {
                ToastUtil.showShort(getApplicationContext(), "" + balance);
                tvMoney.setText("￥" + balance);
            }
        }
        payUtils = new PayUtils(getApplicationContext(), MyBalanceActivity.this);
    }

    @Override
    protected void initView() {
        TextViewUtils.setTextType(MyBalanceActivity.this, tvMoney, "fonnts/PingFangBold.ttf");
        tvTitle.setText("我的\tH币");
        tvRight.setVisibility(View.INVISIBLE);
        tvDetail.setText(Html.fromHtml("<u>" + "H币明细" + "</u>"));  //添加下划线
        pBar = new Dialog(MyBalanceActivity.this, R.style.Dialog);
        pBar.setContentView(R.layout.progress);
    }

    /**
     * 获取余额
     */
    private void getData() {
        String url = "/user/balance";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        new MyAsyncTaskN(MyBalanceActivity.this, 0, url, this).execute(param);
    }

    /***
     * 输入金额框
     * */
    private void payDialog() {
        new RewardDialog(this, R.style.Dialog, new RewardDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, EditText et) {
                if (confirm) {
                    String text = et.getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        ToastUtil.showLayoutShort(getApplicationContext(), "不可为空");
                    } else {
                        if (new MoneyUtils().isZs(text)) {
                            //输入规范
                            fee = Double.valueOf(text);
                            dialog.dismiss();
                            showDialog();
                        } else {
                            ToastUtil.showLayoutShort(getApplicationContext(), "请输入整数金额");
                            et.setText("");
                        }
                    }
                }
            }
        })
                .setTitle("请输入充值\tH币数")
                .setEText("请输入整数金额")
                .show();
    }

    /**
     * 支付对话框
     */
    private void showDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_payinfo, null);
        //取消
        dialogView.findViewById(R.id.pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.re_apliay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUtils.getPayInfo(fee, 0);
                bottomSheetDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.re_w_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUtils.getPayInfo(fee, 1);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("pay")) {
            getData();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_detail, R.id.btn_top_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_detail:   //余额明细
                startActivity(new Intent(getApplicationContext(), WaterDetail.class).setFlags(0));
                break;
            case R.id.btn_top_up:
                payDialog();
                break;
        }
    }


    @Override
    public void onSuccess(int type, String str) {
        balanceBean = new Gson().fromJson(str, BalanceBean.class);
        tvMoney.setText(String.format("%s%.0f","￥",balanceBean.getData().getBalance()));
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
