package saleson.shop.group;

import com.onlinepowers.framework.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.shop.group.domain.Group;
import saleson.shop.group.support.GroupSearchParam;
import saleson.shop.usergroup.UserGroupMapper;
import saleson.shop.userlevel.UserLevelMapper;
import saleson.shop.userlevel.domain.UserLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("GroupService")
public class GroupServiceImpl implements GroupService {

	@Autowired
	GroupMapper groupMapper;
	
	@Autowired
	SequenceService sequenceService;

	@Autowired
	UserGroupMapper userGroupMapper;

	@Autowired
	UserLevelMapper userLevelMapper;
	
	@Override
	public int getGroupCount(GroupSearchParam GroupSearchParam) {

		return groupMapper.getGroupCount(GroupSearchParam);
	}

	@Override
	public List<Group> getGroupList(GroupSearchParam GroupSearchParam) {

		return groupMapper.getGroupList(GroupSearchParam);
	}

	@Override
	public Group getGroupDetail(GroupSearchParam GroupSearchParam) {

		return groupMapper.getGroupDetail(GroupSearchParam);
	}

	@Override
	public void insertGroup(Group Group) {
		groupMapper.insertGroup(Group);
	}

	@Override
	public void updateGroup(Group Group) {

		groupMapper.updateGroup(Group);
	}

	@Override
	public void deleteGroup(String groupCode) {
		
		groupMapper.deleteGroup(groupCode);
		
		// 회원에게 설정된 그룹 삭제
		userGroupMapper.deleteUserGroupCodeForUserDetail(groupCode);
		
	}

	@Override
	public List<Group> getAllGroupList() {

		return groupMapper.getAllGroupList();
	}

	@Override
	public List<Group> getGroupIdByuserId(long userId) {

		return groupMapper.getGroupIdByuserId(userId);
	}

	@Override
	public List<Group> getGroupsAndUserLevelsAll() {
		// 1. Group, UserLevel 정보 조회
		List<Group> groups = groupMapper.getGroupsAll();

		if (groups == null) return new ArrayList<>();

		List<UserLevel> userLevels = userLevelMapper.getUserLevelsAll();
		groups.stream().forEach(g -> {
			List<UserLevel> newUserLevels = userLevels.stream()
					.filter(ul -> g.getGroupCode().equals(ul.getGroupCode()))
					.collect(Collectors.toList());

			g.setUserLevels(newUserLevels);
		});

		return groups;
	}
}


	


