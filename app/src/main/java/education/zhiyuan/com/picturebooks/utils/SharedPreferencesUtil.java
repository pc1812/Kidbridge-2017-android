package education.zhiyuan.com.picturebooks.utils;

import android.content.Context;
import android.content.SharedPreferences;

import education.zhiyuan.com.picturebooks.bean.EventPlay;
import education.zhiyuan.com.picturebooks.bean.EventRecord;
import education.zhiyuan.com.picturebooks.bean.UserInfo;


public class SharedPreferencesUtil {
    private static final String spFileName = "welcomePage";    //第一次进入
    private static final String dkFileName = "toclock";      //打卡
    private static final String loginInfo = "login_info";  //登录信息
    private static final String isLogin = "is_login";    //登录状态
    private static final String readToken = "read_token";    //跟读token
    private static final String readPos = "read_pos";    //绘本播放的 状态
    private static final String recordPos = "record_pos";    //绘本播放的 状态
    private static final String addInfo = "add_info"; //地址
    private static final String userTag = "user_tag"; //手机号
    private static final String newInfo = "new_info";
    private static final String updateSetting = "update_setting"; //更新设置
    private static final String remindTime = "remind_time"; //提醒时间



    //判断是否第一次进入app
    public static Boolean getFirstInfo(Context context) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean("is_first", true);
        return result;
    }

    //保存第一次打开的记录
    public static void putFirstInfo(Context context) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean("is_first", false);
        editor.commit();
    }

    //保存登录状态
    public static void putLoginState(Context context, Boolean value) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                isLogin, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setPreferences.edit();
        editor.putBoolean("login_state", value);
        editor.commit();
    }

    //获取登录状态
    public static boolean getLoginState(Context context) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                isLogin, Context.MODE_PRIVATE);
        return setPreferences.getBoolean("login_state", false);
    }

    //保存手机号
    public static void putIdInfo(Context context, String id) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                userTag, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setPreferences.edit();
        editor.putString("id", id);
        editor.commit();
    }

    //获取手机号
    public static String getIdInfo(Context context) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                userTag, Context.MODE_PRIVATE);
        return setPreferences.getString("id", "");
    }

    //保存消息状态  评论
    public static void putInfoState(Context context, String tag, boolean state) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                newInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setPreferences.edit();
        editor.putBoolean(tag, state);
        editor.commit();
    }

    //是否有新消息  评论
    public static boolean getInfoState(Context context, String tag) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                newInfo, Context.MODE_PRIVATE);
        return setPreferences.getBoolean(tag, false);
    }

    //保存消息状态  评论
    public static void putInfoStateC(Context context, String tag, int type, int state) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                newInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setPreferences.edit();
        if (type == 0) {
            editor.putInt(tag + "state_com", state);
        } else {
            editor.putInt(tag + "state_push", state);
        }
        editor.commit();
    }

    //是否有新消息  评论
    public static int getInfoStateC(Context context, String tag, int type) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                newInfo, Context.MODE_PRIVATE);
        if (type == 0) {
            return setPreferences.getInt(tag + "state_com", -1);
        } else {
            return setPreferences.getInt(tag + "state_push", -1);
        }
    }

    //保存登录信息
    public static void putLoginInfo(Context context, String token, String phone, String pwd, String head, String name) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                loginInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setPreferences.edit();
        editor.putString("token", token);
        editor.putString("user_phone", phone);
        editor.putString("user_pwd", pwd);
        editor.putString("user_head", head);
        editor.putString("user_name", name);
        editor.commit();
    }

    //获取登录信息
    public static UserInfo getLoginInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(
                loginInfo, Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(sp.getString("user_phone", "000"));
        userInfo.setPwd(sp.getString("user_pwd", "000"));
        userInfo.setToken(sp.getString("token", "000"));
        userInfo.setHeadIcon(sp.getString("user_head", "000"));
        userInfo.setNickname(sp.getString("user_name", "000"));
        return userInfo;
    }

    // 保存绘本播放 pos
    public static void putHuibenRead(Context context,
                                     EventPlay eventPlay) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                readPos, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putInt("readPos", eventPlay.getPosition());
        editor.putBoolean("readState", eventPlay.isPlay());
        editor.commit();
    }

    //获取绘本播放 pos
    public static EventPlay getHuibenRead(Context context) {//strDefault  boolean: Value to return if this preference does not exist.
        SharedPreferences setPreferences = context.getSharedPreferences(
                readPos, Context.MODE_PRIVATE);
        EventPlay eventPlay = new EventPlay();
        eventPlay.setPlay(setPreferences.getBoolean("readState", false));
        eventPlay.setPosition(setPreferences.getInt("readPos", -1));
        return eventPlay;
    }

    // 保存绘本录音
    public static void putHuibenRecord(Context context,
                                       EventRecord eventRecord) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                recordPos, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putInt("recordPos", eventRecord.getPosition());
        editor.putBoolean("recordState", eventRecord.isRecprding());
        editor.commit();
    }

    //获取绘本录音 pos
    public static EventRecord getHuibenRecord(Context context) {//strDefault  boolean: Value to return if this preference does not exist.
        SharedPreferences setPreferences = context.getSharedPreferences(
                recordPos, Context.MODE_PRIVATE);
        EventRecord eventRecord = new EventRecord();
        eventRecord.setRecprding(setPreferences.getBoolean("recordState", false));
        eventRecord.setPosition(setPreferences.getInt("recordPos", -1));
        return eventRecord;
    }

    //保存地址
    public static void putAddInfo(Context context,
                                  int flag) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                addInfo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putInt("add_flag", flag);
        editor.commit();
    }

    //保存地址
    public static boolean getAddInfo(Context context) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                addInfo, Context.MODE_PRIVATE);
        return setPreferences.getInt("add_flag", -1) != -1;
    }

    //保存更新设置
    public static void putUpdateSetting(Context context,
                                        boolean flag) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                updateSetting, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean("update_setting", flag);
        editor.commit();
    }

    //获取设置
    public static boolean getUpdateSetting(Context context) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                updateSetting, Context.MODE_PRIVATE);
        return setPreferences.getBoolean("update_setting", false);
    }

    //保存更新设置
    public static void putRemindTime(Context context, String time) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                remindTime, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putString("remind_time", time);
        editor.commit();
    }

    //获取设置
    public static String getRemindTime(Context context) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                remindTime, Context.MODE_PRIVATE);
        return setPreferences.getString("remind_time", "121212");
    }





}
