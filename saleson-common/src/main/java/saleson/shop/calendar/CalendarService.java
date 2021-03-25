package saleson.shop.calendar;

import java.util.ArrayList;

import saleson.shop.calendar.domain.Calendar;
import saleson.shop.calendar.support.CalendarSearchParam;



public interface CalendarService {
	
	/**
	 * 캘린더 휴일,영업 단축일 리스트 
	 * @param calendarSearchParam
	 * @return
	 */
	public ArrayList<Calendar> getCalendarList(CalendarSearchParam calendarSearchParam);
	
	/**
	 * 캘린더 영업, 단축일 삭제 후 등록
	 * @param calendar
	 */
	public void deleteAfterInsertCalendar(Calendar calendar);
	
	/**
	 *  캘린더 영업, 단축일 카운터
	 * @param calendarSearchParam
	 * @return
	 */
	public Calendar getCalendarById(CalendarSearchParam calendarSearchParam);
	
}
