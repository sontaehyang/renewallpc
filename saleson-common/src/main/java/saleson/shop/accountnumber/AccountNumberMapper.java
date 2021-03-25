package saleson.shop.accountnumber;

import java.util.List;

import saleson.shop.accountnumber.domain.AccountNumber;
import saleson.shop.accountnumber.support.AccountNumberParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("accountNumberMapper")
public interface AccountNumberMapper {
	int getAccountNumberCount(AccountNumberParam accountNumberParam);
	List<AccountNumber> getAccountNumberList(AccountNumberParam accountNumberParam);
	AccountNumber getAccountNumber(AccountNumberParam accountNumberParam);
	void insertAccountNumber(AccountNumber accountNumber);
	void updateAccountNumber(AccountNumber accountNumber);
	void deleteAccountNumber(AccountNumberParam accountNumberParam);
	
	/**
	 * 등록 계좌 전채 조회
	 */
	List<AccountNumber> getUseAccountNumberListAll(AccountNumberParam accountNumberParam);
}
