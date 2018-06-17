package com.example.demo.util;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class GetDate {
    public static List<Date> getDateAfter(int num){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar .HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0); //关键的一步，很多时候忽略了毫秒置0，而无法查询出想要的结果
        Date date = cal.getTime();
        cal.add(Calendar.DATE,num);
        Date date1 = cal.getTime();
        List<Date> dateList = new LinkedList<>();
        dateList.add(date);
        dateList.add(date1);
        return dateList;
    }
    /**
     * 判断日期是否在区间内
     *
     * @param nowTime
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 把多余时间排除掉
     * @param cal
     * @return
     */
    public static Date calToDate(Calendar cal){
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0); //关键的一步，很多时候忽略了毫秒置0，而无法查询出想要的结果
        Date date = cal.getTime();
        return date;
    }
}
