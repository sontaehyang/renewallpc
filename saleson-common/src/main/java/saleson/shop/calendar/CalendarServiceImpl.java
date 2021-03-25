package saleson.shop.calendar;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.sequence.service.SequenceService;

import saleson.shop.calendar.domain.Calendar;
import saleson.shop.calendar.support.CalendarSearchParam;

@Service("calendarService")
public class CalendarServiceImpl implements CalendarService {

	@Autowired
	CalendarMapper calendarMapper;
	
	@Autowired
	SequenceService sequenceService;
	

	@Override
	public ArrayList<Calendar> getCalendarList(
			CalendarSearchParam calendarSearchParam) {
		return calendarMapper.getCalendarList(calendarSearchParam);
	}

	@Override
	public void deleteAfterInsertCalendar(Calendar calendar) {
		calendarMapper.deleteCalendar(calendar);
		calendar.setCalendarId(sequenceService.getId("OP_CALENDAR"));
		calendarMapper.insertCalendar(calendar);
	}

	@Override
	public Calendar getCalendarById(CalendarSearchParam calendarSearchParam) {
		return calendarMapper.getCalendarById(calendarSearchParam);
	}
	
	
	
}
