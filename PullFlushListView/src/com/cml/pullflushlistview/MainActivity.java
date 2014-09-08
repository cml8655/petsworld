package com.cml.pullflushlistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cml.pullflushlistview.PullRefleshListView.OnFlushListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private PullRefleshListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (PullRefleshListView) findViewById(R.id.list);

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < 30; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text1", "我是value:" + i);
			data.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_1, new String[] { "text1" },
				new int[] { android.R.id.text1 });

		listView.setOnFlushListener(new OnFlushListener() {

			@Override
			public void onPullUp() {
				Log.i("PullRefleshListView", "上拉刷新");
				// listView.onPullUpSuccess();
			}

			@Override
			public void onPullDown() {
				Log.i("PullRefleshListView", "下拉刷新");
				// listView.onPullDownSuccess();
			}

			@Override
			public void onCancel() {
				Log.i("PullRefleshListView", "取消");

			}
		});
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
