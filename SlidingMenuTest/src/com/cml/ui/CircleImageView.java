package com.cml.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.slidingmenutest.R;

public class CircleImageView extends View {

	private String source;
	private int radius;

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		source = attrs.getAttributeValue("android", "src");
		Log.i("CircleImageView", "画图构造1");
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		source = attrs.getAttributeValue("android", "src");
		Log.i("CircleImageView", "画图构造2");
	}

	public CircleImageView(Context context) {
		super(context);
		Log.i("CircleImageView", "画图构造3");
	}

	@Override
	protected void onDraw(Canvas canvas) {

		Paint paint = new Paint();

		// 消除锯齿
		paint.setAntiAlias(true);

		paint.setStyle(Style.FILL);
		paint.setColor(Color.GRAY);
		paint.setDither(false);

		int radius = 100;

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.head);

		canvas.drawCircle(radius, radius, radius, paint);

		int start = (int) (radius - radius * Math.cos(Math.PI / 4));
		int end = (int) (start + radius * 2 * Math.cos(Math.PI / 4));

		Rect dest = new Rect(start, start, end, end);

		canvas.drawBitmap(bitmap, null, dest, paint);

	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

}
