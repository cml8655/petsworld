package com.pets.bean;

import java.util.Date;

public class DailyShareSubject extends BaseBean {
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private Date createDate;
	private int type;// 说说的类别，阿猫阿狗？
	private int agree;
	private int disagree;
	private boolean allow;
	private String content;
	private boolean useable;// 是否可用 1：可用 -1 删除
	private String backReason;
	private DailyShareImg shareImg;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

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

	public boolean isAllow() {
		return allow;
	}

	public void setAllow(boolean allow) {
		this.allow = allow;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isUseable() {
		return useable;
	}

	public void setUseable(boolean useable) {
		this.useable = useable;
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public DailyShareImg getShareImg() {
		return shareImg;
	}

	public void setShareImg(DailyShareImg shareImg) {
		this.shareImg = shareImg;
	}

	@Override
	public String toString() {
		return "DailyShareSubject [id=" + id + ", username=" + username
				+ ", createDate=" + createDate + ", type=" + type + ", agree="
				+ agree + ", disagree=" + disagree + ", allow=" + allow
				+ ", content=" + content + ", useable=" + useable
				+ ", backReason=" + backReason + ", shareImg=" + shareImg + "]";
	}

}
