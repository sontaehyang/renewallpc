package saleson.shop.ums.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import saleson.common.config.SalesonProperty;
import saleson.common.notification.domain.UmsTemplate;
import saleson.common.utils.ShopUtils;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.config.domain.Config;
import saleson.shop.order.domain.*;

import java.util.HashMap;

public class OrderNew extends UmsTemplate {

    private String orderName;
    private String orderCode;
    private String orderDate;

    private String siteName;
    private String siteUrl;
    private String itemName;
    private String paymentResult;

    public OrderNew() {
        intiCodeMapView();
    }

    public OrderNew(String orderName) {
        intiCodeMapView();
    }

    public OrderNew(Ums ums, Order order, long userId, Config config, String phoneNumber, ApplicationInfo applicationInfo) {
        super(ums, phoneNumber);

        if (order== null) {
            return;
        }

        // 주문정보
        this.orderCode = order.getOrderCode();
        this.orderDate = DateUtils.date(order.getCreatedDate());
        this.orderName = order.getUserName();

        this.siteName = config.getShopName();
        this.siteUrl = SalesonProperty.getSalesonUrlShoppingmall();

        String itemName = order.getOrderShippingInfos().get(0).getOrderItems().get(0).getItemName();
        if (order.getOrderShippingInfos().get(0).getOrderItems().size() > 1) {
            itemName += " 외 " + (order.getOrderShippingInfos().get(0).getOrderItems().size() - 1) + "개";
        }
        this.itemName = itemName;


        String approvalType = "";
        String payAmount = "";
        for (OrderPayment orderPayment : order.getOrderPayments()) {
            if ("card".equals(orderPayment.getApprovalType())) {
                approvalType = "카드";

            } else if ("realtimebank".equals(orderPayment.getApprovalType())) {
                approvalType = "실시간 계좌이체";

            } else  if ("vbank".equals(orderPayment.getApprovalType())) {
                approvalType = "가상계좌";

            } else if  ("bank".equals(orderPayment.getApprovalType())) {
                approvalType = "무통장 입금";

            } else if ("naverpay".equals(orderPayment.getApprovalType())) {
                approvalType = "네이버페이";
            }

            payAmount = StringUtils.numberFormat(orderPayment.getAmount());
        }

        this.paymentResult = approvalType + " " + payAmount + " 원";

        intiCodeMapView();
        intiCodeMap();

        super.initialize(userId, applicationInfo);
    }

    public OrderNew(Ums ums, Buy buy, long userId, Config config, String phoneNumber, ApplicationInfo applicationInfo) {
        super(ums, phoneNumber);

        if (buy == null) {
            return;
        }

        Buyer buyer = buy.getBuyer();

        // 주문정보
        this.orderCode = buy.getOrderCode();
        this.orderDate = DateUtils.getToday("yyyy-MM-dd");
        this.orderName = buyer.getUserName();

        this.siteName = config.getShopName();
        this.siteUrl = SalesonProperty.getSalesonUrlShoppingmall();

        String itemName = buy.getItems().get(0).getItem().getItemName();
        if (buy.getItems().size() > 1) {
            itemName += " 외 " + (buy.getItems().size() - 1) + "개";
        }
        this.itemName = itemName;

        String approvalType = "";
        String payAmount = "";
        for (BuyPayment orderPayment : buy.getPayments()) {
            if ("card".equals(orderPayment.getApprovalType())) {
                approvalType = "카드";

            } else if ("realtimebank".equals(orderPayment.getApprovalType())) {
                approvalType = "실시간 계좌이체";

            } else  if ("vbank".equals(orderPayment.getApprovalType())) {
                approvalType = "가상계좌";

            } else if  ("bank".equals(orderPayment.getApprovalType())) {
                approvalType = "무통장 입금";

            } else if ("naverpay".equals(orderPayment.getApprovalType())) {
                approvalType = "네이버페이";
            }

            payAmount = StringUtils.numberFormat(orderPayment.getAmount());
        }

        this.paymentResult = approvalType + " " + payAmount + " 원";

        intiCodeMapView();
        intiCodeMap();

        super.initialize(userId, applicationInfo);
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("user_name", "주문자명");
        map.put("order_code", "주문코드");
        map.put("order_date", "주문일자");

        map.put("site_name", "상점명");
        map.put("site_url", "상점URL");

        map.put("item_name", "상품명");

        map.put("counsel_tel_number", "고객센터");
        map.put("payment_result", "결제금액");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        HashMap<String, String> map = new HashMap<>();

        Config config = ShopUtils.getConfig();

        map.put("order_code", this.orderCode);
        map.put("user_name", this.orderName);
        map.put("site_name", this.siteName);
        map.put("site_url", this.siteUrl);
        map.put("order_date", this.orderDate);

        map.put("item_name", this.itemName);

        map.put("counsel_tel_number", config.getCounselTelNumber());
        map.put("payment_result", this.paymentResult);

        addCodeMap(map);

    }
}
