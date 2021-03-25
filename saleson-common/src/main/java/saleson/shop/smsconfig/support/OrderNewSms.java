package saleson.shop.smsconfig.support;

import com.onlinepowers.framework.util.DateUtils;
import saleson.common.config.SalesonProperty;
import saleson.shop.config.domain.Config;
import saleson.shop.order.domain.Buy;
import saleson.shop.order.domain.Buyer;
import saleson.shop.order.domain.Order;
import saleson.shop.smsconfig.domain.SmsConfig;

import java.util.HashMap;

public class OrderNewSms extends SmsTemplate {
	private String orderName;
	private String orderCode;
	private String orderDate;

	private String siteName;
	private String siteUrl;
	
	public HashMap<String, String> getMap() {
		HashMap<String, String> map = new HashMap<>();

		map.put("orderName", "주문자명");
		map.put("orderCode", "주문코드");
		map.put("orderDate", "주문일자");

		map.put("siteName", "상점명");
		map.put("siteUrl", "상점URL");

		return map;
	}
	
	public OrderNewSms() {
		this.setMap(this.getMap());
	}
	
	public OrderNewSms(Order order, long userId, SmsConfig smsConfig, Config config) {
		super(smsConfig);
		
		if (order== null) {
			return;
		}
		
		// 주문정보
		this.orderCode = order.getOrderCode();
		this.orderDate = DateUtils.date(order.getCreatedDate());
		this.orderName = order.getUserName();
		
		this.siteName = config.getShopName();
		this.siteUrl = SalesonProperty.getSalesonUrlShoppingmall();
		
		HashMap<String, String> map = this.setMailInfo();
		this.setMap(map);
	}
	
	public OrderNewSms(Buy buy, long userId, SmsConfig smsConfig, Config config) {
		super(smsConfig);
		
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
		
		HashMap<String, String> map = this.setMailInfo();
		this.setMap(map);
	}
	
	private HashMap<String, String> setMailInfo(){
		
		HashMap<String, String> map = new HashMap<>();
		
		map.put("orderCode", this.orderCode);
		map.put("orderName", this.orderName);
		map.put("siteName", this.siteName);
		map.put("siteUrl", this.siteUrl);
		map.put("orderDate", this.orderDate);
		return map;
	
	}
}
