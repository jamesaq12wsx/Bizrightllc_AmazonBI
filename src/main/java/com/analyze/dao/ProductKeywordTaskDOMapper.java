package com.analyze.dao;

import com.analyze.model.ProductKeywordTaskDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductKeywordTaskDOMapper {

    List<ProductKeywordTaskDO> findAll();

    /**
     * Normal user only get the task they created
     * @param createdBy
     * @return
     */
    List<ProductKeywordTaskDO> findAllByCreatedBy(@Param("createdBy") String createdBy);

    /**
     * Get task by task id
     *
     * @param taskId
     * @return
     */
    ProductKeywordTaskDO findByTaskId(@Param("taskId") String taskId);

    /**
     * Return task is not finished or failed.
     * not include task is in process
     *
     * @return
     */
    List<ProductKeywordTaskDO> findAllNonFinishTask();

    /**
     * The tasks success without files created
     * @return
     */
    List<ProductKeywordTaskDO> findAllSuccessTaskWithoutFile();

    void insert(ProductKeywordTaskDO productKeywordTaskDO);

    void update(ProductKeywordTaskDO productKeywordTaskDO);

    void batchUpdate(@Param("taskList") List<ProductKeywordTaskDO> taskList);

}
