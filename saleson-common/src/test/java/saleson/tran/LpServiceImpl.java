package saleson.tran;

import com.onlinepowers.framework.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("lpService")
public class LpServiceImpl implements LpService {

	@Autowired
	SequenceService sequenceService;

	@Override
	public void cancel() {
		System.out.println("lpService.cancel : " + sequenceService.getId("TEST"));

		use();
	}

	@Override
	public void use() {
		System.out.println("lpService.use : " + sequenceService.getId("TEST"));
	}


	@Override
	public void useNewTx() {
		System.out.println("lpService.useNewTx : " + sequenceService.getId("TEST"));
	}
}
