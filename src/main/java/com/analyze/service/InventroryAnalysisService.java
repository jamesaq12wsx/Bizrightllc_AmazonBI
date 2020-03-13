package com.analyze.service;

import java.util.Map;

/**
 * 库存分析服务
 */
public interface InventroryAnalysisService {
    /**
     * 生成EndPos页面分析数据
     * @param paramMap
     * @return
     */
    public Map<String,Object> generateAnalysisData(Map<String, Object> paramMap);

}
