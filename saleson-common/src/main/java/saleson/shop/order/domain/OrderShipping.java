package saleson.shop.order.domain;

import java.util.List;

public class OrderShipping {
	
	private String orderCode;
	private int orderSequence;
	private int shippingSequence;
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

	private long sellerId;
	private String shippingType;
	private String shippingGroupCode;
	private String islandType;
	private int shippingItemCount;
	public int getShippingItemCount() {
		return shippingItemCount;
	}

	public void setShippingItemCount(int shippingItemCount) {
		this.shippingItemCount = shippingItemCount;
	}

	private int shipping;
	private int shippingExtraCharge1;
	private int shippingExtraCharge2;
	private int shippingFreeAmount;
	private int realShipping;
	private String shippingPaymentType;
	private int payShipping;
	private int returnShipping;
	private int discountShipping;
	private int shippingCouponCount;
	private String shipmentGroupCode;
	
	private String returnFlag;
	public String getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}

	private String rePayShipping;
	private int rePayShippingAmount;
	
	// 주문취소시 변경되는 배송비 관련 
	private String addPaymentType;
	private int addPayAmount;
	// 주문취소시 변경되는 배송비 관련
	
	private int remittanceId;
	private int remittanceAmount;
	private String remittanceExpectedDate;
	private String remittanceStatusCode;
	private String remittanceDate;
	private String conditionType;

	// 배송비 변경 시 변경 전 배송비 정보
    private int previousPayShipping;
    private int previousReturnShipping;
    private int previousRemittanceAmount;

    private int previousRealShipping;

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public int getRemittanceId() {
		return remittanceId;
	}

	public void setRemittanceId(int remittanceId) {
		this.remittanceId = remittanceId;
	}

	public String getRemittanceExpectedDate() {
		return remittanceExpectedDate;
	}

	public void setRemittanceExpectedDate(String remittanceExpectedDate) {
		this.remittanceExpectedDate = remittanceExpectedDate;
	}

	public String getRemittanceStatusCode() {
		return remittanceStatusCode;
	}

	public void setRemittanceStatusCode(String remittanceStatusCode) {
		this.remittanceStatusCode = remittanceStatusCode;
	}

	public String getRemittanceDate() {
		return remittanceDate;
	}

	public void setRemittanceDate(String remittanceDate) {
		this.remittanceDate = remittanceDate;
	}

	public int getAddPayAmount() {
		return addPayAmount;
	}

	public void setAddPayAmount(int addPayAmount) {
		this.addPayAmount = addPayAmount;
	}

	public String getAddPaymentType() {
		return addPaymentType;
	}

	public void setAddPaymentType(String addPaymentType) {
		this.addPaymentType = addPaymentType;
	}

	public int getRePayShippingAmount() {
		return rePayShippingAmount;
	}

	public void setRePayShippingAmount(int rePayShippingAmount) {
		this.rePayShippingAmount = rePayShippingAmount;
	}

	public String getRePayShipping() {
		return rePayShipping;
	}

	public void setRePayShipping(String rePayShipping) {
		this.rePayShipping = rePayShipping;
	}

	public int getRemittanceAmount() {
		return remittanceAmount;
	}

	public void setRemittanceAmount(int remittanceAmount) {
		this.remittanceAmount = remittanceAmount;
	}

	private List<OrderItem> orderItems;
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public String getShipmentGroupCode() {
		return shipmentGroupCode;
	}

	public void setShipmentGroupCode(String shipmentGroupCode) {
		this.shipmentGroupCode = shipmentGroupCode;
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

	public int getShippingFreeAmount() {
		return shippingFreeAmount;
	}

	public void setShippingFreeAmount(int shippingFreeAmount) {
		this.shippingFreeAmount = shippingFreeAmount;
	}

	public int getRealShipping() {
		return realShipping;
	}

	public void setRealShipping(int realShipping) {
		this.realShipping = realShipping;
	}

	public int getPayShipping() {
		return payShipping;
	}

	public void setPayShipping(int payShipping) {
		this.payShipping = payShipping;
	}

	public String getShippingPaymentType() {
		return shippingPaymentType;
	}

	public void setShippingPaymentType(String shippingPaymentType) {
		this.shippingPaymentType = shippingPaymentType;
	}

	
	public String getShippingTypeLabel() {
		
		if ("1".equals(this.shippingType)) {
			return "무료배송";
		} else if ("2".equals(this.shippingType)) {
			return "판매자조건부";
		} else if ("3".equals(this.shippingType)) {
			return "출고지조건부";
		} else if ("4".equals(this.shippingType)) {
			return "상품조건부";
		} else if ("5".equals(this.shippingType)) {
			return "개당배송비";
		} else if ("6".equals(this.shippingType)) {
			return "고정배송비";
		}
		
		return "-";
	}
	
	public int getReturnShipping() {
		return returnShipping;
	}

	public void setReturnShipping(int returnShipping) {
		this.returnShipping = returnShipping;
	}

    public int getPreviousPayShipping() {
        return previousPayShipping;
    }

    public void setPreviousPayShipping(int previousPayShipping) {
        this.previousPayShipping = previousPayShipping;
    }

    public int getPreviousReturnShipping() {
        return previousReturnShipping;
    }

    public void setPreviousReturnShipping(int previousReturnShipping) {
        this.previousReturnShipping = previousReturnShipping;
    }

    public int getPreviousRemittanceAmount() {
        return previousRemittanceAmount;
    }

    public void setPreviousRemittanceAmount(int previousRemittanceAmount) {
        this.previousRemittanceAmount = previousRemittanceAmount;
    }

	public int getPreviousRealShipping() {
		return previousRealShipping;
	}

	public void setPreviousRealShipping(int previousRealShipping) {
		this.previousRealShipping = previousRealShipping;
	}
}
