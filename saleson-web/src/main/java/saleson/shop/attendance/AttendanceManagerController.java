package saleson.shop.attendance;

import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import saleson.shop.attendance.domain.Attendance;
import saleson.shop.attendance.support.AttendanceParam;

import java.util.List;

@Controller
@RequestMapping({"/opmanager/attendance"})
@RequestProperty(title="출석체크 관리", layout="default")
public class AttendanceManagerController {
	
    @Autowired
    AttendanceService attendanceService;

	/**
	 * 관리자 출섹체크 설정 목록
	 * @param
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String list(@RequestParam(value="itemsPerPage", required=false) String itemsPerPage, AttendanceParam param, Model model) {

        if (itemsPerPage == null) {
			param.setItemsPerPage(20);
		}
		
		int count = attendanceService.getAttendanceCountByParam(param);

		Pagination pagination = Pagination.getInstance(count, param.getItemsPerPage());
        param.setPagination(pagination);

        List<Attendance> attendanceList = attendanceService.getAttendanceListByParam(param);

        String lastYear = DateUtils.getToday("yyyy");

        model.addAttribute("attendanceCount",count);
		model.addAttribute("attendanceList",attendanceList);
        model.addAttribute("lastYear", lastYear);
		model.addAttribute("attendanceParam",param);
		model.addAttribute("attendanceCount",count);
		model.addAttribute("pagination",pagination);
		
		return ViewUtils.getView("/attendance/list");
	}

	/**
	 * 관리자 출석체크 마스터 등록
	 * @param
	 * @param model
	 * @return
	 */
	@GetMapping(value="/create")
	public String create(AttendanceParam param, Attendance attendance, Model model) {

        String lastYear = DateUtils.getToday("yyyy");
        String currentMonth = DateUtils.getToday("MM");

        model.addAttribute("lastYear", lastYear);
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("mode", "create");
        model.addAttribute("attendance", attendance);
        model.addAttribute("attendanceParam",param);

		return ViewUtils.getView("/attendance/form");
	}

	@PostMapping("/create")
	public String createAction(Model model, Attendance attendance) {

	    int duplicationCount = attendanceService.getAttendanceCountForDuplication(attendance);

	    if (duplicationCount > 0) {
	        throw new UserException("오류 : " + attendance.getYear() + "년 " + attendance.getMonth() + "월에 등록된 출석체크 설정이 존재합니다.");
        }

		// 트랜잭션 단위로 묶기 위함
        /*attendanceService.insertAttendance(attendance);
        attendanceService.insertAttendanceConfig(attendance);*/
		attendanceService.insertAttendanceAndConfig(attendance);

		return ViewUtils.redirect("/opmanager/attendance/list", MessageUtils.getMessage("M00288")); 	// 등록 되었습니다.

	}

	/**
	 * 관리자 출석체크 설정 선택 삭제
	 * @param attendanceParam
	 * @param model
	 * @return
	 */
	@PostMapping("/checked-delete")
	public String checkedDelete(Model model, AttendanceParam attendanceParam) {

		attendanceService.deleteAttendanceById(attendanceParam);

		return ViewUtils.redirect("/opmanager/attendance/list", MessageUtils.getMessage("M00205")); // 삭제 되었습니다.

	}

    /**
     * 관리자 출석체크 마스터 수정
     * @param
     * @param model
     * @return
     */
    @GetMapping(value="/edit")
    public String edit(AttendanceParam param, Model model) {

        String lastYear = DateUtils.getToday("yyyy");

		Attendance attendance = attendanceService.getAttendanceByParam(param);

		if (attendance == null) {
			throw new PageNotFoundException(param.toString());
		}

        model.addAttribute("lastYear", lastYear);
        model.addAttribute("mode", "edit");
        model.addAttribute("attendance", attendance);

        return ViewUtils.getView("/attendance/form");
    }

    @PostMapping("/edit")
    public String editAction(Model model, Attendance attendance) {
        attendanceService.updateAttendance(attendance);

        return ViewUtils.redirect("/opmanager/attendance/list", MessageUtils.getMessage("M00289")); 	// 수정 되었습니다.

    }
}
