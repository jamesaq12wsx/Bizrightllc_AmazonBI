package com.analyze.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ProductKeywordResult {

    private String asin;

    private String productName;

    /**
     * This show the count of every task keyword show
     * in this asin reviews
     */
    private Map<String, Integer> keywordCount;

}
