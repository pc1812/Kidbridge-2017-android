package education.zhiyuan.com.picturebooks.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_about_us);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        RequestOptions options = new RequestOptions();
        options.apply(RequestOptions.circleCropTransform());
        Glide.with(getApplicationContext())
                .load(R.drawable.iv_login_logo)
                .apply(options)
                .into(iv);
        tvTitle.setText("关于我们");
        tvRight.setVisibility(View.GONE);
        String text = "本软件版本为V" + MyApp.versionName;
        tvInfo.setText(text);
        TextViewUtils.setTextColor(tvInfo, tvInfo.getText().toString(), "本软件版本为V".length(), tvInfo.getText().toString().length(), Color.parseColor("#15CF30"));
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
