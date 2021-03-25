package saleson.shop.mall.auction.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import saleson.shop.mall.domain.MallBase;

@XmlRootElement(name="ExchangeBase")
@XmlAccessorType (XmlAccessType.FIELD)
public class ExchangeBase {

	// 교환 신청일 
	@XmlAttribute(name="ExchangeRequestDate")
	private String exchangeRequestDate;
	
	// 교환 도착일(교환 도착일이 존재하지 않을 경우 9999년 12월 30일로 조회 됨) 
	@XmlAttribute(name="ExchangeArrivalDate")
	private String exchangeArrivalDate;
	
	// 상품번호
	@XmlAttribute(name="ItemID")
	private String itemId;
	
	// 주문번호 
	@XmlAttribute(name="OrderNo")
	private int orderNo;

	// 상품명 
	@XmlAttribute(name="ItemName")
	private int itemName;
	
	// 구매자 
	@XmlAttribute(name="BuyerName")
	private int buyerName;
	
	// 수령자 이름 
	@XmlAttribute(name="ReceiverName")
	private int receiverName;
	
	// 구매 금액 
	@XmlAttribute(name="AwardAmount")
	private BigDecimal awardAmount;
	
	// 구매수량
	@XmlAttribute(name="Quantity")
	private int quantity;
	
	// 교환 도착 경과일 
	@XmlAttribute(name="ExchangeArrivalOverDay")
	private String exchangeArrivalOverDay;
	
	/**
	 * 반품 사유 코드
	 * ChangeOfMind : 상품이상 없으나 구매의사 없음(구매자 변심) 
	 * GoodsFault : 상품에 결함이 있음
	 * NotArrived : 상품이 도착하고 있지 않음 
	 * NotDelivery : 판매자가 상품을 발송하지 않음
	 * WrongInformation : 도착한 상품이 상품상세정보와 틀림 
	 * WrongSizeColor : 사이즈 또는 색상등을 잘못 선택함 
	 */
	@XmlAttribute(name="ReturnReasonCode")
	private String returnReasonCode;
	
	// 반품사유 상세 
	@XmlAttribute(name="ReturnDetailReason")
	private String returnDetailReason;

	/**
	 * 반품발송방법 
	 * Direct : 직접전달
	 * Door2Door : 택배 발송
	 * Etc : 기타
	 * EtcDoor2Door : 기타 택배
	 * GoodsFlow : 굿스플로발송 
	 * Parcel : 소포/등기
	 * Post : 일반 우편
	 * QuickService : 퀵서비스
	 * Truck : 화물 배달
	 */
	@XmlAttribute(name="ReturnDeliveryMethod")
	private String returnDeliveryMethod;
	
	// 구매자메모 
	@XmlAttribute(name="BuyerRequestMemo")
	private String buyerRequestMemo;
	
	// 반품택배사명
	@XmlAttribute(name="DeliveryCompanyName")
	private String deliveryCompanyName;
	
	// 반품운송장번호
	@XmlAttribute(name="DeliveryNo")
	private String deliveryNo;
	
	/**
	 * 트러스트셀러무료반품 
	 * NonTrustSeller : 트러스트셀러무료반품미적용 
	 * TrustSeller : 트러스트셀러무료반품적용 
	 */
	@XmlAttribute(name="TrustSellerType")
	private String trustSellerType;
	
	/**
	 * 반품배송비송금방법 
	 * Arbitration : 옥션중재 
	 * Door2Door : 택배기사님에게송금 
	 * Etc : 기타
	 * NotChoice : 선택불가 
	 * Now : 지금결제함 
	 * Seller : 판매자에게직접송금 
	 * SubtractionRefund : 환불금액에서차감 
	 */
	@XmlAttribute(name="PaymentChoice")
	private String paymentChoice;
	
	@XmlElement(name="Buyer", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private Buyer buyer;

	private int mallConfigId; 
	private int mallOrderId;
	private String exchangeStatus;
	
	public String getExchangeStatus() {
		return exchangeStatus;
	}

	public void setExchangeStatus(String exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}

	public int getMallOrderId() {
		return mallOrderId;
	}

	public void setMallOrderId(int mallOrderId) {
		this.mallOrderId = mallOrderId;
	}

	public int getMallConfigId() {
		return mallConfigId;
	}

	public void setMallConfigId(int mallConfigId) {
		this.mallConfigId = mallConfigId;
	}

	public String getExchangeRequestDate() {
		return exchangeRequestDate;
	}

	public void setExchangeRequestDate(String exchangeRequestDate) {
		this.exchangeRequestDate = exchangeRequestDate;
	}

	public String getExchangeArrivalDate() {
		return exchangeArrivalDate;
	}

	public void setExchangeArrivalDate(String exchangeArrivalDate) {
		this.exchangeArrivalDate = exchangeArrivalDate;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getItemName() {
		return itemName;
	}

	public void setItemName(int itemName) {
		this.itemName = itemName;
	}

	public int getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(int buyerName) {
		this.buyerName = buyerName;
	}

	public int getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(int receiverName) {
		this.receiverName = receiverName;
	}

	public BigDecimal getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getExchangeArrivalOverDay() {
		return exchangeArrivalOverDay;
	}

	public void setExchangeArrivalOverDay(String exchangeArrivalOverDay) {
		this.exchangeArrivalOverDay = exchangeArrivalOverDay;
	}

	public String getReturnReasonCode() {
		return returnReasonCode;
	}

	public void setReturnReasonCode(String returnReasonCode) {
		this.returnReasonCode = returnReasonCode;
	}

	public String getReturnDetailReason() {
		return returnDetailReason;
	}

	public void setReturnDetailReason(String returnDetailReason) {
		this.returnDetailReason = returnDetailReason;
	}

	public String getReturnDeliveryMethod() {
		return returnDeliveryMethod;
	}

	public void setReturnDeliveryMethod(String returnDeliveryMethod) {
		this.returnDeliveryMethod = returnDeliveryMethod;
	}

	public String getBuyerRequestMemo() {
		return buyerRequestMemo;
	}

	public void setBuyerRequestMemo(String buyerRequestMemo) {
		this.buyerRequestMemo = buyerRequestMemo;
	}

	public String getDeliveryCompanyName() {
		return deliveryCompanyName;
	}

	public void setDeliveryCompanyName(String deliveryCompanyName) {
		this.deliveryCompanyName = deliveryCompanyName;
	}

	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public String getTrustSellerType() {
		return trustSellerType;
	}

	public void setTrustSellerType(String trustSellerType) {
		this.trustSellerType = trustSellerType;
	}

	public String getPaymentChoice() {
		return paymentChoice;
	}

	public void setPaymentChoice(String paymentChoice) {
		this.paymentChoice = paymentChoice;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	
}
