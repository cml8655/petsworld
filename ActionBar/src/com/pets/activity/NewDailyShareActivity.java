package com.pets.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.actionbar.R;

/**
 * 发表新说说
 * 
 * @author Administrator
 * 
 */
public class NewDailyShareActivity extends Activity implements OnClickListener {
	// 拍照获取
	private static final int CAMERA_REQUEST = 100;
	// 从相册中获取
	private static final int PHOTO_REQUEST = 200;
	// 裁剪图片
	private static final int CROP_REQUEST = 300;
	private ImageView img;
	private PopupWindow window;
	private Button fromPhoto;
	private Button fromCamera;
	private Button cropImg;
	private Uri imgUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		// 获取发表说说的类型
		setContentView(R.layout.pets_daily_share_new);
		img = (ImageView) findViewById(R.id.daily_share_new_img);

		img.setOnClickListener(this);

		window = new PopupWindow();
		View view = getLayoutInflater().inflate(R.layout.pets_img_select, null);

		fromCamera = (Button) view.findViewById(R.id.fromCamera);
		fromPhoto = (Button) view.findViewById(R.id.fromPhotoStorage);
		cropImg = (Button) view.findViewById(R.id.cropImg);

		fromCamera.setOnClickListener(this);
		fromPhoto.setOnClickListener(this);
		cropImg.setOnClickListener(this);

		cropImg.setVisibility(View.INVISIBLE);
		window.setContentView(view);
		window.setClippingEnabled(true);
		window.setOutsideTouchable(true);
		window.setTouchable(true);
		window.setWidth(LayoutParams.MATCH_PARENT);

		WindowManager wm = this.getWindowManager();

		// 获取屏幕高度
		int height = wm.getDefaultDisplay().getHeight();

		window.setHeight(height / 2);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST) {
			if (RESULT_OK == resultCode) {
				Bitmap bitmap = (Bitmap) data.getExtras().get("data");
				img.setImageBitmap(bitmap);

				cropImg.setVisibility(View.VISIBLE);
				imgUri = Uri.parse(Images.Media.insertImage(
						getContentResolver(), bitmap, null, null));

				Toast.makeText(
						this,
						"图片大小：" + bitmap.getWidth() + ":" + bitmap.getHeight()
								+ ":" + bitmap.getByteCount() / 1024,
						Toast.LENGTH_LONG).show();
			}
		} else if (requestCode == PHOTO_REQUEST) {
			if (resultCode == RESULT_OK) {
				imgUri = data.getData();
				img.setImageBitmap(measure(imgUri));
				Toast.makeText(this, "图片大小：" + imgUri.toString(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private Bitmap measure(Uri uri) {

		Cursor cursor = getContentResolver().query(uri,
				new String[] { Images.Media.DATA }, null, null, null);

		if (cursor.moveToNext()) {
			if ("content".equalsIgnoreCase(uri.getScheme())) {

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;

				Bitmap temp = BitmapFactory.decodeFile(cursor.getString(cursor
						.getColumnIndex(Images.Media.DATA)), options);

				Log.i("conn2", "图片字节大小：" + temp.getByteCount());

				int width = temp.getWidth();
				int height = temp.getHeight();

				int defaultHeight = getWindowManager().getDefaultDisplay()
						.getHeight();
				int defaultWidth = getWindowManager().getDefaultDisplay()
						.getWidth();

				float widthRate = defaultWidth / width;
				float heightRate = defaultHeight / height;

				Log.i("conn2", "width:" + width + ":" + height + ",defaut:"
						+ defaultWidth + ":" + defaultHeight);

				options.inSampleSize = (int) widthRate;

			} else if ("file".equalsIgnoreCase(uri.getScheme())) {
				// uri.getPath()
			}
		}
		cursor.close();
		return null;

	}

	@Override
	public void onClick(View v) {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if (v == fromCamera) {
			startActivityForResult(intent, CAMERA_REQUEST);
		} else if (v == fromPhoto) {
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			startActivityForResult(intent, PHOTO_REQUEST);
		} else if (v == cropImg && null != imgUri) {
			intent.setAction("com.android.camera.action.CROP");
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.setData(imgUri);
			intent.putExtra("aspectX", 2);// 裁剪框比例
			intent.putExtra("aspectY", 3);
			intent.putExtra("outputX", 320);// 输出图片大小
			intent.putExtra("outputY", 480);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, CROP_REQUEST);
			return;
		} else if (v == img) {
			if (window.isShowing()) {
				window.dismiss();
			} else {
				window.showAtLocation(getRootView(this), Gravity.BOTTOM
						| Gravity.LEFT, 0, 0);
			}
		}

	}

	private View getRootView(Activity context) {
		return ((ViewGroup) context.findViewById(android.R.id.content))
				.getChildAt(0);
	}

}
