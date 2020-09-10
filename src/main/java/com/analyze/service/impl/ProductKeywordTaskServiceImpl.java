package com.analyze.service.impl;

import cn.hutool.core.util.IdUtil;
import com.analyze.cons.TaskSts;
import com.analyze.dao.PriceApiRequestDOMapper;
import com.analyze.dao.ProductKeywordTaskDOMapper;
import com.analyze.dao.ProductReviewDOMapper;
import com.analyze.dto.ProductKeywordResult;
import com.analyze.dto.ProductKeywordTask;
import com.analyze.dto.ProductKeywordTaskResult;
import com.analyze.dto.ProductReview;
import com.analyze.dto.request.NewProductKeywordTaskRequest;
import com.analyze.dto.request.PriceApiRequest;
import com.analyze.dto.response.*;
import com.analyze.model.*;
import com.analyze.service.PriceApiService;
import com.analyze.service.ProductKeywordTaskService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.print.attribute.standard.Destination;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Log4j
public class ProductKeywordTaskServiceImpl implements ProductKeywordTaskService {

    private final TransactionTemplate transactionTemplate;

    private final ProductKeywordTaskDOMapper productKeywordTaskDOMapper;

    private final PriceApiRequestDOMapper priceApiRequestDOMapper;

    private final ProductReviewDOMapper productReviewDOMapper;

    private ModelMapper modelMapper;

    private PriceApiService priceApiService;

    // Inbuilt format
    final static DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME;
    final static int priceApiValueCount = 999;

    @Autowired
    public ProductKeywordTaskServiceImpl(PlatformTransactionManager transactionManager, ProductKeywordTaskDOMapper productKeywordTaskDOMapper, PriceApiRequestDOMapper priceApiRequestDOMapper, ProductReviewDOMapper productReviewDOMapper, ModelMapper modelMapper, PriceApiService priceApiService) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.productReviewDOMapper = productReviewDOMapper;
        this.modelMapper = modelMapper;

        this.priceApiService = priceApiService;
        // read commit
        transactionTemplate.setIsolationLevel(Isolation.READ_COMMITTED.value());

        this.productKeywordTaskDOMapper = productKeywordTaskDOMapper;
        this.priceApiRequestDOMapper = priceApiRequestDOMapper;
    }

    PropertyMap<ProductReviewDO, PriceApiReview> customMap = new PropertyMap<ProductReviewDO, PriceApiReview>() {
        @Override
        protected void configure() {
            skip(destination.getId());
        }
    };

    /**
     * Create new task for product keyword searching
     *
     * @param request
     * @return
     */
    @Override
    public void createNewTask(NewProductKeywordTaskRequest request) {

        String allAsins = request.getAsins();
        String allKeywords = request.getKeywords();

        if (allAsins == null || StringUtils.isEmpty(allAsins)) {
            throw new IllegalArgumentException("Product keywords task cannot have empty asins");
        }

        if (allKeywords == null || StringUtils.isEmpty(allKeywords)) {
            throw new IllegalArgumentException("Product keywords task cannot have empty keywords");
        }

        List<String> asinList = Arrays.asList(allAsins.split("\n")).stream().distinct().filter(a -> StringUtils.isNotEmpty(a)).collect(Collectors.toList());
        List<String> keywordList = Arrays.asList(allKeywords.split("\n")).stream().distinct().filter(a -> StringUtils.isNotEmpty(a)).collect(Collectors.toList());

        allAsins = asinList.stream().collect(Collectors.joining("\n"));
        allKeywords = keywordList.stream().collect(Collectors.joining("\n"));

        ProductKeywordTaskDO newTask = new ProductKeywordTaskDO();

        LocalDateTime now = LocalDateTime.now();

        String taskId = IdUtil.fastUUID();
        String taskName = request.getTaskName();

        log.info("Create new product keyword task");
        log.info(String.format("Task name: $s", taskName));

        newTask.setTaskId(taskId);
        newTask.setTaskName(request.getTaskName());
        newTask.setTaskStatus(TaskSts.ORIGINAL_INS);
        newTask.setAsins(allAsins);
        newTask.setKeywords(allKeywords);
        newTask.setCreatedBy(request.getUserId());
        newTask.setUpdatedBy(request.getUserId());
        newTask.setCreatedAt(now);
        newTask.setUpdatedAt(now);

        List<PriceApiRequestDO> requestDOList = new LinkedList<>();

        int asinCount = asinList.size();

        for (int i = 0; i <= asinList.size(); i += priceApiValueCount) {
            List<String> selectAsinList = asinList.subList(i, Math.min(asinCount - 1, i + priceApiValueCount));

            PriceApiRequestDO newRequest = new PriceApiRequestDO();
            newRequest.setTaskId(taskId);
            newRequest.setStatus(PriceApiJobStatus.CREATE);
            newRequest.setTopic(PriceApiTopic.REVIEWS);
            newRequest.setCountry("us");
            newRequest.setSource("amazon");
            newRequest.setKey("id");
            newRequest.setMaxPages(3);
            newRequest.setValues(String.join("\n", selectAsinList));

            newRequest.setCreatedBy(request.getUserId());
            newRequest.setUpdatedBy(request.getUserId());

            requestDOList.add(newRequest);
        }

        if (requestDOList.size() == 0) {
            throw new IllegalArgumentException("No input asin to create request");
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                productKeywordTaskDOMapper.insert(newTask);
                priceApiRequestDOMapper.batchInsert(requestDOList);
            }
        });

    }

    @Override
    public List<ProductKeywordTask> getTasksByCreatedUser(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new IllegalArgumentException("Search task by created user, user id can not be empty");
        }

        return productKeywordTaskDOMapper
                .findAllByCreatedBy(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Process task start getting data
     */
    @Override
    public void processTask() {

        //Get all non finish task
        List<ProductKeywordTaskDO> tasks = productKeywordTaskDOMapper.findAllNonFinishTask();

        List<String> taskIds = tasks.stream().map(t -> t.getTaskId()).collect(Collectors.toList());

        // Tasks' request list
        List<PriceApiRequestDO> requestDOList = priceApiRequestDOMapper.findUnNewByTaskIds(taskIds);

        //set task processing
        tasks.forEach(t -> t.setTaskStatus(TaskSts.TASK_WAITING));

        requestDOList.forEach(r -> {
            if (r.getStatus() == PriceApiJobStatus.CREATE) {
                r.setStatus(PriceApiJobStatus.PROCESSING);
            }
        });

        // update task to processing, so the other thread wont handle same task
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                productKeywordTaskDOMapper.batchUpdate(tasks);
                priceApiRequestDOMapper.batchUpdate(requestDOList);
            }
        });

        // Make request for request haven't new
//        List<PriceApiRequest> needCreateJobRequest = requestDOList
//                .stream()
//                .map(rdo -> {
//                    PriceApiRequest request = modelMapper.map(rdo, PriceApiRequest.class);
//                    return request;
//                })
//                .collect(Collectors.toList());

        requestDOList
                .forEach(r -> {
                    try {
                        PriceApiRequest requestDto = modelMapper.map(r, PriceApiRequest.class);

                        PriceApiCreateJobResponseBody responseBody = priceApiService.createAmazonReviewJob(requestDto);
                        log.debug("Price api create job response");
                        log.debug(responseBody.toString());

                        r.setStatus(responseBody.getStatus());
                        r.setJobId(responseBody.getJobId());

                    } catch (Exception e) {
                        e.printStackTrace();
                        log.debug("Price api request failed");
                        log.debug(e.getMessage());

                        r.setStatus(PriceApiJobStatus.CREATE);
                    }
                });

        tasks.forEach(t -> t.setTaskStatus(TaskSts.TASK_OPEN));

//        for (ProductKeywordTaskDO task: tasks) {
//            task.setTaskStatus(TaskSts.TASK_OPEN);
//            // their is a request is not new
//            if (requestDOList.stream().filter(r -> r.getTaskId().equals(task.getTaskId())).filter(r -> r.getStatus() != PriceApiJobStatus.NEW).findAny().isPresent()){
//                task.setTaskStatus(TaskSts.TASK_OPEN);
//            }else{
//                task.setTaskStatus(TaskSts.TASK_SUCCESS);
//            }
//        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                productKeywordTaskDOMapper.batchUpdate(tasks);
                priceApiRequestDOMapper.batchUpdate(requestDOList);
            }
        });

        processPriceApiJobResult();

    }

    /**
     * This function process the request result
     * check the request job status and result
     */
    private void processPriceApiJobResult() {

        log.info("Run task [processPriceApiJobResult]");

        List<ProductKeywordTaskDO> taskDOs = productKeywordTaskDOMapper.findAllNonFinishTask();

        List<String> taskIds = taskDOs.stream().map(t -> t.getTaskId()).collect(Collectors.toList());

        List<PriceApiRequestDO> requestDOs = priceApiRequestDOMapper.findRequestHaveNotFinished(taskIds);

        // tmp store request origin status
        Map<Long, PriceApiJobStatus> requestOriginStatus = new HashMap<>();

        // tmp store task origin status
        Map<String, String> taskOrigStatus = new HashMap<>();

        taskDOs.forEach(t -> {
            taskOrigStatus.put(t.getTaskId(), t.getTaskStatus());
            t.setTaskStatus(TaskSts.TASK_WAITING);
        });

        requestDOs.forEach(r -> {
            requestOriginStatus.put(r.getRequestId(), r.getStatus());
            r.setStatus(PriceApiJobStatus.PROCESSING);
        });

        // update status for operation
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                productKeywordTaskDOMapper.batchUpdate(taskDOs);
                priceApiRequestDOMapper.batchUpdate(requestDOs);
            }
        });

        for (PriceApiRequestDO requestDO : requestDOs) {
            try {
                if (requestDO.getTopic() == PriceApiTopic.REVIEWS) {
                    PriceApiReviewResponseBody responseBody = priceApiService.getReviewJobResult(requestDO.getJobId());

                    log.debug("Price api result request success");
                    log.debug(responseBody.toString());

                    // request
                    // api result failed
                    if (StringUtils.isNotEmpty(responseBody.getSuccess())) {
                        log.debug("Price api get result failed");
                        if (responseBody.getComment() != null) {
                            log.debug(responseBody.getComment());
                        }
                    } else {
                        // get result success
                        for (PriceApiReviewResult result : responseBody.getResults()) {
                            if (result.isSuccess()) {

                                PriceApiSource apiSource = PriceApiSource.fromString(result.getQuery().get("source"));

                                if(result.getContent() != null){

                                    PriceApiReviewResultContent resultContent = result.getContent();

                                    List<PriceApiReview> reviews = result.getContent().getReviews();

                                    List<ProductReviewDO> reviewDOS = reviews.stream()
                                            .map(r -> {
                                                ProductReviewDO reviewDO = new ProductReviewDO();

                                                reviewDO.setBrandName(resultContent.getBrandName());
                                                reviewDO.setProductId(resultContent.getProductId());
                                                reviewDO.setProductName(resultContent.getProductName());
                                                reviewDO.setProductUrl(resultContent.getUrl());

                                                reviewDO.setReviewId(r.getId());
                                                reviewDO.setComment(r.getComment());
                                                reviewDO.setHelpfulCount(r.getHelpfulCount());
                                                reviewDO.setCommentCount(r.getCommentCount());
                                                reviewDO.setReviewerId(r.getReviewerId());
                                                reviewDO.setReviewerName(r.getReviewerName());
                                                reviewDO.setReviewerUrl(r.getReviewerUrl());
                                                reviewDO.setPosition(r.getPosition());
                                                reviewDO.setPage(r.getPage());

                                                reviewDO.setTaskId(requestDO.getTaskId());
                                                reviewDO.setSource(apiSource);

                                                return reviewDO;
                                            })
                                            .collect(Collectors.toList());

                                    // In case the product doesn't have any review, so we could still save task save product info
//                                    reviewDOS.add(ProductReviewDO
//                                            .builder()
//                                            .taskId(requestDO.getTaskId())
//                                            .brandName(resultContent.getBrandName())
//                                            .productId(resultContent.getProductId())
//                                            .productName(resultContent.getProductName())
//                                            .productUrl(resultContent.getUrl())
//                                            .build());

                                    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                                        @Override
                                        protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                                            for (ProductReviewDO reviewDO: reviewDOS){
                                                try{
                                                    productReviewDOMapper.insert(reviewDO);
                                                }catch (Exception e){
                                                    log.info("[Insert review failed]");
                                                    log.info("[Review]");
                                                    log.info(reviewDO.toString());
                                                    log.info(e.getMessage());
                                                    throw e;
                                                }
                                            }
                                        }
                                    });

                                }else{
                                    log.info("Request have no result");
                                }

                            } else {
                                log.debug("Price review failed");
                                log.debug(result.toString());
                            }
                        }
                    }

                    requestDO.setStatus(responseBody.getStatus());
                }

            } catch (Exception e) {
                e.printStackTrace();
                log.debug("Get price api job results failed");
                log.debug(e.getMessage());
            }
        }

        // if still processing, change the status to origin status
        for (PriceApiRequestDO requestDO : requestDOs) {
            if(requestDO.getStatus() == PriceApiJobStatus.PROCESSING){
                requestDO.setStatus(requestOriginStatus.get(requestDO.getRequestId()));
            }
        }

        for(ProductKeywordTaskDO taskDO: taskDOs){

            List<PriceApiRequestDO> taskRequests =  priceApiRequestDOMapper.findByTaskId(taskDO.getTaskId());

            PriceApiRequestDO nonFinishedRequest = taskRequests
                    .stream()
                    .filter(r -> r.getStatus() != PriceApiJobStatus.FINISHED && r.getStatus() != PriceApiJobStatus.CANCELED)
                    .findFirst()
                    .orElse(null);

            if(nonFinishedRequest == null){
                // every request have finish
                taskDO.setTaskStatus(TaskSts.TASK_SUCCESS);
            }else{
                // their is some request haven't finished or canceled
                taskDO.setTaskStatus(taskOrigStatus.get(taskDO.getTaskId()));
            }

//            List<PriceApiRequestDO> taskRequests = requestDOs.stream().filter(r -> r.getTaskId().equals(taskDO.getTaskId())).collect(Collectors.toList());

//            if(taskRequests.stream().filter(r -> r.getTaskId().equals(taskDO.getTaskId()) && (r.getStatus() != PriceApiJobStatus.FINISHED || r.getStatus() != PriceApiJobStatus.CANCELED)).findAny().orElse(null) != null){
//                taskDO.setTaskStatus(TaskSts.TASK_OPEN);
//            }else{
//                taskDO.setTaskStatus(TaskSts.TASK_SUCCESS);
//            }
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                productKeywordTaskDOMapper.batchUpdate(taskDOs);
                priceApiRequestDOMapper.batchUpdate(requestDOs);
            }
        });

    }

    /**
     * Check if task complete, if task complete
     * than create the download file if not exist
     */
    public void processTaskExcel(){
        productKeywordTaskDOMapper.findAllSuccessTaskWithoutFile();
    }

    /**
     * Create export file for complete task
     * @param taskId
     * @return
     */
    @Override
    public boolean taskCompleted(int taskId) {
        return false;
    }

    /**
     * Get the task result
     * This method will get review of the task
     * and return the task result
     *
     * @param taskId
     * @return
     */
    @Override
    public ProductKeywordTaskResult getTaskResult(String taskId) throws Exception {

        if(StringUtils.isEmpty(taskId)){
            throw new IllegalArgumentException("Task id cannot be empty, no task result return");
        }

        ProductKeywordTaskDO taskDO = productKeywordTaskDOMapper.findByTaskId(taskId);

        if(!taskDO.getTaskStatus().equals(TaskSts.TASK_SUCCESS)){
            throw new Exception(String.format("Task %s haven't finished"));
        }

        List<ProductReviewDO> reviewDOs = productReviewDOMapper.findAllByTaskId(taskId);

        String[] keywordArr = taskDO.getKeywords().split("\n");

        Map<String, List<ProductReviewDO>> groupByItem = reviewDOs.stream().collect(Collectors.groupingBy(ProductReviewDO::getProductId));

        List<ProductKeywordResult> productKeywordResults = new ArrayList<>();

        for (Map.Entry<String, List<ProductReviewDO>> entry: groupByItem.entrySet()) {
            String asin = entry.getKey();
            List<ProductReviewDO> asinReviews = entry.getValue();

            ProductKeywordResult keywordResult = null;
            Map<String, Integer> keywordCount = new HashMap<>();

            for (ProductReviewDO r: asinReviews){
                if(keywordResult == null){
                    keywordResult = new ProductKeywordResult();
                    keywordResult.setAsin(asin);
                }

                for (String k: keywordArr){
                    String key = k.trim();
                    if(StringUtils.isNotEmpty(key)){
                        Pattern p = Pattern.compile(key);
                        Matcher m = p.matcher(r.getComment());
                        int i =0;
                        while (m.find()) {
                            i++;
                        }
                        keywordCount.put(key, keywordCount.getOrDefault(key, 0) + i);
                    }
                }
            }

            keywordResult.setKeywordCount(keywordCount);

            productKeywordResults.add(keywordResult);
        }

        ProductKeywordTaskResult taskResult = new ProductKeywordTaskResult();

        taskResult.setTaskId(taskDO.getTaskId());
        taskResult.setTaskName(taskDO.getTaskName());
        taskResult.setAsins(taskDO.getAsins());
        taskResult.setKeywords(taskDO.getKeywords());
        taskResult.setCreatedAt(taskDO.getCreatedAt());
        taskResult.setProductKeywordResults(productKeywordResults);

        return taskResult;
    }

    @Override
    public String getTaskResult(int taskId) {
        return null;
    }

    @Override
    public TaskSts getTaskStatus(int taskId) {
        return null;
    }

    public ProductKeywordTask convertToDTO(ProductKeywordTaskDO productKeywordTaskDO) {
        return modelMapper.map(productKeywordTaskDO, ProductKeywordTask.class);
    }

    public ProductKeywordTaskDO convertToEntity(ProductKeywordTask productKeywordTask) {
        return modelMapper.map(productKeywordTask, ProductKeywordTaskDO.class);
    }
}
