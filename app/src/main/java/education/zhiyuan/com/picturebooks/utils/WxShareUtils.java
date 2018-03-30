package education.zhiyuan.com.picturebooks.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.IOException;
import java.net.URL;

import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.wxapi.WXEntryActivity;

import static education.zhiyuan.com.picturebooks.MyApp.api;
import static education.zhiyuan.com.picturebooks.utils.BitmapUtils.bmpToByteArray;


/**
 * Created by LH on 2017/9/11.
 * 微信分享
 */

public class WxShareUtils {
    private Activity activity;
    private static final int THUMB_SIZE = 80;

    public WxShareUtils(Activity activity) {
        this.activity = activity;
    }

    public void doShare(byte[] bytes, final int type, String title, String description, String url) {
        if (api != null && api.isWXAppInstalled()) {
            WXMusicObject musicObject = new WXMusicObject();
            musicObject.musicUrl = url;
            final WXMediaMessage mediaMessage = new WXMediaMessage();
            mediaMessage.mediaObject = musicObject;
            mediaMessage.title = title;
            mediaMessage.description = description;
            Bitmap thumb = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ff); //音乐略缩图
            Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, THUMB_SIZE, THUMB_SIZE, true);
//            thumb.recycle();
            mediaMessage.thumbData = bmpToByteArray(thumbBmp, true);
            //构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("music");
            //用于唯一标识一个请求
            req.message = mediaMessage;
            //发送到聊天界面——WXSceneSession 发送到朋友圈——WXSceneTimeline
            req.scene = (type == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline);
            api.sendReq(req);
        } else {
            Toast.makeText(activity, "用户未安装微信", Toast.LENGTH_SHORT).show();
        }
    }


    public void shareMusic(final int type, String title, String description, String musicUrl, final String imgUrl) {
        if (api != null && api.isWXAppInstalled()) {
            WXMusicObject musicObject = new WXMusicObject();
            musicObject.musicUrl = musicUrl;
            final WXMediaMessage mediaMessage = new WXMediaMessage();
            mediaMessage.mediaObject = musicObject;
            mediaMessage.title = title;
            mediaMessage.description = description;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap thumb = null;
                    try {
                        thumb = BitmapFactory.decodeStream(new URL(imgUrl).openStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, THUMB_SIZE, THUMB_SIZE, true);
                    thumb.recycle();
                    mediaMessage.thumbData = bmpToByteArray(thumbBmp, true);
                    //构造一个Req
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = buildTransaction("music");
                    //用于唯一标识一个请求
                    req.message = mediaMessage;
                    //发送到聊天界面——WXSceneSession 发送到朋友圈——WXSceneTimeline
                    req.scene = (type == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline);
                    api.sendReq(req);
                }
            }).start();

        } else {
            Toast.makeText(activity, "用户未安装微信", Toast.LENGTH_SHORT).show();
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void sharePic(final String picUrl, final int type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap thumb = null;
                try {
                    thumb = BitmapFactory.decodeStream(new URL(picUrl).openStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //本地资源图片
                //thumb=BitmapFactory.decodeResource(getContext().getResources(),R.mipmap.ic_launcher);
                Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, THUMB_SIZE, THUMB_SIZE, true);
                WXImageObject imgObj = new WXImageObject(thumb);
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = imgObj;
                msg.thumbData = bmpToByteArray(thumbBmp, true);
                thumb.recycle();
                thumbBmp.recycle();
                //构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("music");
                //用于唯一标识一个请求
                req.message = msg;
                //发送到聊天界面——WXSceneSession 发送到朋友圈——WXSceneTimeline
                req.scene = (type == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline);
                api.sendReq(req);
            }
        }).start();
    }

//    public void shareHtml(final int type, String webUrl, final String picUrl, String title, String description) {
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = webUrl;
//        final WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = title;
//        msg.description = description;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap thumb = null;
//                try {
//                    thumb = BitmapFactory.decodeStream(new URL(picUrl).openStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, THUMB_SIZE, THUMB_SIZE, true);
//                thumb.recycle();
//                msg.thumbData = bmpToByteArray(thumbBmp, true);
//                //构造req
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = buildTransaction("webpage");
//                req.message = msg;
//                req.scene = (type == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline);
//                api.sendReq(req);
//            }
//        }).start();
//    }

    public void shareHtml(final boolean need, final int type, int flag, int id, String webUrl, String title, String description) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webUrl;
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        Bitmap thumb = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.iv_share); //音乐略缩图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, THUMB_SIZE, THUMB_SIZE, true);
        thumb.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        //构造req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = (type == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline);
        if (need) {
            WXEntryActivity.type = type;
            WXEntryActivity.flag = flag;
            WXEntryActivity.id = id;
        }
        api.sendReq(req);
    }

}
