package saleson.shop.search.support;

import com.onlinepowers.framework.web.domain.SearchParam;
import saleson.common.utils.CommonUtils;

@SuppressWarnings("serial")
public class SearchRecommendParam extends SearchParam {
	private String[] id;
	private String searchContents;
	private String searchStartDate;
	private String searchEndDate;
	private String displayFlag;

	public String[] getId() {
		return CommonUtils.copy(id);
	}
	public void setId(String[] id) {
		this.id = CommonUtils.copy(id);
	}
	public String getSearchContents() {
		return searchContents;
	}
	public void setSearchContents(String searchContents) {
		this.searchContents = searchContents;
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
	public String getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}
}
