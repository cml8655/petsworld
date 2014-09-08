package com.pets.dao;

import java.sql.SQLException;
import java.util.List;

public interface DaoInterface<T> {
	/**
	 * 将数据T保存到数据库中
	 * 
	 * @param t
	 * @throws SQLException
	 *             存储发生错误
	 */
	public void persistence(T t) throws SQLException;

	/**
	 * 查询单个指定table的指定columns数据，返回T对象
	 * 
	 * @param table
	 * @param columns
	 * @param selection
	 * @param selectArgs
	 * @return
	 */
	public T queryById(String table, String id, String selection,
			String selectionValue);

	/**
	 * 使用sql方式进行查询
	 * 
	 * @param sql
	 * @param values
	 * @return 查询到的数据集合，size==0 没有数据
	 */
	public List<T> queryByRaw(String sql, String[] values);

	/**
	 * 查询单个指定table的指定columns数据，返回T对象
	 * 
	 * @param table
	 * @param columns
	 * @param selection
	 * @param selectArgs
	 * @return
	 */
	public List<T> querySigleton(String table, String[] columns,
			String selection, String[] selectArgs);
}
