package com.pets.bean;

import java.util.Date;

public class DailyShareImg {
	private int id;
	private String url;
	private Date createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "DailyShareImg [id=" + id + ", url=" + url + ", createDate="
				+ createDate + "]";
	}

}
