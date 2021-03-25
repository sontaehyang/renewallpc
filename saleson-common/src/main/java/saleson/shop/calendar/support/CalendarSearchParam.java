package saleson.shop.calendar.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class CalendarSearchParam extends SearchParam {
	
	private String calendarYear;
	private String calendarMonth;
	private String calendarDay;
	private String date;
	
	public String getCalendarYear() {
		return calendarYear;
	}
	public void setCalendarYear(String calendarYear) {
		this.calendarYear = calendarYear;
	}
	public String getCalendarMonth() {
		return calendarMonth;
	}
	public void setCalendarMonth(String calendarMonth) {
		this.calendarMonth = calendarMonth;
	}
	public String getCalendarDay() {
		return calendarDay;
	}
	public void setCalendarDay(String calendarDay) {
		this.calendarDay = calendarDay;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
