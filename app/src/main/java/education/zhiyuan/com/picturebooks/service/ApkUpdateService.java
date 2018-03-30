package education.zhiyuan.com.picturebooks.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import education.zhiyuan.com.picturebooks.http.HttpCallBack;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskMusic;
import education.zhiyuan.com.picturebooks.utils.NetworkUtils;

/**
 * Created by LH on 2017/10/11.
 * Description ：hahah
 */

public class ApkUpdateService extends Service implements HttpCallBack {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String LoadPath = intent.getStringExtra("url");
        String fPath = intent.getStringExtra("fPath");
        int flag = intent.getIntExtra("flag", -1);
        if (flag == 0) {
            new MyAsyncTaskMusic(null, 0, this).execute(LoadPath, fPath);
        } else {
            if (NetworkUtils.isWifiConnected(getApplicationContext())) {
                new MyAsyncTaskMusic(null, 0, this).execute(LoadPath, fPath);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onSuccess(String jsonStr) {
        Log.e("78888787878", "onStartCommand: =====success"+jsonStr);
        EventBus.getDefault().post("apk_insert" + jsonStr);
    }

    @Override
    public void onFaile(String msg) {
        new File(msg).delete();
        Log.e("78888787878", "onFaile: ");
    }

}
