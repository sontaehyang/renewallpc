package saleson.shop.usergroup;

import java.util.Map;

import saleson.shop.usergroup.domain.UserGroup;


public interface UserGroupService {
		

	/**
	 * 유저아이디로 해당 유저가 속한 group을 삭제
	 * @param userId
	 */
	public void deleteUserGroupByUserId(long userId);
	
	
	/**
	 * 해당 유저를 그룹에 추가, 수정
	 * @param map
	 */
	public void insertUserGroups(Map map);
	
	
	/**
	 * 해당 유저들의 그룹을 수정.
	 * @param map
	 */
	public void insertGroupsOfUsers(Map map);
	
	
	/**
	 * 해당 유저들의 그룹을 삭제
	 * @param map
	 */
	public void deleteGroupsOfUsers(Map map);
	
	
	/**
	 * 유저 그룹 등록, 삭제 처리
	 * @param userGroup
	 * @param model
	 */
	public void userGroupSetting(UserGroup userGroup);

}
