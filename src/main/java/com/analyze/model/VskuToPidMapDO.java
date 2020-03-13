package com.analyze.model;

import java.util.Date;

public class VskuToPidMapDO {
    private String vendorSku;

    private String productId;

    private String effFlg;

    private Date insertTime;

    public String getVendorSku() {
        return vendorSku;
    }

    public void setVendorSku(String vendorSku) {
        this.vendorSku = vendorSku;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getEffFlg() {
        return effFlg;
    }

    public void setEffFlg(String effFlg) {
        this.effFlg = effFlg;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}