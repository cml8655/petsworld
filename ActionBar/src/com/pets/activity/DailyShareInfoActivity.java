package com.pets.activity;

import libcore.io.DiskLruCache;
import libcore.io.HttpClientUtils;
import libcore.io.ImageCache;
import libcore.io.LruCacheUtils;
import libcore.io.exception.CannotConnect2NetException;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.actionbar.R;

/**
 * 宠物日志具体信息，以及评论详情
 * 
 * 
 */
public class DailyShareInfoActivity extends Activity {
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.pets_daily_share_info);

		img = (ImageView) findViewById(R.id.daily_share_info_img);

		String url = getIntent().getStringExtra("img");
		DiskLruCache cache = LruCacheUtils.getInstance(this);
		ImageCache imgCache = new ImageCache();
		Bitmap bit = imgCache.getBitmapFromCacheFile(cache, url);
		img.setImageBitmap(bit);

		setProgressBarIndeterminate(true);
		ActionBar bar = getActionBar();
		bar.setTitle(R.string.back);
		bar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater flater = getMenuInflater();
		flater.inflate(R.menu.daily_share_info_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			// Intent intent = new Intent(this, MainActivity.class);
			//
			// // intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			// // intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			// // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// // | Intent.FLAG_ACTIVITY_NEW_TASK);
			//
			// startActivity(intent);

			DailyShareInfoActivity.this.finish();

			return true;
		}
		if (item.getItemId() == R.id.daily_share_reflush) {
			// new CommonFragmentDialog().show(getFragmentManager(), "登录");
			getPopupMenu(findViewById(R.id.daily_share_reflush)).show();

		}
		return super.onOptionsItemSelected(item);
	}

	private PopupMenu getPopupMenu(View root) {
		PopupMenu menu = new PopupMenu(getApplicationContext(), root);

		Menu menus = menu.getMenu();

		menus.add("aaa").setIcon(R.drawable.cai);
		menus.add("bbb");
		menus.add("ccc");
		menus.add("dd");
		menus.add("333");

		return menu;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			getPopupMenu(findViewById(R.id.daily_share_reflush)).show();
			// new CommonFragmentDialog().show(getFragmentManager(), "登录");
		}

		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Intent intent = new Intent(this, MainActivity.class);
			//
			// // intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			// // intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
			// // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(intent);
			DailyShareInfoActivity.this.finish();
			return false;
		}
		return true;
	}
}
