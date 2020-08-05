package com.analyze.dao;

import com.analyze.model.AsinSkuMapDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AsinSkuMapDOMapper {

    AsinSkuMapDO findByAsin(String asin);

    AsinSkuMapDO findBySku(String sku);

    int insert(AsinSkuMapDO record);

    int insertSelective(AsinSkuMapDO record);

    int updateById(AsinSkuMapDO record);

    int insertBatch(List<AsinSkuMapDO> recordList);

}