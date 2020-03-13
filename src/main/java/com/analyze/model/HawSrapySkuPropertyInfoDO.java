package com.analyze.model;

import java.util.Date;

public class HawSrapySkuPropertyInfoDO {
    private String taskId;

    private String productId;

    private String productSimpleId;

    private String propertyName;

    private String propertyValue;

    private Date insertTime;

    private String vendorSku;

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

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
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
}