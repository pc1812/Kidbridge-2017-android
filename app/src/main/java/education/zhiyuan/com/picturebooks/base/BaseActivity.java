package education.zhiyuan.com.picturebooks.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import butterknife.ButterKnife;

public abstract class BaseActivity extends CheckPermissionsActivity     {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView();
        ButterKnife.bind(this);
        initData();
        initView();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    protected abstract void setContentView();

    protected abstract void initData();

    protected abstract void initView();


}
