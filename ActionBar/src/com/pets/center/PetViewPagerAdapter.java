package com.pets.center;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PetViewPagerAdapter extends FragmentStatePagerAdapter {
	private List<Fragment> items;

	public PetViewPagerAdapter(FragmentManager fm, List<Fragment> items) {
		super(fm);
		this.items = items;
	}

	@Override
	public Fragment getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public int getCount() {
		return items.size();
	}

}
