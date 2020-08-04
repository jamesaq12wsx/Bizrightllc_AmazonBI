package com.analyze.service;

import com.analyze.dto.VcPromotionInfo;
import com.analyze.dto.request.VcPromotionSearchCriteria;

import java.util.List;

/**
 * This service get the vendor central promotion data
 */
public interface VcPromotionService {

    List<VcPromotionInfo> getLatestAllPromotions();

    List<VcPromotionInfo> getLatestPromotionByCriteria(VcPromotionSearchCriteria criteria);

}
