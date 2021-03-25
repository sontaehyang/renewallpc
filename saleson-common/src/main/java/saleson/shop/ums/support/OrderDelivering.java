package saleson.shop.ums.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import saleson.common.config.SalesonProperty;
import saleson.common.notification.domain.UmsTemplate;
import saleson.common.utils.ItemUtils;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.config.domain.Config;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShippingInfo;

import java.util.HashMap;

public class OrderDelivering extends UmsTemplate {

    private String orderName;
    private String orderCode;
    private String orderDate;

    private String siteName;
    private String siteUrl;

    private String itemName10;
    private String itemName20;
    private String itemName30;
    private String fullItemName;

    private String deliveryCompanyName;
    private String deliveryNumber;

    public OrderDelivering() {
        intiCodeMapView();
    }

    public OrderDelivering(Order order, long userId, Config config, Ums ums, String phoneNumber, ApplicationInfo applicationInfo) {
        super(ums, phoneNumber);

        this.init(order, userId, config, applicationInfo);
    }

    private void init(Order order, long userId, Config config, ApplicationInfo applicationInfo) {
        if (order== null) {
            return;
        }

        // 주문정보
        this.orderCode = order.getOrderCode();
        this.orderDate = DateUtils.date(order.getCreatedDate());
        this.orderName = order.getUserName();

        this.siteName = config.getShopName();
        this.siteUrl = SalesonProperty.getSalesonUrlShoppingmall();

        this.deliveryCompanyName = order.getMessageTargetDeliveryCompanyName();
        this.deliveryNumber = order.getMessageTargetDeliveryNumber();

        String itemName = "";
        int itemCount = order.getMessageTargetItemSequences().length;
        if (StringUtils.isNotEmpty(order.getMessageTargetDeliveryNumber())) {

            String targetSeq = order.getMessageTargetItemSequences()[0];
            String itemSeq = targetSeq.substring(targetSeq.length() - 1);

            for(OrderShippingInfo info : order.getOrderShippingInfos()) {
                for(OrderItem orderItem : info.getOrderItems()) {
                    if (itemSeq.equals(String.valueOf(orderItem.getItemSequence()))) {
                        itemName = orderItem.getItemName();
                    }
                }
            }

            // 짧은 상품명 2건 이상인 경우에도 '외 n건' 붙임. 2020.10.22
            if (itemName.length() < 10) {
                itemName += itemCount > 1 ? " 외" + (itemCount - 1) + "건" : "";
            }

            if (StringUtils.isNotEmpty(itemName)) {
                this.itemName10 = ItemUtils.getSubstringItemName(itemName, 10, itemCount, false);
                this.itemName20 = ItemUtils.getSubstringItemName(itemName, 20, itemCount, false);
                this.itemName30 = ItemUtils.getSubstringItemName(itemName, 30, itemCount, false);

                this.fullItemName = ItemUtils.getSubstringItemName(itemName, itemName.length(), itemCount, true);
            }
        }

        intiCodeMapView();
        intiCodeMap();

        super.initialize(userId, applicationInfo);
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("order_name", "주문자명");
        map.put("order_code", "주문코드");
        map.put("order_date", "주문일자");

        map.put("site_name", "상점명");
        map.put("site_url", "상점URL");
        map.put("item_name_10", "상품명(10자)");
        map.put("item_name_20", "상품명(20자)");
        map.put("item_name_30", "상품명(30자)");

        map.put("delivery_company_name", "택배사");
        map.put("delivery_number", "송장번호");

        map.put("full_item_name", "상품명");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        HashMap<String, String> map = new HashMap<>();

        map.put("delivery_company_name", this.deliveryCompanyName);
        map.put("delivery_number", this.deliveryNumber);

        map.put("order_code", this.orderCode);
        map.put("order_name", this.orderName);
        map.put("site_name", this.siteName);
        map.put("site_url", this.siteUrl);
        map.put("order_date", this.orderDate);
        map.put("item_name_10", this.itemName10);
        map.put("item_name_20", this.itemName20);
        map.put("item_name_30", this.itemName30);
        map.put("full_item_name", this.fullItemName);

        addCodeMap(map);

    }
}
