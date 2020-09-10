package com.analyze.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class PriceApiReviewResult implements Serializable {

    private Map<String, String> query;

    private boolean success;

    private Map<String, String> metadata;

    private PriceApiReviewResultContent content;

}
