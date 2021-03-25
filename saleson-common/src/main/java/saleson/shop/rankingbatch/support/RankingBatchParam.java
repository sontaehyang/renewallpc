package saleson.shop.rankingbatch.support;

import java.util.List;

import com.onlinepowers.framework.web.domain.SearchParam;

import saleson.shop.rankingconfig.domain.RankingConfig;

@SuppressWarnings("serial")
public class RankingBatchParam extends SearchParam{

	public RankingBatchParam(){};
	public RankingBatchParam(RankingConfig config){
		this.salePriceWeight = config.getSalePriceWeight();
		this.salePriceDays = config.getSalePriceDays();
		this.saleCountWeight = config.getSaleCountWeight();
		this.saleCountDays = config.getSaleCountDays();
		this.itemHitWeight = config.getItemHitWeight();
		this.itemReviewDays = config.getItemReviewDays();
		this.itemReviewWeight = config.getItemReviewWeight();
	};
	
	private int categoryGroupId;
	private String categoryCode;
	private int categoryLevel;
	private List<String> groupCategoryClassCodes;
	public List<String> getGroupCategoryClassCodes() {
		return groupCategoryClassCodes;
	}
	public void setGroupCategoryClassCodes(List<String> groupCategoryClassCodes) {
		this.groupCategoryClassCodes = groupCategoryClassCodes;
	}
	public int getCategoryGroupId() {
		return categoryGroupId;
	}
	public void setCategoryGroupId(int categoryGroupId) {
		this.categoryGroupId = categoryGroupId;
	}
	public int getCategoryLevel() {
		return categoryLevel;
	}
	public void setCategoryLevel(int categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	private String rankingType;
	private String rankingCode;
	private int salePriceDays;
	private int salePriceWeight;
	private int saleCountDays;
	private int saleCountWeight;
	private int itemReviewDays;
	private int itemReviewWeight;
	private int itemHitWeight;
	private int limit;
	
	public String getRankingType() {
		return rankingType;
	}
	public void setRankingType(String rankingType) {
		this.rankingType = rankingType;
	}
	public String getRankingCode() {
		return rankingCode;
	}
	public void setRankingCode(String rankingCode) {
		this.rankingCode = rankingCode;
	}
	public int getSalePriceDays() {
		return salePriceDays;
	}
	public void setSalePriceDays(int salePriceDays) {
		this.salePriceDays = salePriceDays;
	}
	public int getSalePriceWeight() {
		return salePriceWeight;
	}
	public void setSalePriceWeight(int salePriceWeight) {
		this.salePriceWeight = salePriceWeight;
	}
	public int getSaleCountDays() {
		return saleCountDays;
	}
	public void setSaleCountDays(int saleCountDays) {
		this.saleCountDays = saleCountDays;
	}
	public int getSaleCountWeight() {
		return saleCountWeight;
	}
	public void setSaleCountWeight(int saleCountWeight) {
		this.saleCountWeight = saleCountWeight;
	}
	public int getItemReviewDays() {
		return itemReviewDays;
	}
	public void setItemReviewDays(int itemReviewDays) {
		this.itemReviewDays = itemReviewDays;
	}
	public int getItemReviewWeight() {
		return itemReviewWeight;
	}
	public void setItemReviewWeight(int itemReviewWeight) {
		this.itemReviewWeight = itemReviewWeight;
	}
	public int getItemHitWeight() {
		return itemHitWeight;
	}
	public void setItemHitWeight(int itemHitWeight) {
		this.itemHitWeight = itemHitWeight;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
}
