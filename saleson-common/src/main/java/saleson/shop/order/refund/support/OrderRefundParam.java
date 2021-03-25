package saleson.shop.order.refund.support;

import org.springframework.util.StringUtils;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class OrderRefundParam extends SearchParam {

	private String orderCode;
	private int orderSequence;
	private String refundStatusCode;
	private String searchStartDate;
	private String searchEndDate;
	private String searchDateType;
	private String bankName;
	private String bankInName;
	private String virtualNo;
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankInName() {
		return bankInName;
	}
	public void setBankInName(String bankInName) {
		this.bankInName = bankInName;
	}
	public String getVirtualNo() {
		return virtualNo;
	}
	public void setVirtualNo(String virtualNo) {
		this.virtualNo = virtualNo;
	}
	public String getSearchDateType() {
		return searchDateType;
	}
	public void setSearchDateType(String searchDateType) {
		this.searchDateType = searchDateType;
	}
	public String getSearchEndDate() {
		return searchEndDate;
	}
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	public String getSearchStartDate() {
		return searchStartDate;
	}
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	public String getRefundStatusCode() {
		
		if (StringUtils.isEmpty(refundStatusCode)) {
			return "1";
		}
		
		return refundStatusCode;
	}
	public void setRefundStatusCode(String refundStatusCode) {
		this.refundStatusCode = refundStatusCode;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}
}
