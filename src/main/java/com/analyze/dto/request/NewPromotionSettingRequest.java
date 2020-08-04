package com.analyze.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewPromotionSettingRequest extends BaseRequest {

    private Long promotionId;

    private String asin;

    private BigDecimal price;

    private BigDecimal funding;

}
