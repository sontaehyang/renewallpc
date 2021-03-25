package saleson.shop.auth;

import com.onlinepowers.framework.common.OpKeyHolder;
import com.onlinepowers.framework.security.authentication.IdPasswordAuthenticationToken;
import com.onlinepowers.framework.security.token.TokenService;
import com.onlinepowers.framework.security.token.domain.Token;
import com.onlinepowers.framework.security.userdetails.OpUserDetails;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.ShadowUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import saleson.api.common.ApiResponseEntity;
import saleson.api.common.enumerated.ApiError;
import saleson.common.exception.InvalidAuthenticationException;
import saleson.common.exception.ProcessApiException;
import saleson.common.exception.SecureServletException;
import saleson.common.security.api.JwtCode;
import saleson.common.security.api.JwtTokenService;
import saleson.common.utils.EncryptionUtils;
import saleson.common.utils.JwtUtils;
import saleson.shop.auth.domain.GuestRequestAuth;
import saleson.shop.auth.domain.RequestAuth;
import saleson.shop.auth.domain.ResponseAuth;
import saleson.shop.auth.domain.SnsRequestAuth;
import saleson.shop.coupon.CouponService;
import saleson.shop.coupon.domain.Coupon;
import saleson.shop.coupon.support.UserCouponParam;
import saleson.shop.usersns.UserSnsService;
import saleson.shop.usersns.domain.UserSns;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://saleson-front.coding.onlinepowers.com")
@RestController
public class JwtTokenAuthenticationController {

    private Logger log = LoggerFactory.getLogger(JwtTokenAuthenticationController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserSnsService userSnsService;

    @Autowired
    private CouponService couponService;

    @Autowired
    @Qualifier("smsTokenService")
    private TokenService smsTokenService;

    @PostMapping("/api/auth/token")
    public ResponseEntity<ResponseAuth> getUserToken(HttpServletRequest request,
                                                     @RequestBody RequestAuth requestAuth) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseAuth responseAuth;
        try {

            checkedHmac(request, JsonViewUtils.objectToJson(requestAuth));

            String loginType = requestAuth.getLoginType();
            String loginId = requestAuth.getLoginId().trim();
            String password = requestAuth.getPassword();

            if (StringUtils.isEmpty(loginType)) {
                throw new InvalidAuthenticationException();
            }

            if ("ROLE_OPMANAGER".equals(loginType)) {
                loginId = loginId + OpKeyHolder.OPMANAGER_LOGIN_KEY;
            } else if ("ROLE_SELLER".equals(loginType)) {
                loginId = loginId + OpKeyHolder.SELLER_LOGIN_KEY;
            }

            IdPasswordAuthenticationToken authRequest = new IdPasswordAuthenticationToken(loginId, password, loginType, null);

            Authentication authentication = authenticationManager.authenticate(authRequest);

            UserDetails userDetails = (OpUserDetails) authentication.getPrincipal();

            if ("ROLE_OPMANAGER".equals(loginType)) {
                loginId = loginId.replaceAll(OpKeyHolder.OPMANAGER_LOGIN_KEY, "");
            } else if ("ROLE_SELLER".equals(loginType)) {
                loginId = loginId.replaceAll(OpKeyHolder.SELLER_LOGIN_KEY, "");
            }

            String token = jwtTokenService.getJwtToken(
                    loginType,
                    loginId,
                    ((OpUserDetails) userDetails).getUser().getPassword(),
                    JwtUtils.getClientIpAddress(request)
            );

            responseAuth = new ResponseAuth(true, "발행되었습니다.", token);

        } catch (SecureServletException e) {

            log.error("ERROR: {}", e.getMessage(), e);
            httpStatus = HttpStatus.BAD_REQUEST;
            responseAuth = new ResponseAuth(false, "Https만 이용 가능합니다.", "");

        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);
            httpStatus = HttpStatus.UNAUTHORIZED;
            responseAuth = new ResponseAuth(false, "발행에 실패하였습니다.", "");
        }

        return new ResponseEntity<>(responseAuth, httpStatus);
    }

    @PostMapping("/api/auth/sns-token")
    public ResponseEntity<ResponseAuth> getSnsUserToken(HttpServletRequest request,
                                                        @RequestBody SnsRequestAuth requestAuth) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseAuth responseAuth;
        try {

            checkedHmac(request, JsonViewUtils.objectToJson(requestAuth));

            UserSns userSnsData = new UserSns();
            userSnsData.setSnsType(requestAuth.getSnsType());
            userSnsData.setSnsId(requestAuth.getSnsId());
            userSnsData.setEmail(requestAuth.getEmail());
            userSnsData.setSnsName(requestAuth.getSnsName());

            Map<String, String> map = new HashMap<>();
            map = userSnsService.joinProcess(new UserSns(), map, userSnsData);

            UserCouponParam userCouponParam = new UserCouponParam();

            if ("03".equals(map.get("value"))) {
                //신규회원가입 쿠폰 발급[2017-09-08]minae.yun
                userCouponParam.setCouponTargetTimeType("2");
                List<Coupon> newUserCouponList = couponService.getCouponByTargetTimeType(userCouponParam);

                if (newUserCouponList != null && newUserCouponList.size() != 0) {
                    for (Coupon coupon : newUserCouponList) {
                        userCouponParam.setCouponId(coupon.getCouponId());
                        userCouponParam.setUserId(Long.valueOf(map.get("userId")));
                        couponService.insertCouponTargetUserOne(userCouponParam);
                        couponService.userCouponDownload(userCouponParam);
                    }
                }
            }

            if (userSnsData.getIsMypage()) {
                throw new Exception("마이페이지 호출이면 발급이 불가합니다.");
            }

            String snsLoginId = map.get("loginId");

            if (StringUtils.isEmpty(snsLoginId)) {
                throw new Exception("로그인 ID가 존재하지 않습니다.");
            }

            String loginType = "ROLE_USER";
            String loginId = ShadowUtils.getShadowLoginKey(snsLoginId, "");
            String password = ShadowUtils.getShadowLoginPassword(snsLoginId);
            String signature = ShadowUtils.getShadowLoginSignature(snsLoginId);

            IdPasswordAuthenticationToken authRequest = new IdPasswordAuthenticationToken(loginId, password, loginType, signature);
            Authentication authentication = authenticationManager.authenticate(authRequest);

            UserDetails userDetails = (OpUserDetails) authentication.getPrincipal();

            String token = jwtTokenService.getJwtToken(
                    loginType,
                    loginId,
                    ((OpUserDetails) userDetails).getUser().getPassword(),
                    JwtUtils.getClientIpAddress(request)
            );

            responseAuth = new ResponseAuth(true, "발행되었습니다.", token);

        } catch (SecureServletException e) {

            log.error("ERROR: {}", e.getMessage(), e);
            httpStatus = HttpStatus.BAD_REQUEST;
            responseAuth = new ResponseAuth(false, "Https만 이용 가능합니다.", "");

        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);
            httpStatus = HttpStatus.UNAUTHORIZED;
            responseAuth = new ResponseAuth(false, "발행에 실패하였습니다.", "");
        }

        return new ResponseEntity<>(responseAuth, httpStatus);
    }

    @PostMapping("/api/auth/guest-token")
    public ResponseEntity getGuestToken(HttpServletRequest request,
                                        @RequestBody GuestRequestAuth requestAuth) {
        HttpStatus httpStatus = HttpStatus.OK;
        ResponseAuth responseAuth;

        try {

            checkedHmac(request, JsonViewUtils.objectToJson(requestAuth));

            Token smsToken = new Token();

            smsToken.setRequestToken(requestAuth.getRequestToken());
            smsToken.setAccessToken(requestAuth.getAuthNumber());

            if (!smsTokenService.isValidToken(smsToken)) {

                httpStatus = HttpStatus.UNAUTHORIZED;
                responseAuth = new ResponseAuth(false, "발행에 실패하였습니다.", "");

            } else {

                String token = jwtTokenService.getJwtGuestToken(requestAuth.getUserName(), requestAuth.getPhoneNumber());
                responseAuth = new ResponseAuth(true, "발행되었습니다.", token);
            }

        } catch (SecureServletException e) {

            log.error("ERROR: {}", e.getMessage(), e);
            httpStatus = HttpStatus.BAD_REQUEST;
            responseAuth = new ResponseAuth(false, "Https만 이용 가능합니다.", "");

        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);
            httpStatus = HttpStatus.UNAUTHORIZED;
            responseAuth = new ResponseAuth(false, "발행에 실패하였습니다.", "");
        }

        return new ResponseEntity<>(responseAuth, httpStatus);
    }

    /**
     * Hamc 체크
     *
     * @param request
     * @param jsonParam
     * @throws ProcessApiException
     */
    private void checkedHmac(HttpServletRequest request, String jsonParam) throws ProcessApiException {

        String hmac = request.getHeader("Hmac");

        if (StringUtils.isEmpty(hmac)) {
            throw new ProcessApiException("Hmac이 header에 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        if (!EncryptionUtils.isMatchesHmacSha256Hex(hmac, JwtUtils.getCode(JwtCode.REFRESH_TOKEN_SECURE_KEY), jsonParam)) {
            throw new ProcessApiException("인증에 실패 했습니다.", HttpStatus.UNAUTHORIZED);
        }

    }
}
