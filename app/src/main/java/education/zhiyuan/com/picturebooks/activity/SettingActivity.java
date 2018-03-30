package education.zhiyuan.com.picturebooks.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.utils.CacheUtilsNew;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.PermissionDialog;

/**
 * 1./data/data/package_name/files
 * 2./data/data/package_name/cache
 * 3.<sdcard>/Android/data/<package_name>/cache/
 * 4.webview缓存数据
 *
 */
public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_cacheNum)
    TextView tvCacheNum;
    @BindView(R.id.cb_clear)
    CheckBox cbClear;

    private CacheUtilsNew cacheUtils;
    private static int PERMISSION_READ = 100;
    private boolean isPermission = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        tvRight.setVisibility(View.GONE);
        tvTitle.setText("设置");
        cacheUtils = new CacheUtilsNew(getApplicationContext());
        //权限
        check();
        initView();
    }

    public void check() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
                return;
            }
        }
        isPermission = true;
    }

    private void initView() {
        if (isPermission) {
            tvCacheNum.setText(cacheUtils.getTotalCacheSize2());
        }
        cbClear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MyApp.initSetting = false;
                if (b) {
                    cbClear.setBackgroundResource(R.drawable.iv_switch_open);
                    //选中
                    SharedPreferencesUtil.putUpdateSetting(getApplicationContext(), true);
                } else {
                    cbClear.setBackgroundResource(R.drawable.iv_switch_close);
                    SharedPreferencesUtil.putUpdateSetting(getApplicationContext(), false);
                    //没选中
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_READ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isPermission = true;
                tvCacheNum.setText(cacheUtils.getTotalCacheSize2());
            } else {
                new PermissionDialog(SettingActivity.this, "手机读写权限").show();
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.rl_clearcache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_clearcache:
                //清除缓存
                if (isPermission) {
                    if (cacheUtils.clearAppCache()) {
                        tvCacheNum.setText("0KB");
                    } else {
                        ToastUtil.showShort(getApplicationContext(), "清除缓存失败");
                    }
                }
                break;
        }
    }
}
