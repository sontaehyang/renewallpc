package saleson.shop.ums.support;

import com.onlinepowers.framework.util.StringUtils;
import saleson.common.notification.domain.UmsTemplate;
import saleson.common.utils.ItemUtils;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.config.domain.Config;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.domain.OrderReturnApply;
import saleson.shop.order.refund.domain.OrderRefund;
import saleson.shop.order.refund.domain.OrderRefundDetail;

import java.util.HashMap;

public class OrderRefundApproval extends UmsTemplate {

    private String userName;
    private String itemName;
    private String orderCode;
    private int refundAmount;
    private String siteName;

    public OrderRefundApproval() {
        intiCodeMapView();
    }

    public OrderRefundApproval(Ums ums, OrderRefund orderRefund, long userId, Config config, String phoneNumber, ApplicationInfo applicationInfo) {
        super(ums, phoneNumber);

        if (orderRefund == null) {
            return;
        }

        int itemCount = 0;
        String itemName = "";
        for(OrderRefundDetail group : orderRefund.getGroups()) {
            if (group.getOrderReturnApplys().size() > 0) {
                for (OrderReturnApply orderReturnApply : group.getOrderReturnApplys()) {
                    if (itemCount == 0) {
                        itemName = orderReturnApply.getOrderItem().getItemName();
                    }

                    itemCount++;
                }

            } else if (group.getOrderCancelApplys().size() > 0) {
                for (OrderCancelApply orderCancelApply : group.getOrderCancelApplys()) {
                    if (itemCount == 0) {
                        itemName = orderCancelApply.getOrderItem().getItemName();
                    }

                    itemCount++;
                }
            }
        }

        this.userName = orderRefund.getBuyerName();
        this.itemName = ItemUtils.getSubstringItemName(itemName, itemName.length(), itemCount, true);
        this.orderCode = orderRefund.getOrderCode();
        this.refundAmount = orderRefund.getReturnPayAmount();
        this.siteName = config.getShopName();

        intiCodeMapView();
        intiCodeMap();

        super.initialize(userId, applicationInfo);
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("user_name", "주문자명");
        map.put("item_name", "제품명");
        map.put("order_code", "주문번호");
        map.put("refund_amount", "환불금액");
        map.put("site_name", "상점명");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        HashMap<String, String> map = new HashMap<>();

        map.put("user_name", this.userName);
        map.put("item_name", this.itemName);
        map.put("order_code", this.orderCode);
        map.put("refund_amount", StringUtils.numberFormat(Integer.toString(this.refundAmount)));
        map.put("site_name", this.siteName);

        addCodeMap(map);

    }
}
