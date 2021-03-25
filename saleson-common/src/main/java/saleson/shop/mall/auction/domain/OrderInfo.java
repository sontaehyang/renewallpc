package saleson.shop.mall.auction.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="OrderInfoList", namespace="http://schema.auction.co.kr/Arche.APISvc.xsd")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInfo {
	
	@XmlAttribute(name="OrderNo")
	private int orderNo;	// 주문번호
	
	@XmlAttribute(name="OrderStatus")
	private String orderStatus; // 주문 상태
	
	@XmlAttribute(name="ItemNo")
	private String itemNo; // 주문 상태
	
	@XmlAttribute(name="RecpDate")
	private String recpDate; // 주문 일자
	
	@XmlAttribute(name="OrderConfirmDate")
	private String orderConfirmDate; // 주문 확인 일자
	
	@XmlAttribute(name="DeliverySendDate")
	private String deliverySendDate; // 배송 시작 일자
	
	@XmlAttribute(name="DeliveryFinishDate")
	private String deliveryFinishDate; // 배송 완료 일자
	
	@XmlAttribute(name="AcctDate")
	private String acctDate; 
	
	@XmlAttribute(name="RemitDate")
	private String remitDate; 
	
	@XmlAttribute(name="RetApprDate")
	private String retApprDate; 
	
	@XmlAttribute(name="CancDate")
	private String cancDate; 
	
	@XmlAttribute(name="BidSeqNo")
	private String bidSeqNo; 
	
	@XmlAttribute(name="ReturnDate")
	private String returnDate; // 반품 일자
	
	@XmlAttribute(name="ExchangeDate")
	private String exchangeDate; // 교환 일자

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getRecpDate() {
		return recpDate;
	}

	public void setRecpDate(String recpDate) {
		this.recpDate = recpDate;
	}

	public String getOrderConfirmDate() {
		return orderConfirmDate;
	}

	public void setOrderConfirmDate(String orderConfirmDate) {
		this.orderConfirmDate = orderConfirmDate;
	}

	public String getDeliverySendDate() {
		return deliverySendDate;
	}

	public void setDeliverySendDate(String deliverySendDate) {
		this.deliverySendDate = deliverySendDate;
	}

	public String getDeliveryFinishDate() {
		return deliveryFinishDate;
	}

	public void setDeliveryFinishDate(String deliveryFinishDate) {
		this.deliveryFinishDate = deliveryFinishDate;
	}

	public String getAcctDate() {
		return acctDate;
	}

	public void setAcctDate(String acctDate) {
		this.acctDate = acctDate;
	}

	public String getRemitDate() {
		return remitDate;
	}

	public void setRemitDate(String remitDate) {
		this.remitDate = remitDate;
	}

	public String getRetApprDate() {
		return retApprDate;
	}

	public void setRetApprDate(String retApprDate) {
		this.retApprDate = retApprDate;
	}

	public String getCancDate() {
		return cancDate;
	}

	public void setCancDate(String cancDate) {
		this.cancDate = cancDate;
	}

	public String getBidSeqNo() {
		return bidSeqNo;
	}

	public void setBidSeqNo(String bidSeqNo) {
		this.bidSeqNo = bidSeqNo;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getExchangeDate() {
		return exchangeDate;
	}

	public void setExchangeDate(String exchangeDate) {
		this.exchangeDate = exchangeDate;
	}

}
