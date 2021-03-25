package saleson.shop.usergroup;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.common.utils.UserUtils;
import saleson.shop.group.GroupMapper;
import saleson.shop.group.domain.Group;
import saleson.shop.group.support.GroupSearchParam;
import saleson.shop.user.UserMapper;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.usergroup.domain.UserGroup;
import saleson.shop.usergroup.domain.UserGroupLog;
import saleson.shop.userlevel.UserLevelService;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.StringUtils;

@Service("userGroupService")
public class UserGroupServiceImpl implements UserGroupService {
	private static final Logger log = LoggerFactory.getLogger(UserGroupServiceImpl.class);
	
	@Autowired
	UserGroupMapper userGroupMapper;
	
	@Autowired
	SequenceService sequenceService;

	@Autowired
	private GroupMapper groupMapper;
	
	@Autowired
	private UserLevelService userLevelService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void deleteUserGroupByUserId(long userId) {

		userGroupMapper.deleteUserGroupByUserId(userId);
		
	}

	@Override
	public void insertUserGroups(Map map) {

		userGroupMapper.insertUserGroups(map);
	}

	@Override
	public void insertGroupsOfUsers(Map map) {

		userGroupMapper.insertGroupsOfUsers(map);
		
	}

	@Override
	public void deleteGroupsOfUsers(Map map) {

		userGroupMapper.deleteGroupsOfUsers(map);
	}

	@Override
	public void userGroupSetting(UserGroup userGroup) {

		
		if (StringUtils.isEmpty(userGroup.getGroupCode())) {
			throw new UserException("잘못된 접근 입니다.");
		}
		
		if (userGroup.getUserIds() == null) {
			throw new UserException("잘못된 접근 입니다.");
		}
		
		GroupSearchParam groupSearchParam = new GroupSearchParam();
		groupSearchParam.setGroupCode(userGroup.getGroupCode());
		
		Group group = groupMapper.getGroupDetail(groupSearchParam);
		if (group == null) {
			throw new UserException("잘못된 접근 입니다.");
		}
		
		for(String userId : userGroup.getUserIds()) {
			
			userGroup.setUserId(Integer.parseInt(userId));
			User user = userMapper.getUserByUserId(userGroup.getUserId());
			if (user == null) {
				continue;
			}
			
			UserDetail userDetail = (UserDetail) user.getUserDetail();

			
			if (!group.getGroupCode().equals(userDetail.getGroupCode())) {
				//userGroupMapper.updateUserGroupCodeForUserDetail(userGroup);
				userDetail.setGroupCode(userGroup.getGroupCode());
				userDetail.setUserLevelExpirationDate("");
				userDetail.setLevelId(0);
				userDetail.setConditionType("OPMANAGER");
				userMapper.updateUserDetail(userDetail);

				
				UserGroupLog log = new UserGroupLog();
				log.setUserId(userGroup.getUserId());
				log.setGroupCode(group.getGroupCode());
				log.setGroupName(group.getGroupName());
				
				log.setAdminUserName(UserUtils.getManagerName());
				userGroupMapper.insertUserGroupLog(log);
			}
			
			// 회원의 그룹 재설정
			if ("Y".equals(userGroup.getResetUserLevel())) {
				userLevelService.setUserLevel(user);
			}
			
		}
		
		/* CJH 시공 미디어에서는 회원 1명당 1그룹
		HashMap<String, Object> map = new HashMap<>();
		
		if(userGroup.getGroupIds() == null) { 										// 설정된 체크박스가 없을 시
			if(userGroup.getUserIds() == null){										// 한 명일 시
				deleteUserGroupByUserId(userGroup.getUserId());						// 기존 그룹 데이타 삭제
			}
			
			if(userGroup.getUserIds() != null){										// 다수의 이용자 일 시
				int[] userIds = new int[userGroup.getUserIds().length];
				for(int i=0; i < userGroup.getUserIds().length; i++) {
					userIds[i] = Integer.parseInt(userGroup.getUserIds()[i]);
				}
					map.put("userIds", userIds);
					deleteGroupsOfUsers(map);
			}
		} 
		else {																	// 설정 된 체크 박스가 있을 시		
			int[] groupIds = new int[userGroup.getGroupIds().length];
			for(int i = 0; i < userGroup.getGroupIds().length; i++) {
				groupIds[i] = Integer.parseInt(userGroup.getGroupIds()[i]);	// string groupids parsInt
			}
			
			if(mode==true && userGroup.getUserIds() != null) {							// 전체 설정 일떄
				int[] userIds = new int[userGroup.getUserIds().length];
				for(int i=0; i < userGroup.getUserIds().length; i++) {
					userIds[i] = Integer.parseInt(userGroup.getUserIds()[i]);
				}
				
				map.put("userIds", userIds);
				map.put("groupIds", groupIds);
				
				deleteGroupsOfUsers(map);								// 여러명의 기존 그룹 데이타 삭제.
				insertGroupsOfUsers(map);								// 여러명의 그룹 설정
				
			} else {																	// 개인 설정 일 때
				
				deleteUserGroupByUserId(userGroup.getUserId());		// 기존 그룹 데이타 삭제
				
				map.put("userId", userGroup.getUserId());
				map.put("groupIds", groupIds);
			
				insertUserGroups(map);
			}

		}
		*/
	}
	
}
