package com.pets.ui;

import com.example.actionbar.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class ProgressMenuItem extends LinearLayout implements OnClickListener {

	private ProgressBar progressBar;
	private ImageView img;

	private ProgressCallback progressCallback;

	private  Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			stopProgress();
		}
	};

	public Handler getHandler() {
		return handler;
	}

	public ProgressMenuItem(Context context) {
		super(context);
		init();
		// setOnClickListener(this);
	}

	private ImageView initImageView() {

		img = new ImageView(getContext());

		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		img.setImageResource(R.drawable.gd_action_bar_refresh);
		img.setOnClickListener(this);

		return img;
	}

	private ProgressBar initProgressBar(int max, int visible) {
		progressBar = new ProgressBar(getContext());
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, Gravity.CENTER));
		progressBar.setIndeterminate(true);
		progressBar.setVisibility(visible);
		progressBar.setMax(max);
		progressBar.setScrollBarStyle(android.R.attr.progressBarStyleSmall);
		return progressBar;
	}

	private void init() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		setLayoutParams(params);
		setOrientation(VERTICAL);
		addView(initImageView());
		addView(initProgressBar(100, View.GONE));
	}

	public void stopProgress() {
		if (progressBar.isShown()) {
			progressBar.setVisibility(View.INVISIBLE);
		}
		img.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {

		if (!progressBar.isShown()) {
			progressBar.setVisibility(View.VISIBLE);
		}

		img.setVisibility(View.GONE);

		if (null != this.progressCallback) {
			progressCallback.onRefleshClick();
		}

	}

	public static interface ProgressCallback {
		/**
		 * 当刷新图标点击时触发
		 */
		void onRefleshClick();
	}

	public ProgressCallback getProgressCallback() {
		return progressCallback;
	}

	public void setProgressCallback(ProgressCallback progressCallback) {
		this.progressCallback = progressCallback;
	}

}
