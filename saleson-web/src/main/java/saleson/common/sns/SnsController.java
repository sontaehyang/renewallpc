package saleson.common.sns;

import com.onlinepowers.framework.sequence.service.SequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.sns.domain.SnsShare;

@Controller
@RequestMapping("/sns")
public class SnsController {
	private final static Logger log = LoggerFactory.getLogger(SnsController.class);
	
	@Autowired
	SnsService service;
	
	@Autowired
	SequenceService sequenceService;
	
	@PostMapping("share")
	public void snsShare(
			SnsShare snsShare
			){
		
		int snsShareCount = service.getSnsShareCount(snsShare);
		
		snsShare.setSnsCount(snsShareCount);
		
		if (snsShareCount > 0) {
			service.updateSnsShare(snsShare);
		} else {
			int shareId = sequenceService.getId("OP_SNS_SHARE");
			snsShare.setSnsId(shareId);
			service.insertSnsShare(snsShare);
		}
	}
}
