package saleson.shop.mall.support;

import org.springframework.util.StringUtils;

import com.onlinepowers.framework.util.DateUtils;
import saleson.common.utils.CommonUtils;

public class MallConfigParam {
	private String dataStatusCode;

	private int[] id;
	
	private String searchStartDate;
	private String searchStartDateTime;
	
	private String searchEndDate;
	private String searchEndDateTime;
	
	private String collectTargetDefault;
	private String collectTargetClaim;
	
	public String getCollectTargetDefault() {
		return collectTargetDefault;
	}

	public void setCollectTargetDefault(String collectTargetDefault) {
		this.collectTargetDefault = collectTargetDefault;
	}


	public String getCollectTargetClaim() {
		return collectTargetClaim;
	}

	public void setCollectTargetClaim(String collectTargetClaim) {
		this.collectTargetClaim = collectTargetClaim;
	}

	public String getSearchStartDate() {
		
		if (StringUtils.isEmpty(searchStartDate)) {
			return DateUtils.getToday();
		}
		
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		
		if (StringUtils.isEmpty(searchEndDate)) {
			return DateUtils.getToday();
		}
		
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public int[] getId() {
		return CommonUtils.copy(id);
	}

	public void setId(int[] id) {
		this.id = CommonUtils.copy(id);
	}

	public String getDataStatusCode() {
		return dataStatusCode;
	}

	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}

	public String getSearchStartDateTime() {
		if (StringUtils.isEmpty(searchStartDateTime)) {
			return "00";
		}
		
		return searchStartDateTime;
	}

	public void setSearchStartDateTime(String searchStartDateTime) {
		this.searchStartDateTime = searchStartDateTime;
	}

	public String getSearchEndDateTime() {
		if (StringUtils.isEmpty(searchEndDateTime)) {
			return "23";
		}
		
		return searchEndDateTime;
	}

	public void setSearchEndDateTime(String searchEndDateTime) {
		this.searchEndDateTime = searchEndDateTime;
	}
	
}
