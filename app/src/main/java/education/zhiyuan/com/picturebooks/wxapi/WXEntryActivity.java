package education.zhiyuan.com.picturebooks.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import education.zhiyuan.com.picturebooks.MainActivity;
import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.activity.RegisteredActivity;
import education.zhiyuan.com.picturebooks.bean.LoginBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.utils.Util;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    public static int type = -1;
    public static int flag = -1; //绘本：0 ：课程1
    public static int id = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp.api = WXAPIFactory.createWXAPI(this, MyApp.APP_ID, false);
        MyApp.api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (baseResp.getType() == 1) {  //登录
                    //发送成功
                    SendAuth.Resp sendResp = (SendAuth.Resp) baseResp;
                    if (sendResp != null) {
                        String code = sendResp.code;
                        getWXToken(code);
                    }
                } else if (baseResp.getType() == 2) {  //分享
                    if (type == 1) { //TODO 微信分享朋友圈成功，跟读+打卡
                        addWater();

                    }
                    ToastUtil.showShort(getApplicationContext(), "分享成功");
                    finish();
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //发送取消
                ToastUtil.showShort(getApplicationContext(), "取消授权");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //发送被拒绝
                ToastUtil.showShort(getApplicationContext(), "拒绝授权");
                break;
            default:
                //发送返回
                ToastUtil.showShort(getApplicationContext(), "哈----");
                break;
        }
        flag = -1;
        id = -1;
        type = -1;
        EventBus.getDefault().post("wxshare");
        finish();
    }

    /**
     * 分享成功，水滴+1
     */
    public void addWater() {
        String url = " ";
        if (flag == 0) {
            url = "/user/book/repeat/share"; //绘本
        } else {
            url = "/user/course/repeat/share";
        }
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("id", id);
        new MyAsyncTaskN(this, 0, url, new HttpCallBackN() {
            @Override
            public void onSuccess(int type, String str) {
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showShort(getApplicationContext(), msg);
            }
        }).execute(param);
    }


    /**
     * 获取微信token
     * {"event":"UNUSER","data":{"token":"7aa764addba54e55a53086ec5a7dda57"},"describe":"用户不存在 ~"}
     */
    public void getWXToken(String code) {
        String url = "/user/auth";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("code", code);
        param.put("type", 0);
        param.put("sign", Util.sign(param));
        new MyAsyncTaskN(this, 0, url, new HttpCallBackN() {
            @Override
            public void onSuccess(int type, String str) {
                LoginBean loginBean = new Gson().fromJson(str, LoginBean.class);
                if (loginBean.getEvent().equals("UNUSER")) {
                    //未绑定
                    ToastUtil.showShort(getApplicationContext(), "微信授权成功，请继续完成手机号码绑定");
                    Intent intent = new Intent(getApplicationContext(), RegisteredActivity.class);
                    intent.putExtra("token", loginBean.getData().getToken());
                    startActivity(intent);
                } else {
                    SharedPreferencesUtil.putLoginInfo(getApplicationContext(), loginBean.getData().getToken(), "", "", loginBean.getData().getHead(), loginBean.getData().getNickname());
                    SharedPreferencesUtil.putLoginState(getApplicationContext(), true);
                    //保存标记
                    SharedPreferencesUtil.putIdInfo(getApplicationContext(), loginBean.getData().getId());
                    //设置极光推送 别名
                    JPushInterface.setAlias(MyApp.context, //上下文对象
                            loginBean.getData().getId(), //别名
                            new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {
                                }
                            });
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                finish();
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showShort(getApplicationContext(), msg);
            }
        }).execute(param);

    }


}
