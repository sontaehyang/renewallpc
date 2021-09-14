package saleson.shop.order.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import saleson.common.enumeration.DeliveryMethodType;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.Cashbill;
import saleson.shop.config.domain.Config;
import saleson.shop.userdelivery.domain.UserDelivery;
import saleson.shop.userlevel.domain.UserLevel;

import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.ValidationUtils;

public class Buy {

    private Cashbill cashbill;

    public Cashbill getCashbill() {
        return cashbill;
    }

    public void setCashbill(Cashbill cashbill) {
        this.cashbill = cashbill;
    }
	
	private String sellerNames;
	public String getSellerNames() {
		return sellerNames;
	}

	public void setSellerNames(String sellerNames) {
		this.sellerNames = sellerNames;
	}

	private boolean isAdditionItem;
	public boolean isAdditionItem() {
		return isAdditionItem;
	}

	public void setAdditionItem(boolean isAdditionItem) {
		this.isAdditionItem = isAdditionItem;
	}

	private String saveDeliveryFlag;
	private String saveDeliveryName;
	public String getSaveDeliveryName() {
		return saveDeliveryName;
	}

	public void setSaveDeliveryName(String saveDeliveryName) {
		this.saveDeliveryName = saveDeliveryName;
	}

	public String getSaveDeliveryFlag() {
		return saveDeliveryFlag;
	}

	public void setSaveDeliveryFlag(String saveDeliveryFlag) {
		this.saveDeliveryFlag = saveDeliveryFlag;
	}
	
	private int pgPayAmount;
	
	public int getPgPayAmount() {
		return pgPayAmount;
	}

	public void setPgPayAmount(int pgPayAmount) {
		this.pgPayAmount = pgPayAmount;
	}

	private String[] paymentPriority = new String[]{"bank", "card", "vbank", "escrow", "realtimebank", "hp", "kakaopay", "naverpay"};
	private String[] notMixPayType = new String[]{"card", "vbank", "realtimebank", "payco", "bank", "escrow", "kakaopay", "naverpay"};
	
	public Buy() {
		// 현금영수증 신청정보가 없는경우 초기화함
        if (ValidationUtils.isNull(this.cashbill)) {
            this.cashbill = new Cashbill();
        }
	}
	
	public String getDefaultPaymentType() {
		
		if (buyPayments == null) {
			return "";
		}
		
		for(String s : paymentPriority) {
			if (buyPayments.get(s) != null) {
				return s;
			}
		}
		
		return "";
	}

	public String[] getNotMixPayType() {
		return CommonUtils.copy(notMixPayType);
	}

	public void setNotMixPayType(String[] notMixPayType) {
		this.notMixPayType = CommonUtils.copy(notMixPayType);
	}

	public String[] getPaymentPriority() {
		return CommonUtils.copy(paymentPriority);
	}
	public void setPaymentPriority(String[] paymentPriority) {
		this.paymentPriority = CommonUtils.copy(paymentPriority);
	}

	private List<BuyPayment> payments;
	public List<BuyPayment> getPayments() {
		return payments;
	}
	public void setPayments(List<BuyPayment> payments) {
		this.payments = payments;
	}

	private HashMap<String, BuyPayment> buyPayments;
	public HashMap<String, BuyPayment> getBuyPayments() {
		return buyPayments;
	}
	public void setBuyPayments(HashMap<String, BuyPayment> buyPayments) {
		this.buyPayments = buyPayments;
	}

	// 밭는사람(배송지) 정보
	private List<Receiver> receivers;
	public List<Receiver> getReceivers() {
		return receivers;
	}
	public void setReceivers(List<Receiver> receivers) {
		this.receivers = receivers;
	}

	public List<BuyItem> getItems() {
		if (this.receivers == null) {
			return null;
		}
		
		List<BuyItem> items = new ArrayList<>();
		for(Receiver receiver : this.receivers) {
			items.addAll(receiver.getItems());
		}
		
		return items;
	}
	
	// 주문 번호
	private String orderCode;
	
	// 주문 상태
	private String orderStatus;
	
	// 구매자 고유키
	private long userId;
	private String sessionId;
	public boolean getIsLogin() {
		if (this.userId > 0) {
			return true;
		}
		
		return UserUtils.isUserLogin();
	}

	// 결제 금액 정보
	private OrderPrice orderPrice;
	
	// 구매자 정보
	private Buyer buyer;
	
	// 구매자 기본 배송지
	private UserDelivery defaultUserDelivery;
	
	// 사용가능 포인트
	private int retentionPoint;

	// 사용가능 배송비 쿠폰
	private int shippingCoupon;
	public int getShippingCoupon() {
		return shippingCoupon;
	}
	public void setShippingCoupon(int shippingCoupon) {
		this.shippingCoupon = shippingCoupon;
	}
	private HashMap<String, ShippingCoupon> useShippingCoupon;
	public HashMap<String, ShippingCoupon> getUseShippingCoupon() {
		return useShippingCoupon;
	}

	public void setUseShippingCoupon(
			HashMap<String, ShippingCoupon> useShippingCoupon) {
		this.useShippingCoupon = useShippingCoupon;
	}
	
	private String content;
	
	// 배송 요청일
	private String deliveryReqDay;
	private String deliveryReqHour;

	// 구매 불가 상품들
	private List<BuyItem> impossibleToPurchaseProducts;
	
	// 쿠폰 사용 input 만들기 - step1에서 사용
	private List<String> makeUseCouponKeys;
	
	// 사용 쿠폰 POST 데이터 - step1에서 사용된 데이터
	private List<String> useCouponKeys;
	
	// 재고 차감 목
	private HashMap<String, Integer> stockMap;
	
	private String deviceType = "WEB";		// PC / SP 기준 
	
	private String realDeviceType;;				// 장비기준 (normal, mobile, tablet)
	
	private String defaultBuyerCheck = "0";  // 주문자 정보 기본설정 flag 2017-05-18 yulsun.yoo

    private String cashbillType; // 영수증 타입

    private Map<String, Object> cashbillInfo;	// API용_현금영수증 정보

	private String userIp;

	private String failUrl;
	private String successUrl;

	private String campaignCode;

	private String createdDate;

	// 배송 방법 (일반택배, 퀵서비스, 방문수령)
	private DeliveryMethodType deliveryMethodType;

	public DeliveryMethodType getDeliveryMethodType() {
		return deliveryMethodType;
	}
	public void setDeliveryMethodType(DeliveryMethodType deliveryMethodType) {
		this.deliveryMethodType = deliveryMethodType;
	}

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCashbillType() {
        return cashbillType;
    }

    public void setCashbillType(String cashbillType) {
        this.cashbillType = cashbillType;
    }

    public Map<String, Object> getCashbillInfo() {
        return cashbillInfo;
    }

    public void setCashbillInfo(Map<String, Object> cashbillInfo) {
        this.cashbillInfo = cashbillInfo;
    }

    public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	public String getRealDeviceType() {
		return realDeviceType;
	}

	public void setRealDeviceType(String realDeviceType) {
		this.realDeviceType = realDeviceType;
	}

	public HashMap<String, Integer> getStockMap() {
		return stockMap;
	}

	public void setStockMap(HashMap<String, Integer> stockMap) {
		this.stockMap = stockMap;
	}
	
	
	
	/**
	 * Javascript에서 사용할 데이터
	 * @return
	 */
	public String getUserData() {
		
		if (this.receivers.isEmpty()) {
			return "";
		}
		
		Config shopConfig = ShopUtils.getConfig();
		
		HashMap<String, Object> buy = new HashMap<>();
		
		List<Object> receiverList = new ArrayList<>();
		for(Receiver receiver : this.receivers) {
			HashMap<String, Object> receiverMap = new HashMap<>();
			
			receiverMap.put("shippingIndex", receiver.getShippingIndex());
			receiverMap.put("zipcode", receiver.getFullReceiveZipcode());
			
			List<Object> shippingList = new ArrayList<>();
			
			if (receiver.getItemGroups() == null) {
				break;
			}
			
			for(Shipping shipping : receiver.getItemGroups()) {
				HashMap<String, Object> shippingMap = new HashMap<>();
				shippingMap.put("shippingSequence", shipping.getShippingSequence());
				shippingMap.put("shippingType", shipping.getShippingType());
				shippingMap.put("shipping", shipping.getShipping());
				shippingMap.put("shippingExtraCharge1", shipping.getShippingExtraCharge1());
				shippingMap.put("shippingExtraCharge2", shipping.getShippingExtraCharge2());
				shippingMap.put("shippingReturn", shipping.getShippingReturn());
				shippingMap.put("shippingFreeAmount", shipping.getShippingFreeAmount());
				shippingMap.put("realShipping", shipping.getRealShipping());
				shippingMap.put("shippingPaymentType", shipping.getShippingPaymentType());
				shippingMap.put("shippingItemCount", shipping.getShippingItemCount());
				shippingMap.put("shipmentGroupCode", shipping.getShipmentGroupCode());
				shippingMap.put("singleShipping", shipping.isSingleShipping());
				shippingMap.put("islandType", shipping.getIslandType());
				
				if (shipping.isSingleShipping()) {

					HashMap<String, Object> item = new HashMap<>();
					BuyItem buyItem = shipping.getBuyItem();
					setItemPriceForUserData(shopConfig, shipping, item, buyItem);

					shippingMap.put("buyItem", item);

				} else {
					List<Object> buyItemList = new ArrayList<>();
					for(BuyItem buyItem : shipping.getBuyItems()) {
						
						HashMap<String, Object> item = new HashMap<>();
						setItemPriceForUserData(shopConfig, shipping, item, buyItem);

						buyItemList.add(item);
						
					}
					
					shippingMap.put("buyItems", buyItemList);
				}
				
				shippingList.add(shippingMap);
			}
			
			receiverMap.put("shippingList", shippingList);
			receiverList.add(receiverMap);
		}

		buy.put("buyPayments", this.getBuyPayments());
		buy.put("retentionPoint", this.getRetentionPoint());
		buy.put("shippingCoupon", this.getShippingCoupon());
		buy.put("shippingCouponAmount", 0);
		
		buy.put("totalItemAmountBeforeDiscounts", this.orderPrice.getTotalItemAmountBeforeDiscounts());

		buy.put("totalItemPrice", this.orderPrice.getTotalItemPrice());
		buy.put("totalItemSaleAmount", this.orderPrice.getTotalItemSaleAmount());
		buy.put("totalDiscountAmount", this.orderPrice.getTotalDiscountAmount());
		buy.put("totalItemDiscountAmount", this.orderPrice.getTotalItemDiscountAmount());
		buy.put("totalUserLevelDiscountAmount", this.orderPrice.getTotalUserLevelDiscountAmount());

		buy.put("totalExcisePrice", this.orderPrice.getTotalExcisePrice());
		buy.put("totalItemPayAmount", this.orderPrice.getTotalItemPayAmount());
		buy.put("totalShippingAmount", this.orderPrice.getTotalShippingAmount());
		buy.put("totalItemCouponDiscountAmount", this.orderPrice.getTotalItemCouponDiscountAmount());
		buy.put("totalCartCouponDiscountAmount", this.orderPrice.getTotalCartCouponDiscountAmount());
		buy.put("totalPointDiscountAmount", this.orderPrice.getTotalPointDiscountAmount());
		buy.put("totalShippingCouponUseCount", 0);
		buy.put("totalShippingCouponDiscountAmount", 0);
		
		buy.put("isPointApplyCouponDiscount", shopConfig.isPointApplyCouponDiscount());
		buy.put("totalEarnPoint", this.orderPrice.getTotalEarnPoint());
		
		buy.put("orderPayAmount", this.orderPrice.getOrderPayAmount());
		buy.put("receivers", receiverList);
		buy.put("notMixPayType", this.getNotMixPayType());
		return JsonViewUtils.objectToJson(buy);
	}

	private void setItemPriceForUserData(Config shopConfig, Shipping shipping, HashMap<String, Object> item, BuyItem buyItem) {
		ItemPrice itemPrice = buyItem.getItemPrice();

		item.put("itemSequence", buyItem.getItemSequence());
		item.put("couponDiscountPrice", itemPrice.getCouponDiscountPrice());
		item.put("couponDiscountAmount", itemPrice.getCouponDiscountAmount());
		item.put("quantity", itemPrice.getQuantity());
		item.put("beforeDiscountAmount", itemPrice.getBeforeDiscountAmount());

		item.put("isPointApplyCouponDiscount", shopConfig.isPointApplyCouponDiscount());
		if (ValidationUtils.isNull(buyItem.getPointPolicy()) == false) {
			item.put("pointType", buyItem.getPointPolicy().getPointType());
			item.put("point", buyItem.getPointPolicy().getPoint());
		} else {
			item.put("pointType", "1");
			item.put("point", 0);
		}

		UserLevel userLevel = buyItem.getUserLevel();
		if (ValidationUtils.isNull(userLevel) == false) {
			item.put("userLevelPointRate", userLevel.getPointRate());
		} else {
			item.put("userLevelPointRate", 0);
		}

		item.put("baseAmountForShipping", itemPrice.getBaseAmountForShipping());
		item.put("discountAmount", itemPrice.getDiscountAmount());
		item.put("itemDiscountAmount", itemPrice.getItemDiscountAmount());
		item.put("userLevelDiscountAmount", itemPrice.getUserLevelDiscountAmount());

		item.put("saleAmount", itemPrice.getSaleAmount());
		item.put("sumPrice", itemPrice.getSumPrice());
		item.put("itemName", buyItem.getItem().getItemName());
		item.put("itemUserCode", buyItem.getItem().getItemUserCode());
		item.put("optionText", ShopUtils.viewOptionText(buyItem.getOptions()));
		item.put("itemImageSrc", buyItem.getItem().getImageSrc());
		item.put("shipmentGroupCode", shipping.getShipmentGroupCode());
		item.put("additionItemList", buyItem.getAdditionItemList());
	}

	public UserDelivery getDefaultUserDelivery() {
		return defaultUserDelivery;
	}
	public void setDefaultUserDelivery(UserDelivery defaultUserDelivery) {
		this.defaultUserDelivery = defaultUserDelivery;
	}
	
	public List<String> getUseCouponKeys() {
		return useCouponKeys;
	}

	public void setUseCouponKeys(List<String> useCouponKeys) {
		this.useCouponKeys = useCouponKeys;
	}

	public List<String> getMakeUseCouponKeys() {
		return makeUseCouponKeys;
	}

	public void setMakeUseCouponKeys(List<String> makeUseCouponKeys) {
		this.makeUseCouponKeys = makeUseCouponKeys;
	}

	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDeliveryReqDay() {
		return deliveryReqDay;
	}
	public void setDeliveryReqDay(String deliveryReqDay) {
		this.deliveryReqDay = deliveryReqDay;
	}
	public String getDeliveryReqHour() {
		return deliveryReqHour;
	}
	public void setDeliveryReqHour(String deliveryReqHour) {
		this.deliveryReqHour = deliveryReqHour;
	}

	public List<BuyItem> getImpossibleToPurchaseProducts() {
		return impossibleToPurchaseProducts;
	}
	public void setImpossibleToPurchaseProducts(
			List<BuyItem> impossibleToPurchaseProducts) {
		this.impossibleToPurchaseProducts = impossibleToPurchaseProducts;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public int getRetentionPoint() {
		return retentionPoint;
	}
	public void setRetentionPoint(int retentionPoint) {
		this.retentionPoint = retentionPoint;
	}
	public OrderPrice getOrderPrice() {
		return orderPrice;
	}
	
	public void setOrderPrice(int bankPayAmount, Config shopConfig) {
		
		if (this.orderPrice == null) {
			this.orderPrice = new OrderPrice();
		}
		
		this.orderPrice.setItems(this.receivers, bankPayAmount, shopConfig);
		
	}
	
	public void setOrderPrice(OrderPrice orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	public int getTotalItemCount() {
		
		List<BuyItem> items = this.getItems();
		if (items == null) {
			return 0;
		}
		
		return items.size();
	}
	
	public int getMultipleDeliveryCount() {
		
		int count = 0;
		List<BuyItem> items = this.getItems();
		if (items != null) {
			for(BuyItem buyItem : items) {
				count += buyItem.getItemPrice().getQuantity();
			}
		}
		
		return count;
	}
	
	public int getMaxMultipleDelivery() {
		return 10;
	}

	public String getDefaultBuyerCheck() {
		return defaultBuyerCheck;
	}

	public void setDefaultBuyerCheck(String defaultBuyerCheck) {
		this.defaultBuyerCheck = defaultBuyerCheck;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getFailUrl() {
		return failUrl;
	}

	public void setFailUrl(String failUrl) {
		this.failUrl = failUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}


	// 렌탈페이용 파라미터
	public String buyRentalPay = "N";
	public String rentalTotAmt;
	public String rentalMonthAmt;
	public String rentalPartnershipAmt;
	public String rentalPer;

	public String getBuyRentalPay() {
		return buyRentalPay;
	}
	public void setBuyRentalPay(String buyRentalPay) {
		this.buyRentalPay = buyRentalPay;
	}
	public String getRentalTotAmt() {
		return rentalTotAmt;
	}
	public void setRentalTotAmt(String rentalTotAmt) {
		this.rentalTotAmt = rentalTotAmt;
	}
	public String getRentalMonthAmt() {
		return rentalMonthAmt;
	}
	public void setRentalMonthAmt(String rentalMonthAmt) {
		this.rentalMonthAmt = rentalMonthAmt;
	}
	public String getRentalPartnershipAmt() {
		return rentalPartnershipAmt;
	}
	public void setRentalPartnershipAmt(String rentalPartnershipAmt) {
		this.rentalPartnershipAmt = rentalPartnershipAmt;
	}
	public String getRentalPer() {
		return rentalPer;
	}
	public void setRentalPer(String rentalPer) {
		this.rentalPer = rentalPer;
	}
}
