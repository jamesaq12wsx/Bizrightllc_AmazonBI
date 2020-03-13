package com.analyze.model;

import java.io.Serializable;

public class Commonitoring  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5274055980251944148L;
	private Integer id;
	private Integer userId;
	private String productId;
	private String insertTime;
	private String updateTime;
	private Integer type;
	private String memo;
	private String productidUrl;
	private String proName;
	private String sellerName;
	private String serviceIp;
	private Integer status;
	private String producturl;//商品链接
	private String sellerurl;//商家链接
	private String productid_status;
	
	private String xiaoshouliang;//销售额
	private String sellNum;//销售数
	
	public String getXiaoshouliang() {
		return xiaoshouliang;
	}
	public void setXiaoshouliang(String xiaoshouliang) {
		this.xiaoshouliang = xiaoshouliang;
	}
	public String getSellNum() {
		return sellNum;
	}
	public void setSellNum(String sellNum) {
		this.sellNum = sellNum;
	}
	public String getProductid_status() {
		return productid_status;
	}
	public void setProductid_status(String productid_status) {
		this.productid_status = productid_status;
	}
	private Integer page;//第几页
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getProductidUrl() {
		return productidUrl;
	}
	public void setProductidUrl(String productidUrl) {
		this.productidUrl = productidUrl;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getServiceIp() {
		return serviceIp;
	}
	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getProducturl() {
		return producturl;
	}
	public void setProducturl(String producturl) {
		this.producturl = producturl;
	}
	public String getSellerurl() {
		return sellerurl;
	}
	public void setSellerurl(String sellerurl) {
		this.sellerurl = sellerurl;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	
}
