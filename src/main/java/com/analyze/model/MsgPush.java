package com.analyze.model;

import java.io.Serializable;

public class MsgPush implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8010143285721862719L;
	private Integer id;			//标识id
	private String userInfo;		//用户信息
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	
	
}
