package saleson.shop.item.domain;

import saleson.shop.point.domain.PointConfig;

public class ExcelItemPointConfig extends PointConfig {
	private String itemUserCode;
	private String itemName;
	
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
}
