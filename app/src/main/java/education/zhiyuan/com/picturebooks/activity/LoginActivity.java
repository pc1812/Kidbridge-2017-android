package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.utils.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import education.zhiyuan.com.picturebooks.MainActivity;
import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.bean.LoginBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.Md5Encryption;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.StatusBarUtil;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.utils.Util;

/**
 * Created by Lance on 2017/6/19.
 * Email : COCOINUT@163.com
 * Introduce : 登录界面
 */

public class LoginActivity extends BaseActivity implements HttpCallBackN {

    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.tv_new_user)
    TextView tvNewUser;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;

    private String mobilearea = "+86";
    private Dialog pBar;   //网络加载
    private String phone, pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.layout_login);
    }

    @Override
    protected void initData() {
        MyApp.getInstance().addActivity(this);
        if (SharedPreferencesUtil.getLoginState(this)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void initView() {
        tvNewUser.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvForget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        RequestOptions options = new RequestOptions();
        options.apply(RequestOptions.circleCropTransform());
        Glide.with(getApplicationContext())
                .load(R.drawable.iv_login_logo)
                .apply(options)
                .into(ivLogo);

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
        StatusBarUtil.StatusBarLightMode(this);  //设置小米魅族状态栏颜色
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();                //设置状态栏颜色
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
    }

    private int cliclIndex = 0;

    @OnClick({R.id.tv_new_user, R.id.tv_forget, R.id.btn_login, R.id.WChatLogin, R.id.iv_logo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_logo:
                cliclIndex += 1;
                if (cliclIndex == 6) {
                    if (Api.HOST.equals("http://52.53.124.16")) {
                        Api.HOST = "http://kidbridge.test.dev.51zhiyuan.net";
                        ToastUtil.showShort(getApplicationContext(), "http://kidbridge.test.dev.51zhiyuan.net");
                    } else {
                        Api.HOST = "http://52.53.124.16";
                        ToastUtil.showShort(getApplicationContext(), "http://52.53.124.16");
                    }
                    cliclIndex = 0;
                }
                break;
            case R.id.tv_new_user:
                startActivity(new Intent(getApplicationContext(), RegisteredActivity.class));
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
            case R.id.btn_login:
                phone = edPhone.getText().toString();
                pwd = edPassword.getText().toString();
                if (TextUtils.isEmpty(TextViewUtils.replaceBlankHa(phone))) {
                    ToastUtil.showShort(this, "手机号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.showShort(this, "密码不能为空");
                    return;
                }
                checkLogin(mobilearea + phone, pwd);
                break;
            case R.id.WChatLogin:
                weixinLogin();
                break;
        }
    }

    /**
     * 微信登录
     */
    private void weixinLogin() {
        pb.setVisibility(View.VISIBLE);
        if (MyApp.api != null && MyApp.api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            MyApp.api.sendReq(req);
        } else {
            Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 登录操作
     */
    private void checkLogin(final String phone, final String pwd) {
        pBar = new Dialog(this, R.style.Dialog);
        pBar.setContentView(R.layout.progress);
        if (!isFinishing()) {
            pBar.show();
        }
        String url = "/user/login";
        final Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("phone", phone);
        param.put("password", Md5Encryption.md5(phone + ":" + pwd + ":" + Util.DEFAULT_SALT));
        new MyAsyncTaskN(LoginActivity.this, 0, url, this).execute(param);
    }


    @Override
    public void onSuccess(int type, String str) {
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
        MyApp.loginG = false;
        LoginBean loginBean = new Gson().fromJson(str, LoginBean.class);
        SharedPreferencesUtil.putLoginState(getApplicationContext(), true); //登录状态-已登录
        SharedPreferencesUtil.putLoginInfo(getApplicationContext(), loginBean.getData().getToken(), phone, pwd,
                loginBean.getData().getHead(), loginBean.getData().getNickname()); //保存token,phone,pwd,icon,name
        ToastUtil.showShort(getApplicationContext(), "登录成功");
        //保存手机号
        SharedPreferencesUtil.putIdInfo(getApplicationContext(), loginBean.getData().getId());
        //设置极光推送 别名
        JPushInterface.setAlias(this, //上下文对象
                loginBean.getData().getId(), //别名
                new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        Log.e("alias", "set alias result is==login" + i);
                    }
                });
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    public void onError(String msg) {
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
        ToastUtil.showShort(getApplicationContext(), msg);
    }
}
