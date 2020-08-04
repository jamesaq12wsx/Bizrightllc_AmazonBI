package com.analyze.service.impl;

import com.analyze.dao.PromotionSettingDOMapper;
import com.analyze.dto.request.DeletePromotionSettingRequest;
import com.analyze.dto.request.NewPromotionSettingRequest;
import com.analyze.dto.request.PromotionSettingCriteria;
import com.analyze.dto.request.UpdatePromotionSettingRequest;
import com.analyze.exception.ServiceException;
import com.analyze.model.PromotionSettingDO;
import com.analyze.dto.PromotionSetting;
import com.analyze.service.PromotionSettingService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service("PromotionSettingServiceImpl")
public class PromotionSettingServiceImpl implements PromotionSettingService {

    @Autowired
    private PromotionSettingDOMapper promotionSettingDOMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PromotionSetting> getAllPromotionSetting() {
        return promotionSettingDOMapper.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PromotionSetting> getPromotionSettingByPromotionId(Long promotionId) {
        return promotionSettingDOMapper.findByPromotionId(promotionId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PromotionSetting> getPromotionSettingByCriteria(PromotionSettingCriteria criteria) {
        if (criteria.getPromotionIds() != null && Arrays.asList(criteria.getPromotionIds()).stream().filter(id -> id != null).count() != 0) {
            return promotionSettingDOMapper
                    .findByPromotionIds(criteria.getPromotionIds().stream().filter(id -> id != null).collect(Collectors.toList()))
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

//        if (criteria.getPromotionId() != null && StringUtils.isNotEmpty(criteria.getAsin())){
//            return promotionSettingDOMapper
//                    .findByPromotionIdAndAsin(criteria.getPromotionId(), criteria.getAsin())
//                    .stream()
//                    .map(this::convertToDTO)
//                    .collect(Collectors.toList());
//        }
//
        if (criteria.getPromotionId() != null){
            return promotionSettingDOMapper.findByPromotionId(criteria.getPromotionId())
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        return new LinkedList<>();
    }

    @Override
    public void addNewPromotionSetting(NewPromotionSettingRequest request) throws ServiceException {

        if (request.getPromotionId() == null && StringUtils.isEmpty(request.getAsin()) && request.getPrice() == null && request.getFunding() == null ){
            throw new ServiceException(String.format("New promotion setting promotion id, asin, price, funding cannot be null"));
        }

        if (promotionSettingDOMapper.existByPromotionIdAndAsin(request.getPromotionId(), request.getAsin())) {
            throw new ServiceException(String.format("Promotion Id: {}, Asin: {} already exist", request.getPromotionId(), request.getAsin()));
        }

        PromotionSettingDO promotionSettingDO = modelMapper.map(request, PromotionSettingDO.class);

        promotionSettingDO.setCreatedBy(request.getUserId());
        promotionSettingDO.setUpdatedBy(request.getUserId());
        promotionSettingDO.setCreatedAt(LocalDateTime.now());
        promotionSettingDO.setUpdatedAt(LocalDateTime.now());

        promotionSettingDOMapper.insertSelective(promotionSettingDO);
    }

    @Override
    public void updatePromotionSetting(UpdatePromotionSettingRequest request) throws ServiceException {

        if (request.getId() == null){
            throw new ServiceException("Update promotion setting id cannot be empty");
        }

        PromotionSettingDO oldPromotionSettingDO = promotionSettingDOMapper.selectById(request.getId());

        if (oldPromotionSettingDO == null) {
            throw new ServiceException(String.format("Promotion setting id: {} is not exist", request.getId()));
        }

        // If promotion id or asin change
        if (!request.getAsin().equals(oldPromotionSettingDO.getAsin()) || !request.getPromotionId().equals(oldPromotionSettingDO.getPromotionId())) {
            if (promotionSettingDOMapper.existByPromotionIdAndAsin(request.getPromotionId(), request.getAsin())) {
                throw new ServiceException(String.format("Promotion Id: {}, Asin: {} already exist", request.getPromotionId(), request.getAsin()));
            }
        }

        oldPromotionSettingDO.setPromotionId(request.getPromotionId());
        oldPromotionSettingDO.setAsin(request.getAsin());
        oldPromotionSettingDO.setPrice(request.getPrice());
        oldPromotionSettingDO.setFunding(request.getFunding());

        oldPromotionSettingDO.setUpdatedBy(request.getUserId());
        oldPromotionSettingDO.setUpdatedAt(LocalDateTime.now());

        promotionSettingDOMapper.updatePromotion(oldPromotionSettingDO);
    }

    @Override
    public void deletePromotionSetting(DeletePromotionSettingRequest request) throws ServiceException {

        Long id = request.getId();

        if (id == null) {
            throw new ServiceException("Delete promotion setting id could not be null");
        }

        boolean exist = promotionSettingDOMapper.existById(id);

        if (exist == false) {
            throw new ServiceException(String.format("Promotion setting id: {} not exist", id));
        }

        promotionSettingDOMapper.deleteById(id);

    }

    private PromotionSettingDO convertToEntity(PromotionSetting promotionSetting) {
        PromotionSettingDO promotionSettingDO = modelMapper.map(promotionSetting, PromotionSettingDO.class);

        return promotionSettingDO;
    }

    private PromotionSetting convertToDTO(PromotionSettingDO promotionSettingDO) {

        PromotionSetting promotionSetting = modelMapper.map(promotionSettingDO, PromotionSetting.class);

        return promotionSetting;
    }
}
