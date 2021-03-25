package saleson.shop.shadowlogin;

import com.onlinepowers.framework.sequence.service.SequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.shop.shadowlogin.domain.ShadowLoginLog;

@Service("shadowLoginLogService")
public class ShadowLoginLogServiceImpl implements ShadowLoginLogService {

	private static final Logger log = LoggerFactory.getLogger(ShadowLoginLogServiceImpl.class);

	@Autowired
	private ShadowLoginLogMapper shadowLoginLogMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Override
	public int insertShadowLoginLog(String loginTarget, long loginTargetId, long managerId) {
		
		try {
			
			int shadowLoginLogId = sequenceService.getId("OP_SHADOW_LOGIN_LOG");
			shadowLoginLogMapper.insertShadowLoginLog(new ShadowLoginLog(shadowLoginLogId, loginTarget, loginTargetId, managerId));
			return shadowLoginLogId;
			
		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}
		
		return 0;
	}
	
	@Override
	public void updateShadowLogoutLog(int shadowLoginLogId) {
		shadowLoginLogMapper.updateShadowLogoutLogById(shadowLoginLogId);
	}
	
}
