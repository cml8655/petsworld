package com.example.materialtest.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TimerButton extends Button implements OnClickListener {

	private static final String TAG = TimerButton.class.getSimpleName();

	private String textFormat = "请%s秒后重试";
	private int time = 6000;
	private int period = 1000;
	private CharSequence experiodStr;// 超时后显示的内容
	private Drawable bgDrawe;

	private OnClickListener listener;

	private CountDownTimer countTimer;

	@SuppressLint("NewApi")
	public TimerButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	public TimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public TimerButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TimerButton(Context context) {
		super(context);
		init();
	}

	private void init() {
		Log.d(TAG, "timer button init");
		super.setOnClickListener(this);
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		this.listener = l;
	}

	@Override
	public void onClick(View v) {

		Log.d(TAG, "timer button clicked");

		// 默认事件回调
		if (null != listener) {
			listener.onClick(v);
		}
		// 可点击，点击后进行倒计时
		if (this.isClickable()) {

			this.setClickable(false);

			bgDrawe = getBackground();
			setBackgroundColor(Color.GRAY);

			if (null == experiodStr) {
				experiodStr = getText();
			}

			countTimer = new CountDownTimer(time, period) {

				@Override
				public void onTick(long millisUntilFinished) {
					setText(String.format(textFormat, millisUntilFinished / 1000));
				}

				@Override
				public void onFinish() {
					setClickable(true);
					setText(experiodStr);
					setBackgroundDrawable(bgDrawe);
				}
			}.start();
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (null != countTimer) {
			countTimer.cancel();
		}

		Log.d(TAG, "timer button onDetachedFromWindow");
	}

	public String getTextFormat() {
		return textFormat;
	}

	public void setTextFormat(String textFormat) {
		this.textFormat = textFormat;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public CharSequence getExperiodStr() {
		return experiodStr;
	}

	public void setExperiodStr(CharSequence experiodStr) {
		this.experiodStr = experiodStr;
	}

}
