package com.analyze.dao;

import com.analyze.model.SkuCommonInfo;

import java.util.List;
import java.util.Map;

public interface SkuCommonInfoMapper {
	List<Object> Amazon_Monitoring_Intelligence_NewComments_Trigger(Map<String, Object> requestParams);

	int insert(SkuCommonInfo record);

	int insertSelective(SkuCommonInfo record);

	List<Object> Amazon_SKUAnalysis_Operation_buyboxofferAnalysis(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_Operation_buyboxofferChange(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_Operation_offernum(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_Operation_offerlist(String asin);

	List<Object> Amazon_SKUAnalysis_Operation_offerTotal(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_Operation_offerChange(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_Operation_ComprehensiveAnalysis(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_Operation_ComprehensiveAnalysis_PriceTrend(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_pinglun(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_avaStar(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_InsertavaStar(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_ReviewAnalysis_CommentDetails(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_Commentstat(Map<String, Object> map);

	List<Map<Object, Object>> Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_Commentstat_downLoad(
			Map<String, Object> paramMap);

	List<Map<Object, Object>> Amazon_SKUAnalysis_ReviewAnalysis_CommentDetails_downLoad(Map<String, Object> paramMap);

	List<Object> Amazon_SKUAnalysis_Operation_buyboxofferAnalysis_CategoryRank(Map<String, Object> requestParams);

	List<Object> Amazon_Monitoring_Intelligence_PriceChange(Map<String, Object> requestParams);

	List<Object> Amazon_Monitoring_Intelligence_TitleChange(Map<String, Object> map);

	List<Object> Amazon_Monitoring_Intelligence_RankingChange(Map<String, Object> requestParams);

	List<Object> Amazon_Monitoring_Intelligence_NewComments(Map<String, Object> map);

	List<Map<Object, Object>> Amazon_Monitoring_Intelligence_PriceChange_downLoad(Map<String, Object> paramMap);

	List<Map<Object, Object>> Amazon_Monitoring_Intelligence_TitleChange_downLoad(Map<String, Object> paramMap);

	List<Map<Object, Object>> Amazon_Monitoring_Intelligence_RankingChange_downLoad(Map<String, Object> paramMap);

	List<Map<Object, Object>> Amazon_Monitoring_Intelligence_NewComments_downLoad(Map<String, Object> paramMap);

	List<Map<Object, Object>> Amazon_Monitoring_Intelligence_PictureChange_downLoad(Map<String, Object> paramMap);

	List<Object> Amazon_Monitoring_Intelligence_PictureChange(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_MonitoringRankings(Map<String, Object> requestParams);

	List<Map<String, Object>> Amazon_InventoryAnalysis_InventoryManagement(Map<String, Object> paramMap);

	Map<String, Object> select_salesInfo_user(Map<String, Object> paramMap);

	public void insert_salesInfo_user(Map<String, Object> paramMap);

	public void update_salesInfo_user(Map<String, Object> paramMap);

	List<Object> Amazon_Monitoring_Intelligence_CategoryRankings(Map<String, Object> requestParams);

	List<Map<Object, Object>> Amazon_Monitoring_Intelligence_CategoryRankings_downLoad(Map<String, Object> paramMap);

	List<Object> Amazon_SKUAnalysis_Operation_SalesAnalysis_PlatformSalesAnalysis(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_Operation_SalesAnalysis_Trend(Map<String, Object> requestParams);

	List<Object> Amazon_SKUAnalysis_Operation_DetailedData(Map<String, Object> requestParams);

	List<Map<String, Object>> Amazon_SKUAnalysis_Operation_DetailedData_dowload(Map<String, Object> paramMap);

	List<Map<String, Object>> Amazon_InventoryAnalysis_Forecast_Purchase(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_CategoryRankings_Trigger(Map<String, Object> map);

	/*
	 * List<Object> Amazon_MonitoringOverview_CategoryRankings(Map<String,
	 * Object> requestParams);
	 */

	List<Object> Amazon_MonitoringOverview_CategoryRankings_Return(Map<String, Object> requestParams);

	List<Object> Amazon_MonitoringOverview_CategoryRankings_new(Map<String, Object> requestParams);
	List<Map<String, Object>> selectProductListByLabel(Map<String, Object> paramMap);
	
	
	void update_Delta_LA_Info(Map<String, Object> map);
	
	void update_Delta_Vendor_Info(Map<String, Object> map);
	
	void update_sku_CNLeadTime_map(Map<String, Object> map);
	
	List<Map<String, Object>> select_sku_CNLeadTime_map(Map<String, Object> paramMap);
	
	
	List<Map<String, Object>> select_other_Delta(Map<String, Object> paramMap);
	List<Map<String, Object>> select_danpin_Delta(Map<String, Object> paramMap);
	List<Map<String, Object>> select_taozhuang_Delta(Map<String, Object> paramMap);
	

	Map<String, Object> selectSkuBaseInfo(Map<String, Object> paramMap);
	
	
	List<Map<String, Object>> Amazon_InventoryAnalysis_Forecast_History(Map<String, Object> paramMap);
	List<Map<String, Object>> Amazon_InventoryAnalysis_Forecast_SkuCombo(Map<String, Object> paramMap);

	/**
	 * 获取产品线产品预测信息数据
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> Amazon_InventoryAnalysis_Forecast_ProductLineSKU(Map<String, Object> paramMap);

	/**
	 * 获取总产品线列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> Amazon_InventoryAnalysis_Forecast_ProductLineList(Map<String, Object> paramMap);

	/**
	 * 获取endpos页面数据
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> Amazon_InventoryAnalysis_Forecast_EndPosData(Map<String, Object> paramMap);

	/**
	 * 获取endpos页面集合数据
	 * @param paramMap
	 * @return
     */
	List<Map<String, Object>> Amazon_InventoryAnalysis_Forecast_EndPosSetData(Map<String, Object> paramMap);


	void insert_LA_PO_Info(Map<String, Object> map);
	
	void insert_other_Delta(Map<String, Object> map);
	
	List<Map<String, Object>> getToggleData(Map<String, Object> paramMap);
	
	List<Map<String, Object>> getSkuEndPos(Map<String, Object> paramMap);
	
	List<Map<String, Object>> getSkuOverView(Map<String, Object> paramMap);
	
	List<Map<String, Object>> gettaozhuangAlert(Map<String, Object> paramMap);

}