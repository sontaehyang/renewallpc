package saleson.shop.statistics.domain;

import org.springframework.util.StringUtils;
import saleson.common.utils.ShopUtils;

import java.util.List;

public class ItemStatsSummary {
    private int itemId;
    private String itemName;
    private String itemUserCode;
    private String itemImage;

    private List<BaseStats> groupStats;

    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public String getItemName() {

        if (StringUtils.isEmpty(itemName)) {
            return "정보 없음";
        }

        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getItemUserCode() {
        return itemUserCode;
    }
    public void setItemUserCode(String itemUserCode) {
        this.itemUserCode = itemUserCode;
    }
    public String getItemImage() {
        return itemImage;
    }
    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    /**
     * 목록 이미지 경로
     * @return
     */
    public String getImageSrc() {
        return ShopUtils.listImage(this.itemUserCode, this.itemImage);
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

    public List<BaseStats> getGroupStats() {
        return groupStats;
    }

    public void setGroupStats(List<BaseStats> groupStats) {
        this.groupStats = groupStats;
    }
}
