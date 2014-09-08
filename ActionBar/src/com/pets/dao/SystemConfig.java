package com.pets.dao;

import com.pets.bean.SystemConfigBean;

import android.content.Context;

public interface SystemConfig {
	/**
	 * 自动登陆
	 */
	String AUTO_LOGIN = "autoLogin";
	/**
	 * 记住密码
	 */
	String REMEMBER_ME = "rememberMe";//

	String RECEIVE_WITH_WIFI = "receiveImgWithWifi";
	String RECEIVE_WITH_MOBILE = "receiveImgWithMobile";
	String RECEIVE_IMG_NEVER = "receiveImgNever";

	/**
	 * 是否接受推荐
	 */
	String RECEIVE_RECOMMEND = "recevieRecommend";//

	public void store(String name, Context context, int mode,
			SystemConfigBean bean);

	/**
	 * 存储数据已Context.MODE_PRIVATE的方式
	 * 
	 * @param name
	 * @param context
	 * @param bean
	 */
	public void store(String name, Context context, SystemConfigBean bean);

	public SystemConfigBean getData(String name, Context context);

	/**
	 * 清除所有数据
	 * 
	 * @param name
	 * @param context
	 */
	public void clear(String name, Context context);
}
