package saleson.shop.categoriesteamgroup.domain;

import java.util.ArrayList;
import java.util.List;

import saleson.common.utils.CommonUtils;
import saleson.shop.item.domain.Item;
import saleson.shop.seo.domain.Seo;

public class CategoriesTeam {
	
	private int categoryTeamId;
	private String name;
	private String code;
	private String createdDate;
	private String updatedDate;
	private int ordering;
	private String categoryTeamFlag = "Y";
	
	private Seo categoriesSeo;
	private Seo rankSeo;
	private Seo reviewSeo;
	
	private String bestItemDisplayType;
	
	private ArrayList<CategoriesGroup> categoriesGroupList;
	
	private String relatedItemIds[];
	
	// 메인에 사용되는 팀별 랭킹 상품 리스트.
	private List<Item> rankingItems;
	
	
	
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
	public String getCategoryTeamFlag() {
		return categoryTeamFlag;
	}
	public void setCategoryTeamFlag(String categoryTeamFlag) {
		this.categoryTeamFlag = categoryTeamFlag;
	}
	public int getCategoryTeamId() {
		return categoryTeamId;
	}
	public void setCategoryTeamId(int categoryTeamId) {
		this.categoryTeamId = categoryTeamId;
	}
	public ArrayList<CategoriesGroup> getCategoriesGroupList() {
		return categoriesGroupList;
	}
	public void setCategoriesGroupList(
			ArrayList<CategoriesGroup> categoriesGroupList) {
		this.categoriesGroupList = categoriesGroupList;
	}
	public List<Item> getRankingItems() {
		return rankingItems;
	}
	public void setRankingItems(List<Item> rankingItems) {
		this.rankingItems = rankingItems;
	}
	public String[] getRelatedItemIds() {
		return CommonUtils.copy(relatedItemIds);
	}
	public void setRelatedItemIds(String[] relatedItemIds) {
		this.relatedItemIds = CommonUtils.copy(relatedItemIds);
	}
	public Seo getReviewSeo() {
		return reviewSeo;
	}
	public void setReviewSeo(Seo reviewSeo) {
		this.reviewSeo = reviewSeo;
	}
	public String getBestItemDisplayType() {
		return bestItemDisplayType;
	}
	public void setBestItemDisplayType(String bestItemDisplayType) {
		this.bestItemDisplayType = bestItemDisplayType;
	}
	
	
}
