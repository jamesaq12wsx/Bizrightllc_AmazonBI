package com.analyze.service;

import com.analyze.cons.TaskSts;
import com.analyze.dto.ProductKeywordTask;
import com.analyze.dto.ProductKeywordTaskResult;
import com.analyze.dto.request.NewProductKeywordTaskRequest;

import java.util.List;

/**
 *
 */
public interface ProductKeywordTaskService {

    /**
     * Create new task
     * This method will create task record and
     * the requests record for the task
     *
     * Schedule task will check the task and make sure the
     * result from request saved
     */
    void createNewTask(NewProductKeywordTaskRequest request);

    List<ProductKeywordTask> getTasksByCreatedUser(String userId);

    /**
     * Process the task
     * check the unfinish task
     * check the request result
     *
     * @return
     */
    void processTask();

    boolean taskCompleted(int taskId);

    ProductKeywordTaskResult getTaskResult(String taskId) throws Exception;

    /**
     * Get result
     * @param taskId
     * @return
     */
    String getTaskResult(int taskId);

    TaskSts getTaskStatus(int taskId);

}
