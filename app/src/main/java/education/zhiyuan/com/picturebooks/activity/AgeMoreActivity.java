package education.zhiyuan.com.picturebooks.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.adpter.FragmentAgeAdapter;
import education.zhiyuan.com.picturebooks.fragment.FragmentHuibenAge;
import education.zhiyuan.com.picturebooks.fragment.FragmentLessonAge;

/**
 * Created by Lance on 2017/6/20.
 * Email : COCOINUT@163.com
 * Introduce : 年龄分区 更多
 */

public class AgeMoreActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;

    private List<Fragment> AgeFragmentList = new ArrayList<>();
    private List<String> tabTitle = new ArrayList<>();
    private FragmentAgeAdapter fragmentAgeAdapter;
    private String str;
    private int pos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_agemore);
        EventBus.getDefault().register(this);
        str = getIntent().getStringExtra("str");
        pos = getIntent().getIntExtra("pos", 0);
        ButterKnife.bind(this);
        tvTitle.setText("适合年龄");
        tvRight.setVisibility(View.GONE);
        tabTitle.add("3-5岁");
        tabTitle.add("6-8岁");
        tabTitle.add("9-12岁");
        viewPage.setOffscreenPageLimit(3);
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pos=position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (str.equals("lesson")) {
            InitLessonMore();  //课程馆-年龄更多
        } else {
            InitHomeMore();   //绘本馆，年龄更多
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("unlock_hb")) {
            InitHomeMore();   //绘本馆，年龄更多
        }
        if (msg.equals("unlock_course")) {
            InitLessonMore();  //课程馆-年龄更多
        }
    }

    private void InitLessonMore() {
        AgeFragmentList.clear();
        for (int i = 0; i < tabTitle.size(); i++) {
            FragmentLessonAge fragmentLessonAgeMore = FragmentLessonAge.newInstance2("more", i);
            AgeFragmentList.add(fragmentLessonAgeMore);
        }
        fragmentAgeAdapter = new FragmentAgeAdapter(getSupportFragmentManager(), AgeFragmentList, tabTitle);
        viewPage.setAdapter(fragmentAgeAdapter);
        tabLayout.setupWithViewPager(viewPage);
        viewPage.setCurrentItem(pos);
    }

    private void InitHomeMore() {
        AgeFragmentList.clear();
        for (int i = 0; i < tabTitle.size(); i++) {
            FragmentHuibenAge fragmentHuibAgeMore = FragmentHuibenAge.newInstance2("more", i);
            AgeFragmentList.add(fragmentHuibAgeMore);
        }
        fragmentAgeAdapter = new FragmentAgeAdapter(getSupportFragmentManager(), AgeFragmentList, tabTitle);
        viewPage.setAdapter(fragmentAgeAdapter);
        tabLayout.setupWithViewPager(viewPage);
        viewPage.setCurrentItem(pos);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
