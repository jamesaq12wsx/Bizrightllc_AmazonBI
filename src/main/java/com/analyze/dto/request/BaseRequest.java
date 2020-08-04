package com.analyze.dto.request;

import lombok.Data;

@Data
public abstract class BaseRequest {

    private String isParent;
    private String username;
    private String userId;

}
