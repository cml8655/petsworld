package com.pets.ui;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

public class BottomPopWindow extends PopupWindow {

	public static final int MATCH_PARENT = 100;
	/**
	 * 偏移量为0
	 */
	public static final int NO_OFFSET = 0;

	protected Context context;

	public BottomPopWindow(Context context) {
		super(context);
		this.context = context;
		setWidth(LayoutParams.MATCH_PARENT);
	}

	public BottomPopWindow() {
		setWidth(LayoutParams.MATCH_PARENT);
	}

	/**
	 * 设置高度为view的百分比,最大为100表示为与view同高度，最小为1
	 * 
	 * @param view
	 */
	public void percentHeight(View view, int percent) {

		if (null == view) {
			throw new IllegalArgumentException("view could not be null");
		}
		if (percent < 0) {
			throw new IllegalArgumentException("percent must be positive");
		}

		setHeight((int) (view.getHeight() * ((float) percent / MATCH_PARENT)));
	}

	/**
	 * 将window显示到view左下角
	 * 
	 * @param view
	 */
	public void showAtBottom(View view, int offsetX, int offsetY) {

		if (null == view) {
			throw new IllegalArgumentException("view could not be null");
		}

		showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, offsetX, offsetY);

	}

	/**
	 * 将window显示到view左下角,x，y偏移量为0
	 * 
	 * @param view
	 */
	public void showAtBottom(View view) {

		if (null == view) {
			throw new IllegalArgumentException("view could not be null");
		}

		showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, NO_OFFSET,
				NO_OFFSET);

	}

}
