package com.example.actionbar;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listener.TabFragmentListener;

public class MainActivity extends Activity {

	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		// mViewPager = new ViewPager(this);
		// mViewPager.setId(R.id.pager);
		// setContentView(mViewPager);

		// mTabsAdapter = new TabsAdapter(this, mViewPager);
		// mTabsAdapter.addTab(bar.newTab().setText("Simple"),
		// CountingFragment.class, null);
		// mTabsAdapter.addTab(bar.newTab().setText("List"),
		// FragmentPagerSupport.ArrayListFragment.class, null);
		// mTabsAdapter.addTab(bar.newTab().setText("Cursor"),
		// CursorFragment.class, null);
		//
		// if (savedInstanceState != null) {
		// bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
		// }

		tabActionBar();

	}

	private void tabActionBar() {
		Fragment f1 = new TabFragment();
		Fragment f2 = new TabFragment2();

		actionBar = getActionBar();
		actionBar.setTitle("��������");
		actionBar.setDisplayShowTitleEnabled(true);// ���ñ������ʾ
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab tab = actionBar.newTab().setText("first");
		Tab tab1 = actionBar.newTab().setText("second");
		Tab tab2 = actionBar.newTab().setText("second1");
		Tab tab3 = actionBar.newTab().setText("second2");
		Tab tab4 = actionBar.newTab().setText("second3");
		Tab tab5 = actionBar.newTab().setText("second4");

		TabListener tabListener = new TabFragmentListener(this, f1);
		TabListener tabListener2 = new TabFragmentListener(this, f2);

		tab.setTabListener(tabListener);
		tab1.setTabListener(tabListener2);
		tab2.setTabListener(tabListener2);
		tab3.setTabListener(tabListener2);
		tab4.setTabListener(tabListener2);
		tab5.setTabListener(tabListener2);

		actionBar.addTab(tab);
		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
		actionBar.addTab(tab3);
		actionBar.addTab(tab4);
		actionBar.addTab(tab5);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);

		// Set up ShareActionProvider's default share intent
		// MenuItem shareItem = menu.findItem(R.id.action_share);
		// ActionProvider mShareActionProvider = MenuItemCompat
		// .getActionProvider(shareItem);
		// mShareActionProvider.setShareIntent(getDefaultIntent());

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Defines a default (dummy) share intent to initialize the action provider.
	 * However, as soon as the actual content to be used in the intent is known
	 * or changes, you must update the share intent by again calling
	 * mShareActionProvider.setShareIntent()
	 */
	private Intent getDefaultIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		return intent;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String text = "";
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_search2:
			text = "action_search";
			Toast.makeText(this, text, 1).show();
			return true;
			// case R.id.action_settings:
			// text = "action_setting";
			// Toast.makeText(this, text, 1).show();
			// return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
