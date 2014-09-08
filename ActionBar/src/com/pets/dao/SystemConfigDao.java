package com.pets.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.pets.bean.SystemConfigBean;

/**
 * 系统配置dao，使用sharedpreference进行数据存储
 * 
 * @author Administrator
 * 
 */
public class SystemConfigDao implements SystemConfig {
	private static final SystemConfig instance = new SystemConfigDao();

	public static SystemConfig getInstance() {
		return instance;
	}

	private SystemConfigDao() {
	}

	@Override
	public void store(String name, Context context, int mode,
			SystemConfigBean bean) {
		SharedPreferences pre = context.getSharedPreferences(name, mode);

		Editor editor = pre.edit();

		editor.putBoolean(AUTO_LOGIN, bean.isAutoLogin());
		editor.putBoolean(RECEIVE_RECOMMEND, bean.isRecevieRecommend());
		editor.putBoolean(REMEMBER_ME, bean.isRememberMe());
		editor.putBoolean(RECEIVE_WITH_MOBILE, bean.isReceiveWithMobile());
		editor.putBoolean(RECEIVE_WITH_WIFI, bean.isReceiveWithWifi());
		editor.putBoolean(RECEIVE_IMG_NEVER, bean.isReceiveImgNever());

		editor.commit();
	}

	@Override
	public void store(String name, Context context, SystemConfigBean bean) {
		this.store(name, context, Context.MODE_PRIVATE, bean);
	}

	@Override
	public SystemConfigBean getData(String name, Context context) {

		SharedPreferences pre = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		SystemConfigBean bean = new SystemConfigBean();

		boolean autoLogin = pre.getBoolean(AUTO_LOGIN, false);
		bean.setAutoLogin(autoLogin);

		boolean receiveRecommend = pre.getBoolean(RECEIVE_RECOMMEND, true);
		bean.setRecevieRecommend(receiveRecommend);

		boolean withMobile = pre.getBoolean(RECEIVE_WITH_MOBILE, true);
		bean.setReceiveWithMobile(withMobile);

		boolean withWifi = pre.getBoolean(RECEIVE_WITH_WIFI, true);
		bean.setReceiveWithWifi(withWifi);

		boolean neverImg = pre.getBoolean(RECEIVE_IMG_NEVER, false);
		bean.setReceiveImgNever(neverImg);

		boolean rememberMe = pre.getBoolean(REMEMBER_ME, false);
		bean.setRememberMe(rememberMe);

		return bean;
	}

	@Override
	public void clear(String name, Context context) {

		SharedPreferences pre = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);

		Editor e = pre.edit();

		e.clear();
		e.commit();
	}
}
