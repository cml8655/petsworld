package com.pets.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

public class ComeraUtil {

	private Context context;

	public ComeraUtil(Context context) {
		this.context = context;
	}

	/**
	 * 获取照相机
	 * 
	 * @return
	 */
	public static Camera getCameraInstance(Context context) {

		if (!checkCameraHardware(context)) {
			return null;
		}

		Camera c = null;
		
		try {
			c = Camera.open(); // attempt to get a Camera instance
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
		}

		return c; // returns null if camera is unavailable
	}

	/** Check if this device has a camera */
	private static boolean checkCameraHardware(Context context) {

		// this device has a camera
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {

			return true;
		}
		
		return false;
	}
}
