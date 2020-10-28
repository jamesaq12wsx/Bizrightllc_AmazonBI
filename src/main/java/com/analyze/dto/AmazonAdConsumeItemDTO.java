package com.analyze.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * This class is store the product that need ad comsume or not
 *
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmazonAdConsumeItemDTO {

    /**
     * Id
     */
    private Long id;

    /**
     * Ad consume setting id
     */
    private Long settingId;

    private String asin;

    private String name;

    private String keyword;

    /**
     * The product ad need consume or not
     * if true, means in black list
     * if false, means in white list
     */
    private Boolean consume;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String createdBy;

    private String updatedBy;

    private Boolean removed;

}
