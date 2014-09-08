package com.pets.activity;

import java.io.File;
import java.io.IOException;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ComeraActivity extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 使用surfaceview进行预览
		// LinearLayout layout = new LinearLayout(this);
		// LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT);
		// layout.setLayoutParams(param);
		//
		// Camera camera = ComeraUtil.getCameraInstance(this);
		// layout.addView(new ComeraActivity.CameraPreview(this, camera));
		//
		// setContentView(layout);

		/**
		 * 使用自带的照相机
		 */
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		File out = new File(Environment.getExternalStorageDirectory(),
				"camera.jpg");

		Uri uri = Uri.fromFile(out);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

		startActivityForResult(intent, 0);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Image saved to:\n" + data.getData(),
						Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}

		if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Video captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Video saved to:\n" + data.getData(),
						Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the video capture
			} else {
				// Video capture failed, advise user
			}
		}
	}
}
