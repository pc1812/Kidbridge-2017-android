package education.zhiyuan.com.picturebooks.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.adpter.FragmentAgeAdapter;
import education.zhiyuan.com.picturebooks.fragment.ItemHbFragment;
import education.zhiyuan.com.picturebooks.utils.TablayoutLine;

/**
 * Created by Lance on 2017/6/20.
 * Email : COCOINUT@163.com
 * Introduce : 我的绘本
 */

public class MyBridge extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;

    private List<String> titleList;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bridge);
        ButterKnife.bind(this);
        tvRight.setVisibility(View.GONE);
        tvTitle.setText("我的绘本");
        initData();
    }

    /**
     * 模拟初始化数据
     */
    private void initData() {
        titleList = new ArrayList<>();
        titleList.add("免费绘本");
        titleList.add("付费绘本");
        fragmentList = new ArrayList<>();
        for (int i = 0; i < titleList.size(); i++) {
            ItemHbFragment itemHbFragment = new ItemHbFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1 - i);
            bundle.putString("url", "/user/book");
            itemHbFragment.setArguments(bundle);
            fragmentList.add(itemHbFragment);
        }
        viewPage.setOffscreenPageLimit(2);
        viewPage.setAdapter(new FragmentAgeAdapter(getSupportFragmentManager(), fragmentList, titleList));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                TablayoutLine.setIndicator(tabLayout, 30, 30);
            }
        });
        tabLayout.setupWithViewPager(viewPage);
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
