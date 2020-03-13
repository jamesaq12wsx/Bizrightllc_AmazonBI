package com.analyze.dao;

import java.util.List;
import java.util.Map;

import com.analyze.model.ProductLabel;

public interface ProductLabelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductLabel record);

    int insertSelective(ProductLabel record);

    ProductLabel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductLabel record);

    int updateByPrimaryKey(ProductLabel record);

	List<Object> getProductLabel();

	int selectByLabelName(String labelname);

	int selectTableAsinLabel(Map<String, Object> requestParams);

	int addAsinLabel(Map<String, Object> requestParams);

	List<Object> getProductLabelByasin(Map<String, Object> requestParams);

	int delAsinLabel(Map<String, Object> map);
}