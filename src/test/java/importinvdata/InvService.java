package importinvdata;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.math.RandomUtils;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class InvService {
    /**
     * 查询stu表中所有的数据
     * @return
     */
    public static List<InvEntity> getAllByDb(){
        List<InvEntity> list=new ArrayList<InvEntity>();
        try {
            DBhepler db=new DBhepler();
            String sql="select * from ReportDPSalesAndTraff";
            ResultSet rs= db.Search(sql, null);
            while (rs.next()) {
                String reportNo=rs.getString("reportNo");
                String reportType=rs.getString("reportType");
                Date startDate=rs.getDate("startDate");
                Date endDate=rs.getDate("endDate");
                String PAsin=rs.getString("PAsin");
                String CAsin=rs.getString("CAsin");
                String title=rs.getString("title");
                int sessions=rs.getInt("sessions");
                double sessionPercentage=rs.getDouble("sessionPercentage");
                int pageViews=rs.getInt("pageViews");
                double pageViewsPercentage=rs.getDouble("pageViewsPercentage");
                double buyBoxPercentage=rs.getDouble("buyBoxPercentage");
                int unitsOrdered=rs.getInt("unitsOrdered");
                int unitsOrderedB2B=rs.getInt("unitsOrderedB2B");
                double unitSessionPercentage=rs.getDouble("unitSessionPercentage");
                double unitSessionPercentageB2B=rs.getDouble("unitSessionPercentageB2B");
                double orderedProductSales=rs.getDouble("orderedProductSales");
                double orderedProductSalesB2B=rs.getDouble("orderedProductSalesB2B");
                int totalOrderItems=rs.getInt("totalOrderItems");
                int totalOrderItemsB2B=rs.getInt("totalOrderItemsB2B");
                Date insertTime=rs.getDate("insertTime");

                //System.out.println(id+" "+name+" "+sex+ " "+num);
                list.add(new InvEntity(reportNo, reportType, startDate, endDate, PAsin,
                         CAsin,  title,  sessions,  sessionPercentage,  pageViews,  pageViewsPercentage,  buyBoxPercentage,
                        unitsOrdered,  unitsOrderedB2B,  unitSessionPercentage,  unitSessionPercentageB2B,
                        orderedProductSales,  orderedProductSalesB2B,  totalOrderItems,  totalOrderItemsB2B,  insertTime));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查询指定目录中电子表格中所有的数据
     * @param file 文件完整路径
     * @return
     */
    public static List<InvEntity> getAllByExcel(String file){
        List<InvEntity> list=new ArrayList<InvEntity>();
        try {
            Workbook rwb=Workbook.getWorkbook(new File(file));
            Sheet rs=rwb.getSheet("Test Shee 1");//或者rwb.getSheet(0)
            int clos=rs.getColumns();//得到所有的列
            int rows=rs.getRows();//得到所有的行

            System.out.println(clos+" rows:"+rows);
            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < clos; j++) {
                    //第一个是列数，第二个是行数
                    String id=rs.getCell(j++, i).getContents();//默认最左边编号也算一列 所以这里得j++
                    String name=rs.getCell(j++, i).getContents();
                    String sex=rs.getCell(j++, i).getContents();
                    String num=rs.getCell(j++, i).getContents();

                    String reportNo=String.valueOf( RandomUtils.nextLong());
                    String reportType=rs.getCell(j++, i).getContents();
                    Date startDate= new Date("2019-07-25 23:59:59.100");
                    Date endDate= new Date("2019-07-25 23:59:59.100");
                    String PAsin=rs.getCell(j++, i).getContents();
                    String CAsin=rs.getCell(j++, i).getContents();
                    String title=rs.getCell(j++, i).getContents();
                    int sessions=Integer.valueOf(rs.getCell(j++, i).getContents());
                    double sessionPercentage=Double.valueOf(rs.getCell(j++, i).getContents());
                    int pageViews=Integer.valueOf(rs.getCell(j++, i).getContents());
                    double pageViewsPercentage=Double.valueOf(rs.getCell(j++, i).getContents());
                    double buyBoxPercentage=Double.valueOf(rs.getCell(j++, i).getContents());
                    int unitsOrdered=Integer.valueOf(rs.getCell(j++, i).getContents());
                    int unitsOrderedB2B=Integer.valueOf(rs.getCell(j++, i).getContents());
                    double unitSessionPercentage=Double.valueOf(rs.getCell(j++, i).getContents());
                    double unitSessionPercentageB2B=Double.valueOf(rs.getCell(j++, i).getContents());
                    double orderedProductSales=Double.valueOf(rs.getCell(j++, i).getContents());
                    double orderedProductSalesB2B=Double.valueOf(rs.getCell(j++, i).getContents());
                    int totalOrderItems=Integer.valueOf(rs.getCell(j++, i).getContents());
                    int totalOrderItemsB2B=Integer.valueOf(rs.getCell(j++, i).getContents());
                    Date insertTime=new Date();

                    System.out.println("id:"+id+" name:"+name+" sex:"+sex+" num:"+num);
                    list.add(new InvEntity(reportNo, reportType, startDate, endDate, PAsin,
                            CAsin,  title,  sessions,  sessionPercentage,  pageViews,  pageViewsPercentage,  buyBoxPercentage,
                            unitsOrdered,  unitsOrderedB2B,  unitSessionPercentage,  unitSessionPercentageB2B,
                            orderedProductSales,  orderedProductSalesB2B,  totalOrderItems,  totalOrderItemsB2B,  insertTime));
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;

    }

    /**
     * 检查存在
     * @param ReportNo
     * @return
     */
    public static boolean isExist(String ReportNo){
//        try {
//            DBhepler db=new DBhepler();
//            ResultSet rs=db.Search("select * from ReportDPSalesAndTraff where ReportNo=?", new String[]{ReportNo+""});
//            if (rs.next()) {
//                return true;
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return false;

        return true;
    }

    public static void main(String[] args) {
        /*List<InvEntity> all=getAllByDb();
        for (InvEntity InvEntity : all) {
            System.out.println(InvEntity.toString());
        }*/

        System.out.println(isExist(""));

    }

}