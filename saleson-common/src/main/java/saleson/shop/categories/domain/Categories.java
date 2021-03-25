package saleson.shop.categories.domain;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;
import saleson.shop.seo.domain.Seo;


public class Categories {
	private int categoryId;
	private String categoryCode;
	private int categoryGroupId;
	private String categoryUrl;
	private String categoryName;
	private String categoryType = "1";
	private String categoryHeader;
	private String categoryFooter;
	private String categoryBanner;
	private String categoryAdvertisement;
	private String categoryClass1;
	private String categoryClass2;
	private String categoryClass3;
	private String categoryClass4;
	private String categoryLevel;
	private int ordering;
	private String categoryFlag = "Y";
	private String accessType;
	private int categoryCount;
	private String childMaxLevel;
	private String CategoryMobileHtml;
	
	private String categoryMobileHtmlHeader;
	
	private Seo categoriesSeo;
	private Seo rankSeo;
	private Seo reviewSeo;
	
	// 1~4차 카테고리 조회시 사용.
	private Category parentCategory;
	private List<Category> parentSiblingCategories;		// 부모의 형제들
	private List<Category> childCategories;
	private List<Category> siblingCategories;

	private List<String> filterGroupIds;
	private List<String> reviewFilterGroupIds;
	
	public Categories() {}

	public Categories(CategoriesGroup categoryGroup) {
		this.categoryUrl = categoryGroup.getCode();
		this.categoryName = categoryGroup.getName();
		this.categoryFlag = categoryGroup.getCategoryGroupFlag();
		this.categoriesSeo = categoryGroup.getCategoriesSeo();
		this.rankSeo = categoryGroup.getRankSeo();
	}

	public String getCategoryMobileHtmlHeader() {
		return categoryMobileHtmlHeader;
	}


	public void setCategoryMobileHtmlHeader(String categoryMobileHtmlHeader) {
		this.categoryMobileHtmlHeader = categoryMobileHtmlHeader;
	}


	public String getChildMaxLevel() {
		return childMaxLevel;
	}
	public void setChildMaxLevel(String childMaxLevel) {
		this.childMaxLevel = childMaxLevel;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public int getCategoryGroupId() {
		return categoryGroupId;
	}
	public void setCategoryGroupId(int categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}
	public String getCategoryUrl() {
		return categoryUrl;
	}
	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getCategoryHeader() {
		return categoryHeader;
	}
	public void setCategoryHeader(String categoryHeader) {
		this.categoryHeader = categoryHeader;
	}
	public String getCategoryAdvertisement() {
		return categoryAdvertisement;
	}
	public void setCategoryAdvertisement(String categoryAdvertisement) {
		this.categoryAdvertisement = categoryAdvertisement;
	}
	public String getCategoryFooter() {
		return categoryFooter;
	}
	public void setCategoryFooter(String categoryFooter) {
		this.categoryFooter = categoryFooter;
	}
	public String getCategoryBanner() {
		return categoryBanner;
	}
	public void setCategoryBanner(String categoryBanner) {
		this.categoryBanner = categoryBanner;
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
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public String getCategoryFlag() {
		return categoryFlag;
	}
	public void setCategoryFlag(String categoryFlag) {
		this.categoryFlag = categoryFlag;
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
	public Seo getReviewSeo() {
		return reviewSeo;
	}
	public void setReviewSeo(Seo reviewSeo) {
		this.reviewSeo = reviewSeo;
	}
	public int getCategoryCount() {
		return categoryCount;
	}
	public void setCategoryCount(int categoryCount) {
		this.categoryCount = categoryCount;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public Category getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}
	public List<Category> getChildCategories() {
		return childCategories;
	}
	public void setChildCategories(List<Category> childCategories) {
		this.childCategories = childCategories;
	}
	public List<Category> getSiblingCategories() {
		return siblingCategories;
	}
	public void setSiblingCategories(List<Category> siblingCategories) {
		this.siblingCategories = siblingCategories;
	}
	public String getCategoryMobileHtml() {
		return CategoryMobileHtml;
	}
	public void setCategoryMobileHtml(String categoryMobileHtml) {
		CategoryMobileHtml = categoryMobileHtml;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public List<Category> getParentSiblingCategories() {
		return parentSiblingCategories;
	}

	public void setParentSiblingCategories(List<Category> parentSiblingCategories) {
		this.parentSiblingCategories = parentSiblingCategories;
	}

	public List<String> getFilterGroupIds() {
		return filterGroupIds;
	}

	public void setFilterGroupIds(List<String> filterGroupIds) {
		this.filterGroupIds = filterGroupIds;
	}

	public Set<Long> getFilterGroupIdSet() {

		List<String> ids = getFilterGroupIds();
		Set<Long> set = new LinkedHashSet<>();

		if (ids != null && !ids.isEmpty()) {

			for (String id : ids) {
				try {
					set.add(Long.parseLong(id));
				} catch (Exception ignore){}
			}
		}

		return set;
	}

	public List<String> getReviewFilterGroupIds() {
		return reviewFilterGroupIds;
	}

	public void setReviewFilterGroupIds(List<String> reviewFilterGroupIds) {
		this.reviewFilterGroupIds = reviewFilterGroupIds;
	}

	public Set<Long> getReviewFilterGroupIdSet() {

		List<String> ids = getReviewFilterGroupIds();
		Set<Long> set = new LinkedHashSet<>();

		if (ids != null && !ids.isEmpty()) {

			for (String id : ids) {
				try {
					set.add(Long.parseLong(id));
				} catch (Exception ignore){}
			}
		}

		return set;
	}
}
