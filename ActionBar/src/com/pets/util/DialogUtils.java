package com.pets.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;

public class DialogUtils {
	public static final String POSITIVE_BTN_MSG = "确认";
	public static final String NAGATIVE_BTN_MSG = "取消";

	public static <T> void showDialog(Context context, String title,
			String msg, final DialogCallBack back) {

		Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == AlertDialog.BUTTON_NEGATIVE) {
					back.cancel();
				}
				if (which == AlertDialog.BUTTON_POSITIVE) {
					back.confirm();
				}

			}
		};

		builder.setNegativeButton(NAGATIVE_BTN_MSG, listener);
		builder.setPositiveButton(POSITIVE_BTN_MSG, listener);

		builder.create().show();
	}
}
