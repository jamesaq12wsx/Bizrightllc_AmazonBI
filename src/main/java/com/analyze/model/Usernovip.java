package com.analyze.model;

import java.io.Serializable;

public class Usernovip implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8779597866161843038L;
	private Integer useridout;
	private String usernameout;
	private Integer isre;
	private String updatetime;
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getUseridout() {
		return useridout;
	}
	public void setUseridout(Integer useridout) {
		this.useridout = useridout;
	}
	public String getUsernameout() {
		return usernameout;
	}
	public void setUsernameout(String usernameout) {
		this.usernameout = usernameout;
	}
	public Integer getIsre() {
		return isre;
	}
	public void setIsre(Integer isre) {
		this.isre = isre;
	}

}
