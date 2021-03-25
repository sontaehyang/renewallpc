package saleson.shop.categories.support;

import com.onlinepowers.framework.web.domain.SearchParam;
import saleson.common.utils.CommonUtils;


@SuppressWarnings("serial")
public class CategoriesSearchParam extends SearchParam {
	
	private String groupCode;
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	private String categoryCode;
	private String categoryGroupId;
	private String categoryClass;
	private String categoryClass1;
	private String categoryClass2;
	private String categoryClass3;
	private String categoryClass4;
	private String categoryLevel;
	private int ordering;
	private String parentCode;
	private String parentLevel;
	private String parentGroupId;
	private String newCode;
	private String newLevel;
	private String newLevel2;
	private String categoryId;
	private String categoryUrl;
	
	private String[] categoryCodes;
	
	// jsTree 카테고리 이동 관련.
	private String currentCode;
	private String currentLevel;
	private String previousCode;
	private String previousLevel;
	private String previousOrdering;
	
	
	
	
	public String getCurrentCode() {
		return currentCode;
	}
	public void setCurrentCode(String currentCode) {
		this.currentCode = currentCode;
	}
	public String getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}
	public String getPreviousCode() {
		return previousCode;
	}
	public void setPreviousCode(String previousCode) {
		this.previousCode = previousCode;
	}
	public String getPreviousLevel() {
		return previousLevel;
	}
	public void setPreviousLevel(String previousLevel) {
		this.previousLevel = previousLevel;
	}
	public String getPreviousOrdering() {
		return previousOrdering;
	}
	public void setPreviousOrdering(String previousOrdering) {
		this.previousOrdering = previousOrdering;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryGroupId() {
		return categoryGroupId;
	}
	public void setCategoryGroupId(String categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}
	public String getCategoryClass1() {
		return categoryClass1;
	}
	public void setCategoryClass1(String categoryClass1) {
		this.categoryClass1 = categoryClass1;
	}
	public String getCategoryClass2() {
		return categoryClass2;
	}
	public void setCategoryClass2(String categoryClass2) {
		this.categoryClass2 = categoryClass2;
	}
	public String getCategoryClass3() {
		return categoryClass3;
	}
	public void setCategoryClass3(String categoryClass3) {
		this.categoryClass3 = categoryClass3;
	}
	public String getCategoryClass4() {
		return categoryClass4;
	}
	public void setCategoryClass4(String categoryClass4) {
		this.categoryClass4 = categoryClass4;
	}
	public String getCategoryLevel() {
		return categoryLevel;
	}
	public void setCategoryLevel(String categoryLevel) {
		this.categoryLevel = categoryLevel;
	}
	public String[] getCategoryCodes() {
		return CommonUtils.copy(categoryCodes);
	}
	public void setCategoryCodes(String[] categoryCodes) {
		this.categoryCodes = CommonUtils.copy(categoryCodes);
	}
	public String getCategoryClass() {
		return categoryClass;
	}
	public void setCategoryClass(String categoryClass) {
		this.categoryClass = categoryClass;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentLevel() {
		return parentLevel;
	}
	public void setParentLevel(String parentLevel) {
		this.parentLevel = parentLevel;
	}
	
	public String getParentGroupId() {
		return parentGroupId;
	}
	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}
	public String getNewCode() {
		return newCode;
	}
	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}
	public String getNewLevel() {
		return newLevel;
	}
	public void setNewLevel(String newLevel) {
		this.newLevel = newLevel;
	}
	public String getNewLevel2() {
		return newLevel2;
	}
	public void setNewLevel2(String newLevel2) {
		this.newLevel2 = newLevel2;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryUrl() {
		return categoryUrl;
	}
	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}
	
}

