package com.example.materialtest.activity;

import com.example.materialtest.R;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * 沉浸式菜单栏的使用
 * 
 * @author teamlab
 *
 */
public class CommonStatusbarActivity extends AppCompatActivity {
	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_statubar);

		// copy from http://blog.csdn.net/jdsjlzx/article/details/46778631
		// 兼容4.4以下 http://blog.csdn.net/loongggdroid/article/details/47417233
		// 19以后才有沉浸式菜单
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}

	}
}
