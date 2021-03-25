package saleson.shop.statistics.domain;

import java.util.List;

public class AreaStatsSummary {

    private String sido;
    private String sigungu;
    private int sidoMappingGroupKey;

    private List<BaseStats> groupStats;

    public String getSido() {
        return sido;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }

    public String getSigungu() {
        return sigungu;
    }

    public void setSigungu(String sigungu) {
        this.sigungu = sigungu;
    }

    public int getSidoMappingGroupKey() {
        return sidoMappingGroupKey;
    }

    public void setSidoMappingGroupKey(int sidoMappingGroupKey) {
        this.sidoMappingGroupKey = sidoMappingGroupKey;
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
}
