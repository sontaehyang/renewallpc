package saleson.shop.sendmaillog.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class SendMailLogParam extends SearchParam {

	private String orderCode;
	private long userId;
	private String sendType;
	private String mailType;
	private int vendorId;
	private String searchStartDate;
	private String searchEndDate;
	private String templateId;
	private String orderStatus;
	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		if (!templateId.equals("all")) {
			if (templateId.startsWith("order")) {
				this.sendType = "order";
				this.orderStatus = templateId.replace("order_stats_", "");
			} else {
				this.sendType = templateId;
			}
		}
		
		this.templateId = templateId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
