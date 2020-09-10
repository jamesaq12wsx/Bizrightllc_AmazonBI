package com.analyze.dto.request;

import lombok.Data;

/**
 * Keyword task request body
 */
@Data
public class NewProductKeywordTaskRequest extends BaseRequest {

    /**
     * Task name,
     * could be duplicate, let user recongnize every task search
     */
    private String taskName;

    /**
     * asins string, should be separate by new line
     * '\n'
     *
     * this property should be list string, but ModelRequest cannot parse list,
     * TODO: change to list string
     */
    private String asins;

    /**
     * keywords string, should be separate by new line '\n'
     *
     */
    private String keywords;

}
