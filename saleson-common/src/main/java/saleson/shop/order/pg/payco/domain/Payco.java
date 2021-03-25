package saleson.shop.order.pg.payco.domain;

import org.springframework.core.env.Environment;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;
import saleson.shop.order.domain.Buy;
import saleson.shop.order.domain.BuyItem;
import saleson.shop.order.domain.OrderPrice;
import saleson.shop.order.domain.Receiver;

import java.util.ArrayList;
import java.util.List;

public class Payco {
	
	//가맹점 코드
	private String sellerKey;
	//주문번호
	private String sellerOrderReferenceKey;
	//배송비
	private int totalDeliveryFeeAmt;
	//배송비가 포함된 총 결제할 금액
	private int totalPaymentAmt;
	//무통장입금완료통보 URL
	private String nonBankbookDepositInformUrl;
	//결제모드 PAY2타입(결제인증, 승인분리)
	private String payMode;
	//주문완료 후 Redirect 되는 Url
	private String returnUrl;
	
	//주문유형(=결제유형)
	//• 바로구매형 : CHECKOUT
	//• 간편결제형+가맹점id 로그인 : EASYPAY_F
	//• 간편결제형+가맹점id 비로그인(PAYCO회구매) : EASYPAY
	private String orderMethod;
	//주문상품LIST
	private List<BuyProduct> orderProducts;

	public Payco(Buy buy, Environment environment) {
		
		OrderPrice orderPrice = buy.getOrderPrice();
		
		this.sellerKey = environment.getProperty("payco.seller.key");
		this.payMode = "PAY2";
		this.returnUrl = SalesonProperty.getSalesonUrlShoppingmall();
		if (ShopUtils.isMobilePage()) {
			this.returnUrl +=  ShopUtils.getMobilePrefix();
		}
		this.returnUrl +=  "/order/payco/return-url";
		
		this.nonBankbookDepositInformUrl = SalesonProperty.getSalesonUrlShoppingmall() + "/order/payco/note-url";
		this.orderMethod = "EASYPAY_F"; // EASYPAY_F : 회원, EASYPAY : 비회원
		
		this.sellerOrderReferenceKey = buy.getOrderCode();
		this.totalDeliveryFeeAmt = orderPrice.getTotalShippingAmount();
		this.totalPaymentAmt = buy.getPgPayAmount();
		
		if (orderProducts == null) {
			orderProducts = new ArrayList<>();
		}
		
		String cpId = environment.getProperty("payco.seller.cpId");
		String productId = "PROD_EASY";
		
		int i = 0;
		for(Receiver receiver : buy.getReceivers()) {
			for(BuyItem buyItem : receiver.getItems()) {
				orderProducts.add(new BuyProduct(buyItem, cpId, productId, i++));
			}
		}
		
		// 배송비를 상품으로 추가 해야됨
		if (orderPrice.getTotalShippingAmount() > 0) {
			orderProducts.add(new BuyProduct(cpId, productId, orderPrice.getTotalShippingAmount(), i));
		}
		
	}
	
	public String getSellerKey() {
		return sellerKey;
	}
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}
	public String getSellerOrderReferenceKey() {
		return sellerOrderReferenceKey;
	}
	public void setSellerOrderReferenceKey(String sellerOrderReferenceKey) {
		this.sellerOrderReferenceKey = sellerOrderReferenceKey;
	}
	public int getTotalDeliveryFeeAmt() {
		return totalDeliveryFeeAmt;
	}
	public void setTotalDeliveryFeeAmt(int totalDeliveryFeeAmt) {
		this.totalDeliveryFeeAmt = totalDeliveryFeeAmt;
	}
	public int getTotalPaymentAmt() {
		return totalPaymentAmt;
	}
	public void setTotalPaymentAmt(int totalPaymentAmt) {
		this.totalPaymentAmt = totalPaymentAmt;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getOrderMethod() {
		return orderMethod;
	}
	public void setOrderMethod(String orderMethod) {
		this.orderMethod = orderMethod;
	}
	public List<BuyProduct> getOrderProducts() {
		return orderProducts;
	}
	public void setOrderProducts(List<BuyProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public String getNonBankbookDepositInformUrl() {
		return nonBankbookDepositInformUrl;
	}

	public void setNonBankbookDepositInformUrl(String nonBankbookDepositInformUrl) {
		this.nonBankbookDepositInformUrl = nonBankbookDepositInformUrl;
	}

}
