package com.analyze.controller;

import com.analyze.dto.AmazonAdConsumeSettingDTO;
import com.analyze.dto.request.AmazonAdConsumeItemRequest;
import com.analyze.dto.request.AmazonAdConsumeSettingRequest;
import com.analyze.service.AmazonAdService;
import com.analyze.util.RequestUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/amazonad")
public class AmazonAdController extends BaseController {

    @Autowired
    private AmazonAdService amazonAdService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity getAllSetting(HttpServletRequest request) throws UnsupportedEncodingException {

        try{

            Map<String,Object> params = getRequestParams(request);

            List<AmazonAdConsumeSettingDTO> settings = amazonAdService.getAllSetting();

            Map<String, Object> response = RequestUtil.createSuccessResponse(settings);

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (Exception e){

            Map<String, Object> response = RequestUtil.createFailResponse(e);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @RequestMapping(value = "/userall", method = RequestMethod.GET)
    public ResponseEntity getAllUserSetting(HttpServletRequest request) throws UnsupportedEncodingException {

        try{

            Map<String,Object> params = getRequestParams(request);

            String userId = params.get("userId").toString();

            List<AmazonAdConsumeSettingDTO> settings = amazonAdService.getAllSettingByCreatedUser(Long.valueOf(userId));

            Map<String, Object> response = RequestUtil.createSuccessResponse(settings);

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (Exception e){

            Map<String, Object> response = RequestUtil.createFailResponse(e);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @RequestMapping(value = "/{settingId}", method = RequestMethod.GET)
    @ResponseBody
    public Object getSettingById(HttpServletRequest request, @PathVariable Long settingId){

        try{
            Map<String, Object> params = getRequestParams(request);

            String userId = params.get("userId").toString();

            AmazonAdConsumeSettingDTO setting = amazonAdService.getSettingById(settingId);

            Map<String, Object> responseBody = RequestUtil.createSuccessResponse(setting);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        }catch (Exception ex){
            Map<String, Object> responseBody = RequestUtil.createFailResponse(ex);

            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public Object addNewSetting(HttpServletRequest request){

        try{
            Map<String, Object> params = getRequestParams(request);

            AmazonAdConsumeSettingRequest newSetting = modelMapper.map(params, AmazonAdConsumeSettingRequest.class);

            AmazonAdConsumeSettingDTO savedSetting = amazonAdService.insertSetting(newSetting);

            Map<String, Object> responseBody = RequestUtil.createSuccessResponse(savedSetting);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        }catch (Exception ex){
            Map<String, Object> responseBody = RequestUtil.createFailResponse(ex);

            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/{settingId}/update", method = RequestMethod.POST)
    @ResponseBody
    public Object updateSetting(HttpServletRequest request, @PathVariable("settingId") Long settingId){

        try{
            Map<String, Object> params = getRequestParams(request);

            AmazonAdConsumeSettingRequest newSetting = modelMapper.map(params, AmazonAdConsumeSettingRequest.class);

            AmazonAdConsumeSettingDTO savedSetting = amazonAdService.updateSetting(settingId, newSetting);

            Map<String, Object> responseBody = RequestUtil.createSuccessResponse(savedSetting);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        }catch (Exception ex){
            Map<String, Object> responseBody = RequestUtil.createFailResponse(ex);

            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/{settingId}/active", method = RequestMethod.POST)
    @ResponseBody
    public Object activeSetting(HttpServletRequest request, @PathVariable("settingId") Long settingId){

        try{
            Map<String, Object> params = getRequestParams(request);

            AmazonAdConsumeSettingRequest request1 = modelMapper.map(params, AmazonAdConsumeSettingRequest.class);

            Long userId = Long.valueOf(request1.getUserId());

            amazonAdService.startAdConsume(userId, settingId);

            Map<String, Object> responseBody = RequestUtil.createSuccessResponse(SUCCESS);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        }catch (Exception ex){
            Map<String, Object> responseBody = RequestUtil.createFailResponse(ex);

            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/{settingId}/disactive", method = RequestMethod.POST)
    @ResponseBody
    public Object disActiveSetting(HttpServletRequest request, @PathVariable("settingId") Long settingId){

        try{
            Map<String, Object> params = getRequestParams(request);

            AmazonAdConsumeSettingRequest request1 = modelMapper.map(params, AmazonAdConsumeSettingRequest.class);

            Long userId = Long.valueOf(request1.getUserId());

            amazonAdService.stopAdConsume(userId, settingId);

            Map<String, Object> responseBody = RequestUtil.createSuccessResponse(SUCCESS);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        }catch (Exception ex){
            Map<String, Object> responseBody = RequestUtil.createFailResponse(ex);

            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/{settingId}/remove", method = RequestMethod.POST)
    @ResponseBody
    public Object removeSetting(HttpServletRequest request, @PathVariable("settingId") Long settingId){

        try{
            Map<String, Object> params = getRequestParams(request);

            AmazonAdConsumeSettingRequest request1 = modelMapper.map(params, AmazonAdConsumeSettingRequest.class);

            amazonAdService.removeSetting(Long.valueOf(request1.getUserId()), settingId);

            Map<String, Object> responseBody = RequestUtil.createSuccessResponse(SUCCESS);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        }catch (Exception ex){
            Map<String, Object> responseBody = RequestUtil.createFailResponse(ex);

            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/{settingId}/item", method = RequestMethod.POST)
    @ResponseBody
    public Object newItem(HttpServletRequest request, @PathVariable("settingId") Long settingId){

        try{
            Map<String, Object> params = getRequestParams(request);

            AmazonAdConsumeItemRequest newItem = modelMapper.map(params, AmazonAdConsumeItemRequest.class);

            amazonAdService.insertAdConsumeItem(settingId, newItem);

            AmazonAdConsumeSettingDTO setting = amazonAdService.getSettingById(settingId);

            Map<String, Object> responseBody = RequestUtil.createSuccessResponse(setting);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        }catch (Exception ex){

            Map<String, Object> responseBody = RequestUtil.createFailResponse(ex);

            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/{settingId}/item/{itemId}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateItem(HttpServletRequest request, @PathVariable("settingId") Long settingId, @PathVariable("itemId")Long itemId){

        try{
            Map<String, Object> params = getRequestParams(request);

            AmazonAdConsumeItemRequest updateItem = modelMapper.map(params, AmazonAdConsumeItemRequest.class);

            amazonAdService.updateAdConsumeItem(settingId, itemId, updateItem);

            AmazonAdConsumeSettingDTO setting = amazonAdService.getSettingById(settingId);

            Map<String, Object> responseBody = RequestUtil.createSuccessResponse(setting);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        }catch (Exception ex){

            Map<String, Object> responseBody = RequestUtil.createFailResponse(ex);

            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/{settingId}/item/{itemId}/white", method = RequestMethod.POST)
    @ResponseBody
    public Object moveItemToWhiteList(HttpServletRequest request, @PathVariable("settingId") Long settingId, @PathVariable("itemId")Long itemId){

        try{
            Map<String, Object> params = getRequestParams(request);

            AmazonAdConsumeItemRequest request1 = modelMapper.map(params, AmazonAdConsumeItemRequest.class);

            Long userId = Long.valueOf(request1.getUserId());

            amazonAdService.whileListItem(userId, itemId);

            AmazonAdConsumeSettingDTO setting = amazonAdService.getSettingById(settingId);

            Map<String, Object> responseBody = RequestUtil.createSuccessResponse(setting);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        }catch (Exception ex){

            Map<String, Object> responseBody = RequestUtil.createFailResponse(ex);

            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/{settingId}/item/{itemId}/black", method = RequestMethod.POST)
    @ResponseBody
    public Object moveItemToBlackList(HttpServletRequest request, @PathVariable("settingId") Long settingId, @PathVariable("itemId")Long itemId){

        try{
            Map<String, Object> params = getRequestParams(request);

            AmazonAdConsumeItemRequest request1 = modelMapper.map(params, AmazonAdConsumeItemRequest.class);

            Long userId = Long.valueOf(request1.getUserId());

            amazonAdService.blackListItem(userId, itemId);

            AmazonAdConsumeSettingDTO setting = amazonAdService.getSettingById(settingId);

            Map<String, Object> responseBody = RequestUtil.createSuccessResponse(setting);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        }catch (Exception ex){

            Map<String, Object> responseBody = RequestUtil.createFailResponse(ex);

            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/{settingId}/item/{itemId}/remove", method = RequestMethod.POST)
    @ResponseBody
    public Object removeSettingItem(HttpServletRequest request, @PathVariable("settingId") Long settingId, @PathVariable("itemId") Long itemId){

        try{
            Map<String, Object> params = getRequestParams(request);

            AmazonAdConsumeSettingRequest request1 = modelMapper.map(params, AmazonAdConsumeSettingRequest.class);

            amazonAdService.removeItem(Long.valueOf(request1.getUserId()), itemId);

            Map<String, Object> responseBody = RequestUtil.createSuccessResponse(SUCCESS);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        }catch (Exception ex){
            Map<String, Object> responseBody = RequestUtil.createFailResponse(ex);

            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
