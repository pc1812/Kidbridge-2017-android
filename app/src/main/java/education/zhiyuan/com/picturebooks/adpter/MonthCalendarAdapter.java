package education.zhiyuan.com.picturebooks.adpter;

import android.content.Context;
import android.view.ViewGroup;

import org.joda.time.DateTime;

import java.util.List;

import education.zhiyuan.com.picturebooks.clicklisten.OnClickMonthViewListener;
import education.zhiyuan.com.picturebooks.view.commodity.MonthView;

/**
 * Created by necer on 2017/6/12.
 */

public class MonthCalendarAdapter extends CalendarAdapter {

    private OnClickMonthViewListener mOnClickMonthViewListener;

    public MonthCalendarAdapter(Context context, int count, int curr, DateTime dateTime, OnClickMonthViewListener onClickMonthViewListener, List<String> pointList) {
        super(context, count, curr, dateTime, pointList);
        this.mOnClickMonthViewListener = onClickMonthViewListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MonthView monthView = (MonthView) mCalendarViews.get(position);
        if (monthView == null) {
            int i = position - mCurr;
            DateTime dateTime = this.mDateTime.plusMonths(i);
            monthView = new MonthView(mContext, dateTime, mOnClickMonthViewListener, mPointList);
            mCalendarViews.put(position, monthView);
        }
        container.addView(monthView);
        return monthView;
    }
}
