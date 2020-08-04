package com.analyze.dao;

import com.analyze.dto.PromotionSetting;
import com.analyze.model.PromotionSettingDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PromotionSettingDOMapper {

    List<PromotionSettingDO> findAll();

    boolean existById(@Param("id") Long id);

    PromotionSettingDO selectById(@Param("id") Long id);

    boolean existByPromotionIdAndAsin(@Param("promotionId") Long promotionId, @Param("asin") String asin);

    List<PromotionSettingDO> findByPromotionIdAndAsin(@Param("promotionId") Long promotionId, @Param("asin") String asin);

    List<PromotionSettingDO> findByPromotionId(@Param("promotionId") long promotionId);

    List<PromotionSettingDO> findByPromotionIds(@Param("promotionIds") List<Long> promotionIds);

    void insertSelective(PromotionSettingDO newRecord);

    void updatePromotion(PromotionSettingDO record);

    void deleteById(Long id);

}
