package com.cml.pullflushlistview;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 
 * @author Administrator
 * 
 */
public class PullRefleshListView extends ListView implements OnScrollListener {

	private static final String TAG = PullRefleshListView.class.getSimpleName();

	private static final String UPDATE_TIME_KEY = "update_time_key";
	private static final String UPDATE_TIME_PRE = "last_update_time_pre";
	private static final String FOOT_UPDATE_TIME_KEY = "foot_update_time_key";
	private static final String FOOT_UPDATE_TIME_PRE = "foot_last_update_time_pre";

	private enum PullState {
		// 上拉操作
		PULL_UP,
		// 下拉操作
		PULL_DOWN;
	}

	private enum FlushState {
		FLUSHING // 正在刷新
		, PULLDOWN, PULLUP, CANCEL, DEFAULT
	}

	public static interface OnFlushListener {
		/**
		 * 上拉刷新
		 */
		public void onPullUp();

		/**
		 * 下拉刷新
		 */
		public void onPullDown();

		/**
		 * 取消刷新事件
		 */
		public void onCancel();
	}

	private OnFlushListener onFlushListener;

	private FlushState flushState = FlushState.DEFAULT;

	// 显示日期格式 默认为yyyy-MM-dd HH:mm:ss
	private String updateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat format;

	private Animation animation;
	private Animation reverseAnimation;// 旋转动画，旋转动画之后旋转动画.

	// ====================下拉组件===================
	// 第一次点击的y坐标
	private float touchY = 0;

	private View headView;
	private int headViewHeight;
	// 提示文本
	private TextView tipTextView;
	// 更新时间提示文本
	private TextView updateTimeTextView;
	// 箭头图标
	private ImageView arrowImg;
	private ProgressBar headProgressBar;
	// =============================================

	// ====================上拉组件===================
	// 上拉底部显示
	private View footView;
	private int footViewHeight;
	private TextView footTextView;// 提示文本
	private TextView footUpdateTimeTextView;// 底部时间
	// 箭头图标
	private ImageView footArrowImg;
	private ProgressBar footProgressBar;

	// =============================================

	private float lastMovePositionY;
	private int firstPosition;
	private int totalCount;// listview控件长度
	private int lastVisiblePosition;// 最后可见控件
	private boolean isARecord;

	public PullRefleshListView(Context context) {
		super(context);
		setOnScrollListener(this);
		initHeadView(context);
		initFootView(context);
	}

	public PullRefleshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setOnScrollListener(this);
		initHeadView(context);
		initFootView(context);
	}

	public PullRefleshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnScrollListener(this);
		initHeadView(context);
		initFootView(context);
	}

	private void initFootView(Context context) {

		footView = LayoutInflater.from(context).inflate(R.layout.foot, null);

		footArrowImg = (ImageView) footView
				.findViewById(R.id.foot_arrowImageView);
		footTextView = (TextView) footView.findViewById(R.id.foot_tipsTextView);
		footUpdateTimeTextView = (TextView) footView
				.findViewById(R.id.foot_lastUpdatedTextView);
		footProgressBar = (ProgressBar) footView
				.findViewById(R.id.foot_progressBar);

		addFooterView(footView, null, false);

		measureView(footView);
		footViewHeight = footView.getMeasuredHeight();

		// 在这里我们要将此headView设置到顶部不显示位置.
		footView.setPadding(0, 0, 0, -1 * footViewHeight);

	}

	private void initHeadView(Context context) {

		headView = LayoutInflater.from(context).inflate(R.layout.head, null);

		arrowImg = (ImageView) headView.findViewById(R.id.head_arrowImageView);
		tipTextView = (TextView) headView.findViewById(R.id.head_tipsTextView);
		updateTimeTextView = (TextView) headView
				.findViewById(R.id.head_lastUpdatedTextView);
		headProgressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);

		addHeaderView(headView, null, false);

		measureView(headView);
		headViewHeight = headView.getMeasuredHeight();

		Log.i(TAG, "初始化高度：" + headViewHeight);

		// 在这里我们要将此headView设置到顶部不显示位置.
		headView.setPadding(0, -1 * headView.getMeasuredHeight(), 0, 0);

		initAnimation();
	}

	/***
	 * 初始化动画
	 */
	private void initAnimation() {

		// 旋转动画
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());// 匀速
		animation.setDuration(250);
		animation.setFillAfter(true);// 停留在最后状态.

		// 反向旋转动画
		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(250);
		reverseAnimation.setFillAfter(true);
	}

	/***
	 * 作用：测量 headView的宽和高.
	 * 
	 * @param child
	 */
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

	}

	/**
	 * 隐藏下拉
	 */
	private void dismissTopTips() {
		headView.setPadding(0, -1 * headViewHeight, 0, 0);
	}

	/**
	 * 隐藏底部上拉更新提示信息
	 */
	private void dissmissFootTips() {
		footView.setPadding(0, 0, 0, -1 * footViewHeight);
	}

	/**
	 * 显示顶部提示信息
	 * 
	 * @param parent
	 */
	private void showTopTips(View parent) {
		if (headView.getPaddingTop() != 0)
			headView.setPadding(0, 0, 0, 0);
	}

	private void showFootTips(View parent) {
		if (footView.getPaddingBottom() != 0) {
			footView.setPadding(0, 0, 0, 0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		// 按下
		case MotionEvent.ACTION_DOWN:
			touchY = ev.getY();
			firstPosition = getFirstVisiblePosition();
			lastVisiblePosition = getLastVisiblePosition();
			totalCount = getCount();
			break;
		// 移动
		case MotionEvent.ACTION_MOVE:

			// 正在刷新，跳过操作
			if (flushState == FlushState.FLUSHING) {
				break;
			}
			// 下拉操作判断
			if (firstPosition == 0) {
				updateTimeTextView.setText(getLastUpdateTime(false));
				doPullDown(ev);
			} else if (totalCount - lastVisiblePosition < 2) {
				footUpdateTimeTextView.setText(getLastUpdateTime(true));
				doPullUp(ev);
			}

			lastMovePositionY = ev.getY();
			break;
		// 抬起
		case MotionEvent.ACTION_UP:

			touchY = 0;

			isARecord = false;

			doActionUp();

			break;
		}
		/***
		 * 如果是ListView本身的拉动，那么返回true，这样ListView不可以拖动.
		 * 如果不是ListView的拉动，那么调用父类方法，这样就可以上拉执行.
		 */
		// if (isScroller) {
		// return super.onTouchEvent(ev);
		// } else {
		// return true;
		// }

		return super.onTouchEvent(ev);

	}

	/**
	 * 手指弹出后回调事件
	 */
	private void doActionUp() {

		if (null == onFlushListener) {
			return;
		}

		switch (flushState) {

		case PULLDOWN:// 下拉刷新
			// 回复默认提示信息
			setTipText("正在刷新");

			headView.setPadding(0, 0, 0, 0);

			flushState = FlushState.FLUSHING;

			// 更新刷新时间
			modifyLastUpdateTime(false);

			toggleProgressBar(arrowImg, headProgressBar, false);

			// 回调刷新事件
			onFlushListener.onPullDown();

			break;

		case PULLUP:

			flushState = FlushState.FLUSHING;

			// 更新刷新时间
			modifyLastUpdateTime(true);

			toggleProgressBar(footArrowImg, footProgressBar, false);

			// 回调刷新事件
			onFlushListener.onPullUp();

			break;

		case CANCEL:

			flushState = FlushState.FLUSHING;

			// 隐藏下拉刷新
			dismissTopTips();

			// 隐藏上拉刷新提示
			dissmissFootTips();

			onFlushListener.onCancel();

			flushState = FlushState.DEFAULT;

			break;
		default:
			break;
		}

	}

	// 下拉加载更多，默认为下拉操作
	private PullState lastTopPullState = PullState.PULL_DOWN;

	public void onPullUpSuccess() {

		toggleProgressBar(footArrowImg, footProgressBar, true);

		flushState = FlushState.DEFAULT;

		// 隐藏上拉刷新提示
		dissmissFootTips();
	}

	public void onPullDownSuccess() {

		toggleProgressBar(arrowImg, headProgressBar, true);

		flushState = FlushState.DEFAULT;

		// 隐藏下拉刷新
		dismissTopTips();

	}

	/**
	 * 将textView与progressbar转换显示
	 * 
	 * @param text
	 * @param progress
	 * @param showText
	 *            是否显示文本信息
	 */
	private void toggleProgressBar(ImageView img, ProgressBar progress,
			boolean showImg) {
		
		img.clearAnimation();

		if (showImg) {
			progress.setVisibility(GONE);
			img.setVisibility(VISIBLE);
		} else {
			progress.setVisibility(VISIBLE);
			img.setVisibility(GONE);
		}
	}

	/**
	 * 执行上拉操作
	 * 
	 */
	private void doPullUp(MotionEvent ev) {

		// 按下 往下滑动 忽略
		float distance = ev.getY() - touchY;

		if (distance > 0) {
			return;
		}

		if (!isARecord) {
			isARecord = true;
			showFootTips(this);
		}

		PullState state = null;

		// 往上拉
		if (ev.getY() < lastMovePositionY) {
			setFootText("松开刷新");
			state = PullState.PULL_UP;
			flushState = FlushState.PULLUP;
			footView.setPadding(0, 0, 0,
					(int) (ev.getY() - lastMovePositionY + footViewHeight));
		} else {
			setFootText("取消刷新");
			state = PullState.PULL_DOWN;
			flushState = FlushState.CANCEL;
		}

		toggleFootAnimation(state);
	}

	/**
	 * 执行下拉操作
	 * 
	 */
	private void doPullDown(MotionEvent ev) {

		// 按下 往上滑动 忽略
		float distance = ev.getY() - touchY;

		if (distance < 0) {
			return;
		}

		if (!isARecord) {
			isARecord = true;
			showTopTips(this);
		}

		PullState state = null;

		// 往上拉
		if (ev.getY() < lastMovePositionY) {
			setTipText("取消刷新");
			state = PullState.PULL_UP;
			flushState = FlushState.CANCEL;
		} else {

			headView.setPadding(0,
					(int) (ev.getY() - lastMovePositionY + headViewHeight), 0,
					0);

			setTipText("松开刷新");
			state = PullState.PULL_DOWN;
			flushState = FlushState.PULLDOWN;
		}

		toggleAnimation(state);

	}

	private void toggleFootAnimation(PullState state) {

		if (lastTopPullState == state) {
			return;
		}
		// 上下两次操作不同，旋转动画
		if (PullState.PULL_UP == state) {
			footArrowImg.startAnimation(animation);
		} else {
			footArrowImg.startAnimation(reverseAnimation);
		}
		lastTopPullState = state;
	}

	private void toggleAnimation(PullState state) {

		if (lastTopPullState == state) {
			return;
		}
		// 上下两次操作不同，旋转动画
		if (PullState.PULL_UP == state) {
			arrowImg.startAnimation(animation);
		} else {
			arrowImg.startAnimation(reverseAnimation);
		}
		lastTopPullState = state;
	}

	/**
	 * 设置提示文本提示信息
	 * 
	 * @param tip
	 */
	private void setTipText(String tip) {
		tipTextView.setText(tip);
	}

	private void setFootText(String text) {
		footTextView.setText(text);
	}

	private String getLastUpdateTime(boolean isFoot) {

		SharedPreferences pre = null;

		if (!isFoot) {
			pre = getContext().getSharedPreferences(UPDATE_TIME_PRE,
					Context.MODE_PRIVATE);

			return pre.getString(UPDATE_TIME_KEY, "");

		}

		pre = getContext().getSharedPreferences(FOOT_UPDATE_TIME_PRE,
				Context.MODE_PRIVATE);

		return pre.getString(FOOT_UPDATE_TIME_KEY, "");

	}

	/**
	 * 修改最后一次更新时间
	 */
	private void modifyLastUpdateTime(boolean isFoot) {

		if (null == format) {
			format = new SimpleDateFormat(updateTimeFormat);
		}
		SharedPreferences pre = null;

		if (!isFoot) {
			pre = getContext().getSharedPreferences(UPDATE_TIME_PRE,
					Context.MODE_PRIVATE);

			Editor editor = pre.edit();

			editor.putString(UPDATE_TIME_KEY,
					format.format(new Date(System.currentTimeMillis())));

			editor.commit();
			return;
		}

		pre = getContext().getSharedPreferences(FOOT_UPDATE_TIME_PRE,
				Context.MODE_PRIVATE);

		Editor editor = pre.edit();

		editor.putString(FOOT_UPDATE_TIME_KEY,
				format.format(new Date(System.currentTimeMillis())));

		editor.commit();

	}

	public void setOnFlushListener(OnFlushListener onFlushListener) {
		this.onFlushListener = onFlushListener;
	}

}
