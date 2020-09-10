package com.analyze.service.simple;

import com.analyze.dto.response.PriceApiCreateJobResponseBody;
import com.analyze.dto.request.PriceApiRequest;
import com.analyze.dto.response.PriceApiReviewResponseBody;
import com.analyze.model.PriceApiTopic;
import com.analyze.service.HttpService;
import com.analyze.service.PriceApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j
public class SimplePriceApiService implements PriceApiService {

    private HttpService httpService;

    private String token = "XVAQTKORTCCPZUXHGUHZXIESMSKXFAJVYZTUSETZZKATHXHHHTXWSNQHFVWCUPGI";

    private String priceApiUrl = "https://api.priceapi.com/v2/jobs";

    private String priceApiJobUrl = "https://api.priceapi.com/v2/jobs/%s/download?token=%s";

    private ObjectMapper objectMapper;

    @Autowired
    public SimplePriceApiService(HttpService httpService) {
        this.httpService = httpService;
        objectMapper = new ObjectMapper();
    }

    @Override
    public PriceApiCreateJobResponseBody createNewJob(PriceApiRequest request) throws Exception {

        request.setToken(token);

        checkRequest(request);

        PriceApiCreateJobResponseBody response = httpService.restPost(priceApiUrl, null, objectMapper.writeValueAsString(request), new ParameterizedTypeReference<PriceApiCreateJobResponseBody>(){});

        return response;
    }

    @Override
    public PriceApiReviewResponseBody getReviewJobResult(String jobId) throws Exception {

        if( (token == null || StringUtils.isEmpty(token)) && (this.token == null || StringUtils.isEmpty(this.token))){
            throw new Exception("Price api token cannot be empty");
        }

        token = StringUtils.isEmpty(token) ? this.token : token;

        if(jobId == null || StringUtils.isEmpty(jobId)){
            throw new Exception("Price api get job results job id cannot be empty");
        }

        PriceApiReviewResponseBody result = httpService.restGet(String.format(priceApiJobUrl, jobId, token), null, new ParameterizedTypeReference<PriceApiReviewResponseBody>() {});

        return result;
    }

    @Override
    public PriceApiCreateJobResponseBody createAmazonReviewJob(PriceApiRequest requestBody) throws Exception {

        if(requestBody == null){
            throw new Exception("Price api amazon review requestBody body cannot be empty.");
        }

        requestBody.setTopic(PriceApiTopic.REVIEWS);

        requestBody.setSource("amazon");

        requestBody.setKey("id");

        requestBody.setToken(token);

        if (requestBody.getMaxPages() == 0){
            // default value
            requestBody.setMaxPages(3);
        }

        requestBody.setCountry("us");

        checkRequest(requestBody);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity request = new HttpEntity(objectMapper.writeValueAsString(requestBody), httpHeaders);

        ResponseEntity<PriceApiCreateJobResponseBody> response = restTemplate.postForEntity(priceApiUrl, request, PriceApiCreateJobResponseBody.class);

        if(response.getStatusCode() != HttpStatus.OK){
            log.debug("Create price api job response not 200");
            log.debug(response.toString());
            throw new Exception("Create price api job response not 200");
        }

        return response.getBody();

    }

    private void checkRequest(PriceApiRequest request) throws Exception {
        if(request.getValues() == null || StringUtils.isEmpty(request.getValues()) ){
            throw new Exception("Price api requestBody values cannot be empty");
        }

        if(request.getToken() == null || StringUtils.isEmpty(request.getToken())){
            throw new Exception("Price api requestBody token cannot be empty");
        }

        if (request.getCountry() == null || StringUtils.isEmpty(request.getCountry())){
            throw new Exception("Price api requestBody country cannot be empty");
        }

        if (request.getSource() == null ){
            throw new Exception("Price api requestBody source cannot be empty");
        }

        if (request.getKey() == null || StringUtils.isEmpty(request.getKey())){
            throw new Exception("Price api requestBody id cannot be empty");
        }
    }
}
