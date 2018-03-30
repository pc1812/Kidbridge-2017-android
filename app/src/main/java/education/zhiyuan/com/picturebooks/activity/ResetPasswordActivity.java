package education.zhiyuan.com.picturebooks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * 重置密码
 */
public class ResetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_newPwd)
    EditText etNewPwd;
    @BindView(R.id.et_confirmPwd)
    EditText etConfirmPwd;
    private String newPwd,confirmPwd;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        MyApp.getInstance().addActivity(this);
        ButterKnife.bind(this);
        tvTitle.setText("重置密码");
        tvRight.setVisibility(View.INVISIBLE);
        Intent intent=getIntent();
        if (intent!=null){
            phone=intent.getStringExtra("phone");
        }
    }

    @OnClick({R.id.iv_back, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_login:
                newPwd=etNewPwd.getText().toString();
                confirmPwd=etConfirmPwd.getText().toString();
                if(TextUtils.isEmpty(TextViewUtils.replaceBlankHa(newPwd))){
                    //提示输入新密码
                    ToastUtil.showShort(this,"请输入新密码");
                    return;
                }
                if(TextUtils.isEmpty(TextViewUtils.replaceBlankHa(confirmPwd))){
                    //提示输入确认密码
                 ToastUtil.showShort(this,"请输入确认密码");
                  return;
                }
                if (!confirmPwd.equals(newPwd)){
                    ToastUtil.showShort(this,"两次密码输入不一致");
                    return;
                }
                //提交修改信息
                doResetPwd(phone,confirmPwd);
                break;
        }
    }

    public void doResetPwd(String phone,String pwd){
        ToastUtil.showShort(this,"修改密码成功，跳转至登录界面");
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
