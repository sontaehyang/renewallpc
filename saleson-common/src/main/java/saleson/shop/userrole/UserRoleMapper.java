package saleson.shop.userrole;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import com.onlinepowers.framework.security.userdetails.UserRole;

@Mapper("userroleMapper")
public interface UserRoleMapper {
	void insertUserRole(UserRole userRole);
	void deleteUserRole(long userId);
}
