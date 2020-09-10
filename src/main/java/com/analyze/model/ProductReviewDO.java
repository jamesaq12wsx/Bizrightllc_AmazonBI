package com.analyze.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * This is the review from amazon ebay or google any source
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewDO {

    /**
     * product review internal id
     */
    private Long id;

    /**
     * User task id
     */
    private String taskId;

    /**
     * product review external id
     * may be amazon review id or ebay review id
     */
    private String reviewId;

    private PriceApiSource source;

    private String url;

    private String country;

    private String productId;

    private double rating;

    private String brandName;

    private String productName;

    private String productUrl;

    private LocalDateTime date;

    private String reviewerId;

    private String reviewerName;

    private String reviewerUrl;

    private String title;

    private String comment;

    private int helpfulCount;

    private int commentCount;

    private boolean verifiedPurchase;

    private int page;

    private int position;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
