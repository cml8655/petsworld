package com.pets.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SuperDAO extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	private static final String NAME_STRING = "petDatabase";
	protected static final String TAG = "DAO";

	public SuperDAO(Context context) {
		super(context, NAME_STRING, null, VERSION);
	}


	// 用户安装好程序之后需要用到数据库 没有表的时候 创建表 有表就不执行
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "oncreate-->");
		db.execSQL("create table myDataBase(name VARCHAR(20),id INTEGER primary key);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
