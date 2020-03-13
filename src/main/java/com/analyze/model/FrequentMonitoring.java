package com.analyze.model;

import java.io.Serializable;

/**
 * 高频设置添加对象
 * 
 * @author zql
 * @time 2016-12-12 10:23:48
 *
 */
public class FrequentMonitoring implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 604881829663956512L;
	private int id;// 自增
	private int userid;// 用户id
	private String productid;// 商品id
	private int type;// 是否监控 1监控 0取消监控
	private String eType;// 商品所在的平台
	private String brand;// 添加页面 自定义品牌
	private String generalCode;// 添加页面 商品码
	private String generalName;// 添加页面 商品名称
	private String alertPrice;// 添加页面 预警价格
	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	private int isWechat;// 添加页面 微信提醒
	private String insertTime;// 添加监控商品时间
	private String updateTime;// 修改or删除监控商品时间
	private String productImgUrl;//商品图片地址
	private String productName;//商品名称
	private String sellerName;
	
	private String group_id;
	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getProductid_status() {
		return productid_status;
	}

	public void setProductid_status(String productid_status) {
		this.productid_status = productid_status;
	}

	private String status;//监控状态
	private Integer page;//第几页
	private String egoodsurl;//商品链接
	private String productid_status;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}



	public String geteType() {
		return eType;
	}

	public void seteType(String eType) {
		this.eType = eType;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getGeneralCode() {
		return generalCode;
	}

	public void setGeneralCode(String generalCode) {
		this.generalCode = generalCode;
	}

	public String getGeneralName() {
		return generalName;
	}

	public void setGeneralName(String generalName) {
		this.generalName = generalName;
	}

	public String getAlertPrice() {
		return alertPrice;
	}

	public void setAlertPrice(String alertPrice) {
		this.alertPrice = alertPrice;
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

	public String getProductImgUrl() {
		return productImgUrl;
	}

	public void setProductImgUrl(String productImgUrl) {
		this.productImgUrl = productImgUrl;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsWechat() {
		return isWechat;
	}

	public void setIsWechat(int isWechat) {
		this.isWechat = isWechat;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getEgoodsurl() {
		return egoodsurl;
	}

	public void setEgoodsurl(String egoodsurl) {
		this.egoodsurl = egoodsurl;
	}

}
