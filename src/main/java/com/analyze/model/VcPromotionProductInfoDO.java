package com.analyze.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VcPromotionProductInfoDO {

    private String crawId;

    private String promotionId;

    private String productName;

    private String asin;

    private String upc;

    private String amazonPriceStr;

    private BigDecimal amazonPrice;

    private String websitePriceStr;

    private BigDecimal websitePrice;

    private String fundingStr;

    private BigDecimal funding;

    private String likelyPriceStr;

    private BigDecimal likelyPrice;

    private String unitsSoldStr;

    private Integer unitsSold;

    private String amountSpentStr;

    private BigDecimal amountSpent;

    private String revenueStr;

    private BigDecimal revenue;

    private String crawFlg;

    private AsinSkuMapDO asinSkuMap;

}
