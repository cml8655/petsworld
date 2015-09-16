package com.example.materialtest;

import com.example.materialtest.activity.CommonStatusbarActivity;

import android.app.AlertDialog.Builder;
import android.content.Intent;
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
				Snackbar.make(v, "ȷ����ʾ", Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE)
						.setAction("ȷ��", new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				}).show();
			}
		});

		// ����TextInputLayout�¼�
		final TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.til_pwd);

		EditText editText = textInputLayout.getEditText();
		textInputLayout.setHint("Password");

		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				if (s.length() > 4) {
					textInputLayout.setError("���볤�Ȳ��ܴ���4");
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
		// ��viewpager��ͬ���� tabLayout.setupWithViewPager(viewPager);

	}

	/**
	 * ��ʾ���µ�material��ť
	 */
	private void showMaterialDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("ϵͳ��ʾ");
		builder.setMessage("ȷ���˳���");
		builder.setPositiveButton("ȷ��", null);
		builder.setNegativeButton("ȡ��", null);
		builder.create().show();
	}

	public void commonStatusbar(View v) {
		startActivity(new Intent(this, CommonStatusbarActivity.class));
	}

}
