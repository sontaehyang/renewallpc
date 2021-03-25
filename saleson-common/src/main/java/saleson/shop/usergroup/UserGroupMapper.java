package saleson.shop.usergroup;

import java.util.Map;

import saleson.shop.usergroup.domain.UserGroup;
import saleson.shop.usergroup.domain.UserGroupLog;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("usergroupMapper")
public interface UserGroupMapper {

	
	/**
	 * 해당 유저가 속한 그룹 삭제.
	 * @param userId
	 */
	public void deleteUserGroupByUserId(long userId);
	
	/**
	 * userId로 가입된 그룹을 불러온다.
	 * @param userId
	 * @return
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
	 * 회원 정보 DB에 그룹 설정 하기
	 * @param userGroup
	 */
	public void updateUserGroupCodeForUserDetail(UserGroup userGroup);
	
	/**
	 * 그룹 삭제
	 * @param groupCode
	 */
	public void deleteUserGroupCodeForUserDetail(String groupCode);
	
	/**
	 * 회원 그룹변경 로그 기록
	 * @param log
	 */
	public void insertUserGroupLog(UserGroupLog log);
}
