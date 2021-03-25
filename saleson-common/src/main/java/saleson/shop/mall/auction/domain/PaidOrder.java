package saleson.shop.mall.auction.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import saleson.shop.mall.domain.MallBase;

@XmlRootElement(name="PaidOrder", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaidOrder extends MallBase {
	
	@XmlElement(name="OrderBase", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private OrderBase orderBase;
	
	@XmlElement(name="AddressBase", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private AddressBase addressBase;
	
	@XmlAttribute(name="DeliveryFee")
	private String deliveryFee;	// 배송비 결제 여부?
	
	@XmlAttribute(name="ReceiptDate")
	private String receiptDate; // 입금일
	
	@XmlAttribute(name="BundleDelvieryRequestNo")
	private int bundleDelvieryRequestNo; // 묶음결제 요청번호
	
	@XmlAttribute(name="RequestOption")
	private String requestOption; // 주문 옵션
	
	@XmlAttribute(name="DeliveryRemark")
	private String deliveryRemark; // 배송시 요구사항
	
	@XmlAttribute(name="SellerStockCode")
	private String sellerStockCode; // 재고 코드
	
	@XmlAttribute(name="DeliveryDelayDay")
	private int deliveryDelayDay; // 배송 지연일
	
	@XmlAttribute(name="DeliveryDelayNotifyDate")
	private String deliveryDelayNotifyDate; // 배송지연 예고일자
	
	@XmlAttribute(name="SellerId")
	private String sellerId; // 판매자 ID
	
	@XmlAttribute(name="ItemCode")
	private String itemCode; // 상품 코드
	
	@XmlAttribute(name="OrderDate")
	private String orderDate; // 주문 일자
	
	@XmlAttribute(name="PayNo")
	private int payNo; // 결제 번호
	
	@XmlAttribute(name="GroupOrderSeqno")
	private int groupOrderSeqno; // 묶음 배송 번호

	/**
	 * 170 : 결제완료
	 * 201 : 배송준비중
	 * 995 : 취소완료
	 */
	private String ordPrdStat; // 주문상태코드
	private int payShipping; // 배송비
	private String payShippingType; // 배송비 결제 방법
	private int shippingSeqno; // 배송 코드
	private String shippingGroupFlag; // 묶음 배송?

	/**
	 * GetShippingDetailResult Api를 통해 조회된 데이터
	 */
	private GetShippingDetailResult detail;
	
	public GetShippingDetailResult getDetail() {
		return detail;
	}

	public void setDetail(GetShippingDetailResult detail) {
		this.detail = detail;
	}

	public String getShippingGroupFlag() {
		return shippingGroupFlag;
	}

	public void setShippingGroupFlag(String shippingGroupFlag) {
		this.shippingGroupFlag = shippingGroupFlag;
	}

	public String getPayShippingType() {
		return payShippingType;
	}

	public void setPayShippingType(String payShippingType) {
		this.payShippingType = payShippingType;
	}

	public int getShippingSeqno() {
		return shippingSeqno;
	}

	public void setShippingSeqno(int shippingSeqno) {
		this.shippingSeqno = shippingSeqno;
	}

	public int getPayShipping() {
		return payShipping;
	}

	public void setPayShipping(int payShipping) {
		this.payShipping = payShipping;
	}

	public String getOrdPrdStat() {
		return ordPrdStat;
	}

	public void setOrdPrdStat(String ordPrdStat) {
		this.ordPrdStat = ordPrdStat;
	}

	public OrderBase getOrderBase() {
		return orderBase;
	}

	public void setOrderBase(OrderBase orderBase) {
		this.orderBase = orderBase;
	}

	public AddressBase getAddressBase() {
		return addressBase;
	}

	public void setAddressBase(AddressBase addressBase) {
		this.addressBase = addressBase;
	}

	public String getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public String getReceiptDate() {
		
		if (StringUtils.isNotEmpty(receiptDate)) {
			receiptDate = receiptDate.substring(0, receiptDate.indexOf("."));
			return receiptDate.replaceAll("T", " ");
		}
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public int getBundleDelvieryRequestNo() {
		return bundleDelvieryRequestNo;
	}

	public void setBundleDelvieryRequestNo(int bundleDelvieryRequestNo) {
		this.bundleDelvieryRequestNo = bundleDelvieryRequestNo;
	}

	public String getRequestOption() {
		return requestOption;
	}

	public void setRequestOption(String requestOption) {
		this.requestOption = requestOption;
	}

	public String getDeliveryRemark() {
		return deliveryRemark;
	}

	public void setDeliveryRemark(String deliveryRemark) {
		this.deliveryRemark = deliveryRemark;
	}

	public String getSellerStockCode() {
		return sellerStockCode;
	}

	public void setSellerStockCode(String sellerStockCode) {
		this.sellerStockCode = sellerStockCode;
	}

	public int getDeliveryDelayDay() {
		return deliveryDelayDay;
	}

	public void setDeliveryDelayDay(int deliveryDelayDay) {
		this.deliveryDelayDay = deliveryDelayDay;
	}

	public String getDeliveryDelayNotifyDate() {
		return deliveryDelayNotifyDate;
	}

	public void setDeliveryDelayNotifyDate(String deliveryDelayNotifyDate) {
		this.deliveryDelayNotifyDate = deliveryDelayNotifyDate;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getOrderDate() {
		
		if (StringUtils.isNotEmpty(orderDate)) {
			orderDate = orderDate.substring(0, orderDate.indexOf("."));
			return orderDate.replaceAll("T", " ");
		}
		
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getPayNo() {
		return payNo;
	}

	public void setPayNo(int payNo) {
		this.payNo = payNo;
	}

	public int getGroupOrderSeqno() {
		return groupOrderSeqno;
	}

	public void setGroupOrderSeqno(int groupOrderSeqno) {
		this.groupOrderSeqno = groupOrderSeqno;
	}
	
}
