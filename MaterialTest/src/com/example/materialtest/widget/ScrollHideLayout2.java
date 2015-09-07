package com.example.materialtest.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.example.materialtest.R;

/**
 * �������ؿؼ�
 * 
 * @author teamlab
 *
 */
public class ScrollHideLayout2 extends LinearLayout {

	private static final String TAG = ScrollHideLayout2.class.getSimpleName();

	private int scrollViewId = R.id.scrollView;
	private int changeViewId = R.id.changeView;

	private boolean isHide;
	private int changeViewMaxHeight;

	private View changeView;
	private AbsListView scrollView;

	private RectF scrollViewRect = new RectF();

	public ScrollHideLayout2(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollHideLayout2(Context context) {
		this(context, null);
	}

	public ScrollHideLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	// int len = getChildCount();
	//
	// if (null == changeView || null == scrollView) {
	// for (int i = 0; i < len; i++) {
	// View child = getChildAt(i);
	// // �����ؼ�
	// if (child.getId() == scrollViewId && child instanceof AbsListView) {
	// scrollView = (AbsListView) child;
	// setScrollViewRect();
	// }
	// if (child.getId() == changeViewId) {
	// changeView = child;
	// changeView.setMinimumHeight(0);
	//
	// changeViewMaxHeight = changeView.getMeasuredHeight();
	// }
	//
	// }
	// } else {
	// // ���¼�������ؼ���λ��
	// setScrollViewRect();
	// }
	//
	// if (null == changeView || null == scrollView) {
	// throw new
	// IllegalArgumentException("could not foud changeView or scrollView");
	// }
	// }
	//
	// private void setScrollViewRect() {
	// // ��ȡ�����ؼ��ķ�Χ
	// float left = ViewCompat.getX(scrollView);
	// float top = ViewCompat.getY(scrollView);
	// float right = left + scrollView.getMeasuredWidth();
	// float bottom = top + scrollView.getMeasuredHeight();
	//
	// scrollViewRect.left = left;
	// scrollViewRect.top = top;
	// scrollViewRect.right = right;
	// scrollViewRect.bottom = bottom;
	// }
	//
	// private PointF touchPoint = new PointF();
	//

	// @Override
	// public boolean onTouchEvent(MotionEvent ev) {
	//
	// if (!isScrollViewTouch(ev)) {
	// return false;
	// }
	//
	// final android.view.ViewGroup.LayoutParams params =
	// changeView.getLayoutParams();
	//
	// switch (ev.getAction()) {
	//
	// case MotionEvent.ACTION_DOWN:
	// touchPoint.x = ev.getX();
	// touchPoint.y = ev.getY();
	// break;
	//
	// case MotionEvent.ACTION_MOVE:
	//
	// int height = params.height;
	//
	// // �����ؼ��ƶ��¼�
	// float distance = ev.getY() - touchPoint.y;
	//
	// // ���߶ȣ����������϶�
	// if (height >= changeViewMaxHeight && distance > 0) {
	// touchPoint.y = ev.getY();
	// break;
	// }
	// // �Ѿ����� �������ϻ���
	// if (isHide && distance < 0) {
	// touchPoint.y = ev.getY();
	// break;
	// }
	//
	// height = Math.round(height + distance);
	//
	// if (height > changeViewMaxHeight) {
	// height = changeViewMaxHeight;
	// }
	//
	// isHide = false;
	//
	// if (height <= 0 && distance < 0) {
	// height = 0;
	// isHide = true;// �˿ؼ���������
	// // TODO onhide
	// }
	//
	// params.height = height;
	//
	// changeView.requestLayout();
	//
	// touchPoint.x = ev.getX();
	// touchPoint.y = ev.getY();
	//
	// break;
	//
	// case MotionEvent.ACTION_CANCEL:
	// case MotionEvent.ACTION_UP:
	//
	// // �߶ȳ���һ�룬�Զ�����
	// int[] values = null;
	// // ���ϻ���,ʣ��λ�ò���һ��
	// if (params.height <= changeViewMaxHeight / 2) {
	// values = new int[] { params.height, 0 };
	// } else {
	// values = new int[] { params.height, changeViewMaxHeight };
	// }
	//
	// if (null != values) {
	// ValueAnimator anim = ObjectAnimator.ofInt(changeView, "translationY",
	// values);
	// anim.addUpdateListener(new AnimatorUpdateListener() {
	//
	// @Override
	// public void onAnimationUpdate(ValueAnimator animation) {
	// int value = (int) animation.getAnimatedValue();
	// params.height = value;
	// changeView.requestLayout();
	// }
	// });
	// anim.setDuration(250);
	// anim.setTarget(changeView);
	// anim.start();
	// }
	//
	// break;
	// }
	//
	// return true;
	// }

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// // return dector.onTouchEvent(event);
	// return true;
	// }

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG, "action down");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, "action move");
			return false;

		default:
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}

	protected boolean isScrollViewTouch(MotionEvent ev) {

		float x = ev.getX();
		float y = ev.getY();
		return (x >= scrollViewRect.left && x <= scrollViewRect.right) && (y >= scrollViewRect.top && y <= scrollViewRect.bottom);
	}

}
