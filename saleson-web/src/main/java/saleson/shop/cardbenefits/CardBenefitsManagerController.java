package saleson.shop.cardbenefits;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.Const;
import saleson.shop.cardbenefits.domain.CardBenefits;
import saleson.shop.cardbenefits.support.CardBenefitsParam;

import java.util.List;

@Controller
@RequestMapping("/opmanager/card-benefits")
@RequestProperty(title="카드혜택 관리", layout="default")
public class CardBenefitsManagerController {
	
	@Autowired
	private CardBenefitsService cardBenfitsService;
	
	/**
	 * 카드 혜택 리스트
	 * @param cardBenefitsParam
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String list(@ModelAttribute CardBenefitsParam cardBenefitsParam, Model model) {
		
		int benefitsCount = cardBenfitsService.getCardBenefitsCount(cardBenefitsParam);
		
		Pagination pagination = Pagination.getInstance(benefitsCount);

		cardBenefitsParam.setPagination(pagination);

		List<CardBenefits> benefitsList = cardBenfitsService.getCardBenefitsList(cardBenefitsParam);
		
		String today = DateUtils.getToday(Const.DATE_FORMAT);
		
		model.addAttribute("benefitsCount", benefitsCount);
		model.addAttribute("today", today);
		model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
		model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
		model.addAttribute("month3", DateUtils.addYearMonthDay(today, 0, -3, 0));
		model.addAttribute("benefitsList", benefitsList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("CardBenefitsParam", cardBenefitsParam);
		
		return ViewUtils.getView("/card-benefits/list");
		
		
	}
	
	@GetMapping("/form")
	public String form(CardBenefits cardBenefits ,Model model) {
		
		model.addAttribute("cardBenefits", cardBenefits);
		
		return ViewUtils.getView("/card-benefits/form");		
	}
	
	@PostMapping("/insert")
	public String insertBenefits(CardBenefits cardBenefits ,Model model) {
		
		/*if (cardBenfitsService.getDuplicateCardBenefitsCount(cardBenefits.getStartDate()) > 0) {
			return ViewUtils.redirect("/opmanager/card-benefits/form", "일자 중복입니다.");
		}*/
		
		// 기간내 카드 혜택 존재하는지 체크
		periodCheck(cardBenefits);
		
		try{
			// 이미지 파일 관련 추가.
			cardBenfitsService.insertCardBenefits(cardBenefits);
		} catch(Exception e) {
			
			return ViewUtils.redirect("/opmanager/card-benefits/list", "등록 실패!");
		}
		
		return ViewUtils.redirect("/opmanager/card-benefits/list", "등록 되었습니다.");	
		
	}
	
	@GetMapping("/edit/{benefitsId}")
	public String edit(@PathVariable("benefitsId") String benefitsId ,Model model) {
		
		model.addAttribute("cardBenefits", cardBenfitsService.getCardBenefits(Integer.parseInt(benefitsId)));
		model.addAttribute("mode", 1);
		
		return ViewUtils.getView("/card-benefits/form");		
	}
	
	@PostMapping("/edit")
	public String edit(CardBenefits cardBenefits ,Model model) {
		
		/*if (cardBenfitsService.getDuplicateCardBenefitsCount(cardBenefits.getStartDate()) > 0) {
			return ViewUtils.redirect("/opmanager/card-benefits/edit/"+cardBenefits.getBenefitsId(), "일자 중복입니다.");
		}*/
		
		// 기간내 카드 혜택 존재하는지 체크
		periodCheck(cardBenefits);
		
		try{
			// 이미지 파일 관련 추가.
			cardBenfitsService.updateCardBenefits(cardBenefits);
			
		} catch(Exception e) {
			
			return ViewUtils.redirect("/opmanager/card-benefits/list", "수정 실패!");
		}
		
		return ViewUtils.redirect("/opmanager/card-benefits/list", "수정 되었습니다.");	
		
		
	}
	
	@GetMapping("/delete")
	public String delete(CardBenefitsParam cardBenefitsParam ,Model model) {
		
		try{
			// 이미지 파일 관련 추가.
			cardBenfitsService.deleteCardBenefits(cardBenefitsParam);
			
		} catch(Exception e) {
			
			return ViewUtils.redirect("/opmanager/card-benefits/list", "삭제 실패!");
		}
		
		return ViewUtils.redirect("/opmanager/card-benefits/list", "삭제 되었습니다.");	
		
		
	}
	
	// 기간내 카드 혜택 존재하는지 체크 2017-03-08_seungil.lee
	@GetMapping("/check-period")
	public JsonView periodCheck(CardBenefits cardBenefits) {
		List<CardBenefits> cardBenefitsList = cardBenfitsService.checkPeriod(cardBenefits);
		String message = "";
		if (!cardBenefitsList.isEmpty()) {
			CardBenefits result = cardBenefitsList.get(0);
			message = "지정기간에 [ " + result.getSubject() + " (" + DateUtils.formatDate(result.getStartDate(),"-") +  "~" + DateUtils.formatDate(result.getEndDate(),"-") + ") ]";
			if (cardBenefitsList.size() > 1) {
				message += "외 " + String.valueOf(cardBenefitsList.size()-1) + "건의 카드 혜택이 있습니다.";
			} else {
				message += " 혜택이\n등록되어 있습니다.";
			}
			message += "\n카드혜택은 중복되어 등록 될 수 없습니다.";
			return JsonViewUtils.failure(message);
		}
		return JsonViewUtils.success();
	}
	
}
