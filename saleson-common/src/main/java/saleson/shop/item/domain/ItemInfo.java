package saleson.shop.item.domain;

public class ItemInfo {
	private int itemInfoId;
	private int itemId;
	private String itemNoticeCode;
	private String infoCode;
	private String title = "";
	private String description = "";
	private String createdDate;
	
	private String itemUserCode;		// 엑셀용 
	private String itemName;			// 엑셀용
	private String detailContent;		// 엑셀용
	private String headerContentFlag;	// 엑셀용
	private String footerContentFlag;	// 엑셀용

	public int getItemInfoId() {
		return itemInfoId;
	}
	public void setItemInfoId(int itemInfoId) {
		this.itemInfoId = itemInfoId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public String getInfoCode() {
		return infoCode;
	}
	public void setInfoCode(String infoCode) {
		this.infoCode = infoCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getDetailContent() {
		return detailContent;
	}
	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}
	public String getItemNoticeCode() {
		return itemNoticeCode;
	}
	public void setItemNoticeCode(String itemNoticeCode) {
		this.itemNoticeCode = itemNoticeCode;
	}

	public String getHeaderContentFlag() {
		return headerContentFlag;
	}

	public void setHeaderContentFlag(String headerContentFlag) {
		this.headerContentFlag = headerContentFlag;
	}

	public String getFooterContentFlag() {
		return footerContentFlag;
	}

	public void setFooterContentFlag(String footerContentFlag) {
		this.footerContentFlag = footerContentFlag;
	}
}
