package education.zhiyuan.com.picturebooks.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.adpter.SearchAdapter;
import education.zhiyuan.com.picturebooks.adpter.SearchRecordAdapter;
import education.zhiyuan.com.picturebooks.base.BaseActivity;
import education.zhiyuan.com.picturebooks.bean.SearchBean;
import education.zhiyuan.com.picturebooks.bean.SecordRecordBean;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.CustomLoadMoreView;

/**
 * 搜索界面
 */
public class SearchActivity extends BaseActivity implements HttpCallBackN {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.re_search)
    RelativeLayout reSearch;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.ll_record)
    LinearLayout llRecord;
    @BindView(R.id.search_recyclerView)
    RecyclerView searchRecyclerView;
    @BindView(R.id.recyclerView_record)
    RecyclerView recyclerViewRecord;

    private String searchContent;
    private List<SearchBean.DataBean> dataBeanList;
    private int offset = 0, limit = 10;
    private SearchAdapter adapter;
    private View emptyView;
    private TextView tvMsg;

    private List<SecordRecordBean> recordList;
    private SearchRecordAdapter recordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void onResume() {
        super.onResume();
        show();
    }

    @Override
    protected void initData() {
        //搜索结果
        dataBeanList = new ArrayList<>();
        emptyView = getLayoutInflater().inflate(R.layout.view_empty, null);
        tvMsg = emptyView.findViewById(R.id.tv);
        adapter = new SearchAdapter(R.layout.item_card_books, dataBeanList);
        adapter.setEmptyView(emptyView);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(dataBeanList.size(), limit);
            }
        }, searchRecyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getApplicationContext(), BridgeDetail.class);
                intent.putExtra("id", dataBeanList.get(position).getId());
                intent.putExtra("name", dataBeanList.get(position).getName());
                if (dataBeanList.get(position).getPrice() > 0) {
                    intent.putExtra("isFree", false);
                } else {
                    intent.putExtra("isFree", true);
                }
                startActivity(intent);
            }
        });
        searchRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        searchRecyclerView.setAdapter(adapter);

        //搜索记录
        LitePal.initialize(this);
        LitePal.getDatabase();
        recordList = DataSupport.order("id desc").limit(6).find(SecordRecordBean.class);
        tvClear.setVisibility(recordList.size() > 0 ? View.VISIBLE : View.GONE);
        recordAdapter = new SearchRecordAdapter(R.layout.item_search_record, recordList);
        recyclerViewRecord.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRecord.setAdapter(recordAdapter);
        recordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                hide();
                llRecord.setVisibility(View.GONE);
                etContent.setText(recordList.get(position).getContent());
                searchContent = recordList.get(position).getContent();
                dataBeanList.clear();
                getData(offset, limit);
            }
        });
    }

    @Override
    protected void initView() {
        tvTitle.setText("绘本馆");
        tvRight.setVisibility(View.INVISIBLE);
        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchContent = etContent.getText().toString();
                    if (!TextUtils.isEmpty(searchContent)) {
                        hide();
                        dataBeanList.clear();
                        boolean isHave = false;
                        for (int i = 0; i < recordList.size(); i++) {
                            if (recordList.get(i).getContent().equals(etContent.getText().toString())) {
                                isHave = true;
                            }
                        }
                        if (!isHave) {
                            SecordRecordBean recordBean = new SecordRecordBean(System.currentTimeMillis(), etContent.getText().toString());
                            recordBean.save();
                        }
                        llRecord.setVisibility(View.GONE);
                        getData(offset, limit);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void hide() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
        }
    }

    public void show() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(etContent, InputMethodManager.SHOW_FORCED);
        }
    }


    private void getData(int offset, int limit) {
        String url = "/book/search";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        param.put("keyword", searchContent);
        param.put("offset", offset);
        param.put("limit", limit);
        new MyAsyncTaskN(SearchActivity.this, 0, url, this).execute(param);
    }

    @Override
    public void onSuccess(int type, String str) {
        SearchBean searchBean = new Gson().fromJson(str, SearchBean.class);
        if (searchBean.getData().size() > 0) {
            adapter.addData(searchBean.getData());
            adapter.loadMoreComplete();
        } else {
            adapter.loadMoreEnd();
            if (dataBeanList.size() == 0) {
                tvMsg.setText("没有找到与“" + searchContent + "”相关的内容~~~");
            }
        }
    }

    @Override
    public void onError(String msg) {
        adapter.loadMoreFail();
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    @OnClick({R.id.iv_back, R.id.iv_delete, R.id.tv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_delete:
                recordList.clear();
                recordList.addAll(DataSupport.order("id desc").limit(6).find(SecordRecordBean.class));
                recordAdapter.notifyDataSetChanged();
                etContent.setText("");
                llRecord.setVisibility(View.VISIBLE);
                tvClear.setVisibility(recordList.size() > 0 ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_clear:
                DataSupport.deleteAll(SecordRecordBean.class);
                recordList.clear();
                tvClear.setVisibility(View.GONE);
                recordAdapter.notifyDataSetChanged();
                break;
        }
    }
}
