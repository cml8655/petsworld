package com.pets.center;

import android.os.Handler;

import com.pets.ui.ProgressMenuItem;

/**
 * 点击刷新按钮时触发的事件
 * 
 * @author Administrator
 * 
 */
public interface OnRefleshListener {
	/**
	 * 点击刷新按钮时触发的事件
	 */
	void onReflesh(ProgressMenuItem item);

	/**
	 * 刷新完成后回调事件
	 * 
	 */
	void onComplete();

	/**
	 * 设置回调handler
	 * 
	 * @param handler
	 */
	void setHandler(Handler handler);
}
