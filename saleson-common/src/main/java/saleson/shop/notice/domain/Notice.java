package saleson.shop.notice.domain;

import saleson.common.utils.CommonUtils;

public class Notice {
	private int noticeId;
	private String subject;
	private String url;
	private String categoryTeam;
	private String userName;
	private String password;
	private String email;
	private String content;
	private int hits;
	private String boardCode;
	private String subCategory;
	private String createdDate;
	private String targetOption;
	private String relOption;
	private String urlType;
	private String noticeFlag = "N";
	private int visibleType = 1;
	private String sellerSelectFlag = "N";
	
	private long sellerId;
	
	private String link;
	
	private long sellerIds[];
	
	private String newFlag;
	
	public String getNoticeFlag() {
		return noticeFlag;
	}
	public void setNoticeFlag(String noticeFlag) {
		this.noticeFlag = noticeFlag;
	}
	
	public int getVisibleType() {
		return visibleType;
	}
	public void setVisibleType(int visibleType) {
		this.visibleType = visibleType;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.url = link;
		this.link = link;
	}
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCategoryTeam() {
		return categoryTeam;
	}
	public void setCategoryTeam(String categoryTeam) {
		this.categoryTeam = categoryTeam;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getBoardCode() {
		return boardCode;
	}
	public void setBoardCode(String boardCode) {
		this.boardCode = boardCode;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public String getTargetOption() {
		return targetOption;
	}
	public void setTargetOption(String targetOption) {
		this.targetOption = targetOption;
	}
	public String getRelOption() {
		return relOption;
	}
	public void setRelOption(String relOption) {
		this.relOption = relOption;
	}
	public String getUrlType() {
		return urlType;
	}
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
	
	public String getTarget() {
		if ("".equals(this.link)) {
			return "";
		}
		return "Y".equals(this.targetOption) ? " target=\"_blank\"" : "";
 	}
	
	public String getRel() {
		if ("".equals(this.link)) {
			return "";
		}
		return "Y".equals(this.relOption) ? " rel=\"nofollow\"" : "";
 	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public long[] getSellerIds() {
		return CommonUtils.copy(sellerIds);
	}
	public void setSellerIds(long[] sellerIds) {
		sellerIds = CommonUtils.copy(sellerIds);
	}
	public String getSellerSelectFlag() {
		return sellerSelectFlag;
	}
	public void setSellerSelectFlag(String sellerSelectFlag) {
		this.sellerSelectFlag = sellerSelectFlag;
	}
	public String getNewFlag() {
		return newFlag;
	}
	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}
	
	
}
