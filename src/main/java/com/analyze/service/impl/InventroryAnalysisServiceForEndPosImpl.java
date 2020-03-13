package com.analyze.service.impl;

import com.analyze.dao.SkuCommonInfoMapper;
import com.analyze.service.InventroryAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * endpos库存分析实现类
 */
@Service("InventroryAnalysisServiceForEndPosImpl")
public class InventroryAnalysisServiceForEndPosImpl implements InventroryAnalysisService {
    /**
     * 日志打印组件
     */
    private final Logger log = LoggerFactory.getLogger(InventroryAnalysisServiceForEndPosImpl.class);


    @Autowired
    private SkuCommonInfoMapper skuCommonInfoMapper;

    @Override
    public Map<String,Object> generateAnalysisData(Map<String, Object> paramMap) {

        Map<String,Object> returnMap=new HashMap<>();

        if (log.isInfoEnabled()) {
            log.info("==================> 1.step26.参数打印");
            log.info("==================> sku:"+paramMap.get("sku"));
        }


        if (log.isInfoEnabled()) {
            log.info("==================> 2.step37.调用endposData存储过程");
        }
        List<Map<String, Object>> dataList = skuCommonInfoMapper.Amazon_InventoryAnalysis_Forecast_EndPosData(paramMap);
        if (log.isInfoEnabled()) {
            log.info("==================> 2.step41.调用endposSetData存储过程");
        }
        List<Map<String, Object>> setDataList = skuCommonInfoMapper.Amazon_InventoryAnalysis_Forecast_EndPosSetData(paramMap);


        if (log.isInfoEnabled()) {
            log.info("==================> 3.step47.收集时间段桶");
        }
        List<String> dates = new ArrayList<String>();
        for (Map<String, Object> map : dataList) {
            String year_month = map.get("year").toString() + "W" + String.format("%02d",Integer.valueOf(map.get("WEEK_DAY").toString()));
            if (!dates.contains(year_month)) {
                dates.add(year_month);
            }
        }
        for (Map<String, Object> map : setDataList) {
            String year_month = map.get("year").toString() + "W" + String.format("%02d",Integer.valueOf(map.get("WEEK_DAY").toString()));
            if (!dates.contains(year_month)) {
                dates.add(year_month);
            }
        }
        // 时间桶排序
        Collections.sort(dates);



        if (log.isInfoEnabled()) {
            log.info("==================> 4.step51.收集Sku列表桶");
        }
        List<String> setSkus = new ArrayList<String>();
        // 收集该SKU所在的Combo SKU桶
        for (Map<String, Object> map : setDataList) {
            String setSku = map.get("sku").toString();
            if (!setSkus.contains(setSku)) {
                setSkus.add(setSku);
            }
        }


        if (log.isInfoEnabled()) {
            log.info("==================> 5.step55.根据时间段桶,Sku列表桶收集数据信息");
        }

        List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();

        Map<String, Object> mymap = null;
        for (String date : dates) {
            mymap = getTargetData(date, setSkus, dataList , setDataList);
            resultList.add(mymap);
        }

        if (log.isInfoEnabled()) {
            log.info("==================> 6.step59.结果返回处理");
        }

        returnMap.put("data",resultList); // 数据集合
        returnMap.put("skus",setSkus); // 集合列表表头

        return returnMap;
    }


    /**
     * 重组EndPos数据
     * @param date
     * @param setSkus
     * @param dataList
     * @param setDataList
     * @return
     */
    private Map<String, Object> getTargetData(String date,List<String> setSkus,List<Map<String, Object>> dataList,List<Map<String, Object>> setDataList){
        if (log.isInfoEnabled()) {
            log.info("==================> 1.step109.详细结果处理");
        }
        Map<String, Object> mymap  = new HashMap<String,Object>();//这个是要返回的map

        // 1.该SKU单列数据处理
        int SAmz_Sales=0;int SAmz_Set_Tot_Sales = 0;int So_Sales=0;
        for (Map<String, Object> map : dataList) {
            String year_month = map.get("year").toString() + "W" + String.format("%02d",Integer.valueOf(map.get("WEEK_DAY").toString()));
            if (date.equals(year_month)) { // 当前时间段等于
                SAmz_Sales=map.get("SAmz_Sales")==null?0:Integer.parseInt(map.get("SAmz_Sales").toString());
                SAmz_Set_Tot_Sales=map.get("SAmz_Set_Tot_Sales")==null?0:Integer.parseInt(map.get("SAmz_Set_Tot_Sales").toString());
                So_Sales=map.get("So_Sales")==null?0:Integer.parseInt(map.get("So_Sales").toString());
                 break;
            }
        }
        mymap.put("year_week",date);
        mymap.put("So_Sales",So_Sales);
        mymap.put("SAmz_Sales",SAmz_Sales);
        mymap.put("SAmz_Set_Tot_Sales",SAmz_Set_Tot_Sales);


        // 2.该SKU对应Combo集合数据处理
        List<Integer> data = new ArrayList<Integer>();
        for (String setSku:setSkus) {
            int SAmz_Set_Sales=0;
            for (Map<String, Object> map : setDataList) {
                String year_month = map.get("year").toString() + "W" + String.format("%02d",Integer.valueOf(map.get("WEEK_DAY").toString()));
                String nowSetSku =  map.get("sku").toString();
                if (date.equals(year_month)&&setSku.equals(nowSetSku)) {
                    SAmz_Set_Sales=Integer.parseInt(map.get("SAmz_Set_Sales").toString());
                    break;
                }
            }
            data.add(SAmz_Set_Sales);
        }
        mymap.put("arr", data);

        return mymap;
    }


}