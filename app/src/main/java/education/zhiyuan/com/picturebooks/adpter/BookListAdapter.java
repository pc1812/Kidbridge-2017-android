package education.zhiyuan.com.picturebooks.adpter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.BookListBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;

/**
 * Created by LH on 2018/1/11.
 * Description ：书单列表
 */

public class BookListAdapter extends BaseQuickAdapter<BookListBean.DataBean, BaseViewHolder> {

    public BookListAdapter(int layoutResId, @Nullable List<BookListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BookListBean.DataBean dataBean) {
        holder.setVisible(R.id.iv_charge, false);
        GlideUtils.GlideNormal(MyApp.getInstance(), Api.QN + dataBean.getIcon(), (ImageView) holder.getView(R.id.iv_today_left), R.drawable.iv_replace_hb);
        holder.setText(R.id.tv_today_left, dataBean.getName());
        LinearLayout ll = holder.getView(R.id.ll_tag);  //标签
        ll.removeAllViews();
        if (dataBean.getTag().size() > 0) {
            for (int i = 0; i < 1; i++) {  //dataBean.getTag().size()
                View view = LayoutInflater.from(MyApp.getInstance()).inflate(R.layout.item_lesson_tag, null);
                TextView textView = view.findViewById(R.id.tv_tag);
                textView.setText(dataBean.getTag().get(i));
                ll.addView(view);
            }
        }
        holder.setVisible(R.id.tv_today_mid, false);
        if (dataBean.getFit() == 0) {
            holder.setText(R.id.tv_today_left_age, "3-5岁");
        } else if (dataBean.getFit() == 1) {
            holder.setText(R.id.tv_today_left_age, "6-8岁");
        } else if (dataBean.getFit()==2){
            holder.setText(R.id.tv_today_left_age, "9-12岁");
        }else if (dataBean.getFit()==3){
            holder.setText(R.id.tv_today_left_age, "4-7岁");
        }else if (dataBean.getFit()==4){
            holder.setText(R.id.tv_today_left_age, "8-10岁");
        }
    }
}