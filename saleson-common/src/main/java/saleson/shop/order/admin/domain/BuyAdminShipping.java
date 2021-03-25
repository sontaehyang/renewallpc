package saleson.shop.order.admin.domain;

import java.util.ArrayList;
import java.util.List;

import saleson.common.utils.SellerUtils;
import saleson.shop.item.domain.Item;

import com.onlinepowers.framework.util.StringUtils;

public class BuyAdminShipping {
	private long sellerId;
	private String orderCode;
	private int orderSequence;
	
	private int shippingSequence;
	
	private String shipmentGroupCode; // 통합 물류 배송
	private String shippingType; // 배송비 구분 (1: 무료배송, 2: 판매자조건부, 3:출고지조건부, 4:상품조건부, 5:개당배송비, 6:고정배송비)
	private String shippingGroupCode;
	private String islandType;
	private int shipping;
	private int shippingExtraCharge1;
	private int shippingExtraCharge2;
	private int shippingReturn;
	private int shippingFreeAmount;
	private String deliveryInfo;
	private int realShipping;
	private int remittanceAmount;
	private int addDeliveryCharge;
	private int discountShipping; // 배송비 쿠폰 사용으로 할인된 배송비 금액
	private int shippingCouponCount;
	private String shippingPaymentType;
	private int shippingItemCount;
	
	private int payShipping;
	
	private boolean isSingleShipping;
	private BuyAdminItem buyItem;
	private List<BuyAdminItem> buyItems;
	
	public boolean isSingleShipping() {
		return isSingleShipping;
	}
	public void setSingleShipping(boolean isSingleShipping) {
		this.isSingleShipping = isSingleShipping;
	}
	public BuyAdminItem getBuyItem() {
		return buyItem;
	}
	public void setBuyItem(BuyAdminItem buyItem) {
		this.buyItem = buyItem;
	}
	public List<BuyAdminItem> getBuyItems() {
		return buyItems;
	}
	public void setBuyItems(List<BuyAdminItem> buyItems) {
		this.buyItems = buyItems;
	}
	public int getShippingItemCount() {
		return shippingItemCount;
	}
	public void setShippingItemCount(int shippingItemCount) {
		this.shippingItemCount = shippingItemCount;
	}
	public String getShippingPaymentType() {
		return shippingPaymentType;
	}
	public void setShippingPaymentType(String shippingPaymentType) {
		this.shippingPaymentType = shippingPaymentType;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}
	public int getShippingSequence() {
		return shippingSequence;
	}
	public void setShippingSequence(int shippingSequence) {
		this.shippingSequence = shippingSequence;
	}
	public String getShipmentGroupCode() {
		return shipmentGroupCode;
	}
	public void setShipmentGroupCode(String shipmentGroupCode) {
		this.shipmentGroupCode = shipmentGroupCode;
	}
	public String getShippingType() {
		return shippingType;
	}
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}
	public String getShippingGroupCode() {
		return shippingGroupCode;
	}
	public void setShippingGroupCode(String shippingGroupCode) {
		this.shippingGroupCode = shippingGroupCode;
	}
	public String getIslandType() {
		return islandType;
	}
	public void setIslandType(String islandType) {
		this.islandType = islandType;
	}
	public int getShipping() {
		return shipping;
	}
	public void setShipping(int shipping) {
		this.shipping = shipping;
	}
	public int getShippingExtraCharge1() {
		return shippingExtraCharge1;
	}
	public void setShippingExtraCharge1(int shippingExtraCharge1) {
		this.shippingExtraCharge1 = shippingExtraCharge1;
	}
	public int getShippingExtraCharge2() {
		return shippingExtraCharge2;
	}
	public void setShippingExtraCharge2(int shippingExtraCharge2) {
		this.shippingExtraCharge2 = shippingExtraCharge2;
	}
	public int getShippingReturn() {
		return shippingReturn;
	}
	public void setShippingReturn(int shippingReturn) {
		this.shippingReturn = shippingReturn;
	}
	public int getShippingFreeAmount() {
		return shippingFreeAmount;
	}
	public void setShippingFreeAmount(int shippingFreeAmount) {
		this.shippingFreeAmount = shippingFreeAmount;
	}
	public String getDeliveryInfo() {
		return deliveryInfo;
	}
	public void setDeliveryInfo(String deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}
	public int getRealShipping() {
		return realShipping;
	}
	public void setRealShipping(int realShipping) {
		this.realShipping = realShipping;
	}
	public int getRemittanceAmount() {
		return remittanceAmount;
	}
	public void setRemittanceAmount(int remittanceAmount) {
		this.remittanceAmount = remittanceAmount;
	}
	public int getAddDeliveryCharge() {
		return addDeliveryCharge;
	}
	public void setAddDeliveryCharge(int addDeliveryCharge) {
		this.addDeliveryCharge = addDeliveryCharge;
	}
	public int getDiscountShipping() {
		return discountShipping;
	}
	public void setDiscountShipping(int discountShipping) {
		this.discountShipping = discountShipping;
	}
	public int getShippingCouponCount() {
		return shippingCouponCount;
	}
	public void setShippingCouponCount(int shippingCouponCount) {
		this.shippingCouponCount = shippingCouponCount;
	}
	
	public int getPayShipping() {
		return payShipping;
	}
	public void setPayShipping(int payShipping) {
		this.payShipping = payShipping;
	}
	/**
	 * @param list
	 * @param shippingPaymentMethod (지불방법 : 1->선불, 2->착불)
	 * @return
	 */
	public List<BuyAdminShipping> getShippingGroups(List<BuyAdminItem> list, String islandType, String shippingPaymentType) {
		
		if (list == null) {
			return null;
		}
		
		List<BuyAdminShipping> groups = new ArrayList<>();
		for(BuyAdminItem buyItem : list) {
			
			Item item = buyItem.getItem();
			
			// 2: 판매자조건부, 3:출고지조건부 가 아닌경우에는 개별배송 상품임.
			boolean isSingleShipping = true;
			if ("2".equals(item.getShippingType()) || "3".equals(item.getShippingType())) {
				isSingleShipping = false;
			}

			// 개별 배송의 경우 무조건 등록
			boolean isNew = true;
			if (isSingleShipping == false) {
				if (!groups.isEmpty()) {
					for(BuyAdminShipping shipping : groups) {
						if (shipping.getShippingGroupCode().equals(item.getShippingGroupCode())) {
							isNew = false;
							break;
						}
						
					}
				}
			}
			
			if (isNew) {
								
				BuyAdminShipping shipping = new BuyAdminShipping();
				shipping.setShippingType(item.getShippingType());
				shipping.setShippingGroupCode(item.getShippingGroupCode());
				shipping.setShipping(item.getShipping());
				shipping.setIslandType(islandType);
				shipping.setShippingExtraCharge1(item.getShippingExtraCharge1());
				shipping.setShippingExtraCharge2(item.getShippingExtraCharge2());
				shipping.setShippingReturn(item.getShippingReturn());
				shipping.setShippingFreeAmount(item.getShippingFreeAmount());
				shipping.setShippingPaymentType(shippingPaymentType);
				
				// 본사 배송이면?
				if ("1".equals(item.getDeliveryType())) {
					shipping.setSellerId(SellerUtils.DEFAULT_OPMANAGER_SELLER_ID);
				} else {
					shipping.setSellerId(item.getSellerId());
				}
				
				shipping.setShipmentGroupCode(item.getShipmentGroupCode());
				
				// 0으로 나누는 상황은 에러를 발생시킴
				if (item.getShippingItemCount() == 0) {
					item.setShippingItemCount(1);
				}
				
				shipping.setShippingItemCount(item.getShippingItemCount());
				
				// 개별 배송
				if (isSingleShipping) {
					
					// 개별배송의 경우 그룹코드를 임의로 생성함
					shipping.setShippingGroupCode("single-" + buyItem.getItemSequence());	
					shipping.setBuyItem(buyItem);
					
				} else {
					List<BuyAdminItem> items = new ArrayList<>();
					
					items.add(buyItem);
					shipping.setBuyItems(items);
					
				}
				
				shipping.setSingleShipping(isSingleShipping);
				groups.add(shipping);
				
			} else {
				for(BuyAdminShipping shipping : groups) {
					if (shipping.getShippingGroupCode().equals(item.getShippingGroupCode())) {
						List<BuyAdminItem> items = shipping.getBuyItems();
						items.add(buyItem);
						
						shipping.setBuyItems(items);
						break;
					}
				}
			}
		}
		
		int shippingSequence = 0;
		for(BuyAdminShipping shipping : groups) {
			shipping.setShippingSequence(shippingSequence++);
			
			int addDeliveryCharge = 0;
			if ("JEJU".equals(islandType)) {
				addDeliveryCharge = shipping.getShippingExtraCharge1();
			} else if ("ISLAND".equals(islandType)) {
				addDeliveryCharge = shipping.getShippingExtraCharge2();
			}
			
			int realShipping = 0;
			if ("1".equals(shipping.getShippingType())) { // 무료 배송
				
				realShipping = addDeliveryCharge;
				
				
			} else if ("2".equals(shipping.getShippingType()) || "3".equals(shipping.getShippingType())) { // 2 : 판매자 조건부, 3 : 출고지 조건부, 
				
				int totalItemAmount = 0;
				if ("3".equals(shipping.getShippingType())) {
					
					if (StringUtils.isEmpty(shipping.getShipmentGroupCode())) {
						
						for(BuyAdminItem buyItem : shipping.getBuyItems()) {
							BuyAdminItemPrice itemPrice = buyItem.getItemPrice();
							totalItemAmount += itemPrice.getSaleAmount();
						}
						
					} else {
						
						// 통합 물류 배송으로 조건부 배송 조건 금액을 물류 배송 상품 전체로 한다.
						for(BuyAdminItem buyItem : list) {
							
							Item item = buyItem.getItem();
							
							if (StringUtils.isEmpty(item.getShipmentGroupCode())) {
								continue;
							}
							
							if (shipping.getShipmentGroupCode().equals(item.getShipmentGroupCode())) {
								BuyAdminItemPrice itemPrice = buyItem.getItemPrice();
								totalItemAmount += itemPrice.getSaleAmount();
							}
						}
					}
				} else {
					for(BuyAdminItem buyItem : shipping.getBuyItems()) {
						BuyAdminItemPrice itemPrice = buyItem.getItemPrice();
						totalItemAmount += itemPrice.getSaleAmount();
					}
				}
				
				if (shipping.getShippingFreeAmount() <= totalItemAmount) {
					realShipping = addDeliveryCharge;
				} else {
					realShipping = shipping.getShipping() + addDeliveryCharge;
				}
				
			} else if ("4".equals(shipping.getShippingType())) { // 상품 조건부
				
				BuyAdminItem buyItem = shipping.getBuyItem();
				BuyAdminItemPrice itemPrice = buyItem.getItemPrice();
				
				if (shipping.getShippingFreeAmount() <= itemPrice.getSaleAmount()) {
					realShipping = addDeliveryCharge;
				} else {
					realShipping = shipping.getShipping() + addDeliveryCharge;
				}
				
			} else if ("5".equals(shipping.getShippingType())) { // 개당배송비 - BOX 당 배송비
				
				BuyAdminItem buyItem = shipping.getBuyItem();
				BuyAdminItemPrice itemPrice = buyItem.getItemPrice();
				
				if (shipping.getShippingItemCount() == 0) {
					shipping.setShippingItemCount(1);
				}
				
				int boxCount = (int) Math.ceil((float) itemPrice.getQuantity() / shipping.getShippingItemCount());
				realShipping = (shipping.getShipping() + addDeliveryCharge) * boxCount;
				
			} else { // 고정 배송비
				realShipping = shipping.getShipping() + addDeliveryCharge;
			}
			
			shipping.setAddDeliveryCharge(addDeliveryCharge);
			shipping.setRealShipping(realShipping);
			shipping.setPayShipping(realShipping);
			
			// 착불인경우 사용자 배송비 금액을 0으로 잡는다
			if ("2".equals(shippingPaymentType)) {
				shipping.setPayShipping(0);
			}
			
			// CJH 2016.10.20 배송비 할인액을 더해서 정산금액으로 셋팅한다. - 배송비 쿠폰은 본사부담 할인
			shipping.setRemittanceAmount(shipping.getPayShipping() + shipping.getDiscountShipping());
		}
		
		return groups;
	}
}
