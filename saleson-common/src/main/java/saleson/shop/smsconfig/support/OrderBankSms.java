package saleson.shop.smsconfig.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import saleson.common.config.SalesonProperty;
import saleson.shop.config.domain.Config;
import saleson.shop.order.domain.*;
import saleson.shop.smsconfig.domain.SmsConfig;

import java.util.HashMap;

public class OrderBankSms extends SmsTemplate {
	private String orderName;
	private String orderCode;
	private String orderDate;

	private String siteName;
	private String siteUrl;
	
	private String bankVirtualNo;
	private String bankAmount;
	private String bankDate;
	
	public HashMap<String, String> getMap() {
		HashMap<String, String> map = new HashMap<>();

		map.put("orderName", "주문자명");
		map.put("orderCode", "주문코드");
		map.put("orderDate", "주문일자");

		map.put("siteName", "상점명");
		map.put("siteUrl", "상점URL");

		map.put("bankAmount", "입금요청액");
		map.put("bankVirtualNo", "가상계좌번호");
		map.put("bankDate", "입금기한");
		
		return map;
	}
	
	public OrderBankSms() {
		this.setMap(this.getMap());
	}
	
	public OrderBankSms(Order order, OrderPayment orderPayment, long userId, SmsConfig smsConfig, Config config) {
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
		
		this.bankAmount = StringUtils.numberFormat(orderPayment.getAmount());
		this.bankVirtualNo = orderPayment.getBankVirtualNo();
		this.bankDate = orderPayment.getBankDate();
		
		HashMap<String, String> map = this.setMailInfo();
		this.setMap(map);
	}
	
	public OrderBankSms(Buy buy, BuyPayment buyPayment, long userId, SmsConfig smsConfig, Config config) {
		super(smsConfig);
		if (buy == null) {
			return;
		}
		
		Buyer buyer = buy.getBuyer();
		
		// 주문정보
		this.orderCode = buy.getOrderCode();
		this.orderDate = DateUtils.getToday("yyyy-MMdd");
		this.orderName = buyer.getUserName();
		
		this.siteName = config.getShopName();
		this.siteUrl = SalesonProperty.getSalesonUrlShoppingmall();
		
		this.bankAmount = StringUtils.numberFormat(buyPayment.getAmount());
		this.bankVirtualNo = buyPayment.getBankVirtualNo();
		this.bankDate = buyPayment.getBankExpirationDate();
		
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
		map.put("bankVirtualNo", this.bankVirtualNo);
		map.put("bankAmount", this.bankAmount);
		map.put("bankDate", this.bankDate);
		
		return map;
	
	}

}
