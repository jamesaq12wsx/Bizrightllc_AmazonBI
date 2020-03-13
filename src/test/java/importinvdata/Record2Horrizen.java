package importinvdata;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.HashSet;
        import java.util.List;
        import java.util.Map;
        import java.util.Set;

/**
 * 一个行转列的小程序，将如下结构的数据：
 *    c1,c2,pk,fk1,v1
 *    c1,c2,pk,fk2,v2
 *    c1,c2,pk,fk3,v3
 *    .....
 *  转化为:
 *    c1,c2,pk,v1,v2,v3.....
 *
 *
 */
public class Record2Horrizen {
    public static void main(String[] args){
        List<Map> records=getRecords(
                DataGetter.getData(),
                "id",
                "course",
                "grade"
        );
        String sTemplete=" ID:%1$-10s"
                +"姓名:%2$-10s"
                +"语文:%3$-10s"
                +"数学:%4$-10s"
                +"英语:%5$-10s"
                +"物理:%6$-10s";

        for(Map record:records){
            System.out.println(String.format(
                    sTemplete,
                    record.get("id"),
                    record.get("name"),
                    record.get("语文")!=null?record.get("语文"):"--",
                    record.get("数学")!=null?record.get("数学"):"--",
                    record.get("英语")!=null?record.get("英语"):"--",
                    record.get("物理")!=null	?record.get("物理"):"--"
            ));
        }
    }


    /**
     * 获取行专列后的列表
     * @param list 原始数据列表(需要提前按主键排序)
     * @param sPKF 主键字段名称
     * @param sFKF 外键字段名称
     * @param sVF 值字段名称
     * @return
     */
    public static List<Map> getRecords(List<Map> list,
                                       String sPKF,
                                       String sFKF,
                                       String sVF){
        List<Map> results=new ArrayList<Map>();

        if(list instanceof List&&list.size()>0){
            Map record,preRecord,rowDataCatch=new HashMap();
            Set<String> colSet=getColSet(list,sFKF,sVF);
            Object pk=list.get(0).get(sPKF);

            for(int i=0,len=list.size();i<len;i++){
                record=list.get(i);

                //当主键发生跳变或最后一条记录时，行转列输出
                if(i==len-1||!record.get(sPKF).equals(pk)){
					/*
					 * 若最后一行触发的输出，提前写入缓存。因为写缓存
					 * 操作发生在判断之后，若不这样最后一条数据会被漏掉
					*/
                    if(i==len-1){
                        rowDataCatch.put(
                                record.get(sFKF),
                                record.get(sVF)
                        );
                    }
                    preRecord=list.get(i-1);
                    preRecord.remove(sFKF);
                    preRecord.remove(sVF);
                    preRecord.putAll(rowDataCatch);

                    addRecord(results,colSet,preRecord);

                    //重新设置主键标记和行数据缓存
                    rowDataCatch.clear();
                    pk=record.get(sPKF);
                }

                //将外键值值字段写入行数据缓存
                if(i<len-1){
                    rowDataCatch.put(
                            record.get(sFKF),
                            record.get(sVF)
                    );
                }
            }
        }

        return results;
    }

    /**
     * 统计所有输出列
     * @param list 数据列表
     * @param sFKF 外键字段名称
     * @param sVF  值字段名称
     * @return
     */
    private static Set<String> getColSet(List<Map> list,
                                         String sFKF,
                                         String sVF){

        Set<String> colSet=new HashSet<String>();

        if(list instanceof List&&list.size()>0){
            Map record=list.get(0);

            //非变化字段集合
            for(Object key:record.keySet()){
                if(!key.equals(sFKF)&&!key.equals(sVF))
                    colSet.add(key.toString());
            }

            //变化字段集合
            for(Map item:list){
                colSet.add((String)item.get(sFKF));
            }
        }

        return colSet;
    }

    /**
     * 将行数据进行列补齐操作后写入输出列表
     * @param list
     * @param colSet
     * @param rowData
     */
    private static void addRecord(List<Map> list,
                                  Set<String> colSet,
                                  Map rowData){
        //补齐不存在的列
        for(String column:colSet){
            if(!rowData.containsKey(column)){
                rowData.put(column,null);
            }
        }

        list.add(rowData);
    }
}


