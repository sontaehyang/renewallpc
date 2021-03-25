package saleson.shop.item.support;

import java.util.List;

public class OpenMarketItemParam {
	
	private List<String> optionCodes;
	private String itemUserCode;
	
	public String getItemUserCode() {
		return itemUserCode;
	}

	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}

	public List<String> getOptionCodes() {
		return optionCodes;
	}

	public void setOptionCodes(List<String> optionCodes) {
		this.optionCodes = optionCodes;
	}
	
	public int getCount() {
		
		if (this.optionCodes == null) {
			return 0;
		}
		
		return this.optionCodes.size();
	}
	
}
