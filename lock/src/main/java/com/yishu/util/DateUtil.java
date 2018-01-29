/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-12 17:43 admin
 * @since JDK1.7
 */
public class DateUtil {
    public static final SimpleDateFormat yyyy_MM_dd0HH$mm$ss =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// yyyy1MM1dd$HH2mm2ss yyyy_MM_dd0HH$mm$ss
    public static final SimpleDateFormat yyyyMMddHHmmss =new SimpleDateFormat("yyyyMMddHHmmss");//yyyyMMddHHmmss

    public static final SimpleDateFormat yyyy_MM_dd0HH$mm =new SimpleDateFormat("yyyy-MM-dd HH:mm");//yyyy_MM_dd0HH$mm
    public static final SimpleDateFormat yyyyMMddHHmm =new SimpleDateFormat("yyyyMMddHHmm");

    public static final SimpleDateFormat yyyy_MM_dd =new SimpleDateFormat("yyyy-MM-dd");//yyyy_MM_dd0HH$mm
    public static final SimpleDateFormat yyyyMMdd =new SimpleDateFormat("yyyyMMdd");

    /**
     * @discription 返回当前日期的几月后的一天
     * @author 刘正义
     * @created 2015-11-5 下午8:25:53
     * @param number
     *            月数
     * @return
     */
    public static String getAfterMonth(int number)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, number);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        return formatter.format(calendar.getTime());
    }

    public static String long2DateString(Long date, String pattern)
    {
        if (date == null || date.longValue() == 0)
        {
            return "-";
        }
        if (date.longValue() == -99)
        {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(date));
    }

    public static DateTime toDateTime(String dateTime)
    {
        if (dateTime != null && !dateTime.equals(""))
        {
            return null;
        }
        return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(
                dateTime);
    }

    public static Date toDate(String dateTime) throws ParseException
    {
        if (dateTime != null && !dateTime.equals(""))
        {
            return null;
        }
        SimpleDateFormat smf=new SimpleDateFormat("yyyy-MM-dd");

        return smf.parse(dateTime);
    }

    public static String fromDate24H()
    {
        // SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd
        // hh:mm:ss");//12小时制
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
        return sdformat.format(new Date());
    }

    public static long stringTolong(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt2;
        long lTime = 0;
        try {
            if (time != null && !time.equals("")) {
                dt2 = sdf.parse(time);
                lTime = dt2.getTime();
            } else {
                return lTime;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return lTime;
    }

    /**
     * 返回当前时间 格式：yyyy-MM-dd
     *
     * @return String
     */
    public static String fromDateY() {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(new Date());
    }

    /**
     * 后几天
     *
     * @param d
     * @param day
     * @return
     */

    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 前几天
     *
     * @param d
     * @return
     */
    public static String getMonthBefore(Date d, int number) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, number);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return formatter.format(calendar.getTime());
    }

    /**
     * @discription 获取当月的第一天
     * @author 刘正义
     * @created 2015-11-9 上午9:32:08
     * @return
     */
    public static String getMonthStart() {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (1 - index));
        return format1.format(calendar.getTime());
    }

    /**
     * 当月天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 两个日期之间的月数集合
     *
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max))
        {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * @discription 获取几天后的时间
     * @author
     * @created 2016-1-8 下午4:35:25
     * @param d
     * @param day
     * @return
     */
    public static String DateAfter(Date d, int day) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return format1.format(now.getTime());
    }

    public static Date StringToDateByformat1(String time) throws ParseException {
        return yyyy_MM_dd0HH$mm$ss.parse(time);
    }

    public static String DateToStringByformat2(Date date){
        return yyyyMMddHHmmss.format(date);
    }

    public static String format1StringToformat2String(String dateStr) throws ParseException {
        return yyyyMMddHHmmss.format(yyyy_MM_dd0HH$mm$ss.parse(dateStr));
    }

    public static String format1tillminStringToformat2tillminString(String dateStr) throws ParseException {
        return yyyyMMddHHmm.format(yyyy_MM_dd0HH$mm.parse(dateStr));
    }

    public static String format2tillminStringToformat1tillminString(String dateStr) throws ParseException {
        return yyyy_MM_dd0HH$mm.format(yyyyMMddHHmm.parse(dateStr));
    }

    public static String getFormat2TimetagStr(){
        return yyyyMMddHHmmss.format(new Date());
    }

    public static long[] resetPeriod(long startTimeL,long endTimeL) throws ParseException {
//        String startTime="20180103111606";
//        String endTime  ="20180107152556";
        String timestamp="20180105120000";

//        String ZERO_TIME=DateUtil.yyyy_MM_dd0HH$mm$ss.format(0);
//        System.out.println("ZERO_TIME:"+ZERO_TIME);
        long startTag=0;
        long endTag=0;

//        long startTimeL=yyyyMMddHHmmss.parse(startTime).getTime();
//        long endTimeL= yyyyMMddHHmmss.parse(endTime).getTime();
        long timestampL= yyyyMMddHHmmss.parse(timestamp).getTime();

        System.out.println("startTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(startTimeL));
        System.out.println("endTime  :"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(endTimeL));
        int boundary= (int) (timestampL%86400000);
        System.out.println("timestamp-取余数-boundary:"+boundary);

        int start_remainder= (int) (startTimeL%86400000);
//        System.out.println("start_remainder:"+start_remainder);
        int start_round= (int) (startTimeL/86400000);
//        System.out.println("start_round:"+start_round);
        if (start_remainder>=boundary){
            startTag=start_round*1L*86400000+boundary;
        }else {
            startTag=(start_round-1)*1L*86400000+boundary;
        }
//        System.out.println("startTag:"+startTag);
        System.out.println("startTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(startTag));

        int end_remainder= (int) (endTimeL%86400000);
        System.out.println("end_remainder:"+end_remainder);
        int end_round= (int) (endTimeL/86400000);
        System.out.println("end_round:"+end_round);
        if (end_remainder<=boundary){
            endTag=end_round*1L*86400000+boundary;
        }else {
            endTag=(end_round+1)*1L*86400000+boundary;
        }
//        System.out.println("endTag:"+endTag);
        System.out.println("endTime  :"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(endTag));
        int periodSize= (int) ((endTag-startTag)/86400000);
        System.out.println("periodSize:"+periodSize);

        return new long[] {startTag,endTag};

        /*
        String timestamp="20180105120000";
        String ZERO_TIME=DateUtil.yyyy_MM_dd0HH$mm$ss.format(0);
        long timestampL= DateUtil.yyyyMMddHHmmss.parse(timestamp).getTime();

        long now=GetNetworkTime.getWebsiteDate().getTime();
        if (endTimeL<now){
            return null;
        }
        if (startTimeL>endTimeL){
            return null;
        }
        startTimeL=startTimeL>now?startTimeL:now;

//        System.out.println("startTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(startTimeL));
//        System.out.println("endTime  :"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(endTimeL));
        int boundary= (int) (timestampL%86400000);
//        System.out.println("timestamp-取余数-boundary:"+boundary);
        int start_remainder= (int) (startTimeL%86400000);
//        System.out.println("start_remainder:"+start_remainder);
        int start_round= (int) (startTimeL/86400000);
//        System.out.println("start_round:"+start_round);
        if (start_remainder>=boundary){
            startMoment=start_round*1L*86400000+boundary;
        }else {
            startMoment=(start_round-1)*1L*86400000+boundary;
        }
//        System.out.println("startTag:"+startTag);
//        System.out.println("startTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(startTag));

        int end_remainder= (int) (endTimeL%86400000);
//        System.out.println("end_remainder:"+end_remainder);
        int end_round= (int) (endTimeL/86400000);
//        System.out.println("end_round:"+end_round);
        if (end_remainder<=boundary){
            endMoment=end_round*1L*86400000+boundary;
        }else {
            endMoment=(end_round+1)*1L*86400000+boundary;
        }
        */
    }

    public static Date getStartOfDate(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
    public static Date getEndOfDate(Date date){
        Calendar calendar=Calendar.getInstance();
        /*
        calendar.setTime(date);
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        */
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        return calendar.getTime();
    }
    public static Date[] getDateArr(Date date){
//        Date today=new Date();
        Calendar calendar=Calendar.getInstance();
        Date theDate;
//        calendar.setTime(today);
//        calendar.set(Calendar.HOUR_OF_DAY,0);
//        calendar.set(Calendar.MINUTE,0);
//        calendar.set(Calendar.SECOND,0);
//        calendar.set(Calendar.MILLISECOND,0);
//        theDate=calendar.getTime();
        theDate=DateUtil.getStartOfDate(date);
        /*
        Date startDate;
        Date endDate;
        calendar.setTime(theDate);
        calendar.add(Calendar.DAY_OF_MONTH,-15);
        startDate=calendar.getTime();
        calendar.setTime(theDate);
        calendar.add(Calendar.DAY_OF_MONTH,15);
        endDate=calendar.getTime();
        */
        Date[] dateArr=new Date[31];
        for(int i=-15;i<16;i++){
            calendar.setTime(theDate);
            calendar.add(Calendar.DAY_OF_MONTH,i);
            dateArr[i+15]=calendar.getTime();
        }
        return dateArr;
    }
}
