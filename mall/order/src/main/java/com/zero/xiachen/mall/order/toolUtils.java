package com.zero.xiachen.mall.order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class toolUtils {
    /**
     * 生成订单编号
     * @param date
     * @param userId
     * @return 订单编号，20位
     */
    public String generateOrderCode(Date date, String userId){
        SimpleDateFormat ft=new SimpleDateFormat("yyyyMMdd");
        String time=ft.format(date);
        return userId.substring(11,17)+time+setRandNumber(6);
    }

    /**
     *
     * @param N
     * @return
     */
    //生成指定位数的随机数，并转换为字符串
    public String setRandNumber(int N){
        Random rand =new Random();
        int number=(int) Math.pow(10,N);
        return Integer.toString(rand.nextInt(number));
    }
}
