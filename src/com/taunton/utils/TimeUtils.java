package com.taunton.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtils {
	/**
	 * 日期格式 :"yyyy-MM-dd HH:mm:ss"
	 */
	public static final String FORMATTER_1 = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式 :"yyyy-MM-dd"
	 */
	public static final String FORMATTER_2 = "yyyy-MM-dd";
	/**
	 * 日期格式 :"HH:mm:ss"
	 */
	public static final String FORMATTER_3 = "HH:mm:ss";
	/**
	 * 一天换算成毫秒
	 */
	public static final long COUNTMILLS_DAY = 24*60*60*1000;
	/**
	 * 一小时换算成毫秒
	 */
	public static final long COUNTMILLS_4 = 60*60*1000;
	/**
	 * 十分钟换算成毫秒
	 */
	public static final long COUNTMILLS_1 = 10*60*1000;
	/**
	 * 一分钟换算成毫秒
	 */
	public static final long COUNTMILLS_2 = 1*60*1000;
	/**
	 * 五秒换算成毫秒
	 */
	public static final long COUNTMILLS_3 = 1*10*1000;
	/**
	 * 一分钟换算成秒
	 */
	public static final long COUNTSECONDS_1 = 1*60;
	/**
	 * 十分钟换算成秒
	 */
	public static final long COUNTSECONDS_2 = 10*60;
	/**
	 * 三十分钟换算成秒
	 */
	public static final long COUNTSECONDS_3 = 30*60;
	/**
	 * 一个小时换算成秒
	 */
	public static final long COUNTSECONDS_4 = 60*60;
	/**
	 * 12个小时换算成秒
	 */
	public static final long COUNTSECONDS_5 = 12*60*60;
	/**
	 * 24个小时换算成秒
	 */
	public static final long COUNTSECONDS_6 = 24*60;
	/**
	 * 30天换算成秒
	 */
	public static final long COUNTSECONDS_7 = 30*24*60;

	/**
	 * 将java.util.Date的对象格式化为指定格式的字符串
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date,String formatter){
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);	
		String date2 = sdf.format(date);
		return date2;
	}
	/**
	 * 将字符串解析为指定格式的java.util.Date对象
	 * @param dateStr
	 * @return
	 */
	public static Date parseString(String dateStr,String formatter){
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		Date date = null;
		try {
			 date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 根据指定格式获取当前时间格式化字符串
	 * @param formatter
	 * @return
	 */
	public static String getCurrTime(String formatter){
		Date date = new Date();
		String currTime = null;
		currTime = formatDate(date, formatter);
		return currTime;
	}
	
	/**
	 * 根据指定格式和天数获取未来某天时间的格式化字符串
	 * @param formatter 日期格式
	 * @param days 天数
	 * @return
	 */
	public static String getFutureTime(String formatter,double days){
		Date date = new Date();
		long dayss = (long) (days*24*60*60*1000);
		dayss+=date.getTime();
		Date date2 = new Date(dayss);
		String futureTime = null;
		futureTime = formatDate(date2, formatter);
		return futureTime;
	}
	/**
	 * 获取指定时间的毫秒数
	 * @param time "HH:mm:ss"
	 * @return
	 */
	public static long getTimeMills(String time){
		Date curDate = parseString(formatDate(new Date(), FORMATTER_2)+" "+time, FORMATTER_1);
		return curDate.getTime();  
	}
	
	/**
	 * 通过传入的起始时间格式化字符串及对应的格式化格式获取两个时间点之间的时间间隔
	 * @param startTime
	 * @param startPattern
	 * @param endTime
	 * @param endPattern
	 * @return
	 */
	public static String getWasteTime(String startTime,String startPattern,String endTime,String endPattern){
		long start = parseString(startTime,startPattern).getTime();
		long end = parseString(endTime,endPattern).getTime();
		long waste = end-start;
		long days =  waste/86400000;
		long hours = waste%86400000/3600000;
		long minutes = waste%86400000%3600000/60000;
		long seconds = waste%86400000%3600000%60000/1000;
		String result = "";
//		result = (days == 0 && hours == 0 && minutes == 0)?(days == 0 && hours == 0)?(days == 0)?""+seconds+"秒":""+days+"天"+hours+"时"+minutes+"分"+seconds+"秒":""+hours+"时"+minutes+"分"+seconds+"秒":""+minutes+"分"+seconds+"秒";
		result = (days == 0)?(days == 0 && hours == 0)?(days == 0 && hours == 0 && minutes == 0)?""+seconds+"秒":""+minutes+"分"+seconds+"秒":""+hours+"时"+minutes+"分"+seconds+"秒":""+days+"天"+hours+"时"+minutes+"分"+seconds+"秒";
		return result; 
		
	}

}

