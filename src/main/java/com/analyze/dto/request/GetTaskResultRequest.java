package com.analyze.dto.request;

import lombok.Data;

/**
 * For user request task result
 */
@Data
public class GetTaskResultRequest extends BaseRequest {

    private String taskId;

}
