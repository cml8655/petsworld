package com.pets.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.actionbar.R;

/**
 * 用户登陆界面
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pets_login);

		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowHomeEnabled(true);

		bar.setTitle("登录");

		findViewById(R.id.otherWayLogin).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {

						View view = findViewById(R.id.login_main);

						PopupWindow window = new PopupWindow(
								getLayoutInflater().inflate(
										R.layout.activity_main2, null),
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT, true);

						window.setOutsideTouchable(true);
						window.setBackgroundDrawable(new BitmapDrawable());

						window.showAtLocation(view, Gravity.LEFT
								| Gravity.BOTTOM, 0, 0);
						// Animation anin = null;
						//
						// if (vv.getVisibility() == View.INVISIBLE) {
						//
						// anin = new TranslateAnimation(0, 0, 400, 0);
						// vv.setVisibility(View.VISIBLE);
						// } else {
						// vv.setVisibility(View.INVISIBLE);
						// anin = new TranslateAnimation(0, 0, 0, 400);
						// }
						//
						// anin.setDuration(1000);
						// vv.startAnimation(anin);

					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pet_login, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		if (item.getItemId() == R.id.userRegister) {
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
