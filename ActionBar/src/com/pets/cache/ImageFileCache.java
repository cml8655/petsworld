package com.pets.cache;

import java.io.File;

import android.util.LruCache;

public class ImageFileCache implements ImageCache<File> {

	private LruCache<String, File> lruCache;

	public ImageFileCache() {
		lruCache = new LruCache<String, File>(DEFAULT_MAX_SIZE);
	}

	@Override
	public void cache(String key, File img) {
		lruCache.put(key, img);
	}

	@Override
	public File get(String key) {
		return lruCache.get(key);
	}

	@Override
	public void remove(String key) {
		lruCache.remove(key);
	}

	@Override
	public void removeAll() {
		lruCache.evictAll();
	}

}
