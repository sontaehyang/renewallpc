package saleson.shop.item.domain;

public class ExcelItemCheck {
	private String itemUserCode;
	private String itemName = "";
	
	private String team = "";
	private String displayFlag = "";
	
	private String seoNoIndexDisplayFlag = "";
	private String itemLabel = "";
	private String itemType = "";
	
	private int stockQuantity;
	private String itemLabelSoldOut = "";
	private String stockScheduleType = "";
	private String stockScheduleDate = "";
	private String stockScheduleText = "";
	
	private String itemNewFlag = "";
	
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}
	public String getSeoNoIndexDisplayFlag() {
		return seoNoIndexDisplayFlag;
	}
	public void setSeoNoIndexDisplayFlag(String seoNoIndexDisplayFlag) {
		this.seoNoIndexDisplayFlag = seoNoIndexDisplayFlag;
	}
	public String getItemLabel() {
		return itemLabel;
	}
	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public String getItemLabelSoldOut() {
		return itemLabelSoldOut;
	}
	public void setItemLabelSoldOut(String itemLabelSoldOut) {
		this.itemLabelSoldOut = itemLabelSoldOut;
	}
	public String getStockScheduleType() {
		return stockScheduleType;
	}
	public void setStockScheduleType(String stockScheduleType) {
		this.stockScheduleType = stockScheduleType;
	}
	public String getStockScheduleDate() {
		return stockScheduleDate;
	}
	public void setStockScheduleDate(String stockScheduleDate) {
		this.stockScheduleDate = stockScheduleDate;
	}
	public String getStockScheduleText() {
		return stockScheduleText;
	}
	public void setStockScheduleText(String stockScheduleText) {
		this.stockScheduleText = stockScheduleText;
	}
	public String getItemNewFlag() {
		return itemNewFlag;
	}
	public void setItemNewFlag(String itemNewFlag) {
		this.itemNewFlag = itemNewFlag;
	}
	
}
