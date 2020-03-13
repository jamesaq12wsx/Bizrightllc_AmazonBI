package com.analyze.service;

import java.util.List;
import java.util.Map;

/**
 * 报表服务
 */
public interface ReportService {
    /**
     * 生成报表数据
     * @return
     */
    public List<Object>  generateReportData(Map<String, Object> paramMap);

    /**
     * 生成报表文件
     * @return
     */
    public boolean  generateReportFile(Map<String, Object> paramMap);
}
