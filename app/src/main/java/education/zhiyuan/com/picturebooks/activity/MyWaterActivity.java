package education.zhiyuan.com.picturebooks.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.BonusBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.MoneyUtils;
import education.zhiyuan.com.picturebooks.utils.PayUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.RewardDialog;

/**
 * Created by Lance on 2017/6/19.
 * Email : COCOINUT@163.com
 * Introduce : 我的水滴
 */

public class MyWaterActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.bonus_num)
    TextView bonusNum;
    @BindView(R.id.tv_detail)
    TextView tvDetail;

    private String money, waterNum;
    private BonusBean bonusBean;
    private PayUtils payUtils;
    private double fee;  //充值金额

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_water);
        ButterKnife.bind(this);
        TextViewUtils.setTextType(MyWaterActivity.this, bonusNum, "fonnts/PingFangBold.ttf");
        tvTitle.setText("我的水滴");
        tvRight.setVisibility(View.GONE);
        tvDetail.setText(Html.fromHtml("<u>" + "水滴明细" + "</u>"));  //添加下划线
        Intent intent = getIntent();
        if (intent != null) {
            waterNum = intent.getStringExtra("waterNum");
            if (waterNum == null || "".equals(waterNum)) {
                initData();
            } else {
                bonusNum.setText(waterNum);
            }
        } else {
            initData();
        }
        payUtils = new PayUtils(getApplicationContext(), MyWaterActivity.this);
    }

    private void initData() {
        String balanceUrl = "/user/bonus";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        new MyAsyncTaskN(MyWaterActivity.this, 0, balanceUrl, new HttpCallBackN() {
            @Override
            public void onSuccess(int type, String str) {
                bonusBean = new Gson().fromJson(str, BonusBean.class);
                bonusNum.setText(bonusBean.getData().getBonus() + "");
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showShort(getApplicationContext(), msg);
            }
        }).execute(param);
    }

    private void dhDialog() {
        new RewardDialog(this, R.style.Dialog, new RewardDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, EditText et) {
                if (confirm) {  //判断整数、为空
                    String text = et.getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        ToastUtil.showLayoutShort(getApplicationContext(),"不可为空");
                    } else {
                        Pattern p = Pattern.compile("[1-9][0-9]*");
                        Matcher m = p.matcher(text);
                        if (!new MoneyUtils().isZs(text)) {
                            ToastUtil.showLayoutShort(getApplicationContext(), "请输入整数");
                            et.setText("");
                        } else {
                            waterNum = text;
                            exchangeWater(Integer.valueOf(waterNum));
                            dialog.dismiss();
                        }
                    }
                }
            }
        })
                .setTitle("请输入要兑换的水滴数")
                .show();
    }

    /**
     * 兑换水滴至余额
     */
    public void exchangeWater(final int bonus) {
        String url = "/user/balance/ratio";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("bonus", bonus);
        new MyAsyncTaskN(MyWaterActivity.this, 0, url, new HttpCallBackN() {
            @Override
            public void onSuccess(int type, String str) {
                ToastUtil.showShort(getApplicationContext(), "兑换成功");
                bonusNum.setText(bonusBean.getData().getBonus() - bonus);
                EventBus.getDefault().post("pay");
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showShort(getApplicationContext(), msg);
            }
        }).execute(param);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.iv_back, R.id.btn_dh, R.id.tv_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_detail:
                startActivity(new Intent(getApplicationContext(), WaterDetail.class).setFlags(1));
                break;
            case R.id.btn_dh:
                dhDialog();
                break;
        }
    }
}
