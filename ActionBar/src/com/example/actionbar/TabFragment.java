package com.example.actionbar;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment extends Fragment {

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.i("fragment", "createview");
		View view = inflater.inflate(R.layout.activity_main3, container, false);

		// TextView textView = (TextView) view.findViewById(R.id.text);
		// textView.setText("fragment:" + text);
		return view;
	}

	public TabFragment() {
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
