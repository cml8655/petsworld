package com.pets.center;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;

import com.example.actionbar.R;
import com.pets.activity.LoginActivity;
import com.pets.activity.SettingsActivity;
import com.pets.ui.ProgressMenuItem;
import com.pets.util.DialogCallBack;
import com.pets.util.DialogUtils;

public class MainActivity extends FragmentActivity {

	private static List<Fragment> tabs;

	private ViewPager pager;

	private ActionBar bar;

	private PetViewPagerAdapter adapter;

	// 刷新菜单栏
	private ProgressMenuItem progressMenuItem;

	/**
	 * 主界面公共部分，点击后刷新页面信息
	 */
	private ProgressMenuItem.ProgressCallback progressCallback = new ProgressMenuItem.ProgressCallback() {

		@Override
		public void onRefleshClick() {

			Fragment fragment = adapter.getItem(bar
					.getSelectedNavigationIndex());

			if (fragment instanceof OnRefleshListener) {

				OnRefleshListener listener = (OnRefleshListener) fragment;

				listener.onReflesh(progressMenuItem);

				listener.setHandler(progressMenuItem.getHandler());
			}

		}
	};

	private OnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
		public void onPageSelected(int position) {
			getActionBar().setSelectedNavigationItem(position);
		};
	};
	/**
	 * 点击返回键退出确认
	 */
	private DialogCallBack exitConfim = new DialogCallBack() {

		@Override
		public void cancel() {
		}

		@Override
		public void confirm() {
			MainActivity.this.finish();
		}
	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.pets_main);

		pager = (ViewPager) findViewById(R.id.pager);

		tabs = new ArrayList<Fragment>(3);
		tabs.add(new PetDailyShareFragment());
		tabs.add(new PostFragment());
		tabs.add(new PostFragment());

		// 初始化actionbar
		this.initActionBar(true, ActionBar.NAVIGATION_MODE_TABS);
		this.initTabs(getResources().getStringArray(R.array.main_pager_str));

		// 初始化viewPager
		adapter = new PetViewPagerAdapter(getSupportFragmentManager(), tabs);

		this.initPager(adapter);
	}

	private void initPager(PagerAdapter adapter) {
		pager.setOnPageChangeListener(pageChangeListener);
		pager.setAdapter(adapter);
	}

	protected void initActionBar(boolean homeAsUp, int mode) {
		bar = getActionBar();
		// bar.setDisplayHomeAsUpEnabled(homeAsUp);
		bar.setNavigationMode(mode);

	}

	/**
	 * 移动可选菜单栏
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pet_config, menu);

		progressMenuItem = (ProgressMenuItem) menu.findItem(
				R.id.config_progress).getActionView();
		progressMenuItem.setProgressCallback(progressCallback);

		MenuItem item = menu.findItem(R.id.news);
		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
						MainActivity.this).setSmallIcon(R.drawable.cai)
						.setContentTitle("系统信息").setContentText("你的账号在其他地方登录了");

				mBuilder.setProgress(100, 40, true);

				Intent resultIntent = new Intent(MainActivity.this,
						MainActivity.class);

				TaskStackBuilder stackBuilder = TaskStackBuilder
						.create(MainActivity.this);

				stackBuilder.addNextIntent(resultIntent);
				PendingIntent resultPendingIntent = stackBuilder
						.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
				mBuilder.setContentIntent(resultPendingIntent);
				NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				// mId allows you to update the notification later on.
				mNotificationManager.notify(1, mBuilder.build());
				return true;
			}
		});

		setIconEnable(menu, true);
		return super.onCreateOptionsMenu(menu);
	}

	// enable为true时，菜单添加图标有效，enable为false时无效。4.0系统默认无效
	private void setIconEnable(Menu menu, boolean enable) {
		try {
			Class<?> clazz = Class
					.forName("com.android.internal.view.menu.MenuBuilder");
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible",
					boolean.class);
			m.setAccessible(true);

			// MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
			m.invoke(menu, enable);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent = null;
		switch (item.getItemId()) {

		case R.id.personCenter:
			return true;

		case R.id.systemConfig:
			intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;

		case R.id.userlogin:
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return true;

		case R.id.newDailyShare:

			// intent = new Intent(Intent.ACTION_GET_CONTENT);
			// intent.setType("image/*");// 文件类型
			// Intent wrapperIntent = Intent.createChooser(intent, "打开方式");
			// startActivityForResult(wrapperIntent, 0);

			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			File out = new File(Environment.getExternalStorageDirectory(),
					"camera.jpg");

			Uri uri = Uri.fromFile(out);

			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

			startActivityForResult(intent, 0);
			// intent = new Intent(this, ComeraActivity.class);
			// startActivity(intent);
			return true;
		}

		// View view = findViewById(item.getItemId());
		//
		// PopupMenu menu = new PopupMenu(getApplicationContext(), view);
		// Menu menus = menu.getMenu();
		//
		// MenuItem personCenter = menus.add(R.string.personCenter);
		// personCenter.setTitle("个人中心");
		// Intent centerIntent = new Intent(this, SystemConfigActivity.class);
		// centerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// personCenter.setIntent(centerIntent);
		// personCenter.setIcon(R.drawable.gnome_preferences_system);
		//
		// MenuItem config = menus.add(R.string.petConfig);
		// config.setIcon(R.drawable.gnome_preferences_system);
		// config.setTitle("系统设置");
		// config.setIntent(new Intent(this, SystemConfigActivity.class));
		//
		// menu.show();
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		Uri url = intent.getData();
		Toast.makeText(this, "获取到的文件位置:" + url.toString(), Toast.LENGTH_LONG)
				.show();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			DialogUtils.showDialog(this, "系统提示", "确认退出？", exitConfim);
			return false;
		}
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * 初始化标签，并设置监听器与文本信息
	 * 
	 * @param tabs
	 * @param texts
	 */
	private void initTabs(String[] texts) {

		TabListener listen = new TabListener() {

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				pager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {

			}
		};

		for (String text : texts) {
			Tab tab = bar.newTab();
			tab.setTabListener(listen);
			tab.setText(text);
			bar.addTab(tab);
		}
	}

}
