package com.example.materialtest;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * recyclerview demo
 * 
 * @author teamlab
 *
 */
public class RecyclerViewActivity extends AppCompatActivity {

	private RecyclerView recyclerView;
	private Adapter<MyHolder> adapter;
	private List<String> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_recycler);

		final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

		refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		refreshLayout.setSize(SwipeRefreshLayout.LARGE);
		refreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.holo_blue_bright);
		refreshLayout.setProgressViewEndTarget(true, 100);
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				Toast.makeText(getApplicationContext(), "onRefresh", Toast.LENGTH_LONG).show();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						refreshLayout.setRefreshing(false);
					}
				}, 5000);
			}
		});
		// ��仰��Ϊ�ˣ���һ�ν���ҳ���ʱ����ʾ���ؽ�����
		// refreshLayout.setProgressViewOffset(false, 0,
		// (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
		// getResources().getDisplayMetrics()));
		// refreshLayout.setRefreshing(true);

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);

		// ����dumyֵ
		data = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			data.add("values :" + i);
		}

		adapter = new MyAdapter(data, this);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		// ��������
		ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchCallback());
		touchHelper.attachToRecyclerView(recyclerView);

		// ���÷ָ���
		recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

	}

	/**
	 * recyclerview �����϶��¼������һ����¼�
	 * 
	 * @author teamlab
	 *
	 */
	private class ItemTouchCallback extends ItemTouchHelper.Callback {

		@Override
		public int getMovementFlags(RecyclerView recyclerView, ViewHolder holder) {
			return Callback.makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
		}

		@Override
		public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
			// �����϶��¼�
			final int from = viewHolder.getAdapterPosition();
			final int to = target.getAdapterPosition();

			String prevText = data.remove(from);

			data.add(to > from ? to - 1 : to, prevText);
			adapter.notifyItemMoved(from, to);
			return true;
		}

		@Override
		public void onSwiped(ViewHolder holder, int direction) {
			data.remove(holder.getAdapterPosition());
			// direction ��������
			adapter.notifyItemRemoved(holder.getAdapterPosition());
		}

		@Override
		public void onSelectedChanged(ViewHolder viewHolder, int actionState) {

			MyHolder holder = (MyHolder) viewHolder;
			if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
				holder.tv.setBackgroundColor(Color.CYAN);
			}
			super.onSelectedChanged(viewHolder, actionState);
		}

		@Override
		public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
			// ���ݻ�ԭ��������ԭonSelectedChanged�Ĳ���
			super.clearView(recyclerView, viewHolder);
			MyHolder holder = (MyHolder) viewHolder;
			holder.tv.setBackgroundColor(Color.WHITE);
		}
	}

	/**
	 * recyclerview����¼�����item��xml������
	 * 
	 * @param v
	 */
	public void onItemClick(View v) {
		MyHolder holder = (MyHolder) recyclerView.getChildViewHolder(v);
		final int position = holder.getAdapterPosition();

		if (position == RecyclerView.NO_POSITION) {
			return;
		}

		Snackbar.make(v, "ȷ��ɾ����", Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).setAction("ȷ��", new OnClickListener() {

			@Override
			public void onClick(View v) {
				data.remove(position);
				// ���ô˷�������ʹ��Ĭ�ϵĶ���
				adapter.notifyItemRemoved(position);
			}
		}).show();
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
