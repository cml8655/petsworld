package com.pets.dummy;

import java.util.Date;

public class DummyReply {
	private int dailyShareRecordId;
	private int id;
	private Date replyDate;
	private String content;
	private String replyUser;
	private int agree;
	private int disagree;
	

	public int getAgree() {
		return agree;
	}

	public void setAgree(int agree) {
		this.agree = agree;
	}

	public int getDisagree() {
		return disagree;
	}

	public void setDisagree(int disagree) {
		this.disagree = disagree;
	}

	public int getDailyShareRecordId() {
		return dailyShareRecordId;
	}

	public void setDailyShareRecordId(int dailyShareRecordId) {
		this.dailyShareRecordId = dailyShareRecordId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReplyUser() {
		return replyUser;
	}

	public void setReplyUser(String replyUser) {
		this.replyUser = replyUser;
	}

}
