package com.analyze.model;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Request log
 */
@Data
public class ApiRequestDO {

    private int requestId;

    private int taskId;

    private String apiName;

    private RequestMethod method;

    private String url;

    private String headers;

    private String requestBody;

    private String respondBody;

}
