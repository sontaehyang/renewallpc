package saleson.shop.order.domain;

import com.onlinepowers.framework.repository.CodeInfo;
import com.onlinepowers.framework.util.CodeUtils;
import com.onlinepowers.framework.util.StringUtils;
import lombok.ToString;
import saleson.common.enumeration.DeliveryMethodType;
import saleson.model.OrderGiftItem;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.domain.OrderReturnApply;
import saleson.shop.order.claimapply.domain.OrderExchangeApply;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;

import java.util.List;

@ToString
public class OrderItem {
	
	public static final String ITEM_KEY_DIVISION_STRING = "///";

	public String getItemKey() {
		return getOrderCode() + ITEM_KEY_DIVISION_STRING + getOrderSequence() + ITEM_KEY_DIVISION_STRING + getItemSequence();
	}
	

	private int copyItemSequence;
	public int getCopyItemSequence() {
		return copyItemSequence;
	}

	public void setCopyItemSequence(int copyItemSequence) {
		this.copyItemSequence = copyItemSequence;
	}

	
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	private int shippingSequence;
	private int shippingInfoSequence;	

	//환불처리시 사용[2017-07-12]minae.yun
	private int itemCount;	
	private int cancelCount;	
	
	
	private String additionItemFlag;
	private int parentItemSequence;
	public String getAdditionItemFlag() {
		return additionItemFlag;
	}

	public void setAdditionItemFlag(String additionItemFlag) {
		this.additionItemFlag = additionItemFlag;
	}

	public int getParentItemSequence() {
		return parentItemSequence;
	}

	public void setParentItemSequence(int parentItemSequence) {
		this.parentItemSequence = parentItemSequence;
	}


	private String deviceType;

	
	private long userId;
	private String guestFlag;
	private long sellerId;
	private int categoryTeamId;
	private int categoryGroupId;
	private int categoryId;
	private int shipmentId;
	private int couponUserId;
	private int addCouponUserId;
	private int itemId;
	private String itemCode;
	private String itemUserCode;
	private String itemName;
	private String freeGiftName;
	private String imageSrc;
	
	private String islandType;
	private String escrowStatus;

	// [SKC] 2017.09.06 회원 등급 할인 정보.
	private int levelId;
	private String levelName;
	private float userLevelDiscountRate;
	private int userLevelDiscountPrice;

	private int shippingExtraCharge1;
	private int shippingExtraCharge2;
	private int realShipping;
	
	private int purchasePrice;
	private int costPrice;
	private int supplyPrice;
	private int price;
	private int optionPrice;
	
	private int salePrice;
	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	private String shipmentGroupCode;
	private String deliveryType;
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getShipmentGroupCode() {
		return shipmentGroupCode;
	}

	public void setShipmentGroupCode(String shipmentGroupCode) {
		this.shipmentGroupCode = shipmentGroupCode;
	}


	private int quantity;
	private int claimQuantity;
	private int orderQuantity;
	
	private int couponDiscountPrice;
	private String spotSaleFlag;
	private String spotType;
	private int spotDiscountPrice;
	private String taxType;
	private String commissionType;
	private float commissionRate;
	private int commissionPrice;
	private int commissionBasePrice;
	private String sellerDiscountFlag;
	private String sellerDiscountType;
	private int sellerDiscountPrice;
	private String sellerDiscountDetail;
	private int sellerPoint;
	
	private int adminDiscountPrice;
	private String adminDiscountDetail;
	private String brand;
	private String options;
	private int deliveryCompanyId;
	private String deliveryCompanyName;
	private String deliveryCompanyUrl;
	private String deliveryNumber;
	private String itemReturnFlag;
	public String getItemReturnFlag() {
		return itemReturnFlag;
	}

	public void setItemReturnFlag(String itemReturnFlag) {
		this.itemReturnFlag = itemReturnFlag;
	}


	private String shipmentReturnType;
	public String getShipmentReturnType() {
		return shipmentReturnType;
	}

	public void setShipmentReturnType(String shipmentReturnType) {
		this.shipmentReturnType = shipmentReturnType;
	}


	private String pointType;
	private String pointConfigType;
	private int point;
	private String pointLog;
	private int earnPoint;
	private String earnPointFlag;
	private String createdDate;
	private String salesDate;
	private String salesCancelDate;
	
	private String confirmDate;
	private String returnRequestDate;
	private String returnRequestFinishDate;
	private String exchangeRequestDate;
	private String payDate;
	private String shippingReadyDate;
	private String shippingDate;
	private String shippingFinishDate;
	private String cancelRequestDate;
	private String cancelRequestFinishDate;
	private int shippingReturn;
	private String returnPointFlag;
	private String sellerName;
	
	private OrderCancelApply cancelApply;
	public OrderCancelApply getCancelApply() {
		return cancelApply;
	}

	public void setCancelApply(OrderCancelApply cancelApply) {
		this.cancelApply = cancelApply;
	}

	private OrderReturnApply returnApply;
	public OrderReturnApply getReturnApply() {
		return returnApply;
	}

	public void setReturnApply(OrderReturnApply returnApply) {
		this.returnApply = returnApply;
	}


	private OrderExchangeApply exchangeApply;
	public OrderExchangeApply getExchangeApply() {
		return exchangeApply;
	}
	public void setExchangeApply(OrderExchangeApply exchangeApply) {
		this.exchangeApply = exchangeApply;
	}


	private String claimApplyFlag; // 클레임 신청 가능 여부 (Y:가능)
	private String claimApplyItemKey; // 클레임 신청시 상품 구분 Key
	private int claimApplyQuantity; // 신청 수량
	private String claimRefusalReasonText;	// 클레임 거절 사유

	private String claimCode; // 클레임 번호

	public String getClaimCode() {
		return claimCode;
	}

	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}

	public int getClaimApplyQuantity() {
		return claimApplyQuantity;
	}

	public int getShippingSequence() {
		return shippingSequence;
	}

	public void setShippingSequence(int shippingSequence) {
		this.shippingSequence = shippingSequence;
	}

	public void setClaimApplyQuantity(int claimApplyQuantity) {
		this.claimApplyQuantity = claimApplyQuantity;
	}

	public String getClaimRefusalReasonText() {
		return claimRefusalReasonText;
	}

	public void setClaimRefusalReasonText(String claimRefusalReasonText) {
		this.claimRefusalReasonText = claimRefusalReasonText;
	}
	public String getClaimApplyItemKey() {
		return claimApplyItemKey;
	}

	public void setClaimApplyItemKey(String claimApplyItemKey) {
		this.claimApplyItemKey = claimApplyItemKey;
	}

	public String getClaimApplyFlag() {
		return claimApplyFlag;
	}

	public void setClaimApplyFlag(String claimApplyFlag) {
		this.claimApplyFlag = claimApplyFlag;
	}


	// 지연일
	private int delayDay;
	
	// 반송지ID
	private int shipmentReturnId;
	
	// 주문처리한 관리자
	private String updateAdminUserName;
	
	private String isShippingView;
	private OrderShipping orderShipping;
	
	private ShipmentReturn shipmentReturn;
	
	// 정산관련 컬럼
	private int remittanceId;
	private String remittanceType;
	private String remittanceDay;
	private String remittanceExpectedDate;
	private String remittanceDate;
	private String remittanceStatusCode;
	// 정산관련 컬럼
	
	public ShipmentReturn getShipmentReturn() {
		return shipmentReturn;
	}

	public int getRemittanceId() {
		return remittanceId;
	}

	public void setRemittanceId(int remittanceId) {
		this.remittanceId = remittanceId;
	}

	public String getRemittanceType() {
		return remittanceType;
	}

	public void setRemittanceType(String remittanceType) {
		this.remittanceType = remittanceType;
	}

	public String getRemittanceDay() {
		return remittanceDay;
	}

	public void setRemittanceDay(String remittanceDay) {
		this.remittanceDay = remittanceDay;
	}

	public String getRemittanceExpectedDate() {
		return remittanceExpectedDate;
	}

	public void setRemittanceExpectedDate(String remittanceExpectedDate) {
		this.remittanceExpectedDate = remittanceExpectedDate;
	}

	public String getRemittanceDate() {
		return remittanceDate;
	}

	public void setRemittanceDate(String remittanceDate) {
		this.remittanceDate = remittanceDate;
	}

	public String getRemittanceStatusCode() {
		return remittanceStatusCode;
	}

	public void setRemittanceStatusCode(String remittanceStatusCode) {
		this.remittanceStatusCode = remittanceStatusCode;
	}

	public void setShipmentReturn(ShipmentReturn shipmentReturn) {
		this.shipmentReturn = shipmentReturn;
	}

	public String getIsShippingView() {
		return isShippingView;
	}

	public void setIsShippingView(String isShippingView) {
		this.isShippingView = isShippingView;
	}

	public OrderShipping getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(OrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
	/**
	 * 배송가능한 수량
	 * 주문수량 - 취소 수량
	 * @return
	 */
	public int getShippingReadyPossibleQuantity() {
		return quantity - claimQuantity;
	}
	
	// 취소 상품?
	private String cancelFlag;
	private String refundStatus;

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	
	public int getOrderSequence() {
		return orderSequence;
	}

	public int getClaimQuantity() {
		return claimQuantity;
	}

	public void setClaimQuantity(int claimQuantity) {
		this.claimQuantity = claimQuantity;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}

	public int getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}

	public int getShippingInfoSequence() {
		return shippingInfoSequence;
	}

	public void setShippingInfoSequence(int shippingInfoSequence) {
		this.shippingInfoSequence = shippingInfoSequence;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getCancelCount() {
		return cancelCount;
	}

	public void setCancelCount(int cancelCount) {
		this.cancelCount = cancelCount;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
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

	public int getRealShipping() {
		return realShipping;
	}

	public void setRealShipping(int realShipping) {
		this.realShipping = realShipping;
	}

	public int getShippingReturn() {
		return shippingReturn;
	}

	public void setShippingReturn(int shippingReturn) {
		this.shippingReturn = shippingReturn;
	}

	public int getCouponDiscountPrice() {
		return couponDiscountPrice;
	}

	public void setCouponDiscountPrice(int couponDiscountPrice) {
		this.couponDiscountPrice = couponDiscountPrice;
	}

	public int getSellerDiscountPrice() {
		return sellerDiscountPrice;
	}

	public void setSellerDiscountPrice(int sellerDiscountPrice) {
		this.sellerDiscountPrice = sellerDiscountPrice;
	}

	public int getAdminDiscountPrice() {
		return adminDiscountPrice;
	}

	public void setAdminDiscountPrice(int adminDiscountPrice) {
		this.adminDiscountPrice = adminDiscountPrice;
	}

	public int getSpotDiscountPrice() {
		return spotDiscountPrice;
	}

	public void setSpotDiscountPrice(int spotDiscountPrice) {
		this.spotDiscountPrice = spotDiscountPrice;
	}

	public String getSpotType() {
		return spotType;
	}

	public void setSpotType(String spotType) {
		this.spotType = spotType;
	}

	public String getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public String getPointConfigType() {
		return pointConfigType;
	}

	public void setPointConfigType(String pointConfigType) {
		this.pointConfigType = pointConfigType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getAdminDiscountDetail() {
		return adminDiscountDetail;
	}

	public void setAdminDiscountDetail(String adminDiscountDetail) {
		this.adminDiscountDetail = adminDiscountDetail;
	}

	public int getCommissionBasePrice() {
		return commissionBasePrice;
	}

	public void setCommissionBasePrice(int commissionBasePrice) {
		this.commissionBasePrice = commissionBasePrice;
	}

	public String getDeliveryCompanyName() {
		return deliveryCompanyName;
	}

	public void setDeliveryCompanyName(String deliveryCompanyName) {
		this.deliveryCompanyName = deliveryCompanyName;
	}

	public String getDeliveryCompanyUrl() {
		return deliveryCompanyUrl;
	}

	public void setDeliveryCompanyUrl(String deliveryCompanyUrl) {
		this.deliveryCompanyUrl = deliveryCompanyUrl;
	}



	public int getCommissionPrice() {
		return commissionPrice;
	}

	public void setCommissionPrice(int commissionPrice) {
		this.commissionPrice = commissionPrice;
	}

	

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getShippingReadyDate() {
		return shippingReadyDate;
	}

	public void setShippingReadyDate(String shippingReadyDate) {
		this.shippingReadyDate = shippingReadyDate;
	}

	public String getCancelRequestDate() {
		return cancelRequestDate;
	}

	public void setCancelRequestDate(String cancelRequestDate) {
		this.cancelRequestDate = cancelRequestDate;
	}

	public String getCancelRequestFinishDate() {
		return cancelRequestFinishDate;
	}

	public void setCancelRequestFinishDate(String cancelRequestFinishDate) {
		this.cancelRequestFinishDate = cancelRequestFinishDate;
	}

	public String getFreeGiftName() {
		return freeGiftName;
	}

	public void setFreeGiftName(String freeGiftName) {
		this.freeGiftName = freeGiftName;
	}

	public int getDelayDay() {
		return delayDay;
	}

	public void setDelayDay(int delayDay) {
		this.delayDay = delayDay;
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

	
	public String getImageSrc() {
		return imageSrc;
	}
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
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
	public int getCouponUserId() {
		return couponUserId;
	}
	public void setCouponUserId(int couponUserId) {
		this.couponUserId = couponUserId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
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
	public int getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public int getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(int costPrice) {
		this.costPrice = costPrice;
	}
	public int getAddCouponUserId() {
		return addCouponUserId;
	}

	public void setAddCouponUserId(int addCouponUserId) {
		this.addCouponUserId = addCouponUserId;
	}

	public int getSupplyPrice() {
		return supplyPrice;
	}
	public void setSupplyPrice(int supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getOptionPrice() {
		return optionPrice;
	}
	public void setOptionPrice(int optionPrice) {
		this.optionPrice = optionPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSpotSaleFlag() {
		return spotSaleFlag;
	}
	public void setSpotSaleFlag(String spotSaleFlag) {
		this.spotSaleFlag = spotSaleFlag;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getCommissionType() {
		return commissionType;
	}
	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}
	public float getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(float commissionRate) {
		this.commissionRate = commissionRate;
	}
	public String getSellerDiscountFlag() {
		return sellerDiscountFlag;
	}
	public void setSellerDiscountFlag(String sellerDiscountFlag) {
		this.sellerDiscountFlag = sellerDiscountFlag;
	}
	public String getSellerDiscountType() {
		return sellerDiscountType;
	}
	public void setSellerDiscountType(String sellerDiscountType) {
		this.sellerDiscountType = sellerDiscountType;
	}
	
	public String getSellerDiscountDetail() {
		return sellerDiscountDetail;
	}

	public void setSellerDiscountDetail(String sellerDiscountDetail) {
		this.sellerDiscountDetail = sellerDiscountDetail;
	}

	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
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
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getPointLog() {
		return pointLog;
	}
	public void setPointLog(String pointLog) {
		this.pointLog = pointLog;
	}
	public int getEarnPoint() {
		return earnPoint;
	}
	public void setEarnPoint(int earnPoint) {
		this.earnPoint = earnPoint;
	}
	public String getEarnPointFlag() {
		return earnPointFlag;
	}
	public void setEarnPointFlag(String earnPointFlag) {
		this.earnPointFlag = earnPointFlag;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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

	
	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
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

	public int getShipmentReturnId() {
		return shipmentReturnId;
	}

	public void setShipmentReturnId(int shipmentReturnId) {
		this.shipmentReturnId = shipmentReturnId;
	}

	public String getUpdateAdminUserName() {
		return updateAdminUserName;
	}

	public void setUpdateAdminUserName(String updateAdminUserName) {
		this.updateAdminUserName = updateAdminUserName;
	}
	
	/**
	 * 판매 총액
	 * @return
	 */
	public int getSaleAmount() {
		return getSalePrice() * getQuantity();
	}
	
	/**
	 * 쿠폰 할인 총액
	 * @return
	 */
	public int getCouponDiscountAmount() {
		return getCouponDiscountPrice() * getQuantity();
	}
	
	private String orderStatus;
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 주문상태코드
	 *	0 : 주문접수
		10 : 결제완료(출고지시전)
		20 : 배송준비중(상품준비)
		30 : 배송중
		35 : 배송완료
		40 : 구매확정
		50 : 교환처리중
		55 : 교환배송시작
		59 : 교환거절
		60 : 반품처리중
		65 : 반품완료
		69 : 반품거절
		70 : 취소처리중
		75 : 취소완료
	 * @return
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	
	/**
	 * 주문상태 Text
	 * @return
	 */
	public String getOrderStatusLabel(String orderStatus) { 
		
		if ("0".equals(orderStatus)) {
			return "입금대기";
		} else if ("10".equals(orderStatus)) {
			return "결제완료"; // 출고지시전
		} else if ("20".equals(orderStatus)) {
			return "배송준비중"; // 상품 준비
		} else if ("30".equals(orderStatus)) {
			return "배송중";
		} else if ("35".equals(orderStatus)) {
			return "배송완료";
		} else if ("40".equals(orderStatus)) {
			return "구매확정";
		} else if ("50".equals(orderStatus)) {
			return "교환처리중";
		} else if ("55".equals(orderStatus)) {
			return "교환배송중";
		} else if ("59".equals(orderStatus)) {
			return "교환거절";
		} else if ("60".equals(orderStatus)) {
			return "반품처리중";
		} else if ("65".equals(orderStatus)) {
			return "반품완료";
		} else if ("69".equals(orderStatus)) {
			return "반품거절";
		} else if ("70".equals(orderStatus)) {
			return "취소처리중";
		} else if ("75".equals(orderStatus)) {
			return "취소완료";
		}
		
		return "-";
	}
	
	/**
	 * 주문상태 Text
	 * @return
	 */
	public String getOrderStatusLabel() {
		return getOrderStatusLabel(getOrderStatus());
	}
	
	/**
	 * 취소 가능?
	 * @return
	 */
	public boolean getIsCancel() {
		
		if ("0".equals(getOrderStatus()) || "10".equals(getOrderStatus()) || "20".equals(getOrderStatus())) {
			return true;
		}
		
		return false;
	}
	
	public int getSellerPoint() {
		return sellerPoint;
	}

	public void setSellerPoint(int sellerPoint) {
		this.sellerPoint = sellerPoint;
	}

	public String getShippingFinishDate() {
		return shippingFinishDate;
	}

	public void setShippingFinishDate(String shippingFinishDate) {
		this.shippingFinishDate = shippingFinishDate;
	}

	public String getEscrowStatus() {
		return escrowStatus;
	}

	public void setEscrowStatus(String escrowStatus) {
		this.escrowStatus = escrowStatus;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public float getUserLevelDiscountRate() {
		return userLevelDiscountRate;
	}

	public void setUserLevelDiscountRate(float userLevelDiscountRate) {
		this.userLevelDiscountRate = userLevelDiscountRate;
	}

	public int getUserLevelDiscountPrice() {
		return userLevelDiscountPrice;
	}

	public void setUserLevelDiscountPrice(int userLevelDiscountPrice) {
		this.userLevelDiscountPrice = userLevelDiscountPrice;
	}

	// 상품금액 (상품 판매가 * 수량)
	public int getItemAmount() {
		return commissionBasePrice * quantity;
	}

	// 할인 총액 {
	public int getDiscountAmount() {
		return getItemDiscountAmount() + getCouponDiscountAmount() + getUserLevelDiscountAmount();
	}

	// 상품 할인 금액
	public int getItemDiscountAmount() {
		return (sellerDiscountPrice * quantity) + getSpotDiscountAmount();
	}

	// 스팟 할인 금액
    public int getSpotDiscountAmount() {
	    int spotDiscountAmount = 0;
	    if ("Y".equals(spotSaleFlag)) {
            spotDiscountAmount = (spotDiscountPrice) * quantity;
        }
        return spotDiscountAmount;
    }

	// 회원 등급 할인 금액
	public int getUserLevelDiscountAmount() {
		return userLevelDiscountPrice * quantity;
	}

	private String approvalType;
	
	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public String getApprovalTypeLabel() {
		if (StringUtils.isEmpty(this.approvalType)) {
			return "";
		}
		
		CodeInfo codeInfo = CodeUtils.getCodeInfo("ORDER_PAY_TYPE", this.approvalType);
		if (codeInfo == null) {
			return "-";
		}
		
		return codeInfo.getLabel();
	}

	private List<OrderGiftItem> orderGiftItemList;

	public List<OrderGiftItem> getOrderGiftItemList() {
		return orderGiftItemList;
	}

	public void setOrderGiftItemList(List<OrderGiftItem> orderGiftItemList) {
		this.orderGiftItemList = orderGiftItemList;
	}

	private int parentItemId;
	private String parentItemOptions;
	private List<OrderItem> additionItemList;

	// 배송 방법 (일반택배, 퀵서비스, 방문수령)
	private DeliveryMethodType deliveryMethodType;

	private String erpOriginUnique;

	// ERP 전송용
	private int optionIndex;
	private String stockCode;
	private int optionQuantity;

	public int getOptionQuantity() {
		return optionQuantity;
	}
	public void setOptionQuantity(int optionQuantity) {
		this.optionQuantity = optionQuantity;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
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
	public DeliveryMethodType getDeliveryMethodType() {
		return deliveryMethodType;
	}
	public void setDeliveryMethodType(DeliveryMethodType deliveryMethodType) {
		this.deliveryMethodType = deliveryMethodType;
	}
	public int getParentItemId() {
		return parentItemId;
	}
	public void setParentItemId(int parentItemId) {
		this.parentItemId = parentItemId;
	}
	public String getParentItemOptions() {
		return parentItemOptions;
	}
	public void setParentItemOptions(String parentItemOptions) {
		this.parentItemOptions = parentItemOptions;
	}
	public List<OrderItem> getAdditionItemList() {
		return additionItemList;
	}
	public void setAdditionItemList(List<OrderItem> additionItemList) {
		this.additionItemList = additionItemList;
	}

	public String getUniq() {
		if (this.orderCode == null || this.orderCode.isEmpty()) {
			return "";
		}

		if ("50".equals(this.orderStatus)) {
			return orderCode + "" + orderSequence + "" + (this.itemSequence < 10 ? "0" + this.itemSequence : this.itemSequence) + "" + this.orderStatus + (this.optionIndex < 10 ? "0" + this.optionIndex : this.optionIndex);
		}

		return orderCode + "" + orderSequence + "" + (this.itemSequence < 10 ? "0" + this.itemSequence : this.itemSequence) + "" + "00" + "00";
	}
}
