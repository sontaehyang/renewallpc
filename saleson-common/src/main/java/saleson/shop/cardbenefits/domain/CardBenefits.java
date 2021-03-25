package saleson.shop.cardbenefits.domain;


public class CardBenefits {
	
	private int benefitsId;
	private String subject;
	private String content;
	private String startDate;
	private String endDate;
	private String createdDate;
	
	
	public int getBenefitsId() {
		return benefitsId;
	}
	public void setBenefitsId(int benefitsId) {
		this.benefitsId = benefitsId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}
