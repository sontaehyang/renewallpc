package saleson.shop.accountnumber.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class AccountNumberParam extends SearchParam{

	private int shopConfigId = 1;
	private String accountNumberId;
	
	public int getShopConfigId() {
		return shopConfigId;
	}
	public void setShopConfigId(int shopConfigId) {
		this.shopConfigId = shopConfigId;
	}
	public String getAccountNumberId() {
		return accountNumberId;
	}
	public void setAccountNumberId(String accountNumberId) {
		this.accountNumberId = accountNumberId;
	}
	
}
