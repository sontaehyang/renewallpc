package saleson.shop.featured.support;

import java.util.Arrays;
import saleson.common.utils.CommonUtils;

public class FeaturedItemParam {
	
	private String featuredId;
	private String[] mainDisplayItemIds;
	private String displayType;
	private String categoryTeamCode;
	private String categoryCode;
	private String prodString="";
	private String[] userGroup;
	
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	private String bestItemDisplayType;

	/**
	 * 최소 등록가능 상품수
	 * @return
	 */
	public int getMinItemSize() {
		if (featuredId.startsWith("best")) {
			return 9;
		} else if (featuredId.equals("md")) {
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


	public String getFeaturedId() {
		return featuredId;
	}

	public void setFeaturedId(String featuredId) {
		this.featuredId = featuredId;
	}

	public String getProdString() {
		return prodString;
	}

	public void setProdString(String prodString) {
		this.prodString = prodString;
	}

	public String[] getUserGroup() {
		return CommonUtils.copy(userGroup);
	}

	public void setUserGroup(String[] userGroup) {
		this.userGroup = CommonUtils.copy(userGroup);
	}

	@Override
	public String toString() {
		return "FeaturedItemParam [featuredId=" + featuredId + ", mainDisplayItemIds="
				+ Arrays.toString(mainDisplayItemIds) + ", displayType=" + displayType + ", categoryTeamCode="
				+ categoryTeamCode + ", categoryCode=" + categoryCode + ", prodString=" + prodString + ", userGroup="
				+ Arrays.toString(userGroup) + ", bestItemDisplayType=" + bestItemDisplayType + "]";
	}
}
