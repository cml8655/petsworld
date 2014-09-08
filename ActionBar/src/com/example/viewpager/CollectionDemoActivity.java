package com.example.viewpager;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.example.actionbar.R;

public class CollectionDemoActivity extends FragmentActivity {

	private DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	private ViewPager mViewPager;
	private ActionBar actionBar;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection_demo);

		actionBar = getActionBar();
		// Specify that tabs should be displayed in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(
				getSupportFragmentManager(), 3);

		mViewPager = (ViewPager) findViewById(R.id.pager);

		mViewPager.setAdapter(mDemoCollectionPagerAdapter);
		// mViewPager.setOffscreenPageLimit(20);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						getActionBar().setSelectedNavigationItem(position);
					}
				});

		// Create a tab listener that is called when the user changes tabs.
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				mViewPager.setCurrentItem(tab.getPosition());
			}

			public void onTabUnselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
			}

			public void onTabReselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
			}
		};

		// Add 3 tabs, specifying the tab's text and TabListener
		for (int i = 0; i < 3; i++) {
			actionBar.addTab(actionBar.newTab().setText("Tab " + (i + 1))
					.setTabListener(tabListener));
		}
	}
}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

	private int counts;

	public DemoCollectionPagerAdapter(FragmentManager fm, int counts) {
		super(fm);
		this.counts = counts;

	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new DemoObjectFragment();
		Bundle args = new Bundle();
		// Our object is just an integer :-P
		args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return counts;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return null;
	}
}

// Instances of this class are fragments representing a single
// object in our collection.
class DemoObjectFragment extends Fragment {
	public static final String ARG_OBJECT = "object";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// The last two arguments ensure LayoutParams are inflated
		// properly.
		View rootView = inflater.inflate(R.layout.activity_main2, container,
				false);
		// Bundle args = getArguments();
		// ((TextView)
		// rootView.findViewById(android.R.id.text1)).setText(Integer
		// .toString(args.getInt(ARG_OBJECT)));
		return rootView;
	}
}