package saleson.tran;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("odService")
public class OdServiceImpl implements OdService {
	@Autowired
	LpService lpService;


	@Override
	public void changePayment() {
		lpService.cancel();

		if (true) {
			throw new RuntimeException("PG 에러");
		}
	}
}
