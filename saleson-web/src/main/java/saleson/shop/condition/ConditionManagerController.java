package saleson.shop.condition;

import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.shop.condition.domain.CategoryCondition;
import saleson.shop.condition.domain.Condition;
import saleson.shop.condition.domain.ConditionDetail;
import saleson.shop.condition.support.ConditionParam;

import java.util.List;


/**
 * @author seungil.lee
 * @since 2017-07-05
 */

@Controller
@RequestMapping("/opmanager/condition/**")
@RequestProperty(title="검색조건 설정", layout="default")
public class ConditionManagerController {
	private static final Logger log = LoggerFactory.getLogger(ConditionManagerController.class);
	
	@Autowired
	private ConditionService conditionService;
	
	// 목록
	@GetMapping("/list")
	public String conditionList(Model model) {
		List<CategoryCondition> list = conditionService.getCategoryConditionList();
		model.addAttribute("list", list);
		return ViewUtils.view();
	}
	
	@GetMapping("create/{categoryCode}")
	public String createConditionForm(ConditionParam conditionParam, Model model) {
		CategoryCondition categoryCondition = conditionService.getCategoryInfo(conditionParam);
		model.addAttribute("categoryCondition", categoryCondition);
		return ViewUtils.getView("/condition/form");
	}
	
	@PostMapping("create/{categoryCode}")
	public String createCondition(Condition condition, Model model) {
		
		conditionService.insertCondition(condition);
		return ViewUtils.redirect("/opmanager/condition/list");
	}
	
	@GetMapping("detail/list/{conditionId}")
	public String conditionDetailList(ConditionParam conditionParam, Model model,
			@RequestParam(value="paramUseYn", required=false) String paramUseYn) {
		if (!StringUtils.isEmpty(paramUseYn)) {
			conditionParam.setUseYn(paramUseYn);
		}
		CategoryCondition categoryCondition = conditionService.getCategoryCondition(conditionParam);
		model.addAttribute("categoryCondition", categoryCondition);
		model.addAttribute("conditionParam", conditionParam);
		return ViewUtils.getView("/condition/form");
	}
	
	@PostMapping("detail/list/{conditionId}")
	public String updateCondition(Condition condition, ConditionParam conditionParam, Model model,
			@RequestParam(value="paramUseYn", required=false) String paramUseYn) {
		conditionService.updateCondition(condition);
		String param="";
		if (!StringUtils.isEmpty(paramUseYn)) {
			param = "?paramUseYn=" + paramUseYn;
		}
		return ViewUtils.redirect("/opmanager/condition/detail/list/" + condition.getConditionId() + param);
	}
	
	@PostMapping("detail/form/{conditionId}")
	public String createConditionDetail(ConditionDetail conditionDetail, Model model) {
		if (conditionDetail.getDetailId() == 0) {
			conditionService.insertConditionDetail(conditionDetail);
		}
		else {
			conditionService.updateConditionDetail(conditionDetail);
		}
		return ViewUtils.redirect("/opmanager/condition/detail/list/" + conditionDetail.getConditionId());
	}
	
	@PostMapping("detail/change-ordering")
	public JsonView changeOrdering(ConditionParam conditionParam) {
		try {
			conditionService.updateDetailOrdering(conditionParam);
		} catch (Exception e) {
			return JsonViewUtils.failure(e.getMessage());
		}
		return JsonViewUtils.success();
	}
}
