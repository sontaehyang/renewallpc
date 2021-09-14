package saleson.shop.order.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import saleson.common.enumeration.DeliveryMethodType;
import saleson.common.utils.ShopUtils;
import saleson.model.GiftItem;
import saleson.shop.cart.domain.OrderQuantity;
import saleson.shop.coupon.domain.CouponItem;
import saleson.shop.coupon.domain.OrderCoupon;
import saleson.shop.giftitem.domain.GiftItemInfo;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemOption;
import saleson.shop.point.domain.PointPolicy;
import saleson.shop.userlevel.domain.UserLevel;

import com.onlinepowers.framework.util.ValidationUtils;

public class BuyItem implements Cloneable {

	
	@Override
    public Object clone() throws CloneNotSupportedException {
		
		BuyItem cloneObject = (BuyItem) super.clone();
		
		if (ValidationUtils.isNull(itemPrice) == false) {
			cloneObject.setItemPrice((ItemPrice) itemPrice.clone());
		}
		
		// CJH 2016. 10. 27 쿠폰 할인금액 이슈로 상품 쿠폰 정보도 복사해서 새로 생성
		if (itemCoupons != null) {
			
			List<OrderCoupon> newList = new ArrayList<>();
			for(OrderCoupon coupon : itemCoupons) {
				newList.add(copyOrderCoupon(coupon));
			}
			
			cloneObject.setItemCoupons(newList);
		}
		
		if (addItemCoupons != null) {
			
			List<OrderCoupon> newList = new ArrayList<>();
			for(OrderCoupon coupon : addItemCoupons) {
				newList.add(copyOrderCoupon(coupon));
			}
			
			cloneObject.setAddItemCoupons(newList);
		}
		
        return cloneObject;
    }
	
	private UserLevel userLevel; // 회원 등급
	public UserLevel getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}
	
	public void setItemCoupons(List<OrderCoupon> itemCoupons) {
		this.itemCoupons = itemCoupons;
	}

	private Shipping buyShipping;
	public Shipping getBuyShipping() {
		return buyShipping;
	}
	public void setBuyShipping(Shipping buyShipping) {
		this.buyShipping = buyShipping;
	}

	// 장바구니 번호
	private int cartId;
	private String deviceType = "WEB";
	private String sessionId;
	
	
	private int shippingIndex; // 복수 배송지때 사용
	public int getShippingIndex() {
		return shippingIndex;
	}
	public void setShippingIndex(int shippingIndex) {
		this.shippingIndex = shippingIndex;
	}
	
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	private int shippingSequence;
	private int shippingInfoSequence;
	public int getShippingInfoSequence() {
		return shippingInfoSequence;
	}
	public void setShippingInfoSequence(int shippingInfoSequence) {
		this.shippingInfoSequence = shippingInfoSequence;
	}
	public int getShippingSequence() {
		return shippingSequence;
	}
	public void setShippingSequence(int shippingSequence) {
		this.shippingSequence = shippingSequence;
	}

	private long userId;
	private String guestFlag;
	private long sellerId;
	private String sellerName;
	private int categoryTeamId;
	private int categoryGroupId;
	private int categoryId;
	private int shipmentId;
	private int shipmentReturnId;
	private int couponUserId;
	private int addCouponUserId;
	
	private int itemId;
	private String itemCode;
	private String itemUserCode;
	private String itemName;
	private String freeGiftName;
	private String freeGiftItemText;
	private List<GiftItemInfo> freeGiftItemList;
	
	private String brand;
	
	private String options;
	private List<ItemOption> optionList;

	private List<BuyItem> additionItemList;

	private String orderStatus;
	
	private String deliveryType;
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	private int deliveryCompanyId;
	private String deliveryCompanyName;
	private String deliveryNumber;
	
	private String createdDate;
	private String revenueSalesStatus;
	private String salesDate;
	private String salesCancelDate;
	private String deliveryDate;
	private String confirmDate;
	private String returnRequestDate;
	private String returnRequestFinishDate;
	private String exchangeRequestDate;
	private String returnPointFlag = "N";
	
	private String orderItemStatus;
	private String islandType;
	private String payDate;
	private int shippingReturn;
		
	private String additionItemFlag;
	private int parentItemId;
	public int getParentItemId() {
		return parentItemId;
	}
	public void setParentItemId(int parentItemId) {
		this.parentItemId = parentItemId;
	}

	private String parentItemOptions;
	public String getParentItemOptions() {
		return parentItemOptions;
	}
	public void setParentItemOptions(String parentItemOptions) {
		this.parentItemOptions = parentItemOptions;
	}

	private int parentItemSequence;
	public int getParentItemSequence() {
		return parentItemSequence;
	}
	public void setParentItemSequence(int parentItemSequence) {
		this.parentItemSequence = parentItemSequence;
	}
	public int getShippingReturn() {
		return shippingReturn;
	}
	public void setShippingReturn(int shippingReturn) {
		this.shippingReturn = shippingReturn;
	}
	
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getIslandType() {
		return islandType;
	}
	public void setIslandType(String islandType) {
		this.islandType = islandType;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeliveryCompanyName() {
		return deliveryCompanyName;
	}
	public void setDeliveryCompanyName(String deliveryCompanyName) {
		this.deliveryCompanyName = deliveryCompanyName;
	}
	public String getRevenueSalesStatus() {
		return revenueSalesStatus;
	}
	public void setRevenueSalesStatus(String revenueSalesStatus) {
		this.revenueSalesStatus = revenueSalesStatus;
	}
	public String getOrderItemStatus() {
		return orderItemStatus;
	}
	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}
	// 에스크로 상태
	private String escrowStatus;
	
	// 구매시 적립금
	private PointPolicy pointPolicy;
	
	// 상품 정보
	private Item item;
	
	// 구매 수량 정보
	private OrderQuantity orderQuantity;
	
	// 구매 가능 상태 여부(Y: 판매 가능, N: 판매 불가)
	private String availableForSaleFlag= "Y";
	
	// 사용자에게 보여줄 메시지
	private String systemComment;
	
	// 해당 상품에서 사용가능한 쿠폰 - 일반 쿠폰
	private List<OrderCoupon> itemCoupons;
	
	// 해당 상품에서 사용가능한 쿠폰 - 중복 쿠폰
	private List<OrderCoupon> addItemCoupons;
	
	// 상품 가격 정보
	private ItemPrice itemPrice;

	private String campaignCode;

	private String shippingPaymentType = "1";
	private String shipmentGroupCode;

	// 퀵배송 추가요금 설정
	private String quickDeliveryExtraChargeFlag;

	// 배송 방법 (일반택배, 퀵서비스, 방문수령)
	private DeliveryMethodType deliveryMethodType;

	private String erpOriginUnique;

	// ERP UNIQ 생성용
	private int optionIndex;

	private String cashDiscountFlag;

	public int getOptionIndex() {
		return optionIndex;
	}
	public void setOptionIndex(int optionIndex) {
		this.optionIndex = optionIndex;
	}
	public String getErpOriginUnique() {
		return erpOriginUnique;
	}
	public void setErpOriginUnique(String erpOriginUnique) {
		this.erpOriginUnique = erpOriginUnique;
	}
	public String getShipmentGroupCode() {
		return shipmentGroupCode;
	}
	public void setShipmentGroupCode(String shipmentGroupCode) {
		this.shipmentGroupCode = shipmentGroupCode;
	}
	public int getShipmentReturnId() {
		return shipmentReturnId;
	}
	public void setShipmentReturnId(int shipmentReturnId) {
		this.shipmentReturnId = shipmentReturnId;
	}
	public String getFreeGiftName() {
		return freeGiftName;
	}
	public void setFreeGiftName(String freeGiftName) {
		this.freeGiftName = freeGiftName;
	}

	public String getFreeGiftItemText() {
		return freeGiftItemText;
	}

	public void setFreeGiftItemText(String freeGiftItemText) {
		this.freeGiftItemText = freeGiftItemText;
	}

	public List<GiftItemInfo> getFreeGiftItemList() {
		return freeGiftItemList;
	}

	public void setFreeGiftItemList(List<GiftItemInfo> freeGiftItemList) {
		this.freeGiftItemList = freeGiftItemList;
	}

	public String getAdditionItemFlag() {
		return additionItemFlag;
	}
	public void setAdditionItemFlag(String additionItemFlag) {
		this.additionItemFlag = additionItemFlag;
	}
	
	public String getShippingPaymentType() {
		return shippingPaymentType;
	}
	public void setShippingPaymentType(String shippingPaymentType) {
		this.shippingPaymentType = shippingPaymentType;
	}
	
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public List<OrderCoupon> getItemCoupons() {
		return itemCoupons;
	}
	
	public void setItemCoupons(List<OrderCoupon> itemCoupons, boolean isAll) {
		
		if (itemCoupons == null) {
			return;
		}
		
		Item item = this.getItem();
		
		if ("N".equals(item.getCouponUseFlag())) {
			return;
		}
		
		// 스팟할인 상품의 경우 쿠폰을 사용하지 못하게 한다
		//if (item.isSpotItem()) {
		//	return;
		//}
		
		// CJH 2016.11.07 추가구성상품 쿠폰 적용 안됨
		if ("Y".equals(getAdditionItemFlag())) {
			return;
		}
		
		int itemId = item.getItemId();
		for(OrderCoupon itemCoupon : itemCoupons) {
			
			if (isAll == false) {
				boolean isSet = false;
				for(CouponItem cItem : itemCoupon.getCouponItems()) {
					if (itemId == cItem.getItemId()) {
						isSet = true;
					}
					
				}
				
				if (isSet == false) {
					continue;
				}
			}
			
			
			
			int discountPrice = ShopUtils.getCouponDiscountPriceForItemCoupon(itemCoupon, this);
			OrderCoupon coupon = this.newOrderCoupon(itemCoupon, discountPrice);

			if (discountPrice > 0 && coupon != null) {
				if (this.itemCoupons == null) {
					this.itemCoupons = new ArrayList<>();
				} else {
					
					// CJH 2016.4.20 같은쿠폰이 이미 담겨있는경우 무시한다. 같은쿠폰이 여러개 노출됨.
					for(OrderCoupon c : this.itemCoupons) {
						if (coupon.getCouponUserId() == c.getCouponUserId()) {
							coupon = null;
							break;
						}
					}
					
				}
				if (coupon != null) {
					this.itemCoupons.add(coupon);
				}
			}
		}
	}
	
	/**
	 * 사용한 쿠폰정보를 반환
	 * @return
	 */
	public OrderCoupon getUsedCoupon() {
		if (this.couponUserId == 0) {
			return null;
		}
		
		if (this.itemCoupons == null) {
			return null;
		}
		
		for(OrderCoupon coupon : this.itemCoupons) {
			if (coupon.getCouponUserId() == this.couponUserId) {
				return coupon;
			}
		}
		
		return null;
	}
	
	/**
	 * 사용한 쿠폰정보를 반환
	 * @return
	 */
	public OrderCoupon getUsedAddCoupon() {
		
		if (this.addCouponUserId == 0) {
			return null;
		}
		
		if (this.addItemCoupons == null) {
			return null;
		}
		
		for(OrderCoupon coupon : this.addItemCoupons) {
			if (coupon.getCouponUserId() == this.addCouponUserId) {
				return coupon;
			}
		}
		
		return null;
	}
	
	/**
	 * Post로 전송된 사용 쿠폰 적용
	 * @param useCouponKeys
	 */
	public void setCouponUserId(List<String> useCouponKeys, int shippingIndex) {
		if (useCouponKeys == null) {
			return;
		}

		for(String coupon : useCouponKeys) {
			if (coupon.startsWith("item-coupon")) {
				String[] tmp = StringUtils.delimitedListToStringArray(coupon, "-");
				if (tmp.length == 5) {
					int couponUserId = Integer.parseInt(tmp[2]);
					int itemSequence = Integer.parseInt(tmp[3]);
					int sIndex = Integer.parseInt(tmp[4]);

					if (this.getItemSequence() == itemSequence && shippingIndex == sIndex) {
						this.setCouponUserId(couponUserId);
						break;
					}
				}
			}
		}
		
		for(String coupon : useCouponKeys) {
			if (coupon.startsWith("add-item-coupon")) {
				String[] tmp = StringUtils.delimitedListToStringArray(coupon, "-");
				if (tmp.length == 6) {
					int couponUserId = Integer.parseInt(tmp[3]);
					int itemSequence = Integer.parseInt(tmp[4]);
					int sIndex = Integer.parseInt(tmp[5]);

					if (this.getItemSequence() == itemSequence && shippingIndex == sIndex) {
						this.setAddCouponUserId(couponUserId);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param orderCoupon
	 * @return
	 */
	private OrderCoupon copyOrderCoupon(OrderCoupon orderCoupon) {
		OrderCoupon r = new OrderCoupon();
		r.setCouponUserId(orderCoupon.getCouponUserId());
		r.setCouponName(orderCoupon.getCouponName());
		r.setCouponComment(orderCoupon.getCouponComment());
		r.setCouponApplyStartDate(orderCoupon.getCouponApplyStartDate());
		r.setCouponApplyEndDate(orderCoupon.getCouponApplyEndDate());
		
		r.setCouponPayRestriction(orderCoupon.getCouponPayRestriction());
		r.setCouponConcurrently(orderCoupon.getCouponConcurrently());
		r.setCouponPay(orderCoupon.getCouponPay());
		r.setCouponPayType(orderCoupon.getCouponPayType());
		r.setUserId(orderCoupon.getUserId());
		r.setDiscountPrice(orderCoupon.getDiscountPrice());
		
		r.setDataStatusCode(orderCoupon.getDataStatusCode());
		r.setOrderCode(orderCoupon.getOrderCode());
		
		r.setCouponDiscountLimitPrice(orderCoupon.getCouponDiscountLimitPrice());
		r.setDiscountAmount(orderCoupon.getDiscountAmount());
		return r;
	}
	
	/**
	 * 쿠폰 새로 생성
	 * @param orderCoupon
	 * @param discountAmount
	 * @param couponUserId
	 * @param itemId
	 * @return
	 */
	private OrderCoupon newOrderCoupon(OrderCoupon orderCoupon, int discountPrice) {
		OrderCoupon r = null;
		if (orderCoupon == null) {
			return null;
		}
		
		r = new OrderCoupon();
		r.setCouponUserId(orderCoupon.getCouponUserId());
		r.setCouponName(orderCoupon.getCouponName());
		r.setCouponComment(orderCoupon.getCouponComment());
		r.setCouponApplyStartDate(orderCoupon.getCouponApplyStartDate());
		r.setCouponApplyEndDate(orderCoupon.getCouponApplyEndDate());
		
		r.setCouponPayRestriction(orderCoupon.getCouponPayRestriction());
		r.setCouponConcurrently(orderCoupon.getCouponConcurrently());
		r.setCouponPay(orderCoupon.getCouponPay());
		r.setCouponPayType(orderCoupon.getCouponPayType());
		r.setUserId(orderCoupon.getUserId());
		r.setDiscountPrice(discountPrice);
		
		int discountAmount = discountPrice;
		
		// 구매 수량만큼 중복 할인 쿠폰
		if ("2".equals(orderCoupon.getCouponConcurrently())) {
			discountAmount = discountPrice * getItemPrice().getQuantity();
		}
		
		r.setDataStatusCode(orderCoupon.getDataStatusCode());
		r.setOrderCode(orderCoupon.getOrderCode());
		
		r.setCouponDiscountLimitPrice(orderCoupon.getCouponDiscountLimitPrice());
		r.setDiscountAmount(discountAmount);
		
		return r;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public PointPolicy getPointPolicy() {
		return pointPolicy;
	}
	public void setPointPolicy(PointPolicy pointPolicy) {
		this.pointPolicy = pointPolicy;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public List<ItemOption> getOptionList() {
		return optionList;
	}
	public void setOptionList(List<ItemOption> optionList) {
		this.optionList = optionList;
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public ItemPrice getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(ItemPrice itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getSystemComment() {
		return systemComment;
	}
	public void setSystemComment(String systemComment) {
		this.systemComment = systemComment;
	}
	public String getAvailableForSaleFlag() {
		return availableForSaleFlag;
	}
	public void setAvailableForSaleFlag(String availableForSaleFlag) {
		this.availableForSaleFlag = availableForSaleFlag;
	}
	public OrderQuantity getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(OrderQuantity orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getGuestFlag() {
		return guestFlag;
	}
	public void setGuestFlag(String guestFlag) {
		this.guestFlag = guestFlag;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public int getCategoryTeamId() {
		return categoryTeamId;
	}
	public void setCategoryTeamId(int categoryTeamId) {
		this.categoryTeamId = categoryTeamId;
	}
	public int getCategoryGroupId() {
		return categoryGroupId;
	}
	public void setCategoryGroupId(int categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(int shipmentId) {
		this.shipmentId = shipmentId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getCouponUserId() {
		return couponUserId;
	}
	public void setCouponUserId(int couponUserId) {
		this.couponUserId = couponUserId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getDeliveryCompanyId() {
		return deliveryCompanyId;
	}
	public void setDeliveryCompanyId(int deliveryCompanyId) {
		this.deliveryCompanyId = deliveryCompanyId;
	}
	public String getDeliveryNumber() {
		return deliveryNumber;
	}
	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	public String getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
	public String getSalesCancelDate() {
		return salesCancelDate;
	}
	public void setSalesCancelDate(String salesCancelDate) {
		this.salesCancelDate = salesCancelDate;
	}
	public String getReturnRequestDate() {
		return returnRequestDate;
	}
	public void setReturnRequestDate(String returnRequestDate) {
		this.returnRequestDate = returnRequestDate;
	}
	public String getReturnRequestFinishDate() {
		return returnRequestFinishDate;
	}
	public void setReturnRequestFinishDate(String returnRequestFinishDate) {
		this.returnRequestFinishDate = returnRequestFinishDate;
	}
	public String getExchangeRequestDate() {
		return exchangeRequestDate;
	}
	public void setExchangeRequestDate(String exchangeRequestDate) {
		this.exchangeRequestDate = exchangeRequestDate;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getReturnPointFlag() {
		return returnPointFlag;
	}
	public void setReturnPointFlag(String returnPointFlag) {
		this.returnPointFlag = returnPointFlag;
	}
	public int getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}
	public int getAddCouponUserId() {
		return addCouponUserId;
	}
	public void setAddCouponUserId(int addCouponUserId) {
		this.addCouponUserId = addCouponUserId;
	}
	public List<OrderCoupon> getAddItemCoupons() {
		return addItemCoupons;
	}
	public void setAddItemCoupons(List<OrderCoupon> addItemCoupons) {
		this.addItemCoupons = addItemCoupons;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getEscrowStatus() {
		return escrowStatus;
	}
	public void setEscrowStatus(String escrowStatus) {
		this.escrowStatus = escrowStatus;
	}
	public String getCampaignCode() {
		return campaignCode;
	}
	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}

	public List<BuyItem> getAdditionItemList() {
		return additionItemList;
	}

	public void setAdditionItemList(List<BuyItem> additionItemList) {
		this.additionItemList = additionItemList;
	}

	public String getQuickDeliveryExtraChargeFlag() {
		return quickDeliveryExtraChargeFlag;
	}
	public void setQuickDeliveryExtraChargeFlag(String quickDeliveryExtraChargeFlag) {
		this.quickDeliveryExtraChargeFlag = quickDeliveryExtraChargeFlag;
	}
	public DeliveryMethodType getDeliveryMethodType() {
		return deliveryMethodType;
	}
	public void setDeliveryMethodType(DeliveryMethodType deliveryMethodType) {
		this.deliveryMethodType = deliveryMethodType;
	}
	public String getCashDiscountFlag() {
		return cashDiscountFlag;
	}
	public void setCashDiscountFlag(String cashDiscountFlag) {
		this.cashDiscountFlag = cashDiscountFlag;
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
