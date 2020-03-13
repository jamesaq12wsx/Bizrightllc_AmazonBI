package com.analyze.dao;

import com.analyze.model.SkuOffersInfo;

public interface SkuOffersInfoMapper {
    int insert(SkuOffersInfo record);

    int insertSelective(SkuOffersInfo record);
}