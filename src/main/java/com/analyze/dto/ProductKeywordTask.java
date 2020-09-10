package com.analyze.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductKeywordTask {

    /**
     * UUID
     */
    private String taskId;

    /**
     * Task name
     * user set the task name
     */
    private String taskName;

    private String downloadFilepath;

    private String downloadFilename;

    private String taskStatus;

    /**
     * Asins seperate by '\n'
     */
    private String asins;

//    /**
//     * The price api could only take 1000 values
//     * per request, this show how many request need to do
//     */
//    private int requestNeedToDo;

    private String keywords;

    /**
     * Create user id
     */
    private String createdBy;

    /**
     * Update user id
     */
    private String updatedBy;

    /**
     * Create date time
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime createdAt;

    /**
     * Update date time
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime updatedAt;

}
