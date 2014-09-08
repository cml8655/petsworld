package libcore.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import libcore.io.DiskLruCache.Snapshot;
import libcore.io.exception.CannotConnect2NetException;

import org.apache.http.Header;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pets.bean.DailyShareSubject;

/**
 * 图片缓存类
 * 
 * @author 陈孟琳
 * 
 *         2014-8-7
 */
public class ImageCache {
	private String[] mAllowedContentTypes = new String[] {
			"image/png;charset=utf-8", RequestParams.APPLICATION_OCTET_STREAM,
			"image/jpeg;charset=utf-8", "image/png", "image/*;charset=utf-8",
			"image/jpg;charset=utf-8", "image/gif" };
	private boolean success;

	public ImageCache() {
	}

	public Bitmap getBitmapFromCacheFile(DiskLruCache cache, String key) {

		if (cache.isClosed()) {
			return null;
		}

		Snapshot snapShot;

		try {
			snapShot = cache.get(hashKeyForDisk(key));
			if (snapShot != null) {
				InputStream is = snapShot.getInputStream(0);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				Log.i("conn2", "获取缓存图片成功:" + key);
				return bitmap;
			}
		} catch (IOException e) {
			Log.e("ImageCache", e.getMessage());
		}

		return null;
	}

	public void cacheFile(DiskLruCache cache, List<DailyShareSubject> subjects) {

		if (cache.isClosed()) {
			return;
		}

		for (DailyShareSubject subject : subjects) {
			cacheFile(cache, subject);
		}
	}

	public boolean cacheFile(final DiskLruCache cache, AsyncHttpClient client,
			final String key, final View v) {

		if (cache.isClosed()) {
			return false;
		}

		success = false;// 初始化参数

		try {
			String hasKey = hashKeyForDisk(key);

			final DiskLruCache.Editor editor = cache.edit(hasKey);

			client.post(key,
					new BinaryHttpResponseHandler(mAllowedContentTypes) {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] binaryData) {

							OutputStream outputStream = null;

							try {
								if (statusCode == 202 || statusCode == 200) {

									if (editor != null) {
										Log.i("conn2", "图片下载成功:" + key);
										outputStream = editor
												.newOutputStream(0);
										outputStream.write(binaryData);

										editor.commit();
										cache.flush();
									}
								}
							} catch (IOException e) {
								try {
									editor.abort();
								} catch (IOException e1) {
								}
								e.printStackTrace();
							} finally {
								if (null != outputStream) {
									try {
										outputStream.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
							if (v instanceof ImageView) {
								final ImageView img = (ImageView) v;
								img.post(new Runnable() {

									@Override
									public void run() {
										Bitmap bit = getBitmapFromCacheFile(
												cache, key);
										img.setImageBitmap(bit);
									}
								});
							}
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] binaryData, Throwable error) {

							Log.i("conn2",
									"图片下载失败:" + key + ":" + error.getMessage());

						}
					});

		} catch (IOException e) {
			Log.e("ImageCache", e.getMessage());
		}

		return true;
	}

	public boolean cacheFile(DiskLruCache cache, DailyShareSubject subject) {

		if (cache.isClosed()) {
			return false;
		}

		success = false;// 初始化参数

		try {
			String hasKey = hashKeyForDisk(String.valueOf(subject.getId()));

			DiskLruCache.Editor editor = cache.edit(hasKey);

			if (editor != null) {

				OutputStream outputStream = editor.newOutputStream(0);

				if (downloadUrlToStream(subject.getShareImg().getUrl(),
						outputStream)) {
					editor.commit();
				} else {
					editor.abort();
				}
			}
			cache.flush();

		} catch (IOException e) {
			Log.e("ImageCache", e.getMessage());
		}

		return true;
	}

	public boolean downloadUrlToStream(String urlString,
			final OutputStream outputStream) {

		try {
			HttpClientUtils.getClient(null).post(urlString,
					new BinaryHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] data) {

							try {
								if (statusCode == 202 || statusCode == 200) {

									outputStream.write(data);
									success = true;
								}
							} catch (IOException e) {
								Log.e("ImageCache", e.getMessage());
								success = false;
							} finally {
								if (null != outputStream) {
									try {
										outputStream.close();
									} catch (IOException e) {
										Log.e("ImageCache", e.getMessage());
									}
								}
							}
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] binaryData, Throwable error) {
							success = false;
							Log.e("ImageCache", error.getMessage());
						}
					});
		} catch (CannotConnect2NetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return success;
	}

	private String hashKeyForDisk(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

}
