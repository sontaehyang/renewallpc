package saleson.shop.item.domain;

import com.onlinepowers.framework.util.NumberUtils;

public class ItemAddOption extends Item {

	private int addItemOptionId;
	private String addOptionName1;
	private String addOptionName2;
	private String addOptionName3;
	private int textOptionCount;
	private String addOptionStockFlag;
	private String addOptionSoldOutFlag;
	private int addOptionStockQuantity;
	private int addOptionSalePrice;
	
	public String getAddOptionStockFlag() {
		return addOptionStockFlag;
	}
	public void setAddOptionStockFlag(String addOptionStockFlag) {
		this.addOptionStockFlag = addOptionStockFlag;
	}
	public int getAddOptionStockQuantity() {
		return addOptionStockQuantity;
	}
	public void setAddOptionStockQuantity(int addOptionStockQuantity) {
		this.addOptionStockQuantity = addOptionStockQuantity;
	}
	public int getAddItemOptionId() {
		return addItemOptionId;
	}
	public void setAddItemOptionId(int addItemOptionId) {
		this.addItemOptionId = addItemOptionId;
	}
	public String getAddOptionName1() {
		return addOptionName1;
	}
	public void setAddOptionName1(String addOptionName1) {
		this.addOptionName1 = addOptionName1;
	}
	public String getAddOptionName2() {
		return addOptionName2;
	}
	public void setAddOptionName2(String addOptionName2) {
		this.addOptionName2 = addOptionName2;
	}
	public String getAddOptionName3() {
		return addOptionName3;
	}
	public void setAddOptionName3(String addOptionName3) {
		this.addOptionName3 = addOptionName3;
	}
	public int getTextOptionCount() {
		return textOptionCount;
	}
	public void setTextOptionCount(int textOptionCount) {
		this.textOptionCount = textOptionCount;
	}
	public int getAddOptionSalePrice() {
		return addOptionSalePrice;
	}
	public void setAddOptionSalePrice(int addOptionSalePrice) {
		this.addOptionSalePrice = addOptionSalePrice;
	}
	public String getAddOptionSoldOutFlag() {
		return addOptionSoldOutFlag;
	}
	public void setAddOptionSoldOutFlag(String addOptionSoldOutFlag) {
		this.addOptionSoldOutFlag = addOptionSoldOutFlag;
	}
	
	public String getItemKey() {
		if ("Y".equals(getItemOptionFlag()) && ("S2".equals(getItemOptionType()) || "S3".equals(getItemOptionType()))) {
			return "item_" + getItemId() + "_" + getAddItemOptionId();
		}
		
		return "item_" + getItemId();
	}
	
	public String getSoldOutFlag() {
		
		// 품절?
		if ("1".equals(getSoldOut())) {
			return "Y";
		}
		
		// 옵션사용
		if ("Y".equals(getItemOptionFlag())) {
			
			if ("Y".equals(getAddOptionSoldOutFlag())) {
				return "Y";
			}
			
			if (!"S".equals(getItemOptionType()) && "Y".equals(getAddOptionStockFlag())) {
				if (getAddOptionStockQuantity() <= 0) {
					return "Y";
				}
			}
		} else {
			if ("Y".equals(getStockFlag())) {
				if (getStockQuantity() <= 0) {
					return "Y";
				}
			}
		}
		
		return "N";
	}
	
	public String getStockText() {
		if ("Y".equals(getSoldOutFlag())) {
			return "<label class=\"tip\">[품절]</label>";
		} else {
			if ("Y".equals(getItemOptionFlag())) {
				if ("S".equals(getItemOptionType())) {
					return "옵션에서 확인";
				} else {
					if (getAddOptionStockQuantity() > 0) {
						return "재고량 : " + NumberUtils.formatNumber(getAddOptionStockQuantity(), "#,##0") + "개";
					}
				}
			} else {
				if (getStockQuantity() > 0) {
					return "재고량 : " + NumberUtils.formatNumber(getStockQuantity(), "#,##0") + "개";
				}
			}
		}
		
		return "";
	}
	
	public String getItemOptionDesc() {
		
		if ("S2".equals(getItemOptionType())) {
			return getAddOptionName1() + " / " + getAddOptionName2();
		}
		
		if ("S3".equals(getItemOptionType())) {
			return getAddOptionName1() + " / " + getAddOptionName2() + " / " + getAddOptionName3();
		}
		
		return "";
	}
	
	public int getItemSalePrice() {
		return getPresentPrice() + getAddOptionSalePrice();
	}
}
