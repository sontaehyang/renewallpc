package saleson.shop.cashreceipt.domain;

import saleson.common.utils.ShopUtils;

import com.onlinepowers.framework.util.StringUtils;

public class CashReceipt {
	private boolean isSuccess;
	private int cashReceiptId;
	
	private int orderSequence;
	private int paymentSequence;
	private String orderCode;
	
	private int cashReceiptTaxFreeAmount;
	private int cashReceiptAmount;
	
	private String cashReceiptName;
	private String cashReceiptCode;
	private String cashReceiptType = "0";
	private String cashReceiptIssueNumber;
	private String cashReceiptIssueDate;
	private String cashReceiptStatusCode;
	private String createdDate;
	
	private String cashReceiptPhone1;
	private String cashReceiptPhone2;
	private String cashReceiptPhone3;
	
	private String cashReceiptEmail;
	
	private String cashReceiptBusinessNumber1;
	private String cashReceiptBusinessNumber2;
	private String cashReceiptBusinessNumber3;
	
	private String productName;
	private String pgApprovalType;
	private String cashReceiptPgServiceType;
	private String pgTid;

	public int getOrderSequence() {
		return orderSequence;
	}

	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}

	public int getPaymentSequence() {
		return paymentSequence;
	}

	public void setPaymentSequence(int paymentSequence) {
		this.paymentSequence = paymentSequence;
	}

	public int getCashReceiptTaxFreeAmount() {
		return cashReceiptTaxFreeAmount;
	}

	public void setCashReceiptTaxFreeAmount(int cashReceiptTaxFreeAmount) {
		this.cashReceiptTaxFreeAmount = cashReceiptTaxFreeAmount;
	}

	public String getCashReceiptPgServiceType() {
		return cashReceiptPgServiceType;
	}

	public void setCashReceiptPgServiceType(String cashReceiptPgServiceType) {
		this.cashReceiptPgServiceType = cashReceiptPgServiceType;
	}

	public String getCashReceiptStatusCodeLabel() {
		
		if ("1".equals(this.getCashReceiptStatusCode())) {
			return "신청";
		} else if ("2".equals(this.getCashReceiptStatusCode())) {
			return "발행";
		}  else if ("3".equals(this.getCashReceiptStatusCode())) {
			return "취소";
		}
		
		return "";
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getPgTid() {
		return pgTid;
	}
	public void setPgTid(String pgTid) {
		this.pgTid = pgTid;
	}
	public String getPgApprovalType() {
		return pgApprovalType;
	}
	public void setPgApprovalType(String pgApprovalType) {
		this.pgApprovalType = pgApprovalType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getCashReceiptPhone1() {
		return cashReceiptPhone1;
	}
	public void setCashReceiptPhone1(String cashReceiptPhone1) {
		this.cashReceiptPhone1 = cashReceiptPhone1;
	}
	public String getCashReceiptPhone2() {
		return cashReceiptPhone2;
	}
	public void setCashReceiptPhone2(String cashReceiptPhone2) {
		this.cashReceiptPhone2 = cashReceiptPhone2;
	}
	public String getCashReceiptPhone3() {
		return cashReceiptPhone3;
	}
	public void setCashReceiptPhone3(String cashReceiptPhone3) {
		this.cashReceiptPhone3 = cashReceiptPhone3;
	}
	public String getCashReceiptBusinessNumber1() {
		return cashReceiptBusinessNumber1;
	}
	public void setCashReceiptBusinessNumber1(String cashReceiptBusinessNumber1) {
		this.cashReceiptBusinessNumber1 = cashReceiptBusinessNumber1;
	}
	public String getCashReceiptBusinessNumber2() {
		return cashReceiptBusinessNumber2;
	}
	public void setCashReceiptBusinessNumber2(String cashReceiptBusinessNumber2) {
		this.cashReceiptBusinessNumber2 = cashReceiptBusinessNumber2;
	}
	public String getCashReceiptBusinessNumber3() {
		return cashReceiptBusinessNumber3;
	}
	public void setCashReceiptBusinessNumber3(String cashReceiptBusinessNumber3) {
		this.cashReceiptBusinessNumber3 = cashReceiptBusinessNumber3;
	}
	public int getCashReceiptId() {
		return cashReceiptId;
	}
	public void setCashReceiptId(int cashReceiptId) {
		this.cashReceiptId = cashReceiptId;
	}

	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getCashReceiptAmount() {
		return cashReceiptAmount;
	}
	public void setCashReceiptAmount(int cashReceiptAmount) {
		this.cashReceiptAmount = cashReceiptAmount;
	}
	public String getCashReceiptName() {
		return cashReceiptName;
	}
	public void setCashReceiptName(String cashReceiptName) {
		this.cashReceiptName = cashReceiptName;
	}
	public String getCashReceiptCode() {
		
		if (StringUtils.isNotEmpty(this.cashReceiptCode)) {
			return cashReceiptCode;
		}
		
		boolean isAppCashReceipts = false;
		if ("1".equals(this.cashReceiptType)) {
			isAppCashReceipts = true;
			
			if (StringUtils.isEmpty(this.cashReceiptBusinessNumber1)) isAppCashReceipts = false;
			if (StringUtils.isEmpty(this.cashReceiptBusinessNumber2)) isAppCashReceipts = false;
			if (StringUtils.isEmpty(this.cashReceiptBusinessNumber3)) isAppCashReceipts = false;
			
			this.cashReceiptCode = this.cashReceiptBusinessNumber1 + "-" + this.cashReceiptBusinessNumber2 + "-" + this.cashReceiptBusinessNumber3;
		} else if ("2".equals(this.cashReceiptType)) {
			isAppCashReceipts = true;
			
			if (StringUtils.isEmpty(this.cashReceiptPhone1)) isAppCashReceipts = false;
			if (StringUtils.isEmpty(this.cashReceiptPhone2)) isAppCashReceipts = false;
			if (StringUtils.isEmpty(this.cashReceiptPhone3)) isAppCashReceipts = false;
			
			this.cashReceiptCode = this.cashReceiptPhone1 + "-" + this.cashReceiptPhone2 + "-" + this.cashReceiptPhone3;
		}
		
		if (isAppCashReceipts == false) {
			this.cashReceiptCode = "";
		}
		
		return cashReceiptCode;
	}
	
	public void setCashReceiptCode(String cashReceiptCode) {
		
		if (StringUtils.isNotEmpty(cashReceiptCode)) {
			String[] cutArray = ShopUtils.phoneNumberForDelimitedToStringArray(cashReceiptCode);

			if ("1".equals(this.cashReceiptType)) {
				this.cashReceiptBusinessNumber1 = cutArray[0];
				this.cashReceiptBusinessNumber2 = cutArray[1];
				this.cashReceiptBusinessNumber3 = cutArray[2];
			} else if ("2".equals(this.cashReceiptType)) {
				this.cashReceiptPhone1 = cutArray[0];
				this.cashReceiptPhone2 = cutArray[1];
				this.cashReceiptPhone3 = cutArray[2];
			}
		}
		
		this.cashReceiptCode = cashReceiptCode;
	}
	public String getCashReceiptType() {
		if (StringUtils.isEmpty(cashReceiptType)) {
			return "0";
		} else {
			if (StringUtils.isEmpty(this.getCashReceiptCode())) {
				return cashReceiptType;
			}
		}
		
		return cashReceiptType;
	}
	public void setCashReceiptType(String cashReceiptType) {
		this.cashReceiptType = cashReceiptType;
	}

	public String getCashReceiptIssueNumber() {
		return cashReceiptIssueNumber;
	}
	public void setCashReceiptIssueNumber(String cashReceiptIssueNumber) {
		this.cashReceiptIssueNumber = cashReceiptIssueNumber;
	}
	public String getCashReceiptIssueDate() {
		return cashReceiptIssueDate;
	}
	public void setCashReceiptIssueDate(String cashReceiptIssueDate) {
		this.cashReceiptIssueDate = cashReceiptIssueDate;
	}
	public String getCashReceiptStatusCode() {
		return cashReceiptStatusCode;
	}
	public void setCashReceiptStatusCode(String cashReceiptStatusCode) {
		this.cashReceiptStatusCode = cashReceiptStatusCode;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCashReceiptEmail() {
		return cashReceiptEmail;
	}

	public void setCashReceiptEmail(String cashReceiptEmail) {
		this.cashReceiptEmail = cashReceiptEmail;
	}
	
	
}
