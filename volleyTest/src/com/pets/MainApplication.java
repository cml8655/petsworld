package com.pets;

import com.pets.cache.ImageCacheManager;
import com.pets.cache.ImageCacheManager.CacheType;
import com.pets.net.RequestManager;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

/**
 * Example application for adding an L1 image cache to Volley.
 * 
 * @author Trey Robinson
 * 
 */
public class MainApplication extends Application {

	private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 10;
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100; // PNG is lossless so
														// quality is ignored
														// but must be provided

	@Override
	public void onLowMemory() {
		Log.i("MainApplication", "law memery happened£¡");
		super.onLowMemory();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("MainApplication", "init the base application!");
		init();
	}

	/**
	 * Intialize the request manager and the image cache
	 */
	private void init() {
		RequestManager.init(this);
		createImageCache();
	}

	/**
	 * Create the image cache. Uses Memory Cache by default. Change to Disk for
	 * a Disk based LRU implementation.
	 */
	private void createImageCache() {
		ImageCacheManager.getInstance().init(this, this.getPackageCodePath(),
				DISK_IMAGECACHE_SIZE, DISK_IMAGECACHE_COMPRESS_FORMAT,
				DISK_IMAGECACHE_QUALITY, CacheType.DISK);
	}
}