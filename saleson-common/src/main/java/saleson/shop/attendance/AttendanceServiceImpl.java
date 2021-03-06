package saleson.shop.attendance;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.attendance.domain.*;
import saleson.shop.attendance.support.AttendanceParam;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("attendanceService")
public class AttendanceServiceImpl implements AttendanceService {
	private static final Logger log = LoggerFactory.getLogger(AttendanceServiceImpl.class);
	
	@Autowired
	private AttendanceMapper attendanceMapper;

	@Autowired
	SequenceService sequenceService;

    @Override
    public int getAttendanceCountByParam(AttendanceParam attendanceParam) {
        return attendanceMapper.getAttendanceCountByParam(attendanceParam);
    }

    @Override
    public List<Attendance> getAttendanceListByParam(AttendanceParam attendanceParam) {
        return attendanceMapper.getAttendanceListByParam(attendanceParam);
    }

    @Override
    public void insertAttendance(Attendance attendance) {

    	long attendanceId = (long) sequenceService.getId("OP_ATTENDANCE");

		attendance.setAttendanceId(attendanceId);


        if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
            attendance.setCreatedBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
        } else {
            attendance.setCreatedBy("λΉνμ");
        }

        attendance.setCreatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));

        try {
            attendanceMapper.insertAttendance(attendance);
        } catch (Exception e) {
            throw new UserException("λ±λ‘μ²λ¦¬μ€ μ€λ₯κ° λ°μνμ΅λλ€.");
        }
    }

    @Override
    public void insertAttendanceConfig(Attendance attendance) {

        for (int i=0; i < attendance.getEventCode().length; i++) {
            AttendanceConfig attendanceConfig = new AttendanceConfig();
            attendanceConfig.setAttendanceConfigId((long) sequenceService.getId("OP_ATTENDANCE_CONFIG"));
            attendanceConfig.setAttendanceId(attendance.getAttendanceId());
            attendanceConfig.setEventCode(attendance.getEventCode()[i]);
            attendanceConfig.setDays(attendance.getDays()[i]);
            attendanceConfig.setContinueYn(attendance.getContinueYn()[i]);

            if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
                attendanceConfig.setCreatedBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
                attendanceConfig.setUpdatedBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
            } else {
                attendanceConfig.setCreatedBy("λΉνμ");
                attendanceConfig.setUpdatedBy("λΉνμ");
            }

            attendanceConfig.setCreatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
            attendanceConfig.setUpdatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));

            try {
                attendanceMapper.insertAttendanceConfig(attendanceConfig);
            } catch (Exception e) {
                throw new UserException("λ±λ‘μ²λ¦¬μ€ μ€λ₯κ° λ°μνμ΅λλ€.");
            }
        }
    }

    @Override
    public int getAttendanceCountForDuplication(Attendance attendance) {
        return attendanceMapper.getAttendanceCountForDuplication(attendance);
    }

    @Override
    public void deleteAttendanceById(AttendanceParam attendanceParam) {
        attendanceMapper.deleteAttendanceById(attendanceParam);
    }


	@Override
	public Attendance getAttendanceByParam(AttendanceParam param) {
		return attendanceMapper.getAttendanceByParam(param);
	}


	@Override
	public List<AttendanceCheck> getAttendanceCheckedListByParam(AttendanceParam param) {
		return attendanceMapper.getAttendanceCheckedListByParam(param);
	}


	@Override
	public List<CalendarData> getAttendanceCalendar() {
		// λ¬λ ₯ λ§λ€κΈ°.
		LocalDate start = ShopUtils.getLocalDate(DateUtils.getToday("yyyyMM") + "01");
		LocalDate end = start.plusMonths(1);

		List<CalendarData> calendar = new ArrayList<>();


		// 1. μ΄μ λ¬ μΌμ μΆκ° (λΉμ΄μλ λ μ§)
		int dayOfWeek = start.getDayOfWeek().getValue();
		if (dayOfWeek != 7) {
			Stream.iterate(start.minusDays(dayOfWeek), date -> date.plusDays(1))
					.limit(ChronoUnit.DAYS.between(start.minusDays(dayOfWeek), start))
					.map(date -> new CalendarData(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), date.getDayOfWeek().getValue(), true))
					.collect(Collectors.toCollection(() -> calendar));
		}

		// 2. μ΄λ²λ¬ μΌμ μΆκ°
		Stream.iterate(start, date -> date.plusDays(1))
				.limit(ChronoUnit.DAYS.between(start, end))
				.map(date -> new CalendarData(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), date.getDayOfWeek().getValue()))
				.collect(Collectors.toCollection(() -> calendar));


		// 3. λ€μλ¬ λ¬λ ₯ μΆκ° (λΉμ΄μλ ν­λͺ©)
		dayOfWeek = end.getDayOfWeek().getValue();
		int plusDays = 0;
		if (dayOfWeek != 7) {
			plusDays = 8 - dayOfWeek;


			Stream.iterate(end, date -> date.plusDays(1))
					.limit(ChronoUnit.DAYS.between(end.plusDays(1), end.plusDays(plusDays)))
					.map(date -> new CalendarData(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), date.getDayOfWeek().getValue(), true))
					.collect(Collectors.toCollection(() -> calendar));
		}


		// νμμ μΆμ μ²΄ν¬ μ λ³΄ κ°μ Έμ€κΈ°
		if (UserUtils.isUserLogin()) {
			String year = DateUtils.getToday("yyyy");
			String month = DateUtils.getToday("MM");

			AttendanceParam param = new AttendanceParam();
			param.setYearMonth(year + month);
			param.setUserId(UserUtils.getUserId());

			List<AttendanceCheck> checkedList = attendanceMapper.getAttendanceCheckedListByParam(param);

			if (checkedList != null && !checkedList.isEmpty()) {
				for (CalendarData calendarData : calendar) {
					for (AttendanceCheck attendanceCheck : checkedList) {
						if (attendanceCheck.getCheckedDate().equals(calendarData.getDate())) {
							calendarData.setChecked(true);
							calendarData.setCheckedDate(attendanceCheck.getCheckedDate() + attendanceCheck.getCheckedTime());
							break;
						}
					}
				}
			}
		}

		for (CalendarData calendarData : calendar) {
			System.out.println(calendarData);
		}

		return calendar;
	}


	@Override
	public Map<String, List<String>> checkAttendance(String date, Long userId) {
		// 1. λ‘κ·ΈμΈ μ¬λΆ μ²΄ν¬.
		if (userId == null) {
			throw new UserException("λ‘κ·ΈμΈ ν μΆμ μ΄λ²€νΈ μ°Έμ¬κ° κ°λ₯ν©λλ€.");
		}

		if (date == null || date.length() != 8) {
			throw new UserException("μΆμ μ²λ¦¬ μ€ μ€λ₯ λ°μ.");
		}

		// 2. μΆμ μ΄λ²€νΈ μ‘°ν (μ΄λ²λ¬)
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String updated = DateUtils.getToday(Const.DATETIME_FORMAT);

		AttendanceParam param = new AttendanceParam();
		param.setYear(year);
		param.setMonth(month);

		Attendance attendance = attendanceMapper.getAttendanceByParam(param);

		if (attendance == null) {
			throw new UserException("μ§ν μ€μΈ μΆμ μ΄λ²€νΈκ° μμ΅λλ€. (" + year + "λ " + month + "μ)");
		}

		// 3. μ΄λ²€νΈ μ°Έμ¬ μ¬λΆ μ‘°ν (μ€λ μΆμ μ²΄ν¬ μ¬λΆ)
		param.setCheckedDate(date);
		param.setUserId(UserUtils.getUserId());
		param.setAttendanceId(attendance.getAttendanceId());

		int checkedCount = attendanceMapper.getAttendanceCheckedCountByParam(param);

		if (checkedCount > 0) {
			throw new UserException("μ΄λ―Έ μΆμ μ΄λ²€νΈμ μ°Έμ¬νμμ΅λλ€.");
		}

		// 4. μΆμ μ²΄ν¬ μ²λ¦¬
		AttendanceCheck attendanceCheck = new AttendanceCheck();
		attendanceCheck.setAttendanceCheckId((long) sequenceService.getId("OP_ATTENDANCE_CHECK"));
		attendanceCheck.setAttendanceId(attendance.getAttendanceId());
		attendanceCheck.setCheckedDate(date);
		attendanceCheck.setCheckedTime(DateUtils.getToday("HHmmss"));
		attendanceCheck.setUserId(UserUtils.getUserId());

		attendanceMapper.insertAttendanceCheck(attendanceCheck);


		// 5. μ΄λ²€νΈ μ°κ³λ₯Ό μν μΆμ μ λ³΄ λ±λ‘..
		AttendanceParam eventParam = new AttendanceParam();
		eventParam.setAttendanceId(attendance.getAttendanceId());
		eventParam.setUserId(userId);
		//eventParam.setSuccessYn("N");

		// 5-1. μ΄λ²€νΈ μ°Έμ¬ λͺ©λ‘ μ‘°ν
		List<AttendanceEvent> eventAll = attendanceMapper.getAttendanceEventListByParam(eventParam);

		List<AttendanceEvent> events = new ArrayList<>();			// sussessYn == 'N'
		List<AttendanceEvent> eventList = new ArrayList<>();		// μ κ· μ΄λ²€νΈ μ λ³΄ λ±λ‘μ©..
		List<String> eventCodes = new ArrayList<>();				// μ΄λ²€νΈ λΉμ²¨ μ½λ λͺ©λ‘..
		List<String> eventDays = new ArrayList<>();					// μ΄λ²€νΈ λΉμ²¨ κΈ°μ€μΌ (μ) chuseok_5, chulseok_10, chulseok_30 ννλ‘ μ¬μ©λλ λ μ§)


		// μΆμ μ΄λ²€νΈμ μ¬λ¬λ² μ°Έμ¬κ° κ°λ₯νκ° ?
		// - true : μ°μ μΆμ 5μΌ λ¬μ± ν μ΄λ²€νΈ μ°Έμ¬ -> μ΄ν λ€μ 5μΌ μ°μ μΆμνλ©΄ λ λ€μ μ΄λ²€νΈ μ°Έμ¬ κ°λ₯.
		// - false : ν λ² λͺ©νλ₯Ό λ¬μ±νλ©΄ λ..
		final boolean CAN_PARTICIPATE_MANY_TIMES = false;

		// μ²μμΌλ‘ μ΄λ²€νΈ μ λ³΄λ₯Ό λ±λ‘ νλ κ²½μ° μΈκ°?
		boolean isFirstChecked = true;

		// successYn == 'N' μΈ κ²½μ°λ§...
		if (eventAll != null && !eventAll.isEmpty()) {
			events = eventAll.stream()
					.filter(ae -> "N".equals(ae.getSuccessYn()))
					.collect(Collectors.toList());					// successYn == 'N'μΈ λ°μ΄ν°λ§ filtering

			isFirstChecked = false;
		}


		// 5-2. μ΄λ²€νΈ μ λ³΄κ° μλ κ²½μ°μλ μ κ· λ±λ‘
		if (isFirstChecked
				|| (!isFirstChecked && events.isEmpty() && CAN_PARTICIPATE_MANY_TIMES)) {


			for (AttendanceConfig attendanceConfig : attendance.getAttendanceConfigs()) {
				AttendanceEvent event = new AttendanceEvent(attendanceConfig);
				event.setUserId(userId);
				event.setCheckedDays(1);
				event.setUpdatedDate(updated);

				// λͺ©ν λ¬μ± μ€μ μ΄ 1μΈ κ²½μ° ==> λ°λ‘ μ΄λ²€νΈ μ°Έμ¬κΈ°ν
				boolean isSuccess = false;
				if (attendanceConfig.getDays() == 1) {
					event.setUpdatedDate(updated);
					event.setSuccessYn("Y");

					isSuccess = true;
					eventCodes.add(attendanceConfig.getEventCode());
					eventDays.add("chulseok_" + attendanceConfig.getDays());
				}
				event.setAttendanceEventId((long) sequenceService.getId("OP_ATTENDANCE_EVENT"));
				eventList.add(event);


				if (isSuccess && CAN_PARTICIPATE_MANY_TIMES) {
					AttendanceEvent event2 = new AttendanceEvent(attendanceConfig);
					event2.setUserId(userId);
					event2.setUpdatedDate(updated);

					eventList.add(event2);
				}
			}

			// μ κ· λ±λ‘
			if (eventList != null && !eventList.isEmpty()) {
				attendanceMapper.insertAttendanceEventBy(eventList);
			}

		} else {

			// μ°μ μ¬λΆ νμΈμ μν λ°μ΄ν° μ‘°ν (μ΄μ  μΆμ μ²΄ν¬λ₯Ό νλκ°?)
			LocalDate localDate = ShopUtils.getLocalDate(date).minusDays(1);
			String yesterday = ShopUtils.localDateToString(localDate);

			AttendanceParam checkParam = new AttendanceParam();
			checkParam.setAttendanceId(attendance.getAttendanceId());
			checkParam.setUserId(userId);
			checkParam.setCheckedDate(yesterday);

			int yesterdayCheckedCount = attendanceMapper.getAttendanceCheckedCountByParam(checkParam);
			boolean isCheckedContinuously = yesterdayCheckedCount == 0 ? false : true;

			List<Long> resetIds = new ArrayList<>();
			List<Long> updateIds = new ArrayList<>();
			List<Long> successIds = new ArrayList<>();


			// 5-3. μ΄λ²€νΈ μ λ³΄κ° μλ κ²½μ°μλ μ κ· λ±λ‘
			for (AttendanceEvent event : events) {
				if ("Y".equals(event.getContinueYn()) && !isCheckedContinuously) {	// μ΄κΈ°ν
					resetIds.add(event.getAttendanceEventId());
					continue;
				}

				if (event.getDays() - event.getCheckedDays() == 1) {  // λͺ©ν λ¬μ±
					successIds.add(event.getAttendanceEventId());

					// λͺ©νλ₯Ό λ¬μ±ν μ΄λ²€νΈ μ½λ
					eventCodes.add(event.getEventCode());
					eventDays.add("chulseok_" + event.getDays());

					if (CAN_PARTICIPATE_MANY_TIMES) {					// λͺ©ν λ¬μ± ν μ¬λ¬λ² μ°Έμ¬κ° κ°λ₯νλ€λ©΄ μ΄λ²€νΈ μ λ³΄ μΆκ° λ±λ‘.
						AttendanceEvent newEvent = new AttendanceEvent();
						newEvent.setAttendanceEventId((long) sequenceService.getId("OP_ATTENDANCE_EVENT"));
						newEvent.setAttendanceId(event.getAttendanceId());
						newEvent.setUserId(userId);
						newEvent.setEventCode(event.getEventCode());
						newEvent.setContinueYn(event.getContinueYn());
						newEvent.setDays(event.getDays());
						newEvent.setCheckedDays(0);
						newEvent.setSuccessYn("N");
						newEvent.setUpdatedDate(updated);

						eventList.add(newEvent);
					}

					continue;

				} else {
					updateIds.add(event.getAttendanceEventId());	// μλ°μ΄νΈ
					continue;
				}
			}


			// 1) λͺ©ν λ¬μ± μ€ν¨ => μ΄κΈ°ν
			if (!resetIds.isEmpty()) {
				EventData eventData = new EventData("reset", resetIds, updated);
				attendanceMapper.updateAttendanceEventByIds(eventData);
			}

			// 2) μΉ΄μ΄νΈ μλ°μ΄νΈ
			if (!updateIds.isEmpty()) {
				EventData eventData = new EventData("update", updateIds, updated);
				attendanceMapper.updateAttendanceEventByIds(eventData);
			}

			// 3) λͺ©ν λ¬μ± => μλ°μ΄νΈ λ° μ κ· μ λ³΄ λ±λ‘
			if (!successIds.isEmpty()) {
				EventData eventData = new EventData("success", successIds, updated);
				attendanceMapper.updateAttendanceEventByIds(eventData);
			}
			if (eventList != null && !eventList.isEmpty()) {
				attendanceMapper.insertAttendanceEventBy(eventList);
			}

		}

		Map<String, List<String>> result = new HashMap<>();
		result.put("eventCodes", eventCodes);
		result.put("eventDays", eventDays);
		return result;
	}

    @Override
    public void updateAttendance(Attendance attendance) {

        if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
            attendance.setUpdatedBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
        } else {
            attendance.setUpdatedBy("λΉνμ");
        }

        attendance.setUpdatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));

        // attendaceνμ΄λΈ μλ°μ΄νΈ
        attendanceMapper.updateAttendance(attendance);

        // attendanceidλ‘ attendanceConfigνμ΄λΈ row μ­μ 
        attendanceMapper.deleteAllAttendanceConfigByAttendanceId(attendance);

        // attendanceConfigsμ μ λ³΄λ‘ attendanceConfigνμ΄λΈ row μμ±
        insertAttendanceConfig(attendance);
    }

	@Override
	public void insertAttendanceAndConfig(Attendance attendance) {
		insertAttendance(attendance);
		insertAttendanceConfig(attendance);
	}

}
