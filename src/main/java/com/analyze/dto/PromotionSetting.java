package com.analyze.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Required;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * This is Promotion Setting payload object
 */
@Data
public class PromotionSetting {

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
