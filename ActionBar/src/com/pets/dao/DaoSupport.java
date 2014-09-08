package com.pets.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DaoSupport<T> extends SuperDAO implements DaoInterface<T> {

	public DaoSupport(Context context) {
		super(context);
	}
	void a(){
		SQLiteDatabase db=this.initReadbleDatabase();
	}
	/**
	 * 获取可写可读数据库
	 */
	public SQLiteDatabase initWritableDatabase() {
		return getWritableDatabase();
	}

	/**
	 * 获取只读数据库
	 */
	public SQLiteDatabase initReadbleDatabase() {
		return getReadableDatabase();
	}
}
