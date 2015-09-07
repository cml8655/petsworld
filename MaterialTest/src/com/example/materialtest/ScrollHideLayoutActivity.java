package com.example.materialtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ScrollHideLayoutActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_scroll_hide_layout);
		ListView listView = (ListView) findViewById(R.id.scrollView);
		
		
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();

		for (int i = 0; i < 100; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", "value" + i);
			data.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, new String[] { "text" },
				new int[] { android.R.id.text1 });
		listView.setAdapter(adapter);
	}
}
