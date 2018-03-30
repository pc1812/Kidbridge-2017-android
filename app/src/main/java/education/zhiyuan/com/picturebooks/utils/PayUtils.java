package education.zhiyuan.com.picturebooks.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.bean.AliBean;
import education.zhiyuan.com.picturebooks.bean.PayResult;
import education.zhiyuan.com.picturebooks.bean.WeiPayBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;

/**
 * Created by Spring on 2017/8/3.
 */

public class PayUtils implements HttpCallBackN {
    private Context context;
    private Activity activity;
    private static final int SDK_PAY_FLAG = 1;

    public PayUtils(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void getPayInfo(double fee, int type) {
        String url = "";
        if (type == 0) {
            url = "/payment/alipay";
        } else {
            url = "/payment/wechat";
        }
        Map param = new HashMap();
        param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(context).getToken());
        param.put("fee", fee);
        param.put("sign", Util.sign(param));
        new MyAsyncTaskN(activity, type, url, this).execute(param);
    }

    /**
     * 支付宝支付
     */
    public void aliPay(String jsonObject) {
        final String orderInfo = jsonObject;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post("pay");
                    } else {
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    /**
     * 微信支付
     */
    public void weiPay(WeiPayBean weiPayBean) {
        WeiPayBean.DataBean.PaymentBean paymentBean = weiPayBean.getData().getPayment();
        PayReq request = new PayReq();
        request.appId = paymentBean.getAppid(); //APPID
        request.partnerId = paymentBean.getPartnerid();
        request.prepayId = paymentBean.getPrepayid();
        request.nonceStr = paymentBean.getNoncestr();  //随机字符串
        request.timeStamp = paymentBean.getTimestamp() + ""; //时间戳
        request.packageValue = paymentBean.getPackageX();
        request.sign = paymentBean.getSign();
        MyApp.api.sendReq(request);
    }

    @Override
    public void onSuccess(int type, String str) {
        switch (type) {
            case 0: //支付宝
                AliBean aliBean = new Gson().fromJson(str, AliBean.class);
                aliPay(aliBean.getData().getPayment());
                break;
            case 1: //微信
                WeiPayBean weiPayBean = new Gson().fromJson(str, WeiPayBean.class);
                weiPay(weiPayBean);
                break;
        }
    }


    @Override
    public void onError(String msg) {
        ToastUtil.showShort(context, msg);
    }
}
