package com.ahav.reserve.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

//关于时间的工具类
public class meetingUtils {
    //获得下一天的开始时间,参数日期
    public static Date getEndTime(Date date){
        DateFormat b=new SimpleDateFormat("yyyy-MM-dd");
        try{
            date = b.parse(b.format(date));  //将时间转为这种格式的字符串，然后在将这种格式的字符串转为这种格式的时间
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        System.out.println(date);
        return date;
    }

    //获得当天的开始时间
    public static Date getStartTime(Date date){
        DateFormat b=new SimpleDateFormat("yyyy-MM-dd");
        try{
            date = b.parse(b.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //将时间转为指定的格式的时间字符串(如:查询历史前台就要这种格式的时间字符串)
    public static String transformTimeFormat(Date date){
        DateFormat b=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr;
        dateStr = b.format(date);
        return dateStr;
    }


    //将字符串转为这种格式("yyyy-MM-dd HH:mm")的时间
    public static Date parse(String date){
        DateFormat b=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parse = null;
        try{
            parse = b.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    //将字符串转为这种格式("yyyy-MM-dd")的时间
    public static Date parse2(String date){
        DateFormat b=new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try{
            parse = b.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    //获取uuid
    public static String getUUID32(){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

}
