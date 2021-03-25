package saleson.shop.point.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class PointParam extends SearchParam {
	private long userId;
	private String pointType;
	private String searchStartDate;
	private String searchEndDate;
	private String itemName;
	//LCH-PointParam point 사용/적립구분      <추가>
	private String plusminus; 
	private String groupCode;
	private String levelId;
	
	public String getGroupCode() {
		return groupCode;
	}
	
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	//LCH-PointParam point 사용/적립구분      <추가>

	public String getPlusminus() {
		return plusminus;
	}

	public void setPlusminus(String plusminus) {
		this.plusminus = plusminus;
	}
	
	
}
