package com.analyze.dao;

import com.analyze.model.PromotionTaskDO;

import java.util.List;
import java.util.Map;

public interface PromotionTaskDOMapper {
    int insert(PromotionTaskDO record);

    int insertSelective(PromotionTaskDO record);

    List<PromotionTaskDO> selectPromotionList(Map<String, Object> paramMap);

    PromotionTaskDO selectByTaskId(Map<String, Object> paramMap);

    int updatePromotionByTaskId(Map<String, Object> paramMap);

    List<Map<String,Object>> qryPromotionListByDate(Map<String, Object> paramMap);

    int selectCountByTaskSts(Map<String, Object> paramMap);

    PromotionTaskDO selectOneByTaskSts(Map<String, Object> paramMap);

}