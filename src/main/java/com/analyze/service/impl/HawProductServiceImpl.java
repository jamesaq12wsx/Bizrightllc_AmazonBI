package com.analyze.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.analyze.cons.RespErrorEnum;
import com.analyze.cons.RespResult;
import com.analyze.dao.SkuScrapyTaskDOMapper;
import com.analyze.dao.SkuScrapyTaskVSkuListMapper;
import com.analyze.model.SkuScrapyTaskDO;
import com.analyze.model.SkuScrapyTaskVSkuList;
import com.analyze.service.HawProductService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Haw商品服务实现类
 */
@Service("HawProductServiceImpl")
public class HawProductServiceImpl implements HawProductService {
    /**
     * 日志打印组件
     */
    private final Logger log = LoggerFactory.getLogger(HawProductServiceImpl.class);

    private final static int BATCHCOUNT=100;

    private final static String sheetName="Template-OUTDOOR_LIVING";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private SkuScrapyTaskDOMapper skuScrapyTaskDOMapper;

    @Override
    public int taskFileVendorSkuIntoDB(Map<String, Object> paramMap) {

        log.info("任务文件VendorSku入库");


        String taskId= StrUtil.toString(paramMap.get("taskId"));

        // 查询获取该任务信息
        SkuScrapyTaskDO skuScrapyTaskDO= skuScrapyTaskDOMapper.selectByTaskId(paramMap);
        log.debug("任务信息查询返回结果 SkuScrapyTaskDO: [{}]" ,skuScrapyTaskDO.toString());
        String fullFilePath =skuScrapyTaskDO.getUploadFilePath()+skuScrapyTaskDO.getUploadFileName() ;

        log.debug("获取sheet工作页 fullFilePath [{}]  taskId [{}] ",fullFilePath,taskId);
        ExcelReader reader = ExcelUtil.getReader(fullFilePath);
        Workbook workbook = reader.getWorkbook();
        Sheet sheet = workbook.getSheet(sheetName); // 获取产品页

        Map<String,Object> params = new HashMap<>();
        params.put("taskId",taskId);
        int startrow=4;
        int endrow=0;
        log.debug("sheet参数转换成数组 sheet=>[{}] startrow=>[{}] endrow=>[{}]  params=>[{}] ",sheet,startrow,endrow,params);
        List<SkuScrapyTaskVSkuList> resultList= transSheet2Do(sheet,startrow,endrow,params);

        log.debug("批量插入数据库 resultList=>[{}]",resultList);
        dealResult(resultList);

        return RespResult.SUCC_OOM;
    }

    /**
     * 处理请求结果
     *
     * @param members 1.数据校验（暂无传输安全机制）
     *                2.数据处理
     *                3.数据入库 （支持数据快速入库）
     */
    private void dealResult(List<SkuScrapyTaskVSkuList> members) {

        // 1.数据校验
        if (ObjectUtil.isEmpty(members)) {
            if (log.isInfoEnabled()){
                log.info("服务器无返回数据");
            }
            return ;
        }

        // 2.数据处理
        int result = 1;
        SqlSession batchSqlSession = null;
        try {
            batchSqlSession = this.sqlSessionTemplate
                    .getSqlSessionFactory()
                    .openSession(ExecutorType.BATCH, false);// 获取批量方式的sqlsession
            //通过新的session获取mapper
            SkuScrapyTaskVSkuListMapper mapper = batchSqlSession.getMapper(SkuScrapyTaskVSkuListMapper.class);

            int batchCount = BATCHCOUNT;// 每批commit的个数
            int batchLastIndex = batchCount;// 每批最后一个的下标

            for (int index = 0; index < members.size(); ) {
                if (batchLastIndex >= members.size()) {
                    batchLastIndex = members.size();

                    result = result + mapper.insertBatch(members.subList(index, batchLastIndex));
                    batchSqlSession.commit();
                    //清理缓存，防止溢出
                    batchSqlSession.clearCache();
                    if (log.isInfoEnabled()) {
                        log.info("index:" + index + " batchLastIndex:" + batchLastIndex);
                    }
                    break;// 数据插入完毕，退出循环
                } else {

                    result = result + mapper.insertBatch(members.subList(index, batchLastIndex));
                    batchSqlSession.commit();
                    //清理缓存，防止溢出
                    batchSqlSession.clearCache();
                    if (log.isInfoEnabled()) {
                        log.info("index:" + index + " batchLastIndex:" + batchLastIndex);
                    }
                    index = batchLastIndex;// 设置下一批下标
                    batchLastIndex = index + (batchCount - 1);
                }
                if (log.isInfoEnabled()) {
                    log.info("=============>result=[" + result + "] begin=[" + index + "] end=[" + batchLastIndex + "]");
                }
            }
            batchSqlSession.commit();
        } catch (Exception e) {
            throw new RuntimeException(RespErrorEnum.SERVICE_DATA_EXPC.getSubStatusMsg());
        } finally {
            batchSqlSession.close();
        }

        return;
    }

    /**
     * 转换列表对象
     * @param sheet
     * @return
     */
    public List<SkuScrapyTaskVSkuList> transSheet2Do( Sheet sheet,int startrow,int endrow ,Map<String,Object> params) {
        List<SkuScrapyTaskVSkuList> reulstList = new ArrayList<>();
        int rowindex=startrow;
        if (endrow==0) {
            endrow=sheet.getLastRowNum();
        }
        for (;rowindex<=endrow;++rowindex) {
            Row row= sheet.getRow(rowindex);
            reulstList.add(SkuScrapyTaskVSkuList.builder()
                    .taskId(StrUtil.toString(params.get("taskId")))
                    .vendorSku(row.getCell(1).getStringCellValue())
                    .insertTime(DateUtil.date()).build());
        }

        return reulstList;
    }

    public static void main(String[] args) {
        ExcelReader reader = ExcelUtil.getReader("/Users/zhucan/Downloads/BIUploadFile/Template.xlsm");
        Workbook workbook = reader.getWorkbook();
        Sheet sheet = workbook.getSheet("Template-OUTDOOR_LIVING"); // 获取第二个工作簿
        Row row= sheet.getRow(4);
        System.out.println(" row："+row.getCell(1).getStringCellValue());
        System.out.println(sheet.getLastRowNum());
    }

}