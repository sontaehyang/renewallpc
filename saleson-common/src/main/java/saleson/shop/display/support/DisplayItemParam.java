package saleson.shop.display.support;

import saleson.common.utils.CommonUtils;

import java.util.List;

@SuppressWarnings("serial")
public class DisplayItemParam extends DisplayParam {

	private String conditionType;
	private String[] displayItemIds;
	
	private String listType;

	// 전용상품 구분
	private List<String> privateTypes;
	private String privateType;

	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String[] getDisplayItemIds() {
		return CommonUtils.copy(displayItemIds);
	}
	public void setDisplayItemIds(String[] displayItemIds) {
		this.displayItemIds = CommonUtils.copy(displayItemIds);
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}


	public List<String> getPrivateTypes() {
		return privateTypes;
	}

	public void setPrivateTypes(List<String> privateTypes) {
		this.privateTypes = privateTypes;
	}

	public String getPrivateType() {
		return privateType;
	}

	public void setPrivateType(String privateType) {
		this.privateType = privateType;
	}
}
