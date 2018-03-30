package education.zhiyuan.com.picturebooks.adpter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.RepeatRankBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.TimeTools;

/**
 * Created by LH on 2018/1/11.
 * Description ：绘本详情-跟读榜
 */

public class BridgeRankAdapter extends BaseQuickAdapter<RepeatRankBean.DataBean.RankBean,BaseViewHolder> {

    public BridgeRankAdapter(int layoutResId, @Nullable List<RepeatRankBean.DataBean.RankBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepeatRankBean.DataBean.RankBean item) {
        GlideUtils.GlideCircle(MyApp.getInstance(),Api.QN + item.getUserBook().getUser().getHead(),(ImageView) helper.getView(R.id.iv_head),R.mipmap.iv_login_logo);
        if (TextUtils.isEmpty(item.getUserBook().getUser().getNickname())) {
            helper.setText(R.id.name, "匿名用户");
        } else {
            helper.setText(R.id.name, item.getUserBook().getUser().getNickname());
        }
        helper.setText(R.id.admire_count, item.getLike() + "");
        helper.setText(R.id.time, TimeTools.getStrTimeS(item.getCreateTime() + ""));
    }
}