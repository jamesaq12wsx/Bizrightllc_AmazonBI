package com.analyze.model;

import java.io.Serializable;

/**
 * 微信普通监控商品每日详情
 * @author zhouxian
 *
 */
public class WxModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -525827271578598806L;
	private String oldimgUrl;
	private Integer taojinbi;
	private String pinglun_num;
	private Integer juhuasuan;
	private String maijiaxiu_num;
	private String egoodsid;
	private Integer goodsName_status;
	private String xiaoshouliang;
	private String inserttime;
	private String newimgUrl;
	private String sellNum;
	private String manzeng;
	private String baoyou;
	private String offlineTime;
	private String newgoodsName;
	private String xinzengshoucangliang;
	private Integer imgUrl_status;
	private Integer taoqianggou;
	private Integer taoqingcang;
	private String youhuijuan;
	private String hongbao;
	private String onlineNum;
	private String oldgoodsName;
	private String price;
	private String shopName;
	private String manjian;
	private Integer tiantiantejia;
	private String zhekou;
	private String monitorTime;
	private String leastPrice;//高频破底价
	private String prefixPrice;//高频上一次价格
	private String price_status;//价格变化状态
	private Integer yujingjiage;//是否达到预警价格
	private String activity;//正在参与的活动
	private Integer activity_status;//参与活动状态
	private String goodsid;
	private Integer price_Status;
	private Integer priceName_status;
	private String priceName;
	private String before_priceName;
	
	public String getOldimgUrl() {
		return oldimgUrl;
	}
	public void setOldimgUrl(String oldimgUrl) {
		this.oldimgUrl = oldimgUrl;
	}
	public Integer getTaojinbi() {
		return taojinbi;
	}
	public void setTaojinbi(Integer taojinbi) {
		this.taojinbi = taojinbi;
	}
	public String getPinglun_num() {
		return pinglun_num;
	}
	public void setPinglun_num(String pinglun_num) {
		this.pinglun_num = pinglun_num;
	}
	public Integer getJuhuasuan() {
		return juhuasuan;
	}
	public void setJuhuasuan(Integer juhuasuan) {
		this.juhuasuan = juhuasuan;
	}
	public String getMaijiaxiu_num() {
		return maijiaxiu_num;
	}
	public void setMaijiaxiu_num(String maijiaxiu_num) {
		this.maijiaxiu_num = maijiaxiu_num;
	}
	public String getEgoodsid() {
		return egoodsid;
	}
	public void setEgoodsid(String egoodsid) {
		this.egoodsid = egoodsid;
	}
	public Integer getGoodsName_status() {
		return goodsName_status;
	}
	public void setGoodsName_status(Integer goodsName_status) {
		this.goodsName_status = goodsName_status;
	}
	public String getXiaoshouliang() {
		return xiaoshouliang;
	}
	public void setXiaoshouliang(String xiaoshouliang) {
		this.xiaoshouliang = xiaoshouliang;
	}
	public String getInserttime() {
		return inserttime;
	}
	public void setInserttime(String inserttime) {
		this.inserttime = inserttime;
	}
	public String getNewimgUrl() {
		return newimgUrl;
	}
	public void setNewimgUrl(String newimgUrl) {
		this.newimgUrl = newimgUrl;
	}
	public String getSellNum() {
		return sellNum;
	}
	public void setSellNum(String sellNum) {
		this.sellNum = sellNum;
	}
	public String getManzeng() {
		return manzeng;
	}
	public void setManzeng(String manzeng) {
		this.manzeng = manzeng;
	}
	public String getBaoyou() {
		return baoyou;
	}
	public void setBaoyou(String baoyou) {
		this.baoyou = baoyou;
	}
	public String getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(String offlineTime) {
		this.offlineTime = offlineTime;
	}
	public String getNewgoodsName() {
		return newgoodsName;
	}
	public void setNewgoodsName(String newgoodsName) {
		this.newgoodsName = newgoodsName;
	}
	public String getXinzengshoucangliang() {
		return xinzengshoucangliang;
	}
	public void setXinzengshoucangliang(String xinzengshoucangliang) {
		this.xinzengshoucangliang = xinzengshoucangliang;
	}
	public Integer getImgUrl_status() {
		return imgUrl_status;
	}
	public void setImgUrl_status(Integer imgUrl_status) {
		this.imgUrl_status = imgUrl_status;
	}
	public Integer getTaoqianggou() {
		return taoqianggou;
	}
	public void setTaoqianggou(Integer taoqianggou) {
		this.taoqianggou = taoqianggou;
	}
	public Integer getTaoqingcang() {
		return taoqingcang;
	}
	public void setTaoqingcang(Integer taoqingcang) {
		this.taoqingcang = taoqingcang;
	}
	public String getYouhuijuan() {
		return youhuijuan;
	}
	public void setYouhuijuan(String youhuijuan) {
		this.youhuijuan = youhuijuan;
	}
	public String getHongbao() {
		return hongbao;
	}
	public void setHongbao(String hongbao) {
		this.hongbao = hongbao;
	}
	public String getOnlineNum() {
		return onlineNum;
	}
	public void setOnlineNum(String onlineNum) {
		this.onlineNum = onlineNum;
	}
	public String getOldgoodsName() {
		return oldgoodsName;
	}
	public void setOldgoodsName(String oldgoodsName) {
		this.oldgoodsName = oldgoodsName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getManjian() {
		return manjian;
	}
	public void setManjian(String manjian) {
		this.manjian = manjian;
	}
	public Integer getTiantiantejia() {
		return tiantiantejia;
	}
	public void setTiantiantejia(Integer tiantiantejia) {
		this.tiantiantejia = tiantiantejia;
	}
	public String getZhekou() {
		return zhekou;
	}
	public void setZhekou(String zhekou) {
		this.zhekou = zhekou;
	}
	public String getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(String monitorTime) {
		this.monitorTime = monitorTime;
	}
	public String getLeastPrice() {
		return leastPrice;
	}
	public void setLeastPrice(String leastPrice) {
		this.leastPrice = leastPrice;
	}
	public String getPrefixPrice() {
		return prefixPrice;
	}
	public void setPrefixPrice(String prefixPrice) {
		this.prefixPrice = prefixPrice;
	}
	public String getPrice_status() {
		return price_status;
	}
	public void setPrice_status(String price_status) {
		this.price_status = price_status;
	}
	public Integer getYujingjiage() {
		return yujingjiage;
	}
	public void setYujingjiage(Integer yujingjiage) {
		this.yujingjiage = yujingjiage;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public Integer getActivity_status() {
		return activity_status;
	}
	public void setActivity_status(Integer activity_status) {
		this.activity_status = activity_status;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public Integer getPrice_Status() {
		return price_Status;
	}
	public void setPrice_Status(Integer price_Status) {
		this.price_Status = price_Status;
	}
	public Integer getPriceName_status() {
		return priceName_status;
	}
	public void setPriceName_status(Integer priceName_status) {
		this.priceName_status = priceName_status;
	}
	public String getPriceName() {
		return priceName;
	}
	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}
	public String getBefore_priceName() {
		return before_priceName;
	}
	public void setBefore_priceName(String before_priceName) {
		this.before_priceName = before_priceName;
	}
	
}
