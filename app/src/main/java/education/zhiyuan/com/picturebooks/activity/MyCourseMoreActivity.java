package education.zhiyuan.com.picturebooks.activity;

import android.os.Bundle;
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
import education.zhiyuan.com.picturebooks.fragment.ItemLessonFragment;
import education.zhiyuan.com.picturebooks.utils.TablayoutLine;

/**
 * 课程馆-我的课程更多
 */
public class MyCourseMoreActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_more);
        ButterKnife.bind(this);
        tvRight.setVisibility(View.INVISIBLE);
        tvTitle.setText("我的课程");
        initData();
    }

    private void initData() {
        titleList = new ArrayList<>();
        titleList.add("未开课");
        titleList.add("已开课");
        fragmentList = new ArrayList<>();
        for (int i = 0; i < titleList.size(); i++) {
            ItemLessonFragment itemLessonFragment = new ItemLessonFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i + 3);
            bundle.putString("url", "/user/course");
            itemLessonFragment.setArguments(bundle);
            fragmentList.add(itemLessonFragment);
        }
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


