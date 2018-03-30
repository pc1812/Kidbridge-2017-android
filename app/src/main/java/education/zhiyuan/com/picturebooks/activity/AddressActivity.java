package education.zhiyuan.com.picturebooks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import education.zhiyuan.com.picturebooks.bean.AddressBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * 地址
 * */
public class AddressActivity extends BaseActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    private String contact, phone, region, street;
    private AddressBean addressBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_address2);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            contact = intent.getStringExtra("contact");
            phone = intent.getStringExtra("phone");
            region = intent.getStringExtra("region");
            street = intent.getStringExtra("street");
        }
    }

    @Override
    protected void initView() {
        tvTitle.setText("地址");
        tvName.setText(contact);
        String ads = region.replace("-", "");
        tvAddress.setText(ads + street);
        tvPhone.setText(phone);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("update_address")) {
            getData();
        }
    }

    private void getData() {
        String url = "/user/receiving";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        new MyAsyncTaskN(AddressActivity.this, 0, url, this).execute(param);
    }

    @Override
    public void onSuccess(int type, String str) {
        addressBean = new Gson().fromJson(str, AddressBean.class);
        contact = addressBean.getData().getContact();
        phone = addressBean.getData().getPhone();
        street = addressBean.getData().getStreet();
        region = addressBean.getData().getRegion();
        tvName.setText(contact);
        String ads = region.replace("-", "");
        tvAddress.setText(ads +street);
        tvPhone.setText(phone);
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showShort(getApplicationContext(),msg);
    }

    @OnClick({R.id.iv_back, R.id.ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll:
                Intent intent = new Intent(this, EditAddressActivity.class);
                intent.putExtra("flag", 1);
                intent.putExtra("contact", contact);
                intent.putExtra("phone", phone);
                intent.putExtra("region", region);
                intent.putExtra("street", street);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
