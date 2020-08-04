package com.analyze.dao;

import com.analyze.model.VcPromotionInfoDO;
import com.analyze.dto.request.VcPromotionSearchCriteria;

import java.util.List;

public interface VcPromotionInfoDOMapper {

    public List<VcPromotionInfoDO> findAll();

    public List<VcPromotionInfoDO> findAllWithProductInfo();

    public List<VcPromotionInfoDO> findAllLastCrawWithProductInfo();

    /**
     * Get last craw promotion data with criteria
     * @param vcPromotionSearchCriteria
     * @return
     */
    public List<VcPromotionInfoDO> findLastCrawPromotionWithCriteria(VcPromotionSearchCriteria vcPromotionSearchCriteria);

}
