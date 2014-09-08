package com.pets.model;

import java.util.List;

import com.pets.bean.DailyShareSubject;

/**
 * json返回数据格式模型对象
 * 
 * @author 陈孟琳
 * 
 *         2014年7月29日
 */
public class JsonResult {
	// 默认是成功的
	private boolean success = true;
	private String msg;
	private List<DailyShareSubject> rows;
	private long total = 100;

	public List<DailyShareSubject> getRows() {
		return rows;
	}

	public void setRows(List<DailyShareSubject> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
