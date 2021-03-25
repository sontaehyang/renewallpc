package saleson.shop.claimmemo;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.Const;
import saleson.shop.claim.ClaimService;
import saleson.shop.claim.domain.ClaimMemo;
import saleson.shop.claim.support.ClaimMemoParam;

import java.util.List;

@Controller
@RequestMapping("/opmanager/claim-memo/**")
@RequestProperty(title="CLAIMMEMO", layout="default")
public class ClaimMemoManagerController {
	
	private static final Logger log = LoggerFactory.getLogger(ClaimMemoManagerController.class);
	
	@Autowired
	private ClaimService claimService;
	
	/**
	 * 상담내역 리스트
	 * @param param
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String faqList(Model model , ClaimMemoParam param) {
		
		param.setPageType("total");
		
		int totalCount = claimService.getClaimMemoCount(param);
		
		Pagination pagination = Pagination.getInstance(totalCount);	
		param.setPagination(pagination);
		
		List<ClaimMemo> ClaimMemolist = claimService.getClaimMemoList(param);
		
		String today = DateUtils.getToday(Const.DATE_FORMAT);
		
		model.addAttribute("today", today);
		model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
		model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
		model.addAttribute("month2", DateUtils.addYearMonthDay(today, 0, -2, 0));
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("ClaimMemolist", ClaimMemolist);
		model.addAttribute("ClaimMemoParam", param);
		model.addAttribute("pagination", pagination);
		
		return ViewUtils.view();
	}

}
