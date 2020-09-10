package com.analyze.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PriceApiSource {

    @JsonProperty("amazon")
    AMAZON("amazon");

    private String value;

    PriceApiSource(String source) {
        this.value = source;
    }

//    @JsonValue
//    public String toValue(){
//        return value;
//    }

    @JsonCreator
    public static PriceApiSource fromString(String source) {
        for (PriceApiSource priceApiSource: PriceApiSource.values()) {
            if (priceApiSource.getValue().toLowerCase().equals(source.toLowerCase())){
                return priceApiSource;
            }
        }

        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
