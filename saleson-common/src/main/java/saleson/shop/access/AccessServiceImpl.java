package saleson.shop.access;

import com.onlinepowers.framework.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.common.utils.UserUtils;
import saleson.shop.access.domain.Access;
import saleson.shop.access.support.AccessParam;

import java.util.HashMap;
import java.util.List;

@Service("accessService")

public class AccessServiceImpl implements AccessService {

	@Autowired
    AccessMapper accessMapper;

	@Autowired
    SequenceService sequenceService;

	@Override
	public int getAllowIpCount(AccessParam accessParam){
		return accessMapper.getAllowIpCount(accessParam);
	}
	
	@Override
	public List<Access> getAllowIpList(AccessParam accessParam){
		return accessMapper.getAllowIpList(accessParam);
	}
	
	@Override
	public void insertAllowIp(Access access){

		access.setAllowIpId(sequenceService.getId("OP_ALLOW_IP"));
		//		access.setAccessType("1"); 	// 접근타입(1: 관리자, 2: 판매자)
		access.setDisplayFlag("Y");
		access.setCreatedUser(UserUtils.getLoginId());
		accessMapper.insertAllowIp(access);
	}
	
	@Override
	public Access getAccess(int accessId){
		return accessMapper.getAccess(accessId);
	}
	
	@Override
	public void edit(Access access){
		accessMapper.edit(access);
	}
	
	@Override
	public void deleteAllowIp(AccessParam accessParam){
		accessMapper.deleteAllowIp(accessParam);
	}

    @Override
    public void deleteAllowIp(HashMap<String, Object> params) {
        accessMapper.deleteAllowIpById(params);
    }

}
