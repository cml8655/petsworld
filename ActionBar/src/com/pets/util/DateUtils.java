package com.pets.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static final String DATE_LONG_FORMAT = "MM-dd HH:mm";

	private static SimpleDateFormat format = new SimpleDateFormat(
			DATE_LONG_FORMAT);

	/**
	 * 将日期转换成 MM-dd HH:mm 格式
	 * 
	 * @param date
	 * @return date转换后的str值， 如果date==null 返回 ""
	 */
	public static String format(Date date) {
		if (null == date) {
			return "";
		}
		return format.format(date);
	}
}
