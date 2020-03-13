package com.analyze.model;

import java.io.Serializable;

public class EnterpriseMembers implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5975512082267994308L;
	private String account;
	private String userName;
	private String gender;
	private String wechatNum;
	private String mobilePhone;
	private String email;
	private String department;
	private String degree;
	private String tag;
	private Integer focusStatus;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getWechatNum() {
		return wechatNum;
	}
	public void setWechatNum(String wechatNum) {
		this.wechatNum = wechatNum;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getFocusStatus() {
		return focusStatus;
	}
	public void setFocusStatus(Integer focusStatus) {
		this.focusStatus = focusStatus;
	}
	
}
