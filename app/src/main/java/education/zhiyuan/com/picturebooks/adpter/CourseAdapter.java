package education.zhiyuan.com.picturebooks.adpter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import education.zhiyuan.com.picturebooks.MyApp;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.MyLessonBean;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;

/**
 * Created by LH on 2018/1/11.
 * Description ：hahah
 */

public class CourseAdapter extends BaseQuickAdapter<MyLessonBean.DataBean, BaseViewHolder> {
    private Map<Integer, String> fitMap;
    private int flag = -1;

    public CourseAdapter(int layoutResId, @Nullable List<MyLessonBean.DataBean> data, int flag) {
        super(layoutResId, data);
        this.flag = flag;
        fitMap = new HashMap<>();
        fitMap.put(0, "3-5岁");
        fitMap.put(1, "6-8岁");
        fitMap.put(2, "9-12岁");
        fitMap.put(3, "4-7岁");
        fitMap.put(4, "8-10岁");
    }

    @Override
    protected void convert(BaseViewHolder holder, MyLessonBean.DataBean dataBean) {
        GlideUtils.GlideNormal(MyApp.getInstance(), Api.QN + dataBean.getIcon().get(0), (ImageView) holder.getView(R.id.iv_today_left), R.drawable.iv_replace_les);
        if (flag == 0) {
            holder.setVisible(R.id.iv_charge, false);
        } else {
            holder.setVisible(R.id.iv_charge, dataBean.getLock() == 0);
        }

        holder.setText(R.id.tv_lessonName, dataBean.getName());
        holder.setText(R.id.tv_fit, fitMap.get(dataBean.getFit()));
        if (dataBean.getStatus() == 0) {  //，0：未开课(报名中)，1：已开课，2：已结束
            holder.setText(R.id.tv_lessonFlag, "未开课");
        } else if (dataBean.getStatus() == 1) {
            holder.setText(R.id.tv_lessonFlag, "已开课");
        } else {
            holder.setText(R.id.tv_lessonFlag, "已结束");
        }
        holder.setText(R.id.tv_cycle, dataBean.getCycle() + "天");
        LinearLayout ll = holder.getView(R.id.ll_tag);  //标签
        ll.removeAllViews();
        if (dataBean.getTag().size() > 0) {
            for (int i = 0; i < 1; i++) {  // dataBean.getTag().size()
                View view = LayoutInflater.from(MyApp.getInstance()).inflate(R.layout.item_lesson_tag, null);
                TextView textView = view.findViewById(R.id.tv_tag);
                textView.setText(dataBean.getTag().get(i));
                ll.addView(view);
            }
        }
        holder.setText(R.id.price, "¥" + dataBean.getPrice() + "");
    }
}