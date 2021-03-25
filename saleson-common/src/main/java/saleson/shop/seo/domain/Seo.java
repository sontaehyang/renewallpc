package saleson.shop.seo.domain;

public class Seo {
	private int seoId;
	private String seoUrl;
	private String title;
	private String keywords;
	private String description;
	private String headerContents1;
	private String headerContents2;
	private String headerContents3;
	private String themawordTitle;
	private String themawordDescription;
	private String themawordTopTitle;
	private String indexFlag = "N";
	private long createdUserId;
	private String createdDate;
	
	
	
	public int getSeoId() {
		return seoId;
	}
	public void setSeoId(int seoId) {
		this.seoId = seoId;
	}
	
	public String getSeoUrl() {
		return seoUrl;
	}
	public void setSeoUrl(String seoUrl) {
		this.seoUrl = seoUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHeaderContents1() {
		return headerContents1;
	}
	public void setHeaderContents1(String headerContents1) {
		this.headerContents1 = headerContents1;
	}
	public String getHeaderContents2() {
		return headerContents2;
	}
	public void setHeaderContents2(String headerContents2) {
		this.headerContents2 = headerContents2;
	}
	public String getHeaderContents3() {
		return headerContents3;
	}
	public void setHeaderContents3(String headerContents3) {
		this.headerContents3 = headerContents3;
	}
	public String getThemawordTitle() {
		return themawordTitle;
	}
	public void setThemawordTitle(String themawordTitle) {
		this.themawordTitle = themawordTitle;
	}
	public String getThemawordDescription() {
		return themawordDescription;
	}
	public void setThemawordDescription(String themawordDescription) {
		this.themawordDescription = themawordDescription;
	}
	
	public String getIndexFlag() {
		return indexFlag;
	}
	public void setIndexFlag(String indexFlag) {
		this.indexFlag = indexFlag;
	}
	public long getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(long createdUserId) {
		this.createdUserId = createdUserId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getThemawordTopTitle() {
		return themawordTopTitle == null ? headerContents1 : themawordTopTitle;
	}
	public void setThemawordTopTitle(String themawordTopTitle) {
		this.themawordTopTitle = themawordTopTitle;
	}
	
	/* seo null check 추가 2017-04-25 jeongah.choi*/
	public boolean isSeoNull() {
		if (this.seoId != 0) {
			return false;
		}
		if (seoUrl != null && !"".equals(seoUrl)) {
			return false;
		}
		if (title != null && !"".equals(title)) {
			return false;
		}
		if (keywords != null && !"".equals(keywords)) {
			return false;
		}
		if (description != null && !"".equals(description) && !" ".equals(description)) {
			return false;
		}
		if (headerContents1 != null && !"".equals(headerContents1)) {
			return false;
		}
		if (headerContents2 != null && !"".equals(headerContents2)) {
			return false;
		}
		if (headerContents3 != null && !"".equals(headerContents3)) {
			return false;
		}
		if (themawordTitle != null && !"".equals(themawordTitle)) {
			return false;
		}
		if (themawordDescription != null && !"".equals(themawordDescription)) {
			return false;
		}
		if (themawordTopTitle != null && !"".equals(themawordTopTitle)) {
			return false;
		}
		return true;
	}
	
}
