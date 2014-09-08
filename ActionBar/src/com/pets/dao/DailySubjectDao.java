package com.pets.dao;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.pets.bean.DailyShareImg;
import com.pets.bean.DailyShareSubject;
import com.pets.bean.User;

@SuppressLint("SimpleDateFormat")
public class DailySubjectDao extends BaseDao<DailyShareSubject> {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final String TABLE = "pet_daily_subject";

	public DailySubjectDao(Context context, CursorFactory factory) {
		super(context, factory);
	}

	@Override
	public DailyShareSubject get(Serializable id) {

		if (null == id) {
			throw new IllegalArgumentException("id counld not be null ");
		}
		Cursor cursor = null;
		try {
			db = getReadableDatabase();

			cursor = db.query(TABLE, new String[] { "id", "username",
					"user_img", "datetime(create_date,'localtime')", "type",
					"agree", "disagree", "allow", "content", "useable",
					"back_reason", "img_url" }, "id=?",
					new String[] { String.valueOf(id) }, null, null, null);

			if (cursor.moveToNext()) {
				DailyShareSubject subject = wrapSubjectFromDB(cursor);

				return subject;
			}
		} catch (Exception e) {
			// Log.e("error", e.getMessage());
			throw new IllegalArgumentException(e);
		} finally {
			if (null != cursor)
				cursor.close();
			db.close();
		}

		return null;
	}

	@Override
	public Serializable insert(DailyShareSubject t) {

		if (t == null) {
			throw new IllegalArgumentException(
					"insert object counld not be null ");
		}

		db = getWritableDatabase();
		db.beginTransaction();

		ContentValues values = new ContentValues();
		values.put("id", t.getId());
		values.put("username", t.getUsername());

		values.put("create_date", format.format(t.getCreateDate()));
		values.put("type", t.getType());
		values.put("agree", t.getAgree());
		values.put("disagree", t.getDisagree());
		values.put("allow", t.isAllow());
		values.put("content", t.getContent());
		values.put("useable", t.isUseable());
		values.put("back_reason", t.getBackReason());
		values.put("img_url", t.getShareImg().getUrl());
		values.put("user_img", t.getUser().getUsername());

		long id = db.insert(TABLE, null, values);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
		return id;
	}

	@Override
	public Serializable update(DailyShareSubject t) {
		return null;
	}

	@Override
	public Serializable delete(Serializable id) {
		return null;
	}

	@Override
	public List<DailyShareSubject> getAll(int limit) {

		if (limit < 0) {
			throw new IllegalArgumentException("limit must be positive ");
		}
		Cursor cursor = null;
		try {
			db = getReadableDatabase();

			cursor = db.rawQuery("select * from " + TABLE + " limit ?",
					new String[] { String.valueOf(limit) });

			// cursor = db.query(TABLE, new String[] { "id", "username",
			// "user_img", "datetime(create_date,'localtime')", "type",
			// "agree", "disagree", "allow", "content", "useable",
			// "back_reason", "img_url" }, "limit ? OFFSET 0",
			// new String[] { String.valueOf(limit) }, null, null, null);

			List<DailyShareSubject> subjects = new ArrayList<DailyShareSubject>();

			while (cursor.moveToNext()) {

				DailyShareSubject subject = wrapSubjectFromDB(cursor);

				subjects.add(subject);
			}

			return subjects;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			if (null != cursor)
				cursor.close();
			db.close();
		}
	}

	private DailyShareSubject wrapSubjectFromDB(Cursor cursor)
			throws ParseException {
		DailyShareSubject subject = new DailyShareSubject();

		subject.setId(cursor.getInt(cursor.getColumnIndex("id")));
		subject.setUsername(cursor.getString(cursor.getColumnIndex("username")));
		subject.setType(cursor.getInt(cursor.getColumnIndex("type")));
		subject.setAgree(cursor.getInt(cursor.getColumnIndex("agree")));
		subject.setDisagree(cursor.getInt(cursor.getColumnIndex("disagree")));
		subject.setAllow(cursor.getInt(cursor.getColumnIndex("allow")) == 1);
		subject.setContent(cursor.getString(cursor.getColumnIndex("content")));
		subject.setUseable(cursor.getInt(cursor.getColumnIndex("useable")) == 1);

		subject.setBackReason(cursor.getString(cursor
				.getColumnIndex("back_reason")));

		DailyShareImg img = new DailyShareImg();
		img.setUrl(cursor.getString(cursor.getColumnIndex("img_url")));
		subject.setShareImg(img);

		User user = new User();
		user.setUsername(subject.getUsername());
		subject.setUser(user);

		String createTime = cursor.getString(cursor
				.getColumnIndex("datetime(create_date,'localtime')"));
		if (null != createTime) {
			subject.setCreateDate(format.parse(createTime));
		}
		return subject;
	}

}
