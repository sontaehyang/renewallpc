package saleson.shop.categoriesteamgroup.support;


public class CategoriesTeamGroupSearchParam {
	
	private String categoryGroupId;

	private String categoryTeamId;
	private String categoryType ="";
	private String type;
	private String code;
	
	
	private String targetCategoryGroupId;		// group -> 1차로 바꿀 때 부모 그룹 ID 
	
	
	
	public String getTargetCategoryGroupId() {
		return targetCategoryGroupId;
	}
	public void setTargetCategoryGroupId(String targetCategoryGroupId) {
		this.targetCategoryGroupId = targetCategoryGroupId;
	}
	public String getCategoryGroupId() {
		return categoryGroupId;
	}
	public void setCategoryGroupId(String categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}
	public String getCategoryTeamId() {
		return categoryTeamId;
	}
	public void setCategoryTeamId(String categoryTeamId) {
		this.categoryTeamId = categoryTeamId;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
