package saleson.shop.item.domain;

import java.util.List;

public class ItemOptionGroup {

	private int itemId;
	private String optionType;
	private String optionTitle;
	private String optionDisplayType;	// 옵션 출력방법.(select, radio)
	private String optionHideFlag;		// 옵션 숨김 
	
	private List<ItemOption> itemOptions;
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getOptionType() {
		return optionType;
	}
	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}
	public String getOptionTitle() {
		return optionTitle;
	}
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	public List<ItemOption> getItemOptions() {
		return itemOptions;
	}
	public void setItemOptions(List<ItemOption> itemOptions) {
		this.itemOptions = itemOptions;
	}
	public String getOptionDisplayType() {
		return optionDisplayType;
	}
	public void setOptionDisplayType(String optionDisplayType) {
		this.optionDisplayType = optionDisplayType;
	}
	public String getOptionHideFlag() {
		return optionHideFlag;
	}
	public void setOptionHideFlag(String optionHideFlag) {
		this.optionHideFlag = optionHideFlag;
	}
	
}
