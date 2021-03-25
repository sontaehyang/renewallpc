package saleson.shop.calendar;

import java.util.ArrayList;

import saleson.shop.calendar.domain.Calendar;
import saleson.shop.calendar.support.CalendarSearchParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("calendarMapper")
public interface CalendarMapper {
	
	/**
	 * 캘린더 휴일,영업 단축일 리스트 
	 * @param calendarSearchParam
	 * @return
	 */
	ArrayList<Calendar> getCalendarList(CalendarSearchParam calendarSearchParam);
	
	/**
	 * 캘린더 영업, 단축일 등록
	 * @param calendar
	 */
	void insertCalendar(Calendar calendar);
	
	/**
	 * 캘린더 영업, 단축일 수정
	 * @param calendar
	 */
	void deleteCalendar(Calendar calendar);
	
	
	/**
	 * 캘린더 영업, 단축일 카운터
	 * @param calendarSearchParam
	 * @return
	 */
	Calendar getCalendarById(CalendarSearchParam calendarSearchParam);
	
}
