package saleson.shop.ums.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.springframework.util.unit.DataUnit;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.config.domain.Config;
import saleson.shop.order.domain.*;
import saleson.common.notification.domain.UmsTemplate;

import java.util.HashMap;

public class OrderBank extends UmsTemplate {

    private String orderCode;
    private String orderName;
    private String orderDate;

    private String siteUrl;
    private String siteName;

    private String bankVirtualNo;
    private String bankAmount;
    private String bankDate;
    private String itemName;

    public OrderBank() {intiCodeMapView();}

    public OrderBank(Ums ums, Order order, OrderPayment orderPayment, long userId, Config config, String phoneNumber, ApplicationInfo applicationInfo) {
        super(ums, phoneNumber);

        if (order== null) {
            return;
        }

        // 주문정보
        this.orderCode = order.getOrderCode();
        this.orderName = order.getUserName();
        this.orderDate = DateUtils.date(order.getCreatedDate());

        this.siteUrl = SalesonProperty.getSalesonUrlShoppingmall();
        this.siteName = config.getShopName();

        this.bankVirtualNo = ShopUtils.unescapeHtml(orderPayment.getBankVirtualNo());
        this.bankAmount = StringUtils.numberFormat(orderPayment.getAmount());
        this.bankDate = orderPayment.getBankDate();

        intiCodeMapView();
        intiCodeMap();

        super.initialize(userId, applicationInfo);
    }

    public OrderBank(Ums ums, Buy buy, BuyPayment buyPayment, long userId, Config config, String phoneNumber, ApplicationInfo applicationInfo) {
        super(ums, phoneNumber);

        if (buy == null) {
            return;
        }

        Buyer buyer = buy.getBuyer();

        // 주문정보
        this.orderCode = buy.getOrderCode();
        this.orderName = buyer.getUserName();
        this.orderDate = DateUtils.getToday("yyyy-MM-dd");

        this.siteUrl = SalesonProperty.getSalesonUrlShoppingmall();
        this.siteName = config.getShopName();

        String productName = buy.getItems().get(0).getItem().getItemName();
        if (buy.getItems().size() > 1) {
            productName += " 외 " + (buy.getItems().size() - 1) + "개";
        }
        this.itemName = productName;

        this.bankVirtualNo = ShopUtils.unescapeHtml(buyPayment.getBankVirtualNo());
        this.bankAmount = StringUtils.numberFormat(buyPayment.getAmount());
        this.bankDate = DateUtils.formatDate(buyPayment.getBankExpirationDate(), "-");

        intiCodeMapView();
        intiCodeMap();

        super.initialize(userId, applicationInfo);
    }

    private void intiCodeMapView() {

        HashMap<String, String> map = new HashMap<>();

        map.put("order_code", "주문코드");
        map.put("user_name", "주문자명");
        map.put("order_date", "주문일자");

        map.put("site_url", "상점URL");
        map.put("site_name", "상점명");

        map.put("item_name", "상품명");

        map.put("bank_virtual_no", "계좌번호");
        map.put("bank_amount", "입금금액");
        map.put("bank_date", "입금기한");

        addCodeViewMap(map);
    }

    private void intiCodeMap() {

        HashMap<String, String> map = new HashMap<>();

        map.put("order_code", this.orderCode);
        map.put("user_name", this.orderName);
        map.put("order_date", this.orderDate);

        map.put("site_url", this.siteUrl);
        map.put("site_name", this.siteName);

        map.put("item_name", this.itemName);

        map.put("bank_virtual_no", this.bankVirtualNo);
        map.put("bank_amount", this.bankAmount);
        map.put("bank_date", this.bankDate);

        addCodeMap(map);

    }
}
