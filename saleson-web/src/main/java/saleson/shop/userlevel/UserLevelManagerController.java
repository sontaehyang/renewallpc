package saleson.shop.userlevel;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import saleson.shop.userlevel.domain.UserLevel;
import saleson.shop.userlevel.support.UserLevelSearchParam;

import java.util.List;

@Controller
@RequestMapping("/opmanager/user-level")
@RequestProperty(title="회원관리", layout="default", template="opmanager")
public class UserLevelManagerController {
	
	@Autowired
	private UserLevelService userLevelService;
	
	
	/**
	 * 회원 레벨 리스트
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/list/{groupCode}")
	public String list(RequestContext requestContext, Model model, UserLevelSearchParam userLevelSearchParam) {
		
		int userLevelCount = userLevelService.getUserLevelCount(userLevelSearchParam);
		List<UserLevel> list = userLevelService.getUserLevelList(userLevelSearchParam);
		
		
		model.addAttribute("userLevelCount", userLevelCount);
		model.addAttribute("list", list);
		
		return "view:/user-level/list";
	}
	
	/**
	 * 회원 레벨 등록 폼
	 * @param requestContext
	 * @param model
	 * @param userLevel
	 * @return
	 */
	@GetMapping("/create/{groupCode}")
	public String create(RequestContext requestContext, Model model, UserLevel userLevel) {

		model.addAttribute("formAction", "create");
		model.addAttribute("requestContext", requestContext);
		return "view:/user-level/form";
	}
	
	/**
	 * 회원 레벨 등록 
	 * @param requestContext
	 * @param model
	 * @param userLevel
	 * @return
	 */
	@PostMapping("/create")
	public String createAction(RequestContext requestContext, Model model,UserLevel userLevel,
			@RequestParam(value="uploadfile[]", required=false) MultipartFile[] multipartFiles) {
		
		UserLevelSearchParam userLevelSearchParam = new UserLevelSearchParam();
		userLevelSearchParam.setLevelName(userLevel.getLevelName());
		int userLevelCount = userLevelService.getUserLevelCount(userLevelSearchParam);
		
		if (userLevelCount > 0) {
			throw new UserException(MessageUtils.getMessage("M00295"),"/opmanager/user-level/list/" + userLevel.getGroupCode());
		}
		
		if (!FileUtils.isPossibleUploadFile(multipartFiles,"*.jpg;*.jpeg;*.gif;*.png;")) {
			throw new UserException(MessageUtils.getMessage("M00296"),"/opmanager/user-level/create");
		}
		
		userLevelService.insertUserLevel(userLevel,multipartFiles);
		return ViewUtils.redirect("/opmanager/user-level/list/" + userLevel.getGroupCode(), MessageUtils.getMessage("M00288"));
	}
	
	
	/**
	 * 회원 레벨 수정 폼
	 * @param requestContext
	 * @param model
	 * @param userLevel
	 * @param userLevelSearchParam
	 * @return
	 */
	@GetMapping("/edit/{levelId}")
	public String edit(RequestContext requestContext, Model model, UserLevelSearchParam userLevelSearchParam) {
		UserLevel userLevel = userLevelService.getUserLevelDetail(userLevelSearchParam);
		
		if (ValidationUtils.isNull(userLevel)){
			throw new UserException(MessageUtils.getMessage("M00297"),"/opmanager/user-level/list/default");
		}
		
		model.addAttribute("formAction", "edit");
		model.addAttribute("userLevel",userLevel);
		model.addAttribute("requestContext",requestContext);
		
		return "view:/user-level/form";
	}
	
	/**
	 * 회원 레벨 수정
	 * @param requestContext
	 * @param model
	 * @param userLevel
	 * @return
	 */
	
	@PostMapping("/edit")
	public String editAction(RequestContext requestContext, Model model, 
			UserLevel userLevel, @RequestParam(value="uploadfile[]", required=false) MultipartFile[] multipartFiles) {
		
		if (!FileUtils.isPossibleUploadFile(multipartFiles,"*.jpg;*.jpeg;*.gif;*.png;")) {
			throw new UserException(MessageUtils.getMessage("M00296"),"/opmanager/user-level/edit/"+userLevel.getLevelId());
		}
		
		userLevelService.updateUserLevel(userLevel,multipartFiles);
		return ViewUtils.redirect("/opmanager/user-level/list/" + userLevel.getGroupCode(), MessageUtils.getMessage("M00289"));
	}
	
	@GetMapping("/delete/{levelId}")
	public String delete(RequestContext requestContext, UserLevelSearchParam userLevelSearchParam) {
		
		UserLevel userLevel = userLevelService.getUserLevelDetail(userLevelSearchParam);
		
		if (userLevel == null){
			throw new UserException(MessageUtils.getMessage("M00297"),"/opmanager/user-level/list/default");
		}
		
		if (userLevel.getUserCount() > 0) {
			return ViewUtils.redirect("/opmanager/user-level/list/" + userLevel.getGroupCode(), "해당 레벨을 삭제하기 위해서는 해당 레벨으로 설정된 회원을 다른 레벨로 이전후 삭제 가능합니다.");
		}
		
		userLevelService.deleteUserLevel(userLevelSearchParam.getLevelId());
		
		return ViewUtils.redirect("/opmanager/user-level/list/" + userLevel.getGroupCode(), MessageUtils.getMessage("M00205"));
	}
	
	@PostMapping("/file-delete/{levelId}")
	public JsonView fileDelete(RequestContext requestContext, @PathVariable("levelId") int levelId){
	
		try {
			userLevelService.updateUserLevelFileDelete(levelId);
		} catch (Exception e) {
			return JsonViewUtils.exception(e.getMessage());
		}
		
		return JsonViewUtils.success();
	}
	
}
