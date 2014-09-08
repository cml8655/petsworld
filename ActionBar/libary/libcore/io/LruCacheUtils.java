package libcore.io;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

public class LruCacheUtils {
	public static DiskLruCache cache;
	private static final String CACHE_PATH_NAME = "img";
	// 缓存20MB
	private static int CACHE_SIZE = 20 * 1024 * 1024;

	public static DiskLruCache getInstance(Context context) {

		if (null == cache) {
			External external = new External(context);
			try {
				cache = DiskLruCache.open(
						external.getDiskCacheDir(CACHE_PATH_NAME),
						external.getAppVersion(), 1, CACHE_SIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return cache;
	}

	static class External {
		private Context context;

		public External(Context context) {
			this.context = context;
		}

		public File getDiskCacheDir(String uniqueName) {

			String cachePath;
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())
					|| !Environment.isExternalStorageRemovable()) {
				cachePath = context.getExternalCacheDir().getPath();
			} else {
				cachePath = context.getCacheDir().getPath();
			}
			return new File(cachePath + File.separator + uniqueName);
		}

		public int getAppVersion() {
			try {
				PackageInfo info = context.getPackageManager().getPackageInfo(
						context.getPackageName(), 0);
				return info.versionCode;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			return 1;
		}
	}
}
