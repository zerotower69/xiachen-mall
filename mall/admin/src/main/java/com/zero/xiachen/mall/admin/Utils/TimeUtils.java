package com.zero.xiachen.mall.admin.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换
 */
public class TimeUtils {
    /**
     * 给定一个过去的日期，返回现在已经过了了多少秒
     * @param
     * @return
     */
//    public static void main(String args []) throws ParseException {
//        TimeUtils timeUtils=new TimeUtils();
//        String time_1970="1970-01-01 08:00:00";
//        System.out.println(stringTimeToDate(time_1970));
//        DateFormat dt=DateFormat.getDateInstance();
//    }   测试使用，

    /**
     * 给定一个已经过去的时间，返回现在距离此的秒数
     * @param passTime
     */
    public static long passTheTime(Date passTime){

        Date nowDate =new Date(); //现在的时间
         return (nowDate.getTime() - passTime.getTime()) / 1000;
    }

    /**
     * 字符串时间变为毫秒
     * @param time
     * @return
     */
    public static long stringTimeToDate(String time) throws ParseException {
        SimpleDateFormat st=new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        Date date=st.parse(time);
        return date.getTime();
    }
}
