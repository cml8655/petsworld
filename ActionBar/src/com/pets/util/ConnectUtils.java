package com.pets.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.loopj.android.http.RequestParams;

/**
 * 网络数据工具类
 * 
 * @author Administrator
 * 
 */
public class ConnectUtils {
	public static final String BASE_URL = "http://192.168.1.110:8080/pp/";

	public static final String DAILY_SHARE_URL = BASE_URL
			+ "dailyShare/findSubject.action";

	public static RequestParams getParams(Context context, String key) {

		RequestParams params = new RequestParams();
		SharedPreferences pre = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Log.i("conn2", "获取偏好设置：" + pre + ":" + pre.contains("rows"));
		SharedPreferences.Editor editor = pre.edit();

		if (!pre.contains("rows")) {

			editor.putInt("rows", 1);
			editor.putInt("page", 1);

			params.put("rows", 2);
			params.put("page", 1);
			editor.commit();
			return params;
		}

		editor.putInt("rows", pre.getInt("rows", 20) + 1);

		editor.commit();

		params.put("rows", pre.getInt("rows", 20));
		params.put("page", 1);
		return params;
	}

	public enum RequestType {
		DAILY_SHARE, USER
	}
}
