package importinvdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataGetter {
    public static List<Map> getData(){
        List<Map> datas=new ArrayList<Map>();
        Map record;

        //记录1
        record=new HashMap();
        record.put("id", "id-1");
        record.put("name","姓名1");
        record.put("course","语文");
        record.put("grade", 30);
        datas.add(record);

        record=new HashMap();
        record.put("id", "id-1");
        record.put("name","姓名1");
        record.put("course","数学");
        record.put("grade", 40);
        datas.add(record);

        record=new HashMap();
        record.put("id", "id-1");
        record.put("name","姓名1");
        record.put("course","英语");
        record.put("grade",60);
        datas.add(record);

        //记录2
        record=new HashMap();
        record.put("id", "id-2");
        record.put("name","姓名2");
        record.put("course","语文");
        record.put("grade", 60);
        datas.add(record);

        record=new HashMap();
        record.put("id", "id-2");
        record.put("name","姓名2");
        record.put("course","物理");
        record.put("grade", 80);
        datas.add(record);

        //记录3
        record=new HashMap();
        record.put("id", "id-3");
        record.put("name","姓名3");
        record.put("course","语文");
        record.put("grade", 60);
        datas.add(record);

        record=new HashMap();
        record.put("id", "id-3");
        record.put("name","姓名3");
        record.put("course","英语");
        record.put("grade", 80);
        datas.add(record);

        return datas;
    }
}
