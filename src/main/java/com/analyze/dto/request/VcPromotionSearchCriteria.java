package com.analyze.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * The criteria of the promotion select
 */
@Data
public class VcPromotionSearchCriteria {

    /**
     * Promotion Id
     */
    private String promotionId;

    /**
     * product asin
     */
    private String asin;

    /**
     * promotion status
     */
    private String status;

    /**
     * promotion start date from
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateTo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDateTo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdOnFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdOnTo;

}
