package com.analyze.service.simple;

import com.analyze.service.HttpService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SimpleHttpService implements HttpService {

    private RestTemplate restTemplate;

    public SimpleHttpService() {
        this.restTemplate = new RestTemplate();

        // This help server deserialize jackson properly
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
    }

    @Override
    public <T> T restGet(String url, Map<String, String> headers, ParameterizedTypeReference<T> type) {

        HttpHeaders httpHeaders = new HttpHeaders();

        if (headers != null && !headers.isEmpty()){

            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);


            for (Map.Entry<String, String> header: headers.entrySet()) {
                httpHeaders.add(header.getKey(), header.getValue());
            }
        }

        HttpEntity requestBody = new HttpEntity(null, httpHeaders);

//        ResponseEntity<T> response = restTemplate.postForEntity(url, requestBody, type);

        ResponseEntity<T> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestBody,
                type
        );

        T responseBody = responseEntity.getBody();

        return responseBody;
    }

    @Override
    public <T> T restPost(String url, Map<String, String> headers, String body, ParameterizedTypeReference<T> type) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (headers != null && !headers.isEmpty()){

            httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);


            for (Map.Entry<String, String> header: headers.entrySet()) {
                httpHeaders.add(header.getKey(), header.getValue());
            }
        }

        HttpEntity request = new HttpEntity(body, httpHeaders);

//        ResponseEntity<T> response = restTemplate.postForEntity(url, requestBody, type);

        ResponseEntity<T> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                type
        );

        T responseBody = responseEntity.getBody();

        return responseBody;
    }
}
