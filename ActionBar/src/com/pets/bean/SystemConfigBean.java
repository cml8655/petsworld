package com.pets.bean;

import java.io.Serializable;

public class SystemConfigBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean autoLogin;// 自动登陆
	private boolean rememberMe;// 记住密码
	private boolean receiveWithMobile;
	private boolean receiveWithWifi;
	private boolean receiveImgNever;

	private boolean recevieRecommend;// 是否接受推荐

	public boolean isAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public boolean isReceiveWithMobile() {
		return receiveWithMobile;
	}

	public void setReceiveWithMobile(boolean receiveWithMobile) {
		this.receiveWithMobile = receiveWithMobile;
	}

	public boolean isReceiveWithWifi() {
		return receiveWithWifi;
	}

	public void setReceiveWithWifi(boolean receiveWithWifi) {
		this.receiveWithWifi = receiveWithWifi;
	}

	public boolean isReceiveImgNever() {
		return receiveImgNever;
	}

	public void setReceiveImgNever(boolean receiveImgNever) {
		this.receiveImgNever = receiveImgNever;
	}

	public boolean isRecevieRecommend() {
		return recevieRecommend;
	}

	public void setRecevieRecommend(boolean recevieRecommend) {
		this.recevieRecommend = recevieRecommend;
	}

}
