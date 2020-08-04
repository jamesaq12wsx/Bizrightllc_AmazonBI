package com.analyze.controller;

import com.analyze.dto.PromotionSetting;
import com.analyze.dto.VcPromotionInfo;
import com.analyze.dto.request.*;
import com.analyze.exception.ServiceException;
import com.analyze.service.PromotionSettingService;
import com.analyze.service.VcPromotionService;
import com.analyze.util.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 2018-04-18
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/promotion")
public class PromotionController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(PromotionController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VcPromotionService vcPromotionService;

    @Autowired
    private PromotionSettingService promotionSettingService;
//
//    @RequestMapping(value = "/setting/all", method = RequestMethod.GET)
//    @ResponseBody
//    public Object getPromotionSettings(HttpServletRequest request){
//
//        try{
//            List<PromotionSetting> promotionSettingDOList = promotionSettingService.getAllPromotionSetting();
//
//            return RequestUtil.createSuccessResponse(promotionSettingDOList);
//        }catch (Exception e){
//            return RequestUtil.createFailResponse(e);
//        }
//
//    }

    @RequestMapping(value = "/setting/search", method = RequestMethod.POST)
    @ResponseBody
    public Object searchPromotionSetting(HttpServletRequest request) {

        try {

            Map<String, Object> requestParams = getRequestParams(request);

            PromotionSettingCriteria criteria = modelMapper.map(requestParams, PromotionSettingCriteria.class);

            List<Long> ids = Arrays.asList(criteria.getPromotionIdsStr().split(","))
                    .stream()
                    .map(s -> {
                        try {
                            return Long.parseLong(s);
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(id -> id != null)
                    .collect(Collectors.toList());

            criteria.setPromotionIds(ids);

//            criteria = modelMapper.map(requestParams, PromotionSettingCriteria.class);

            if (criteria != null && criteria.getPromotionIds() == null) {
                throw new ServiceException("Promotion Setting Criteria Cannot be NULL");
            }

            List<PromotionSetting> resultData = promotionSettingService.getPromotionSettingByCriteria(criteria);

            return RequestUtil.createSuccessResponse(resultData);

        } catch (Exception e) {
            return RequestUtil.createFailResponse(e);
        }

    }

    @RequestMapping(value = "/setting/add", method = RequestMethod.POST)
    @ResponseBody
    public Object addPromotionSetting(HttpServletRequest request) {

        try {

            Map<String, Object> requestParams = getRequestParams(request);

            NewPromotionSettingRequest requestBody = modelMapper.map(requestParams, NewPromotionSettingRequest.class);

            promotionSettingService.addNewPromotionSetting(requestBody);

            return RequestUtil.createSuccessResponse("Success");

        } catch (Exception e) {
            e.printStackTrace();
            return RequestUtil.createFailResponse(e);
        }

    }

    @RequestMapping(value = "/setting/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePromotionSetting(HttpServletRequest request) {

        try {

            Map<String, Object> requestParams = getRequestParams(request);

            UpdatePromotionSettingRequest requestBody = modelMapper.map(requestParams, UpdatePromotionSettingRequest.class);

            promotionSettingService.updatePromotionSetting(requestBody);

            return RequestUtil.createSuccessResponse("Success");

        } catch (Exception e) {
            return RequestUtil.createFailResponse(e);
        }

    }

    @RequestMapping(value = "/setting/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object removePromotionSetting(HttpServletRequest request) {

        try {

            Map<String, Object> requestParams = getRequestParams(request);

            DeletePromotionSettingRequest requestBody = modelMapper.map(requestParams, DeletePromotionSettingRequest.class);

            promotionSettingService.deletePromotionSetting(requestBody);

            return RequestUtil.createSuccessResponse("Success");

        } catch (Exception e) {
            return RequestUtil.createFailResponse(e);
        }

    }

    @RequestMapping(value = "/vc", method = RequestMethod.POST)
    @ResponseBody
    public Object getVcPromotion(HttpServletRequest request) {

        try {

            Map<String, Object> params = getRequestParams(request);

            VcPromotionSearchCriteria criteria = modelMapper.map(params, VcPromotionSearchCriteria.class);

            List<VcPromotionInfo> data = vcPromotionService.getLatestPromotionByCriteria(criteria);

            return RequestUtil.createSuccessResponse(data);

        } catch (Exception e) {
            return RequestUtil.createFailResponse(e);
        }
    }

//    @RequestMapping(value = "/test", method = RequestMethod.POST)
//    @ResponseBody
//    public Object testPost(HttpServletRequest request, @RequestBody TestRequest requestBody){
//
//        Map<String,Object> resultMap = new HashMap<>();
//
//        try{
//
//            Map<String, Object> map = getRequestParams(request);
//
////            TestRequest requestBody = modelMapper.map(map, TestRequest.class);
//
//            resultMap.put(STATUS, SUCCESS);
//            resultMap.put("data", requestBody);
//
//        }catch (Exception e){
//
//            resultMap.put(STATUS, FAIL);
//
//            resultMap.put(MSG, e.getMessage());
//
//        }
//
//        return resultMap;
//    }

}
