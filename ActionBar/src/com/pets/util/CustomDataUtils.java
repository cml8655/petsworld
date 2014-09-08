package com.pets.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;

/**
 * 客户端基础配置信息工具类，使用preference作为存储
 * 
 * @author Administrator
 * 
 */
public class CustomDataUtils {
	private static final Preferences prefer = Preferences.userRoot();

	public static final String DATE_LONG_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_SHORT_FORMAT_YMDHMS = "yyyyMMddHHmmss";

	/**
	 * 获取指定对象的最后一次刷新时间，如果是第一次获取，则返回当前时间. 每次获取后将更新最后一次刷新时间
	 * 
	 * @param key
	 * @param format
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String lastUpdateTime(String key, String format) {

		String lastTime = prefer.get(key, null);

		SimpleDateFormat f = new SimpleDateFormat(format);

		String curr = f.format(new Date(System.currentTimeMillis()));

		prefer.put(key, curr);

		return null == lastTime ? curr : lastTime;
	}
}
