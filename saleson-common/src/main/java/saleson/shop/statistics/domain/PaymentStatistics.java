package saleson.shop.statistics.domain;

import com.onlinepowers.framework.repository.CodeInfo;
import com.onlinepowers.framework.util.CodeUtils;
import com.onlinepowers.framework.util.StringUtils;

public class PaymentStatistics {
	private String payDate;
	private String approvalType;
	private String deviceType;
	private int payCount;
	private int payAmount;
	private int cancelCount;
	private int cancelAmount;
	
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public int getPayCount() {
		return payCount;
	}
	public void setPayCount(int payCount) {
		this.payCount = payCount;
	}
	public int getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}
	public int getCancelCount() {
		return cancelCount;
	}
	public void setCancelCount(int cancelCount) {
		this.cancelCount = cancelCount;
	}
	public int getCancelAmount() {
		return cancelAmount;
	}
	public void setCancelAmount(int cancelAmount) {
		this.cancelAmount = cancelAmount;
	}
	
	public String getApprovalTypeLabel() {
		if (StringUtils.isEmpty(this.approvalType)) {
			return "";
		}
		
		CodeInfo codeInfo = CodeUtils.getCodeInfo("ORDER_PAY_TYPE", this.approvalType);
		if (codeInfo == null) {
			return "-";
		}
		
		return codeInfo.getLabel();
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
}
