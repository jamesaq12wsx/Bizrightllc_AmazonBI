package com.analyze.service.impl;

import com.analyze.dao.PromotionTaskDOMapper;
import com.analyze.dao.SkuInfoMapper;
import com.analyze.model.report.WeekAndMonthOutboundQryRet;
import com.analyze.service.ReportService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 周月报表实现类
 */
@Service("ReportServiceForWeekAndMonthOutboundImpl")
public class ReportServiceForWeekAndMonthOutboundImpl implements ReportService {
    /**
     * 日志打印组件
     */
    private final Logger log = LoggerFactory.getLogger(ReportServiceForWeekAndMonthOutboundImpl.class);

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private PromotionTaskDOMapper promotionTaskDOMapper;


    private final String uploadFilePath = "D:\\BIUploadFile\\";

    /**
     * 生成周月销量报表数据
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<Object> generateReportData(Map<String, Object> paramMap) {
        List<Object> resultlist = null;
        /**
         * 1.参数打印
         */
        if (log.isInfoEnabled()) {
            log.info("================>周月报表内部生成调用");
            log.info(paramMap.toString());
        }
        // 统计方式 counttype :amount,sales
        // counttime :weekly monthly
        // countnum :统计时间段数量
        String counttype = (String) paramMap.get("counttype");
        String counttime = (String) paramMap.get("counttime");
        String countnum = (String) paramMap.get("countnum");
        Calendar c = Calendar.getInstance();
        String nowyear = String.valueOf(c.get(Calendar.YEAR)) ;
        c.add(Calendar.YEAR, -1);
        String lastyear =String.valueOf( c.get(Calendar.YEAR));
        c.add(Calendar.YEAR, -1);
        String beforelastyear =String.valueOf( c.get(Calendar.YEAR));

        /**
         * 2.调用存储过程
         */
        if (log.isInfoEnabled()) {
            log.info("================>2.调用存储过程");
            log.info("================>ourCode:"+paramMap.get("ourCode"));
        }
        // 调用存储过程返回要计算列表
        List<Object> list = skuInfoMapper.Amazon_Report_WeeklyAndMonthlyOutbound(paramMap);
        if (log.isInfoEnabled()) {
            log.info("================>2.1.list:"+list.toString());
        }


        if (log.isInfoEnabled()) {
            log.info("================>3.存储过程返回结果计算");
        }
        /**
         * 3.存储过程返回结果计算
         */
        // 第一遍循环,收集时间段数据桶,得到所有时间段头,
        // 及按照渠道,timetype划分组,统计List<Map> resultMap, timetype:  NOWXXXX  LASXXXX
        int listsize = list.size();
        int indexlist = 0;
        WeekAndMonthOutboundQryRet qryRetObj=null;
        String calyearmax="";
        String calyearmin="";
        String callastyearmax="";
        String callastyearmin="";
        String nowChannel="";
        String nowtimetypehead="";
        Map<String,Object> nowResult=null;
        List<Map> resultMap=new ArrayList<>();
        Map recordMap=null;
        while (indexlist < listsize) {
            if (log.isInfoEnabled()) {
                log.info("================>3.1数据桶统计");
                log.info(list.get(indexlist).toString());
            }
            qryRetObj=new WeekAndMonthOutboundQryRet();
            recordMap = (Map) list.get(indexlist);
            qryRetObj.setChannel( recordMap.get("channel")==null?"":recordMap.get("channel").toString());
            qryRetObj.setTimetype( recordMap.get("timetype")==null?"":recordMap.get("timetype").toString());
            qryRetObj.setYear(  recordMap.get("year")==null?"":recordMap.get("year").toString() );
            qryRetObj.setTimenum(  recordMap.get("timenum")==null?"":recordMap.get("timenum").toString());
            qryRetObj.setSales( recordMap.get("sales")==null?"":recordMap.get("sales").toString() );
            qryRetObj.setAmount( recordMap.get("amount")==null?"":recordMap.get("amount").toString() );
            if (log.isInfoEnabled()) {
                log.info("================>3.11 打印qryRetObj:"+qryRetObj.toString());
                log.info("================>lastyear:"+lastyear);
                log.info("================>nowyear:"+nowyear);
            }
            // 数据桶统计
            if ( (qryRetObj.getTimetype().startsWith("LAS") && qryRetObj.getTimetype().endsWith(lastyear) )
                  || (qryRetObj.getTimetype().startsWith("NOW") && qryRetObj.getTimetype().endsWith(nowyear)) ) {
                if (log.isInfoEnabled()) {
                    log.info("================>3.12 统计最大值");
                    log.info("================>calyearmax:"+calyearmax);
                    log.info("================>calyearmin:"+calyearmin);

                }
                if (calyearmax.isEmpty() ) {
                    log.info("================>calyearmax为空");
                    calyearmax=qryRetObj.getTimenum();
                } else {
                    log.info("================>calyearmax不为空");
                    calyearmax=qryRetObj.getTimenum().compareTo(calyearmax)>0?qryRetObj.getTimenum():calyearmax;
                }
                if (calyearmin.isEmpty()) {
                    log.info("================>calyearmin为空");
                    calyearmin=qryRetObj.getTimenum();
                } else {
                    log.info("================>calyearmin不为空");
                    calyearmin=qryRetObj.getTimenum().compareTo(calyearmin)<0?qryRetObj.getTimenum():calyearmin;
                }
                if (log.isInfoEnabled()) {
                    log.info("================>calyearmin:"+calyearmin+ "  calyearmax:"+calyearmax);
                }
            } else if ((qryRetObj.getTimetype().startsWith("LAS") && !qryRetObj.getTimetype().endsWith(lastyear) )
                    || (qryRetObj.getTimetype().startsWith("NOW") && !qryRetObj.getTimetype().endsWith(nowyear) )
                    ) {
                if (log.isInfoEnabled()) {
                    log.info("================>3.12 统计最小值");
                }
                if (callastyearmax.isEmpty()) {
                    callastyearmax=qryRetObj.getTimenum();
                } else {
                    callastyearmax=qryRetObj.getTimenum().compareTo(callastyearmax)>0?qryRetObj.getTimenum():callastyearmax;
                }
                if (callastyearmin.isEmpty()) {
                    callastyearmin=qryRetObj.getTimenum();
                } else {
                    callastyearmin=qryRetObj.getTimenum().compareTo(callastyearmin)<0?qryRetObj.getTimenum():callastyearmin;
                }
                if (log.isInfoEnabled()) {
                    log.info("================>callastyearmin:"+callastyearmin+ "  callastyearmax:"+callastyearmax);
                }
            }

            if (log.isInfoEnabled()) {
                log.info("================>3.2分组数据列统计");
            }
            // 分组数据列统计
            // 当nowChannel,nowtimetypehead不一致时,创建一个HashMap存储分组记录值
            if (log.isInfoEnabled()) {
                log.info("================>nowChannel:"+nowChannel);
                log.info("================>qryRetObj.getChannel():"+qryRetObj.getChannel());
                log.info("================>qryRetObj.getTimetype():"+qryRetObj.getTimetype());
                log.info("================>nowtimetypehead:"+nowtimetypehead);
                log.info("================>!nowChannel==qryRetObj.getChannel():"+(!nowChannel.equals(qryRetObj.getChannel())));
                log.info("================>!qryRetObj.getTimetype().startsWith(nowtimetypehead):"+(!qryRetObj.getTimetype().startsWith(nowtimetypehead)));
            }
            if ( (nowtimetypehead.isEmpty() && nowChannel.isEmpty() )  || !nowChannel.equals(qryRetObj.getChannel()) || !qryRetObj.getTimetype().startsWith(nowtimetypehead)) {
                if (log.isInfoEnabled()) {
                    log.info("================>nowChannel,nowtimetypehead不一致");
                }
                //更新nowChannel,nowtimetypehead值
                nowChannel=qryRetObj.getChannel();
                nowtimetypehead=qryRetObj.getTimetype().substring(0,3);
                nowResult =new HashMap<String,Object>();
                resultMap.add(nowResult);
            }
            if (log.isInfoEnabled()) {
                log.info("================>channel Empty Confirm");
                log.info("================>channel:" +nowResult.get("channel"));
            }
            if (nowResult.get("channel")==null) {
                if (log.isInfoEnabled()) {
                    log.info("================>channel Empty");
                }
                nowResult.put("channel",qryRetObj.getChannel());
            }
            if (log.isInfoEnabled()) {
                log.info("================>3.3计算Year,LastYear值");
            }
            // 计算Year,LastYear值
            if (log.isInfoEnabled()) {
                log.info("================>lastyear:"+lastyear);
                log.info("================>lastyear:"+nowyear);
                log.info("================>nowResult.Year:"+nowResult.get("Year"));
                log.info("================>nowResult.LastYear:"+ nowResult.get("LastYear"));
            }
            if (qryRetObj.getTimetype().startsWith("LAS")) {
//                if (qryRetObj.getTimetype().endsWith(lastyear) &&  nowResult.get("Year")==null) {
//                    nowResult.put("Year",qryRetObj.getYear());
//                } else if (!qryRetObj.getTimetype().endsWith(lastyear) &&  nowResult.get("LastYear")==null) {
//                    nowResult.put("LastYear",qryRetObj.getYear());
//                }
                nowResult.put("Year",lastyear);
                nowResult.put("LastYear",beforelastyear);
            }
            if (qryRetObj.getTimetype().startsWith("NOW")) {
//                if (qryRetObj.getTimetype().endsWith(nowyear) &&  nowResult.get("Year")==null) {
//                    nowResult.put("Year",qryRetObj.getYear());
//                } else if (!qryRetObj.getTimetype().endsWith(nowyear) &&  nowResult.get("LastYear")==null) {
//                    nowResult.put("LastYear",qryRetObj.getYear());
//                }
                nowResult.put("Year",nowyear);
                nowResult.put("LastYear",lastyear);
            }
            if (log.isInfoEnabled()) {
                log.info("================>3.4根据统计数值维度不同而存储不同的key值");
            }
            // 根据统计数值维度不同而存储不同的key值
            if (counttype.equals("sales")) {
                if ( (qryRetObj.getTimetype().startsWith("LAS") && qryRetObj.getTimetype().endsWith(lastyear) )
                        || (qryRetObj.getTimetype().startsWith("NOW") && qryRetObj.getTimetype().endsWith(nowyear)) ) {
                    nowResult.put("CURNOW"+qryRetObj.getTimenum(),qryRetObj.getSales());
                } else if ((qryRetObj.getTimetype().startsWith("LAS") && !qryRetObj.getTimetype().endsWith(lastyear) )
                        || (qryRetObj.getTimetype().startsWith("NOW") && !qryRetObj.getTimetype().endsWith(nowyear) )
                        ) {
                    nowResult.put("CURLAS"+qryRetObj.getTimenum(),qryRetObj.getSales());
                }
            } else {
                if ( (qryRetObj.getTimetype().startsWith("LAS") && qryRetObj.getTimetype().endsWith(lastyear) )
                        || (qryRetObj.getTimetype().startsWith("NOW") && qryRetObj.getTimetype().endsWith(String.valueOf(nowyear))) ) {
                    nowResult.put("CURNOW"+qryRetObj.getTimenum(),qryRetObj.getAmount());
                } else if ((qryRetObj.getTimetype().startsWith("LAS") && !qryRetObj.getTimetype().endsWith(lastyear) )
                        || (qryRetObj.getTimetype().startsWith("NOW") && !qryRetObj.getTimetype().endsWith(String.valueOf(nowyear)) )
                        ) {
                    nowResult.put("CURLAS"+qryRetObj.getTimenum(),qryRetObj.getAmount());
                }
            }

//            if (log.isInfoEnabled()) {
//                log.info("================>3.41.nowResult:"+nowResult.toString());
//            }


            indexlist++;
        }

        if (log.isInfoEnabled()) {
            log.info("================>3.42.resultMap:"+resultMap.toString());
        }


        if (log.isInfoEnabled()) {
            log.info("================>3.5根据统计数据桶最大最小构造出动态列头列表");
        }
        // 根据统计数据桶最大最小构造出动态列头列表
        List<Object> dynamicList =new ArrayList<>();
        // nowyear max-min  nowyearlast max-min  lastyear max-min lastyearlast max-min
        if (log.isInfoEnabled()) {
            log.info("============>step[264] calyearmax:"+calyearmax);
            log.info("============>step[264] calyearmin:"+calyearmin);
            log.info("============>step[264] callastyearmax:"+callastyearmax);
            log.info("============>step[264] callastyearmin:"+callastyearmin);
        }

        int calyearmaxint=calyearmax.isEmpty()?-1:Integer.valueOf(calyearmax);  //
        int calyearminint=calyearmin.isEmpty()?-1:Integer.valueOf(calyearmin);
        int callastyearmaxint=callastyearmax.isEmpty()?-1:Integer.valueOf(callastyearmax);
        int callastyearminint=callastyearmin.isEmpty()?-1:Integer.valueOf(callastyearmin);

        if (log.isInfoEnabled()) {
            log.info("============>step[264] calyearmaxint:"+calyearmaxint);
            log.info("============>step[264] calyearminint:"+calyearminint);
            log.info("============>step[264] callastyearmaxint:"+callastyearmaxint);
            log.info("============>step[264] callastyearminint:"+callastyearminint);
        }

        if (calyearmaxint!=-1 && calyearminint !=-1) {
            dynamicList.add("Year");
            for (int indexa=calyearmaxint;indexa>=calyearminint;indexa--) {
                dynamicList.add("CURNOW"+String.format("%02d",indexa));
            }
        }
        if (callastyearmaxint!=-1 && callastyearminint !=-1) {
            dynamicList.add("LastYear");
            for (int indexb=callastyearmaxint;indexb>=callastyearminint;indexb--) {
                dynamicList.add("CURLAS"+String.format("%02d",indexb));
            }
        }

        if (log.isInfoEnabled()) {
            log.info("================>3.6转换统计结果");
        }
        resultlist=convertResult(log,nowyear,lastyear,beforelastyear,dynamicList,resultMap);


        return resultlist;
    }

    @Override
    public boolean generateReportFile(Map<String, Object> paramMap) {
        return false;
    }

    /**
     * 收集好的ListMap判空,比较数值
     */
    public static List<Object> convertResult(Logger log,String nowyear, String lastyear,String beforelastyear, List<Object> dynamicList, List<Map> resultMap) {
        List<Object> dealResultList = new ArrayList<>(); // 整合的所有的显示结果列表
        List<String> resultList = new ArrayList<>();// 单条结果列表
        if (log.isInfoEnabled()) {
            log.info("================>1.把表头显示放置进第一行");
            log.info("=================>dynamicList:"+dynamicList.toString());
            log.info("resultMap:"+resultMap.toString());
        }

        /**
         * 1.把表头显示放置进第一行
         */
        resultList.add("Channel");
        for (Object obj : dynamicList) {
            if (!obj.toString().equals("Year") && !obj.toString().equals("LastYear")) {
                resultList.add(StringUtils.substring(obj.toString(), 6, 8));
            } else {
                resultList.add(obj.toString());
            }

        }
        resultList.add("Total");
        dealResultList.add(resultList);
        if (log.isInfoEnabled()) {
            log.info("================>resultList:"+resultList.toString());
        }


        if (log.isInfoEnabled()) {
            log.info("================>2.处理每一列结果数据,放进处理结果列表中");
            log.info("================>resultMap.size:"+resultMap.size());
        }
        /**
         * 2.处理每一列结果数据,放进处理结果列表中
         */
        int resultMapIndex = 0;
        for (Map result : resultMap) {

            if (log.isInfoEnabled()) {
                log.info("================>2.1 result:"+result.toString());
            }

            /**
             * lastyear无对应行处理
             *
             * 前提:当前行是某个渠道nowyear数据
             * 1.当前是第一个
             * 2.当前不是第一个，并且(上一个不是本渠道数据或者上一个不是lastyear数据)
             * 需要补充记录条数
             */

            if (log.isInfoEnabled()) {
                log.info("================>step[323] lastyear无对应行处理 ");
                log.info("================>Year:"+resultMap.get(resultMapIndex).get("Year"));
                log.info("================>nowyear:"+nowyear);
                log.info("================>lastyear:"+lastyear);
                log.info("================>resultMapIndex:"+resultMapIndex);
                log.info("================>channel[resultMapIndex]:"+resultMap.get(resultMapIndex).get("channel"));
                log.info("================>Judge1:"+resultMap.get(resultMapIndex).get("Year").equals(nowyear) );
                log.info("================>Judge2:"+(resultMapIndex == 0 ));
            }
            if (resultMap.get(resultMapIndex).get("Year").equals(nowyear) &&
                    (
                            (resultMapIndex == 0) ||
                                    (resultMapIndex > 0 && (!resultMap.get(resultMapIndex).get("channel").equals(resultMap.get(resultMapIndex - 1).get("channel")) || !resultMap.get(resultMapIndex - 1).get("Year").equals(lastyear)))
                    )
                    ) {
                if (log.isInfoEnabled()) {
                    log.info("================>2.11 lastyear无对应行处理 ");
                }
                List<String> resultListC = new ArrayList<>();
                resultListC.add(resultMap.get(resultMapIndex).get("channel").toString());
                for (Object obj : dynamicList) {
                    if (log.isInfoEnabled()) {
                        log.info("================>2.11 lastyear无对应行处理 obj:"+obj.toString()+" result.get(obj):"+result.get(obj.toString()));

                    }
                    // 空值处理
                    if (!obj.toString().equals("Year") && !obj.toString().equals("LastYear")) {
                        resultListC.add("0.00");
                    } else if (obj.toString().equals("Year") ) {
                        resultListC.add(lastyear);
                    } else if (obj.toString().equals("LastYear") ) {
                        resultListC.add(beforelastyear);
                    }
                }
                resultListC.add("0.00");
                dealResultList.add(resultListC);
            }


            if (log.isInfoEnabled()) {
                log.info("================>2.1真实记录录入");
            }
            /**
             * 2.1真实记录录入
             */
            List<String> resultListA = new ArrayList<>();
            resultListA.add(String.valueOf(result.get("channel")).isEmpty() ? "" : String.valueOf(result.get("channel")));
            Double totalnum = 0.00;
            // 构造初步列表
            for (Object obj : dynamicList) {
                if (log.isInfoEnabled()) {
                    log.info("================>2.11 obj:"+obj.toString());
                }
                // 空值处理
                if (!obj.toString().equals("Year") && !obj.toString().equals("LastYear")) {
                    if (log.isInfoEnabled()) {
                        log.info("================>2.12 result.get(obj):"+result.get(obj.toString()));
                    }
                    resultListA.add(result.get(obj.toString())==null ||result.get(obj.toString()).toString().isEmpty()  ? "0.00" : (result.get(obj.toString()).toString()));
                } else {
                    resultListA.add(result.get(obj.toString())==null|| result.get(obj.toString()).toString().isEmpty() ? "-" : (result.get(obj.toString()).toString()));
                }

                if (log.isInfoEnabled()) {
                    log.info("================>step[373]: 空值处理完毕 ");
                }
                if (!obj.toString().equals("Year") && !obj.toString().equals("LastYear")) {
                    totalnum += Double.valueOf(  result.get(obj.toString())==null? "0.00":result.get(obj.toString()).toString()  );
                }
                if (log.isInfoEnabled()) {
                    log.info("================>step[379] totalnum:"+totalnum);
                }
            }
            resultListA.add(String.format("%.2f", totalnum));
            dealResultList.add(resultListA);
            if (log.isInfoEnabled()) {
                log.info("================>2.12 resultListA:"+resultListA.toString());
                log.info("================>2.12 dealResultList:"+dealResultList.toString());
            }



            if (log.isInfoEnabled()) {
                log.info("================>3.nowyear无对应行处理");
            }
            /**
             * nowyear无对应行处理
             *
             * 前提:当前行是某个渠道lastyear数据
             * 1.当前是最后一个，并且当前是lastyear数据
             * 2.当前不是最后一个，并且(下一个不是本渠道数据或者下一个不是nowyear数据)
             * 需要补充记录条数
             */
            if (resultMap.get(resultMapIndex).get("Year").equals(lastyear) &&
                    (
                            (resultMapIndex == resultMap.size() - 1) ||
                                    (resultMapIndex < resultMap.size() - 1 && (!resultMap.get(resultMapIndex).get("channel").equals(resultMap.get(resultMapIndex + 1).get("channel")) || !resultMap.get(resultMapIndex+1).get("Year").equals(nowyear)))
                    )
                    ) {

                if (log.isInfoEnabled()) {
                    log.info("================>3.1.nowyear无对应行,进行补录");
                }

                List<String> resultListB = new ArrayList<>();
                resultListB.add(resultMap.get(resultMapIndex).get("channel").toString());
                for (Object obj : dynamicList) {
                    // 空值处理
                    if (!obj.toString().equals("Year") && !obj.toString().equals("LastYear")) {
                        resultListB.add("0.00");
                    } else if (obj.toString().equals("Year") ) {
                        resultListB.add(nowyear);
                    } else if (obj.toString().equals("LastYear") ) {
                        resultListB.add(lastyear);
                    }
                }
                resultListB.add("0.00");
                dealResultList.add(resultListB);
            }

            resultMapIndex++;
        }


        return dealResultList;
    }




//    public static void main(String [] args) {
//
//        ReportServiceForWeekAndMonthOutboundImpl reportServiceForWeekAndMonthOutboundImpl =new ReportServiceForWeekAndMonthOutboundImpl();
//
//        Map<String, Object> paramMap=new HashMap<>();
//        // 统计方式 counttype :amount,sales
//        // counttime :weekly monthly
//        // countnum :统计时间段数量
//        paramMap.put("counttype","sales");
//        paramMap.put("counttime","weekly");
//        paramMap.put("countnum","10");
//
//        List<Object> list= reportServiceForWeekAndMonthOutboundImpl.generateReportData(paramMap);
//
//        System.out.println(list.toString());
//
//        return;
//    }
}
