package com.pets.activity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.actionbar.R;

public class SettingsActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener, OnPreferenceChangeListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(SettingsActivity.this, "点击了", 1).show();

			}
		});

	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		Toast.makeText(SettingsActivity.this, "onPreferenceTreeClick点击了", 1)
				.show();
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Toast.makeText(this, "变了：" + key, 1).show();
		if (key.equals("pref_sync")) {
			Preference pre = findPreference("pref_sync1");
			pre.setTitle("哈哈 被改变了额！");
			pre.setDefaultValue(true);
		}

	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		Toast.makeText(this, "变了：" + newValue, 1).show();
		preference.setSummary("is changed！");
		return true;
	}

}