package saleson.shop.display.domain;

import saleson.shop.display.support.DisplayItemParam;
import saleson.shop.item.domain.Item;

public class DisplayItem extends Item{
	
	public DisplayItem() {}
	public DisplayItem(DisplayItemParam param) {
		this.displayGroupCode = param.getDisplayGroupCode();
		this.displaySubCode = param.getDisplaySubCode();
		this.viewTarget = param.getViewTarget();
	}
	
	private String displayGroupCode;
	private String displaySubCode;
	private String viewTarget;
	private int itemId;
	private int ordering;
	private String createdDate;
	
	public String getDisplayGroupCode() {
		return displayGroupCode;
	}
	public void setDisplayGroupCode(String displayGroupCode) {
		this.displayGroupCode = displayGroupCode;
	}
	public String getDisplaySubCode() {
		return displaySubCode;
	}
	public void setDisplaySubCode(String displaySubCode) {
		this.displaySubCode = displaySubCode;
	}
	public String getViewTarget() {
		return viewTarget;
	}
	public void setViewTarget(String viewTarget) {
		this.viewTarget = viewTarget;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
}