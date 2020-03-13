package com.analyze.model;

import java.util.Date;

public class HawSrapySkuInfoDO {
    private String taskId;

    private String productId;

    private String productSimpleId;

    private String productTitle;

    private String productTitleElse;

    private String productPrice;

    private String imgUrl;

    private String productIntroduce;

    private String pageUrl;

    private Date insertTime;

    private String vendorSku;

    private String merchantSuggestedAsin;

    private String productBrands;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductSimpleId() {
        return productSimpleId;
    }

    public void setProductSimpleId(String productSimpleId) {
        this.productSimpleId = productSimpleId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductTitleElse() {
        return productTitleElse;
    }

    public void setProductTitleElse(String productTitleElse) {
        this.productTitleElse = productTitleElse;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductIntroduce() {
        return productIntroduce;
    }

    public void setProductIntroduce(String productIntroduce) {
        this.productIntroduce = productIntroduce;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getVendorSku() {
        return vendorSku;
    }

    public void setVendorSku(String vendorSku) {
        this.vendorSku = vendorSku;
    }

    public String getMerchantSuggestedAsin() {
        return merchantSuggestedAsin;
    }

    public void setMerchantSuggestedAsin(String merchantSuggestedAsin) {
        this.merchantSuggestedAsin = merchantSuggestedAsin;
    }

    public String getProductBrands() {
        return productBrands;
    }

    public void setProductBrands(String productBrands) {
        this.productBrands = productBrands;
    }
}