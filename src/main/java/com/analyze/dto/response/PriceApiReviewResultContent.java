package com.analyze.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class PriceApiReviewResultContent implements Serializable {

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("product_name")
    private String productName;

    /**
     * Amazon -> Asin
     */
    @JsonProperty("product_id")
    private String productId;

    private String url;

    private int count;

    private double rating;

    @JsonProperty("rating_distribution")
    private Map<String, String> ratingDistribution;

    private List<PriceApiReview> reviews;

}
