package saleson.shop.deliverycompany;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.deliverycompany.support.DeliveryCompanyParam;

import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.web.domain.ListParam;

@Service("deliveryCompanyService")
public class DeliveryCompanyServiceImpl implements DeliveryCompanyService {

	@Autowired
	private DeliveryCompanyMapper deliveryCompanyMapper;
	
	@Autowired
	SequenceService sequenceService;

	@Override
	public List<DeliveryCompany> getDeliveryCompanyList(DeliveryCompanyParam deliveryCompanyParam) {
		return deliveryCompanyMapper.getDeliveryCompanyList(deliveryCompanyParam);
	}

	@Override
	public void deleteDeliveryCompanyById(ListParam listParam) {
		
		if (listParam.getId() != null) {
			for (String deliveryCompanyId : listParam.getId()) {
				deliveryCompanyMapper.deleteDeliveryCompanyById(Integer.parseInt(deliveryCompanyId));
			}	
		}
	}

	@Override
	public int getDeliveryCompanyCount(DeliveryCompanyParam deliveryCompanyParam) {
		return deliveryCompanyMapper.getDeliveryCompanyCount(deliveryCompanyParam);
	}

	@Override
	public DeliveryCompany getDeliveryCompanyById(int deliveryCompanyId) {
		return deliveryCompanyMapper.getDeliveryCompanyById(deliveryCompanyId);
	}

	@Override
	public void updateDeliveryCompanyById(DeliveryCompany deliveryCompany) {
		deliveryCompanyMapper.updateDeliveryCompanyById(deliveryCompany);
	}

	@Override
	public void insertDeliveryCompany(DeliveryCompany deliveryCompany) {
		deliveryCompany.setDeliveryCompanyId(sequenceService.getId("OP_DELIVERY_COMPANY"));
		deliveryCompanyMapper.insertDeliveryCompany(deliveryCompany);
	}
}
