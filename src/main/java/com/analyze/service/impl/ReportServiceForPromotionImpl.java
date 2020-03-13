package com.analyze.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.analyze.config.CommonConfig;
import com.analyze.cons.RespResult;
import com.analyze.cons.TaskSts;
import com.analyze.dao.PromotionTaskDOMapper;
import com.analyze.model.PromotionTaskDO;
import com.analyze.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 周月报表实现类
 */
@Service("ReportServiceForPromotionImpl")
public class ReportServiceForPromotionImpl implements ReportService {
    /**
     * 日志打印组件
     */
    private final Logger log = LoggerFactory.getLogger(ReportServiceForPromotionImpl.class);

    @Autowired
    private PromotionTaskDOMapper promotionTaskDOMapper;

    /**
     * 生成报表数据
     * @param paramMap
     * @return
     */
    @Override
    public List<Object> generateReportData(Map<String, Object> paramMap) {
        return Collections.emptyList();
    }

    @Override
    public boolean generateReportFile(Map<String, Object> paramMap) {

        log.info("读取任务id数据 =>[{}]",paramMap.get("taskId"));

        if (ObjectUtil.isEmpty(paramMap.get("taskId"))) {
            return false;
        }

        // 获取任务信息
        PromotionTaskDO promotionTaskDO=promotionTaskDOMapper.selectByTaskId(paramMap);
        if (ObjectUtil.isEmpty(promotionTaskDO) ||  !promotionTaskDO.getTaskSts().equals(TaskSts.ORIGINAL_INS)) {
            return false;
        }

        //  更新任务信息
        Map<String,Object> updateMap=new HashMap<>();
        updateMap.put("taskId",paramMap.get("taskId"));
        updateMap.put("taskSts",TaskSts.TASK_WAITING);
        int result=promotionTaskDOMapper.updatePromotionByTaskId(updateMap);
        if (result!= RespResult.SUCC_OOM) {
            return false;
        }

        // 初始化Excel文件
        final String filePath = CommonConfig.PROMOTION_UPLOAD_FILE_PATH;
        final String fileName = "Promotion-"+ IdUtil.fastSimpleUUID()+".xlsx";
        BigExcelWriter writer = ExcelUtil.getBigWriter(filePath+fileName);

        // 文件录入
        String dateFromStr=promotionTaskDO.getDateFrom();
        String dateToStr=promotionTaskDO.getDateTo();
        Date dateFrom= DateUtil.parse(dateFromStr,"yyyyMMdd");
        Date dateTo=  DateUtil.parse(dateToStr,"yyyyMMdd");
        while (DateUtil.compare(dateFrom, dateTo)<=0) {

            // 初始日期小于等于结束日期,循环获取数据，添加Excel生成
            addExcel(writer,dateFrom);

            // 日期往后一天
            dateFrom=DateUtil.offsetDay(dateFrom, 1);

        }

        writer.close();

        //  更新任务信息
        updateMap.clear();
        updateMap.put("taskId",paramMap.get("taskId"));
        updateMap.put("taskSts",TaskSts.TASK_SUCCESS);
        updateMap.put("downloadFilePath",filePath);
        updateMap.put("downloadFileName",fileName);
        result=promotionTaskDOMapper.updatePromotionByTaskId(updateMap);
        if (result!= RespResult.SUCC_OOM) {
            updateMap.clear();
            updateMap.put("taskId",paramMap.get("taskId"));
            updateMap.put("taskSts",TaskSts.TASK_FAILD);
            promotionTaskDOMapper.updatePromotionByTaskId(updateMap);
            return false;
        }

        return true;
    }

    private boolean addExcel(BigExcelWriter writer ,Date curDate) {
        log.info("获取 [{}] 数据",curDate);

        String curDateStr=DateUtil.format(curDate,"yyyyMMdd");

        log.info("获取数据");
        Map<String ,Object> param =new HashMap<>();
        param.put("crawId",curDateStr+"%");
        List<Map<String,Object>> resultMap = promotionTaskDOMapper.qryPromotionListByDate(param);
        if (ObjectUtil.isEmpty(resultMap)) {
            return false;
        }

        log.info("进行Excel数据写入");
        int rIndex=0;
        for (;rIndex<resultMap.size();rIndex++) {
            writer.writeRow(resultMap.get(rIndex),true);
        }

        return true ;
    }

    public static void main(String [] args) {
        DateUtil.parse("20200304","yyyyMMdd");

        System.out.println(DateUtil.parse("20200304","yyyyMMdd"));

        return;
    }

}
