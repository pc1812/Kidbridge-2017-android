package education.zhiyuan.com.picturebooks.http;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.File;
import java.util.Map;

/**
 * Created by Spring on 2017/8/2.
 * 音频下载
 */

public class MyAsyncTaskMusic extends AsyncTask<String, Void, String> {
    private HttpCallBack callBack;
    private Map<Integer, String> map;
    private int pos;
    private boolean pro = false;
    private String fPath;

    public MyAsyncTaskMusic(Map<Integer, String> map, int pos, HttpCallBack callBack) {
        this.map = map;
        this.pos = pos;
        this.callBack = callBack;
    }

    @Override
    protected String doInBackground(String... strings) {
        fPath = strings[1];
        return new LoadApkUtils().loadApk(strings[0], strings[1]);
//        return new HttpLoadMu().load(strings[0], strings[1]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (!TextUtils.isEmpty(s)) {
            if (!TextUtils.isEmpty(s)) {
                if (map != null) {
                    map.put(pos, s);
                }
                File file = new File(s);
                if (file.exists()){
                    callBack.onSuccess(s);
                }
            } else {
                callBack.onFaile(fPath);
            }
        }
    }

}
