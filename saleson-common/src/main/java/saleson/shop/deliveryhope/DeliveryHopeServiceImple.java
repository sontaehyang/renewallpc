package saleson.shop.deliveryhope;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.deliveryhope.domain.DeliveryHope;

import com.onlinepowers.framework.sequence.service.SequenceService;


@Service("deliveryHopeService")
public class DeliveryHopeServiceImple implements DeliveryHopeService{

	@Autowired
	private DeliveryHopeMapper deliveryHopeMapper;
	
	@Autowired
	private SequenceService sequenceService;

	
	@Override
	public List<DeliveryHope> getDeliveryHopeList() {
		return deliveryHopeMapper.getDeliveryHopeList();
	}


	@Override
	public void insertDeliveryHope(DeliveryHope deliveryHope) {
		deliveryHope.setDeliveryHopeId(sequenceService.getId("OP_DELIVERY_HOPE"));
		deliveryHopeMapper.insertDeliveryHope(deliveryHope);
	}


	@Override
	public void updateDeliveryHope(DeliveryHope deliveryHope) {
		deliveryHopeMapper.updateDeliveryHope(deliveryHope);
	}


	@Override
	public void deleteDeliveryHope(int deliveryHopeId) {
		deliveryHopeMapper.deleteDeliveryHope(deliveryHopeId);
	}

}
