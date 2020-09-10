package com.analyze.dao;

import com.analyze.dto.ProductReview;
import com.analyze.model.ProductReviewDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductReviewDOMapper {

    public List<ProductReviewDO> findAll();

    public List<ProductReviewDO> findAllByTaskId(@Param("taskId") String taskId);

    public void insert(ProductReviewDO reviewDO);

    /**
     * Insert review if not exists,
     * Update review if exists
     *
     * @param reviewDO
     */
    public void insertOrUpdate(ProductReviewDO reviewDO);

    public void batchInsert(List<ProductReviewDO> reviewDOSList);

}
