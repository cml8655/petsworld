package com.pets.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.actionbar.R;
import com.pets.bean.SystemConfigBean;
import com.pets.dao.SystemConfig;
import com.pets.dao.SystemConfigDao;
import com.pets.util.DialogCallBack;
import com.pets.util.DialogUtils;

public class SystemConfigActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	private TextView autoLogin;
	private CheckBox autoLoginBox;

	private TextView imageNone;
	private CheckBox imageNoneBox;

	private TextView onlyMobile;
	private CheckBox onlyMobileBox;

	private TextView onlyWifi;
	private CheckBox onlyWifiBox;

	private TextView receiveRecommend;
	private CheckBox receiveRecommentBox;

	private Button clearCache;

	private SystemConfig config = SystemConfigDao.getInstance();

	@Override
	public void onClick(View v) {
		final int id = v.getId();

		switch (id) {
		case R.id.config_auto_login:
			this.toggleBox(autoLoginBox);
			break;

		case R.id.config_img_none:
			this.toggleBox(imageNoneBox);
			break;

		case R.id.config_only_mobile:
			this.toggleBox(onlyMobileBox);
			break;

		case R.id.config_only_wifi:
			this.toggleBox(onlyWifiBox);
			break;

		case R.id.config_receive_recommend:
			this.toggleBox(receiveRecommentBox);
			break;

		case R.id.config_clear_cache:
			DialogUtils.showDialog(this, "确认", "确认清除缓存？", new DialogCallBack() {

				@Override
				public void confirm() {
					showMsg(Toast.LENGTH_LONG, "确认");
				}

				@Override
				public void cancel() {
					showMsg(Toast.LENGTH_LONG, "取消");
				}
			});
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		final int id = buttonView.getId();

		SystemConfigBean bean = getSystemConfig(this, this.getClass()
				.getSimpleName());

		switch (id) {
		case R.id.config_auto_login_checkbox:
			bean.setAutoLogin(isChecked);
			break;

		case R.id.config_img_none_checkbox:
			bean.setReceiveImgNever(isChecked);
			break;

		case R.id.config_only_mobile_checkbox:
			bean.setReceiveWithMobile(isChecked);
			break;

		case R.id.config_only_wifi_checkbox:
			bean.setReceiveWithWifi(isChecked);
			break;

		case R.id.config_receive_recommend_checkbox:
			bean.setRecevieRecommend(isChecked);
			break;
		}

		config.store(this.getClass().getSimpleName(), this, bean);
		this.showMsg(Toast.LENGTH_SHORT, "配置已保存");
	}

	/**
	 * checkbox 将toggle选中状态
	 * 
	 * @param box
	 */
	private void toggleBox(CheckBox box) {
		boolean isSelect = box.isChecked();
		box.setChecked(!isSelect);
	}

	private SystemConfigBean getSystemConfig(Context context, String name) {

		return config.getData(name, context);
	}

	/**
	 * 初始化系统配置
	 */
	private void initSystemConfig() {
		SystemConfigBean config = getSystemConfig(this, this.getClass()
				.getSimpleName());
		checkBox(config);

	}

	/**
	 * 设置组件的选中状态
	 * 
	 * @param bean
	 */
	private void checkBox(SystemConfigBean bean) {

		autoLoginBox.setChecked(bean.isAutoLogin());
		imageNoneBox.setChecked(bean.isReceiveImgNever());
		onlyMobileBox.setChecked(bean.isReceiveWithMobile());
		onlyWifiBox.setChecked(bean.isReceiveWithWifi());
		receiveRecommentBox.setChecked(bean.isRecevieRecommend());

	}

	public void showMsg(int duration, String text) {
		Toast.makeText(this, text, duration).show();
	}

	/**
	 * 初始化界面中所有组件 给组件添加点击事件
	 */
	private void initComponents(OnClickListener listener) {
		autoLogin = (TextView) findViewById(R.id.config_auto_login);
		autoLoginBox = (CheckBox) findViewById(R.id.config_auto_login_checkbox);

		imageNone = (TextView) findViewById(R.id.config_img_none);
		imageNoneBox = (CheckBox) findViewById(R.id.config_img_none_checkbox);

		onlyMobile = (TextView) findViewById(R.id.config_only_mobile);
		onlyMobileBox = (CheckBox) findViewById(R.id.config_only_mobile_checkbox);

		onlyWifi = (TextView) findViewById(R.id.config_only_wifi);
		onlyWifiBox = (CheckBox) findViewById(R.id.config_only_wifi_checkbox);

		receiveRecommend = (TextView) findViewById(R.id.config_receive_recommend);
		receiveRecommentBox = (CheckBox) findViewById(R.id.config_receive_recommend_checkbox);

		clearCache = (Button) findViewById(R.id.config_clear_cache);

		autoLogin.setOnClickListener(listener);
		autoLoginBox.setOnCheckedChangeListener(this);

		imageNone.setOnClickListener(listener);
		imageNoneBox.setOnCheckedChangeListener(this);

		onlyMobile.setOnClickListener(listener);
		onlyMobileBox.setOnCheckedChangeListener(this);

		onlyWifi.setOnClickListener(listener);
		onlyWifiBox.setOnCheckedChangeListener(this);

		receiveRecommend.setOnClickListener(listener);
		receiveRecommentBox.setOnCheckedChangeListener(this);

		clearCache.setOnClickListener(listener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pets_config);

		ActionBar bar = getActionBar();
		bar.setTitle("返回");
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowCustomEnabled(true);

		initComponents(this);
		initSystemConfig();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
