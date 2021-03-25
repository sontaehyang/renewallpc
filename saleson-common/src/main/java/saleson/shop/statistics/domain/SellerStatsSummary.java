package saleson.shop.statistics.domain;

import org.springframework.util.StringUtils;

import java.util.List;

public class SellerStatsSummary {
    private long sellerId;
    private String sellerName;

    private List<BaseStats> groupStats;

    public long getSellerId() {
        return sellerId;
    }
    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }
    public String getSellerName() {

        if (StringUtils.isEmpty(sellerName)) {
            return "정보 없음";
        }

        return sellerName;
    }
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
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
