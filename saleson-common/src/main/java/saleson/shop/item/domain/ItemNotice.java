package saleson.shop.item.domain;

public class ItemNotice {
	private String itemNoticeCode;
	private String itemNoticeTitle;
	private String noticeTitle;
	private String noticeDescription;
	private int ordering;
	public String getItemNoticeCode() {
		return itemNoticeCode;
	}
	public void setItemNoticeCode(String itemNoticeCode) {
		this.itemNoticeCode = itemNoticeCode;
	}
	public String getItemNoticeTitle() {
		return itemNoticeTitle;
	}
	public void setItemNoticeTitle(String itemNoticeTitle) {
		this.itemNoticeTitle = itemNoticeTitle;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeDescription() {
		return noticeDescription;
	}
	public void setNoticeDescription(String noticeDescription) {
		this.noticeDescription = noticeDescription;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
}
