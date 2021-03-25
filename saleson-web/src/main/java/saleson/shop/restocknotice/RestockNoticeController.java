package saleson.shop.restocknotice;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleson.common.utils.UserUtils;
import saleson.shop.restocknotice.domain.RestockNotice;
import saleson.shop.user.UserService;

@Controller
@RequestMapping("/restock-notice")
@RequestProperty(title="재입고알림", layout="default")
public class RestockNoticeController {

    @Autowired
    private RestockNoticeService restockNoticeService;

    @Autowired
    private UserService userService;

    /**
     * 재입고알림 추가
     * @param requestContext
     * @param restockNotice
     * @return
     */
    @PostMapping("add")
    public JsonView add(RequestContext requestContext, RestockNotice restockNotice) {
        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        if (!UserUtils.isUserLogin()) {
            throw new UserException(MessageUtils.getMessage("M00540"));	// 로그인하십시오.
        }

        try {
            User user = UserUtils.getUser();

            // 세션에 저장된 회원 핸드폰번호 정보가 없을 경우
            if ("--".equals(user.getPhoneNumber()) || StringUtils.isEmpty(user.getPhoneNumber())) {
                // 로그인중에 핸드폰번호를 변경했는지 2차 검사 (회원정보 수정후 재 로그인을 하지 않으면 세션 갱신이 안됨)
                user = userService.getUserByUserId(user.getUserId());
                if ("--".equals(user.getPhoneNumber()) || StringUtils.isEmpty(user.getPhoneNumber())) {
                    return JsonViewUtils.failure("핸드폰 번호가 설정되어 있지 않아 알림 전송이 불가능합니다.\n핸드폰 번호를 설정해주세요.");
                }
            }

            restockNotice.setUserId(user.getUserId());
            restockNotice.setSendFlag("N");

            // 중복 체크
            boolean check = restockNoticeService.isRestockNotice(restockNotice);

            if (!check) {
                restockNoticeService.insertRestockNotice(restockNotice);
            } else {
                return JsonViewUtils.failure("이미 신청한 상태입니다.");
            }
        } catch(Exception e) {
            return JsonViewUtils.failure(e.getMessage());
        }

        return JsonViewUtils.success();
    }
}
