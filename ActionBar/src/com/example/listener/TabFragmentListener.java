package com.example.listener;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;


public class TabFragmentListener implements TabListener {
	private Fragment fragment;
	private Context context;

	public TabFragmentListener(Context context, Fragment fragment) {
		this.context = context;
		this.fragment = fragment;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// ft.remove(fragment);
		if (null != fragment)
			ft.detach(fragment);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

		// if (null == fragment) {
		// fragment = Fragment.instantiate(context, clazz.getName());
		// ft.add(android.R.id.content, fragment, null);
		// }
		ft.replace(android.R.id.content, fragment);

		ft.attach(fragment);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}
