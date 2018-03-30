package education.zhiyuan.com.picturebooks.view.commodity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;

import org.joda.time.DateTime;

import java.util.Iterator;
import java.util.List;

import education.zhiyuan.com.picturebooks.clicklisten.OnClickMonthViewListener;
import education.zhiyuan.com.picturebooks.utils.RUtils;


public class MonthView extends CalendarView {

    private List<DateTime> monthDateTimeList;
    private List<String> lunarList;
    private List<String> localDateList;
    private OnClickMonthViewListener onClickMonthViewListener;

    private Paint selectPaint;//选中的边框画笔
    private Paint pushPaint;//打卡的画笔

    public MonthView(Context mContext, DateTime dateTime, OnClickMonthViewListener onClickMonthViewListener, List<String> pointList) {
        super(mContext, pointList);
        this.mInitialDateTime = dateTime;
        this.onClickMonthViewListener = onClickMonthViewListener;

        RUtils.NCalendar monthCalendar = RUtils.getMonthCalendar(dateTime);
        lunarList = monthCalendar.lunarList;
        localDateList = monthCalendar.localDateList;
        monthDateTimeList = monthCalendar.dateTimeList;
        initPaint();
    }

    //初始化画笔对象
    private void initPaint() {
        selectPaint = new Paint();
        selectPaint.setColor(mSelectCircleColor);
        selectPaint.setAntiAlias(true);
        selectPaint.setStyle(Paint.Style.STROKE);
        selectPaint.setStrokeWidth(mHollowCircleStroke);
        selectPaint.setTextAlign(Paint.Align.CENTER);

        pushPaint = new Paint();
        pushPaint.setColor(mPushCardColor);
        pushPaint.setStyle(Paint.Style.STROKE);
        pushPaint.setAntiAlias(true);
        pushPaint.setTextSize(mSolarTextSize);
        pushPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        mRectList.clear();
        int a = monthDateTimeList.size() / 7;
        //6行7列
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < 7; j++) {
                Rect rect = new Rect(j * mWidth / 7, i * mHeight / a, j * mWidth / 7 + mWidth / 7, i * mHeight / a + mHeight / a);
                RectF rectF = new RectF(j * mWidth / 7, i * mHeight / a, j * mWidth / 7 + mWidth / 7, i * mHeight / a + mHeight / a);
                mRectList.add(rect);
                DateTime dateTime = monthDateTimeList.get(i * 7 + j);
                Paint.FontMetricsInt fontMetrics = mSorlarPaint.getFontMetricsInt();
                int baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
                //当月和上下月的颜色不同
                if (RUtils.isEqualsMonth(dateTime, mInitialDateTime)) {
                    //当天和选中的日期不绘制农历 TODO  当天
                    if (RUtils.isToday(dateTime)) {
                        mSorlarPaint.setColor(mSelectCircleColor);
                        int radius = 15;
                        canvas.drawRoundRect(rectF, radius, radius, mSorlarPaint);
                        mSorlarPaint.setColor(Color.WHITE);
                        canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                    } else if (mSelectDateTime != null && dateTime.toLocalDate().equals(mSelectDateTime.toLocalDate())) {
                        RectF rectF2 = new RectF(j * mWidth / 7 + 1, i * mHeight / a + 1, j * mWidth / 7 + mWidth / 7 - 1, i * mHeight / a + mHeight / a - 1);
                        int radius = 15;
                        canvas.drawRoundRect(rectF2, radius, radius, selectPaint);
                        mSorlarPaint.setColor(mSolarTextColor);
                        canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                    } else {
                        mSorlarPaint.setColor(mSolarTextColor);
                        canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                        if (mPushDateTimes != null) {
                            for (int k = 0; k < mPushDateTimes.size(); k++) {
                                if (dateTime.toLocalDate().equals(mPushDateTimes.get(k).toLocalDate())) {
                                    DateTime pushDay = mPushDateTimes.get(k);
                                    canvas.drawText(pushDay.getDayOfMonth() + "", rect.centerX(), baseline, pushPaint);
                                }
                            }
                        }
                        if (mChangeTime != null) {
                            Iterator<DateTime> iter = mChangeTime.keySet().iterator();
                            while (iter.hasNext()) {
                                DateTime key = iter.next();
                                int value = mChangeTime.get(key);
                                if (key.toLocalDate().equals(dateTime.toLocalDate())) {
                                    if (value == 0) { //未到时间
                                        pushPaint.setColor(Color.BLACK);
                                    }
                                    if (value == 1) { //已打卡
                                        pushPaint.setColor(Color.parseColor("#15CF30"));
                                    }
                                    if (value == -1) { //未打卡
                                        pushPaint.setColor(mPushCardColor);
                                    }
                                    canvas.drawText(key.getDayOfMonth() + "", rect.centerX(), baseline, pushPaint);
                                }
                            }
                        }
                    }
                } else {
                    mSorlarPaint.setColor(mHintColor);
                    canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                }
                if (mPointList.contains(dateTime.toLocalDate().toString())) {
                    mSorlarPaint.setColor(mPointColor);
                    canvas.drawCircle(rect.centerX(), rect.bottom - mPointSize, mPointSize, mSorlarPaint);
                }
            }
        }
    }

    /**
     * 绘制农历
     *
     * @param canvas
     * @param rect
     * @param i
     * @param j
     */
    private void drawLunar(Canvas canvas, Rect rect, int color, int i, int j) {
        if (isShowLunar) {
            mLunarPaint.setColor(color);
            String lunar = lunarList.get(i * 7 + j);
            canvas.drawText(lunar, rect.centerX(), rect.bottom - RUtils.dp2px(getContext(), 5), mLunarPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    private GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            for (int i = 0; i < mRectList.size(); i++) {
                Rect rect = mRectList.get(i);
                if (rect.contains((int) e.getX(), (int) e.getY())) {
                    DateTime selectDateTime = monthDateTimeList.get(i);
                    if (RUtils.isLastMonth(selectDateTime, mInitialDateTime)) {
                        onClickMonthViewListener.onClickLastMonth(selectDateTime);
                    } else if (RUtils.isNextMonth(selectDateTime, mInitialDateTime)) {
                        onClickMonthViewListener.onClickNextMonth(selectDateTime);
                    } else {
                        onClickMonthViewListener.onClickCurrentMonth(selectDateTime);
                    }
                    break;
                }
            }
            return true;
        }
    });


    //选中的是一月中的第几周
    public int getWeekRow(DateTime dateTime) {
        int indexOf = localDateList.indexOf(dateTime.toLocalDate().toString());
        return indexOf / 7;
    }
}
