package saleson.shop.order.api;





import saleson.shop.item.domain.api.ItemInfo;

import java.util.List;

public class ApiOrderList {

    // 주문번호
    private String orderCode;

    // 주문순서
    private int orderSequence;

    // 주문일자
    private String createdDate;

    // 상품정보
    List<ItemInfo> items;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getOrderSequence() {
        return orderSequence;
    }

    public void setOrderSequence(int orderSequence) {
        this.orderSequence = orderSequence;
    }

    public List<ItemInfo> getItems() {
        return items;
    }

    public void setItems(List<ItemInfo> items) {
        this.items = items;
    }

}
