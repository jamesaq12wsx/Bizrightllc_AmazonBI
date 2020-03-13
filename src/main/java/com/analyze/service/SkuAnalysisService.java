package com.analyze.service;

import java.util.Map;

/**
 * SKU分析服务
 */
public interface SkuAnalysisService {
    /**
     * 获取最佳卖家类目信息数据
     * @param paramMap
     * @return
     */
    public Map<String,Object> Amazon_MonitoringOverview_BestSellerCategory(Map<String, Object> paramMap);

    /**
     * 获取最佳卖家Top数据
     * @param paramMap
     * @return
     */
    public Map<String,Object> Amazon_MonitoringOverview_BestSellerTop(Map<String, Object> paramMap);

    /**
     * 获取最佳卖家Top详细数据
     * @param paramMap
     * @return
     */
    public Map<String,Object> Amazon_MonitoringOverview_BestSellerTopDetail(Map<String, Object> paramMap);


    /**
     * 更新最佳卖家目录信息
     * @param paramMap
     * @return
     */
    public int Amazon_MonitoringOverview_UpdBestSellerCategory(Map<String, Object> paramMap);
}
