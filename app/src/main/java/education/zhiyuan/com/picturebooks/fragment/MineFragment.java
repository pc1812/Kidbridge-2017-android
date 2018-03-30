package education.zhiyuan.com.picturebooks.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.activity.AboutUsActivity;
import education.zhiyuan.com.picturebooks.activity.FeedBackActivity;
import education.zhiyuan.com.picturebooks.activity.IsTeacherActivity;
import education.zhiyuan.com.picturebooks.activity.LoginActivity;
import education.zhiyuan.com.picturebooks.activity.MessageActivity;
import education.zhiyuan.com.picturebooks.activity.MyBalanceActivity;
import education.zhiyuan.com.picturebooks.activity.MyBridge;
import education.zhiyuan.com.picturebooks.activity.MyClassActivity;
import education.zhiyuan.com.picturebooks.activity.MyEvaluateActivity;
import education.zhiyuan.com.picturebooks.activity.MyMedalActivity;
import education.zhiyuan.com.picturebooks.activity.MyToReadActivity;
import education.zhiyuan.com.picturebooks.activity.MyWaterActivity;
import education.zhiyuan.com.picturebooks.activity.SettingActivity;
import education.zhiyuan.com.picturebooks.activity.UserInfoActivity;
import education.zhiyuan.com.picturebooks.bean.BalanceBean;
import education.zhiyuan.com.picturebooks.bean.MyBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CusDialog;
import education.zhiyuan.com.picturebooks.view.commodity.custom.SignDialog;

/**
 * Created by Lance on 2017/6/16.
 * Email : COCOINUT@163.com
 * Introduce :
 */

public class MineFragment extends Fragment implements HttpCallBackN {

    @BindView(R.id.iv_Head)
    ImageView ivHead;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_medal)
    TextView tvMedal;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.redPoint)
    ImageView redPoint;
    @BindView(R.id.tv_blance)
    TextView tvBlance;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.re_teacher)
    RelativeLayout reTeacher;

    Unbinder unbinder;
    private SpannableStringBuilder builder;
    private BalanceBean balanceBean;
    private MyBean myBean;
    private MyBean.DataBean.UserBean userBean;
    private MyBean.DataBean.PhaseBean phaseBean;
    private int bonus, over;  //当前水滴数，距离下一阶段所需水滴数
    private String nowMedal, futureMedal; //当前勋章，下一勋章
    private Map param, outParam;
    private CusDialog cusDialog;
    private String sign;  //签名

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, view);
        param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
        outParam = new HashMap();
        outParam.put("timestamp", System.currentTimeMillis());
        outParam.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(SharedPreferencesUtil.getInfoState(getContext(), SharedPreferencesUtil.getIdInfo(getContext())) ? "show" : "hide");
        redPoint.setVisibility(SharedPreferencesUtil.getInfoState(getContext(), SharedPreferencesUtil.getIdInfo(getContext())) ? View.VISIBLE : View.GONE);
    }

    public void initView() {
        //判断是否为老师
        if (myBean.getData().getRole() == 1) {
            reTeacher.setVisibility(View.VISIBLE);
        } else {
            reTeacher.setVisibility(View.GONE);
        }
        GlideUtils.GlideCircle(getContext(), Api.QN + userBean.getHead(), ivHead, R.drawable.default_head);
        tvName.setText(TextUtils.isEmpty(userBean.getNickname()) ? "匿名用户" : (userBean.getNickname().length() > 9 ? userBean.getNickname().substring(0, 9) + "..." : userBean.getNickname()));
        tvAge.setText(userBean.getAge());  //年龄
        String signature = userBean.getSignature(); //签名
        if (!TextUtils.isEmpty(signature)) {
            tvContent.setText(signature);
        }
        tvBlance.setText(String.format("%.0f",userBean.getBalance()));//余额
        bonus = userBean.getBonus(); //水滴
        over = myBean.getData().getPhase().getOver();
        nowMedal = phaseBean.getNow().getName();
        futureMedal = phaseBean.getFuture().getName();
        if (phaseBean.getNow().getId() == -1) {
            initText(1);  //目前还未获得勋章
        } else {
            initText(0);
        }
        if (phaseBean.getOver() == -1) {  //全部获得勋章
            initText(2);
        }
    }


    /**
     * 数据变化
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("pay")) {
            initData();
        }
        if (msg.equals("water")) {
            initData();
        }
        if (msg.contains("ivHead_")) { //修改了头像
            RequestOptions options = new RequestOptions();
            options.apply(RequestOptions.circleCropTransform());
            options.error( R.drawable.default_head);
            Glide.with(getContext())
                    .load(msg.replace("ivHead_", "").trim())
                    .apply(options)
                    .into(ivHead);
        }
        if (msg.contains("nickName_")) { //修改了昵称
            String newName = msg.replace("nickName_", "");
            tvName.setText(newName.length() > 9 ? newName.substring(0, 9) + "..." : newName);
        }
        if (msg.contains("birth")) { //修改了生日
            initData();
        }
        if (msg.equals("show")) {
            redPoint.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        String url = "/user/my";
        param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
        new MyAsyncTaskN(getActivity(), 1, url, this).execute(param);
    }

    private void initText(int flag) {
        if (flag == 0) {
            //String text = "宝贝已经有70滴水啦、获得松柏勋章，距离\n竹子勋章还差30滴水，继续加油哦!";
            String text = "宝贝已经有" + bonus + "滴水，获得\"" + nowMedal + "\",\n继续加油哦！";
            SpannableString ss = new SpannableString(text);
            int one = text.indexOf("有") + 1; //5
            int two = one + (bonus + "").length();
            ss.setSpan(new ForegroundColorSpan(Color.YELLOW), one, two, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.YELLOW), two + 7, two + 7 + nowMedal.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvMedal.setText(ss);
        } else if (flag == 1) {
            //宝贝已经有。。。水滴了，目前未获得勋章，继续加油哦！
            String text = "宝贝已经有" + bonus + "滴水，目前未获得勋章，\n继续加油哦！";
            SpannableString ss = new SpannableString(text);
            int one = text.indexOf("有") + 1;
            int one_t = one + (bonus + "").length();
            ss.setSpan(new ForegroundColorSpan(Color.YELLOW), one, one_t, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvMedal.setText(ss);
        } else {
            //String text = "宝贝已经有70滴水啦、获得松柏勋章，距离\n竹子勋章还差30滴水，继续加油哦!";
            String text = "宝贝已经有" + bonus + "滴水，获得\"" + nowMedal + "\"，\n继续加油哦！";
            SpannableString ss = new SpannableString(text);
            int one = text.indexOf("有") + 1; //5
            int two = one + (bonus + "").length();
            ss.setSpan(new ForegroundColorSpan(Color.YELLOW), one, two, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.YELLOW), two + 7, two + 7 + nowMedal.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvMedal.setText(ss);
        }
    }

    @OnClick({R.id.iv_message, R.id.iv_Head, R.id.li_signature, R.id.re_achievement, R.id.re_balance, R.id.re_data, R.id.re_water, R.id.re_read, R.id.re_bridge, R.id.re_lesson, R.id.re_evaluation, R.id.re_teacher, R.id.re_setting, R.id.re_feedback, R.id.re_about, R.id.re_out, R.id.re_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_message:
                //消息
                startActivity(new Intent(getContext(), MessageActivity.class));
                break;
            case R.id.iv_Head:
                //点击头像
                break;
            case R.id.li_signature:
                //编辑签名
                initSignDialog();
                break;
            case R.id.re_achievement:
                //我的成就
                startActivity(new Intent(getContext(), MyMedalActivity.class));
                break;
            case R.id.re_balance:
                //我的余额
                Intent intent = new Intent(getContext(), MyBalanceActivity.class);
                if (balanceBean != null) {
                    intent.putExtra("balance", userBean.getBalance() + "");
                }
                startActivity(intent);
                break;
            case R.id.re_data:
                //我的资料
                startActivity(new Intent(getContext(), UserInfoActivity.class));
                break;
            case R.id.re_water:
                //我的水滴
                startActivity(new Intent(getContext(), MyWaterActivity.class));
                break;
            case R.id.re_read:
                //我的跟读
                startActivity(new Intent(getContext(), MyToReadActivity.class));
                break;
            case R.id.re_bridge:
                //我的绘本
                startActivity(new Intent(getContext(), MyBridge.class));
                break;
            case R.id.re_lesson:
                //我的课程
                startActivity(new Intent(getContext(), MyClassActivity.class));
                break;
            case R.id.re_evaluation:
                //老师评价
                startActivity(new Intent(getContext(), MyEvaluateActivity.class));
                break;
            case R.id.re_teacher:
                //我是老师
                startActivity(new Intent(getContext(), IsTeacherActivity.class));
                break;
            case R.id.re_setting:
                //设置
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.re_feedback:
                //反馈
                startActivity(new Intent(getContext(), FeedBackActivity.class));
                break;
            case R.id.re_about:
                //关于我们
                startActivity(new Intent(getContext(), AboutUsActivity.class));
                break;
            case R.id.re_out:
                //退出
                initDialog("提示\n您确定要退出登录吗？", "取消", "确定");
                break;
            case R.id.re_service:   //跳转到拨号界面
                if (myBean != null) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + myBean.getData().getCustomerservice()));
                    startActivity(dialIntent);
                }
                break;
        }
    }

    /**
     * 签名dialog
     */
    private void initSignDialog() {
        new SignDialog(getContext(), R.style.Dialog, "签名", "请输入签名", new SignDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, EditText et) {
                if (confirm) {
                    //修改签名
                    sign = TextViewUtils.replaceBlank(et.getText().toString());
                    if (TextUtils.isEmpty(sign)) {
                        ToastUtil.showShort(getContext(), "请输入签名");
                    } else {
                        changeSign();
                    }
                }
            }
        })
                .show();

    }

    private void changeSign() {
        String signUrl = "/user/update/signature";
        Map signParam = new HashMap();
        signParam.put("timestamp", System.currentTimeMillis());
        signParam.put("token", SharedPreferencesUtil.getLoginInfo(getContext()).getToken());
        signParam.put("signature", sign);
        new MyAsyncTaskN(getActivity(), 2, signUrl, this).execute(signParam);
    }

    private void tofinish() {
        String url = "/user/logout";
        new MyAsyncTaskN(getActivity(), 0, url, this).execute(outParam);
    }

    public void initDialog(String content, String positiveStr, String negativeStr) {
        //弹出提示框
        cusDialog = new CusDialog(getContext(), R.style.Dialog, content, new CusDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (!confirm) {
                    tofinish();
                }
            }
        });
        cusDialog.setPositiveButton(positiveStr)
                .setNegativeButton(negativeStr)
                .setColor("#3179cf")
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSuccess(int type, String str) {
        switch (type) {
            case 0:
                //删除极光别名
                JPushInterface.setAlias(getContext(), //上下文对象
                        "", //空字符串表示取消之前的设置
                        new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                Log.e("alias", "login-out" + i);
                            }
                        });

                ToastUtil.showShort(getContext(), "退出成功");
                SharedPreferencesUtil.putLoginState(getContext(), false);  //修改登录状态
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
            case 1:
                myBean = new Gson().fromJson(str, MyBean.class);
                userBean = myBean.getData().getUser();
                phaseBean = myBean.getData().getPhase();
                initView();
                break;
            case 2:
                ToastUtil.showShort(getContext(), "修改签名成功");
                tvContent.setText(sign);
                break;
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showShort(getContext(), msg);
    }
}
