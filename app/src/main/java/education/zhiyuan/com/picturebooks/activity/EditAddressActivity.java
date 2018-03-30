package education.zhiyuan.com.picturebooks.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.selectaddress.SelectAddressWindow;

/**
 * 添加/修改地址
 */
public class EditAddressActivity extends BaseActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_detail)
    EditText etDetail;
    @BindView(R.id.layout_main)
    LinearLayout layoutMain;

    private int flag = -1; //0：添加，1：修改
    private SelectAddressWindow selectAddressWindow;
    private String contact, phone, region, street;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_address);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            flag = intent.getIntExtra("flag", 0);
        }
        if (flag == 1) {
            contact = intent.getStringExtra("contact");
            phone = intent.getStringExtra("phone");
            region = intent.getStringExtra("region");
            street = intent.getStringExtra("street");
            etName.setText(contact);
            etPhone.setText(phone);
            tvAddress.setText(region);
            etDetail.setText(street);
        }
    }

    @Override
    protected void initView() {
        tvTitle.setText(flag == 0 ? "填写地址" : "填写地址");
        tvRight.setText("保存");
    }

    @OnClick({R.id.iv_back, R.id.tv_right, R.id.tv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right: //保存
                if (TextUtils.isEmpty(TextViewUtils.replaceBlankHa(etName.getText().toString()))) {
                    ToastUtil.showShort(getApplicationContext(), "收件人不能为空");
                    return;
                }
                if (TextUtils.isEmpty(TextViewUtils.replaceBlankHa(etPhone.getText().toString()))) {
                    ToastUtil.showShort(getApplicationContext(), "联系方式不能为空");
                    return;
                }
                if (TextUtils.isEmpty(TextViewUtils.replaceBlankHa(tvAddress.getText().toString()))) {
                    ToastUtil.showShort(getApplicationContext(), "区域地址不能为空");
                    return;
                }
                if (TextUtils.isEmpty(TextViewUtils.replaceBlankHa(etDetail.getText().toString()))) {
                    ToastUtil.showShort(getApplicationContext(), "详细地址不能为空");
                    return;
                }
                updateAddress();
                break;
            case R.id.tv_address:
                showSelectCity();
                break;
        }
    }

    public void updateAddress() {
        String url = "/user/update/receiving";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("contact", etName.getText().toString());
        param.put("phone", etPhone.getText().toString());
        param.put("region", tvAddress.getText().toString());
        param.put("street", etDetail.getText().toString());
        new MyAsyncTaskN(EditAddressActivity.this, 0, url, this).execute(param);
    }

    public void hide() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
        }
    }

    public void showSelectCity() {
        hide();
        selectAddressWindow = new SelectAddressWindow(EditAddressActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_cancel:
                        selectAddressWindow.dismiss();
                        break;
                    case R.id.tv_ok:
                        tvAddress.setText(selectAddressWindow.getmCurrentProviceName() + "-"
                                + selectAddressWindow.getmCurrentCityName() + "-"
                                + selectAddressWindow.getmCurrentDistrictName());
                        selectAddressWindow.dismiss();
                        break;
                }
            }
        });
        selectAddressWindow.showAtLocation(layoutMain, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onSuccess(int type, String str) {
        if (flag == 0) {
            ToastUtil.showShort(getApplicationContext(), "添加成功");
            EventBus.getDefault().post("address_success");
        } else {
            ToastUtil.showShort(getApplicationContext(), "修改成功");
            EventBus.getDefault().post("update_address");
        }
        finish();
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showShort(getApplicationContext(), msg);
    }

}
