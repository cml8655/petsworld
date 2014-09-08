package com.example.actionbar;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment2 extends Fragment {

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.i("fragment", "createview");
		View view = inflater.inflate(R.layout.activity_main2, container, false);
		return view;
	}

	public TabFragment2() {
		Log.i("fragment", "≥ı ºªØfragment");
	}

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("fragment", "create");
		super.onCreate(savedInstanceState);
	}
}
