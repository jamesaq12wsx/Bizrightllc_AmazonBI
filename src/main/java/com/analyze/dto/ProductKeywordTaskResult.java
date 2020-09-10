package com.analyze.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductKeywordTaskResult {

    private String taskId;

    private String taskName;

    private String asins;

    private String keywords;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime createdAt;

    private List<ProductKeywordResult> productKeywordResults;

}
