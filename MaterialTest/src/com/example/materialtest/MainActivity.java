package com.example.materialtest;

import android.app.AlertDialog.Builder;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Snackbar
		findViewById(R.id.hello).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Snackbar.make(v, "确定提示", Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).setAction("确定", new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				}).show();
			}
		});

		// 设置TextInputLayout事件
		final TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.til_pwd);

		EditText editText = textInputLayout.getEditText();
		textInputLayout.setHint("Password");

		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				if (s.length() > 4) {
					textInputLayout.setError("密码长度不能大于4");
					textInputLayout.setErrorEnabled(true);
				} else {
					textInputLayout.setErrorEnabled(false);
				}
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		// TabLayout tabLayout = (TabLayout) findViewById(android.R.id.tabs);
		// tabLayout.addTab(tabLayout.newTab().setText("tab1"));
		// tabLayout.addTab(tabLayout.newTab().setText("tab2"));
		// tabLayout.addTab(tabLayout.newTab().setText("tab3"));
		// 与viewpager共同滑动 tabLayout.setupWithViewPager(viewPager);

	}

	/**
	 * 显示最新的material按钮
	 */
	private void showMaterialDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("系统提示");
		builder.setMessage("确定退出？");
		builder.setPositiveButton("确定", null);
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}

}
