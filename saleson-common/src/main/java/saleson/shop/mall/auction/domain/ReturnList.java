package saleson.shop.mall.auction.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import saleson.shop.mall.domain.MallBase;

@XmlRootElement(name="ReturnList")
@XmlAccessorType (XmlAccessType.FIELD)
public class ReturnList extends MallBase {

	/**
	 * Requested : 요청
	 * Hold : 보류
	 * Reject : 거부
	 */
	@XmlAttribute(name="ReturnStatus")
	private String returnStatus;
	
	// 반품요청일 
	@XmlAttribute(name="RequestedDate")
	private String requestedDate;
	
	// 반품일 
	@XmlAttribute(name="ReturnDate")
	private String returnDate;
	
	// 반품도착일
	@XmlAttribute(name="ReturnArrivalDate")
	private String returnArrivalDate;
	
	// 결제번호
	@XmlAttribute(name="PayNo")
	private int payNo;
	
	// 수령자 이름
	@XmlAttribute(name="RecieverName")
	private String recieverName;
	
	/**
	 * 최초 발송비 부담
	 * AuctionCharge : 옥션 수수료 부담
	 * BuyerCharge  : 구매자 수수료 부담
	 * SellerCharge  : 판매자 수수료 부담
	 */
	@XmlAttribute(name="FirstDeliveryFeeCharge")
	private String firstDeliveryFeeCharge;
	
	/**
	 * 반품 배송비 부담
	 * AuctionCharge : 옥션 수수료 부담
	 * BuyerCharge  : 구매자 수수료 부담
	 * SellerCharge  : 판매자 수수료 부담
	 */
	@XmlAttribute(name="ReturnFeeCharge")
	private String returnFeeCharge;
	
	/**
	 * 반품신청 주체
	 * Buyer : 구매자
	 * ReturnByCall : 전화 반품
	 * Seller : 판매자
	 */
	@XmlAttribute(name="ReturnRequester")
	private String returnRequester;
	
	/**
	 * 반품사유코드 
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
	
	// 반품송장번호
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
	
	/**
	 * 반품/교환 Pickup Type 
	 * Auction : 기존반품
	 * SellerDirect : 자체수거
	 */
	@XmlAttribute(name="RetPickupType")
	private String retPickupType;
	
	@XmlAttribute(name="ReturnFeeAmount")
	private BigDecimal returnFeeAmount;
	
	@XmlElement(name="Buyer", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private Buyer buyer;
	
	@XmlElement(name="Order", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
	private Order order;
	
	public int getIntReturnFeeAmount() {
		if (returnFeeAmount == null) {
			return 0;
		}
		
		return returnFeeAmount.intValue();
	}
	
	public BigDecimal getReturnFeeAmount() {
		return returnFeeAmount;
	}

	public void setReturnFeeAmount(BigDecimal returnFeeAmount) {
		this.returnFeeAmount = returnFeeAmount;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public String getRequestedDate() {
		
		if (StringUtils.isNotEmpty(requestedDate)) {
			requestedDate = requestedDate.substring(0, requestedDate.indexOf("."));
			return requestedDate.replaceAll("T", " ");
		}
		
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getReturnArrivalDate() {
		return returnArrivalDate;
	}

	public void setReturnArrivalDate(String returnArrivalDate) {
		this.returnArrivalDate = returnArrivalDate;
	}

	public int getPayNo() {
		return payNo;
	}

	public void setPayNo(int payNo) {
		this.payNo = payNo;
	}

	public String getRecieverName() {
		return recieverName;
	}

	public void setRecieverName(String recieverName) {
		this.recieverName = recieverName;
	}

	public String getFirstDeliveryFeeCharge() {
		return firstDeliveryFeeCharge;
	}

	public void setFirstDeliveryFeeCharge(String firstDeliveryFeeCharge) {
		this.firstDeliveryFeeCharge = firstDeliveryFeeCharge;
	}

	public String getReturnFeeCharge() {
		return returnFeeCharge;
	}

	public void setReturnFeeCharge(String returnFeeCharge) {
		this.returnFeeCharge = returnFeeCharge;
	}

	public String getReturnRequester() {
		return returnRequester;
	}

	public void setReturnRequester(String returnRequester) {
		this.returnRequester = returnRequester;
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

	public String getRetPickupType() {
		return retPickupType;
	}

	public void setRetPickupType(String retPickupType) {
		this.retPickupType = retPickupType;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}
