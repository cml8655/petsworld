package libcore.io;

import libcore.io.exception.CannotConnect2NetException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

/**
 * 初始化时必须先设置context，此方法推荐在app初始化的时候调用
 * 
 * @author 陈孟琳
 * 
 *         2014-8-15
 */
public class HttpClientUtils {

	private static PersistentCookieStore store;

	private static void setContext(Context context) {
		if (null == store) {
			synchronized (HttpClientUtils.class) {
				store = new PersistentCookieStore(context);
			}
		}
	}

	public static AsyncHttpClient getClient(Context context)
			throws CannotConnect2NetException {

		if (!hasConnect(context, ConnectivityManager.TYPE_WIFI)
				&& !hasConnect(context, ConnectivityManager.TYPE_MOBILE)) {

			throw new CannotConnect2NetException("无网络连接，请检查网络");
		}

		AsyncHttpClient client = new AsyncHttpClient();

		if (null == store) {
			setContext(context);
		}

		client.setCookieStore(store);

		return client;
	}

	public static boolean hasConnect(Context context, int type) {

		ConnectivityManager conn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = conn.getActiveNetworkInfo();

		return null != info && info.getType() == type;
	}

}
