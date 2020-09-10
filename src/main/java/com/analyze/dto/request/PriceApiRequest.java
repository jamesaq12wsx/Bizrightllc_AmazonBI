package com.analyze.dto.request;

import com.analyze.model.PriceApiSource;
import com.analyze.model.PriceApiTopic;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * This is the request dto to price api
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PriceApiRequest {

    /**
     * This is the internal request id for server recognize the request
     * this wont serialize when doing price api request
     */
//    @JsonIgnore
//    private String requestId;

    private String token;

    private String country;

    private String source;

    private PriceApiTopic topic;

    private String key;

    @JsonProperty("max_pages")
    private int maxPages;

    private String values;

//    @JsonIgnoreProperties
//    private List<String> values;
//
//    @JsonProperty("values")
//    public String getValues(){
//        if(values != null){
//            return StringUtils.join(values, "\n");
//        }else{
//            return null;
//        }
//    }

//    public void setValues(List<String> values) {
//        this.values = values;
//
//        if(values != null){
//            this.valuesStr = StringUtils.join(values, "\n");
//        }else{
//            this.valuesStr = null;
//        }
//    }
//
//    @JsonProperty("values")
//    private String valuesStr;

}
