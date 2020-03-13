package com.analyze.model;

import java.io.Serializable;

/**
 * 用户监控店铺
 * @author zql
 * @time  2016-12-23 10:55:28
 */
public class ShopMonitoring implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7940441913093709762L;
	private Integer id;//流水号
	private String shopid;//店铺id
	private String shopname;//店铺名称
	private String sellername;//商家名称
	private String shopImgUrl;//店铺图片地址
	private String level;//店铺等级
	private Integer userid;//监控店铺用户
	private String focus;//主营
	private String areaName;//所属地区
	private String insertTime;//监控时间
	private Integer goodsSum;//商品总数
	private Integer type;//监控状态
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	private String updateTime;//删除时间
	private Integer page;//分页
	private String sellerId;//卖家编号
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getShopid() {
		return shopid;
	}
	public void setShopid(String shopid) {
		this.shopid = shopid;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getSellername() {
		return sellername;
	}
	public void setSellername(String sellername) {
		this.sellername = sellername;
	}
	
	public String getShopImgUrl() {
		return shopImgUrl;
	}
	public void setShopImgUrl(String shopImgUrl) {
		this.shopImgUrl = shopImgUrl;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getFocus() {
		return focus;
	}
	public void setFocus(String focus) {
		this.focus = focus;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getGoodsSum() {
		return goodsSum;
	}
	public void setGoodsSum(Integer goodsSum) {
		this.goodsSum = goodsSum;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}

	
}
