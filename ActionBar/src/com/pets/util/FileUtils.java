package com.pets.util;

import java.io.File;

import android.os.Environment;

public class FileUtils {

	/**
	 * 如果存在外部存储卡，返回外部存储卡，否则返回内存
	 * 
	 * @return
	 */
	public static File getSuitableStorage() {
		if (hasExternalStorage()) {
			return Environment.getExternalStorageDirectory();
		}
		return Environment.getExternalStorageDirectory();
	}

	public static boolean hasExternalStorage() {

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}

		return false;
	}
}
