package com.analyze.dto.response;

import com.analyze.model.PriceApiJobStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PriceApiReviewResponseBody {

    @JsonProperty("job_id")
    private String jobId;

    private PriceApiJobStatus status;

    @JsonProperty("free_credits")
    private String freeCredits;

    @JsonProperty("paid_credits")
    private String paidCredits;

    private List<PriceApiReviewResult> results;

    private String success;

    private String reason;

    private String comment;

}
