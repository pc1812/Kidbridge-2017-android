package education.zhiyuan.com.picturebooks.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.bean.MyAchievement;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * 我的成就  /user//medal/v2
 */
public class MyMedalActivity extends BaseActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.medal_recyclerView)
    RecyclerView medalRecyclerView;
    @BindView(R.id.tv_num)
    TextView tvNum;
    private Dialog pBar;
    private MyAchievement myAchievement;
    private List<MyAchievement.DataBean.MedalListBean> medalListBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_my_medal);
    }

    @Override
    protected void initData() {
        pBar = new Dialog(MyMedalActivity.this, R.style.Dialog);
        pBar.setContentView(R.layout.progress);
        if (!isFinishing()) {
            pBar.show();
        }
        String url = "/user/medal/v2";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        new MyAsyncTaskN(MyMedalActivity.this, 0, url, this).execute(param);
    }

    @Override
    protected void initView() {
        tvTitle.setText("勋章墙");
        medalListBeen = new ArrayList<>();
        initRecyclerView();
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
                if (medalListBean.getBonus() <= myAchievement.getData().getBonus()) {  //获得
                    Glide.with(getApplicationContext()).load(Api.QN + medalListBean.getIcon().getActive()).into((ImageView) holder.getView(R.id.iv_zx));
                } else {
                    Glide.with(getApplicationContext()).load(Api.QN + medalListBean.getIcon().getQuiet()).into((ImageView) holder.getView(R.id.iv_zx));
                }
                //勋章
                holder.setText(R.id.tv_name, medalListBean.getName());
                holder.setText(R.id.tv_num, medalListBean.getBonus() + "");
            }
        });
    }


    @Override
    public void onSuccess(int type, String str) {
        dis();
        myAchievement = new Gson().fromJson(str, MyAchievement.class);
        medalListBeen.addAll(myAchievement.getData().getMedalList());
        if (medalRecyclerView.getAdapter() != null) {
            medalRecyclerView.getAdapter().notifyDataSetChanged();
        }
        tvNum.setText(myAchievement.getData().getBonus() + "");
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

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
