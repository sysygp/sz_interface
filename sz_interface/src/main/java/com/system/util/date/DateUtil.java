package com.system.util.date;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.system.util.stringutil.StringDateConstant;
import com.system.util.stringutil.StringUtils;


/**
 * 
* @ClassName: DateUtils 
* @Description: 日期工具类 
* @author 杨功平 852704764@qq.com 
* @date 2014年11月14日 下午2:40:32 
*
 */
public class DateUtil {
	/**
	 * ORA标准时间格式
	 */
	public static final DateFormat DF_YM = new SimpleDateFormat(StringDateConstant.FMT_YM);
	public static final DateFormat DF_YMD = new SimpleDateFormat(StringDateConstant.FMT_YMD);
	public static final DateFormat DF_YMDH = new SimpleDateFormat(StringDateConstant.FMT_YMDH);
	public static final DateFormat DF_YMDHM = new SimpleDateFormat(StringDateConstant.FMT_YMDHM);
	public static final DateFormat DF_YMDHMS = new SimpleDateFormat(StringDateConstant.FMT_YMDHMS);
	public static final DateFormat DF_HMS = new SimpleDateFormat(StringDateConstant.FMT_HMS);
	
	/**
	 * 精简时间格式
	 */
	public static final DateFormat DF_YM_TRIM = new SimpleDateFormat(StringDateConstant.FMT_YM_TRIM);
	public static final DateFormat DF_YMD_TRIM = new SimpleDateFormat(StringDateConstant.FMT_YMD_TRIM);
	public static final DateFormat DF_YMDH_TRIM = new SimpleDateFormat(StringDateConstant.FMT_YMDH_TRIM);
	public static final DateFormat DF_YMDHM_TRIM = new SimpleDateFormat(StringDateConstant.FMT_YMDHM_TRIM);
	public static final DateFormat DF_YMDHMS_TRIM = new SimpleDateFormat(StringDateConstant.FMT_YMDHMS_TRIM);
	public static final DateFormat DF_HMS_TRIM = new SimpleDateFormat(StringDateConstant.FMT_HMS_TRIM);

	
	private static final long ONE_DAY = 24 * 60 * 60 * 1000;
	
	private static final long ONE_HOUR = 60 * 60 * 1000;
	
	private static final long ONE_MIN = 60 * 1000;
	
	
	/**
	 * 将两个格式为HH:MM:SS的时间字符串相加，例如：00:59:06 + 01:00:59 返回 02:00:05。
	 * 
	 * @param time1
	 *            要累计的时间字符串
	 * @param time2
	 *            要累计的时间字符串
	 * @return 累计后的时间字符串
	 */
	public static String addTwoTimeStr(String time1, String time2) {
		
		String returnStr = "00:00:00";
		if (time1 != null && !time1.equalsIgnoreCase("") && time2 != null && !time2.equalsIgnoreCase("")) {
			String[] time1Array = time1.split(":");
			String[] time2Array = time2.split(":");
			int hour1 = (new Integer(time1Array[0])).intValue();
			int hour2 = (new Integer(time2Array[0])).intValue();
			int min1 = (new Integer(time1Array[1])).intValue();
			int min2 = (new Integer(time2Array[1])).intValue();
			int sec1 = (new Integer(time1Array[2])).intValue();
			int sec2 = (new Integer(time2Array[2])).intValue();
			
			String lastSec, lastMin, lastHour;
			
			int totalSec = sec1 + sec2;
			if (totalSec / 60 > 0) {
				// 超过1分钟的时间累计到min1中
				min1 = min1 + totalSec / 60;
			}
			if (totalSec % 60 > 9) {
				lastSec = new Integer(totalSec % 60).toString();
			} else {
				lastSec = new String("0" + new Integer(totalSec % 60).toString());
			}
			
			int totalMin = min1 + min2;
			if (totalMin / 60 > 0) {
				// 超过1分钟的时间累计到hour1中
				hour1 = hour1 + totalMin / 60;
			}
			if (totalMin % 60 > 9) {
				lastMin = new Integer(totalMin % 60).toString();
			} else {
				lastMin = new String("0" + new Integer(totalMin % 60).toString());
			}
			
			int totalHour = hour1 + hour2;
			if (totalHour % 24 > 9) {
				lastHour = new Integer(totalHour % 24).toString();
			} else {
				lastHour = new String("0" + new Integer(totalHour % 24).toString());
			}
			
			returnStr = lastHour + ":" + lastMin + ":" + lastSec;
		} else if (time1 != null && !time1.equalsIgnoreCase("")) {
			returnStr = time1.substring(0, 8);
		} else if (time2 != null && !time2.equalsIgnoreCase("")) {
			returnStr = time2.substring(0, 8);
		} else {
			returnStr = "00:00:00";
		}
		
		return returnStr;
	}
	
	// 当前日期
	@SuppressWarnings({ "deprecation" })
	public static String currentDate() {
		Date myDate = new Date();
		int thisYear = myDate.getYear() + 1900;
		int thisMonth = myDate.getMonth() + 1;
		int thisDate = myDate.getDate();
		String date = thisYear + "年" + thisMonth + "月" + thisDate + "日";
		return date;
	}
	
	/**
	 * 获取当前时间
	 * 
	 */
	public static Date getCurDate(){
		return new Date();
	}
	
	/**
	 * 创建一个标准ORA时间格式的克隆
	 * 
	 * @return 标准ORA时间格式的克隆
	 */
	private static synchronized DateFormat getOraDateTimeFormat() {
		SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) DF_YMD_TRIM.clone();
		theDateTimeFormat.setLenient(false);
		return theDateTimeFormat;
	}
	
	/**
	 * 创建一个带分秒的ORA时间格式的克隆
	 * 
	 * @return 标准ORA时间格式的克隆
	 */
	private static synchronized DateFormat getOraExtendDateTimeFormat() {
		SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) DF_YMDHMS_TRIM.clone();
		theDateTimeFormat.setLenient(false);
		return theDateTimeFormat;
	}
	
	/**
	 * 得到系统当前的日期 格式为YYYY-MM-DD
	 * 
	 * @return 系统当前的日期 格式为YYYY-MM-DD
	 */
	public static String getSystemCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return doTransform(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * 返回格式为YYYY-MM-DD
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return
	 */
	private static String doTransform(int year, int month, int day) {
		StringBuffer result = new StringBuffer();
		result.append(String.valueOf(year)).append("-").append(month < 10 ? "0" + String.valueOf(month) : String.valueOf(month)).append("-").append(
				day < 10 ? "0" + String.valueOf(day) : String.valueOf(day));
		return result.toString();
	}
	
	/**
	 * 得到系统当前的日期和时间 格式为YYYY-MM-DD hh:mm:ss
	 * 
	 * @return 格式为YYYY-MM-DD hh:mm:ss的系统当前的日期和时间
	 */
	public static final String getSystemCurrentDateTime() {
		Calendar newCalendar = Calendar.getInstance();
		newCalendar.setTimeInMillis(System.currentTimeMillis());
		int year = newCalendar.get(Calendar.YEAR);
		int month = newCalendar.get(Calendar.MONTH) + 1;
		int day = newCalendar.get(Calendar.DAY_OF_MONTH);
		int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = newCalendar.get(Calendar.MINUTE);
		int second = newCalendar.get(Calendar.SECOND);
		return doTransform(year, month, day, hour, minute, second);
	}
	
	/**
	 * 得到系统当前的时间 格式为hh:mm:ss
	 * 
	 * @return 格式为hh:mm:ss的系统当前时间
	 */
	public static final String getSystemCurrentTime() {
		return getSystemCurrentDateTime().substring(11, 19);
	}
	
	/**
	 * 返回格式为YYYY-MM-DD hh:mm:ss
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            小时
	 * @param minute
	 *            分钟
	 * @param second
	 *            秒
	 * @return
	 */
	private static final String doTransform(int year, int month, int day, int hour, int minute, int second) {
		StringBuffer result = new StringBuffer();
		result.append(String.valueOf(year)).append("-").append(month < 10 ? "0" + String.valueOf(month) : String.valueOf(month)).append("-").append(
				day < 10 ? "0" + String.valueOf(day) : String.valueOf(day)).append(" ").append(hour < 10 ? "0" + String.valueOf(hour) : String.valueOf(hour))
				.append(":").append(minute < 10 ? "0" + String.valueOf(minute) : String.valueOf(minute)).append(":").append(
						second < 10 ? "0" + String.valueOf(second) : String.valueOf(second));
		return result.toString();
	}
	
	/**
	 * 获得昨天的日期
	 * 
	 * @return 指定日期的上一天 格式:YYYY-MM-DD
	 */
	public static synchronized String getDayBeforeToday() {
		Date date = new Date(System.currentTimeMillis());
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -1);
		return doTransform(toString(gc.getTime(), getOraExtendDateTimeFormat())).substring(0, 10);
	}
	
	/**
	 * 获得指定日期的上一天的日期
	 * 
	 * @param dateStr
	 *            指定的日期 格式:YYYY-MM-DD
	 * @return 指定日期的上一天 格式:YYYY-MM-DD
	 */
	public static synchronized String getDayBeforeToday(String dateStr) {
		Date date = toDayStartDate(dateStr);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -1);
		
		return doTransform(toString(gc.getTime(), getOraExtendDateTimeFormat())).substring(0, 10);
	}
	
	/**
	 * 获得明天的日期
	 * 
	 * @return 指定日期的下一天 格式:YYYY-MM-DD
	 */
	public static synchronized String getDayAfterToday() {
		Date date = new Date(System.currentTimeMillis());
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 1);
		return doTransform(toString(gc.getTime(), getOraExtendDateTimeFormat())).substring(0, 10);
	}
	
	/**
	 * 获得指定日期的下一天的日期
	 * 
	 * @param dateStr
	 *            指定的日期 格式:YYYY-MM-DD
	 * @return 指定日期的下一天 格式:YYYY-MM-DD
	 */
	public static synchronized String getDayAfterToday(String dateStr) {
		Date date = toDayStartDate(dateStr);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 1);
		return doTransform(toString(gc.getTime(), getOraExtendDateTimeFormat())).substring(0, 10);
	}
	
	/**
	 * 获得以后几个月的日期
	 * 
	 * @return 指定日期的后面几个月 格式:YYYY-MM-DD
	 */
	public static synchronized Date getDayAfterMonth(int months) {
		Date date = new Date(System.currentTimeMillis());
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, months);
		return gc.getTime();
	}
	
	/**
	 * 返回格式为YYYY-MM-DD hh:mm:ss
	 * 
	 * @param date
	 *            输入格式为ORA标准时间格式
	 * @return
	 */
	private static String doTransform(String date) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(date.substring(0, 4));
		buffer.append("-");
		buffer.append(date.substring(4, 6));
		buffer.append("-");
		buffer.append(date.substring(6, 8));
		buffer.append(" ");
		buffer.append(date.substring(8, 10));
		buffer.append(":");
		buffer.append(date.substring(10, 12));
		buffer.append(":");
		buffer.append(date.substring(12, 14));
		
		return buffer.toString();
	}
	
	/**
	 * 将一个日期对象转换成为指定日期、时间格式的字符串。 如果日期对象为空，返回一个空字符串对象.
	 * 
	 * @param theDate
	 *            要转换的日期对象
	 * @param theDateFormat
	 *            返回的日期字符串的格式
	 * @return 转换结果
	 */
	public static synchronized String toString(Date theDate, DateFormat theDateFormat) {
		if (theDate == null) {
			return "";
		} else {
			return theDateFormat.format(theDate);
		}
	}
	
	/**
	 * 将Date类型转换后返回本系统默认的日期格式 YYYY-MM-DD hh:mm:ss
	 * 
	 * @param theDate
	 *            要转换的Date对象
	 * @return 转换后的字符串
	 */
	public static synchronized String toDefaultString(Date theDate) {
		if (theDate == null) {
			return "";
		}
		return doTransform(toString(theDate, getOraExtendDateTimeFormat()));
	}
	
	/**
	 * 将输入格式为2004-8-13 12:31:22类型的字符串转换为标准的Date类型
	 * 
	 * @param dateStr
	 *            要转换的字符串
	 * @return 转化后的标准的Date类型
	 */
	public static synchronized Date toDate(String dateStr) {
		String[] list0 = dateStr.split(" ");
		String date = list0[0];
		String time = list0[1];
		String[] list1 = date.split("-");
		int year = new Integer(list1[0]).intValue();
		int month = new Integer(list1[1]).intValue();
		int day = new Integer(list1[2]).intValue();
		String[] list2 = time.split(":");
		int hour = new Integer(list2[0]).intValue();
		int min = new Integer(list2[1]).intValue();
		int second = new Integer(list2[2]).intValue();
		Calendar cale = Calendar.getInstance();
		cale.set(year, month - 1, day, hour, min, second);
		return cale.getTime();
	}
	
	/**
	 * 将输入格式为2004-8-13,2004-10-8类型的字符串转换为标准的Date类型,这种Date类型 对应的日期格式为YYYY-MM-DD 00:00:00,代表一天的开始时刻
	 * 
	 * @param dateStr
	 *            要转换的字符串
	 * @return 转换后的Date对象
	 */
	public static synchronized Date toDayStartDate(String dateStr) {
		String[] list = dateStr.split("-");
		int year = Integer.parseInt(list[0]);
		int month = Integer.parseInt(list[1]);
		int day = Integer.parseInt(list[2]);
		Calendar cale = Calendar.getInstance();
		cale.set(year, month - 1, day, 0, 0, 0);
		
		return cale.getTime();
		
	}
	
	/**
	 * 将输入格式为2004-8-13,2004-10-8类型的字符串转换为标准的Date类型,这种Date类型 对应的日期格式为YYYY-MM-DD 23:59:59,代表一天的结束时刻
	 * 
	 * @param dateStr
	 *            输入格式:2004-8-13,2004-10-8
	 * @return 转换后的Date对象
	 */
	public static synchronized Date toDayEndDate(String dateStr) {
		String[] list = dateStr.split("-");
		int year = new Integer(list[0]).intValue();
		int month = new Integer(list[1]).intValue();
		int day = new Integer(list[2]).intValue();
		Calendar cale = Calendar.getInstance();
		cale.set(year, month - 1, day, 23, 59, 59);
		return cale.getTime();
		
	}
	
	/**
	 * 将两个scorm时间相加
	 * 
	 * @param scormTime1
	 *            scorm时间,格式为00:00:00(1..2).0(1..3)
	 * @param scormTime2
	 *            scorm时间,格式为00:00:00(1..2).0(1..3)
	 * @return 两个scorm时间相加的结果
	 */
	public static synchronized String addTwoScormTime(String scormTime1, String scormTime2) {
		int dotIndex1 = scormTime1.indexOf(".");
		int hh1 = Integer.parseInt(scormTime1.substring(0, 2));
		int mm1 = Integer.parseInt(scormTime1.substring(3, 5));
		int ss1 = 0;
		if (dotIndex1 != -1) {
			ss1 = Integer.parseInt(scormTime1.substring(6, dotIndex1));
		} else {
			ss1 = Integer.parseInt(scormTime1.substring(6, scormTime1.length()));
		}
		int ms1 = 0;
		if (dotIndex1 != -1) {
			ms1 = Integer.parseInt(scormTime1.substring(dotIndex1 + 1, scormTime1.length()));
		}
		
		int dotIndex2 = scormTime2.indexOf(".");
		int hh2 = Integer.parseInt(scormTime2.substring(0, 2));
		int mm2 = Integer.parseInt(scormTime2.substring(3, 5));
		int ss2 = 0;
		if (dotIndex2 != -1) {
			ss2 = Integer.parseInt(scormTime2.substring(6, dotIndex2));
		} else {
			ss2 = Integer.parseInt(scormTime2.substring(6, scormTime2.length()));
		}
		int ms2 = 0;
		if (dotIndex2 != -1) {
			ms2 = Integer.parseInt(scormTime2.substring(dotIndex2 + 1, scormTime2.length()));
		}
		
		int hh = 0;
		int mm = 0;
		int ss = 0;
		int ms = 0;
		
		if (ms1 + ms2 >= 1000) {
			ss = 1;
			ms = ms1 + ms2 - 1000;
		} else {
			ms = ms1 + ms2;
		}
		if (ss1 + ss2 + ss >= 60) {
			mm = 1;
			ss = ss1 + ss2 + ss - 60;
		} else {
			ss = ss1 + ss2 + ss;
		}
		if (mm1 + mm2 + mm >= 60) {
			hh = 1;
			mm = mm1 + mm2 + mm - 60;
		} else {
			mm = mm1 + mm2 + mm;
		}
		hh = hh + hh1 + hh2;
		
		StringBuffer sb = new StringBuffer();
		if (hh < 10) {
			sb.append("0").append(hh);
		} else {
			sb.append(hh);
		}
		sb.append(":");
		if (mm < 10) {
			sb.append("0").append(mm);
		} else {
			sb.append(mm);
		}
		sb.append(":");
		if (ss < 10) {
			sb.append("0").append(ss);
		} else {
			sb.append(ss);
		}
		sb.append(".");
		if (ms < 10) {
			sb.append(ms).append("00");
		} else if (ms < 100) {
			sb.append(ms).append("0");
		} else {
			sb.append(ms);
		}
		return sb.toString();
	}
	
	/**
	 * 根据timeType返回当前日期与传入日期的差值（当前日期减传入日期） 当要求返回月份的时候，date的日期必须和当前的日期相等， 否则返回0（例如：2003-2-23 和 2004-6-12由于23号和12号不是同一天，固返回0， 2003-2-23 和 2005-6-23
	 * 则需计算相差的月份，包括年，此例应返回28（个月）。 2003-2-23 和 2001-6-23 也需计算相差的月份，包括年，此例应返回-20（个月））
	 * 
	 * @param date
	 *            要与当前日期比较的日期
	 * @param timeType
	 *            0代表返回两个日期相差月数，1代表返回两个日期相差天数
	 * @return 根据timeType返回当前日期与传入日期的差值
	 */
	public static int CompareDateWithNow(Date date, int timeType) {
		Date now = Calendar.getInstance().getTime();
		
		Calendar calendarNow = Calendar.getInstance();
		calendarNow.setTime(now);
		calendarNow.set(Calendar.HOUR, 0);
		calendarNow.set(Calendar.MINUTE, 0);
		calendarNow.set(Calendar.SECOND, 0);
		
		Calendar calendarPara = Calendar.getInstance();
		calendarPara.setTime(date);
		calendarPara.set(Calendar.HOUR, 0);
		calendarPara.set(Calendar.MINUTE, 0);
		calendarPara.set(Calendar.SECOND, 0);
		
		float nowTime = now.getTime();
		float dateTime = date.getTime();
		
		if (timeType == 0) {
			if (calendarNow.get(Calendar.DAY_OF_YEAR) == calendarPara.get(Calendar.DAY_OF_YEAR))
				return 0;
			return (calendarNow.get(Calendar.YEAR) - calendarPara.get(Calendar.YEAR)) * 12 + calendarNow.get(Calendar.MONTH) - calendarPara.get(Calendar.MONTH);
		} else {
			float result = nowTime - dateTime;
			float day = 24 * 60 * 60 * 1000;
			// System.out.println("day "+day);
			// result = (result > 0) ? result : -result;
			// System.out.println(result);
			result = result / day;
			Float resultFloat = new Float(result);
			float fraction = result - resultFloat.intValue();
			if (fraction > 0.5) {
				return resultFloat.intValue() + 1;
			} else if (fraction < -0.5) {
				return resultFloat.intValue() - 1;
			} else {
				return resultFloat.intValue();
			}
		}
	}
    /**
     * 判断2个时间相差多少天、多少小时、多少分<br>
     * <br>
     * @param pBeginTime 请假开始时间<br>
     * @param pEndTime 请假结束时间<br>
     * @return String 计算结果<br>
     * @Exception 发生异常<br>
     */
 public static String TimeDiff(String pBeginTime, String pEndTime) throws Exception {
	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
  Long beginL = format.parse(pBeginTime).getTime();
  Long endL = format.parse(pEndTime).getTime();
  Long day = (endL - beginL)/86400000;
  Long hour = ((endL - beginL)%86400000)/3600000;
  Long min = ((endL - beginL)%86400000%3600000)/60000;
  return ("实际请假" + day + "小时" + hour + "分钟" + min);
 }
	/**
	 * 判断当前月份是否是最后12月
	 * 
	 * @return 否是最后12月
	 */
	public static boolean isLastMonth() {
		if (month().equals("12"))
			return true;
		return false;
	}
	
	/**
	 * 获得当前日期的年份
	 * 
	 * @return 当前日期的年份
	 */
	public static Integer year() {
		Calendar calendar = Calendar.getInstance();
		return new Integer(calendar.get(Calendar.YEAR));
	}
	
	/**
	 * 获得当前日期的月份
	 * 
	 * @return 当前日期的月份
	 */
	public static Integer month() {
		Calendar calendar = Calendar.getInstance();
		return new Integer(calendar.get(Calendar.MONTH) + 1);
	}
	
	/**
	 * 获得当前日期的前一个月份,如果当前是1月，返回12
	 * 
	 * @return 当前日期的前一个月份
	 */
	public static Integer beforeMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		if (month == 0) {
			return new Integer(12);
		} else {
			return new Integer(month);
		}
	}
	
	/**
	 * 获得当前日期的后一个月份,如果当前是12月，返回1
	 * 
	 * @return 当前日期的后一个月份
	 */
	public static Integer nextMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		if (month == 11) {
			return new Integer(1);
		} else {
			return new Integer(month + 2);
		}
	}
	
	/**
	 * 获得当前日期的日
	 * 
	 * @return 当前日期的日
	 */
	public static Integer day() {
		Calendar calendar = Calendar.getInstance();
		return new Integer(calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * 获得当前时间的小时
	 * 
	 * @return 当前时间的小时
	 */
	public static Integer hour() {
		Calendar calendar = Calendar.getInstance();
		return new Integer(calendar.get(Calendar.HOUR_OF_DAY));
	}
	
	/**
	 * 获得当前时间的分钟
	 * 
	 * @return 当前时间的分钟
	 */
	public static Integer minute() {
		Calendar calendar = Calendar.getInstance();
		return new Integer(calendar.get(Calendar.MINUTE));
	}
	
	/**
	 * 获得当前时间的秒
	 * 
	 * @return 当前时间的秒
	 */
	public static Integer second() {
		Calendar calendar = Calendar.getInstance();
		return new Integer(calendar.get(Calendar.SECOND));
	}
	
	/**
	 * 获得当前的星期是本月的第几周
	 * 
	 * @return 当前的星期是本月的第几周
	 */
	public static int WeekofMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * 获得当前的日期是本星期的第几天
	 * 
	 * @return 当前的日期是本星期的第几天
	 */
	public static int DayofWeek() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	/**
	 * 获得这个月有几周
	 * 
	 * @return 这个月有几周
	 */
	public static int getMaxWeekofMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * 获得当前的星期是本月的第几周
	 * 
	 * @return 当前的星期是本月的第几周
	 */
	public static int WeekofMonth(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0, 0);
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * 获得当前的日期是本星期的第几天
	 * 
	 * @return 当前的日期是本星期的第几天
	 */
	public static int DayofWeek(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0, 0);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	/**
	 * 获得当前的日期是本星期的第几天
	 * 
	 * @return 当前的日期是本星期的第几天
	 */
	public static int getTodayofWeek() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	/**
	 * 获得这个月有几周
	 * 
	 * @return 这个月有几周
	 */
	public static int getMaxWeekofMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1, 0, 0, 0);
		return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * 得到当前日期的字符串，格式是YYYY-MM-DD
	 * 
	 * @return 当前日期的字符串，格式是YYYY-MM-DD
	 */
	public static String Date() {
		Calendar calendar = Calendar.getInstance();
		StringBuffer date = new StringBuffer();
		date.append(calendar.get(Calendar.YEAR));
		date.append('-');
		date.append(calendar.get(Calendar.MONTH) + 1);
		date.append('-');
		date.append(calendar.get(Calendar.DAY_OF_MONTH));
		return date.toString();
	}
	
	/**
	 * 得到当前日期时间的字符串，格式是YYYY-MM-DD HH:MM:SS
	 * 
	 * @return 当前日期时间的字符串，格式是YYYY-MM-DD HH:MM:SS
	 */
	public static String DateTime() {
		Calendar calendar = Calendar.getInstance();
		StringBuffer date = new StringBuffer();
		date.append(calendar.get(Calendar.YEAR));
		date.append('-');
		date.append(calendar.get(Calendar.MONTH) + 1);
		date.append('-');
		date.append(calendar.get(Calendar.DAY_OF_MONTH));
		date.append(' ');
		date.append(calendar.get(Calendar.HOUR_OF_DAY));
		date.append(':');
		date.append(calendar.get(Calendar.MINUTE));
		date.append(':');
		date.append(calendar.get(Calendar.SECOND));
		return date.toString();
	}
	
	/**
	 * 比较2个日期大小
	 * 
	 * @param startTime
	 *            起始日期
	 * @param endTime
	 *            结束日期
	 * @return 比较2个日期大小。>0：startTime>endTime 0:startTime=endTime <0:startTime<endTime
	 * @throws ParseException
	 */
	public static int compareTwoDate(String startTime, String endTime) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date b = formatter.parse(startTime);
		Date c = formatter.parse(endTime);
		return b.compareTo(c);
		
	}
	
	/**
	 * 比较2个日期大小
	 * 
	 * @param startTime
	 *            起始日期
	 * @param endTime
	 *            结束日期
	 * @return 比较2个日期大小。>0：startTime>endTime 0:startTime=endTime <0:startTime<endTime
	 * @throws ParseException
	 */
	public static int compareTwoDate(Date endTime, Date startTime) {
		
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		
		java.util.Calendar c2 = java.util.Calendar.getInstance();
		
		c1.setTime(endTime);
		
		c2.setTime(startTime);
		
		int result = c1.compareTo(c2);
		
		if (result == 0)
			
			System.out.println("输入时间相等当前时间");
		
		else if (result < 0)
			
			System.out.println("输入时间小于当前时间");
		
		else
			
			System.out.println("输入时间大于当前时间");
		return result;
	}
	
	/**
	 * 比较一个日期是否在指定的日期段中
	 * 
	 * @param nowSysDateTime
	 *            要判断的日期
	 * @param startTime
	 *            起始日期
	 * @param endTime
	 *            结束日期
	 * @return nowSysDateTime在startTime和endTime中返回true
	 * @throws ParseException
	 */
	public static boolean compareThreeDate(String nowSysDateTime, String startTime, String endTime) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date b = formatter.parse(startTime);
		Date a = formatter.parse(nowSysDateTime);
		Date c = formatter.parse(endTime);
		if (a.compareTo(b) >= 0 && a.compareTo(c) <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 得到某一年的某月的前/后一个月是那一年 例如得到2002年1月的前一个月是哪年 (2002,1,-1) =2001.
	 * 
	 * @param year
	 *            时间点的年份
	 * @param month
	 *            时间点的月份
	 * @param pastMonth
	 *            于时间点的月份距离,负数表示以前的时间,正数表示以后的时间
	 * @return 返回年
	 */
	public static int getYearPastMonth(int year, int month, int pastMonth) {
		return year + (int) Math.floor((double) (month - 1 + pastMonth) / (double) 12);
	}
	
	/**
	 * 得到某个月的下几个个月是那个月.
	 * 
	 * @param month
	 *            当前月
	 * @param pastMonth
	 *            和当前月的月数差距
	 * @return 目标月数
	 */
	public static int getMonthPastMonth(int month, int pastMonth) {
		return ((12 + month - 1 + pastMonth) % 12) + 1;
	}
	
	/**
	 * 返回月份的季度数. 0表示非法月份.正常返回1,2,3,4.
	 * 
	 * @param nMonth
	 *            int
	 * @return int
	 */
	public static int getQuarterbyMonth(int nMonth) {
		final int[] monthQuarters = { 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
		
		if (nMonth >= 0 && nMonth <= 12) {
			return monthQuarters[nMonth];
		} else {
			return 0;
		}
	}
	
	/**
	 * 得到当前日期.
	 * 
	 * @return 当前日期的java.sql.Date格式
	 */
	public static java.sql.Date getNowSqlDate() {
		java.util.Date aDate = new java.util.Date();
		return new java.sql.Date(aDate.getTime());
	}
	
	/**
	 * 得到当前日期.
	 * 
	 * @return 当前日期的java.util.Date格式
	 */
	public static java.util.Date getNowDate() {
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 得到当前时间.
	 * 
	 * @return java.sql.Time
	 */
	public static java.sql.Timestamp getNowTimestamp() {
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}
	
	/**
	 * 得到某个时间的字符串显示,格式为yyyy-MM-dd HH:mm.
	 * 
	 * @param aTime
	 *            要分析的时间
	 * @return String
	 */
	public static String getTimeShow(java.sql.Timestamp aTime) {
		if (null == aTime) {
			return "";
		}
		SimpleDateFormat sdfTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
		return sdfTemp.format(aTime);
	}
	
	/**
	 * 按照自己的格式显示时间.
	 * 
	 * @param aTime
	 *            要分析的时间
	 * @param aFormat
	 *            按照SimpleDateFormat的规则的格式
	 * @return 字符串
	 */
	public static String getSelfTimeShow(java.sql.Timestamp aTime, String aFormat) {
		if (null == aTime) {
			return "";
		}
		SimpleDateFormat sdfTemp = new SimpleDateFormat(aFormat, Locale.US);
		return sdfTemp.format(aTime);
	}
	
	/**
	 * 按照自己的格式显示时间.
	 * 
	 * @param aTime
	 *            要分析的时间
	 * @param aFormat
	 *            按照SimpleDateFormat的规则的格式
	 * @return 字符串
	 */
	public static String getSelfTimeShow(java.sql.Date aTime, String aFormat) {
		if (null == aTime) {
			return "";
		}
		SimpleDateFormat sdfTemp = new SimpleDateFormat(aFormat, Locale.US);
		return sdfTemp.format(aTime);
	}
	
	/**
	 * 查询某个月的天数.
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 月的天数
	 */
	public static int getDayinMonth(int year, int month) {
		final int[] dayNumbers = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int result;
		if ((month == 2) && ((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0)) {
			result = 29;
		} else {
			result = dayNumbers[month - 1];
		}
		return result;
	}
	
	/**
	 * 检查日期是否合法.
	 * 
	 * @param ayear
	 *            年
	 * @param amonth
	 *            月
	 * @param aday
	 *            日
	 * @return 合法返回0,非法返回-1,空返回1
	 */
	public static int validDate(int ayear, int amonth, int aday) {
		int isGood = 0;
		if ((ayear == 0) || (amonth == 0) || (aday == 0)) {
			isGood = 1;
		} else {
			int monthDays = getDayinMonth(ayear, amonth);
			if ((aday < 1) || (aday > monthDays)) {
				isGood = -1;
			}
		}
		return isGood;
	}
	
	/**
	 * 检查日期是否合法.
	 * 
	 * @param syear
	 *            年
	 * @param smonth
	 *            月
	 * @param sday
	 *            日
	 * @return 合法返回0,非法返回-1,空返回1
	 */
	public static int validDate(String syear, String smonth, String sday) {
		int ayear, amonth, aday;
		ayear = StringUtils.myparseInt(syear, 0);
		amonth = StringUtils.myparseInt(smonth, 0);
		aday = StringUtils.myparseInt(sday, 0);
		return validDate(ayear, amonth, aday);
	}
	
	/**
	 * 检查一个日期字符串是否合法: 2003-9-5.
	 * 
	 * @param aDateStr
	 *            日期字符串
	 * @return 合法返回0,非法返回-1,空返回1
	 */
	public static int validDate(String aDateStr) {
		if (StringUtils.isTrimEmpty(aDateStr)) {
			return 1;
		}
		String[] aObj = StringUtils.splitString("/-/", aDateStr);
		if (null == aObj) {
			return 1;
		}
		return validDate(aObj[0], aObj[1], aObj[2]);
	}
	
	/**
	 * 检测时间的合法性.
	 * 
	 * @param nHour
	 *            int
	 * @param nMin
	 *            int
	 * @param nSec
	 *            int
	 * @return int 合法返回0,非法返回-1,空返回1
	 */
	public static int validTime(int nHour, int nMin, int nSec) {
		int isGood = 0; // normal
		if ((nHour == 0) || (nMin == 0) || (nSec == 0)) {
			isGood = 1; // empty
		} else {
			if ((nHour > 23 || nHour < 0 || nMin > 59 || nMin < 0 || nSec > 59 || nSec < 0)) {
				isGood = -1; // invalid
			}
		}
		return isGood;
	}
	
	/**
	 * 检查一个日期字符串是否合法: 2003-9-5 13:52:5.
	 * 
	 * @param aDateTimeStr
	 *            日期字符串
	 * @return 合法返回0,非法返回-1,空返回1
	 */
	public static int validDateTime(String aDateTimeStr) {
		if (StringUtils.isTrimEmpty(aDateTimeStr)) {
			return 1;
		}
		String[] aObj = StringUtils.splitString("/ /", aDateTimeStr);
		if (null == aObj) {
			return 1;
		}
		if (aObj.length != 2) {
			return 1;
		}
		if (validDate(aObj[0]) == 0) {
			String[] aTimeObj = StringUtils.splitString("/:/", aObj[1]);
			if (aTimeObj.length == 3) {
				int nHour = StringUtils.myparseInt(aTimeObj[0], 0);
				int nMin = StringUtils.myparseInt(aTimeObj[0], 0);
				int nSec = StringUtils.myparseInt(aTimeObj[0], 0);
				return validTime(nHour, nMin, nSec);
			}
		}
		return -1;
	}
	
	/**
	 * 检查日期是否为空.
	 * 
	 * @param syear
	 *            年
	 * @param smonth
	 *            月
	 * @param sday
	 *            日
	 * @return 为空返回true
	 */
	public static boolean isEmptyDate(String syear, String smonth, String sday) {
		boolean isEmpty = false;
		int ayear, amonth, aday;
		ayear = StringUtils.myparseInt(syear, 0);
		amonth = StringUtils.myparseInt(smonth, 0);
		aday = StringUtils.myparseInt(sday, 0);
		
		if ((ayear == 0) || (amonth == 0) || (aday == 0)) {
			isEmpty = true;
		}
		return isEmpty;
	}
	
	/**
	 * 检查日期是否为空.
	 * 
	 * @param ayear
	 *            年
	 * @param amonth
	 *            月
	 * @param aday
	 *            日
	 * @return 为空返回true
	 */
	public static boolean isEmptyDate(int ayear, int amonth, int aday) {
		boolean isEmpty = false;
		if ((ayear == 0) || (amonth == 0) || (aday == 0)) {
			isEmpty = true;
		}
		return isEmpty;
	}
	
	/**
	 * 得到现在时间的前/后一段时间.
	 * 
	 * @param nSecs
	 *            距离现在时间的秒数
	 * @return Timestamp
	 */
	public static java.sql.Timestamp getPastTime(int nSecs) {
		java.sql.Timestamp ts1 = getNowTimestamp();
		java.sql.Timestamp ts2;
		ts2 = new java.sql.Timestamp(ts1.getTime() - nSecs * 1000);
		return ts2;
	}
	
	/**
	 * 得到距离某个时间一段时间的一个时间.
	 * 
	 * @param aTime
	 *            相对的时间
	 * @param nSecs
	 *            时间距离:秒
	 * @return Timestamp
	 */
	public static java.sql.Timestamp getPastTime(java.sql.Timestamp aTime, int nSecs) {
		java.sql.Timestamp ts2;
		ts2 = new java.sql.Timestamp(aTime.getTime() - nSecs * 1000);
		return ts2;
	}
	
	/**
	 * 检查aDate是不是今天.
	 * 
	 * @param aDate
	 *            分析的日期
	 * @return 是今天返回true
	 */
	public static boolean isToday(java.sql.Date aDate) {
		Calendar aCal1 = Calendar.getInstance();
		aCal1.setTime(aDate);
		
		java.sql.Date date1 = getNowSqlDate();
		
		Calendar aCal2 = Calendar.getInstance();
		aCal2.setTime(date1);
		
		return ((aCal1.get(Calendar.DATE) == aCal2.get(Calendar.DATE)) && (aCal1.get(Calendar.YEAR) == aCal2.get(Calendar.YEAR)) && (aCal1.get(Calendar.MONTH) == aCal2
				.get(Calendar.MONTH)));
	}
	
	/**
	 * 把字符串按照规则转换为日期时间的long值. 如果不合法,则返回今天.
	 * 
	 * @param str
	 *            要分析的字符串
	 * @param pattern
	 *            规则
	 * @throws NullPointerException
	 * @return long
	 */
	public static Long str2DateTime(String str, String pattern) {
		DateFormat dateFmt = new SimpleDateFormat(pattern, Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFmt.parse(str));
			// return c.getTimeInMillis();
			return new Long(calendar.getTime().getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 从一个日期字符串得到时间的long值.
	 * 
	 * @param strTemp
	 *            String
	 * @return long
	 */
	public static Long dateStr2Time(String strTemp) {
		if (null == strTemp || strTemp.length() < 8) {
			return null;
		}
		String sSign = strTemp.substring(4, 5);
		String sPattern = new StringBuffer("yyyy").append(sSign).append("MM").append(sSign).append("dd").toString();
		return str2DateTime(strTemp, sPattern);
	}
	
	/**
	 * 从一个日期时间字符串得到时间的long值. 例如 2004-5-6 23:52:22 ,包含秒
	 * 
	 * @param strTemp
	 *            String
	 * @return long
	 */
	public static Long dateTimeStr2Time(String strTemp) {
		if (null == strTemp || strTemp.length() < 8) {
			return null;
		}
		String sSign = strTemp.substring(4, 5);
		String sPattern = new StringBuffer("yyyy").append(sSign).append("MM").append(sSign).append("dd").append(" hh:mm:ss").toString();
		
		Long aLong = str2DateTime(strTemp, sPattern);
		if (null == aLong) {
			String sShortPattern = new StringBuffer("yyyy").append(sSign).append("MM").append(sSign).append("dd").append(" hh:mm").toString();
			aLong = str2DateTime(strTemp, sShortPattern);
		}
		return aLong;
	}
	
	/**
	 * 把2003-10-11这种字符串转换为时间格式,如果不合法,返回的是当前时间.
	 * 
	 * @param aStr
	 *            要分析的字符串,格式为2003-11-11
	 * @return Timestamp
	 */
	public static java.sql.Timestamp dateStr2Timestamp(String aStr) {
		Long temp = dateStr2Time(aStr);
		if (null == temp) {
			return null;
		}
		return new java.sql.Timestamp(temp.longValue());
	}
	
	/**
	 * 把2003-10-11这种字符串转换为时间格式,如果不合法,返回的是当前时间.
	 * 
	 * @param aStr
	 *            要分析的字符串,格式为2003-11-11
	 * @return Date
	 */
	public static java.util.Date dateStr2Date(String aStr) {
		Long result = dateStr2Time(aStr);
		if (null == result) {
			return null;
		}
		return new java.sql.Date(result.longValue());
		
		// version 1 for check 2004.6.26 by scud
		
		// Calendar aSJ = Calendar.getInstance(); aSJ.setTime(new
		// java.util.Date()); if (StrFunc.calcCharCount('-', aStr) >= 2) { int
		// aYear, aMonth, aDay; String[] aObj = StrFunc.splitString("/-/",
		// aStr); aYear = StrFunc.myparseInt(aObj[0]); aMonth =
		// StrFunc.myparseInt(aObj[1]) - 1; aDay = StrFunc.myparseInt(aObj[2]);
		// if (aYear > 0 && aMonth > -1 && aDay > -1) { aSJ.set(aYear, aMonth,
		// aDay); } } return new java.sql.Date(aSJ.getTime().getTime());
	}
	
	/**
	 * 把2003-10-11 23:43:55这种字符串转换为时间格式,如果不合法,返回的是当前时间.
	 * 
	 * @param aStr
	 * @return Date
	 */
	public static java.util.Date timeStr2Date(String aStr) {
		Long result = dateTimeStr2Time(aStr);
		if (null == result) {
			return null;
		}
		return new java.sql.Date(result.longValue());
	}
	
	/**
	 * 把2003-10-11 23:43:55这种字符串转换为时间格式,如果不合法,返回的是当前时间.
	 * 
	 * @param aStr
	 *            要分析的字符串
	 * @return Timestamp
	 */
	public static java.sql.Timestamp dateTimeStr2Timestamp(String aStr) {
		Long tempVar = dateTimeStr2Time(aStr);
		if (null == tempVar) {
			return null;
		}
		return new java.sql.Timestamp(tempVar.longValue());
	}
	
	/**
	 * 日期字符串转换为日期范围的开始时间,即从该天的0:0:0开始. 如果该数据库类型就是日期类型,则直接getFormDate即可.
	 * 
	 * @param aStr
	 *            String
	 * @return Timestamp
	 */
	public static java.sql.Timestamp dateStr2BeginDateTime(String aStr) {
		if (StringUtils.isTrimEmpty(aStr)) {
			return null;
		} else {
			return dateTimeStr2Timestamp(aStr + " 0:0:0");
		}
	}
	
	/**
	 * 日期字符串转换为日期范围的结束时间,即到该天的23:59:59. 如果该数据库类型就是日期类型,则直接getFormDate即可.
	 * 
	 * @param aStr
	 *            String
	 * @return Timestamp
	 */
	public static java.sql.Timestamp dateStr2EndDateTime(String aStr) {
		if (StringUtils.isTrimEmpty(aStr)) {
			return null;
		} else {
			return dateTimeStr2Timestamp(aStr + " 23:59:59");
		}
	}
	
	/**
	 * 得到日期中的年.
	 * 
	 * @param aDate
	 *            要分析的日期
	 * @return 年
	 */
	public static int getYearFromDate(java.util.Date aDate) {
		Calendar cFF = Calendar.getInstance();
		cFF.setTime(aDate);
		return cFF.get(Calendar.YEAR);
	}
	
	/**
	 * 得到日期中的月.
	 * 
	 * @param aDate
	 *            要分析的日期
	 * @return 月
	 */
	public static int getMonthFromDate(java.util.Date aDate) {
		Calendar cFF = Calendar.getInstance();
		cFF.setTime(aDate);
		return cFF.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 得到日期中的天.
	 * 
	 * @param aDate
	 *            要分析的日期
	 * @return 天
	 */
	public static int getDAYFromDate(java.util.Date aDate) {
		Calendar cFF = Calendar.getInstance();
		cFF.setTime(aDate);
		return cFF.get(Calendar.DATE);
	}
	
	/**
	 * 得到日期中的小时.
	 * 
	 * @param aDate
	 *            要分析的日期
	 * @return 小时
	 */
	public static int getHourFromDate(java.util.Date aDate) {
		Calendar cFF = Calendar.getInstance();
		cFF.setTime(aDate);
		return cFF.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 得到日期中的分钟.
	 * 
	 * @param aDate
	 *            要分析的日期
	 * @return 分钟
	 */
	public static int getMinuteFromDate(java.util.Date aDate) {
		Calendar cFF = Calendar.getInstance();
		cFF.setTime(aDate);
		return cFF.get(Calendar.MINUTE);
	}
	
	/**
	 * 得到日期的星期.
	 * 
	 * @param aDate
	 *            要分析的日期
	 * @return 星期
	 */
	public static int getWeekFromDate(java.util.Date aDate) {
		Calendar cFF = Calendar.getInstance();
		cFF.setTime(aDate);
		return cFF.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 得到当前月的第一天是日期几.
	 * 
	 * @param year
	 *            要分析的年份
	 * @param month
	 *            要分析的月份
	 * @return 星期
	 */
	public static int getFirstDayOfWeek(Integer year, Integer month) {
		Calendar cFF = Calendar.getInstance();
		cFF.set(Calendar.YEAR, year);
		cFF.set(Calendar.MONTH, month - 1);
		cFF.set(Calendar.DATE, 1);
		return cFF.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	/**
	 * 获得当前时间与给定时间的距离
	 * 
	 * @param compMillSecond
	 *            给定时间的与协调世界时 1970 年 1 月 1 日午夜之间的时间差
	 * @return 例如:367Day 59H 56Min
	 */
	public static String diff(long compMillSecond) {
		long diff = System.currentTimeMillis() - compMillSecond;
		long day = diff / ONE_DAY;
		long hour = (diff % ONE_DAY) / ONE_HOUR;
		long min = ((diff % ONE_DAY) % ONE_HOUR) / ONE_MIN;
		return String.valueOf(day) + " Days " + String.valueOf(hour) + " Hours " + String.valueOf(min) + " Mins ";
	}
	
	/**
	 * 得到间隔时间的天数
	 * 
	 * @param overdate
	 * @return
	 */
	public static String getIntevalDays(Date overdate) {
		String temp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date nowdate = new Date();
		long nowlong = nowdate.getTime();
		long overlong = overdate.getTime();
		long templong = overlong - nowlong;
		long day = templong / 1000 / 60 / 60 / 24;
		temp = String.valueOf(day);
		if (overdate.getTime() < nowdate.getTime()) {
			return "-1";
		}
		return temp;
	}
	
	/**
	 * 计算2个日期相隔多少天
	 * 
	 * @param beforeDate
	 *            起始日期
	 * @param afterDate
	 *            结束日期
	 * @return 相差多少天
	 */
	public static Integer getIntevalDays(Date beforeDate, Date afterDate) {
		Integer temp = -1;
		long beforeLong = beforeDate.getTime();
		long afterLong = afterDate.getTime();
		if (beforeLong < afterLong) {
			long templong = afterLong - beforeLong;
			long day = templong / 1000 / 60 / 60 / 24;
			temp = Integer.valueOf(String.valueOf(day));
		}
		return temp;
	}
	
	/**
	 * 得到间隔时间的分钟数或者秒
	 * 
	 * @param overdate
	 * @return
	 */
	public static long getIntevalMinutes(Date overdate) {
		String temp = null;
		
		Date nowdate = new Date();
		long nowlong = nowdate.getTime();
		long overlong = overdate.getTime();
		long templong =  nowlong-overlong;
		long hour = templong / 1000 / 60 / 60 / 24;
		templong = templong - hour * 60 * 60 * 1000;
	    //秒
		long second=templong/1000;
		long minute = second / 60;
		//分钟
		long nowminute = minute + 1;
		temp = String.valueOf(minute + 1);
		if (overdate.getTime()>nowdate.getTime()) {
			return 0;
		}
		
		return second;
	}
	
	/**
	 * 获得给定日期的后几天的日期
	 * 
	 * @param date
	 *            给定的日期
	 * @param days
	 *            过几天的天数
	 * @return 返回给定日期后几天的日期
	 */
	public static synchronized Date getDayAfterDate(Date date, int days) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DAY_OF_YEAR, days);
		return gc.getTime();
	}
	
	public static java.util.Date toDateFmt(String sDate) {
		return toDate(sDate, StringDateConstant.FMT_YMD);
	}
	
	/**
	 * change string to date
	 * 
	 * @param sDate
	 *            the date string sFmt the date format
	 * @return Date object
	 */
	public static java.util.Date toDate(String sDate, String sFmt) {
		SimpleDateFormat sdfFrom = null;
		java.util.Date dt = null;
		try {
			sdfFrom = new SimpleDateFormat(sFmt);
			dt = sdfFrom.parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			sdfFrom = null;
		}
		return dt;
	}
	
	/**
	 * 获得当前星期开始日期
	 * 
	 * @return
	 */
	public static Date getWeekStartDate() {
		Calendar calendar = Calendar.getInstance();
		Date firstDateOfWeek;
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DAY_OF_WEEK, -(dayOfWeek - 1));
		firstDateOfWeek = calendar.getTime();
		calendar.add(Calendar.DAY_OF_WEEK, 6);
		return firstDateOfWeek;
	}
	
	/**
	 * 获得当前星期结束日期
	 * 
	 * @return
	 */
	public static Date getWeekEndDate() {
		Calendar calendar = Calendar.getInstance();
		Date lastDateOfWeek;
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DAY_OF_WEEK, -(dayOfWeek - 1));
		calendar.add(Calendar.DAY_OF_WEEK, 6);
		lastDateOfWeek = calendar.getTime();
		return lastDateOfWeek;
	}
	
	/**
	 * 获得当前月份的第一天
	 * 
	 * @return Date YYYY-MM-DD 00:00:00
	 */
	public static Date getMonthStartDate() {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(new Date(System.currentTimeMillis()));
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return toDayStartDate(df.format(gc.getTime()));
	}
	
	/**
	 * 获得当前月份的最后一天
	 * 
	 * @return Date YYYY-MM-DD 23:59:59
	 */
	public static Date getMonthEndDate() {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat dff = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.DATE, -1);
		return toDayEndDate(df.format(cal.getTime()));
	}
	
	/**
	 * change date object to string
	 * 
	 * @param dt
	 *            date object sFmt the date format
	 * @return the formatted string
	 */
	public static String toString(java.util.Date dt, String sFmt) {
		SimpleDateFormat sdfFrom = null;
		String sRet = null;
		try {
			sdfFrom = new SimpleDateFormat(sFmt);
			sRet = sdfFrom.format(dt).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			sdfFrom = null;
		}
		return sRet;
	}
	
	/**
	 * 获得一个小时之后的时间
	 * 
	 * @return
	 */
	public static String getAfterHour() {
		SimpleDateFormat forma = new SimpleDateFormat("HHmmss");
		java.util.Calendar Cal = java.util.Calendar.getInstance();
		Cal.setTime(new Date());
		Cal.add(java.util.Calendar.HOUR_OF_DAY, +1);
		
		return forma.format(Cal.getTime());
		
	}
	
	/**
	 * 获得当前的小时分秒
	 * 
	 * @return
	 */
	public static String getSysdateNow() {
		return toString(new java.util.Date(System.currentTimeMillis()), "HHmmss");
	}
	
	/**
	 * get system date string
	 * 
	 * @return the formated string of system date
	 */
	public static String getSysdate0() {
		return toString(new java.util.Date(System.currentTimeMillis()), StringDateConstant.FMT_YMD_TRIM);
	}
	
	/**
	 * 返回字符串年月日型
	 * 
	 * @param d
	 * @return
	 */
	public static String date2String(Date d) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd ");
		String result = format1.format(d);
		return result;
	}
	
	/**
	 * 返回字符串年月日型
	 * 
	 * @param d
	 * @return
	 */
	public static String dateYmdString(Date d) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd ");
		String result = format1.format(d);
		return result;
	}
	
	/**
	 * 返回日期型年月日
	 * 
	 * @param d
	 * @return
	 */
	public static Date String2Date(String d) {
		try {
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			Date result = format1.parse(d);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取当月总计天数
	 * 
	 * @param str
	 * @return
	 */
	public static int monthDays(String str) {
		
		Calendar c = Calendar.getInstance();
		int year = Integer.parseInt(str.substring(0, 4));
		int month = Integer.parseInt(str.substring(5, 7)) - 1;
		c.set(c.YEAR, year);
		c.set(c.MONTH, month);
		int i = c.getActualMaximum(c.DAY_OF_MONTH);
		return i;
	}
	
	/**
	 * 获取yyyy-MM-dd日期中的年份
	 * 
	 * @param d
	 * @return
	 */
	public static String getYear(Date d) {
		String s = DateUtil.date2String(d);
		String result = s.substring(0, 4);
		return result;
	}
	
	/**
	 * 获取yyyy-MM-dd日期中的月份
	 * 
	 * @param d
	 * @return
	 */
	public static String getMonth(Date d) {
		String s = DateUtil.date2String(d);
		String result = s.substring(5, 7);
		return result;
	}
	
	/**
	 * 获取yyyy-MM-dd日期中的日份
	 * 
	 * @param d
	 * @return
	 */
	public static String getDay(Date d) {
		String s = DateUtil.date2String(d);
		String result = s.substring(8, 10);
		return result;
	}
	
	/**
	 * 格式化日期为yyyy-MM-dd:HH:mm:ss
	 * 
	 * @param d
	 * @return
	 */
	public static String formateDate(Date d) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = format1.format(d);
		return result;
	}
	
	/**
	 * 格式化字符串转为日期型
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	/**
	 * 格式化字符串转为日期型
	 * 
	 * @param d
	 * @return
	 * @throws ParseException
	 */
	public static Date formateStr(String d) {
		Date str = null;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			str = format1.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 格式化日期为yyyy-MM-dd:HH:mm:ss
	 * 
	 * @param d
	 * @return
	 */
	public static String formatDateToStr(Date d, String format) {
		SimpleDateFormat format1 = new SimpleDateFormat(format);
		String result = format1.format(d);
		return result;
	}
	
	public static String getAfterDay(int day) {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, day);
		String value = doTransform(toString(gc.getTime(), getOraExtendDateTimeFormat()));
		return value;
		
	}
	
	public static Date gethours(String time) {
		Date date = null;
		try {
			date = DF_HMS_TRIM.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
		
	}
	public static String fromDateToString(Date date) { 
		String str = null;
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			str = inputFormat.format(date); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	public static long fromDateStringToLong(String inVal) { // 此方法计算时间毫秒
		Date date = null; // 定义时间类型
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = inputFormat.parse(inVal); // 将字符型转换成日期型
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date.getTime(); // 返回毫秒数
	}
	
	private static String dqsj() { // 此方法用于获得当前系统时间（格式类型 2007-11-6 15:10:58）
		Date date = new Date(); // 实例化日期类型
		String today = date.toLocaleString(); // 获取当前时间
		// System.out.println("获得当前系统时间 "+today); //显示
		return today; // 返回当前时间
	}
	
	public static long getCountSsTime(String Sendtime) {
		
		long endT = fromDateStringToLong(DateUtil.getSystemCurrentDateTime()); // 结束时间
		long startT = fromDateStringToLong(Sendtime); // 开始时间
		long ss = (endT - startT) / (1000); // 共计秒数
		return ss;
	}
	
	public static String getTime(String endtime, String begtime) {// 先传结束时 间，后传开始时间
	
		long endT = fromDateStringToLong(endtime); // 结束时间
		long startT = fromDateStringToLong(begtime); // 开始时间
		
		long ss = (endT - startT) / (1000); // 共计秒数
		// System.out.println(ss);
		int MM = (int) ss / 60; // 共计分钟数
		int hh = (int) ss / 3600; // 共计小时数
		int dd = (int) hh / 24; // 共计天数
		
		// System.out.println("共" + dd + "天 准确时间是：" + hh + " 小时 " + MM + " 分 钟" + ss + " 秒 共计：" + ss * 1000 + " 毫秒");
		String msg = "";
		int k = 0;
		
		if (MM >= 1440) { // 如果大于1天
			int min = MM % 1440;// 取得分钟
			String mi = min / 60 + "小时" + min % 60 + "分";// 取得时，分
			msg = MM / 1440 + "天" + mi;// 取得天，时，分
		}
		if (MM < 60) {// 如果小于1小时
			msg = MM + "分钟";
		}
		if (MM >= 60 && MM < 1440) { // 如果大于一小时，并且小于一天
			msg = MM / 60 + "小时" + MM % 60 + "分";
		}
		msg = String.valueOf(ss);
		return msg;
	}
	
	public static String getTimedifference(String beginTime, String endTime) {
		
		String differenceTim = "";
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			java.util.Date begin = dfs.parse(beginTime);
			java.util.Date end = dfs.parse(beginTime);
			long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
			long day1 = between / (24 * 3600);
			long hour1 = between % (24 * 3600) / 3600;
			long minute1 = between % 3600 / 60;
			long second1 = between % 60 / 60;
			differenceTim = "" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒";
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return differenceTim;
		
	}
	
	public static String getGmtDate(Date date) {
		
		SimpleDateFormat f = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", java.util.Locale.US);
		f.setTimeZone(TimeZone.getTimeZone("GMT"));
		return f.format(date);
	}
	
	
	public static int getMothofDay()
	{
		Calendar   cal = Calendar.getInstance();   
		  cal.set(Calendar.YEAR,getYearFromDate(new Date()));   
		  cal.set(Calendar.MONTH,getMonthFromDate(new Date()));  
		  int   maxDate   =   cal.getActualMaximum(Calendar.DATE);
		  return maxDate;
	}
	/**
	 * 获取几个月之后的时间差
	 * @param day
	 * @return
	 */
	public static long getafterMonthFromSecond(int month) {
		Calendar cFF = Calendar.getInstance();
		cFF.setTime(new Date());
		cFF.add(Calendar.MONTH, month);
		long begin=new Date().getTime();
		long second=(cFF.getTime().getTime()-begin)/1000;
	    return second;
	}
	
	/**
	 * 获取几个月之后的时间差
	 * @param day
	 * @return
	 */
	public static Date getafterMonthFromDate(int month) {
		Calendar cFF = Calendar.getInstance();
		cFF.setTime(new Date());
		cFF.add(Calendar.MONTH, month);
		
	    return cFF.getTime();
	}
	
	/**
	 * 获取指定时间
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getDate(int year,int month,int day){
		if(year < 1970 || year > 2099){
			year = new GregorianCalendar().get(GregorianCalendar.YEAR);
		}
		if(month < 0 || month > 11){
			month = new GregorianCalendar().get(GregorianCalendar.MONTH);
		}
		return getDate(year, month, day, 0, 0, 0);
	}
	/**
	 * 获取指定时间
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param seconds
	 * @return
	 */
	public static Date getDate(int year,int month,int day,int hour,int minute,int seconds){
		GregorianCalendar gCalendar = new GregorianCalendar(year,month,day,hour,minute,seconds);
		return gCalendar.getTime();
	}
	/**
	 * <pre>
	 * 获取指定时间对应的时间差的时间值
	 * @param srcDate 源时间
	 * @param unit 要计算时间差的单位 
	 * 	年：1，月：2，日：5，时：10，分：12，秒：13
	 * @param difference 要计算时间差的差值
	 * @return 指定时间对应的时间差的时间值
	 * </pre>
	 */
	public static Date getDate(Date srcDate,int unit,int difference){
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(srcDate);
		gCalendar.add(unit, difference);
		return gCalendar.getTime();
	}
	
	/**
	 * 获取一周前时间点
	 * @return
	 */
	public static Date getOneWeekBeforeTime(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_WEEK, -7);
		return c.getTime();
	}
	
	/**
	 * 获取几个月前时间点
	 * @return
	 */
	public static Date getFewMonthBeforeTime(int month){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -month);
		return calendar.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(toDefaultString(getOneWeekBeforeTime()));
		System.out.println(toDefaultString(getFewMonthBeforeTime(3)));
	}
}
