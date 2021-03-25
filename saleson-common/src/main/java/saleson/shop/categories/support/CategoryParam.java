package saleson.shop.categories.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class CategoryParam extends SearchParam {
	private String categoryTeamCode = "";
	private String categoryGroupCode = "";
	private String categoryUrl = "";
	private String categoryCode = "";		// 선택된 카테고리 코드.
	public String getCategoryTeamCode() {
		return categoryTeamCode;
	}
	public void setCategoryTeamCode(String categoryTeamCode) {
		this.categoryTeamCode = categoryTeamCode;
	}
	public String getCategoryGroupCode() {
		return categoryGroupCode;
	}
	public void setCategoryGroupCode(String categoryGroupCode) {
		this.categoryGroupCode = categoryGroupCode;
	}
	public String getCategoryUrl() {
		return categoryUrl;
	}
	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
}
