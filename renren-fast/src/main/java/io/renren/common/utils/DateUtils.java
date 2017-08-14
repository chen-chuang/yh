package io.renren.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 日期处理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
    
    public static Date parse(String date, String pattern) {
    	Date d = null;
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            try {
				d = df.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        return d;
    }
    
	public static String addByDay(Date date,int days, String ptn) {
		Calendar calendar = new GregorianCalendar(TimeZone.getDefault());
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		SimpleDateFormat format = new SimpleDateFormat(ptn);
		Date date1 = calendar.getTime();
		return format.format(date1);
	}
	
	public static void main(String[] args) {
		System.out.println(addByDay(new Date(), 1, DATE_TIME_PATTERN));
	}
}
