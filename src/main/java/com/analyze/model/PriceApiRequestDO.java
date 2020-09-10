package com.analyze.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Price api request
 *
 * This class save the status of the request
 */
@Data
public class PriceApiRequestDO {

    /**
     * Internal request id
     */
    private Long requestId;

    /**
     * External job id
     * from price api
     */
    private String jobId;

    /**
     * Product keyword task id
     */
    private String taskId;

    /**
     * request topic
     */
    private PriceApiTopic topic;

    private PriceApiJobStatus status;

    private String country;

    private String source;

    private Integer maxPages;

    private String key;

    /**
     * value may be asin
     */
    private String values;

    /**
     * Create user id
     */
    private String createdBy;

    /**
     * Update user id
     */
    private String updatedBy;

    /**
     * Create date time
     */
    private LocalDateTime createdAt;

    /**
     * Update date time
     */
    private LocalDateTime updatedAt;

}
