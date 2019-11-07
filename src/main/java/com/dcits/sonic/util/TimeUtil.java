package com.dcits.sonic.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间处理公共类
 */
public class TimeUtil {

    /**
     * 获取当前时间
     *
     * @return 返回当前时间
     */
    public static String gerCurrentTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(date);
        return currentTime;
    }

    /**
     * 轮询耗时统计
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 时间差
     */
    public static String getDiffTime(String startTime, String endTime) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diffSeconds = 0;
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            long diff = end.getTime() - start.getTime();
            diffSeconds = diff / 1000;

        } catch (Exception e) {

        }
        return diffSeconds + "秒";
    }
}
