package education.zhiyuan.com.picturebooks.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
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

/**
 * 反馈
 * */
public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_remind)
    EditText etRemind;

    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_feed_back);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvRight.setVisibility(View.GONE);
        tvTitle.setText("反馈");
    }

    @OnClick({R.id.iv_back, R.id.btn_put})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_put:
                content = TextViewUtils.replaceBlankHa(etRemind.getText().toString());
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showShort(getApplicationContext(), "请输入反馈内容");
                } else {
                    try {
                        putData();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void putData() throws UnsupportedEncodingException {
        String url = "/feedback/add";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("content", etRemind.getText().toString());
        new MyAsyncTaskN(this, 0, url, new HttpCallBackN() {
            @Override
            public void onSuccess(int type, String str) {
                ToastUtil.showShort(getApplicationContext(), "提交成功");
            }
            @Override
            public void onError(String msg) {
                ToastUtil.showShort(getApplicationContext(), msg);
            }
        }).execute(param);
    }
}
