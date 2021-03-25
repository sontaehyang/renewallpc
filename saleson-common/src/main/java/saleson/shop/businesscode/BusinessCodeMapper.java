package saleson.shop.businesscode;

import java.util.List;

import saleson.shop.businesscode.domain.BusinessCode;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("businesscodeMapper")
public interface BusinessCodeMapper {
	int getBusinessCodeCount(BusinessCode businessCode);
	List<BusinessCode> getBusinessCodeList(BusinessCode businessCode);
	BusinessCode getBusinessCode(int businessCodeId);
	void insertBusinessCode(BusinessCode businessCode);
	void updateBusinessCode(BusinessCode businessCode);
	void deleteBusinessCode(int businessCodeId);
	List<BusinessCode> getBusinessCodeListAll(BusinessCode businessCode);
	void updateOrdering(BusinessCode businessCode);
	void updateDeleteOrdering(BusinessCode businessCode);
	void updateInsertOrdering(BusinessCode businessCode);
}
