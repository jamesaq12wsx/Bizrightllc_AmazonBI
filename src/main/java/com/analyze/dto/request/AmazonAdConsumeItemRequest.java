package com.analyze.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AmazonAdConsumeItemRequest extends BaseRequest {

    /**
     *
     */
    private String asin;

    /**
     * product name
     */
    private String name;

    /**
     * keyword
     */
    private String keyword;

    /**
     * The product ad need consume or not
     * if true, means in black list
     * if false, means in white list
     */
    private Boolean consume;

}
