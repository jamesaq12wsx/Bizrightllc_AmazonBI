package com.analyze.dao;

import com.analyze.model.SkuScrapyTaskVSkuList;

import java.util.List;

public interface SkuScrapyTaskVSkuListMapper {
    int insert(SkuScrapyTaskVSkuList record);

    int insertSelective(SkuScrapyTaskVSkuList record);

    int insertBatch(List<SkuScrapyTaskVSkuList> recordList);
}