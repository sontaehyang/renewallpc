package saleson.shop.item.domain;

import saleson.common.utils.ShopUtils;

public class ItemOption {
	private int itemOptionId;
	private int itemId;
	private String optionType = "";
	private String optionDisplayType = "";
	private String optionHideFlag = "";
	private String optionName1 = "";
	private String optionName2 = "";
	private String optionName3 = "";
	private String optionStockCode = "";
	private int optionPrice;
	private int optionPriceNonmember;
	private int optionQuantity = 1;
	private String optionStockFlag;
	private String optionSoldOutFlag;
	private int optionStockQuantity = -1;
	private String optionStockScheduleText = "";
	private String optionStockScheduleDate = "";
	private String optionDisplayFlag = "Y";
	private int optionOrdering;
	private long createdUserId;
	private String createdDate;
	private int optionCostPrice;
	private int price;
	private String erpItemCode;
	
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
	public String getOptionType() {
		return optionType;
	}
	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}
	
	public String getOptionHideFlag() {
		return optionHideFlag;
	}
	public void setOptionHideFlag(String optionHideFlag) {
		this.optionHideFlag = optionHideFlag;
	}
	public String getOptionName1() {
		String optionName1 = this.optionName1;

		// 옵션조합형, 프론트에서 앞자리 3개 절삭 (숫자 넣어서 ordering 하기 위함)
		if ("C".equals(this.optionType) && !ShopUtils.isOpmanagerPage() && !ShopUtils.isSellerPage()) {
			if (optionName1.length() > 3) {
				optionName1 = optionName1.substring(3);
			} else {
				optionName1 = "";
			}
		}

		return optionName1;
	}
	public void setOptionName1(String optionName1) {
		this.optionName1 = optionName1;
	}
	public String getOptionName2() {
		return optionName2;
	}
	public void setOptionName2(String optionName2) {
		this.optionName2 = optionName2;
	}
	public String getOptionName3() {
		return optionName3;
	}
	public void setOptionName3(String optionName3) {
		this.optionName3 = optionName3;
	}
	public String getOptionStockCode() {
		return optionStockCode;
	}
	public void setOptionStockCode(String optionCode) {
		this.optionStockCode = optionCode;
	}
	
	public int getOptionPrice() {
		return optionPrice;
	}
	public void setOptionPrice(int price) {
		this.optionPrice = price;
	}
	public int getOptionPriceNonmember() {
		return optionPriceNonmember;
	}
	public void setOptionPriceNonmember(int priceNonmember) {
		this.optionPriceNonmember = priceNonmember;
	}
	public int getOptionStockQuantity() {
		return optionStockQuantity;
	}
	public void setOptionStockQuantity(int stockQuantity) {
		this.optionStockQuantity = stockQuantity;
	}
	public String getOptionStockScheduleText() {
		return optionStockScheduleText;
	}
	public void setOptionStockScheduleText(String stockScheduleText) {
		this.optionStockScheduleText = stockScheduleText;
	}
	public String getOptionStockScheduleDate() {
		return optionStockScheduleDate;
	}
	public void setOptionStockScheduleDate(String stockScheduleDate) {
		this.optionStockScheduleDate = stockScheduleDate;
	}
	public String getOptionDisplayFlag() {
		return optionDisplayFlag;
	}
	public void setOptionDisplayFlag(String displayFlag) {
		this.optionDisplayFlag = displayFlag;
	}
	public long getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(long createdUserId) {
		this.createdUserId = createdUserId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
	
	public String getOptionDisplayType() {
		return optionDisplayType;
	}
	public void setOptionDisplayType(String optionDisplayType) {
		this.optionDisplayType = optionDisplayType;
	}
	
	
	
	/**
	 * 추가 금액.
	 * @return
	 */
	private void setExtraPrice(int price) {}
	public int getExtraPrice() {
		//if (UserUtils.isUserLogin()) {
			return this.optionPrice;
		//} 
		//return this.priceNonmember;
	}
	
	public int getOptionCostPrice() {
		return optionCostPrice;
	}
	public void setOptionCostPrice(int optionCostPrice) {
		this.optionCostPrice = optionCostPrice;
	}
	public String getOptionStockFlag() {
		return optionStockFlag;
	}
	public void setOptionStockFlag(String optionStockFlag) {
		this.optionStockFlag = optionStockFlag;
	}
	public String getOptionSoldOutFlag() {
		return optionSoldOutFlag;
	}
	public void setOptionSoldOutFlag(String optionSoldOutFlag) {
		this.optionSoldOutFlag = optionSoldOutFlag;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	// 품절 여부 (품절상태, 재고연동유무, 재고수량 반영)
	public boolean isSoldOut() {
		if ("Y".equals(this.optionSoldOutFlag)
				|| ("Y".equals(this.optionStockFlag) && this.optionStockQuantity == 0)) {
			return true;
		} 
		
		return false;
	}

	public String getErpItemCode() {
		return erpItemCode;
	}

	public void setErpItemCode(String erpItemCode) {
		this.erpItemCode = erpItemCode;
	}

	public int getOptionQuantity() {
		return optionQuantity;
	}

	public void setOptionQuantity(int optionQuantity) {
		this.optionQuantity = optionQuantity;
	}

	public int getOptionOrdering() {
		return optionOrdering;
	}

	public void setOptionOrdering(int optionOrdering) {
		this.optionOrdering = optionOrdering;
	}
}
