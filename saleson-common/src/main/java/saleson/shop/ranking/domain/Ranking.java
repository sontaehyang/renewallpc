package saleson.shop.ranking.domain;

import saleson.shop.item.domain.Item;

public class Ranking {
	private int rankingId;
	private String categoryUrl;
	private int itemId;
	private int ordering;
	
	private Item item;
	

	public int getRankingId() {
		return rankingId;
	}

	public void setRankingId(int rankingId) {
		this.rankingId = rankingId;
	}

	public String getCategoryUrl() {
		return categoryUrl;
	}

	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
}
