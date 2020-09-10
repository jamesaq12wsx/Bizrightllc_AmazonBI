package com.analyze.dao;

import com.analyze.model.PriceApiRequestDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PriceApiRequestDOMapper {

    List<PriceApiRequestDO> findAll();

    boolean existByRequestId(@Param("requestId") Long requestId);

    PriceApiRequestDO selectByRequestId(@Param("requestId") Long requestId);

    List<PriceApiRequestDO> findByTaskId(@Param("taskId") String taskId);

    /**
     * Get the request haven't new
     * Status is Create
     */
    List<PriceApiRequestDO> findUnNewByTaskIds(@Param("taskIds") List<String> taskIds);

    /**
     * Get the request have not finish
     * status not create(have not start), finished and canceled
     *
     * @param taskIds
     * @return
     */
    List<PriceApiRequestDO> findRequestHaveNotFinished(@Param("taskIds") List<String> taskIds);

    /**
     * Get the request unfhinished and uncanceled
     * @return
     */
    List<PriceApiRequestDO> findAllUnFinishedAndCanceled();

    void insert(PriceApiRequestDO newRecord);

    void batchInsert(List<PriceApiRequestDO> newRecords);

    /**
     * Only allow to update jobId and status
     * @param record
     */
    void updatePriceApiRequest(PriceApiRequestDO record);

    void batchUpdate(@Param("requestList") List<PriceApiRequestDO> records);

    void deleteById(Long id);

}
