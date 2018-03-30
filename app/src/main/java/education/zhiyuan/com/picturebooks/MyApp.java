package education.zhiyuan.com.picturebooks;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Lance on 2017/6/16.
 * Email : COCOINUT@163.com
 */

public class MyApp extends MultiDexApplication {
    public static boolean isLogin = false;
    public static Context context;
    private static MyApp instance;
    private static List<Activity> activityList = new ArrayList<>();
    public static IWXAPI api;
    public static String APP_ID = "wxb6f923a74661ce3b"; //wxb6f923a74661ce3b
    public static String versionName;
    public static int versionCode;
    public static boolean initSetting = true; //默认WiFi下自动下载
    public static int resolutionW=-1,resolutionH=-1;
    public static boolean loginG=false; //被下线
    public static int llHeight=-1;
    private void getVersionInfo() throws Exception {
        PackageManager packageManager = getPackageManager();
        //0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        versionName = packInfo.versionName;
        versionCode = packInfo.versionCode;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instance = this;
        //注册到微信
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
        //极光
        JPushInterface.setDebugMode(false);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
        try {
            getVersionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }


    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void finishActivity() {
        for (int i = 0; i < activityList.size(); i++) {
            activityList.get(i).finish();
        }
    }

    public static Context getContext() {
        return context;
    }

    public static MyApp getInstance() {
        return instance;
    }

}
