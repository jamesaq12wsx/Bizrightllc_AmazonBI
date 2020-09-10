package com.analyze.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PriceApiTopic {

    @JsonProperty("search_results")
    SEARCH_RESULT("search_results"),
    @JsonProperty("reviews")
    REVIEWS("reviews");

    private String value;

    PriceApiTopic(String topic){
        this.value = topic;
    }

    @JsonValue
    public String getValue(){
        return value;
    }

    public static PriceApiTopic fromString(String topic) {
        for (PriceApiTopic priceApiTopic: PriceApiTopic.values()) {
            if (priceApiTopic.getValue().toLowerCase().equals(topic.toLowerCase())){
                return priceApiTopic;
            }
        }

        return null;
    }

}
