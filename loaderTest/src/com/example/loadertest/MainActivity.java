package com.example.loadertest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {

	private static final String TAG = "myTag";
	LoaderManager manager;
	AsyncTaskLoader<String> loader;

	public void loadData(View v) {
		if (loader.isStarted()) {
			Log.i(TAG, "isstarted:");

			loader.reset();
		}
		loader.forceLoad();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.i(TAG, "初始化：," + Thread.currentThread().getId());

		manager = getSupportLoaderManager();
		manager.initLoader(1, null, new LoaderCallbacks<String>() {

			@Override
			public Loader<String> onCreateLoader(int arg0, Bundle arg1) {

				Log.i(TAG, "onCreateLoader,thread Id:"
						+ Thread.currentThread().getId());

				loader = new MyLoader(MainActivity.this);
				return loader;
			}

			@Override
			public void onLoadFinished(Loader<String> arg0, String arg1) {
				Log.i(TAG, "onLoadFinished," + arg1
						+ Thread.currentThread().getId());
			}

			@Override
			public void onLoaderReset(Loader<String> arg0) {
				Log.i(TAG, "onLoaderReset," + arg0
						+ Thread.currentThread().getId());
			}
		});
		Log.i(TAG, "启动结束：," + Thread.currentThread().getId());
	}

	static class MyLoader extends AsyncTaskLoader<String> {

		private String oldData;

		public MyLoader(Context context) {
			super(context);
		}

		@Override
		public void deliverResult(String data) {

			Log.i(TAG, "deliverResult，thread Id:"
					+ Thread.currentThread().getId() + ",data:" + data);

			if (isReset() && data != null) {
				// 如果使用cursor则需要将流对象关闭
				onReleaseResources(data);
			}

			if (isStarted()) {
				super.deliverResult(data);
			}

		}

		/**
		 * Handles a request to cancel a load.
		 */
		@Override
		public void onCanceled(String data) {

			super.onCanceled(data);
			// At this point we can release the resources associated with 'apps'
			// if needed.
			onReleaseResources(data);
		}

		/**
		 * Handles a request to completely reset the Loader.
		 */
		@Override
		protected void onReset() {

			super.onReset();

			// Ensure the loader is stopped
			onStopLoading();

			// At this point we can release the resources associated with
			// 'apps'if needed.
			if (oldData != null) {
				onReleaseResources(oldData);
				oldData = null;
			}

		}

		@Override
		public String dataToString(String data) {
			Log.i(TAG, "dataToString，thread Id:"
					+ Thread.currentThread().getId());
			return data;
		}

		@Override
		protected void onStartLoading() {
			Log.i(TAG, "onStartLoading，thread Id:"
					+ Thread.currentThread().getId());
		}

		@Override
		public String loadInBackground() {
			Log.i(TAG, "loadInBackground，thread Id:"
					+ Thread.currentThread().getId());
			return "哈哈哈";
		}

		/**
		 * Helper function to take care of releasing resources associated with
		 * an actively loaded data set.
		 */
		protected void onReleaseResources(String data) {
			// For a simple List<> there is nothing to do. For something
			// like a Cursor, we would close it here.
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
