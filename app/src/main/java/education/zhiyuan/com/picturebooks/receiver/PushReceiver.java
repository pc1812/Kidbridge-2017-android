package education.zhiyuan.com.picturebooks.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import org.litepal.LitePal;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import education.zhiyuan.com.picturebooks.bean.CommentBeanReceiver;
import education.zhiyuan.com.picturebooks.bean.JGCommentBean;
import education.zhiyuan.com.picturebooks.bean.JGPushBean;
import education.zhiyuan.com.picturebooks.bean.PushBeanReceiver;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;

/**
 * Created by LH on 2017/9/23.
 */

public class PushReceiver extends BroadcastReceiver {
    private static String TAG = "PushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            String content = bundle.getString(JPushInterface.EXTRA_MESSAGE); //推送内容
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);  //附加字段，json数据
            //数据库
            LitePal.initialize(context);
            LitePal.getDatabase();
            //获取json
            Map receiverMap = new Gson().fromJson(content, Map.class);
            if (receiverMap.get("type").toString().equals("0.0")) { //评论消息
                CommentBeanReceiver commentBeanReceiver = new Gson().fromJson(content, CommentBeanReceiver.class);
                JGCommentBean jgCommentBean = new JGCommentBean();
                jgCommentBean.setTag(SharedPreferencesUtil.getIdInfo(context));
                jgCommentBean.setCommentPid(commentBeanReceiver.getBody().getComment().getPid());
                jgCommentBean.setCommentSid(commentBeanReceiver.getBody().getComment().getSid());
                jgCommentBean.setCommentType(commentBeanReceiver.getBody().getComment().getType());
                jgCommentBean.setText(commentBeanReceiver.getBody().getMessage().getText());
                jgCommentBean.setCreateTime(commentBeanReceiver.getBody().getMessage().getCreateTime());
                jgCommentBean.setHead(commentBeanReceiver.getBody().getUser().getHead());
                jgCommentBean.setNickname(commentBeanReceiver.getBody().getUser().getNickname());
                jgCommentBean.setUserId(commentBeanReceiver.getBody().getUser().getId());
                SharedPreferencesUtil.putInfoState(context, SharedPreferencesUtil.getIdInfo(context), true);
                SharedPreferencesUtil.putInfoStateC(context, SharedPreferencesUtil.getIdInfo(context), 0, SharedPreferencesUtil.getInfoStateC(context, SharedPreferencesUtil.getIdInfo(context), 0) + 1);  // 0,1,2,3
                Log.e("pushpush", "onReceive: ==comm" + jgCommentBean.save());
            } else { //推送消息
                PushBeanReceiver pushBeanReceiver = new Gson().fromJson(content, PushBeanReceiver.class);
                JGPushBean jgPushBean = new JGPushBean();
                jgPushBean.setTag(SharedPreferencesUtil.getIdInfo(context));
                jgPushBean.setText(pushBeanReceiver.getBody().getMessage().getText());
                jgPushBean.setCreateTime(pushBeanReceiver.getBody().getMessage().getCreateTime());
                Log.e("pushpush", "onReceive: ===push" + jgPushBean.save());
                SharedPreferencesUtil.putInfoState(context, SharedPreferencesUtil.getIdInfo(context), true);
                SharedPreferencesUtil.putInfoStateC(context, SharedPreferencesUtil.getIdInfo(context), 1, SharedPreferencesUtil.getInfoStateC(context, SharedPreferencesUtil.getIdInfo(context), 1) + 1);
            }
        }
    }
}

