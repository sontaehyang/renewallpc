package saleson.shop.order.pg.payco.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayApprovalResult {

	private String sellerOrderReferenceKey; // 가맹점 key
	private String reserveOrderNo; // 주문 예약 번호
	private String orderNo; // 주문번호
	private String memberName; // 주문자명
	private String memberEmail; // 주문자 Email
	private String orderChannel; // 주문 채널
	private int totalOrderAmt; // 총 주문금액
	private int totalDeliveryFeeAmt; // 총 배송비
	private int totalRemoteAreaDeliveryFeeAmt; // 총 도서산간비
	private int totalPaymentAmt; // 총 결제금액
	private String paymentCompletionYn; // 결제완료여부(Y/N)
	
	private List<PayProduct> orderProducts;
	private List<PaymentDetail> paymentDetails; // 결제내역 List
	private String orderCertifyKey; // 주문 인증키
	
	public String getOrderCertifyKey() {
		return orderCertifyKey;
	}
	public void setOrderCertifyKey(String orderCertifyKey) {
		this.orderCertifyKey = orderCertifyKey;
	}
	public String getSellerOrderReferenceKey() {
		return sellerOrderReferenceKey;
	}
	public void setSellerOrderReferenceKey(String sellerOrderReferenceKey) {
		this.sellerOrderReferenceKey = sellerOrderReferenceKey;
	}
	public String getReserveOrderNo() {
		return reserveOrderNo;
	}
	public void setReserveOrderNo(String reserveOrderNo) {
		this.reserveOrderNo = reserveOrderNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getOrderChannel() {
		return orderChannel;
	}
	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}
	public int getTotalOrderAmt() {
		return totalOrderAmt;
	}
	public void setTotalOrderAmt(int totalOrderAmt) {
		this.totalOrderAmt = totalOrderAmt;
	}
	public int getTotalDeliveryFeeAmt() {
		return totalDeliveryFeeAmt;
	}
	public void setTotalDeliveryFeeAmt(int totalDeliveryFeeAmt) {
		this.totalDeliveryFeeAmt = totalDeliveryFeeAmt;
	}
	public int getTotalRemoteAreaDeliveryFeeAmt() {
		return totalRemoteAreaDeliveryFeeAmt;
	}
	public void setTotalRemoteAreaDeliveryFeeAmt(int totalRemoteAreaDeliveryFeeAmt) {
		this.totalRemoteAreaDeliveryFeeAmt = totalRemoteAreaDeliveryFeeAmt;
	}
	public int getTotalPaymentAmt() {
		return totalPaymentAmt;
	}
	public void setTotalPaymentAmt(int totalPaymentAmt) {
		this.totalPaymentAmt = totalPaymentAmt;
	}
	public String getPaymentCompletionYn() {
		return paymentCompletionYn;
	}
	public void setPaymentCompletionYn(String paymentCompletionYn) {
		this.paymentCompletionYn = paymentCompletionYn;
	}
	public List<PaymentDetail> getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	public List<PayProduct> getOrderProducts() {
		return orderProducts;
	}
	public void setOrderProducts(List<PayProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}
	
}
