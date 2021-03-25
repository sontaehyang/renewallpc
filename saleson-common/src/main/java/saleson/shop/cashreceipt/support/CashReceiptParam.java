/*
 * 2017.05.25 Jun-Eu Son 
 * Kspay 현금영수증관리 관련 Param
 */
package saleson.shop.cashreceipt.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class CashReceiptParam extends SearchParam {

	private int cashReceiptId;
	private int cashReceiptAmount;
	private int cashReceiptTaxFreeAmount;
	
	private String cashReceiptPgServiceType;
	private String searchStartDate;
	private String searchEndStartDate;
	private String orderCode;
	private String cashReceiptName;
	private String cashReceiptCode;
	private String cashReceiptType;
	private String cashReceiptIssueNumber;
	private String cashReceiptIssueDate;
	private String cashReceiptStatusCode;
	private String createdDate;
	
	public int getCashReceiptId() {
		return cashReceiptId;
	}
	public void setCashReceiptId(int cashReceiptId) {
		this.cashReceiptId = cashReceiptId;
	}
	public int getCashReceiptAmount() {
		return cashReceiptAmount;
	}
	public void setCashReceiptAmount(int cashReceiptAmount) {
		this.cashReceiptAmount = cashReceiptAmount;
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
	public String getSearchStartDate() {
		return searchStartDate;
	}
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	public String getSearchEndStartDate() {
		return searchEndStartDate;
	}
	public void setSearchEndStartDate(String searchEndStartDate) {
		this.searchEndStartDate = searchEndStartDate;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getCashReceiptName() {
		return cashReceiptName;
	}
	public void setCashReceiptName(String cashReceiptName) {
		this.cashReceiptName = cashReceiptName;
	}
	public String getCashReceiptCode() {
		return cashReceiptCode;
	}
	public void setCashReceiptCode(String cashReceiptCode) {
		this.cashReceiptCode = cashReceiptCode;
	}
	public String getCashReceiptType() {
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
	
	
}
