package com.analyze.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.analyze.cons.RespErrorEnum;
import com.analyze.cons.TaskSts;
import com.analyze.dao.PromotionTaskDOMapper;
import com.analyze.dao.SkuInfoMapper;
import com.analyze.dao.SkuScrapyTaskDOMapper;
import com.analyze.model.PromotionTaskDO;
import com.analyze.model.SkuScrapyTaskDO;
import com.analyze.service.HawProductService;
import com.analyze.service.ReportService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 报表Controller类
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(ReportController.class);
    @Autowired
    @Qualifier("ReportServiceForWeekAndMonthOutboundImpl")
    private ReportService reportServiceForWeekAndMonthOutbound;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuScrapyTaskDOMapper skuScrapyTaskDOMapper;

    @Autowired
    private HawProductService hawProductServiceImpl;

    @Autowired
    private PromotionTaskDOMapper promotionTaskDOMapper;

    private final String uploadFilePath = "D:\\BIUploadFile\\";


    @RequestMapping(value = "/Amazon_Report_WeeklyAndMonthlyOutbound", method = RequestMethod.POST)
    @ResponseBody
    public Object Amazon_Report_WeeklyAndMonthlyOutbound(HttpServletRequest request, HttpServletResponse response, String counttype,
                                                         String counttime, String countnum, String ourCode) {
        if (log.isInfoEnabled()) {
            log.info("==============> 调用周月销量报表生成服务");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, Object> paramMap = new HashedMap(getRequestParams(request));
            paramMap.put("counttype", counttype);
            paramMap.put("counttime", counttime);
            paramMap.put("countnum", countnum);
            paramMap.put("ourCode", ourCode);
            List<Object> list = reportServiceForWeekAndMonthOutbound.generateReportData(paramMap);

            resultMap.put("data", list);
            resultMap.put(STATUS, SUCCESS);
        } catch (Exception e) {
            resultMap.put(STATUS, FAIL);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;

    }

    @RequestMapping(value = "/Amazon_Report_ProductSkuList", method = RequestMethod.POST)
    @ResponseBody
    public Object Amazon_Report_ProductSkuList(HttpServletRequest request, HttpServletResponse response) {
        if (log.isInfoEnabled()) {
            log.info("==============> 查询用户可选择SKU列表");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, Object> paramMap = getRequestParams(request);
            List<Object> list = skuInfoMapper.Amazon_Report_ProductSkuList(paramMap);
            return list;
        } catch (Exception e) {
            resultMap.put(STATUS, FAIL);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;

    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFile(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) {
        if (log.isInfoEnabled()) {
            log.info("==============> uploadFile");
        }
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> record = new HashMap<>();
        try {
            Map<String, Object> paramMap = getRequestParams(request);
            FileUtils.writeByteArrayToFile(new File(uploadFilePath + file.getOriginalFilename()), file.getBytes());
            record.put("taskId", paramMap.get("taskId"));
            record.put("taskSts", TaskSts.UPLOAD_FILE);
            record.put("uploadFilePath", uploadFilePath);
            record.put("uploadFileName", file.getOriginalFilename());
            skuScrapyTaskDOMapper.updateByTaskId(record);
            resultMap.put(STATUS, SUCCESS);
        } catch (IOException e) {
            resultMap.put(STATUS, FAIL);
            resultMap.put(MSG, e.getMessage());
        }
        log.debug("ResultMap :  [{}]", resultMap.toString());
        return resultMap;
    }


    /**
     * 通过流把文件传到前台下载
     *
     * @param request
     * @param response
     * @param taskId
     */
    @RequestMapping(value = "/toDownload")
    @ResponseBody
    public void toDownload(HttpServletRequest request, HttpServletResponse response, @RequestParam("taskId") String taskId) throws IOException {
        log.info("下载文件");

        ServletOutputStream out = null;
        FileInputStream ips = null;

        Map<String,Object> params=new HashMap<>();
        params.put("taskId",taskId);
        SkuScrapyTaskDO skuScrapyTaskDO=skuScrapyTaskDOMapper.selectByTaskId(params);

        try {
            String url = StrUtil.concat(true,skuScrapyTaskDO.getDownloadFilePath(),skuScrapyTaskDO.getDownloadFileName());  //文件路径
            //获取文件存放的路径
            File file = new File(url);
            String fileName = file.getName();
            if (!file.exists()) {
                //如果文件不存在就跳出
                return;
            }
            ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");
            //为文件重新设置名字，采用数据库内存储的文件名称
            response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                ips.close();
            } catch (IOException e) {
                System.out.println("关闭流出现异常");
                e.printStackTrace();
            }
        }
        return;
    }

    /**
     * 从输入流中获取数据
     *
     * @param fis 输入流
     * @return
     * @throws Exception
     */
    private byte[] readInputStream(InputStream fis) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = fis.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        fis.close();
        return outStream.toByteArray();
    }


    /**
     * 创建Haw网站抓取任务
     */
    @RequestMapping(value = "/generateHawTask", method = RequestMethod.POST)
    @ResponseBody
    public Object generateHawTask(HttpServletRequest request, HttpServletResponse response) {
        if (log.isInfoEnabled()) {
            log.info("==============> generateHawTask");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, Object> paramMap = getRequestParams(request);
            int result = skuScrapyTaskDOMapper.insertSelective(SkuScrapyTaskDO.builder()
                    .taskId(IdUtil.fastSimpleUUID())
                    .taskName(ObjectUtil.toString(paramMap.get("taskName")))
                    .taskSts(TaskSts.ORIGINAL_INS)
                    .insertTime(DateUtil.date()).build());
            if (result == 1) {
                resultMap.put(STATUS, SUCCESS);
            } else {
                resultMap.put(STATUS, FAIL);
                resultMap.put(MSG, RespErrorEnum.GENERATE_TASK_ERROR.getSubStatusMsg());
            }
        } catch (Exception e) {
            resultMap.put(STATUS, FAIL);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;

    }

    /**
     * 启动抓取任务
     */
    @RequestMapping(value = "/openHawTask", method = RequestMethod.POST)
    @ResponseBody
    public Object openHawTask(HttpServletRequest request, HttpServletResponse response) {
        if (log.isInfoEnabled()) {
            log.info("==============> openHawTask");
        }
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> record = new HashMap<>();
        try {
            Map<String, Object> paramMap = getRequestParams(request);
            record.put("taskId", paramMap.get("taskId"));
            record.put("taskSts", TaskSts.TASK_OPEN);
            // 状态更改
            int result = skuScrapyTaskDOMapper.updateByTaskId(record);
            if (result == 1) {
                resultMap.put(STATUS, SUCCESS);
            } else {
                resultMap.put(STATUS, FAIL);
                resultMap.put(MSG, RespErrorEnum.GENERATE_TASK_ERROR.getSubStatusMsg());
                return resultMap;
            }
//            // 文件入库  （文件信息入库迁移至定时任务，改善用户体验）
//            int result1 = hawProductServiceImpl.taskFileVendorSkuIntoDB(record);
//            if (result1 == 1) {
//                resultMap.put(STATUS, SUCCESS);
//            } else {
//                resultMap.put(STATUS, FAIL);
//                resultMap.put(MSG, RespErrorEnum.GENERATE_TASK_ERROR.getSubStatusMsg());
//                return resultMap;
//            }

        } catch (Exception e) {
            resultMap.put(STATUS, FAIL);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }


    /**
     * 查询Haw抓取任务列表
     */
    @RequestMapping(value = "/QryHawTaskList", method = RequestMethod.POST)
    @ResponseBody
    public Object QryHawTaskList(HttpServletRequest request, HttpServletResponse response) {
        if (log.isInfoEnabled()) {
            log.info("==============> QryHawTaskList");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, Object> paramMap = getRequestParams(request);
            List<SkuScrapyTaskDO> resultList = skuScrapyTaskDOMapper.selectTaskList(paramMap);
            resultMap.put("data", resultList);
            resultMap.put(STATUS, SUCCESS);
        } catch (Exception e) {
            resultMap.put(STATUS, FAIL);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }

    /**
     * 更改任务信息
     */
    @RequestMapping(value = "/updateHawTask", method = RequestMethod.POST)
    @ResponseBody
    public Object updateHawTask(HttpServletRequest request, HttpServletResponse response) {
        if (log.isInfoEnabled()) {
            log.info("==============> updateHawTask");
        }
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> record = new HashMap<>();
        try {
            Map<String, Object> paramMap = getRequestParams(request);
            record.put("taskId", paramMap.get("taskId"));
            record.put("taskName", paramMap.get("taskName"));
            record.put("taskSts", paramMap.get("taskSts"));
            int result = skuScrapyTaskDOMapper.updateByTaskId(record);
            if (result == 1) {
                resultMap.put(STATUS, SUCCESS);
            } else {
                resultMap.put(STATUS, FAIL);
                resultMap.put(MSG, RespErrorEnum.UPDATE_TASK_ERROR.getSubStatusMsg());
            }
        } catch (Exception e) {
            resultMap.put(STATUS, FAIL);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }


    /**
     * 获取Promotion文件列表
     */
    @RequestMapping(value = "/QryPromotionList", method = RequestMethod.POST)
    @ResponseBody
    public Object QryPromotionList(HttpServletRequest request, HttpServletResponse response) {
        if (log.isInfoEnabled()) {
            log.info("==============> QryPromotionList");
        }
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Map<String, Object> paramMap = getRequestParams(request);
            List<PromotionTaskDO> resultList = promotionTaskDOMapper.selectPromotionList(paramMap);
            resultMap.put("data", resultList);
            resultMap.put(STATUS, SUCCESS);
        } catch (Exception e) {
            resultMap.put(STATUS, FAIL);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }


    /**
     * 下载Promotion文件
     * 通过流把文件传到前台下载
     * @param request
     * @param response
     * @param taskId
     */
    @RequestMapping(value = "/toPromotionDownload")
    @ResponseBody
    public Object toPromotionDownload(HttpServletRequest request, HttpServletResponse response, @RequestParam("taskId") String taskId) throws IOException {
        log.info("下载文件");

        Map<String, Object> resultMap = new HashMap<>();

        ServletOutputStream out = null;
        FileInputStream ips = null;

        Map<String,Object> params=new HashMap<>();
        params.put("taskId",taskId);
        PromotionTaskDO promotionTaskDO=promotionTaskDOMapper.selectByTaskId(params);

        try {
            String url = StrUtil.concat(true,promotionTaskDO.getDownloadFilePath(),promotionTaskDO.getDownloadFileName());  //文件路径
            //获取文件存放的路径
            File file = new File(url);
            String fileName = file.getName();
            if (!file.exists()) {
                //如果文件不存在就跳出
                resultMap.put(STATUS, FAIL);
                resultMap.put(MSG, "文件无法下载，请稍后");
                return resultMap;
            }
            ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");
            //为文件重新设置名字，采用数据库内存储的文件名称
            response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            resultMap.put(STATUS, SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(STATUS, FAIL);
            resultMap.put(MSG, e.getMessage());
        } finally {
            try {
                out.close();
                ips.close();
            } catch (IOException e) {
               log.info("关闭流出现异常");
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    /**
     * 生成Promotion文件生成任务
     */
    @RequestMapping(value = "/generatePromotionTask", method = RequestMethod.POST)
    @ResponseBody
    public Object generatePromotionTask(HttpServletRequest request, HttpServletResponse response) {
        if (log.isInfoEnabled()) {
            log.info("==============> generatePromotionTask");
        }
        Map<String, Object> resultMap = new HashMap<>();
        PromotionTaskDO promotionTaskDO=new PromotionTaskDO();
        try {
            Map<String, Object> paramMap = getRequestParams(request);
            promotionTaskDO.setTaskId(IdUtil.fastSimpleUUID());
            promotionTaskDO.setDateFrom(String.valueOf(paramMap.get("dateFrom")));
            promotionTaskDO.setDateTo(String.valueOf(paramMap.get("dateTo")));
            promotionTaskDO.setInsertTime(DateUtil.date());
            promotionTaskDO.setTaskName("File Download "+String.valueOf(paramMap.get("dateFrom"))+" To " + String.valueOf(paramMap.get("dateTo")));
            promotionTaskDO.setTaskSts(TaskSts.ORIGINAL_INS);
            log.info("promotionTaskDO=>[{}]",promotionTaskDO);
            // 状态更改
            int result = promotionTaskDOMapper.insertSelective(promotionTaskDO);
            if (result == 1) {
                resultMap.put(STATUS, SUCCESS);
            } else {
                resultMap.put(STATUS, FAIL);
                resultMap.put(MSG, RespErrorEnum.GENERATE_TASK_ERROR.getSubStatusMsg());
                return resultMap;
            }
        } catch (Exception e) {
            resultMap.put(STATUS, FAIL);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }



}
