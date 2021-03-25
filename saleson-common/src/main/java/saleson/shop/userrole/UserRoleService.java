package saleson.shop.userrole;

import com.onlinepowers.framework.security.userdetails.UserRole;


public interface UserRoleService {
	
	/**
	 * 관리자 권한 등록
	 * @param userRole
	 */
	public void insertUserRole(UserRole userRole);
	
	/**
	 * 관리자 권한 삭제
	 * @param userRole
	 */
	public void deleteUserRole(long userId);
}
