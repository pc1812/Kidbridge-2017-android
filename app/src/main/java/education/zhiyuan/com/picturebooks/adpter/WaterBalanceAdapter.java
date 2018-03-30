package education.zhiyuan.com.picturebooks.adpter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.BillBean;
import education.zhiyuan.com.picturebooks.utils.TimeTools;

/**
 * Created by LH on 2018/1/11.
 * Description ：水滴余额
 */

public class WaterBalanceAdapter extends BaseQuickAdapter<BillBean.DataBean, BaseViewHolder> {

    private int flag;
    private Activity activity;

    public WaterBalanceAdapter(int layoutResId, @Nullable List<BillBean.DataBean> data, Activity activity, int flag) {
        super(layoutResId, data);
        this.activity = activity;
        this.flag = flag;
    }

    @Override
    protected void convert(BaseViewHolder helper, BillBean.DataBean dataBean) {
        if (helper.getLayoutPosition() == getData().size() - 1) {
            helper.setVisible(R.id.view, false);
        }
        double fee = dataBean.getFee();
        if (fee > 0) {
            if (flag != 0) {
                helper.setText(R.id.tv_fee, "+" + ((int) fee));
            } else {
//                helper.setText(R.id.tv_fee, "+" +   new DecimalFormat("0.00").format(fee));
                helper.setText(R.id.tv_fee, "+" + ((int) fee));
            }
            helper.setTextColor(R.id.tv_fee, Color.GREEN);
        } else {
            if (fee == 0) {
                if (flag != 0) {
                    helper.setText(R.id.tv_fee, "-" + ((int) fee));
                } else {
//                    helper.setText(R.id.tv_fee, "-" +  new DecimalFormat("0.00").format(fee));
                    helper.setText(R.id.tv_fee, "-" + ((int) fee));
                }
            } else {
                if (flag != 0) {
                    helper.setText(R.id.tv_fee, "" + ((int) fee));
                } else {
                    helper.setText(R.id.tv_fee, "" + ((int) fee));
//                    helper.setText(R.id.tv_fee, "" + new DecimalFormat("0.00").format(fee));
                }
            }
            helper.setTextColor(R.id.tv_fee, Color.RED);
        }
        helper.setText(R.id.tv_time, TimeTools.dateFormat(dataBean.getCreateTime() + "", "yyyy-MM-dd\t\tHH:mm:ss"));
        //0解锁绘本，1解锁课程，2余额充值，3打赏，4被打赏，5余额兑换，积分扣除，6余额兑换，金额增加，7绘本跟读，8课程跟读
        switch (dataBean.getBillType()) {
            case 0:
                helper.setText(R.id.tv_fee, "" + ((int) fee));
                helper.setText(R.id.tv_detail, "绘本解锁");
                break;
            case 1:
                helper.setText(R.id.tv_fee, "" + ((int) fee));
                helper.setText(R.id.tv_detail, "课程解锁");
                break;
            case 2:
                helper.setText(R.id.tv_detail, "H币充值");
                break;
            case 3:
                helper.setText(R.id.tv_detail, "打赏");
                break;
            case 4:
                helper.setText(R.id.tv_detail, "被打赏");
                break;
            case 5:
                helper.setText(R.id.tv_detail, "H币兑换");
                break;
            case 6:
                helper.setText(R.id.tv_detail, "H币兑换");
                break;
            case 7:
                helper.setText(R.id.tv_detail, "绘本跟读");
                break;
            case 8:
                helper.setText(R.id.tv_detail, "课程跟读");
                break;
            case 9:
                helper.setText(R.id.tv_detail, "系统赠送");
                break;
            case 10:
                helper.setText(R.id.tv_detail, "系统扣除");
                break;
            case 11:
                helper.setText(R.id.tv_detail, "H币充值");
                break;
            case 12:
                helper.setText(R.id.tv_detail, "跟读分享");
                break;
            case 13:
                helper.setText(R.id.tv_detail, "跟读分享");
                break;
        }
    }
}