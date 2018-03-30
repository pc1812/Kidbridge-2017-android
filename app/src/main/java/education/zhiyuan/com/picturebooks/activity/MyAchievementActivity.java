package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.MyAchievement;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * 我的成就
 */
public class MyAchievementActivity extends AppCompatActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.medal_recyclerView)
    RecyclerView medalRecyclerView;
    @BindView(R.id.ll_model)
    LinearLayout llModel;

    private int width;
    private MyAchievement myAchievement;
    private List<MyAchievement.DataBean.MedalListBean> medalListBeen;
    private int[] medal = {R.id.iv_medal_one, R.id.iv_medal_two, R.id.iv_medal_three, R.id.iv_medal_four, R.id.iv_medal_five};
    private int[] pro = {R.id.pb_medal_one, R.id.pb_medal_two, R.id.pb_medal_three, R.id.pb_medal_four};
    private int[] num = {R.id.tv_pro_one, R.id.tv_pro_two, R.id.tv_pro_three, R.id.tv_pro_four};

    private int pos, progress;

    private List<Integer> listPos = new ArrayList<>();
    private Dialog pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_achievement);
        ButterKnife.bind(this);
        tvTitle.setText("我的成就");
        tvRight.setVisibility(View.INVISIBLE);
        WindowManager wm = (WindowManager) MyAchievementActivity.this.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        medalListBeen = new ArrayList<>();
        initRecyclerView();
        getData();
    }

    private void getData() {
        pBar = new Dialog(MyAchievementActivity.this, R.style.Dialog);
        pBar.setContentView(R.layout.progress);
        if (!isFinishing()) {
            pBar.show();
        }
      //  /user/medal/v2    /user/medal
        String url = "/user/medal/v2";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        new MyAsyncTaskN(MyAchievementActivity.this, 0, url, this).execute(param);
    }

    private void initRecyclerView() {
        medalRecyclerView.setLayoutManager(new GridLayoutManager(this, 3) {
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                if (getChildCount() > 0) {
                    View firstChildView = recycler.getViewForPosition(0);
                    measureChild(firstChildView, widthSpec, heightSpec);
                    setMeasuredDimension(View.MeasureSpec.getSize(widthSpec), firstChildView.getMeasuredHeight() * 2);
                } else {
                    super.onMeasure(recycler, state, widthSpec, heightSpec);
                }
            }
        });
        medalRecyclerView.setAdapter(new CommonAdapter<MyAchievement.DataBean.MedalListBean>(getApplicationContext(), R.layout.item_achievement_xz, medalListBeen) {
            @Override
            protected void convert(ViewHolder holder, MyAchievement.DataBean.MedalListBean medalListBean, int position) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width / 3, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams lp_i = new LinearLayout.LayoutParams(width / 3, width / 4);
                holder.getView(R.id.iv_zx).setLayoutParams(lp_i);
                holder.getView(R.id.ll_achieve).setLayoutParams(lp);
                ImageView iv = (ImageView) findViewById(medal[position]); //上面的勋章图片
                if (medalListBean.getBonus() <= myAchievement.getData().getBonus()) {  //获得
                    listPos.add(position);
                    iv.setImageResource(R.drawable.iv_xzget);
                    Glide.with(getApplicationContext()).load(Api.QN + medalListBean.getIcon().getActive()).into((ImageView) holder.getView(R.id.iv_zx));
                } else {
                    iv.setImageResource(R.drawable.iv_xzno_gray);
                    Glide.with(getApplicationContext()).load(Api.QN + medalListBean.getIcon().getQuiet()).into((ImageView) holder.getView(R.id.iv_zx));
                }
                if (position == medalListBeen.size() - 1) {
                    handler.sendEmptyMessage(1);
                }
                //勋章
                holder.setText(R.id.tv_name, medalListBean.getName());
                holder.setText(R.id.tv_num, medalListBean.getBonus() + "");
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //pos 获得水滴的pos
            if (listPos.size() > 0) {
                pos = listPos.get(listPos.size() - 1);
                initTop();
            }
        }
    };

    public void initTop() {
        if (pos == medalListBeen.size() - 1) {// TODO 全部获得 0.1.2,3,4
            for (int i = 0; i < pro.length; i++) {
                ProgressBar pb = (ProgressBar) findViewById(pro[i]);
                pb.setProgress(100);
            }
            TextView tv = (TextView) findViewById(num[pos - 1]);
            tv.setVisibility(View.VISIBLE);
            tv.setText(myAchievement.getData().getBonus() + "");
        } else if (pos >= 0 && pos < medalListBeen.size() - 1) {  //获得0-多个
            for (int i = 0; i < pos; i++) {
                ProgressBar pb = (ProgressBar) findViewById(pro[i]);
                pb.setProgress(100);
            }
            ProgressBar pb = (ProgressBar) findViewById(pro[pos]);
            double result = ((myAchievement.getData().getBonus() - medalListBeen.get(pos).getBonus()) * 1.0)
                    / (medalListBeen.get(pos + 1).getBonus() - medalListBeen.get(pos).getBonus()) * 100;
            progress = Integer.valueOf((result + "").substring(0, (result + "").indexOf(".")));
            pb.setProgress(progress);
            TextView tv = (TextView) findViewById(num[pos]);
            tv.setVisibility(View.VISIBLE);
            tv.setText(myAchievement.getData().getBonus() + "");
        }

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onSuccess(int type, String str) {
        dis();
        myAchievement = new Gson().fromJson(str, MyAchievement.class);
        medalListBeen.addAll(myAchievement.getData().getMedalList());
        if (medalRecyclerView.getAdapter() != null) {
            medalRecyclerView.getAdapter().notifyDataSetChanged();
        }
        for (int i = 0; i < llModel.getChildCount(); i++) {
            TextView tv = (TextView) llModel.getChildAt(i);
            tv.setText(myAchievement.getData().getMedalList().get(i).getName());
        }
    }

    @Override
    public void onError(String msg) {
        dis();
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dis();
    }

    public void dis() {
        if (pBar != null) {
            if (pBar.isShowing()) {
                pBar.dismiss();
            }
        }
    }
}
