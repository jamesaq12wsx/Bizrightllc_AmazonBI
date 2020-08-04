package com.analyze.service;

import com.analyze.dto.request.DeletePromotionSettingRequest;
import com.analyze.dto.request.NewPromotionSettingRequest;
import com.analyze.dto.request.PromotionSettingCriteria;
import com.analyze.dto.request.UpdatePromotionSettingRequest;
import com.analyze.exception.ServiceException;
import com.analyze.dto.PromotionSetting;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PromotionSettingService {

    /**
     * Return all promotion setting
     * @return
     */
    public List<PromotionSetting> getAllPromotionSetting();

    /**
     * Return promotion setting of promotion id
     * @param promotionId
     * @return
     */
    public List<PromotionSetting> getPromotionSettingByPromotionId(Long promotionId);

    public List<PromotionSetting> getPromotionSettingByCriteria(PromotionSettingCriteria criteria);

    public void addNewPromotionSetting(NewPromotionSettingRequest request) throws ServiceException;

    public void updatePromotionSetting(UpdatePromotionSettingRequest request) throws ServiceException;

    public void deletePromotionSetting(DeletePromotionSettingRequest request) throws ServiceException;

}
