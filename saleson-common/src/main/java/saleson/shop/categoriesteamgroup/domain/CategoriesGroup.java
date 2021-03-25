package saleson.shop.categoriesteamgroup.domain;

import java.util.List;

import saleson.shop.categories.domain.Categories;
import saleson.shop.groupbanner.domain.GroupBanner;
import saleson.shop.item.domain.Item;
import saleson.shop.seo.domain.Seo;

public class CategoriesGroup {
	
	private int categoryGroupId;
	private int categoryTeamId;
	private String name;
	private String code;
	private String createdDate;
	private String updatedDate;
	private String categoryGroupFlag = "Y";
	private String accessType;
	private String defcate;
	private int ordering;
	
	private Seo categoriesSeo;
	private Seo rankSeo;
	
	private String groupName;
	private String groupOrdering;
	
	private List<Categories> categoryList;
	
	// 팀별 랭킹에 사용되는 그룹 랭킹 상품 리스트.
	private List<Item> rankingItems;
	
	// 그룹별 베너 목록
	private List<GroupBanner> groupBanners;
	
	private String itemList;
	
	public List<GroupBanner> getGroupBanners() {
		return groupBanners;
	}
	public void setGroupBanners(List<GroupBanner> groupBanners) {
		this.groupBanners = groupBanners;
	}
	
	public int getCategoryGroupId() {
		return categoryGroupId;
	}
	public void setCategoryGroupId(int categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}
	public int getCategoryTeamId() {
		return categoryTeamId;
	}
	public void setCategoryTeamId(int categoryTeamId) {
		this.categoryTeamId = categoryTeamId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getCategoryGroupFlag() {
		return categoryGroupFlag;
	}
	public void setCategoryGroupFlag(String categoryGroupFlag) {
		this.categoryGroupFlag = categoryGroupFlag;
	}
	public String getDefcate() {
		return defcate;
	}
	public void setDefcate(String defcate) {
		this.defcate = defcate;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public Seo getCategoriesSeo() {
		return categoriesSeo;
	}
	public void setCategoriesSeo(Seo categoriesSeo) {
		this.categoriesSeo = categoriesSeo;
	}
	public Seo getRankSeo() {
		return rankSeo;
	}
	public void setRankSeo(Seo rankSeo) {
		this.rankSeo = rankSeo;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupOrdering() {
		return groupOrdering;
	}
	public void setGroupOrdering(String groupOrdering) {
		this.groupOrdering = groupOrdering;
	}
	public List<Categories> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Categories> categoryList) {
		this.categoryList = categoryList;
	}
	public List<Item> getRankingItems() {
		return rankingItems;
	}
	public void setRankingItems(List<Item> rankingItems) {
		this.rankingItems = rankingItems;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public String getItemList() {
		return itemList;
	}
	public void setItemList(String itemList) {
		this.itemList = itemList;
	}
	
}
