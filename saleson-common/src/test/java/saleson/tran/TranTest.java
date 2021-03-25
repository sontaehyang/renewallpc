package saleson.tran;

import com.onlinepowers.framework.sequence.service.SequenceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import saleson.common.SalesonTest;

public class TranTest extends SalesonTest {

	@Autowired
	OdRefundService tranService;

	@Autowired
	SequenceService sequenceService;

	@Autowired
	LpService lpService;

	//@Test
	public void sequenceTranTest() {

		System.out.println("Controller: " + sequenceService.getId("TEST"));


		try {
			tranService.refundProcess();
		} catch (Exception e) {

			lpService.useNewTx();
		}




	}
}
