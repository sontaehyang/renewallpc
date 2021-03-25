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
            attendance.setCreatedBy("비회원");
        }

        attendance.setCreatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));

        try {
            attendanceMapper.insertAttendance(attendance);
        } catch (Exception e) {
            throw new UserException("등록처리중 오류가 발생했습니다.");
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
                attendanceConfig.setCreatedBy("비회원");
                attendanceConfig.setUpdatedBy("비회원");
            }

            attendanceConfig.setCreatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
            attendanceConfig.setUpdatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));

            try {
                attendanceMapper.insertAttendanceConfig(attendanceConfig);
            } catch (Exception e) {
                throw new UserException("등록처리중 오류가 발생했습니다.");
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
		// 달력 만들기.
		LocalDate start = ShopUtils.getLocalDate(DateUtils.getToday("yyyyMM") + "01");
		LocalDate end = start.plusMonths(1);

		List<CalendarData> calendar = new ArrayList<>();


		// 1. 이전달 일자 추가 (비어있는 날짜)
		int dayOfWeek = start.getDayOfWeek().getValue();
		if (dayOfWeek != 7) {
			Stream.iterate(start.minusDays(dayOfWeek), date -> date.plusDays(1))
					.limit(ChronoUnit.DAYS.between(start.minusDays(dayOfWeek), start))
					.map(date -> new CalendarData(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), date.getDayOfWeek().getValue(), true))
					.collect(Collectors.toCollection(() -> calendar));
		}

		// 2. 이번달 일자 추가
		Stream.iterate(start, date -> date.plusDays(1))
				.limit(ChronoUnit.DAYS.between(start, end))
				.map(date -> new CalendarData(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), date.getDayOfWeek().getValue()))
				.collect(Collectors.toCollection(() -> calendar));


		// 3. 다음달 달력 추가 (비어있는 항목)
		dayOfWeek = end.getDayOfWeek().getValue();
		int plusDays = 0;
		if (dayOfWeek != 7) {
			plusDays = 8 - dayOfWeek;


			Stream.iterate(end, date -> date.plusDays(1))
					.limit(ChronoUnit.DAYS.between(end.plusDays(1), end.plusDays(plusDays)))
					.map(date -> new CalendarData(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(), date.getDayOfWeek().getValue(), true))
					.collect(Collectors.toCollection(() -> calendar));
		}


		// 회원의 출석 체크 정보 가져오기
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
		// 1. 로그인 여부 체크.
		if (userId == null) {
			throw new UserException("로그인 후 출석 이벤트 참여가 가능합니다.");
		}

		if (date == null || date.length() != 8) {
			throw new UserException("출석 처리 중 오류 발생.");
		}

		// 2. 출석 이벤트 조회 (이번달)
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String updated = DateUtils.getToday(Const.DATETIME_FORMAT);

		AttendanceParam param = new AttendanceParam();
		param.setYear(year);
		param.setMonth(month);

		Attendance attendance = attendanceMapper.getAttendanceByParam(param);

		if (attendance == null) {
			throw new UserException("진행 중인 출석 이벤트가 없습니다. (" + year + "년 " + month + "월)");
		}

		// 3. 이벤트 참여 여부 조회 (오늘 출석 체크 여부)
		param.setCheckedDate(date);
		param.setUserId(UserUtils.getUserId());
		param.setAttendanceId(attendance.getAttendanceId());

		int checkedCount = attendanceMapper.getAttendanceCheckedCountByParam(param);

		if (checkedCount > 0) {
			throw new UserException("이미 출석 이벤트에 참여하였습니다.");
		}

		// 4. 출석 체크 처리
		AttendanceCheck attendanceCheck = new AttendanceCheck();
		attendanceCheck.setAttendanceCheckId((long) sequenceService.getId("OP_ATTENDANCE_CHECK"));
		attendanceCheck.setAttendanceId(attendance.getAttendanceId());
		attendanceCheck.setCheckedDate(date);
		attendanceCheck.setCheckedTime(DateUtils.getToday("HHmmss"));
		attendanceCheck.setUserId(UserUtils.getUserId());

		attendanceMapper.insertAttendanceCheck(attendanceCheck);


		// 5. 이벤트 연계를 위한 출석 정보 등록..
		AttendanceParam eventParam = new AttendanceParam();
		eventParam.setAttendanceId(attendance.getAttendanceId());
		eventParam.setUserId(userId);
		//eventParam.setSuccessYn("N");

		// 5-1. 이벤트 참여 목록 조회
		List<AttendanceEvent> eventAll = attendanceMapper.getAttendanceEventListByParam(eventParam);

		List<AttendanceEvent> events = new ArrayList<>();			// sussessYn == 'N'
		List<AttendanceEvent> eventList = new ArrayList<>();		// 신규 이벤트 정보 등록용..
		List<String> eventCodes = new ArrayList<>();				// 이벤트 당첨 코드 목록..
		List<String> eventDays = new ArrayList<>();					// 이벤트 당첨 기준일 (예) chuseok_5, chulseok_10, chulseok_30 형태로 사용되는 날짜)


		// 출석 이벤트에 여러번 참여가 가능한가 ?
		// - true : 연속 출석 5일 달성 후 이벤트 참여 -> 이후 다시 5일 연속 출석하면 또 다시 이벤트 참여 가능.
		// - false : 한 번 목표를 달성하면 끝..
		final boolean CAN_PARTICIPATE_MANY_TIMES = false;

		// 처음으로 이벤트 정보를 등록 하는 경우 인가?
		boolean isFirstChecked = true;

		// successYn == 'N' 인 경우만...
		if (eventAll != null && !eventAll.isEmpty()) {
			events = eventAll.stream()
					.filter(ae -> "N".equals(ae.getSuccessYn()))
					.collect(Collectors.toList());					// successYn == 'N'인 데이터만 filtering

			isFirstChecked = false;
		}


		// 5-2. 이벤트 정보가 없는 경우에는 신규 등록
		if (isFirstChecked
				|| (!isFirstChecked && events.isEmpty() && CAN_PARTICIPATE_MANY_TIMES)) {


			for (AttendanceConfig attendanceConfig : attendance.getAttendanceConfigs()) {
				AttendanceEvent event = new AttendanceEvent(attendanceConfig);
				event.setUserId(userId);
				event.setCheckedDays(1);
				event.setUpdatedDate(updated);

				// 목표 달성 설정이 1인 경우 ==> 바로 이벤트 참여기회
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

			// 신규 등록
			if (eventList != null && !eventList.isEmpty()) {
				attendanceMapper.insertAttendanceEventBy(eventList);
			}

		} else {

			// 연속 여부 확인을 위한 데이터 조회 (어제 출석 체크를 했는가?)
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


			// 5-3. 이벤트 정보가 없는 경우에는 신규 등록
			for (AttendanceEvent event : events) {
				if ("Y".equals(event.getContinueYn()) && !isCheckedContinuously) {	// 초기화
					resetIds.add(event.getAttendanceEventId());
					continue;
				}

				if (event.getDays() - event.getCheckedDays() == 1) {  // 목표 달성
					successIds.add(event.getAttendanceEventId());

					// 목표를 달성한 이벤트 코드
					eventCodes.add(event.getEventCode());
					eventDays.add("chulseok_" + event.getDays());

					if (CAN_PARTICIPATE_MANY_TIMES) {					// 목표 달성 후 여러번 참여가 가능하다면 이벤트 정보 추가 등록.
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
					updateIds.add(event.getAttendanceEventId());	// 업데이트
					continue;
				}
			}


			// 1) 목표 달성 실패 => 초기화
			if (!resetIds.isEmpty()) {
				EventData eventData = new EventData("reset", resetIds, updated);
				attendanceMapper.updateAttendanceEventByIds(eventData);
			}

			// 2) 카운트 업데이트
			if (!updateIds.isEmpty()) {
				EventData eventData = new EventData("update", updateIds, updated);
				attendanceMapper.updateAttendanceEventByIds(eventData);
			}

			// 3) 목표 달성 => 업데이트 및 신규 정보 등록
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
            attendance.setUpdatedBy("비회원");
        }

        attendance.setUpdatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));

        // attendace테이블 업데이트
        attendanceMapper.updateAttendance(attendance);

        // attendanceid로 attendanceConfig테이블 row 삭제
        attendanceMapper.deleteAllAttendanceConfigByAttendanceId(attendance);

        // attendanceConfigs의 정보로 attendanceConfig테이블 row 생성
        insertAttendanceConfig(attendance);
    }

	@Override
	public void insertAttendanceAndConfig(Attendance attendance) {
		insertAttendance(attendance);
		insertAttendanceConfig(attendance);
	}

}
