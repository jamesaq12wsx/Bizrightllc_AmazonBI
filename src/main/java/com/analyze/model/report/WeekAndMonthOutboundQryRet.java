package com.analyze.model.report;

/**
 * 周/月销量报表查询返回实体
 */
public class WeekAndMonthOutboundQryRet {
    private String channel;
    private String timetype;
    private String year;
    private String timenum;
    private String sales;
    private String amount;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTimetype() {
        return timetype;
    }

    public void setTimetype(String timetype) {
        this.timetype = timetype;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTimenum() {
        return timenum;
    }

    public void setTimenum(String timenum) {
        this.timenum = timenum;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "WeekAndMonthOutboundQryRet{" +
                "channel='" + channel + '\'' +
                ", timetype='" + timetype + '\'' +
                ", year='" + year + '\'' +
                ", timenum='" + timenum + '\'' +
                ", sales='" + sales + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
