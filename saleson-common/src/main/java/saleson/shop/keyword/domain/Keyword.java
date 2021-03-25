package saleson.shop.keyword.domain;

public class Keyword {
	private String keyword = "";
	private String keywordType = "";
	private String createdDate = "";
	private String keywordSeperation = "";
	private int weight;
	
	public Keyword() {
		
	}
	
	public Keyword(String keywordType, String keyword, String keywordSeperation) {
		this.keywordType = keywordType;
		this.keyword = keyword;
		this.keywordSeperation = keywordSeperation;
	}
	
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getKeywordType() {
		return keywordType;
	}
	public void setKeywordType(String keywordType) {
		this.keywordType = keywordType;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getKeywordSeperation() {
		return keywordSeperation;
	}
	public void setKeywordSeperation(String keywordSeperation) {
		this.keywordSeperation = keywordSeperation;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
}
