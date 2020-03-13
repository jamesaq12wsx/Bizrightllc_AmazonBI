package importinvdata;


import java.util.Date;

public class InvEntity {
    private String ReportNo;
    private String ReportType;
    private Date StartDate;
    private Date EndDate;
    private String PAsin;
    private String CAsin;
    private String Title;
    private int Sessions;
    private double SessionPercentage;
    private int PageViews;
    private double PageViewsPercentage;
    private double BuyBoxPercentage;
    private int UnitsOrdered;
    private int UnitsOrderedB2B;
    private double UnitSessionPercentage;
    private double UnitSessionPercentageB2B;
    private double OrderedProductSales;
    private double OrderedProductSalesB2B;
    private int TotalOrderItems;
    private int TotalOrderItemsB2B;
    private Date insertTime;


    public InvEntity(String reportNo, String reportType, Date startDate, Date endDate, String PAsin, String CAsin, String title, int sessions, double sessionPercentage, int pageViews, double pageViewsPercentage, double buyBoxPercentage, int unitsOrdered, int unitsOrderedB2B, double unitSessionPercentage, double unitSessionPercentageB2B, double orderedProductSales, double orderedProductSalesB2B, int totalOrderItems, int totalOrderItemsB2B, Date insertTime) {
        ReportNo = reportNo;
        ReportType = reportType;
        StartDate = startDate;
        EndDate = endDate;
        this.PAsin = PAsin;
        this.CAsin = CAsin;
        Title = title;
        Sessions = sessions;
        SessionPercentage = sessionPercentage;
        PageViews = pageViews;
        PageViewsPercentage = pageViewsPercentage;
        BuyBoxPercentage = buyBoxPercentage;
        UnitsOrdered = unitsOrdered;
        UnitsOrderedB2B = unitsOrderedB2B;
        UnitSessionPercentage = unitSessionPercentage;
        UnitSessionPercentageB2B = unitSessionPercentageB2B;
        OrderedProductSales = orderedProductSales;
        OrderedProductSalesB2B = orderedProductSalesB2B;
        TotalOrderItems = totalOrderItems;
        TotalOrderItemsB2B = totalOrderItemsB2B;
        this.insertTime = insertTime;
    }

    public String getReportNo() {
        return ReportNo;
    }

    public void setReportNo(String reportNo) {
        ReportNo = reportNo;
    }

    public String getReportType() {
        return ReportType;
    }

    public void setReportType(String reportType) {
        ReportType = reportType;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public String getPAsin() {
        return PAsin;
    }

    public void setPAsin(String PAsin) {
        this.PAsin = PAsin;
    }

    public String getCAsin() {
        return CAsin;
    }

    public void setCAsin(String CAsin) {
        this.CAsin = CAsin;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getSessions() {
        return Sessions;
    }

    public void setSessions(int sessions) {
        Sessions = sessions;
    }

    public double getSessionPercentage() {
        return SessionPercentage;
    }

    public void setSessionPercentage(double sessionPercentage) {
        SessionPercentage = sessionPercentage;
    }

    public int getPageViews() {
        return PageViews;
    }

    public void setPageViews(int pageViews) {
        PageViews = pageViews;
    }

    public double getPageViewsPercentage() {
        return PageViewsPercentage;
    }

    public void setPageViewsPercentage(double pageViewsPercentage) {
        PageViewsPercentage = pageViewsPercentage;
    }

    public double getBuyBoxPercentage() {
        return BuyBoxPercentage;
    }

    public void setBuyBoxPercentage(double buyBoxPercentage) {
        BuyBoxPercentage = buyBoxPercentage;
    }

    public int getUnitsOrdered() {
        return UnitsOrdered;
    }

    public void setUnitsOrdered(int unitsOrdered) {
        UnitsOrdered = unitsOrdered;
    }

    public int getUnitsOrderedB2B() {
        return UnitsOrderedB2B;
    }

    public void setUnitsOrderedB2B(int unitsOrderedB2B) {
        UnitsOrderedB2B = unitsOrderedB2B;
    }

    public double getUnitSessionPercentage() {
        return UnitSessionPercentage;
    }

    public void setUnitSessionPercentage(double unitSessionPercentage) {
        UnitSessionPercentage = unitSessionPercentage;
    }

    public double getUnitSessionPercentageB2B() {
        return UnitSessionPercentageB2B;
    }

    public void setUnitSessionPercentageB2B(double unitSessionPercentageB2B) {
        UnitSessionPercentageB2B = unitSessionPercentageB2B;
    }

    public double getOrderedProductSales() {
        return OrderedProductSales;
    }

    public void setOrderedProductSales(double orderedProductSales) {
        OrderedProductSales = orderedProductSales;
    }

    public double getOrderedProductSalesB2B() {
        return OrderedProductSalesB2B;
    }

    public void setOrderedProductSalesB2B(double orderedProductSalesB2B) {
        OrderedProductSalesB2B = orderedProductSalesB2B;
    }

    public int getTotalOrderItems() {
        return TotalOrderItems;
    }

    public void setTotalOrderItems(int totalOrderItems) {
        TotalOrderItems = totalOrderItems;
    }

    public int getTotalOrderItemsB2B() {
        return TotalOrderItemsB2B;
    }

    public void setTotalOrderItemsB2B(int totalOrderItemsB2B) {
        TotalOrderItemsB2B = totalOrderItemsB2B;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}