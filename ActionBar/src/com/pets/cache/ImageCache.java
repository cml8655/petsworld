package com.pets.cache;

public interface ImageCache<T> {

	/**
	 * 10MB
	 */
	int DEFAULT_MAX_SIZE = 1024 * 1024 * 1024 * 10;

	public void cache(String key, T img);

	public T get(String key);

	public void remove(String key);

	public void removeAll();
}
