package com.analyze.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Search product review keyword task
 */
@Data
public class ProductKeywordTaskDO {

    /**
     * UUID
     */
    private String taskId;

    /**
     *
     */
    private String taskName;

    private String downloadFilepath;

    private String downloadFilename;

    private String taskStatus;

    /**
     * Asins seperate by ','
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
    private LocalDateTime createdAt;

    /**
     * Update date time
     */
    private LocalDateTime updatedAt;

}
