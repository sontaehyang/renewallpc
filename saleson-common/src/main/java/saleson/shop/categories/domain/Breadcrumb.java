package saleson.shop.categories.domain;

import java.util.ArrayList;
import java.util.List;

public class Breadcrumb {
	private String teamId;
	private String teamName;
	private String teamUrl;
	private String groupId;
	private String groupName;
	private String groupUrl;
	private String categoryClass;
	
	private List<BreadcrumbCategory> breadcrumbCategories = new ArrayList<>();

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamUrl() {
		return teamUrl;
	}

	public void setTeamUrl(String teamUrl) {
		this.teamUrl = teamUrl;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupUrl() {
		return groupUrl;
	}

	public void setGroupUrl(String groupUrl) {
		this.groupUrl = groupUrl;
	}

	public List<BreadcrumbCategory> getBreadcrumbCategories() {
		return breadcrumbCategories;
	}

	public void setBreadcrumbCategories(
			List<BreadcrumbCategory> breadcrumbCategories) {
		this.breadcrumbCategories = breadcrumbCategories;
	}

	public String getCategoryClass() {
		return categoryClass;
	}

	public void setCategoryClass(String categoryClass) {
		this.categoryClass = categoryClass;
	}
	
	
	// 1차 카테고리 정보
	public String getCategoryUrl1() {
		return getCategoryUrl(1);
	}
	
	// 2차 카테고리 정보
	public String getCategoryUrl2() {
		return getCategoryUrl(2);
	}
	
	// 3차 카테고리 정보
	public String getCategoryUrl3() {
		return getCategoryUrl(3);
	}
	
	
	// 4차 카테고리 정보
	public String getCategoryUrl4() {
		return getCategoryUrl(4);
	}
		
	
	// 카테고리정보 리턴.
	private String getCategoryUrl(int depth) {
		if (breadcrumbCategories.size() < depth) {
			return "";
		}
		
		return breadcrumbCategories.get(depth - 1).getCategoryUrl();
	}
	
}
