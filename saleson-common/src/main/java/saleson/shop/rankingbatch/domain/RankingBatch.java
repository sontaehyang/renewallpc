package saleson.shop.rankingbatch.domain;

public class RankingBatch {

	private int rankingType;
	private String rankingCode;
	private int itemId;
	private int ordering;
	private String dataStatusCode;
	
	public int getRankingType() {
		return rankingType;
	}
	public void setRankingType(int rankingType) {
		this.rankingType = rankingType;
	}
	public String getRankingCode() {
		return rankingCode;
	}
	public void setRankingCode(String rankingCode) {
		this.rankingCode = rankingCode;
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
	public String getDataStatusCode() {
		return dataStatusCode;
	}
	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}
	
	
	
}
