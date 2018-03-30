package education.zhiyuan.com.picturebooks.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.MainActivity;
import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.LoginBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * Created by Lance on 2017/6/19.
 * Email : COCOINUT@163.com
 * Introduce : 注册页面
 */

public class RegisteredActivity extends AppCompatActivity implements HttpCallBackN {

    @BindView(R.id.edPhone)
    EditText edPhone;
    @BindView(R.id.et_p)
    EditText edPassword;
    @BindView(R.id.tv_has)
    TextView tvHas;
    @BindView(R.id.ed_code)
    EditText edCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.re_view)
    RelativeLayout reView;
    @BindView(R.id.iv)
    ImageView iv;

    private boolean sendMessage = true;// 获取短信验证防止多次获取
    private int recLen = 120; // 倒计时
    private String token = ""; //微信绑定手机号的token
    private String mobilearea = "+86";
    private String url;
    private static final String APP_ID = "wxb6f923a74661ce3b";
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registered);
        MyApp.getInstance().addActivity(this);
        ButterKnife.bind(this);
        //注册到微信
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        if (!TextUtils.isEmpty(token)) {
            btnRegister.setText("绑定");
        } else {
            btnRegister.setText("注册");
        }
        InitView();
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

    @Override
    protected void onResume() {
        super.onResume();
        pb.setVisibility(View.GONE);
        education.zhiyuan.com.picturebooks.utils.StatusBarUtil.StatusBarLightMode(this);  //设置小米魅族状态栏颜色
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();                //设置状态栏颜色
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
    }

    private void InitView() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        tvHas.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        RequestOptions options = new RequestOptions();
        options.apply(RequestOptions.circleCropTransform());
        Glide.with(getApplicationContext())
                .load(R.drawable.iv_login_logo)
                .apply(options)
                .into(iv);

    }

    @OnClick({R.id.tv_has, R.id.btn_register, R.id.WChatLogin, R.id.tv_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_has:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case R.id.btn_register:
                String phone = edPhone.getText().toString();
                String pwd = edPassword.getText().toString();
                String code = edCode.getText().toString();
                if (TextUtils.isEmpty(TextViewUtils.replaceBlankHa(phone))) {
                    ToastUtil.showShort(getApplicationContext(), "手机号码不能为空");
                    sendMessage = true;
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.showShort(getApplicationContext(), "密码不能为空");
                    sendMessage = true;
                    return;
                }
                if (pwd.length() < 8) {
                    ToastUtil.showShort(getApplicationContext(), "密码不能少于八位");
                    sendMessage = true;
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShort(getApplicationContext(), "验证码不能为空");
                    sendMessage = true;
                    return;
                }
                // TODO  提交注册
                url = "/user/register";
                Map param = new HashMap();
                param.put("timestamp", System.currentTimeMillis());
                param.put("phone", mobilearea + phone);
                param.put("password", pwd);
                param.put("code", code);
                if (!TextUtils.isEmpty(token)) {
                    param.put("bind", token);
                }
                new MyAsyncTaskN(RegisteredActivity.this, 1, url, this).execute(param);
                break;
            case R.id.WChatLogin:
                weixinLogin();
                break;
            case R.id.tv_code: //获取验证码
                if (sendMessage) {
                    sendMessage = false;
                    if (TextUtils.isEmpty(TextViewUtils.replaceBlankHa(edPhone.getText().toString()))) {
                        ToastUtil.showShort(this, "手机号不能为空");
                        sendMessage = true;
                    } else {
                        //获取注册短信验证码（中国区域）
                        url = "/user/register/verification";
                        Map paramR = new HashMap();
                        paramR.put("timestamp", System.currentTimeMillis());
                        paramR.put("phone", mobilearea + edPhone.getText().toString());
                        new MyAsyncTaskN(RegisteredActivity.this, 0, url, this).execute(paramR);
                    }
                }
                break;
        }
    }

    /**
     * 微信登录
     */
    private void weixinLogin() {
        pb.setVisibility(View.VISIBLE);
        if (api != null && api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);
        } else {
            Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendtime() {
        // 发送验证码按钮的倒计时设置
        if (recLen > 0) {
            final Timer timer = new Timer();
            TimerTask task = null;
            task = new TimerTask() {
                @Override
                public void run() {
                    tvCode.setClickable(false);
                    runOnUiThread(new Runnable() { // UI thread
                        @Override
                        public void run() {
                            if (recLen < 1) {
                                tvCode.setText("再次获取 ");
                                timer.cancel();
                                recLen = 120;
                                tvCode.setClickable(true);
                                sendMessage = true;
                            } else {
                                recLen--;
                                tvCode.setText("重新发送（" + recLen + "）");
                            }
                        }
                    });
                }
            };
            timer.schedule(task, 1000, 1000); // timeTask
        }
    }

    @Override
    public void onSuccess(int type, String str) {
        if (type == 1) {
            if (!TextUtils.isEmpty(token)) {
                ToastUtil.showShort(getApplicationContext(), "绑定成功");
            } else {
                ToastUtil.showShort(getApplicationContext(), "注册成功");
            }
            LoginBean loginBean = new Gson().fromJson(str, LoginBean.class);
            SharedPreferencesUtil.putLoginState(getApplicationContext(), true); //登录状态-已登录
            SharedPreferencesUtil.putLoginInfo(getApplicationContext(), loginBean.getData().getToken(), mobilearea + edPhone.getText().toString(),
                    edPassword.getText().toString(), "", ""); //保存token,phone,pwd
            //跳转到Main
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        if (type == 0) {
            sendtime();
            ToastUtil.showShort(getApplicationContext(), "验证码已发送，请注意短信验证码查收！");
        }
    }

    @Override
    public void onError(String msg) {
        sendMessage = true;
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
