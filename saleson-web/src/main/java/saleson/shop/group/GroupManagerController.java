package saleson.shop.group;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleson.common.utils.UserUtils;
import saleson.shop.group.domain.Group;
import saleson.shop.group.support.GroupSearchParam;

import java.util.List;

@Controller
@RequestMapping("/opmanager/group")
@RequestProperty(title="회원관리", layout="default", template="opmanager")
public class GroupManagerController {
	
	@Autowired
	private GroupService GroupService;
	
	
	/**
	 * 회원 그룹 리스트
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/list")
	public String list(RequestContext requestContext, Model model,
			GroupSearchParam groupParam) {
		
		int groupCount = GroupService.getGroupCount(groupParam);
		Pagination pagination = Pagination.getInstance(groupCount);
		groupParam.setPagination(pagination);
		groupParam.setConditionType("OPMANAGER");
		
		List<Group> list = GroupService.getGroupList(groupParam);
		
		model.addAttribute("groupParam", groupParam);
		model.addAttribute("pagination", pagination);
		model.addAttribute("GroupCount", groupCount);
		model.addAttribute("list", list);
		
		return ViewUtils.view();
	}
	
	/**
	 *  그룹 등록 폼
	 * @param requestContext
	 * @param model
	 * @param Group
	 * @return
	 */
	@GetMapping("/create")
	public String create(RequestContext requestContext, Model model,Group Group)
	{
		model.addAttribute("Group", Group);
		model.addAttribute("formAction", "create");
		model.addAttribute("requestContext",requestContext);
		
		return ViewUtils.view();
	}
	
	/*
	 * 회원 그룹 등록 
	 * @param requestContext
	 * @param model
	 * @param Group
	 * @return
	 */
	@PostMapping("/create")
	public String createAction(RequestContext requestContext, Model model,Group Group)
	{
		GroupSearchParam GroupSearchParam = new GroupSearchParam();
		GroupSearchParam.setGroupName(Group.getGroupName());
		int GroupCount = GroupService.getGroupCount(GroupSearchParam);
		
		if (GroupCount > 0) {
			throw new UserException(MessageUtils.getMessage("M00295"),"/opmanager/group/list");
		}
		Group.setCreatedUserId(UserUtils.getLoginId());
		
		GroupService.insertGroup(Group);
		
		return ViewUtils.redirect("/opmanager/group/list",MessageUtils.getMessage("M00288"));
	}
	
	
	/**
	 * 회원 그룹 수정 폼
	 * @param requestContext
	 * @param model
	 * @param group
	 * @param GroupSearchParam
	 * @return
	 */
	@GetMapping("/edit/{groupCode}")
	public String edit(RequestContext requestContext, Model model, GroupSearchParam GroupSearchParam) {
		Group Group = GroupService.getGroupDetail(GroupSearchParam);
		
		if (ValidationUtils.isNull(Group)){
			throw new UserException(MessageUtils.getMessage("M00297"),"/opmanager/group/list");
		}
		
		model.addAttribute("formAction", "edit");
		model.addAttribute("Group",Group);
		model.addAttribute("requestContext",requestContext);
		
		return "view:/group/form";
	}
	
	/**
	 * 회원 그룹 수정
	 * @param requestContext
	 * @param model
	 * @param group
	 * @return
	 */
	
	@PostMapping("/edit")
	public String editAction(RequestContext requestContext, Model model, Group Group) {

		Group.setUpdatedUserId(UserUtils.getLoginId()); 			
		GroupService.updateGroup(Group);
		
		return ViewUtils.redirect("/opmanager/group/list", MessageUtils.getMessage("M00289"));
	}
	
	/**
	 * 회원 그룹 삭제
	 * @param GroupSearchParam
	 * @return
	 */
	@GetMapping("/delete/{groupCode}")
	public String delete(GroupSearchParam GroupSearchParam) {
		
		Group group = GroupService.getGroupDetail(GroupSearchParam);
		if (group == null) {
			throw new PageNotFoundException();
		}
		
		if (group.getUserCount() > 0) {
			return ViewUtils.redirect("/opmanager/group/list", "해당 그룹을 삭제하기 위해서는 해당 그룹으로 설정된 회원을 다른 그룹으로 이전후 삭제 가능합니다.");
		}
		
		// OP_USER_GROUP 테이블에 있는 해당 그룹 값도 삭제해줘야함.
		GroupService.deleteGroup(GroupSearchParam.getGroupCode());
		
		return ViewUtils.redirect("/opmanager/group/list",MessageUtils.getMessage("M00205"));
	}
	
	
}
