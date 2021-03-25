package saleson.shop.statistics.domain;


import java.util.ArrayList;
import java.util.List;

public class StatsSummary {
    private List<BaseStats> baseStats;

    public StatsSummary(List<BaseStats> baseStats) {
        if (baseStats == null) {
            baseStats = new ArrayList<>();
        }
        this.baseStats = baseStats;
    }

    public long getSaleCount() {
        return baseStats.stream().mapToLong(s -> s.getSaleCount()).sum();
    }

    public long getCancelCount() {
        return baseStats.stream().mapToLong(s -> s.getCancelCount()).sum();
    }

    public long getTotalCount() {
        return getSaleCount() + getCancelCount();
    }

    public long getSaleAmount() {
        return baseStats.stream().mapToLong(s -> s.getSaleAmount()).sum();
    }

    public long getCancelAmount() {
        return baseStats.stream().mapToLong(s -> s.getCancelAmount()).sum();
    }

    public long getPayAmount() {
        return baseStats.stream().mapToLong(s -> s.getPayAmount()).sum();
    }

    public long getCancelPayAmount() {
        return baseStats.stream().mapToLong(s -> s.getCancelPayAmount()).sum();
    }

    public long getItemPrice() {
        return baseStats.stream().mapToLong(s -> s.getItemPrice()).sum();
    }

    public long getCancelItemPrice() {
        return baseStats.stream().mapToLong(s -> s.getCancelItemPrice()).sum();
    }

    public long getTotalItemPrice() {
        return this.getItemPrice() + this.getCancelItemPrice();
    }

    public long getDiscountAmount() { return baseStats.stream().mapToLong(s -> s.getDiscountAmount()).sum(); }

    public long getCancelDiscountAmount() { return baseStats.stream().mapToLong(s -> s.getCancelDiscountAmount()).sum(); }

    public long getShipping() {
        return baseStats.stream().mapToLong(s -> s.getShipping()).sum();
    }

    public long getCancelShipping() {
        return baseStats.stream().mapToLong(s -> s.getCancelShipping()).sum();
    }

    public long getTotalAmount() {
        return getSaleAmount() + getCancelAmount();
    }

    public long getTotalDiscountAmount() { return this.getDiscountAmount() + this.getCancelDiscountAmount(); }

    public long getTotalShipping() { return this.getShipping() + this.getCancelShipping(); }

    public long getTotalPayAmount() { return this.getTotalAmount() + this.getTotalShipping(); }

    public int getTotalRecord() {
        return (int) baseStats.size();
    }

    @Override
    public String toString() {
        return "StatsSummary{" +
                "saleCount=" + getSaleCount() + "," +
                "saleAmount=" + getSaleAmount() + "," +
                "cancelCount=" + getCancelCount() + "," +
                "cancelAmount=" + getCancelAmount() + "," +
                "totalRecord=" + getTotalRecord() + "," +
                '}';
    }
}
