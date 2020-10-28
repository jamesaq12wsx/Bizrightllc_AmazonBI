package com.analyze.schedule;

import cn.hutool.core.util.ObjectUtil;
import com.analyze.cons.RespErrorEnum;
import com.analyze.cons.TaskSts;
import com.analyze.dao.PromotionTaskDOMapper;
import com.analyze.dao.SkuScrapyTaskDOMapper;
import com.analyze.model.PromotionTaskDO;
import com.analyze.service.ProductKeywordTaskService;
import com.analyze.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类用于系统定时任务的推送
 * @author Wei
 */
@Component("scheduledTask")
public class ScheduledTask {

	private final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

	@Autowired
	private SkuScrapyTaskDOMapper skuScrapyTaskDOMapper;

	@Autowired
	private PromotionTaskDOMapper promotionTaskDOMapper;

	@Autowired
	@Qualifier("ReportServiceForPromotionImpl")
	private ReportService reportServiceForPromotionImpl;

	@Autowired
	private ProductKeywordTaskService productKeywordTaskService;

	/**
	 * 定时变更Haw开启状态任务为抓取中
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void updHawTaskOpenToWait() {
		log.info("0.step25=>开始执行［updHawTaskOpenToWait］");
		Map<String,Object> params =new HashMap<>();

		params.put("taskSts", TaskSts.TASK_WAITING);
		log.debug("查询任务状态 : [{}]",params.get("taskSts"));
		int taskCount = skuScrapyTaskDOMapper.selectCountByTaskSts(params);
		if (taskCount!=0) {
			log.debug("taskCount : [{}]",taskCount);
			return;
		}

		params.put("oldTaskSts", TaskSts.TASK_OPEN);
		params.put("taskSts", TaskSts.TASK_WAITING);
		log.debug("params: [{}] ", params.toString());
		int result = skuScrapyTaskDOMapper.updateByTaskSts(params);
		if (result<0) {
			throw new RuntimeException(RespErrorEnum.SERVICE_DATA_EXPC.getSubStatusMsg());
		}

	}

	/**
	 * 定时启动报表生成任务
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void generatePromotionReport() {
		log.info("0.step25=>开始执行［generatePromotionReport］");
		Map<String,Object> params =new HashMap<>();

		params.put("taskSts", TaskSts.TASK_WAITING);
		log.debug("查询任务状态 : [{}]",params.get("taskSts"));
		int taskCount = promotionTaskDOMapper.selectCountByTaskSts(params);
		if (taskCount!=0) {
			log.debug("taskCount : [{}]",taskCount);
			return;
		}

		params.clear();
		params.put("taskSts",TaskSts.ORIGINAL_INS);
		PromotionTaskDO promotionTaskDO=promotionTaskDOMapper.selectOneByTaskSts(params);
		if (ObjectUtil.isEmpty(promotionTaskDO)) {
			return;
		}
		params.clear();
		params.put("taskId", promotionTaskDO.getTaskId());
		log.debug("params : [{}]",params);
		boolean result = reportServiceForPromotionImpl.generateReportFile(params);
		if (result!=true) {
			log.debug("result : [{}]",result);
			return;
		}
	}

	/**
	 * Find any open task and deal with the task
	 */
//	@Scheduled(cron = "0/10 * * * * ?")
	public void updateProductKeywordTask() {

		log.info("Process Task［updateProductKeywordTask］");

		productKeywordTaskService.processTask();

//		Map<String,Object> params =new HashMap<>();
//
//		params.put("taskSts", TaskSts.TASK_WAITING);
//		log.debug("查询任务状态 : [{}]",params.get("taskSts"));
//		int taskCount = skuScrapyTaskDOMapper.selectCountByTaskSts(params);
//		if (taskCount!=0) {
//			log.debug("taskCount : [{}]",taskCount);
//			return;
//		}
//
//		params.put("oldTaskSts", TaskSts.TASK_OPEN);
//		params.put("taskSts", TaskSts.TASK_WAITING);
//		log.debug("params: [{}] ", params.toString());
//		int result = skuScrapyTaskDOMapper.updateByTaskSts(params);
//		if (result<0) {
//			throw new RuntimeException(RespErrorEnum.SERVICE_DATA_EXPC.getSubStatusMsg());
//		}

	}

}
