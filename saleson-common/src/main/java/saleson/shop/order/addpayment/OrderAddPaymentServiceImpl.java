package saleson.shop.order.addpayment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.order.addpayment.domain.OrderAddPayment;

import com.onlinepowers.framework.sequence.service.SequenceService;

@Service("orderAddPaymentService")
public class OrderAddPaymentServiceImpl implements OrderAddPaymentService {

	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private OrderAddPaymentMapper orderAddPaymentMapper;
	
	@Override
	public int getDuplicateRegistrationCheckByIssueCode(OrderAddPayment orderAddPayment) {
		return orderAddPaymentMapper.getDuplicateRegistrationCheckByIssueCode(orderAddPayment);
	}
	
	@Override
	public void insertOrderAddPayment(OrderAddPayment orderAddPayment) {
		orderAddPayment.setAddPaymentId(sequenceService.getId("OP_ORDER_ADD_PAYMENT"));
		orderAddPaymentMapper.insertOrderAddPayment(orderAddPayment);
	}

	
	
}
