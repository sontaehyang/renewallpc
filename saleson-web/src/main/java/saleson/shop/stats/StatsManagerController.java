package saleson.shop.stats;

import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.SecurityUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import saleson.shop.stats.domain.DayStats;
import saleson.shop.stats.domain.MonthStats;
import saleson.shop.stats.support.StatsSearchParam;
import saleson.shop.stats.support.VisitExeclView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/opmanager/stats")
@RequestProperty(layout="default")
public class StatsManagerController {
	@Autowired
	private StatsService statsService;
	
	
	/**
	 * 접속통계
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("visit/index")
	public String visit(StatsSearchParam searchParam, Model model) {
		String currentYear = DateUtils.getToday().substring(0, 4);
		String currentMonth = DateUtils.getToday().substring(4, 6);
		String currentYearMonth = DateUtils.getToday().substring(0, 6);
		if (searchParam.getStartDate() == null) {
			searchParam.setStartDate(currentYear);
		}
		
		if (searchParam.getEndDate() == null) {
			searchParam.setEndDate(currentYear);
		}
		
		HttpSession session = RequestContextUtils.getSession();
		session.setAttribute("reStatsSearchParam", searchParam);
		
		model.addAttribute("searchParam", searchParam);
		model.addAttribute("currentYear", currentYear);
		model.addAttribute("currentMonth", currentMonth);
		model.addAttribute("summary", statsService.getVisitSummary());
		model.addAttribute("list", statsService.getMonthStatsList(searchParam));
		model.addAttribute("dayStatsList", statsService.getDayStatsList(currentYearMonth));
		return ViewUtils.view();
	}
	
	
	/**
	 * 일별 접속 통계 
	 * @param yearMonth
	 * @param model
	 * @return
	 */
	@GetMapping("visit/day")
	@RequestProperty(layout="blank")
	public String visitDay(@RequestParam("yearMonth") String yearMonth, Model model) {

		if (yearMonth != null && yearMonth.length() >= 6) {
			model.addAttribute("year", yearMonth.substring(0, 4));
			model.addAttribute("month", yearMonth.substring(4, 6));
			model.addAttribute("dayStatsList", statsService.getDayStatsList(yearMonth));
		}

		return ViewUtils.view();
	}
	
	
	/**
	 * 접속 경로 통계
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("referer")
	public String referer(StatsSearchParam searchParam, Model model) {
		String startDate = DateUtils.addDay(DateUtils.getToday(), -7);
		String endDate = DateUtils.getToday();
		if (searchParam.getStartDate() == null) {
			searchParam.setStartDate(startDate);
		}
		
		if (searchParam.getEndDate() == null) {
			searchParam.setEndDate(endDate);
		}
		
		model.addAttribute("searchParam", searchParam);
		model.addAttribute("domainList", statsService.getVisitCountByDomain(searchParam));
		model.addAttribute("browserList", statsService.getVisitCountByBrowser(searchParam));
		model.addAttribute("osList", statsService.getVisitCountByOS(searchParam));
		return ViewUtils.view();
	}
	
	
	/**
	 * 배송준비 목록 엑셀 다운로드
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping(value="visit/download-excel")
	public ModelAndView downloadExcelProcess(){

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		//Execl
		ModelAndView mav = new ModelAndView(new VisitExeclView());
		
		HttpSession session = RequestContextUtils.getSession();
		StatsSearchParam searchParam = (StatsSearchParam)session.getAttribute("reStatsSearchParam");
		searchParam.setConditionType("EXCEL_DOWNLOAD");
		
		List<MonthStats> monthStatsList = statsService.getMonthStatsList(searchParam);
		HashMap<String, List<DayStats>> dayStatsListByYear = new HashMap<String, List<DayStats>>();
		
		for (MonthStats monthStats: monthStatsList) {
			
			List<DayStats>	dayStats = statsService.getDayStatsList(monthStats.getYearMonth());
			
			dayStatsListByYear.put(monthStats.getYearMonth(), dayStats);
			
		}
		
		
		mav.addObject("monthStatsList", monthStatsList);
		mav.addObject("dayStatsListByYear", dayStatsListByYear);
		mav.addObject("searchParam", searchParam);
		
		return mav;
	}
}
