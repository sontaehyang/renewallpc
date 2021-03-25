package saleson.shop.attendance;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.attendance.domain.Attendance;
import saleson.shop.attendance.domain.AttendanceCheck;
import saleson.shop.attendance.support.AttendanceParam;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/m/attendance")
@RequestProperty(title="출석체크", template="mobile", layout="default")
public class AttendanceMobileController {
	private static final Logger log = LoggerFactory.getLogger(AttendanceMobileController.class);
	
	@Autowired
	private AttendanceService attendanceService;

	/**
	 * 이번 달 출석 체크 화면
	 * @param param
	 * @param model
	 * @return
	 */
	@GetMapping
	public String index(AttendanceParam param, Model model) {
		String year = DateUtils.getToday("yyyy");
		String month = DateUtils.getToday("MM");
		param.setYear(year);
		param.setMonth(month);

		Attendance attendance = attendanceService.getAttendanceByParam(param);

		if (attendance == null) {
			throw new UserException("진행 중인 출석 이벤트가 없습니다. (" + year + "년 " + month + "월)");
		}

		int checkedCount = 0;
		int continuouslyCheckedCount = 0;

		if (UserUtils.isUserLogin()) {
			AttendanceParam param2 = new AttendanceParam();
			param2.setYearMonth(year + month);
			param2.setUserId(UserUtils.getUserId());

			List<AttendanceCheck> checkedList = attendanceService.getAttendanceCheckedListByParam(param2);

			if (checkedList != null) {
				checkedCount = checkedList.size();

				LocalDate prevDate = null;
				for (AttendanceCheck attendanceCheck : checkedList) {
					LocalDate date = ShopUtils.getLocalDate(attendanceCheck.getCheckedDate());

					if (prevDate == null || date.equals(prevDate.minusDays(1))) {
						continuouslyCheckedCount++;
					} else {
						break;
					}
					prevDate = date;
				}
			}
		}

		model.addAttribute("attendance", attendance);
		model.addAttribute("checkedCount", checkedCount);
		model.addAttribute("continuouslyCheckedCount", continuouslyCheckedCount);
		model.addAttribute("calendar", attendanceService.getAttendanceCalendar());
		return "view:/attendance/index";
	}


	/**
	 * 출석 체크 처리
	 *
	 * 출석 체크 후 이벤트를 달성한 경우 eventCodes를 리턴함.
	 * @return
	 */
	@PostMapping("/check")
	public JsonView check() {

		// 1. 로그인 여부 체크.
		if (!UserUtils.isUserLogin()) {
			return JsonViewUtils.failure("로그인 후 출석 이벤트 참여가 가능합니다.");
		}

		try {
			String today = DateUtils.getToday(Const.DATE_FORMAT);
			Long userId = UserUtils.getUserId();
			Map<String, List<String>> eventCodes = attendanceService.checkAttendance(today, userId);

			return JsonViewUtils.success(eventCodes);

		} catch (Exception e) {
			return JsonViewUtils.failure(e.getMessage());

		}
	}



	@GetMapping(value = "/test")
	public JsonView test(@RequestParam(value = "days", required = false, defaultValue = "1") int days) {

		// 1. 로그인 여부 체크.
		if (!UserUtils.isUserLogin()) {
			return JsonViewUtils.failure("로그인 후 출석 이벤트 참여가 가능합니다.");
		}
		Map<String, Map<String, List<String>>> result = new HashMap<>();
		try {
			String yearMonth = DateUtils.getToday("yyyyMM");
			Long userId = UserUtils.getUserId();

			for (int i = 1; i <= 30; i++) {
				if (i % days == 0 && days > 1) {
					continue;
				}
				String today = yearMonth + "" + (i < 10 ? "0" + i : i);

				Map<String, List<String>> eventCodes = attendanceService.checkAttendance(today, userId);

				result.put(today, eventCodes);

			}

			return JsonViewUtils.success(result);

		} catch (Exception e) {
			return JsonViewUtils.failure(e.getMessage());

		}
	}
}
