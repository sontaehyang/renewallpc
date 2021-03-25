package saleson.shop.point;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.SecurityUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import saleson.shop.group.GroupService;
import saleson.shop.point.domain.PointDayGroup;
import saleson.shop.point.domain.PointHistory;
import saleson.shop.point.domain.PublicationPoint;
import saleson.shop.point.support.DayGroupExcelView;
import saleson.shop.point.support.PointParam;
import saleson.shop.point.support.PublicationPointExcelView;
import saleson.shop.point.support.TotalPointHistoryExcelView;
import saleson.shop.userlevel.UserLevelService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/opmanager/point")
@RequestProperty(title="포인트 내역", layout="default", template="opmanager")
public class PointManagerController {

	@Autowired
	private PointService pointService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserLevelService userLevelService;
	
	/**
	 * 발행 내역
	 * @param pointParam
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String list(@ModelAttribute PointParam pointParam, Model model) {
		
		if (StringUtils.isEmpty(pointParam.getSearchStartDate())) {
			pointParam.setSearchStartDate(DateUtils.getToday("yyyyMM") + "01");
			pointParam.setSearchEndDate(DateUtils.getToday());
		}
		
		List<PublicationPoint> list = pointService.getPublicationPointListByParamForManager(pointParam);

		/*model.addAttribute("groupList", groupService.getAllGroupList());
		model.addAttribute("userLevelGroup", userLevelService.getUserLevelGroupList(null));*/
		model.addAttribute("list", list);
		model.addAttribute("pagination", pointParam.getPagination());
		model.addAttribute("totalCount", pointParam.getPagination().getTotalItems());

		return "view:/point/list";
	}

	/**
	 * 발행 내역 엑셀 다운로드
	 * @param pointParam
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping(value="download-excel")
	public ModelAndView downloadExcelProcess (PointParam pointParam, HttpServletRequest request) {

		pointParam.setConditionType("USER-POINT-EXCEL");

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
			// 엑셀 다운로드 + 개인정보 권한이 있는 경우
		} else if(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS")){
			// excelReadingLogService.insertExcelReadingUserLog(request,ExcelReadingLog.EXCEL_TYPE_ISSUE_POINT,"");
		}

		ModelAndView mav = new ModelAndView(new PublicationPointExcelView());
		
		mav.addObject("list", pointService.getPublicationPointListByParamForManagerExcelDownload(pointParam));
		return mav;
	}
	
	/**
	 * 사용 내역
	 * @param pointParam
	 * @param model
	 * @return
	 */
	@GetMapping("total-history/list")
	public String totalHistory(@ModelAttribute PointParam pointParam, Model model) {
		
		if (StringUtils.isEmpty(pointParam.getSearchStartDate())) {
			pointParam.setSearchStartDate(DateUtils.getToday("yyyyMM") + "01");
			pointParam.setSearchEndDate(DateUtils.getToday());
		}
		
		List<PointHistory> list = pointService.getPointTotalHistoryListByParam(pointParam);
		
		model.addAttribute("list", list);
		model.addAttribute("pagination", pointParam.getPagination());
		model.addAttribute("totalCount", pointParam.getPagination().getTotalItems());

		return "view:/point/total-history";
	}
	
	/**
	 * 사용 내역 엑셀 다운로드
	 * @param pointParam
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping(value="history-download-excel")
	public ModelAndView historyDownloadExcelProcess (PointParam pointParam, HttpServletRequest request) {

		pointParam.setConditionType("HISTORY-POINT-EXCEL");

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
			// 엑셀 다운로드 + 개인정보 권한이 있는 경우
		} else if(SecurityUtils.hasRole("ROLE_EXCEL") && SecurityUtils.hasRole("ROLE_ISMS")){
			// excelReadingLogService.insertExcelReadingUserLog(request,ExcelReadingLog.EXCEL_TYPE_HISTORY_POINT,"");
		}

		ModelAndView mav = new ModelAndView(new TotalPointHistoryExcelView());
		
		mav.addObject("list", pointService.getPointTotalHistoryListByParamForExcelDownload(pointParam));
		return mav;
	}
	
	/**
	 * 일별 발생/사용현황
	 * @param pointParam
	 * @param model
	 * @return
	 */
	@GetMapping("day-group/list")
	public String dayGroupList(@ModelAttribute PointParam pointParam, Model model) {
		
		if (StringUtils.isEmpty(pointParam.getSearchStartDate())) {
			pointParam.setSearchStartDate(DateUtils.getToday("yyyyMM") + "01");
			pointParam.setSearchEndDate(DateUtils.getToday());
		}
		
		List<PointDayGroup> list = pointService.getPointDayGroupListByParam(pointParam);
		
		model.addAttribute("list", list);
		model.addAttribute("pagination", pointParam.getPagination());
		model.addAttribute("totalCount", pointParam.getPagination().getTotalItems());

		return "view:/point/day-group";
	}
	
	/**
	 * 일별 발생/사용현황 엑셀다운로드
	 * @param pointParam
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping(value="day-group-download-excel")
	public ModelAndView dayGroupDownloadExcelProcess (PointParam pointParam) {

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new DayGroupExcelView());
		
		mav.addObject("list", pointService.getPointDayGroupListByParamForExcelDownload(pointParam));
		return mav;
	}

	
	
}