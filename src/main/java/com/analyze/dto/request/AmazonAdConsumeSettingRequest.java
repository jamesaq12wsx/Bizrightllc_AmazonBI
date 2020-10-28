package com.analyze.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AmazonAdConsumeSettingRequest extends BaseRequest {

    private String name;

    private String description;

    /**
     * Search words devide by ','
     */
    private String searchWords;

}
