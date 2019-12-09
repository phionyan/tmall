package com.phion.tmall.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test3 {
 
    /**
     * @param args
     * @throws ParseException
     * format() 对日期进行格式化处理
     * parse() 将日期设置为date类型
     */
    public static void main(String[] args) throws ParseException {  
        // TODO Auto-generated method stub  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date d1=sdf.parse("2016-01-01 10:10:10");  
        Date d2=sdf.parse("2019-12-5 22:00:00");
        Date d3=sdf.parse(sdf.format(new Date()));//获得当前时间
        System.out.println(daysBetween(d1,d2));  
        System.out.println(daysBetween(d2, d3));
        System.out.println(daysBetween("2016-09-08 10:10:10","2016-09-29 00:00:00")); 
        System.out.println(daysBetween("2016-09-08 10:10:10", d3));
    }  
      
    /**
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     * calendar 对日期进行时间操作
     * getTimeInMillis() 获取日期的毫秒显示形式
     */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();      
        long between_days=(time2-time1)/(1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));           
    }
    /**
     * 字符串日期格式的计算
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));     
    }  
    /**
     * 字符串日期格式和date日期格式的计算
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String smdate,Date bdate) throws ParseException {
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(sdf.parse(smdate));
    	long time1 = cal.getTimeInMillis();
    	cal.setTime(bdate);
    	long time2 = cal.getTimeInMillis();
    	long between_days=(time2-time1)/(1000*3600*24);
    	return Integer.parseInt(String.valueOf(between_days));
    }
  
}