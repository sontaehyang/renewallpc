package saleson.shop.order.domain;

import java.util.List;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;

import saleson.common.utils.UserUtils;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.shipmentreturn.domain.ShipmentReturn;


public class OrderExchangeApply {
	
	private long userId;
	private long sellerId;
	private int orderExchangeShippingInfoId;
	private int orderExchangeApplyId;
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
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

	public int getItemSequence() {
		return itemSequence;
	}

	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}

	private OrderItem orderItem;
	
	private String exchangeReceiveName;
	private String exchangeReceivePhone;
	private String exchangeReceiveMobile;
	private String exchangeReceiveZipcode;
	private String exchangeReceiveSido;
	private String exchangeReceiveSigungu;
	private String exchangeReceiveEupmyeondong;
	private String exchangeReceiveAddress;
	private String exchangeReceiveAddress2;
	
	private int exchangeDeliveryCompanyId;
	private String exchangeDeliveryNumber;
	private String exchangeDeliveryDate;
	
	private String exchangeDeliveryCompanyName;
	private String exchangeDeliveryCompanyUrl;
	
	private String exchangeShippingStartFlag = "Y";
	private int exchangeShippingDeliveryCompanyId;
	private String exchangeShippingCompanyName;
	private String exchangeShippingNumber;
	private String exchangeShippingCompanyUrl;
	private String exchangeShippingStartDate;
	
	private String exchangeReason;
	private String exchangeReasonText;
	private String exchangeMemo;
	private String exchangeRefusalReasonText;
	
	private ShipmentReturn shipmentReturn;
	
	// 무료배송?
	private boolean isFreeShipping = false;
	
	private String conditionType;
	
	// 실제 계산된 배송비 - 환불 사유에 영향받지 않음
	private int exchangeRealShipping;
	
	// 발송 택배비 - 무료배송인경우
	private int resendShipping;
	
	// 회수 택배비
	private int collectionShipping;

	private List<DeliveryCompany> deliveryCompanys;

	private String exchangeReceiveZipcode1;
	private String exchangeReceiveZipcode2;
	
	public String getExchangeRefusalReasonText() {
		return exchangeRefusalReasonText;
	}

	public void setExchangeRefusalReasonText(String exchangeRefusalReasonText) {
		this.exchangeRefusalReasonText = exchangeRefusalReasonText;
	}

	public String getExchangeReceiveZipcode1() {
		return exchangeReceiveZipcode1;
	}

	public void setExchangeReceiveZipcode1(String exchangeReceiveZipcode1) {
		this.exchangeReceiveZipcode1 = exchangeReceiveZipcode1;
	}

	public String getExchangeReceiveZipcode2() {
		return exchangeReceiveZipcode2;
	}

	public void setExchangeReceiveZipcode2(String exchangeReceiveZipcode2) {
		this.exchangeReceiveZipcode2 = exchangeReceiveZipcode2;
	}

	public String getFullExchangeReceiveZipcode() {
		return exchangeReceiveZipcode1 + "-" + exchangeReceiveZipcode2;
	}
	
	public String getExchangeReceiveName() {
		return exchangeReceiveName;
	}

	public void setExchangeReceiveName(String exchangeReceiveName) {
		this.exchangeReceiveName = exchangeReceiveName;
	}

	public String getExchangeReceivePhone() {
		return exchangeReceivePhone;
	}

	public void setExchangeReceivePhone(String exchangeReceivePhone) {
		this.exchangeReceivePhone = exchangeReceivePhone;
	}

	public String getExchangeReceiveMobile() {
		return exchangeReceiveMobile;
	}

	public void setExchangeReceiveMobile(String exchangeReceiveMobile) {
		this.exchangeReceiveMobile = exchangeReceiveMobile;
	}

	public String getExchangeReceiveZipcode() {
		return exchangeReceiveZipcode;
	}

	public void setExchangeReceiveZipcode(String exchangeReceiveZipcode) {
		String[] temp = StringUtils.delimitedListToStringArray(exchangeReceiveZipcode, "-");
		if (temp.length == 2) {
			this.exchangeReceiveZipcode1 = temp[0];
			this.exchangeReceiveZipcode2 = temp[1];
		}
		
		this.exchangeReceiveZipcode = exchangeReceiveZipcode;
	}

	public String getExchangeReceiveSido() {
		return exchangeReceiveSido;
	}

	public void setExchangeReceiveSido(String exchangeReceiveSido) {
		this.exchangeReceiveSido = exchangeReceiveSido;
	}

	public String getExchangeReceiveSigungu() {
		return exchangeReceiveSigungu;
	}

	public void setExchangeReceiveSigungu(String exchangeReceiveSigungu) {
		this.exchangeReceiveSigungu = exchangeReceiveSigungu;
	}

	public String getExchangeReceiveEupmyeondong() {
		return exchangeReceiveEupmyeondong;
	}

	public void setExchangeReceiveEupmyeondong(String exchangeReceiveEupmyeondong) {
		this.exchangeReceiveEupmyeondong = exchangeReceiveEupmyeondong;
	}

	public String getExchangeReceiveAddress() {
		return exchangeReceiveAddress;
	}

	public void setExchangeReceiveAddress(String exchangeReceiveAddress) {
		this.exchangeReceiveAddress = exchangeReceiveAddress;
	}

	

	public String getExchangeReceiveAddress2() {
		return exchangeReceiveAddress2;
	}

	public void setExchangeReceiveAddress2(String exchangeReceiveAddress2) {
		this.exchangeReceiveAddress2 = exchangeReceiveAddress2;
	}

	public int getExchangeDeliveryCompanyId() {
		return exchangeDeliveryCompanyId;
	}

	public void setExchangeDeliveryCompanyId(int exchangeDeliveryCompanyId) {
		this.exchangeDeliveryCompanyId = exchangeDeliveryCompanyId;
	}

	public String getExchangeDeliveryNumber() {
		return exchangeDeliveryNumber;
	}

	public void setExchangeDeliveryNumber(String exchangeDeliveryNumber) {
		this.exchangeDeliveryNumber = exchangeDeliveryNumber;
	}

	public String getExchangeDeliveryDate() {
		return exchangeDeliveryDate;
	}

	public void setExchangeDeliveryDate(String exchangeDeliveryDate) {
		this.exchangeDeliveryDate = exchangeDeliveryDate;
	}

	public String getExchangeDeliveryCompanyName() {
		return exchangeDeliveryCompanyName;
	}

	public void setExchangeDeliveryCompanyName(String exchangeDeliveryCompanyName) {
		this.exchangeDeliveryCompanyName = exchangeDeliveryCompanyName;
	}

	public String getExchangeDeliveryCompanyUrl() {
		return exchangeDeliveryCompanyUrl;
	}

	public void setExchangeDeliveryCompanyUrl(String exchangeDeliveryCompanyUrl) {
		this.exchangeDeliveryCompanyUrl = exchangeDeliveryCompanyUrl;
	}

	public boolean isFreeShipping() {
		return isFreeShipping;
	}

	public void setFreeShipping(boolean isFreeShipping) {
		this.isFreeShipping = isFreeShipping;
	}

	public int getExchangeRealShipping() {
		return exchangeRealShipping;
	}

	public void setExchangeRealShipping(int exchangeRealShipping) {
		this.exchangeRealShipping = exchangeRealShipping;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public int getOrderExchangeShippingInfoId() {
		return orderExchangeShippingInfoId;
	}

	public void setOrderExchangeShippingInfoId(int orderExchangeShippingInfoId) {
		this.orderExchangeShippingInfoId = orderExchangeShippingInfoId;
	}

	public List<DeliveryCompany> getDeliveryCompanys() {
		return deliveryCompanys;
	}

	public void setDeliveryCompanys(List<DeliveryCompany> deliveryCompanys) {
		this.deliveryCompanys = deliveryCompanys;
	}

	public int getOrderExchangeApplyId() {
		return orderExchangeApplyId;
	}

	public void setOrderExchangeApplyId(int orderExchangeApplyId) {
		this.orderExchangeApplyId = orderExchangeApplyId;
	}

	public String getExchangeShippingStartFlag() {
		return exchangeShippingStartFlag;
	}

	public void setExchangeShippingStartFlag(String exchangeShippingStartFlag) {
		this.exchangeShippingStartFlag = exchangeShippingStartFlag;
	}

	public int getExchangeShippingDeliveryCompanyId() {
		return exchangeShippingDeliveryCompanyId;
	}

	public void setExchangeShippingDeliveryCompanyId(
			int exchangeShippingDeliveryCompanyId) {
		this.exchangeShippingDeliveryCompanyId = exchangeShippingDeliveryCompanyId;
	}

	

	public String getExchangeShippingCompanyName() {
		return exchangeShippingCompanyName;
	}

	public void setExchangeShippingCompanyName(String exchangeShippingCompanyName) {
		this.exchangeShippingCompanyName = exchangeShippingCompanyName;
	}

	public String getExchangeShippingNumber() {
		return exchangeShippingNumber;
	}

	public void setExchangeShippingNumber(String exchangeShippingNumber) {
		this.exchangeShippingNumber = exchangeShippingNumber;
	}

	public String getExchangeShippingCompanyUrl() {
		return exchangeShippingCompanyUrl;
	}

	public void setExchangeShippingCompanyUrl(String exchangeShippingCompanyUrl) {
		this.exchangeShippingCompanyUrl = exchangeShippingCompanyUrl;
	}

	public String getExchangeShippingStartDate() {
		
		if (StringUtils.isEmpty(exchangeShippingStartDate)) {
			return DateUtils.getToday();
		}
		
		return exchangeShippingStartDate;
	}

	public void setExchangeShippingStartDate(String exchangeShippingStartDate) {
		this.exchangeShippingStartDate = exchangeShippingStartDate;
	}

	public String getExchangeReason() {
		return exchangeReason;
	}

	public void setExchangeReason(String exchangeReason) {
		this.exchangeReason = exchangeReason;
	}

	public String getExchangeReasonText() {
		return exchangeReasonText;
	}

	public void setExchangeReasonText(String exchangeReasonText) {
		this.exchangeReasonText = exchangeReasonText;
	}

	public String getExchangeMemo() {
		return exchangeMemo;
	}

	public void setExchangeMemo(String exchangeMemo) {
		this.exchangeMemo = exchangeMemo;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public ShipmentReturn getShipmentReturn() {
		return shipmentReturn;
	}

	public void setShipmentReturn(ShipmentReturn shipmentReturn) {
		this.shipmentReturn = shipmentReturn;
	}
	
	public int getResendShipping() {
		return resendShipping;
	}

	public void setResendShipping(int resendShipping) {
		this.resendShipping = resendShipping;
	}

	public int getCollectionShipping() {
		return collectionShipping;
	}

	public void setCollectionShipping(int collectionShipping) {
		this.collectionShipping = collectionShipping;
	}

	public boolean getIsLogin() {
		
		if (this.userId > 0) {
			return true;
		}
		
		return UserUtils.isUserLogin();
	}
}
