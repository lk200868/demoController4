package com.kgc.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

//import com.kbao.core.common.exception.BusinessException;

public class DateHelper {
	public static final String SPLIT_CHAR = ",";
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String DEFAULT_FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_ALL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DEFAULT_SHORT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_NUMBER_DATE_FORMAT="yyyyMMddHHmmss";
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	
	
	public final static SimpleDateFormat DEFAULT_SIMPLE_DATE_FORMAT = new SimpleDateFormat(DEFAULT_FULL_DATE_FORMAT);
	
	/**
     * 将未指定格式的日期字符串转化成java.util.Date类型日期对象
	 *  
     * @param date, 待转换的日期字符串, 顺序必须为: 年(2位或4位)-月-日 时-分-秒    (毫秒数不做处理)
     * @return
	 * @throws ParseException 
     */
    public static Date parseStringToDate(String date){
        Date result=null;
        date = date.replaceAll("T", " ");
        String parse=date;
        parse=parse.replaceFirst("^[0-9]{4}([^0-9])", "yyyy$1");
        parse=parse.replaceFirst("^[0-9]{2}([^0-9])", "yy$1");
        parse=parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9])", "$1MM$2");
        parse=parse.replaceFirst("([^0-9])[0-9]{1,2}( ?)", "$1dd$2");
        parse=parse.replaceFirst("( )[0-9]{1,2}([^0-9])", "$1HH$2");
        parse=parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9]?)", "$1mm$2");
        parse=parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9]?)", "$1ss$2");
        parse=parse.replaceFirst("(\\.)[0-9]{1,3}([^0-9]?)", "$1SSS$2");
        
        SimpleDateFormat format=new SimpleDateFormat(parse);
		try {
			result=format.parse(date);
		} catch (ParseException e) {
			System.out.print(new BusinessException("日期转换出错",e));
		}

        return result;
    }
    
    /**
     * 将未指定格式的日期字符串转化成java.util.Date类型日期对象
	 *  
     * @param date,待转换的日期字符串
     * @return
     * @throws ParseException
     */
    public static Timestamp parseStringToTimestamp(String date){
		Date d = parseStringToDate(date);
		SimpleDateFormat dateformat = new SimpleDateFormat(DEFAULT_FULL_DATE_FORMAT);
		Timestamp tmp = Timestamp.valueOf(dateformat.format(d));
		return tmp;
    } 
    
	/** 根据时间变量返回时间字符串
	 * @return 返回时间字符串
	 * @param pattern 时间字符串样式
	 * @param date 时间变量
	 */
    public static String date2String(Date date, String pattern){
    	if (date == null){
			return null;
		}
		try{
			SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
			sfDate.setLenient(false);
			return sfDate.format(date);
		}
		catch (Exception e){
			return null;
		}
    }
    
	/**
	 * 获取现在时间
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getTime() {
		return string2Time(getTimeString());
	}

	/**
	 * 获取现在时间
	 * @return 返回短时间格式 yyyy-MM-dd
	 */
	public static Date getDate() {
		return string2Date(getDateString());
	}
	
	/**
	 * 获取现在时间
	 * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getTimeString() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FULL_DATE_FORMAT);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getDateString() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_SHORT_DATE_FORMAT);
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	/**
	 * 指定日期格式获得现在时间
	 * @param pattern 时间字符串样式
	 * @return
	 */
	public static String getDateString(String pattern){
		try{
			SimpleDateFormat sfDate = new SimpleDateFormat(pattern);
			sfDate.setLenient(false);
			return sfDate.format(new Date());
		}
		catch (Exception e){
			return null;
		}
    }
	
	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * @param strDate
	 * @return
	 */
	public static Date string2Time(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FULL_DATE_FORMAT);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(date, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * @param dateDate
	 * @return
	 */
	public static String time2String(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FULL_DATE_FORMAT);
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * @param dateDate
	 * @return
	 */
	public static String date2String(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_SHORT_DATE_FORMAT);
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * @param strDate
	 * @return
	 */
	public static Date string2Date(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_SHORT_DATE_FORMAT);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 获取当前小时
	 * @return
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FULL_DATE_FORMAT);
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 获取当前分钟
	 * @return
	 */
	public static String getMin() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FULL_DATE_FORMAT);
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}
	
	/**
	 * 获取当前月
	 * @return
	 */
	public static String getMonth() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String time = format.format(c.getTime());
		return time;
	}

	/**
	 * 获取上月
	 * @return
	 */
	public static String getLastMonth() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String time = format.format(c.getTime());
		return time;
	}
	
	/**
	 * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
	 * @param st1
	 * @param st2
	 * @return
	 */
	public static String betweenHours(String st1, String st2) {
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
			return "0";
		else {
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
			if ((y - u) > 0)
				return y - u + "";
			else
				return "0";
		}
	}

	/**
	 * 得到二个日期间的间隔天数
	 * @param sj1
	 * @param sj2
	 * @return
	 */
	public static String betweenDays(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat(DEFAULT_SHORT_DATE_FORMAT);
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 * @param nowdate
	 * @param delay
	 * @return
	 */
	public static String nextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(DEFAULT_SHORT_DATE_FORMAT);
			String mdate = "";
			Date d = string2Date(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 判断是否润年
	 * @param date
	 * @return
	 */
	public static boolean isLeapYear(String date) {

		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = string2Date(date);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 获取一个月的最后一天
	 * @param date yyyy-MM-dd
	 * @return
	 */
	public static String getEndDateOfMonth(String date) {
		String str = date.substring(0, 8);
		String month = date.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "30";
		} else {
			if (isLeapYear(date)) {
				str += "29";
			} else {
				str += "28";
			}
		}
		return str;
	}
	
	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * @return
	 */
	public static String getSeqWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * 获得一个日期所在的周的星期几的日期，如：找出2002年2月3日所在周的星期一是几号
	 * @param date
	 * @param num
	 * @return
	 */
	public static String getWeek(String date, String num) {
		// 再转换为时间
		Date dd = DateHelper.string2Date(date);
		Calendar c = Calendar.getInstance();
		c.setTime(dd);
		if (num.equals("1")) // 返回星期一所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		else if (num.equals("2")) // 返回星期二所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		else if (num.equals("3")) // 返回星期三所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		else if (num.equals("4")) // 返回星期四所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		else if (num.equals("5")) // 返回星期五所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		else if (num.equals("6")) // 返回星期六所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		else if (num.equals("0")) // 返回星期日所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new SimpleDateFormat(DEFAULT_SHORT_DATE_FORMAT).format(c.getTime());
	}
	
	/**
	 * 获取两个时间之间的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat(DEFAULT_SHORT_DATE_FORMAT);
		Date date = null;
		Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}


}
