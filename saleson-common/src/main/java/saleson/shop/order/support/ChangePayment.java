package saleson.shop.order.support;

public class ChangePayment {
	private int paymentSequence;
	private int cancelAmount;
	private String returnBankName;
	private String returnBankInName;
	private String returnBankVirtualNo;
	
	public String getReturnBankName() {
		return returnBankName;
	}
	public void setReturnBankName(String returnBankName) {
		this.returnBankName = returnBankName;
	}
	public String getReturnBankInName() {
		return returnBankInName;
	}
	public void setReturnBankInName(String returnBankInName) {
		this.returnBankInName = returnBankInName;
	}
	public String getReturnBankVirtualNo() {
		return returnBankVirtualNo;
	}
	public void setReturnBankVirtualNo(String returnBankVirtualNo) {
		this.returnBankVirtualNo = returnBankVirtualNo;
	}
	public int getPaymentSequence() {
		return paymentSequence;
	}
	public void setPaymentSequence(int paymentSequence) {
		this.paymentSequence = paymentSequence;
	}
	public int getCancelAmount() {
		return cancelAmount;
	}
	public void setCancelAmount(int cancelAmount) {
		this.cancelAmount = cancelAmount;
	}
}
