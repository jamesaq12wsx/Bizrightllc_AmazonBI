package com.analyze.dto.request;

import com.analyze.dto.request.BaseRequest;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TestRequest extends BaseRequest {

    private String name;
    private LocalDate birthday;

}
