package education.zhiyuan.com.picturebooks.utils;

import android.content.Context;
import android.util.TypedValue;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by necer on 2017/6/9.
 */

public class RUtils {


    /**
     * dp转px
     *
     * @param context
     * @param
     * @return
     */
    public static float dp2px(Context context, int dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static float sp2px(Context context, float spVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }


    /**
     * 屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getDisplayWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 屏幕高度
     *
     * @param context
     * @return
     */
    public static int getDisplayHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    //是否同月
    public static boolean isEqualsMonth(DateTime dateTime1, DateTime dateTime2) {
        return dateTime1.getMonthOfYear() == dateTime2.getMonthOfYear();
    }


    /**
     * 第一个是不是第二个的上一个月,只在此处有效
     *
     * @param dateTime1
     * @param dateTime2
     * @return
     */
    public static boolean isLastMonth(DateTime dateTime1, DateTime dateTime2) {
        DateTime dateTime = dateTime2.plusMonths(-1);
        return dateTime1.getMonthOfYear() == dateTime.getMonthOfYear();
    }


    /**
     * 第一个是不是第二个的下一个月，只在此处有效
     *
     * @param dateTime1
     * @param dateTime2
     * @return
     */
    public static boolean isNextMonth(DateTime dateTime1, DateTime dateTime2) {
        DateTime dateTime = dateTime2.plusMonths(1);
        return dateTime1.getMonthOfYear() == dateTime.getMonthOfYear();
    }


    /**
     * 获得两个日期距离几个月
     *
     * @return
     */
    public static int getIntervalMonths(DateTime dateTime1, DateTime dateTime2) {
        return (dateTime2.getYear() - dateTime1.getYear()) * 12 + (dateTime2.getMonthOfYear() - dateTime1.getMonthOfYear());
    }

    /**
     * 获得两个日期距离几周
     *
     * @param dateTime1
     * @param dateTime2
     * @return
     */
    public static int getIntervalWeek(DateTime dateTime1, DateTime dateTime2) {
        DateTime sunFirstDayOfWeek1 = getSunFirstDayOfWeek(dateTime1);
        DateTime sunFirstDayOfWeek2 = getSunFirstDayOfWeek(dateTime2);
        int days = Days.daysBetween(sunFirstDayOfWeek1, sunFirstDayOfWeek2).getDays();
        if (days > 0) {
            return (days + 1) / 7;
        } else if (days < 0) {
            return (days - 1) / 7;
        } else {
            return days;
        }
    }


    /**
     * 是否是今天
     *
     * @param dateTime
     * @return
     */
    public static boolean isToday(DateTime dateTime) {
        return new DateTime().toLocalDate().equals(dateTime.toLocalDate());
    }

    public static NCalendar getMonthCalendar(DateTime dateTime) {
        DateTime lastMonthDateTime = dateTime.plusMonths(-1);//上个月
        DateTime nextMonthDateTime = dateTime.plusMonths(1);//下个月

        int firstDayOfWeek = getFirstDayOfWeekOfMonth(dateTime.getYear(), dateTime.getMonthOfYear());//该月的第一天是星期几
        int lastMonthDays = lastMonthDateTime.dayOfMonth().getMaximumValue();//上月天数
        int days = dateTime.dayOfMonth().getMaximumValue();//当月天数

        NCalendar nCalendar = new NCalendar();
        List<DateTime> dateTimeList = new ArrayList<>();
        List<String> lunarList = new ArrayList<>();
        List<String> localDateList = new ArrayList<>();
        int j = 1;
        int i = 0;
        while (true){
            DateTime dateTime1 = null;
            if (i < firstDayOfWeek) { // 前一个月
                int temp = lastMonthDays - firstDayOfWeek + 1;
                dateTime1 = new DateTime(lastMonthDateTime.getYear(), lastMonthDateTime.getMonthOfYear(), (temp + i), 0, 0, 0);
            } else if (i < days + firstDayOfWeek) { // 本月
                dateTime1 = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), (i - firstDayOfWeek + 1), 0, 0, 0);
            } else { // 下一个月
                if (i%7==0){
                    break;
                }
                dateTime1 = new DateTime(nextMonthDateTime.getYear(), nextMonthDateTime.getMonthOfYear(), j, 0, 0, 0);
                j++;
            }
            dateTimeList.add(dateTime1);
            localDateList.add(dateTime1.toLocalDate().toString());
            LunarCalendarUtils.Lunar lunar = LunarCalendarUtils.solarToLunar(new LunarCalendarUtils.Solar(dateTime1.getYear(), dateTime1.getMonthOfYear(), dateTime1.getDayOfMonth()));
            String lunarDayString = LunarCalendarUtils.getLunarDayString(dateTime1.getYear(), dateTime1.getMonthOfYear(), dateTime1.getDayOfMonth(), lunar.lunarYear, lunar.lunarMonth, lunar.lunarDay, lunar.isLeap);
            lunarList.add(lunarDayString);
            i++;
        }

        nCalendar.dateTimeList = dateTimeList;
        nCalendar.localDateList = localDateList;
        nCalendar.lunarList = lunarList;
        return nCalendar;
    }


    /**
     * @param dateTime 今天
     * @param type     0，周日，1周一
     * @return
     */
    public static List<DateTime> getMonthCalendar2(DateTime dateTime, int type) {

        DateTime lastMonthDateTime = dateTime.plusMonths(-1);//上个月
        DateTime nextMonthDateTime = dateTime.plusMonths(1);//下个月

        int days = dateTime.dayOfMonth().getMaximumValue();//当月天数
        int lastMonthDays = lastMonthDateTime.dayOfMonth().getMaximumValue();//上个月的天数
        //    int nextMonthDays = nextMonthDateTime.dayOfMonth().getMaximumValue();//下个月个月的天数


        int firstDayOfWeek = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), 1, 0, 0, 0).getDayOfWeek();//当月第一天周几

        int endDayOfWeek = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), days, 0, 0, 0).getDayOfWeek();//当月最后一天周几

        List<DateTime> dateTimes = new ArrayList<>();

        //周日开始的
        if (type == 0) {
            //上个月
            if (firstDayOfWeek != 7) {
                for (int i = 0; i < firstDayOfWeek; i++) {
                    // dadafa.add(lastMonthDays - (firstDayOfWeek - i - 1) + "");
                    dateTimes.add(new DateTime(lastMonthDateTime.getYear(), lastMonthDateTime.getMonthOfYear(), lastMonthDays - (firstDayOfWeek - i - 1), 0, 0, 0));
                }
            }
            //当月
            for (int i = 0; i < days; i++) {
                // dadafa.add((i + 1) + "");
                dateTimes.add(new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), i + 1, 0, 0, 0));
            }
            //下个月
            if (endDayOfWeek == 7) {
                endDayOfWeek = 0;
            }
            for (int i = 0; i < 6 - endDayOfWeek; i++) {
                //dadafa.add((i + 1) + "");
                dateTimes.add(new DateTime(nextMonthDateTime.getYear(), nextMonthDateTime.getMonthOfYear(), i + 1, 0, 0, 0));
            }
        } else {
            //周一开始的
            for (int i = 0; i < firstDayOfWeek - 1; i++) {
                // dadafa.add(lastMonthDays - (firstDayOfWeek - i -2) + "");
                dateTimes.add(new DateTime(lastMonthDateTime.getYear(), lastMonthDateTime.getMonthOfYear(), lastMonthDays - (firstDayOfWeek - i - 2), 0, 0, 0));
            }
            for (int i = 0; i < days; i++) {
                //dadafa.add((i + 1) + "");
                dateTimes.add(new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), i + 1, 0, 0, 0));
            }
            for (int i = 0; i < 7 - endDayOfWeek; i++) {
                //dadafa.add((i + 1) + "");
                dateTimes.add(new DateTime(nextMonthDateTime.getYear(), nextMonthDateTime.getMonthOfYear(), i + 1, 0, 0, 0));
            }
        }
        return dateTimes;

    }


    /**
     * 某月第一天是周几
     *
     * @return
     */
    public static int getFirstDayOfWeekOfMonth(int year, int month) {
        int dayOfWeek = new DateTime(year, month, 1, 0, 0, 0).getDayOfWeek();
        if (dayOfWeek == 7) {
            return 0;
        }
        return dayOfWeek;
    }
/*    */

    /**
     * 某月第一天是周几
     *
     * @return
     *//*
    public static int getFirstDayOfWeekOfMonth2(int year, int month) {
        int dayOfWeek = new DateTime(year, month, 1, 0, 0, 0).getDayOfWeek();
        if (dayOfWeek == 7) {
            return 0;
        }
        return dayOfWeek;
    }*/
    public static NCalendar getWeekCalendar2(DateTime dateTime, int type) {
        List<DateTime> dateTimeList = new ArrayList<>();
        List<String> lunarStringList = new ArrayList<>();

        if (type == 0) {
            dateTime = getSunFirstDayOfWeek(dateTime);
        } else {
            dateTime = getMonFirstDayOfWeek(dateTime);
        }

        NCalendar calendar = new NCalendar();
        for (int i = 0; i < 7; i++) {
            DateTime dateTime1 = dateTime.plusDays(i);

            LunarCalendarUtils.Lunar lunar = LunarCalendarUtils.solarToLunar(new LunarCalendarUtils.Solar(dateTime1.getYear(), dateTime1.getMonthOfYear(), dateTime1.getDayOfMonth()));
            String lunarDayString = LunarCalendarUtils.getLunarDayString(dateTime1.getYear(), dateTime1.getMonthOfYear(), dateTime1.getDayOfMonth(), lunar.lunarYear, lunar.lunarMonth, lunar.lunarDay, lunar.isLeap);

            dateTimeList.add(dateTime1);
            lunarStringList.add(lunarDayString);
        }
        calendar.dateTimeList = dateTimeList;
        calendar.lunarList = lunarStringList;
        return calendar;
    }


    /**
     * 周视图的数据
     *
     * @param dateTime
     * @return
     */
    public static NCalendar getWeekCalendar(DateTime dateTime) {
        List<DateTime> dateTimeList = new ArrayList<>();
        List<String> lunarStringList = new ArrayList<>();

        dateTime = getSunFirstDayOfWeek(dateTime);
        NCalendar calendar = new NCalendar();
        for (int i = 0; i < 7; i++) {
            DateTime dateTime1 = dateTime.plusDays(i);

            LunarCalendarUtils.Lunar lunar = LunarCalendarUtils.solarToLunar(new LunarCalendarUtils.Solar(dateTime1.getYear(), dateTime1.getMonthOfYear(), dateTime1.getDayOfMonth()));
            String lunarDayString = LunarCalendarUtils.getLunarDayString(dateTime1.getYear(), dateTime1.getMonthOfYear(), dateTime1.getDayOfMonth(), lunar.lunarYear, lunar.lunarMonth, lunar.lunarDay, lunar.isLeap);

            dateTimeList.add(dateTime1);
            lunarStringList.add(lunarDayString);
        }
        calendar.dateTimeList = dateTimeList;
        calendar.lunarList = lunarStringList;
        return calendar;
    }

    //转化一周从周日开始
    public static DateTime getSunFirstDayOfWeek(DateTime dateTime) {
        if (dateTime.dayOfWeek().get() == 7) {
            return dateTime;
        } else {
            return dateTime.minusWeeks(1).withDayOfWeek(7);
        }
    }

    //转化一周从周一开始
    public static DateTime getMonFirstDayOfWeek(DateTime dateTime) {
        return dateTime.dayOfWeek().withMinimumValue();
    }


    //包含农历,公历,格式化的日期
    public static class NCalendar {
        public List<DateTime> dateTimeList;
        public List<String> lunarList;
        public List<String> localDateList;//2004-12-12
    }


}
