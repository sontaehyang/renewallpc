package saleson.shop.order.pg.payco.domain;

public class CancelProduct {
	private String sellerOrderProductReferenceKey; // 외부가맹점에서 관리하는 주문상품번호
	private String cpId; // 상점 ID
	private String productId; // 상품 ID
	private int productAmt; // 취소 할 주문상품 금액
	private String cancelDetailContent; // 취소 상세 사유 (필수 아님)
	
	public String getSellerOrderProductReferenceKey() {
		return sellerOrderProductReferenceKey;
	}
	public void setSellerOrderProductReferenceKey(
			String sellerOrderProductReferenceKey) {
		this.sellerOrderProductReferenceKey = sellerOrderProductReferenceKey;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getProductAmt() {
		return productAmt;
	}
	public void setProductAmt(int productAmt) {
		this.productAmt = productAmt;
	}
	public String getCancelDetailContent() {
		return cancelDetailContent;
	}
	public void setCancelDetailContent(String cancelDetailContent) {
		this.cancelDetailContent = cancelDetailContent;
	}
	
	
}
