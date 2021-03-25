package saleson.shop.group;

import java.util.List;
import java.util.Map;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.group.domain.Group;
import saleson.shop.group.support.GroupSearchParam;
import saleson.shop.usergroup.domain.UserGroupLog;

@Mapper("groupMapper")
public interface GroupMapper {
	
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
	 * 모든 회원 그룹 리스트
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
	 * @param groupCode
	 */
	public void deleteGroup(String groupCode);
	
	
	/**
	 * 유저아이디로 가입된 그룹을 불러온다.
	 * @param userId
	 * @return
	 */
	public List<Group> getGroupIdByuserId(long userId);


	/**
	 * 그룹과 회원등급 목록을 조회함
	 * @return
	 */
	List<Group> getGroupsAll();
}