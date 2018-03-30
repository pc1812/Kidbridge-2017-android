package education.zhiyuan.com.picturebooks.adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Map;

import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.SecordRecordBean;

/**
 * Created by LH on 2018/1/11.
 * Description ：搜索记录
 */

public class SearchRecordAdapter extends BaseQuickAdapter<SecordRecordBean, BaseViewHolder> {

    private Map<Integer, String> fitMap;

    public SearchRecordAdapter(int layoutResId, @Nullable List<SecordRecordBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holder, SecordRecordBean secordRecordBean) {
        holder.setText(R.id.tv, secordRecordBean.getContent());

    }
}