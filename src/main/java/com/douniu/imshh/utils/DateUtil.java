package com.douniu.imshh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static Date string2Date(String dateString){	
		return string2Date(dateString, "yyyy-MM-dd");
	}
	
	public static Date string2Date(String dateString, String format){		
		if (dateString == null || dateString.trim().equals(""))
			return null;
		
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static String Date2String(Date date, String fomat){
		SimpleDateFormat sdf = new SimpleDateFormat(fomat);
		return sdf.format(date);
	}
	
	/**
	 * 获取某年月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth1(int year, int month) {     
        Calendar cal = Calendar.getInstance();     
        //设置年份  
        cal.set(Calendar.YEAR, year);  
        //设置月份  
        cal.set(Calendar.MONTH, month-1); 
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数  
        cal.set(Calendar.DAY_OF_MONTH, lastDay);  
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        return sdf.format(cal.getTime());
    }
	
	public static Date getLastDayOfYM(int year, int month){
		Calendar cal = Calendar.getInstance();     
        //设置年份  
        cal.set(Calendar.YEAR, year);  
        //设置月份  
        cal.set(Calendar.MONTH, month-1); 
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数  
        cal.set(Calendar.DAY_OF_MONTH, lastDay);  
        return cal.getTime();
	}
	
	public static String getNexMonth(String dateString, String format){
		Date date = DateUtil.string2Date(dateString, format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
        cal.add(cal.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
        String nextMonth = dft.format(cal.getTime());
        return nextMonth;
	}
	
	public static Date preDay(String dateString, String format){
		
		return null;
	}
}
