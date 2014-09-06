package com.img.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.util.Log;

/**
 * 图片压缩工具类
 * 
 * @author Administrator
 * 
 */
public class ImageCompress {

	public static final String CONTENT = "content";
	public static final String FILE = "file";

	private static final int MB = 1024 * 1024;

	/**
	 * 图片压缩参数
	 * 
	 * @author Administrator
	 * 
	 */
	public static class CompressOptions {
		public static final int DEFAULT_WIDTH = 400;
		public static final int DEFAULT_HEIGHT = 800;

		public int maxWidth = DEFAULT_WIDTH;
		public int maxHeight = DEFAULT_HEIGHT;

		/**
		 * 设置为true时：将数据转换成data字节数据返回
		 */
		public boolean hasExtra;
		public byte[] data;
		/**
		 * 压缩后图片保存的文件
		 */
		public File destFile;
		/**
		 * 图片压缩格式,默认为jpg格式
		 */
		public CompressFormat imgFormat = CompressFormat.JPEG;

		/**
		 * 图片压缩比例 默认为30
		 */
		public int quality = 30;

		public Uri uri;

		/**
		 * 文件大小
		 */
		public int maxSize = 1;
	}

	public Bitmap compressFromUri(Context context,
			CompressOptions compressOptions) {

		// uri指向的文件路径
		String filePath = getFilePath(context, compressOptions.uri);

		if (null == filePath) {
			return null;
		}

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		Bitmap temp = BitmapFactory.decodeFile(filePath, options);

		int actualWidth = options.outWidth;
		int actualHeight = options.outHeight;

		int desiredWidth = getResizedDimension(compressOptions.maxWidth,
				compressOptions.maxHeight, actualWidth, actualHeight);
		int desiredHeight = getResizedDimension(compressOptions.maxHeight,
				compressOptions.maxWidth, actualHeight, actualWidth);

		options.inJustDecodeBounds = false;
		options.inSampleSize = findBestSampleSize(actualWidth, actualHeight,
				desiredWidth, desiredHeight);

		Bitmap bitmap = null;

		Bitmap destBitmap = BitmapFactory.decodeFile(filePath, options);

		// If necessary, scale down to the maximal acceptable size.
		if (destBitmap.getWidth() > desiredWidth
				|| destBitmap.getHeight() > desiredHeight) {
			bitmap = Bitmap.createScaledBitmap(destBitmap, desiredWidth,
					desiredHeight, true);
			destBitmap.recycle();
		} else {
			bitmap = destBitmap;
		}

		compressFile(compressOptions, bitmap);

		return bitmap;
	}

	/**
	 * compress file from bitmap with compressOptions
	 * 
	 * @param compressOptions
	 * @param bitmap
	 */
	private void compressFile(CompressOptions compressOptions, Bitmap bitmap) {

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		do {

			os.reset();
			bitmap.compress(compressOptions.imgFormat, compressOptions.quality,
					os);

		} while (os.toByteArray().length / MB >= compressOptions.maxSize);

		// 将数据输出到字节数组中
		if (compressOptions.hasExtra) {
			compressOptions.data = os.toByteArray();
		}

		OutputStream stream = null;

		try {

			// 不需要将数据输出到文件中
			if (null == compressOptions.destFile) {
				return;
			}
			stream = new FileOutputStream(compressOptions.destFile);
			stream.write(os.toByteArray());

		} catch (Exception e) {
			Log.e("ImageCompress", e.getMessage());
		} finally {
			try {
				os.close();
				if (null != stream)
					stream.close();
			} catch (IOException e) {
				Log.e("ImageCompress", e.getMessage());
			}

		}
	}

	private static int findBestSampleSize(int actualWidth, int actualHeight,
			int desiredWidth, int desiredHeight) {
		double wr = (double) actualWidth / desiredWidth;
		double hr = (double) actualHeight / desiredHeight;
		double ratio = Math.min(wr, hr);
		float n = 1.0f;
		while ((n * 2) <= ratio) {
			n *= 2;
		}

		return (int) n;
	}

	private static int getResizedDimension(int maxPrimary, int maxSecondary,
			int actualPrimary, int actualSecondary) {
		// If no dominant value at all, just return the actual.
		if (maxPrimary == 0 && maxSecondary == 0) {
			return actualPrimary;
		}

		// If primary is unspecified, scale primary to match secondary's scaling
		// ratio.
		if (maxPrimary == 0) {
			double ratio = (double) maxSecondary / (double) actualSecondary;
			return (int) (actualPrimary * ratio);
		}

		if (maxSecondary == 0) {
			return maxPrimary;
		}

		double ratio = (double) actualSecondary / (double) actualPrimary;
		int resized = maxPrimary;
		if (resized * ratio > maxSecondary) {
			resized = (int) (maxSecondary / ratio);
		}
		return resized;
	}

	/**
	 * 获取文件的路径
	 * 
	 * @param scheme
	 * @return
	 */
	private String getFilePath(Context context, Uri uri) {

		String filePath = null;

		if (CONTENT.equalsIgnoreCase(uri.getScheme())) {

			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { Images.Media.DATA }, null, null, null);

			if (null == cursor) {
				return null;
			}

			try {
				if (cursor.moveToNext()) {
					filePath = cursor.getString(cursor
							.getColumnIndex(Images.Media.DATA));
				}
			} finally {
				cursor.close();
			}
		}

		// 从文件中选择
		if (FILE.equalsIgnoreCase(uri.getScheme())) {
			filePath = uri.getPath();
		}

		return filePath;
	}
}
