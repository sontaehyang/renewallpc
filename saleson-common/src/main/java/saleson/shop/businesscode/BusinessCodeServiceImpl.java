package saleson.shop.businesscode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.sequence.service.SequenceService;

import saleson.shop.businesscode.domain.BusinessCode;

@Service("businessCodeServiec")
public class BusinessCodeServiceImpl implements BusinessCodeService{
	@Autowired
	BusinessCodeMapper businessCodeMapper;
	
	@Autowired
	SequenceService sequenceService;
	
	@Override
	public int getBusinessCodeCount(BusinessCode businessCode) {

		return businessCodeMapper.getBusinessCodeCount(businessCode);
	}

	@Override
	public List<BusinessCode> getBusinessCodeList(BusinessCode businessCode) {

		return businessCodeMapper.getBusinessCodeList(businessCode);
	}

	@Override
	public void insertBusinessCode(BusinessCode businessCode) {

		businessCode.setBusinessCodeId(sequenceService.getId("OP_BUSINESS_CODE"));
		businessCodeMapper.updateInsertOrdering(businessCode);
		businessCodeMapper.insertBusinessCode(businessCode);
	}

	@Override
	public void updateBusinessCode(BusinessCode businessCode) {

		if (businessCode.getPreviousOrdering() != businessCode.getOrdering()) {
			businessCodeMapper.updateOrdering(businessCode);
		}
		businessCodeMapper.updateBusinessCode(businessCode);
	}

	@Override
	public void deleteBusinessCode(int businessCodeId) {

		BusinessCode businessCode = businessCodeMapper.getBusinessCode(businessCodeId);
		businessCodeMapper.updateDeleteOrdering(businessCode);
		businessCodeMapper.deleteBusinessCode(businessCodeId);
	}

	@Override
	public List<BusinessCode> getBusinessCodeListAll(BusinessCode businessCode) {

		return businessCodeMapper.getBusinessCodeListAll(businessCode);
	}

}
