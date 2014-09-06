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
 * ͼƬѹ��������
 * 
 * @author Administrator
 * 
 */
public class ImageCompress {

	public static final String CONTENT = "content";
	public static final String FILE = "file";

	private static final int MB = 1024 * 1024;

	/**
	 * ͼƬѹ������
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
		 * ����Ϊtrueʱ��������ת����data�ֽ����ݷ���
		 */
		public boolean hasExtra;
		public byte[] data;
		/**
		 * ѹ����ͼƬ������ļ�
		 */
		public File destFile;
		/**
		 * ͼƬѹ����ʽ,Ĭ��Ϊjpg��ʽ
		 */
		public CompressFormat imgFormat = CompressFormat.JPEG;

		/**
		 * ͼƬѹ������ Ĭ��Ϊ30
		 */
		public int quality = 30;

		public Uri uri;

		/**
		 * �ļ���С
		 */
		public int maxSize = 1;
	}

	public Bitmap compressFromUri(Context context,
			CompressOptions compressOptions) {

		// uriָ����ļ�·��
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

		// ������������ֽ�������
		if (compressOptions.hasExtra) {
			compressOptions.data = os.toByteArray();
		}

		OutputStream stream = null;

		try {

			// ����Ҫ������������ļ���
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
	 * ��ȡ�ļ���·��
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

		// ���ļ���ѡ��
		if (FILE.equalsIgnoreCase(uri.getScheme())) {
			filePath = uri.getPath();
		}

		return filePath;
	}
}
