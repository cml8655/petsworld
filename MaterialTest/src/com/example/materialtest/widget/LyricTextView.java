package com.example.materialtest.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class LyricTextView extends View {

	private static final String TAG = "LyricTextView";

	private Paint paint = new Paint();

	LyricTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	public LyricTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public LyricTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LyricTextView(Context context) {
		super(context);
		init();
	}

	private void init() {
		// paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		setBackgroundColor(Color.GRAY);

		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
						len++;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					postInvalidate();
					if (len >= 20) {
						len = 0;
					}
				}
			};
		}.start();
	}

	int len = 0;

	@Override
	protected void onDraw(Canvas canvas) {

		String text = "1込22込込込込込込込込込込込込込込込込込込込込";

		paint.setColor(Color.WHITE);

		String oldText = text.substring(0, len);
		float width = paint.measureText(oldText);

		FontMetrics fontMetrics = paint.getFontMetrics();

		canvas.drawText(text.substring(len), 30 + width, 30, paint);

		paint.setColor(Color.RED);
		canvas.drawText(oldText, 30, 30, paint);
		
		// Log.d(TAG, "text onDraw" + metrics.ascent + "," + metrics.descent +
		// "," + metrics.top + "," + metrics.bottom);
	}

	private Bitmap drawableToBitamp(Drawable drawable, int width) {
		int h = getHeight();
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(width, h, config);

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, h);
		drawable.draw(canvas);
		return bitmap;
	}

}
