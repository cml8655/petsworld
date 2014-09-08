package com.pets.util;

public interface DialogCallBack {
	/**
	 * 点击取消按钮的回调事件
	 * 
	 * @return
	 */
	void cancel();

	/**
	 * 点击确认按钮的回调事件
	 * 
	 * @return
	 */
	void confirm();
}