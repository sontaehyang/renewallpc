package saleson.shop.accountnumber;

import java.util.List;

import saleson.shop.accountnumber.domain.AccountNumber;
import saleson.shop.accountnumber.support.AccountNumberParam;

import com.onlinepowers.framework.web.domain.ListParam;


public interface AccountNumberService {
	public int getAccountNumberCount(AccountNumberParam accountNumberParam);
	public List<AccountNumber> getAccountNumberList(AccountNumberParam accountNumberParam);
	public AccountNumber getAccountNumber(AccountNumberParam accountNumberParam);
	public void insertAccountNumber(AccountNumber accountNumber);
	public void updateAccountNumber(AccountNumber accountNumber);
	public void deleteAccountNumber(AccountNumberParam accountNumberParam);
	public void deleteAccountNumberListParam(ListParam listParam);
	
	public List<AccountNumber> getUseAccountNumberListAll();
}
