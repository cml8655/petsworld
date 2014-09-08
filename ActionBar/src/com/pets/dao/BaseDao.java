package com.pets.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public abstract class BaseDao<T> extends SQLiteOpenHelper {

	private static final String DB_FILE_NAME = "db_create.sql";
	protected static final int version = 1;
	public static final String NAME = "pets.db";
	protected SQLiteDatabase db;
	protected Context context;

	public BaseDao(Context context, CursorFactory factory) {
		super(context, NAME, factory, version);
		this.context = context;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS pet_daily_subject");
		Log.i("db_create", "删除表");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("db_create", "建表。。。。。");
		StringBuffer sql = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(context
					.getAssets().open(DB_FILE_NAME)));

			String line = null;

			while ((line = reader.readLine()) != null) {
				sql.append(line);
			}

		} catch (IOException e) {
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Log.i("db_create", sql.toString());
		db.execSQL(sql.toString());
	}

	public abstract T get(Serializable id);

	public abstract List<T> getAll(int limit);

	public abstract Serializable insert(T t);

	public abstract Serializable update(T t);

	public abstract Serializable delete(Serializable id);
}
