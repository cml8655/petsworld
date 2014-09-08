package com.pets.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 所有bean的基类，提供分页属性
 *
 * 2014年7月29日
 */
public class BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int start;
	private int len = 1;
	
	// datagrid页面传入参数
	private int page;
	private int rows;
	private Date startTime;
	private int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Date getStartTime() {
		return startTime;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLen() {
		return len;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setLen(int len) {
		this.len = len;
	}

}
