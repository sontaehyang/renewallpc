package saleson.tran;

import com.onlinepowers.framework.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OdRefundServiceImpl implements OdRefundService {

	@Autowired
	SequenceService sequenceService;

	@Autowired
	OdService odService;

	@Override
	public void refundProcess() {
		System.out.println("REFUND_PROCESS 1: " + sequenceService.getId("TEST"));
		System.out.println("REFUND_PROCESS 2: " + sequenceService.getId("TEST"));
		System.out.println("REFUND_PROCESS 3: " + sequenceService.getId("TEST"));


		odService.changePayment();

	}

}
