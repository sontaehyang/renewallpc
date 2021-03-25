package saleson.shop.rankingconfig.domain;

public class RankingConfig {

	private String rankConfigCode;
	private int salePriceDays;
	private int salePriceWeight;
	private int saleCountDays;
	private int saleCountWeight;
	private int itemReviewDays;
	private int itemReviewWeight;
	private int itemHitWeight;
	private String createdDate;
	
	public String getRankConfigCode() {
		return rankConfigCode;
	}
	public void setRankConfigCode(String rankConfigCode) {
		this.rankConfigCode = rankConfigCode;
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
