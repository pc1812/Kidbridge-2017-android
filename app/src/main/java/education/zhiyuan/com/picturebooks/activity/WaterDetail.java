package education.zhiyuan.com.picturebooks.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.adpter.WaterBalanceAdapter;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.bean.BillBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;

/**
 * Created by Lance on 2017/6/22.
 * Email : COCOINUT@163.com
 * Introduce : 水滴/余额 明细
 */

public class WaterDetail extends BaseActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    List<String> list = new ArrayList<>();
    private int flag;

    private List<BillBean.DataBean> dataBeanList;
    private View emptyView;
    private WaterBalanceAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDate();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.layout_water_detail);
    }

    @Override
    protected void initData() {
        flag = getIntent().getFlags();
        if (flag == 0) {
            tvTitle.setText("H币明细");
        } else {
            tvTitle.setText("水滴明细");
        }
        dataBeanList = new ArrayList<>();
        emptyView=getLayoutInflater().inflate(R.layout.view_empty,null);
        adapter=new WaterBalanceAdapter(R.layout.item_water_detail, dataBeanList,this,flag);
        adapter.setEmptyView(emptyView);
    }

    @Override
    protected void initView() {
        tvRight.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 获取数据
     */
    private void getDate() {
        //传递过来的flag与feeType 一致，，0:余额，1:水滴
        String balanceUrl = "/bill/list";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("feeType", flag);
        new MyAsyncTaskN(WaterDetail.this, 0, balanceUrl, this).execute(param);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onSuccess(int type, String str) {
        BillBean billBean = new Gson().fromJson(str, BillBean.class);
        if (billBean.getData().size() > 0) {
            adapter.addData(billBean.getData());
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showShort(getApplicationContext(), msg);
    }
}
