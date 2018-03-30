package education.zhiyuan.com.picturebooks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * 忘记密码
 * */
public class ForgetPwdActivity extends BaseActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.et_newPwd)
    EditText etNewPwd;

    private boolean sendMessage = true;// 获取短信验证防止多次获取
    private int recLen = 120; // 倒计时
    private String phone, code, newPwd;
    private String mobilearea = "+86";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_pwd_forget);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvRight.setVisibility(View.GONE);
        tvTitle.setText("手机验证");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    mobilearea = "+86";
                } else {
                    mobilearea = "+1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.btn_code, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_code:  //获取验证码
                phone = etPhone.getText().toString();
                if (sendMessage) {
                    sendMessage = false;
                    if (TextUtils.isEmpty(TextViewUtils.replaceBlankHa(phone))) {
                        ToastUtil.showShort(this, "手机号不能为空");
                        sendMessage = true;
                    } else {
                        //发送验证码
                        String url = "/user/update/password/verification";
                        Map param = new HashMap();
                        param.put("timestamp", System.currentTimeMillis());
                        param.put("phone", mobilearea + phone);
                        new MyAsyncTaskN(ForgetPwdActivity.this, 0, url, this).execute(param);
                    }
                }
                break;
            case R.id.btn_next:  //完成
                phone = etPhone.getText().toString();
                code = etCode.getText().toString();
                newPwd = etNewPwd.getText().toString();
                if (TextUtils.isEmpty(TextViewUtils.replaceBlankHa(phone))) {
                    ToastUtil.showShort(this, "手机号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(TextViewUtils.replaceBlankHa(code))) {
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(newPwd)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //修改
                String url = "/user/update/password";
                Map param = new HashMap();
                param.put("timestamp", System.currentTimeMillis());
                param.put("phone", mobilearea + phone);
                param.put("code", code);
                param.put("password", newPwd);
                new MyAsyncTaskN(ForgetPwdActivity.this, 1, url, this).execute(param);
                break;
        }
    }

    /**
     * 送验证码按钮的倒计时设置
     */
    private void sendtime() {
        if (recLen > 0) {
            final Timer timer = new Timer();
            TimerTask task = null;
            task = new TimerTask() {
                @Override
                public void run() {
                    btnCode.setClickable(false);
                    runOnUiThread(new Runnable() { // UI thread
                        @Override
                        public void run() {
                            if (recLen < 1) {
                                btnCode.setText("再次获取 ");
                                timer.cancel();
                                recLen = 60;
                                btnCode.setClickable(true);
                                sendMessage = true;
                            } else {
                                recLen--;
                                btnCode.setText("重新发送（" + recLen + "）");
                            }
                        }
                    });
                }
            };
            timer.schedule(task, 1000, 1000);
        }
    }

    @Override
    public void onSuccess(int type, String str) {
        switch (type) {
            case 0:
                sendtime();
                ToastUtil.showShort(getApplicationContext(), "验证码已发送，请注意查收");
                break;
            case 1:
                ToastUtil.showShort(getApplicationContext(), "修改成功");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showShort(getApplicationContext(), msg);
        sendMessage = true;
    }

}
