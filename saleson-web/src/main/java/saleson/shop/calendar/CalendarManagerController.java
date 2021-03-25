package saleson.shop.calendar;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import saleson.shop.calendar.domain.Calendar;
import saleson.shop.calendar.support.CalendarSearchParam;

import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;

@Controller
@RequestMapping("/opmanager/calendar")
@RequestProperty(title="캘린더 관리", layout="default")
public class CalendarManagerController {
	
	@Autowired
	private CalendarService calendarService;
	
	
	/**
	 * 관리자 운영설정 캘린더 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String list(@ModelAttribute("searchParam") CalendarSearchParam searchParam, Model model) {
		
		java.util.Calendar cal = java.util.Calendar.getInstance();
		 
		 String strYear = searchParam.getCalendarYear() == null ? "" : searchParam.getCalendarYear();
		 String strMonth = searchParam.getCalendarMonth() == null ? "" : searchParam.getCalendarMonth();
		 
		 int year = cal.get(java.util.Calendar.YEAR);
		 int month = cal.get(java.util.Calendar.MONTH);
		 int date = cal.get(java.util.Calendar.DATE);

		 if (!"".equals(strYear) && !"".equals(strMonth)) {
			 year = Integer.parseInt(strYear);
			 month = Integer.parseInt(strMonth);
		 }

		 if(month == 12){
			  year = year +1 ;
		 }
		 if(month == -1){
			  year = year - 1 ;
		 }
		  
		 if(month > 11){
			  month = 0;
		 }
		  
		 if(month < 0){
			  month = 11;
		 }
		 
		 cal.set(year, month, 1);
		 
		 int startDay = cal.getMinimum(java.util.Calendar.DATE);
		 int endDay = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		 int start = cal.get(java.util.Calendar.DAY_OF_WEEK);
		 int newLine = 0;
		 
		 searchParam.setCalendarYear(Integer.toString(year));
		 searchParam.setCalendarMonth(Integer.toString(month+1).length() == 1 ? "0"+Integer.toString(month+1) : Integer.toString(month+1));
		
		 ArrayList<Calendar> calendarList = calendarService.getCalendarList(searchParam);
		 HashMap<String, String> dayList = new HashMap<>();
		 for (Calendar calendar : calendarList) {
			 int day = Integer.parseInt(calendar.getCalendarDay());
			 dayList.put(""+day, calendar.getHday());
		 }
		
		 model.addAttribute("year",year);
		 model.addAttribute("month",month);
		 model.addAttribute("date",date);
		 model.addAttribute("strYear",strYear);
		 model.addAttribute("strMonth",strMonth);
		 model.addAttribute("startDay",startDay);
		 model.addAttribute("endDay",endDay);
		 model.addAttribute("start",start);
		 model.addAttribute("newLine",newLine);
		
		 model.addAttribute("calendarList",dayList);
		
		return ViewUtils.view();
		
		
	}
	
	/**
	 * 관리자 운영설정 캘린더 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/popup/{date}")
	@RequestProperty(title="캘린더 팝업", layout="base")
	public String popup(@ModelAttribute("searchParam") CalendarSearchParam searchParam, Model model) {
		
		if (ValidationUtils.isNull(searchParam.getDate())) {
			FlashMapUtils.alert(MessageUtils.getMessage("validator.NotEmpty"));
			return ViewUtils.redirect("/");
		}


		if (searchParam.getDate() != null) {
			String[] date = searchParam.getDate().split("-");

			searchParam.setCalendarYear(date[0]);
			searchParam.setCalendarMonth(date[1].length() == 1 ? "0" + date[1] : date[1]);
			searchParam.setCalendarDay(date[2].length() == 1 ? "0" + date[2] : date[2]);
		}
		
		Calendar calendar =  calendarService.getCalendarById(searchParam);
		
		model.addAttribute("calendar",calendar);
		model.addAttribute("calendarSearchParam",searchParam);
		
		return ViewUtils.getManagerView("/calendar/popup");
		
	}
	
	
	/**
	 * 관리자 운영설정 캘린더 관리 리스트
	 * @param searchParam
	 * @param model
	 * @return
	 */
	@GetMapping("/create")
	@RequestProperty(title="캘린더 팝업", layout="base")
	public String create(Calendar calendar, Model model, CalendarSearchParam searchParam) {
		
		if (ValidationUtils.isNull(calendar)) {
			FlashMapUtils.alert(MessageUtils.getMessage("validator.NotEmpty"));
			return ViewUtils.redirect("/opmanager/calendar/list");
		}
		
		calendarService.deleteAfterInsertCalendar(calendar);
		
		
		return ViewUtils.redirect("/opmanager/calendar/popup/"+calendar.getCalendarYear()+"-"+calendar.getCalendarMonth()+"-"+calendar.getCalendarDay(),"등록되었습니다.","self.close(); opener.location.reload();");
		
	}
	
	
}
