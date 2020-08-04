package com.analyze.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VcPromotionInfoDO {

    private String crawId;

    private String createdOnStr;

    private String promotionId;

    private String status;

    private String name;

    private String startDateStr;

    private String endDateStr;

    private String type;

    private String heroProduct;

    private String vendorCode;

    private String marketPlace;

    private String billingContact;

    private String fundingAgreement;

    private String merchandisingFee;

    private String crawFlg;

    private LocalDateTime createdOn;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime insertAt;

    private List<VcPromotionProductInfoDO> products;
}
