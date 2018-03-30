package education.zhiyuan.com.picturebooks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;

import butterknife.BindView;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.base.BaseActivity;

/**
 * Created by Lance on 2017/6/21.
 * Email : COCOINUT@163.com
 * Introduce : Banner图片轮播详情 广告详情页
 */

public class BannerDetailActivity extends BaseActivity implements ChromeClientCallbackManager.ReceivedTitleCallback {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll)
    LinearLayout ll;

    private String url;
    private AgentWeb mAgentWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.layout_bannerdetail);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(ll, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .defaultProgressBarColor()
                    .setReceivedTitleCallback(this)
                    .createAgentWeb()
                    .ready()
                    .go(url);
        }
    }

    @Override
    protected void initView() {
        tvRight.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        tvTitle.setText(title);
    }
}
