package education.zhiyuan.com.picturebooks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.adpter.GuideViewPagerAdapter;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;

/**
 * Created by Lance on 2017/6/16.
 * Email : COCOINUT@163.com
 * Introduce : 引导页
 */

public class GuideActivity extends BaseActivity {

    @BindView(R.id.mViewPage)
    ViewPager mViewPage;

    private GuideViewPagerAdapter adapter;
    private List<View> views;
    private int[] picid = {R.drawable.iv_guid_one, R.drawable.iv_guid_two, R.drawable.iv_guid_three};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_guide);
    }

    @Override
    protected void initData() {
        SharedPreferencesUtil.putUpdateSetting(getApplicationContext(), true);
        if (SharedPreferencesUtil.getFirstInfo(this)) {
            SharedPreferencesUtil.putFirstInfo(this);
            views = new ArrayList<>();
            for (int i = 0; i < picid.length; i++) {
                ImageView iv = new ImageView(this);
                iv.setImageResource(picid[i]);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                views.add(iv);
            }
            adapter = new GuideViewPagerAdapter(views);
            mViewPage.setAdapter(adapter);
        } else {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        }
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.iv_go)
    public void onViewClicked() {
        if (mViewPage.getCurrentItem() == 2) {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        }
    }
}
