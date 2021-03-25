package saleson.shop.order.claimapply.domain;

import com.onlinepowers.framework.repository.CodeInfo;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.refund.domain.OrderRefund;

import java.util.List;
import java.util.Map;

public class RefundInfo {

    private String  partCancel= "0";
    private OrderPgData orderPgData;
    private Map<String, Object> orderRefundApiInfo;

    public String getPartCancel() {
        return partCancel;
    }

    public void setPartCancel(String partCancel) {
        this.partCancel = partCancel;
    }

    public OrderPgData getOrderPgData() {
        return orderPgData;
    }

    public void setOrderPgData(OrderPgData orderPgData) {
        this.orderPgData = orderPgData;
    }

    public Map<String, Object> getOrderRefundApiInfo() {
        return orderRefundApiInfo;
    }

    public void setOrderRefundApiInfo(Map<String, Object> orderRefundApiInfo) {
        this.orderRefundApiInfo = orderRefundApiInfo;
    }
}
