package saleson.shop.notice.domain;

public class NoticeSeller {
	
	private int noticeSellerId;
	private int noticeId;
	private long sellerId;
	private String createdDate;
	private String sellerName;
	
	public int getNoticeSellerId() {
		return noticeSellerId;
	}
	public void setNoticeSellerId(int noticeSellerId) {
		this.noticeSellerId = noticeSellerId;
	}
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	
}
