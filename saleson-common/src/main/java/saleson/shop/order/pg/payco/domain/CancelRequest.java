package saleson.shop.order.pg.payco.domain;

import java.util.List;

public class CancelRequest {
	private String sellerKey; // 가맹점 코드
	private String orderNo; // 주문번호 (TID!!) 
	private int cancelTotalAmt; // 취소할 총금액
	private int totalCancelTaxfreeAmt; // 총 취소할 면세금액 (필수 아님)
	private int totalCancelTaxableAmt; // 총 취소할 과세금액 (필수 아님)
	private int totalCancelVatAmt; // 총 취소할 부가세 (필수 아님)
	private int totalCancelPossibleAmt; // 총 취소가능금액 (현재기준) : 취소가능금액 검증 (필수 아님)
	private List<CancelProduct> orderProducts; // 취소할 상품 리스트 (값이 없으면 전체 취소) (필수아님)
	private String requestMemo; // 취소처리 요청메모
	private String orderCertifyKey;
	
	public String getSellerKey() {
		return sellerKey;
	}
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getCancelTotalAmt() {
		return cancelTotalAmt;
	}
	public void setCancelTotalAmt(int cancelTotalAmt) {
		this.cancelTotalAmt = cancelTotalAmt;
	}
	public int getTotalCancelTaxfreeAmt() {
		return totalCancelTaxfreeAmt;
	}
	public void setTotalCancelTaxfreeAmt(int totalCancelTaxfreeAmt) {
		this.totalCancelTaxfreeAmt = totalCancelTaxfreeAmt;
	}
	public int getTotalCancelTaxableAmt() {
		return totalCancelTaxableAmt;
	}
	public void setTotalCancelTaxableAmt(int totalCancelTaxableAmt) {
		this.totalCancelTaxableAmt = totalCancelTaxableAmt;
	}
	public int getTotalCancelVatAmt() {
		return totalCancelVatAmt;
	}
	public void setTotalCancelVatAmt(int totalCancelVatAmt) {
		this.totalCancelVatAmt = totalCancelVatAmt;
	}
	public int getTotalCancelPossibleAmt() {
		return totalCancelPossibleAmt;
	}
	public void setTotalCancelPossibleAmt(int totalCancelPossibleAmt) {
		this.totalCancelPossibleAmt = totalCancelPossibleAmt;
	}
	public List<CancelProduct> getOrderProducts() {
		return orderProducts;
	}
	public void setOrderProducts(List<CancelProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}
	public String getRequestMemo() {
		return requestMemo;
	}
	public void setRequestMemo(String requestMemo) {
		this.requestMemo = requestMemo;
	}
	public String getOrderCertifyKey() {
		return orderCertifyKey;
	}
	public void setOrderCertifyKey(String orderCertifyKey) {
		this.orderCertifyKey = orderCertifyKey;
	}
	
}
