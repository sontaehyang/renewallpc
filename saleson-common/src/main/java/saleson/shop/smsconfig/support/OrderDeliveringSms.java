package saleson.shop.smsconfig.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ItemUtils;
import saleson.shop.config.domain.Config;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShippingInfo;
import saleson.shop.smsconfig.domain.SmsConfig;

import java.util.HashMap;

public class OrderDeliveringSms extends SmsTemplate {
	private String orderName;
	private String orderCode;
	private String orderDate;

	private String siteName;
	private String siteUrl;
	
	private String itemName10;
	private String itemName20;
	private String itemName30;
	private String fullItemName;
	private String deliveryNumber;
	
	public HashMap<String, String> getMap() {
		HashMap<String, String> map = new HashMap<>();

		map.put("orderName", "주문자명");
		map.put("orderCode", "주문코드");
		map.put("orderDate", "주문일자");

		map.put("siteName", "상점명");
		map.put("siteUrl", "상점URL");
		map.put("itemName10", "상품명(10자)");
		map.put("itemName20", "상품명(20자)");
		map.put("itemName30", "상품명(30자)");
		map.put("delivery_number", "송장번호");

		map.put("fullItemName", "상품명");
		return map;
	}
	
	public OrderDeliveringSms() {
		this.setMap(this.getMap());
	}
	
	public OrderDeliveringSms(Order order, long userId, SmsConfig smsConfig, Config config) {
		super(smsConfig);
		this.init(order, userId, config);
	}
	
	private void init(Order order, long userId, Config config) {
		if (order== null) {
			return;
		}
		
		// 주문정보
		this.orderCode = order.getOrderCode();
		this.orderDate = DateUtils.date(order.getCreatedDate());
		this.orderName = order.getUserName();
		
		this.siteName = config.getShopName();
		this.siteUrl = SalesonProperty.getSalesonUrlShoppingmall();
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
			
			if (StringUtils.isNotEmpty(itemName)) {
				this.itemName10 = ItemUtils.getSubstringItemName(itemName, 10, itemCount, false);
				this.itemName20 = ItemUtils.getSubstringItemName(itemName, 20, itemCount, false);
				this.itemName30 = ItemUtils.getSubstringItemName(itemName, 30, itemCount, false);

				this.fullItemName = ItemUtils.getSubstringItemName(itemName, itemName.length(), itemCount, true);
			}
		}
		
		HashMap<String, String> map = this.setMailInfo();
		this.setMap(map);
	}
	
	private HashMap<String, String> setMailInfo(){
		
		HashMap<String, String> map = new HashMap<>();
		
		map.put("deliveryNumber", this.deliveryNumber);
		map.put("orderCode", this.orderCode);
		map.put("orderName", this.orderName);
		map.put("siteName", this.siteName);
		map.put("siteUrl", this.siteUrl);
		map.put("orderDate", this.orderDate);
		map.put("itemName10", this.itemName10);
		map.put("itemName20", this.itemName20);
		map.put("itemName30", this.itemName30);
		map.put("fullItemName", this.fullItemName);
		return map;
	
	}
}
