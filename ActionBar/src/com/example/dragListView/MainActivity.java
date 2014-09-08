package com.example.dragListView;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.actionbar.R;

public class MainActivity extends Activity implements
		FlushOnPullListView.OnRefreshLoadingMoreListener {

	private FlushOnPullListView dlv_main;// 下拉ListView
	private MyAdapter adapter;

	private final static int DRAG_INDEX = 1;// 下拉刷新标识

	private final static int LOADMORE_INDEX = 2;// 加载更多标识

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dlv_main = (FlushOnPullListView) findViewById(R.id.dlv_main);
		adapter = new MyAdapter(this);
		dlv_main.setAdapter(adapter);
		dlv_main.setOnRefreshListener(this);

	}

	/***
	 * 
	 * @author jia
	 */

	class MyAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;

		public MyAdapter(Context context) {
			super();
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.item, null);
				holder.textView = (TextView) convertView
						.findViewById(R.id.tv_tripline_content_item_1);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			return convertView;
		}
	}

	class ViewHolder {
		public TextView textView;
		public TextView textView2;
	}

	/***
	 * 执行类 异步
	 * 
	 * @author zhangjia
	 * 
	 */
	class MyAsyncTask extends AsyncTask<Void, Void, Void> {
		private Context context;
		private int index;// 用于判断是下拉刷新还是点击加载更多

		public MyAsyncTask(Context context, int index) {
			this.context = context;
			this.index = index;
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (index == DRAG_INDEX)
				dlv_main.onRefreshComplete();
			else if (index == LOADMORE_INDEX)
				dlv_main.onLoadMoreComplete(false);
		}

	}

	/***
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		new MyAsyncTask(this, DRAG_INDEX).execute();
	}

	/***
	 * 点击加载更多
	 */
	@Override
	public void onLoadMore() {
		new MyAsyncTask(this, LOADMORE_INDEX).execute();
	}

}