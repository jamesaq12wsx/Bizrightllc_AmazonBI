package importinvdata;

import java.util.List;

/**
 * @author zhucan
 *
 */
public class TestExcelToDb {
    public static void main(String[] args) {
        //得到表格中所有的数据
        List<InvEntity> listExcel=InvService.getAllByExcel("d://book.xls");

        DBhepler db=new DBhepler();

        for (InvEntity InvEntity : listExcel) {
            String sql="INSERT INTO [ReportDPSalesAndTraff]([ReportNo], [ReportType], [StartDate], [EndDate], [PAsin], [CAsin], [Title], " +
                    "[Sessions], [SessionPercentage], [PageViews], [PageViewsPercentage], [BuyBoxPercentage], [UnitsOrdered], [UnitsOrderedB2B]," +
                    " [UnitSessionPercentage], [UnitSessionPercentageB2B], [OrderedProductSales], [OrderedProductSalesB2B], [TotalOrderItems]," +
                    " [TotalOrderItemsB2B], [insertTime]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            String[] str=new String[]{InvEntity.getReportNo(),InvEntity.getReportType(), String.valueOf(InvEntity.getStartDate()), String.valueOf(InvEntity.getEndDate()),InvEntity.getPAsin(),InvEntity.getCAsin(),InvEntity.getTitle(),
                    String.valueOf(InvEntity.getSessions()), String.valueOf(InvEntity.getSessionPercentage()), String.valueOf(InvEntity.getPageViews()), String.valueOf(InvEntity.getPageViewsPercentage()), String.valueOf(InvEntity.getBuyBoxPercentage()), String.valueOf(InvEntity.getUnitsOrdered()), String.valueOf(InvEntity.getUnitsOrderedB2B()),
                    String.valueOf(InvEntity.getUnitSessionPercentage()), String.valueOf(InvEntity.getUnitSessionPercentageB2B()), String.valueOf(InvEntity.getOrderedProductSales()), String.valueOf(InvEntity.getOrderedProductSalesB2B()), String.valueOf(InvEntity.getTotalOrderItems()),
                    String.valueOf(InvEntity.getTotalOrderItemsB2B()), String.valueOf(InvEntity.getInsertTime())};

            db.AddU(sql, str);
        }
    }
}