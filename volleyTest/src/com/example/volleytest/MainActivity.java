package com.example.volleytest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.pets.cache.ImageCacheManager;
import com.pets.net.RequestManager;

public class MainActivity extends Activity {

	private RequestQueue queue;

	public void downImg(View v) {

		ImageLoader loader = new ImageLoader(queue, new ImageCache() {

			@Override
			public void putBitmap(String arg0, Bitmap arg1) {
				Toast.makeText(getApplicationContext(),
						"putBitmapº”‘ÿª∫¥Ê" + Thread.currentThread().getId(),
						Toast.LENGTH_LONG).show();
			}

			@Override
			public Bitmap getBitmap(String arg0) {
				Toast.makeText(getApplicationContext(),
						"getBitmapª∫¥Ê" + Thread.currentThread().getId(),
						Toast.LENGTH_LONG).show();
				return null;
			}
		});

		ImageListener listener = ImageLoader.getImageListener((ImageView) v,
				R.drawable.abc_ab_bottom_transparent_light_holo,
				R.drawable.abc_ab_bottom_transparent_dark_holo);

		loader.get(
				"http://img.blog.csdn.net/20140427202306328?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ3VvbGluX2Jsb2c=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast",
				listener);

	}

	@SuppressWarnings("static-access")
	public void downNetImg(View v) {
		ImageLoader loader = ImageCacheManager.getInstance().getImageLoader();
		ImageListener listener = loader.getImageListener((ImageView) v,
				R.drawable.abc_ab_bottom_solid_light_holo,
				R.drawable.ic_launcher);

		loader.get(
				"http://image6.tuku.cn/pic/wallpaper/meinv/guozimmqingchunshishaozhubanzhutidatu/001.jpg",
				listener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.d("TAG", "out_threadId:" + Thread.currentThread().getId());
		queue = RequestManager.getRequestQueue();

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
