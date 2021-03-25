package saleson.shop.attendance;

import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * AttendanceMobileController 를 상속 받아 처리함.
 */
@Controller
@RequestMapping("/attendance")
@RequestProperty(title="출석체크", template="front", layout="default")
public class AttendanceController extends AttendanceMobileController {
	private static final Logger log = LoggerFactory.getLogger(AttendanceController.class);
}
