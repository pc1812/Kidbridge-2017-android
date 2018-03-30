package education.zhiyuan.com.picturebooks.http;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.activity.LoginActivity;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * Created by LH on 2017/9/19.
 */

public class MyAsyncTaskN extends AsyncTask<Map, Void, String> {
    private String url;
    private HttpCallBackN callBack;
    private Activity activity;
    private int type;

    public MyAsyncTaskN(Activity activity, int type, String url, HttpCallBackN callBack) {
        this.activity = activity;
        this.type = type;
        this.url = url;
        this.callBack = callBack;
    }


    @Override
    protected String doInBackground(Map... maps) {
        return new HttpUtils().doPost(Api.HOST + url, maps[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (!TextUtils.isEmpty(s)) {
            Map map = new Gson().fromJson(s, Map.class);
            String event = map.get("event").toString();
            if ("SUCCESS".equals(event) || "INSUFFICIENT_BALANCE".equals(event) || "UNUSER".equals(event)) {  //成功
                callBack.onSuccess(type, s);
            } else if ("UNTOKEN".equals(event) || "UNAUTH".equals(event) || "AUTH_ABNORMAL".equals(event)) { //登录失效|未登录
                //TODO 是不是要删除之前的别名
                JPushInterface.setAlias(activity, //上下文对象
                        "", //空字符串表示取消之前的设置
                        new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                Log.e("alias", "set alias result is==login" + i);
                            }
                        });
                ToastUtil.showShort(activity, map.get("describe").toString());
                if (!MyApp.loginG) {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    SharedPreferencesUtil.putLoginState(activity, false);
                    activity.finish();
                    MyApp.loginG = true;
                    Log.e("9090111", "onPostExecute: ");
                }
            } else if ("ERROR".equals(event)) {
                callBack.onError(map.get("describe").toString());
            }
        } else {
            callBack.onError("网络错误，请稍后重试！");
        }
    }
}
