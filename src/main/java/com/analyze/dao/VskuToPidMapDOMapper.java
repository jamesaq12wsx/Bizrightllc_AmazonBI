package com.analyze.dao;

import com.analyze.model.VskuToPidMapDO;

public interface VskuToPidMapDOMapper {
    int insert(VskuToPidMapDO record);

    int insertSelective(VskuToPidMapDO record);
}