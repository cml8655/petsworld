package com.example.materialtest;

import java.util.ArrayList;
import java.util.List;

import com.example.materialtest.RecyclerViewActivity.MyHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		// CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)
		// findViewById(R.id.collapsing_toolbar);
		// collapsingToolbar.setTitle("cheeseName");

		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);

		// 设置dumy值
		List<String> data = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			data.add("values :" + i);
		}

		MyAdapter adapter = new MyAdapter(data, this);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		setToolbarAsActionbar();

		// 侧滑菜单
		setSlidingmenu();

		setTablayout();

	}

	/**
	 * recyclerview点击事件，在item的xml中配置
	 * 
	 * @param v
	 */
	public void onItemClick(View v) {
		startActivity(new Intent(this, ThirdAcitity.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void setToolbarAsActionbar() {
		Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
		bar.setTitle("welcome");
		setSupportActionBar(bar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// 设置菜单点击事件
		bar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				Toast.makeText(getApplicationContext(), "点击2222：" + arg0.getTitle(), Toast.LENGTH_LONG).show();
				return false;
			}
		});
	}

	private void setTablayout() {
		TabLayout tablayout = (TabLayout) findViewById(R.id.tabs);
		tablayout.addTab(tablayout.newTab().setText("tab1"));
		tablayout.addTab(tablayout.newTab().setText("tab2"));
		tablayout.addTab(tablayout.newTab().setText("tab4"));
		tablayout.addTab(tablayout.newTab().setText("tab6"));
	}

	/**
	 * 侧边菜单栏配置
	 */
	private void setSlidingmenu() {
		// 侧滑菜单
		final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);

		final NavigationView menu = (NavigationView) findViewById(R.id.nv_main_navigation);
		final int len = menu.getMenu().size();
		for (int i = 0; i < len; i++) {
			menu.getMenu().getItem(i).setCheckable(true);
		}
		menu.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {

			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				Toast.makeText(getApplicationContext(), "点击：" + item.getTitle(), Toast.LENGTH_LONG).show();
				for (int i = 0; i < len; i++) {
					menu.getMenu().getItem(i).setChecked(false);
				}
				item.setChecked(true);
				mDrawerLayout.closeDrawers();
				return true;
			}
		});
	}

	public static class MyHolder extends RecyclerView.ViewHolder {

		TextView tv;

		public MyHolder(View itemView) {
			super(itemView);
			tv = (TextView) itemView.findViewById(R.id.tv);
		}

	}

	public static class MyAdapter extends RecyclerView.Adapter<MyHolder> {

		private List<String> data;
		private Context context;

		public MyAdapter(List<String> data, Context context) {
			super();
			this.data = data;
			this.context = context;
		}

		@Override
		public int getItemCount() {
			return data.size();
		}

		@Override
		public void onBindViewHolder(MyHolder holder, int position) {
			String txt = data.get(position);
			holder.tv.setText("item:" + txt);
		}

		@Override
		public MyHolder onCreateViewHolder(ViewGroup container, int position) {
			LayoutInflater inflater = LayoutInflater.from(context);
			MyHolder holder = new MyHolder(inflater.inflate(R.layout.layout_recycler_item, container, false));
			return holder;
		}
	}

}
