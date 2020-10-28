package com.analyze.dao;

import com.analyze.model.AmazonAdConsumeLogDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The class store the amazon ad consume
 *
 */
@Repository
@Mapper
public interface AmazonAdConsumeLogDOMapper {

    /**
     * Get all Log
     * @return
     */
    List<AmazonAdConsumeLogDO> getAll();

    /**
     *
     * @param settingId
     * @return
     */
    List<AmazonAdConsumeLogDO> getAllBySettingId(@Param("settingId") Long settingId);

    /**
     *
     * @param record
     */
    void insert(AmazonAdConsumeLogDO record);

}
