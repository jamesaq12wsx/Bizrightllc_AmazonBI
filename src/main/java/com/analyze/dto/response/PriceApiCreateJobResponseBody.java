package com.analyze.dto.response;

import com.analyze.model.PriceApiJobStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * The response body of create price api job
 */
@Data
public class PriceApiCreateJobResponseBody {

    @JsonProperty("job_id")
    private String jobId;

    private String version;

    private String country;

    private String source;

    private String topic;

    private String key;

    private PriceApiJobStatus status;

    private int requested;

    @JsonProperty("open_values")
    private int openValues;

    @JsonProperty("free_credits")
    private int freeCredits;

    @JsonProperty("paid_credits")
    private int paidCredits;

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;

    @JsonProperty("expires_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime expiresAt;
}
