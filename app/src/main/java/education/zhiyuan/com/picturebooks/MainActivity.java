package education.zhiyuan.com.picturebooks;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.bean.VersionCheck;
import education.zhiyuan.com.picturebooks.fragment.HomeFragment;
import education.zhiyuan.com.picturebooks.fragment.LessonFragment;
import education.zhiyuan.com.picturebooks.fragment.MineFragment;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.service.ApkUpdateService;
import education.zhiyuan.com.picturebooks.utils.CacheUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TimeTools;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CusDialog;
import education.zhiyuan.com.picturebooks.view.commodity.custom.PermissionDialog;
import education.zhiyuan.com.picturebooks.view.commodity.custom.UpdateDialog;

/**
 * Created by Lance
 * <p>
 * Created time 2017/6/16.
 * <p>
 * Email:COCOINUT@163.com
 */
public class MainActivity extends AppCompatActivity implements UpdateDialog.UpdateClick, HttpCallBackN {

    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.iv_lesson)
    ImageView ivLesson;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.iv_red)
    ImageView redPoint;

    FragmentManager fm;
    HomeFragment homeFragment;
    LessonFragment lessonFragment;
    MineFragment mineFragment;
    @BindView(R.id.tv_hb)
    TextView tvHb;
    @BindView(R.id.tv_less)
    TextView tvLess;
    @BindView(R.id.tv_m)
    TextView tvM;

    private static int PERMISSION_WRITE = 100;
    private static String APK_URL;
    private String nowVersionCode;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/hbUpdate/";
    private boolean isExit = false;

    private UpdateDialog updateDialog;
    private String remindTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getResolution();
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        MyApp.getInstance().finishActivity();
        fm = getSupportFragmentManager();
        showFragment(0);

        String url = "/version/check";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        new MyAsyncTaskN(MainActivity.this, 0, url, this).execute(param);
//        apkCheck();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    public void getResolution() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            MyApp.resolutionW = metrics.widthPixels;
            MyApp.resolutionH = metrics.heightPixels;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        redPoint.setVisibility(SharedPreferencesUtil.getInfoState(this, SharedPreferencesUtil.getIdInfo(this)) ? View.VISIBLE : View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("unlock_hb")) {   //绘本解锁刷新
            if (homeFragment != null) {
                homeFragment.initAge(true);
            }
        }
        if (msg.equals("unlock_course")) {  //课程解锁刷新
            if (lessonFragment != null) {
                lessonFragment.initAge(true);
            }
        }
        if (msg.equals("show")) {
            redPoint.setVisibility(View.VISIBLE);
        } else {
            redPoint.setVisibility(View.GONE);
        }
        if (msg.contains("apk_insert")) {
            //弹出提示框
            updateDialog = new UpdateDialog(MainActivity.this, this);
//                      .setContent("更新更新更新更新更新哈哈哈哈哈哈哈或或或或或")
//                            .setTime("时间：2017/07/07")
//                            .setInfo("版本：2.0.1\t\t大小：22M")
            updateDialog.show();
        }
    }

    /**
     * 展示fragment
     *
     * @param index
     */
    private void showFragment(int index) {
        FragmentTransaction ft = fm.beginTransaction();
        hideFragment(ft);
        ivHome.setImageResource(R.drawable.iv_main_home_gray);
        ivLesson.setImageResource(R.drawable.iv_main_lesson_gray);
        ivMine.setImageResource(R.drawable.iv_main_mine_gray);
        switch (index) {
            case 0:
                ivHome.setImageResource(R.drawable.iv_main_home_green);
                if (lessonFragment != null) {
                    lessonFragment.setAutoPlay(false);
                }
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    ft.add(R.id.mFragment, homeFragment);
                } else {
                    homeFragment.setAutoPlay(true);
                    ft.show(homeFragment);
                }
                break;
            case 1:
                ivLesson.setImageResource(R.drawable.iv_main_lesson_green);
                if (homeFragment != null) {
                    homeFragment.setAutoPlay(false);
                }
                if (lessonFragment == null) {
                    lessonFragment = new LessonFragment();
                    ft.add(R.id.mFragment, lessonFragment);
                } else {
                    lessonFragment.setAutoPlay(true);
                    ft.show(lessonFragment);
                }
                break;
            case 2:
                ivMine.setImageResource(R.drawable.iv_main_mine_green);
                if (homeFragment != null) {
                    homeFragment.setAutoPlay(false); //homeFragment 停止轮播
                }
                if (lessonFragment != null) {
                    lessonFragment.setAutoPlay(false); //lessonFragment 停止轮播
                }
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    ft.add(R.id.mFragment, mineFragment);
                } else {
                    ft.show(mineFragment);
                }
                break;
        }
        ft.commit();
    }

    /**
     * 隐藏
     */
    private void hideFragment(FragmentTransaction ft) {
        if (homeFragment != null) {
            ft.hide(homeFragment);
        }
        if (lessonFragment != null) {
            ft.hide(lessonFragment);
        }
        if (mineFragment != null) {
            ft.hide(mineFragment);
        }
    }

    @OnClick({R.id.li_home, R.id.li_lesson, R.id.li_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.li_home:
                showFragment(0);
                break;
            case R.id.li_lesson:
                showFragment(1);
                break;
            case R.id.li_mine:
                EventBus.getDefault().post(SharedPreferencesUtil.getInfoState(getApplicationContext(), SharedPreferencesUtil.getIdInfo(getApplicationContext())) ? "show" : "hide");
                showFragment(2);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void checkPer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE);
                return;
            }
        }
        doApkUpdate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_WRITE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doApkUpdate();
            } else {
                new PermissionDialog(MainActivity.this, "手机读写权限").show();
            }
        }
    }

    private void apkCheck() {
        //TODO 1、访问服务器，获取最新apk版本号
        if (!nowVersionCode.equals(MyApp.versionName)) {
            checkPer();
        }
    }

    /**
     * 版本更新
     */
    public void doApkUpdate() {
        //TODO 1.1 有新版本，判断之前是否下载过
        Log.e("asasdd", "doApkUpdate: " + nowVersionCode);
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(nowVersionCode + "pictureBooks.apk")) {
                    //已经下载好，提示是否安装
                    isExit = true;
                    if (!isTime()) {
                        return;
                    }
                    //弹出提示框
                    updateDialog = new UpdateDialog(MainActivity.this, this);
//                      .setContent("更新更新更新更新更新哈哈哈哈哈哈哈或或或或或")
//                            .setTime("时间：2017/07/07")
//                            .setInfo("版本：2.0.1\t\t大小：22M")
                    updateDialog.show();
                    SharedPreferencesUtil.putRemindTime(getApplicationContext(), System.currentTimeMillis() + "");
                } else {
                    //删除之前下载的
                    files[i].delete();
                }
            }
        }
        if (!isExit) {
            //TODO WiFi下不自动更新，，提示有新版本
//            if (MyApp.initSetting || !SharedPreferencesUtil.getUpdateSetting(getApplicationContext())) {
            new CusDialog(MainActivity.this, R.style.Dialog, "App有更新", new CusDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (!confirm) {
                        startService();
                    }
                }
            }).setNegativeButton("确定").setPositiveButton("取消").show();
//                return;
//            }
            //TODO 1.2下载新版本,开启服务
//            startService();
        }
    }

    public void startService() {
        Intent intent = new Intent(MainActivity.this, ApkUpdateService.class);
        intent.putExtra("url", APK_URL);
        intent.putExtra("flag", 0);
        intent.putExtra("name", nowVersionCode + "pictureBooks.apk");
        intent.putExtra("save", path + nowVersionCode + "pictureBooks.apk");
        File f = new File(path);
        if (f.exists()) {
            CacheUtils.deleteDir(f);
            f.mkdirs();
        } else {
            f.mkdirs();
        }
        intent.putExtra("fPath", path + nowVersionCode + "pictureBooks.apk");
        startService(intent);
    }

    public boolean isTime() {
        remindTime = SharedPreferencesUtil.getRemindTime(getApplicationContext());
        int nexRemindTime = Integer.valueOf(TimeTools.getStr(TimeTools.getDateStrNext(TimeTools.getStrTime(remindTime), 0) + ""));
        int timeToday = Integer.valueOf(TimeTools.getStr(System.currentTimeMillis() + ""));
        return nexRemindTime <= timeToday;
    }

    /**
     * 安装apk  7.0
     */
    private void installApk(String mUrl) {
        File file = new File(path, nowVersionCode + "pictureBooks.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri apkurl = FileProvider.getUriForFile(MainActivity.this,
                    getPackageName() + ".fileprovider", file);
            intent.setDataAndType(apkurl, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(new File(mUrl)), "application/vnd.android.package-archive");
        }
        try {
            startActivity(intent);
        }catch (Exception e){
            Log.e("error", "installApk: "+e.getMessage() );
        }

    }

    @Override
    public void laterClick() {
        updateDialog.dismiss();
    }

    @Override
    public void nowClick() {
        updateDialog.dismiss();
        installApk(path + nowVersionCode + "pictureBooks.apk");
    }

    @Override
    public void onSuccess(int type, String str) {
        VersionCheck versionCheck = new Gson().fromJson(str, VersionCheck.class);
        nowVersionCode = versionCheck.getData().getNumber();
        APK_URL = Api.QN + versionCheck.getData().getContent();
        apkCheck();
    }

    @Override
    public void onError(String msg) {

    }
}
