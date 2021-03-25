package saleson.shop.accountnumber;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.domain.ListParam;

import saleson.shop.accountnumber.domain.AccountNumber;
import saleson.shop.accountnumber.support.AccountNumberParam;
import saleson.shop.config.domain.Config;


@Service("accountNumberService")
public class AccountNumberServiceImple implements AccountNumberService{
	private static final Logger log = LoggerFactory.getLogger(AccountNumberServiceImple.class);

	@Autowired 
	AccountNumberMapper accountNumberMapper;

	@Autowired
	SequenceService sequenceService;
	
	@Override
	public List<AccountNumber> getAccountNumberList(AccountNumberParam accountNumberParam) {
		log.debug("excute getAccountNumberList");
		return accountNumberMapper.getAccountNumberList(accountNumberParam);
	}

	@Override
	public AccountNumber getAccountNumber(AccountNumberParam accountNumberParam) {
		
		return accountNumberMapper.getAccountNumber(accountNumberParam);
	}

	@Override
	public void insertAccountNumber(AccountNumber accountNumber) {
		
		accountNumber.setAccountNumberId(sequenceService.getId("OP_ACCOUNT_NUMBER"));
		accountNumberMapper.insertAccountNumber(accountNumber);
	}

	@Override
	public void updateAccountNumber(AccountNumber accountNumber) {
		
		accountNumberMapper.updateAccountNumber(accountNumber);
	}

	@Override
	public void deleteAccountNumber(AccountNumberParam accountNumberParam) {
		
		accountNumberMapper.deleteAccountNumber(accountNumberParam);
	}

	@Override
	public int getAccountNumberCount(AccountNumberParam accountNumberParam) {
		
		return accountNumberMapper.getAccountNumberCount(accountNumberParam);
	}

	@Override
	public void deleteAccountNumberListParam(ListParam listParam) {
		
		AccountNumberParam accountNumberParam = new AccountNumberParam();
				if (listParam.getId() != null) {
					for (String accountNumberId : listParam.getId()) {
						if (StringUtils.isNotEmpty(accountNumberId)) {
							accountNumberParam.setAccountNumberId(accountNumberId);
							accountNumberMapper.deleteAccountNumber(accountNumberParam);
						}
					}
				}
	}

	@Override
	public List<AccountNumber> getUseAccountNumberListAll() {
		AccountNumberParam accountNumberParam = new AccountNumberParam();
		accountNumberParam.setShopConfigId(Config.SHOP_CONFIG_ID);
		
		return accountNumberMapper.getUseAccountNumberListAll(accountNumberParam);
	}
}
