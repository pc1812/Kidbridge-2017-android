package education.zhiyuan.com.picturebooks.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.fragment.ItemLessonFragment;

/**
 * TODO 热门课程更多
 */
public class HotCourseMoreActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;

    private FragmentManager fm;
    private ItemLessonFragment itemLessonFragment;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_course);
        ButterKnife.bind(this);
        tvRight.setVisibility(View.INVISIBLE);
        tvTitle.setText("热门课程");
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        doInit();
    }

    private void doInit() {
        itemLessonFragment = new ItemLessonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", "/course/hot/list");
        bundle.putInt("type", 2);
        itemLessonFragment.setArguments(bundle);
        ft.add(R.id.mFragment, itemLessonFragment);
        ft.commit();
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
