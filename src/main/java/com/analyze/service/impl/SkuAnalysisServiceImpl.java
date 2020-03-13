package com.analyze.service.impl;

import com.analyze.dao.SkuInfoMapper;
import com.analyze.model.TreeMenuModel;
import com.analyze.service.SkuAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Sku分析实现类
 */
@Service("SkuAnalysisServiceImpl")
public class SkuAnalysisServiceImpl implements SkuAnalysisService {
    /**
     * 日志打印组件
     */
    private final Logger log = LoggerFactory.getLogger(SkuAnalysisServiceImpl.class);


    @Autowired
    private SkuInfoMapper skuInfoMapper;


    /**
     * 获取最佳卖家类目信息
     * @param paramMap
     * @return
     */
    @Override
    public Map<String, Object> Amazon_MonitoringOverview_BestSellerCategory(Map<String, Object> paramMap) {

        Map<String, Object> returnMap= new HashMap<>();
        List<TreeMenuModel> treeMenuModelList=new ArrayList<>();

        if (log.isInfoEnabled()) {
            log.info("==================> 1.step32.查询最佳卖家目录类别");
        }
        List<Map<String, Object>> dataList = skuInfoMapper.Amazon_MonitoringOverview_BestSellerCategory(paramMap);

        if (log.isInfoEnabled()) {
            log.info("==================> 2.step37.结果处理");
        }
        TreeMenuModel treeMenuModel=null;
        for (Map<String, Object> map : dataList) {
            treeMenuModel=new TreeMenuModel();
            treeMenuModel.setId(map.get("CategoryId")==null?"":map.get("CategoryId").toString());
            treeMenuModel.setParent(map.get("ParentId")==null?"":map.get("ParentId").toString());
            treeMenuModel.setText(map.get("CategoryName")==null?"":map.get("CategoryName").toString());
            treeMenuModelList.add(treeMenuModel);
        }

        returnMap.put("data",treeMenuModelList); // 数据集合

        return returnMap;
    }

    /**
     * 获取最佳卖家Top数据
     * @param paramMap
     * @return
     */
    @Override
    public Map<String, Object> Amazon_MonitoringOverview_BestSellerTop(Map<String, Object> paramMap) {


        Map<String, Object> returnMap= new HashMap<>();

        if (log.isInfoEnabled()) {
            log.info("==================> 1.step74.查询最佳卖家Top列表");
        }
        List<Map<String, Object>> dataList = skuInfoMapper.Amazon_MonitoringOverview_BestSellerTop(paramMap);

        if (log.isInfoEnabled()) {
            log.info("==================> 2.step78.结果处理");
        }

        returnMap.put("data",dataList); // 数据集合

        return returnMap;
    }

    /**
     * 获取最佳卖家Top详细数据
     * @param paramMap
     * @return
     */
    @Override
    public Map<String, Object> Amazon_MonitoringOverview_BestSellerTopDetail(Map<String, Object> paramMap) {


        Map<String, Object> returnMap= new HashMap<>();

        if (log.isInfoEnabled()) {
            log.info("==================> 1.step98.查询最佳卖家Top详细列表");
        }
        List<Map<String, Object>> dataList = skuInfoMapper.Amazon_MonitoringOverview_BestSellerTopDetail(paramMap);

        if (log.isInfoEnabled()) {
            log.info("==================> 2.step103.结果处理");
        }

        returnMap.put("data",dataList); // 数据集合

        return returnMap;
    }

    /**
     * 更新最佳卖家目录信息
     * @param paramMap
     * @return
     */
    @Override
    public int Amazon_MonitoringOverview_UpdBestSellerCategory(Map<String, Object> paramMap) {

        if (log.isInfoEnabled()) {
            log.info("==================> 1.step116.更新最佳卖家目录信息");
        }
        int result= skuInfoMapper.Amazon_MonitoringOverview_UpdBestSellerCategory(paramMap);

        return result;
    }


}