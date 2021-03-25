package saleson.shop.group;

import java.util.List;
import java.util.Map;

import saleson.shop.group.domain.Group;
import saleson.shop.group.support.GroupSearchParam;


public interface GroupService {
	
	/**
	 * 회원 그룹 카운트
	 * @param GroupSearchParam
	 * @return
	 */
	public int getGroupCount(GroupSearchParam GroupSearchParam);
	
	/**
	 * 회원 그룹 리스트
	 * @return
	 */
	public List<Group> getGroupList(GroupSearchParam GroupSearchParam);
	
	/**
	 * 조건없는 회원 그룹 리스트
	 * @return
	 */
	public List<Group> getAllGroupList();
	/**
	 * 회원 그룹 상세 정보
	 * @param Group
	 * @return
	 */
	public Group getGroupDetail(GroupSearchParam GroupSearchParam);
	
	/**
	 * 회원 그룹 등록
	 * @param Group
	 */
	public void insertGroup(Group Group);
	
	/**
	 * 회원 그룹 수정
	 * @param Group
	 */
	public void updateGroup(Group Group);
	
	/**
	 * 회원 그룹 삭제
	 * @param GroupId
	 */
	public void deleteGroup(String groupCode);
	
	/**
	 * userId로 가입된 그룹을 불러온다.
	 * @param userId
	 * @return
	 */
	public List<Group> getGroupIdByuserId(long userId);


	/**
	 * 모든 그룹 정보 및 USER_LEVEL 정보를 조회함
	 * (회원 등급 산정시 시용됨 - UesrLevel 정보는 ORDER BY GROUP_CODE DESC, DEPTH DESC )
	 * @return
	 */
	public List<Group> getGroupsAndUserLevelsAll();

}
