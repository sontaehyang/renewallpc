package saleson.shop.access;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import saleson.shop.access.domain.Access;
import saleson.shop.access.support.AccessParam;

import java.util.HashMap;
import java.util.List;

@Mapper("accessMapper")
public interface AccessMapper {
	
	int getAllowIpCount(AccessParam accessParam);

	List<Access> getAllowIpList(AccessParam accessParam);

	void insertAllowIp(Access access);

	Access getAccess(int accessId);

	void edit(Access access);

	void deleteAllowIp(AccessParam accessParam);

    void deleteAllowIpById(HashMap<String, Object> params);
}
