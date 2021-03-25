package saleson.shop.sendmaillog.domain;


public class SendMailLog {
	private int sendMailLogId;
	private String orderCode;
	private int vendorId;
	private long userId;
	private String sendLoginId;
	private String sendName;
	private String sendEmail;
	private String receiveLoginId;
	private String receiveName;
	private String receiveEmail;
	private String subject;
	private String content;
	private String sendFlag;
	private String sendDate;
	private String susinNumber;
	private String sendType;
	private String orderStatus;
	private String orderStatusLabel;
	private String mailType;
	private String useTagFlag;
	private String createdDate;
	
	
	public String getOrderStatusLabel() {
		return orderStatusLabel;
	}
	public void setOrderStatusLabel(String orderStatusLabel) {
		this.orderStatusLabel = orderStatusLabel;
	}
	public int getSendMailLogId() {
		return sendMailLogId;
	}
	public void setSendMailLogId(int sendMailLogId) {
		this.sendMailLogId = sendMailLogId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getSendLoginId() {
		return sendLoginId;
	}
	public void setSendLoginId(String sendLoginId) {
		this.sendLoginId = sendLoginId;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}
	public String getReceiveLoginId() {
		return receiveLoginId;
	}
	public void setReceiveLoginId(String receiveLoginId) {
		this.receiveLoginId = receiveLoginId;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getReceiveEmail() {
		return receiveEmail;
	}
	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
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
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getSusinNumber() {
		return susinNumber;
	}
	public void setSusinNumber(String susinNumber) {
		this.susinNumber = susinNumber;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	public String getUseTagFlag() {
		return useTagFlag;
	}
	public void setUseTagFlag(String useTagFlag) {
		this.useTagFlag = useTagFlag;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
