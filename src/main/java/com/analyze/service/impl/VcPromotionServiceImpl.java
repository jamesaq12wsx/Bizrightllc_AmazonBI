package com.analyze.service.impl;

import com.analyze.dao.VcPromotionInfoDOMapper;
import com.analyze.dto.VcPromotionInfo;
import com.analyze.model.VcPromotionInfoDO;
import com.analyze.dto.request.VcPromotionSearchCriteria;
import com.analyze.service.VcPromotionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("VcPromotionServiceImpl")
public class VcPromotionServiceImpl implements VcPromotionService {

    @Autowired
    private VcPromotionInfoDOMapper vcPromotionInfoDOMapper;

    @Override
    public List<VcPromotionInfo> getLatestAllPromotions() {

        return vcPromotionInfoDOMapper
                .findAllLastCrawWithProductInfo()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<VcPromotionInfo> getLatestPromotionByCriteria(VcPromotionSearchCriteria criteria) {

        if (criteria == null) {
            return getLatestAllPromotions();
        }

        return vcPromotionInfoDOMapper
                .findLastCrawPromotionWithCriteria(criteria)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public VcPromotionInfo convertToDto(VcPromotionInfoDO vcPromotionInfoDO) {
        ModelMapper modelMapper = new ModelMapper();

        VcPromotionInfo vcPromotionInfo = modelMapper.map(vcPromotionInfoDO, VcPromotionInfo.class);

        return vcPromotionInfo;
    }
}
