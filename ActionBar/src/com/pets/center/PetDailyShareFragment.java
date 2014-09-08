package com.pets.center;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libcore.io.HttpClientUtils;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import com.example.actionbar.R;
import com.example.dragListView.FlushOnPullListView;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pets.activity.DailyShareInfoActivity;
import com.pets.bean.DailyShareImg;
import com.pets.bean.DailyShareSubject;
import com.pets.bean.User;
import com.pets.dao.BaseDao;
import com.pets.dao.DailySubjectDao;
import com.pets.dummy.DummyDailyShareRecord;
import com.pets.dummy.DummyFactory;
import com.pets.model.JsonResult;
import com.pets.ui.PetSimpleAdapter;
import com.pets.ui.PetSimpleAdapter.OnclickCallback;
import com.pets.ui.ProgressMenuItem;
import com.pets.util.ConnectUtils;
import com.pets.util.DateUtils;

/**
 * 萌宠中心
 * 
 * @author Administrator
 * 
 */
public class PetDailyShareFragment extends Fragment implements
		FlushOnPullListView.OnRefreshLoadingMoreListener, OnRefleshListener {

	private static final String TAG = PetDailyShareFragment.class
			.getSimpleName();

	private BaseDao<DailyShareSubject> dao;

	private static int currSelected = 0;
	private static final String SELECTED = "selectedItemPosition";

	private FlushOnPullListView list;

	private final static int DRAG_INDEX = 1;// 下拉刷新标识

	private final static int LOADMORE_INDEX = 2;// 加载更多标识

	private List<Map<String, Object>> listData;

	private PetSimpleAdapter adapter;
	// 刷新按钮handler
	private Handler flushHanler;

	private OnclickCallback callback = new OnclickCallback() {

		@Override
		public void onClick(View v, Integer position) {
			currSelected = position;
			if (v.getId() == R.id.daily_share_imgview) {
				Intent intent = new Intent();
				Map<String, Object> data = listData.get(position);

				intent.putExtra("img", (String) data.get("img"));
				intent.putExtra("username", (String) data.get("username"));
				intent.putExtra("content", (String) data.get("content"));

				intent.setClass(getActivity(), DailyShareInfoActivity.class);
				startActivity(intent);
			}
		}
	};

	public void onSaveInstanceState(Bundle outState) {

		outState.putInt(SELECTED, currSelected);
		super.onSaveInstanceState(outState);
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dao = new DailySubjectDao(getActivity(), null);
		Log.i(TAG, "oncreate" + currSelected);
	}

	/**
	 * 初始化需要添加监听事件的view id
	 * 
	 * @return
	 */
	private ArrayList<Integer> initItemsListener() {
		ArrayList<Integer> listenerItem = new ArrayList<Integer>();
		listenerItem.add(R.id.daily_share_zan);
		listenerItem.add(R.id.daily_share_text);
		listenerItem.add(R.id.daily_share_pin);
		listenerItem.add(R.id.daily_share_imgview);
		listenerItem.add(R.id.daily_share_cai);

		return listenerItem;
	}

	private View listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		listView = LayoutInflater.from(this.getActivity()).inflate(
				R.layout.pets_daily_share_list, null, false);

		list = (FlushOnPullListView) listView
				.findViewById(R.id.daily_share_list);

		list.setEmptyView(listView.findViewById(R.id.empty));

		listData = getMapData();

		ArrayList<Integer> listenerItem = initItemsListener();

		adapter = new PetSimpleAdapter(getActivity(), listData,
				R.layout.pets_daily_share_list_item, new String[] { "userImg",
						"img", "username", "content" }, new int[] {
						R.id.daily_share_user_img, R.id.daily_share_imgview,
						R.id.daily_share_username, R.id.daily_share_text },
				listenerItem, callback);

		list.setAdapter(adapter);

		list.setOnRefreshListener(this);

		// list.setOnItemClickListener(new ItemClickListener());
		// list.setOnItemLongClickListener(new ItemLongClickListener());

		// if (adapter.getCount() > currSelected) {
		// list.setSelection(currSelected + 1);
		// }
		return listView;
	}

	private List<Map<String, Object>> getMapData() {

		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// 这里需要从数据库获取数据

		List<DailyShareSubject> subjects = dao.getAll(10);

		if (subjects.size() == 0) {
			loadFromNet();
			return list;
		}

		// 异步获取数据
		for (int i = 0; i < subjects.size(); i++) {

			DailyShareSubject record = subjects.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", record.getContent());
			map.put("title", record.getUsername());
			map.put("username", record.getUsername() + "\t"
					+ com.pets.util.DateUtils.format(record.getCreateDate()));
			list.add(map);
		}

		return list;

		// try {
		//
		// Log.i("conn2", "获取数据开始。。。。" + Thread.currentThread().getId());
		//
		// HttpClientUtils.getClient(getActivity()).get(
		// "http://www.baidu.com", new TextHttpResponseHandler() {
		// @Override
		// public void onSuccess(int statusCode, Header[] headers,
		// String responseString) {
		//
		// // 测试时使用
		// List<DummyDailyShareRecord> records = DummyFactory
		// .dailyShareRecordFactory(10, "异步加载的标题哦",
		// "哈哈哈 我是内容啊！", "陈孟琳");
		//
		// // 异步获取数据
		// for (int i = 0; i < records.size(); i++) {
		//
		// DummyDailyShareRecord record = records.get(i);
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("content", record.getContent());
		// map.put("title", record.getTitle());
		// map.put("username",
		// record.getUsername()
		// + "\t"
		// + com.pets.util.DateUtils
		// .format(record
		// .getTime()));
		// list.add(map);
		//
		// }
		// adapter.notifyDataSetChanged();
		// }
		//
		// @Override
		// public void onFailure(int statusCode, Header[] headers,
		// String responseString, Throwable throwable) {
		// Log.i("conn2", "获取数据开始失败"
		// + Thread.currentThread().getId() + "::"
		// + throwable.getMessage());
		// }
		// });
		// Log.i("conn2", "获取数据开始结束" + Thread.currentThread().getId());
		// } catch (CannotConnect2NetException e) {
		// Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG)
		// .show();
		// }
		//
		// return list;
	}

	private void loadFromNet() {

		try {
			RequestParams params = ConnectUtils.getParams(getActivity(),
					"dailyShare");
			HttpClientUtils
					.getClient(getActivity())
					.post("http://192.168.1.110:8080/pp/dailyShare/findSubject.action",
							params,
							new BaseJsonHttpResponseHandler<JsonResult>() {

								@Override
								public void onSuccess(int statusCode,
										Header[] headers,
										String rawJsonResponse,
										JsonResult response) {

									try {

										for (DailyShareSubject s : response
												.getRows()) {

											Map<String, Object> map = new HashMap<String, Object>();

											map.put("username",
													s.getUsername()
															+ "\n"
															+ DateUtils.format(s
																	.getCreateDate()));
											map.put("content", s.getContent());

											User user = s.getUser();
											if (null != user
													&& user.getImg() != null) {
												// 用户头像
												map.put("userImg", user
														.getImg().getUrl());
											}
											// TODO 测试盒
											map.put("userImg",
													"http://192.168.1.110:8080/pp/img/people.png");

											DailyShareImg img = s.getShareImg();

											if (null != img) {
												map.put("img", img.getUrl());
											}

											listData.add(map);
										}
										adapter.notifyDataSetChanged();
									} catch (Exception e) {
										Log.i("conn2",
												"onSuccess数据封装出错："
														+ e.getMessage());
									}

								}

								@Override
								public void onFailure(int statusCode,
										Header[] headers, Throwable throwable,
										String rawJsonData,
										JsonResult errorResponse) {
									Toast.makeText(getActivity(), "网络连接失败",
											Toast.LENGTH_LONG).show();
									Log.i("conn2", "获取数据出错onFailure:"
											+ throwable.getMessage());
								}

								@Override
								protected JsonResult parseResponse(
										String rawJsonData, boolean isFailure)
										throws Throwable {
									GsonBuilder builder = new GsonBuilder()
											.setDateFormat("yyyy-MM-dd HH:mm:ss");

									Log.i("conn2", "parseResponse:"
											+ rawJsonData);
									return builder.create().fromJson(
											rawJsonData, JsonResult.class);
								}

							});
			Log.i("conn2", "获取数据开始结束" + Thread.currentThread().getId());
		} catch (Exception e) {
			Log.i("conn2", "获取数据出错:" + e.getMessage());
		}
	}

	/***
	 * 执行类 异步
	 * 
	 * @author zhangjia
	 * 
	 */
	class MyAsyncTask extends
			AsyncTask<Void, Void, List<DummyDailyShareRecord>> {
		private Context context;
		private int index;// 用于判断是下拉刷新还是点击加载更多

		public MyAsyncTask(Context context, int index) {
			this.context = context;
			this.index = index;
		}

		@Override
		protected List<DummyDailyShareRecord> doInBackground(Void... params) {

			int random = (int) (Math.random() * 20);

			return DummyFactory.dailyShareRecordFactory(random, "from net:"
					+ random);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(List<DummyDailyShareRecord> data) {

			for (DummyDailyShareRecord record : data) {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("content", record.getContent());
				map.put("title", record.getTitle());
				map.put("img", record.getImg());
				map.put("username",
						record.getUsername() + "\t"
								+ DateUtils.format(record.getTime()));
				listData.add(0, map);
			}

			// 刷新显示
			adapter.notifyDataSetChanged();

			Log.i("text", "onPostExecute,返回数据了！");

			if (index == DRAG_INDEX)
				list.onRefreshComplete();
			else if (index == LOADMORE_INDEX)
				list.onLoadMoreComplete(false);

			Toast.makeText(getActivity(), "刷新成功，新加记录" + data.size() + "个",
					Toast.LENGTH_LONG).show();

			// 返回回调信息
			onComplete();
		}
	}

	/***
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		Log.i("text", "onRefresh:开始下拉刷新");
		new MyAsyncTask(this.getActivity(), DRAG_INDEX).execute();
	}

	/***
	 * 点击加载更多
	 */
	@Override
	public void onLoadMore() {
		new MyAsyncTask(this.getActivity(), LOADMORE_INDEX).execute();
	}

	class ItemLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {

			Toast.makeText(getActivity(), "点击了，确定删除这个么？", Toast.LENGTH_SHORT)
					.show();
			// parent.removeViewAt(position);
			return true;
		}

	}

	@Override
	public void onReflesh(ProgressMenuItem item) {
		new MyAsyncTask(this.getActivity(), DRAG_INDEX).execute();
	}

	@Override
	public void onComplete() {
		if (null != flushHanler) {
			if (list.getCount() > 0)
				list.setSelection(0);
			flushHanler.sendEmptyMessage(1);
		}
	}

	@Override
	public void setHandler(Handler handler) {
		flushHanler = handler;
	}

}
