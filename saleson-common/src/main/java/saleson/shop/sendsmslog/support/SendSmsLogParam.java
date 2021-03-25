package saleson.shop.sendsmslog.support;

import com.onlinepowers.framework.web.domain.SearchParam;

public class SendSmsLogParam extends SearchParam {
	
	private int sendSmsLogId;
	private long userId;
	private String orderCode;
	private String sendTelNumber;
	private String receiveTelNumber;
	private String orderStatus;
	private String content;
	private String sendType;
	private String createdDate;
	private String searchStartDate;
	private String searchEndDate;
	
	public int getSendSmsLogId() {
		return sendSmsLogId;
	}
	public void setSendSmsLogId(int sendSmsLogId) {
		this.sendSmsLogId = sendSmsLogId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getSendTelNumber() {
		return sendTelNumber;
	}
	public void setSendTelNumber(String sendTelNumber) {
		this.sendTelNumber = sendTelNumber;
	}
	public String getReceiveTelNumber() {
		return receiveTelNumber;
	}
	public void setReceiveTelNumber(String receiveTelNumber) {
		this.receiveTelNumber = receiveTelNumber;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
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
}
