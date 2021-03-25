package saleson.shop.usersns;

import com.onlinepowers.framework.annotation.handler.Authorize;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.CommonUtils;
import com.onlinepowers.framework.util.ShadowUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.enumeration.eventcode.EventCodeLogType;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.common.utils.UserUtils;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.eventcode.EventCodeService;
import saleson.shop.user.UserService;
import saleson.shop.usersns.domain.UserSns;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author	seungil.lee
 * @since	2017-05-24
 */

@Controller
@RequestMapping({"/sns-user", "/m/sns-user"})
@RequestProperty(title="특집페이지", layout="default")
public class UserSnsController {
    private static final Logger log = LoggerFactory.getLogger(UserSnsController.class);

    @Autowired
    private UserSnsService userSnsService;

    @Autowired
    private UserService userService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private EventCodeService eventCodeService;

    @GetMapping(value="/naver-callback")
    @RequestProperty(layout="blank")
    public String naverCallback(HttpServletRequest request) {
        return ViewUtils.getView("/sns-user/naver-callback");
    }

	@PostMapping(value="/naver-callback")
	@RequestProperty(layout="blank")
	public String naverCallback2(HttpServletRequest request) {
		return ViewUtils.getView("/sns-user/naver-callback");
	}


    /**
     * SNS 계정 조회 및 가입
     * status { 00: 정상, 01: 해당 이메일로 아이디 존재 }
     * @param userSnsData
     * @return
     */
	@PostMapping(value="/search")
    @ResponseBody
    public Map<String, String> userSearch(HttpServletRequest request, UserSns userSnsData) {
        UserSns userSns = new UserSns();
        Map <String, String> map = new HashMap<>();
        try {
            map = userSnsService.joinProcess(userSns, map, userSnsData);
            UserCouponParam userCouponParam = new UserCouponParam();

            if ("00".equals(map.get("value"))) {
                //신규회원가입 쿠폰 발급[2017-09-08]minae.yun
                userCouponParam.setCouponTargetTimeType("2");
                List<Coupon> newUserCouponList = couponService.getCouponByTargetTimeType(userCouponParam);
                long userId = Long.parseLong(map.get("userId"));

                if (newUserCouponList != null && newUserCouponList.size() != 0) {
                    for (Coupon coupon : newUserCouponList) {
                        int count = 0;
                        userCouponParam.setCouponId(coupon.getCouponId());
                        userCouponParam.setUserId(userId);
                        count = couponService.getUserCouponListForNewUserCoupon(userCouponParam);
                        if (count == 0) {
                            couponService.insertCouponTargetUserOne(userCouponParam);
                            couponService.userCouponDownload(userCouponParam);
                        }
                    }
                }

                eventCodeService.insertLog(request, userId, EventCodeType.NONE, EventCodeLogType.USER);
            }

            if (!userSnsData.getIsMypage()) {
                String loginId = map.get("loginId");
                map.put("shadowUserId", loginId);
                map.put("shadowLoginKey", ShadowUtils.getShadowLoginKey(loginId, ""));
                map.put("shadowLoginPassword", ShadowUtils.getShadowLoginPassword(loginId));
                map.put("shadowLoginSignature", ShadowUtils.getShadowLoginSignature(loginId));
            }
            map.put("status", "00");
        } catch (Exception e) {
            map.put("status", "01");
            map.put("message", e.getMessage());
        }

        return map;
    }

    // mypage SNS 설정
    @GetMapping("/setup-sns")
    @RequestProperty(layout="mypage")
    @Authorize("hasRole('ROLE_USER')")
    public String setupSnsForm(UserSns userSnsData, Model model, HttpServletRequest request) {
        if (CommonUtils.isMobile(request)) {
            RequestContextUtils.setTemplate("mobile");
            RequestContextUtils.setLayout("default");
        }
        userSnsData.setUserId(UserUtils.getUserId());
        List<UserSns> userSnsList = userSnsService.getUserSnsList(userSnsData);
        model.addAttribute("userSnsList", userSnsList);
        model.addAttribute("userName", UserUtils.getUser().getUserName());
        model.addAttribute("isMypage", true);

        // mypage 정보 가져오기
        userService.setMypageUserInfoForFront(model);
        return ViewUtils.getView("/sns-user/setup-sns");
    }

    @PostMapping("/setup-sns")
    @RequestProperty(layout="mypage")
    @Authorize("hasRole('ROLE_USER')")
    public String setupSnsAction(User user, Model model) {
        user.setUserId(UserUtils.getUserId());
        userSnsService.updateUser(user);
        return ViewUtils.redirect("/sns-user/setup-sns");
    }

    // SNS 연결 해제
    @PostMapping("/disconnect-sns")
    @ResponseBody
    // status { 00: 정상, 01: error }
    public Map<String, String> disconnectSns(UserSns userSnsData) {
        Map<String, String> map = new HashMap<>();
        try {
            UserSns userSnsParam = new UserSns();
            userSnsParam.setUserId(UserUtils.getUserId());

            UserSns userSns = userSnsService.getUserSnsInfo(userSnsData);
            List<UserSns> userSnsList = userSnsService.getUserSnsList(userSnsParam);
            // 연결 된 SNS 계정이 2개 이상이고 CertifiedDate 값이 하나라도 존재한다면 현재 들어온 SNS는 연결해제 진행.
            if(userSnsList != null && userSnsList.size() >= 2){
                for(int i = 0; i < userSnsList.size(); i++){
                    if(userSnsList.get(i).getCertifiedDate() != null){
                        userSns.setCertifiedDate(userSnsList.get(i).getCertifiedDate());
                    }
                }
            }

            if (userSns != null) {
                // 아이디를 등록한 상태이거나, SNS 계정이 2개 이상 연결되어 있으면 해당 SNS 계정은 연결해제 가능
                if (!StringUtils.isEmpty(userSns.getCertifiedDate()) || (userSnsList != null && userSnsList.size() >= 2)) {
                    userSnsService.disconnectSns(userSnsData);
                    map.put("status", "00");
                    map.put("message", "정상적으로 연결이 해제되었습니다.");
                    map.put("value", "00");
                } else {
                    map.put("status", "00");
                    map.put("message", "해제할 수 없는 상태입니다. SNS를 추가 등록 하시거나 회원정보 수정에서 아이디를 등록 해주세요.");
                    map.put("value", "01");
                }
            } else {
                map.put("status", "00");
                map.put("message", "해제할 수 없는 상태입니다. SNS를 추가 등록 하시거나 회원정보 수정에서 아이디를 등록 해주세요.");
                map.put("value", "01");
            }
        } catch(Exception e) {
            map.put("status", "01");
            map.put("message", e.getMessage());
        }
        return map;
    }

    // mypage에서 아이디 중복체크시 사용(SNS 가입 사용자)
    @PostMapping("/loginId-check")
    @ResponseBody
    // status { 00: 정상, 01: error }
    public Map<String, String> loginIdCheck(UserSns userSnsData) {
        Map<String, String> map = new HashMap<>();
        try {
            int userCount = userSnsService.getDuplicatedUserCount(userSnsData);
            map.put("status", "00");
            map.put("message", "조회되었습니다.");
            map.put("value", String.valueOf(userCount));
        } catch(Exception e) {
            map.put("status", "01");
            map.put("message", e.getMessage());
        }
        return map;
    }

    // 가입 된 SNS 아이디가 있는지 확인
    @PostMapping("/sns-joined-check")
    @ResponseBody
    public Map<String, String> snsJoinedCheck(UserSns userSnsParam) {
        Map<String, String> map = new HashMap<>();
        try {
            UserSns userSns = userSnsService.getUserSnsInfo(userSnsParam);
            map.put("status", "00");
            map.put("message", "조회되었습니다.");
            map.put("value", userSns == null ? "0" : "1");
        } catch(Exception e) {
            map.put("status", "01");
            map.put("message", e.getMessage());
        }
        return map;
    }

    //
    @GetMapping("/redirect")
    @RequestProperty(layout="base")
    public String popupRedirect(@RequestParam(value="redirect", required=false) String redirect
            , @RequestParam(value="opener", required=false) String opener
            , Model model) {
        model.addAttribute("redirect", redirect);
        model.addAttribute("opener", opener);
        return ViewUtils.getView("/sns-user/redirect");
    }
}
