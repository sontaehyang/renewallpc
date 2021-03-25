package saleson.shop.userrole;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.security.userdetails.UserRole;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
	private static final Logger log = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
	@Autowired
	UserRoleMapper userRoleMapper;

	@Override
	public void insertUserRole(UserRole userRole) {
		userRoleMapper.insertUserRole(userRole);
	}

	@Override
	public void deleteUserRole(long userId) {
		userRoleMapper.deleteUserRole(userId);
	}
	
	
}
