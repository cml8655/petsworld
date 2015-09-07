package com.example.materialtest;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.materialtest.SecondActivity.MyAdapter;

public class ThirdAcitity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_third);

		// ��ҳ�����ù�����
		final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		CollapsingToolbarLayout la = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
		la.setTitle("���±���");
		
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);
		
		// ����dumyֵ
		List<String> data = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			data.add("values :" + i);
		}

		MyAdapter adapter = new MyAdapter(data, this);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	/**
	 * recyclerview����¼�����item��xml������
	 * 
	 * @param v
	 */
	public void onItemClick(View v) {
	}
}
