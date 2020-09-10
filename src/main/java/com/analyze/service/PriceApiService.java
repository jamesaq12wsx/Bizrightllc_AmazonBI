package com.analyze.service;

import com.analyze.dto.response.PriceApiCreateJobResponseBody;
import com.analyze.dto.request.PriceApiRequest;
import com.analyze.dto.response.PriceApiReviewResponseBody;

public interface PriceApiService {

    /**
     * Create new job
     * @param request
     * @return
     */
    PriceApiCreateJobResponseBody createNewJob(PriceApiRequest request) throws Exception;

    /**
     * Get Job result
     * @param jobId
     * @return
     */
    PriceApiReviewResponseBody getReviewJobResult(String jobId) throws Exception;

    /**
     * Create price api job of product reviews
     * @param request
     * @return
     */
    PriceApiCreateJobResponseBody createAmazonReviewJob(PriceApiRequest request) throws Exception;

}
