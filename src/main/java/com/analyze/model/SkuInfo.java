package com.analyze.model;

import java.util.Date;

public class SkuInfo {
    private String asin;

    private String ismain;

    private String compareourasin;

    private String sellername;

    private String ourcode;

    private String category;

    private String amzbrand;

    private String isourbrand;

    private String groupsku;

    private Date inserttime;

    private Integer id;

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin == null ? null : asin.trim();
    }

    public String getIsmain() {
        return ismain;
    }

    public void setIsmain(String ismain) {
        this.ismain = ismain == null ? null : ismain.trim();
    }

    public String getCompareourasin() {
        return compareourasin;
    }

    public void setCompareourasin(String compareourasin) {
        this.compareourasin = compareourasin == null ? null : compareourasin.trim();
    }

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername == null ? null : sellername.trim();
    }

    public String getOurcode() {
        return ourcode;
    }

    public void setOurcode(String ourcode) {
        this.ourcode = ourcode == null ? null : ourcode.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getAmzbrand() {
        return amzbrand;
    }

    public void setAmzbrand(String amzbrand) {
        this.amzbrand = amzbrand == null ? null : amzbrand.trim();
    }

    public String getIsourbrand() {
        return isourbrand;
    }

    public void setIsourbrand(String isourbrand) {
        this.isourbrand = isourbrand == null ? null : isourbrand.trim();
    }

    public String getGroupsku() {
        return groupsku;
    }

    public void setGroupsku(String groupsku) {
        this.groupsku = groupsku == null ? null : groupsku.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}