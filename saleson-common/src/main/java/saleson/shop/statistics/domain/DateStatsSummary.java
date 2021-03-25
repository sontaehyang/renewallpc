package saleson.shop.statistics.domain;

import java.util.List;

public class DateStatsSummary {
    private String searchDate;

    private List<BaseStats> groupStats;

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public List<BaseStats> getGroupStats() {
        return groupStats;
    }

    public void setGroupStats(List<BaseStats> groupStats) {
        this.groupStats = groupStats;
    }

    public long getSubSaleCount() {
        return groupStats.stream().mapToLong(s -> s.getSaleCount()).sum();
    }

    public long getSubItemPrice() {
        return groupStats.stream().mapToLong(s -> s.getItemPrice()).sum();
    }

    public long getSubDiscountAmount() {
        return groupStats.stream().mapToLong(s -> s.getDiscountAmount()).sum();
    }

    public long getSubSaleAmount() {
        return groupStats.stream().mapToLong(s -> s.getSaleAmount()).sum();
    }

    public long getSubShipping() {
        return groupStats.stream().mapToLong(s -> s.getShipping()).sum();
    }

    public long getSubPayAmount() {
        return groupStats.stream().mapToLong(s -> s.getPayAmount()).sum();
    }

    public long getSubCancelCount() {
        return groupStats.stream().mapToLong(s -> s.getCancelCount()).sum();
    }

    public long getSubCancelItemPrice() {
        return groupStats.stream().mapToLong(s -> s.getCancelItemPrice()).sum();
    }

    public long getSubCancelDiscountAmount() {
        return groupStats.stream().mapToLong(s -> s.getCancelDiscountAmount()).sum();
    }

    public long getSubCancelAmount() {
        return groupStats.stream().mapToLong(s -> s.getCancelAmount()).sum();
    }

    public long getSubCancelShipping() {
        return groupStats.stream().mapToLong(s -> s.getCancelShipping()).sum();
    }

    public long getSubCancelPayAmount() {
        return groupStats.stream().mapToLong(s -> s.getCancelPayAmount()).sum();
    }

    public long getSubTotalCount() {
        return this.getSubSaleCount() + this.getSubCancelCount();
    }

    public long getSubTotalItemPrice() {
        return this.getSubItemPrice() + this.getSubCancelItemPrice();
    }

    public long getSubTotalDiscountAmount() {
        return this.getSubDiscountAmount() + this.getSubCancelDiscountAmount();
    }

    public long getSubTotalAmount() {
        return this.getSubSaleAmount() + this.getSubCancelAmount();
    }

    public long getSubTotalShipping() {
        return this.getSubShipping() + this.getSubCancelShipping();
    }

    public long getSubTotalPayAmount() {
        return this.getSubTotalAmount() + this.getSubTotalShipping();
    }

}
