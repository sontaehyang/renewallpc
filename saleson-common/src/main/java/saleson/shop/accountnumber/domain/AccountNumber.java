package saleson.shop.accountnumber.domain;

public class AccountNumber {
	private int shopConfigId = 1;
	private int accountNumberId;
	private String bankName;
	private String accountNumber;
	private String accountHolder;
	private String useFlag = "Y";
	private long userId;
	private String created;
	
	
	public int getShopConfigId() {
		return shopConfigId;
	}
	public void setShopConfigId(int shopConfigId) {
		this.shopConfigId = shopConfigId;
	}
	public int getAccountNumberId() {
		return accountNumberId;
	}
	public void setAccountNumberId(int accountNumberId) {
		this.accountNumberId = accountNumberId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	
	
	
}
