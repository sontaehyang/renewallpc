package saleson.shop.item.domain;

public class ExcelItemRelation {
	private String itemUserCode;
	private String itemName;
	private String relationItemUserCode;
	private String relationItemName;
	private int ordering;
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
	public String getRelationItemUserCode() {
		return relationItemUserCode;
	}
	public void setRelationItemUserCode(String relationItemUserCode) {
		this.relationItemUserCode = relationItemUserCode;
	}
	public String getRelationItemName() {
		return relationItemName;
	}
	public void setRelationItemName(String relationItemName) {
		this.relationItemName = relationItemName;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
}
