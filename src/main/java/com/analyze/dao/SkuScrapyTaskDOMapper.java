package com.analyze.dao;

import com.analyze.model.SkuScrapyTaskDO;

import java.util.List;
import java.util.Map;


public interface SkuScrapyTaskDOMapper {
    int insert(SkuScrapyTaskDO record);

    int insertSelective(SkuScrapyTaskDO record);

    int updateByTaskId(Map<String, Object> record);

    int updateByTaskSts(Map<String, Object> paramMap);

    int selectCountByTaskSts(Map<String, Object> paramMap);

    List<SkuScrapyTaskDO> selectTaskList(Map<String, Object> paramMap);

    SkuScrapyTaskDO selectByTaskId(Map<String, Object> paramMap);
}