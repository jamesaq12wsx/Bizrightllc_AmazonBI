package com.analyze.dto.response;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;

@Data
public class ResponseDTO {

    private String status;

    /**
     * Response data if the status success
     */
    private Object data;

    /**
     * Response msg if status failed
     */
    private String msg;

}
