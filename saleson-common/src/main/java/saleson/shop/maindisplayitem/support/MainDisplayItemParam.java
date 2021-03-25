package saleson.shop.maindisplayitem.support;

import saleson.common.utils.CommonUtils;

public class MainDisplayItemParam {
	
	private String templateId;
	private String[] mainDisplayItemIds;
	private String displayType;
	private String categoryTeamCode;
	private String bestItemDisplayType;

	/**
	 * 최소 등록가능 상품수
	 * @return
	 */
	public int getMinItemSize() {
		if (templateId.startsWith("best")) {
			return 9;
		} else if (templateId.equals("md")) {
			return 3;
		}
		
		return 0;
	}
	
	public String getBestItemDisplayType() {
		return bestItemDisplayType;
	}
	public void setBestItemDisplayType(String bestItemDisplayType) {
		this.bestItemDisplayType = bestItemDisplayType;
	}
	public String getCategoryTeamCode() {
		return categoryTeamCode;
	}
	public void setCategoryTeamCode(String categoryTeamCode) {
		this.categoryTeamCode = categoryTeamCode;
	}
	public String getDisplayType() {
		return displayType;
	}
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	public String[] getMainDisplayItemIds() {
		return CommonUtils.copy(mainDisplayItemIds);
	}
	public void setMainDisplayItemIds(String[] mainDisplayItemIds) {
		this.mainDisplayItemIds = CommonUtils.copy(mainDisplayItemIds);
	}


	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
}
