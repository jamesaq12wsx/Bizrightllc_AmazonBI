package com.analyze.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * This is the model that product manager insert to monitor
 * the amazon promotion
 */
@Data
public class PromotionSettingDO {

    private Long id;

    private Long promotionId;

    private String asin;

    private BigDecimal price;

    private BigDecimal funding;

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
