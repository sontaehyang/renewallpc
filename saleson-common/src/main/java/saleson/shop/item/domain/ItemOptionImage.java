package saleson.shop.item.domain;

public class ItemOptionImage {
	private int itemOptionImageId;
	private int itemOptionId;
	private int itemId;
	private String optionName;
	private String optionImage;
	
	public int getItemOptionImageId() {
		return itemOptionImageId;
	}
	public void setItemOptionImageId(int itemOptionImageId) {
		this.itemOptionImageId = itemOptionImageId;
	}
	public int getItemOptionId() {
		return itemOptionId;
	}
	public void setItemOptionId(int itemOptionId) {
		this.itemOptionId = itemOptionId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public String getOptionImage() {
		return optionImage;
	}
	public void setOptionImage(String optionImage) {
		this.optionImage = optionImage;
	}
}
