package com.analyze.model;

import java.util.Date;

public class SkuOffersInfo {
    private String asin;

    private String price;

    private String shipping;

    private String condition;

    private String delivery;

    private String sellername;

    private String sellerpositiverate;

    private String sellerrwnum;

    private String sellerurl;

    private String isprime;

    private Date inserttime;

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin == null ? null : asin.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping == null ? null : shipping.trim();
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition == null ? null : condition.trim();
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery == null ? null : delivery.trim();
    }

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername == null ? null : sellername.trim();
    }

    public String getSellerpositiverate() {
        return sellerpositiverate;
    }

    public void setSellerpositiverate(String sellerpositiverate) {
        this.sellerpositiverate = sellerpositiverate == null ? null : sellerpositiverate.trim();
    }

    public String getSellerrwnum() {
        return sellerrwnum;
    }

    public void setSellerrwnum(String sellerrwnum) {
        this.sellerrwnum = sellerrwnum == null ? null : sellerrwnum.trim();
    }

    public String getSellerurl() {
        return sellerurl;
    }

    public void setSellerurl(String sellerurl) {
        this.sellerurl = sellerurl == null ? null : sellerurl.trim();
    }

    public String getIsprime() {
        return isprime;
    }

    public void setIsprime(String isprime) {
        this.isprime = isprime == null ? null : isprime.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }
}