package com.analyze.dao;

import cn.hutool.json.JSONObject;
import com.analyze.dto.request.PriceApiRequest;
import com.analyze.dto.response.PriceApiCreateJobResponseBody;
import com.analyze.dto.response.PriceApiReviewResponseBody;
import com.analyze.model.VcPromotionInfoDO;
import com.analyze.service.PriceApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:spring-applicationcontext.xml",
        "classpath*:spring-datasource.xml"})
public class VcPromotionInfoDOMapperTest {

    @Autowired
    private VcPromotionInfoDOMapper vcPromotionInfoDOMapper;

    @Autowired
    private PriceApiService priceApiService;

    @Test
    public void testGetAllPromotion() {
        List<VcPromotionInfoDO> result = vcPromotionInfoDOMapper.findAllLastCrawWithProductInfo();

        Assert.assertTrue(result.size() != 0);
        Assert.assertTrue(result.get(0).getProducts().get(0).getAsinSkuMap() != null);
    }

    @Test
    public void testPriceApiServiceCreateReviewJob() throws Exception {

        PriceApiRequest request = new PriceApiRequest();

        request.setCountry("us");

        request.setMaxPages(3);

        request.setValues("B073JYC4XM\nB074S9BJ6B");

        PriceApiCreateJobResponseBody responseBody = priceApiService.createAmazonReviewJob(request);

        System.out.println(responseBody.toString());
    }

    @Test
    public void testObjectToJsonStr(){

    }

    @Test
    public void testPriceApiCreateReviewJob() throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        JSONObject requestBody = new JSONObject();

        requestBody.put("token", "XVAQTKORTCCPZUXHGUHZXIESMSKXFAJVYZTUSETZZKATHXHHHTXWSNQHFVWCUPGI");
        requestBody.put("source","amazon");
        requestBody.put("topic", "reviews");
        requestBody.put("key", "id");
        requestBody.put("country", "us");
        requestBody.put("max_pages", 3);
        requestBody.put("timeout", 1);
        requestBody.put("values", "B073JYC4XM\nB074S9BJ6B");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity request = new HttpEntity(requestBody.toString(), httpHeaders);

        String createJobResponse = restTemplate.postForObject("https://api.priceapi.com/v2/jobs", request, String.class);

        System.out.println(createJobResponse);

//        PriceApiCreateJobResponseBody responseBody = priceApiService.createAmazonReviewJob(request);

//        System.out.println(responseBody.toString());
    }

    @Test
    public void testPriceApiGetResult() throws Exception {
        PriceApiReviewResponseBody responseBody = priceApiService.getReviewJobResult("5f44015d78c48e27571f6ff4");

        System.out.println(responseBody.toString());
    }

    @Test
    public void testGetPriceApiJobResult() {
        RestTemplate restTemplate = new RestTemplate();
        // This help server deserialize jackson properly
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

//        String result = restTemplate.getForObject("https://api.priceapi.com/v2/jobs/5f44015d78c48e27571f6ff4/download?token=XVAQTKORTCCPZUXHGUHZXIESMSKXFAJVYZTUSETZZKATHXHHHTXWSNQHFVWCUPGI", String.class);
        ResponseEntity<PriceApiCreateJobResponseBody> response = restTemplate
                .exchange("https://api.priceapi.com/v2/jobs/5f44015d78c48e27571f6ff4/download?token=XVAQTKORTCCPZUXHGUHZXIESMSKXFAJVYZTUSETZZKATHXHHHTXWSNQHFVWCUPGI", HttpMethod.GET, null, PriceApiCreateJobResponseBody.class);

        System.out.println(response);
    }

    @Test
    public void testParseStringToLocalDateTime() {
        // ISO-8601 formatted string
        String str = "2020-08-19T23:50:27Z";

        // parse string to `LocalDateTime`
        LocalDateTime dateTime = LocalDateTime.parse(str);

        // print `LocalDateTime`
        System.out.println("Parsed LocalDateTime: " + dateTime);
    }

}
