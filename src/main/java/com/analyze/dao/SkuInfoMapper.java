package com.analyze.dao;

import com.analyze.model.SkuInfo;

import java.util.List;
import java.util.Map;

public interface SkuInfoMapper {
	int insert(SkuInfo record);

	int insertSelective(SkuInfo record);

	List<Object> Amazon_SKUAnalysis_SkuSearch(Map<String, Object> map);

	List<Object> seachAsin(Map<String, Object> requestParams);

	Long seachWhether(Map<String, Object> requestParams);

	List<Object> seachAsinMe(Map<String, Object> requestParams);

	List<Object> Amazon_MerchandiseInfo_ProductLineInfo(Map<String, Object> requestParams);

	List<Map<Object, Object>> Amazon_SKUAnalysis_SkuSearch_downLoad(Map<String, Object> paramMap);

	List<Object> Amazon_SKUAnalysis_SkuSearch_Details(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_SalesAnalysis(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_TotalSalesAnalysis(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_HotSaleList_Channel(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_HotSaleList(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_CategorySelect(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_CategorySalesAnalysisDate(Map<String, Object> map);

	List<Object> Amazon_SalesAnalysis_BrandSalesAnalysisDate(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_BrandSalesAnalysis(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_SkuInformation(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_SkuSalesTrend(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_SkuRegionSalesTrendDate(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_SkuRegionSalesTrend(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_CategorySalesAnalysis(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_HotRanking(Map<String, Object> requestParams);

	List<Object> Amazon_SalesAnalysis_HotRanking_Select(int i);

	List<Object> Amazon_UserAnalysis_HousetimeSalesAnalysis(Map<String, Object> requestParams);

	List<Object> Amazon_UserAnalysis_RegionSalesAnalysis(Map<String, Object> requestParams);

	List<Object> Amazon_UserAnalysis_CategorySalesAnalysis(Map<String, Object> requestParams);

	int addSku(Map<String, Object> paramMap);

	List<Object> getSkumanage(Map<String, Object> paramMap);

	int deleteByPrimaryKey(Long id);

	Long getCount(Map<String, Object> paramMap);

	List<Object> getSkuinfo(Map<String, Object> paramMap);

	SkuInfo selectByPrimaryKey2(Long id);

	Map<String, Object> checkHas(SkuInfo skuInfo);

	Map<String, Object> search_price(Object s);

	List<Object> getMain_asin(Map<String, Object> mp);

	Object getAsinMe_price(Object o);

	List<Object> getAsin_price(Object o);

	List<Object> getCategoryInfo(Map<String, Object> paramMap);

	List<Object> getCategoryInfo_parent(Map<String, Object> paramMap);

	int updateParentCategory(Map<String, Object> paramMap);

	int deleteparentCategory(Map<String, Object> paramMap);

	int addCategory(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_SKU_Brand(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_SKU(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_SKU_Jingpin(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_PriceChange(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_TitleChange(Map<String, Object> paramMap);

	List<Object> getSkuCompareInfoByAsin(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_RankingChange(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_NewReview(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_PictureChange(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_Category(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_PriceOverview(Map<String, Object> paramMap);

	/**
	 * 获取最佳卖家类目列表
	 * @param paramMap
	 * @return
     */
	List<Map<String, Object>> Amazon_MonitoringOverview_BestSellerCategory(Map<String, Object> paramMap);

	/**
	 * 获取最佳卖家Top数据
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> Amazon_MonitoringOverview_BestSellerTop(Map<String, Object> paramMap);

	/**
	 * 获取最佳卖家Top详细数据
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> Amazon_MonitoringOverview_BestSellerTopDetail(Map<String, Object> paramMap);

	/**
	 * 更新最佳卖家目录信息
	 * @param paramMap
	 * @return
     */
	int Amazon_MonitoringOverview_UpdBestSellerCategory(Map<String, Object> paramMap);

	List<Object> new_getLable(Map<String, Object> paramMap);

	List<Object> product_getLable(Map<String, Object> paramMap);

	int new_addlable(Map<String, Object> paramMap);

	int new_deletelabel(Map<String, Object> paramMap);

	int new_editasinlabel(Map<String, Object> paramMap);

	int new_editproductlabel(Map<String, Object> paramMap);

	int productAddLable(Map<String, Object> paramMap);

	int productdelLable(Map<String, Object> paramMap);

	List<Object> getSkuInfo_new(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_Product(Map<String, Object> paramMap);

	int updateProduct_skuInfo(Map<String, Object> paramMap);

	int insertProduct_skuInfo(Map<String, Object> paramMap);

	int addSkuCompareInfo(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_ProductJingping(Map<String, Object> paramMap);

	int delSkuCompareInfo(Map<String, Object> paramMap);

	List<Object> Amazon_MonitoringOverview_SKU_SimilarProducts(Map<String, Object> paramMap);

	List<Object> getOwnBrand(Map<String, Object> paramMap);

	int addOwnBrand(Map<String, Object> paramMap);

	int delOwnBrand(Map<String, Object> paramMap);

	/**
	 * 获取SKU对应销量信息
	 * @param paramMap
	 * @return
     */
	List<Object> Amazon_Report_WeeklyAndMonthlyOutbound(Map<String, Object> paramMap);

	/**
	 * 查询用户在报表模块可选SKU列表信息
	 * @param paramMap
	 * @return
	 */
	List<Object> Amazon_Report_ProductSkuList(Map<String, Object> paramMap);



}