package com.analyze.dao;

import java.util.List;
import java.util.Map;

import com.analyze.model.SkuManage;

public interface SkuManageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkuManage record);

    int insertSelective(SkuManage record);

    SkuManage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkuManage record);

    int updateByPrimaryKey(SkuManage record);

	List<Object> getSkumanage(Map<String, Object> paramMap);


}