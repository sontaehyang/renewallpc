package saleson.shop.order.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import saleson.common.utils.SellerUtils;
import saleson.shop.item.domain.Item;

import com.onlinepowers.framework.util.StringUtils;
import saleson.shop.userlevel.domain.UserLevel;

public class Shipping {

	private long sellerId;
	private String orderCode;
	private int orderSequence;
	public int getOrderSequence() {
		return orderSequence;
	}

	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}

	private int shippingSequence;
	
	private int defaultIsLandShipping;
	private int defaultRealShipping;
	public int getDefaultIsLandShipping() {
		return defaultIsLandShipping;
	}

	public void setDefaultIsLandShipping(int defaultIsLandShipping) {
		this.defaultIsLandShipping = defaultIsLandShipping;
	}

	public int getDefaultRealShipping() {
		return defaultRealShipping;
	}

	public void setDefaultRealShipping(int defaultRealShipping) {
		this.defaultRealShipping = defaultRealShipping;
	}
	
	private String shipmentGroupCode; // 통합 물류 배송
	private String shippingType; // 배송비 구분 (1: 무료배송, 2: 판매자조건부, 3:출고지조건부, 4:상품조건부, 5:개당배송비, 6:고정배송비)
	private String shippingTypeLabel;
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
	private ShippingCoupon shippingCoupon;
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

	public ShippingCoupon getShippingCoupon() {
		return shippingCoupon;
	}

	public void setShippingCoupon(ShippingCoupon shippingCoupon) {
		this.shippingCoupon = shippingCoupon;
	}

	public int getRemittanceAmount() {
		return remittanceAmount;
	}

	public void setRemittanceAmount(int remittanceAmount) {
		this.remittanceAmount = remittanceAmount;
	}

	private int shippingItemCount;
	
	// 실제 배송비 - 지불 배송비 (선결제)
	private int payShipping;
	
	// 개별 배송 상품 여부
	private boolean isSingleShipping;
	
	// 배송정책별 묶음 상품
	private List<BuyItem> buyItems;
	
	// 개별 배송 상품
	private BuyItem buyItem;
	
	// 배송비 지불 방법 (1 : 선불, 2 : 착불)
	private String shippingPaymentType;
	
	// 배송 요구사항
	private String content;

	// 퀵배송 여부 (Y:퀵배송, N:일반택배)
	private String quickDeliveryFlag;

	public String getQuickDeliveryFlag() {
		return quickDeliveryFlag;
	}
	public void setQuickDeliveryFlag(String quickDeliveryFlag) {
		this.quickDeliveryFlag = quickDeliveryFlag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getShippingItemCount() {
		return shippingItemCount;
	}

	public void setShippingItemCount(int shippingItemCount) {
		this.shippingItemCount = shippingItemCount;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getIslandType() {
		return islandType;
	}

	public void setIslandType(String islandType) {
		this.islandType = islandType;
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

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

    /**
     *
     * @param list
     * @param islandType
     * @return
     */
	public List<Shipping> getShippingGroups(List<BuyItem> list, String islandType) {
		
		if (list == null) {
			return null;
		}

		List<Shipping> groups = new ArrayList<>();
		for(BuyItem buyItem : list) {
			
			Item item = buyItem.getItem();

            long itemCount = list.stream().filter(b -> b.getItemId() == item.getItemId()).count();

			// 2: 판매자조건부, 3:출고지조건부 가 아닌경우에는 개별배송 상품임.
			boolean isSingleShipping = true;
			if ("2".equals(item.getShippingType()) || "3".equals(item.getShippingType())) {
				isSingleShipping = false;
			} else if (itemCount > 1) {     // 같은 상품의 옵션일 경우 하나의 상품으로 보고 묶음배송 처리.
                isSingleShipping = false;
            }

			// 개별 배송의 경우 무조건 등록
			boolean isNew = true;
			if (isSingleShipping == false) {
				if (!groups.isEmpty()) {
					for(Shipping shipping : groups) {
						if (shipping.getShippingGroupCode().equals(item.getShippingGroupCode())) {
							isNew = false;
							break;
						}

					}
				}
			}
			
			if (isNew) {
				
				// 관리자에서 재계산 할때
				if ("FOR_ITEM".equals(islandType)) {
					islandType = buyItem.getIslandType();
				}

				// 추가구성상품일 경우 무료배송 처리 - 본상품 배송비만 적용
				boolean isAdditionItem = false;
				if ("Y".equals(buyItem.getAdditionItemFlag())) {
					isAdditionItem = true;
				}

				Shipping shipping = new Shipping();
				shipping.setShippingType(isAdditionItem ? "1" : item.getShippingType());
				shipping.setShippingGroupCode(isAdditionItem ? "" : item.getShippingGroupCode());
				shipping.setShipping(isAdditionItem ? 0 : item.getShipping());
				shipping.setIslandType(isAdditionItem ? null : islandType);
				shipping.setShippingExtraCharge1(isAdditionItem ? 0 : item.getShippingExtraCharge1());
				shipping.setShippingExtraCharge2(isAdditionItem ? 0 : item.getShippingExtraCharge2());
				shipping.setShippingReturn(isAdditionItem ? 0 : item.getShippingReturn());
				shipping.setShippingFreeAmount(isAdditionItem ? 0 : item.getShippingFreeAmount());
				shipping.setShippingPaymentType(isAdditionItem ? "1" : buyItem.getShippingPaymentType());

				// 퀵배송일 경우 착불 처리
				if ("Y".equals(buyItem.getQuickDeliveryFlag())) {
					shipping.setShippingPaymentType("2");
					shipping.setQuickDeliveryFlag("Y");
				}
				
				// 본사 배송이면?
				if ("1".equals(buyItem.getDeliveryType())) {
					shipping.setSellerId(SellerUtils.DEFAULT_OPMANAGER_SELLER_ID);
				} else {
					shipping.setSellerId(buyItem.getSellerId());
				}
				
				shipping.setShipmentGroupCode(buyItem.getShipmentGroupCode());
				
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
					List<BuyItem> items = new ArrayList<>();
					
					items.add(buyItem);
					shipping.setBuyItems(items);
					
				}
				
				shipping.setSingleShipping(isSingleShipping);
				groups.add(shipping);
				
			} else {
				for(Shipping shipping : groups) {
					if (shipping.getShippingGroupCode().equals(item.getShippingGroupCode())) {
						List<BuyItem> items = shipping.getBuyItems();
						items.add(buyItem);

						shipping.setBuyItems(items);
						break;
					}
				}
			}
		}
		
		int shippingSequence = 0;
		long quickDeliveryCount = list.stream().filter(b -> "Y".equals(b.getQuickDeliveryFlag())).count();

		for(Shipping shipping : groups) {
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
						
						for(BuyItem buyItem : shipping.getBuyItems()) {
							ItemPrice itemPrice = buyItem.getItemPrice();
							totalItemAmount += itemPrice.getBaseAmountForShipping();
						}
						
					} else {
						
						// 통합 물류 배송으로 조건부 배송 조건 금액을 물류 배송 상품 전체로 한다.
						for(BuyItem buyItem : list) {
							
							if (StringUtils.isEmpty(buyItem.getShipmentGroupCode())) {
								continue;
							}
							
							if (shipping.getShipmentGroupCode().equals(buyItem.getShipmentGroupCode())) {
								ItemPrice itemPrice = buyItem.getItemPrice();
								totalItemAmount += itemPrice.getBaseAmountForShipping();
							}
						}
					}
				} else {
					for(BuyItem buyItem : shipping.getBuyItems()) {
						ItemPrice itemPrice = buyItem.getItemPrice();
						totalItemAmount += itemPrice.getBaseAmountForShipping();
					}
				}
				
				if (shipping.getShippingFreeAmount() <= totalItemAmount) {
					realShipping = addDeliveryCharge;
				} else {
					realShipping = shipping.getShipping() + addDeliveryCharge;
				}
				
			} else if ("4".equals(shipping.getShippingType())) { // 상품 조건부

                if (shipping.getBuyItems() != null) {
                    int totalItemAmount = 0;

                    for(BuyItem buyItem : shipping.getBuyItems()) {
                        ItemPrice itemPrice = buyItem.getItemPrice();
                        totalItemAmount += itemPrice.getBaseAmountForShipping();
                    }

                    if (shipping.getShippingFreeAmount() <= totalItemAmount) {
                        realShipping = addDeliveryCharge;
                    } else {
                        realShipping = shipping.getShipping() + addDeliveryCharge;
                    }
                } else {
                    BuyItem buyItem = shipping.getBuyItem();
                    ItemPrice itemPrice = buyItem.getItemPrice();

                    if (shipping.getShippingFreeAmount() <= itemPrice.getBaseAmountForShipping()) {
                        realShipping = addDeliveryCharge;
                    } else {
                        realShipping = shipping.getShipping() + addDeliveryCharge;
                    }
                }

			} else if ("5".equals(shipping.getShippingType())) { // 개당배송비 - BOX 당 배송비

                int totalItemQuantity = 0;

                if (shipping.getBuyItems() != null) {


                    for(BuyItem buyItem : shipping.getBuyItems()) {
                        ItemPrice itemPrice = buyItem.getItemPrice();

                        if (shipping.getShippingItemCount() == 0) {
                            shipping.setShippingItemCount(1);
                        }

                        totalItemQuantity += itemPrice.getQuantity();
                    }
                } else {
                    BuyItem buyItem = shipping.getBuyItem();
                    ItemPrice itemPrice = buyItem.getItemPrice();

                    if (shipping.getShippingItemCount() == 0) {
                        shipping.setShippingItemCount(1);
                    }

                    totalItemQuantity += itemPrice.getQuantity();
                }

                int boxCount = (int) Math.ceil((float) totalItemQuantity / shipping.getShippingItemCount());
                realShipping = (shipping.getShipping() + addDeliveryCharge) * boxCount;
			} else { // 고정 배송비
				realShipping = shipping.getShipping() + addDeliveryCharge;
			}
			
			shipping.setAddDeliveryCharge(addDeliveryCharge);
			shipping.setRealShipping(realShipping);
			shipping.setPayShipping(realShipping);

			// 착불인경우 사용자 배송비 금액을 0으로 잡는다
			if ("2".equals(shipping.getShippingPaymentType())) {
				shipping.setPayShipping(0);

				// 퀵배송일 경우 배송비 금액을 0으로 처리
				if (quickDeliveryCount > 0) {
					shipping.setAddDeliveryCharge(0);
					shipping.setRealShipping(0);
					shipping.setPayShipping(0);
					shipping.setShipping(0);
					shipping.setShippingExtraCharge1(0);
					shipping.setShippingExtraCharge2(0);
				}
			}
			
			// CJH 2016.10.20 배송비 할인액을 더해서 정산금액으로 셋팅한다. - 배송비 쿠폰은 본사부담 할인
			shipping.setRemittanceAmount(shipping.getPayShipping() + shipping.getDiscountShipping());
		}
		
		return groups;
	}
	
	
	public String getShippingPaymentType() {
		return shippingPaymentType;
	}
	public void setShippingPaymentType(String shippingPaymentType) {
		this.shippingPaymentType = shippingPaymentType;
	}
	public int getShippingSequence() {
		return shippingSequence;
	}
	public void setShippingSequence(int shippingSequence) {
		this.shippingSequence = shippingSequence;
	}
	public boolean isSingleShipping() {
		return isSingleShipping;
	}
	public void setSingleShipping(boolean isSingleShipping) {
		this.isSingleShipping = isSingleShipping;
	}
	
	public int getPayShipping() {
		return payShipping;
	}
	public void setPayShipping(int payShipping) {
		this.payShipping = payShipping;
	}
	public int getRealShipping() {
		return realShipping;
	}
	public void setRealShipping(int realShipping) {
		this.realShipping = realShipping;
	}
	
	public List<BuyItem> getBuyItems() {
		return buyItems;
	}

	public void setBuyItems(List<BuyItem> buyItems) {
		this.buyItems = buyItems;
	}

	public BuyItem getBuyItem() {
		return buyItem;
	}

	public void setBuyItem(BuyItem buyItem) {
		this.buyItem = buyItem;
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
	public int getShipping() {
		return shipping;
	}
	public void setShipping(int shipping) {
		this.shipping = shipping;
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

	public String getShipmentGroupCode() {
		return shipmentGroupCode;
	}

	public void setShipmentGroupCode(String shipmentGroupCode) {
		this.shipmentGroupCode = shipmentGroupCode;
	}
	public String getShippingTypeLabel() {
		
		String shippingTypeLabel = "";
		
		if("1".equals(this.getShippingType())){
			shippingTypeLabel = "무료배송";
		}else if("2".equals(this.getShippingType())){
			shippingTypeLabel = "판매자조건부";
		}else if("3".equals(this.getShippingType())){
			shippingTypeLabel = "출고지조건부";
		}else if("4".equals(this.getShippingType())){
			shippingTypeLabel = "상품조건부";
		}else if("5".equals(this.getShippingType())){
			shippingTypeLabel = "개당배송비";
		}else if("6".equals(this.getShippingType())){
			shippingTypeLabel = "고정배송비";
		}

		return shippingTypeLabel;
	}
	public void setShippingTypeLabel(String shippingTypeLabel) {
		this.shippingTypeLabel = shippingTypeLabel;
	}
}
