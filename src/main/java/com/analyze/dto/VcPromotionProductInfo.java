package com.analyze.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VcPromotionProductInfo {

    private String crawId;

    private String promotionId;

    private String productName;

    private String asin;

    private String upc;

    private BigDecimal amazonPrice;

    private BigDecimal websitePrice;

    private BigDecimal funding;

    private BigDecimal likelyPrice;

    private Integer unitsSold;

    private BigDecimal amountSpent;

    private BigDecimal revenue;

    private String crawFlg;

    private AsinSkuMap asinSkuMap;

}
