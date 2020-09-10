package com.analyze.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PriceApiJobStatus {

    //This is server status, request is create haven't request
    //Need request
    CREATE("create"),
    PROCESSING("processing"),
    // These are price api status
    NEW("new"),
    WORKING("working"),
    FINISHING("finishing"),
    FINISHED("finished"),
    CANCELED("canceled");

    private String value;

    PriceApiJobStatus(String source) {
        this.value = source;
    }

    @JsonValue
    public String toValue(){
        return value;
    }

    @JsonCreator
    public static PriceApiJobStatus fromString(String status) {
        for (PriceApiJobStatus priceApiJobStatus: PriceApiJobStatus.values()) {
            if (priceApiJobStatus.toValue().toLowerCase().equals(status.toLowerCase())){
                return priceApiJobStatus;
            }
        }

        return null;
    }

}
