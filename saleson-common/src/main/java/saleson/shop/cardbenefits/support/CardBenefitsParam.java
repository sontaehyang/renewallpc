package saleson.shop.cardbenefits.support;

import com.onlinepowers.framework.web.domain.SearchParam;
import saleson.common.utils.CommonUtils;

@SuppressWarnings("serial")
public class CardBenefitsParam extends SearchParam{
	
	private int benefitsId;
	private String subject;
	private String content;
	private String startDate;
	private String endDate;
	private String createdDate;
	
	private String searchStartDate;
	private String searchEndDate;

	private String[] benefitsIds = null;
	
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
	public String getSearchStartDate() {
		return searchStartDate;
	}
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	public String getSearchEndDate() {
		return searchEndDate;
	}
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	public String[] getBenefitsIds() {
		return CommonUtils.copy(benefitsIds);
	}
	public void setBenefitsIds(String[] benefitsIds) {
		this.benefitsIds = CommonUtils.copy(benefitsIds);
	}
	
}
