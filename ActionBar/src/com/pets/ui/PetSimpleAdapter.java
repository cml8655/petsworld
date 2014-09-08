package com.pets.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libcore.io.DiskLruCache;
import libcore.io.HttpClientUtils;
import libcore.io.ImageCache;
import libcore.io.LruCacheUtils;
import libcore.io.exception.CannotConnect2NetException;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 继承SimpleAdapter，给adapter中的button添加点击事件
 * 
 * @author Administrator
 * 
 */
public class PetSimpleAdapter extends BaseAdapter implements OnClickListener {

	public static final String ADAPTER_LISTENER = "adapterListener";
	// 控件点击监听器
	private OnClickListener listener;

	private int[] mTo;
	private String[] mFrom;
	private ViewBinder mViewBinder;

	private List<? extends Map<String, ?>> mData;

	private int mResource;
	private int mDropDownResource;
	private LayoutInflater mInflater;
	private OnclickCallback callBack;
	private ArrayList<Integer> listenerItem;
	private Map<View, Integer> itemStorage;

	private ImageCache imgCache = new ImageCache();

	/**
	 * data中如果需要设置点击事件监听器，则需要在map中指定key=listener，value=监听器
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 * @param from
	 * @param to
	 */
	public PetSimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to, ArrayList<Integer> listenItem, OnclickCallback callBack) {
		this.listenerItem = listenItem;
		this.callBack = callBack;
		mData = data;
		mResource = mDropDownResource = resource;
		mFrom = from;
		mTo = to;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		itemStorage = new HashMap<View, Integer>(data.size());
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @see android.widget.Adapter#getView(int, View, ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mResource);
	}

	private View createViewFromResource(int position, View convertView,
			ViewGroup parent, int resource) {
		View v;
		if (convertView == null) {
			v = mInflater.inflate(resource, parent, false);
		} else {
			v = convertView;
		}

		bindView(position, v);

		return v;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent,
				mDropDownResource);
	}

	private void bindView(int position, View view) {
		final Map dataSet = mData.get(position);
		if (dataSet == null) {
			return;
		}
		final ViewBinder binder = mViewBinder;
		final String[] from = mFrom;
		final int[] to = mTo;
		final int count = to.length;

		for (int i = 0; i < count; i++) {
			final View v = view.findViewById(to[i]);
			if (v != null) {
				final Object data = dataSet.get(from[i]);
				String text = data == null ? "" : data.toString();
				if (text == null) {
					text = "";
				}

				boolean bound = false;
				if (binder != null) {
					bound = binder.setViewValue(v, data, dataSet, text);
				}

				if (!bound) {
					if (v instanceof Checkable) {
						if (data instanceof Boolean) {
							((Checkable) v).setChecked((Boolean) data);
						} else if (v instanceof TextView) {
							setViewText((TextView) v, text);
						} else {
							throw new IllegalStateException(v.getClass()
									.getName()
									+ " should be bound to a Boolean, not a "
									+ (data == null ? "<unknown type>"
											: data.getClass()));
						}
					} else if (v instanceof TextView) {
						setViewText((TextView) v, text);
					} else if (v instanceof ImageView) {
						if (data instanceof String) {
							// 从网络下载数据
							loadFromUrl((String) data, v.getContext(),
									(ImageView) v);
						} else if (data instanceof Uri) {
							((ImageView) v).setImageURI((Uri) data);
						} else if (data instanceof Integer) {
							setViewImage((ImageView) v, (Integer) data);
						} else {
							setViewImage((ImageView) v, text);
						}
					} else {
						throw new IllegalStateException(
								v.getClass().getName()
										+ " is not a "
										+ " view that can be bounds by this SimpleAdapter");
					}
				}

			}

		}
		addItemListener(position, view);
	}

	private void addItemListener(int position, final View view) {

		for (int id : listenerItem) {

			View v = view.findViewById(id);

			itemStorage.put(v, position);

			// 设置监听器
			v.setOnClickListener(this);
		}
	}

	/**
	 * 从缓存中加载图片，如果图片不存在，则会从网络上加载，并添加到缓存中
	 * 
	 * @param url
	 * @param context
	 * @param view
	 */
	private void loadFromUrl(final String url, final Context context,
			final ImageView view) {

		DiskLruCache cache = LruCacheUtils.getInstance(context);
		Bitmap bit = imgCache.getBitmapFromCacheFile(cache, url);
		if (null != bit) {
			view.setTag(url);
			view.setImageBitmap(bit);
		} else {
			try {
				imgCache.cacheFile(cache, HttpClientUtils.getClient(context),
						url, view);
			} catch (CannotConnect2NetException e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
						.show();
			}
		}

	}

	public void setViewImage(ImageView v, int value) {
		v.setImageResource(value);
	}

	public void setViewImage(ImageView v, String value) {
		try {
			v.setImageResource(Integer.parseInt(value));
		} catch (NumberFormatException nfe) {
			v.setImageURI(Uri.parse(value));
		}
	}

	public void setViewText(TextView v, String text) {
		v.setText(text);
	}

	public OnClickListener getListener() {
		return listener;
	}

	public void setListener(OnClickListener listener) {
		this.listener = listener;
	}

	public static interface ViewBinder {
		/**
		 * 此接口用于控件自定义设值
		 * 
		 * @param view
		 * @param data
		 * @param dataset
		 *            当前item所有控件的值
		 * @param textRepresentation
		 * @return
		 */
		boolean setViewValue(View view, Object data, Map<String, ?> dataset,
				String textRepresentation);
	}

	/**
	 * 控件点击的回调事件
	 * 
	 * @author Administrator
	 * 
	 */
	public static interface OnclickCallback {
		/**
		 * 当用户点击时触发的事件，会将view的下标（pisition）传入，使用时可以使用data.get(pisition)获取此组件的所有数据
		 * 
		 * @param v
		 * @param position
		 */
		public void onClick(View v, Integer position);
	}

	@Override
	public void onClick(View v) {

		if (null != callBack && null != itemStorage.get(v)) {
			callBack.onClick(v, itemStorage.get(v));
		}
	}

	public Map<View, Integer> getItemStorage() {
		return itemStorage;
	}

	public void setItemStorage(Map<View, Integer> itemStorage) {
		this.itemStorage = itemStorage;
	}

}
