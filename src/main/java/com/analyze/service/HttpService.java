package com.analyze.service;

import org.springframework.core.ParameterizedTypeReference;

import java.util.Map;

public interface HttpService {

    public <T> T restGet(String url, Map<String, String> headers, ParameterizedTypeReference<T> type);

    public <T> T restPost(String url, Map<String, String> headers, String body, ParameterizedTypeReference<T> type);

}
