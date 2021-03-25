package saleson.shop.access;

import saleson.shop.access.domain.Access;
import saleson.shop.access.support.AccessParam;

import java.util.HashMap;
import java.util.List;

public interface AccessService {
	
	public int getAllowIpCount(AccessParam accessParam);
	
	public List<Access> getAllowIpList(AccessParam accessParam);
	
	public void insertAllowIp(Access access);
	
	public Access getAccess(int accessId);
	
	public void edit(Access access);
	
	public void deleteAllowIp(AccessParam accessParam);

    public void deleteAllowIp(HashMap<String, Object> params);

}
