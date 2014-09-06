package com.example.slidingmenutest;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.img.util.ImageCompress;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends Activity {

	private static final int PHOTO_REQUEST = 1;

	public void chooseImg(View v) {
		Toast.makeText(this, "点击了按钮", Toast.LENGTH_LONG).show();
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, PHOTO_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK && requestCode == PHOTO_REQUEST) {
			ImageView img = (ImageView) findViewById(R.id.img);

			Uri imgUri = data.getData();

			ImageCompress compress = new ImageCompress();
			ImageCompress.CompressOptions options = new ImageCompress.CompressOptions();
			options.uri = imgUri;
			options.maxWidth = getWindowManager().getDefaultDisplay()
					.getWidth();
			options.maxHeight = getWindowManager().getDefaultDisplay()
					.getHeight();
			options.destFile = new File(
					Environment.getExternalStorageDirectory(), "test1.jpg");
			Bitmap bitmap = compress.compressFromUri(this, options);

			img.setImageBitmap(bitmap);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.activity_horizontal_margin);
		// menu.setShadowDrawable(R.drawable.hdpi);
		menu.setBehindOffsetRes(R.dimen.activity_vertical_margin2);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu);
		 menu.setBackgroundColor(Color.BLUE);

		Toast.makeText(this, "您的安卓版本:" + Build.VERSION.SDK_INT,
				Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
