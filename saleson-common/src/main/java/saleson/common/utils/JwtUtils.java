package saleson.common.utils;

        import com.fasterxml.jackson.core.type.TypeReference;
        import com.onlinepowers.framework.common.ServiceType;
        import com.onlinepowers.framework.util.CipherUtils;
        import com.onlinepowers.framework.util.JsonViewUtils;
        import io.jsonwebtoken.Jwts;
        import io.jsonwebtoken.SignatureAlgorithm;
        import io.jsonwebtoken.security.Keys;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.util.StringUtils;
        import saleson.common.security.api.JwtCode;
        import saleson.common.security.api.RequestTokenInfo;
        import saleson.common.security.api.TokenAuthGuestUserInfo;
        import saleson.common.security.api.TokenAuthUserInfo;
        import saleson.shop.user.domain.GuestUser;

        import javax.crypto.SecretKey;
        import javax.servlet.http.HttpServletRequest;
        import java.time.LocalDateTime;
        import java.time.ZoneId;
        import java.util.Base64;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.UUID;

public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    private JwtUtils() {
    }

    /**
     * Jwt 토큰 인증키(HS256) 생성
     * <br/> JwtCode.TOKEN_SECURE_KEY
     * @return
     */
    public static String generateTokenSecureKey() {

        try {

            SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

            return Base64.getEncoder().encodeToString(key.getEncoded());

        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);
            return "";
        }

    }
    /**
     * 토큰 발행 HMAC Hashing key 생성
     * <br/> JwtCode.REFRESH_TOKEN_SECURE_KEY
     * @return
     */
    public static String  generateRefreshTokenSecureKey() {
        return generateTokenSecureKey();
    }

    /**
     *토큰 Sing HMAC Hashing key 생성
     * <br/> JwtCode.TOKEN_SIGN_SECURE_KEY
     * @return
     */
    public static String generateTokenSignSecureKey() {
        return RandomStringUtils.getRandomString("",10,20);
    }

    public static String getCode(JwtCode jwtCode) {
        return jwtCode.getCode();
    }

    public static SecretKey getSecretKey() throws Exception {
        byte[] decode = Base64.getDecoder().decode(JwtUtils.getCode(JwtCode.TOKEN_SECURE_KEY));
        return Keys.hmacShaKeyFor(decode);
    }

    /**
     * JWT Token 에서 해당 Key 값 가져오기
     * @param token
     * @param key
     * @return
     * @throws Exception
     */
    public static Object getValueByToken(String token, String key) throws Exception{
        return Jwts.parser().setSigningKey(JwtUtils.getSecretKey())
                .parseClaimsJws(token)
                .getBody().get(key);
    }

    /**
     * HttpServletRequest에서 Token 획득
     * @param request
     * @return
     * @throws Exception
     */
    public static String getToken(HttpServletRequest request) throws Exception {
        String bearerToken = request.getHeader(JwtUtils.getCode(JwtCode.JWT_TOKEN_HEADER_PARAM));
        if (bearerToken == null) {
            return null;
        }

        int length = bearerToken.length();
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtUtils.getCode(JwtCode.JWT_TOKEN_PREFIX))) {
            String jwt = bearerToken.substring(7, length);
            return jwt.trim();
        }

        return null;
    }

    public static String getJti(String token) throws Exception {
        return (String)JwtUtils.getValueByToken(token, "jti");
    }

    /**
     * 클라이언트 IP 확인
     * @param request
     * @return
     */
    public static String getClientIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        if(JwtUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(JwtUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(JwtUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if(JwtUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(JwtUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            // ip = request.getRemoteAddr();
            ip = saleson.common.utils.CommonUtils.getClientIp(request);
        }

        return ip;
    }

    /**
     * 문자열 빈값 체크
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return value == null || "".equals(value) || "null".equals(value);
    }

    /**
     * JWT 수행시 ERROR 메세지 구현
     * @param status
     * @param error
     * @param description
     * @return
     */
    public static HashMap<String, Object> getErrorMessageMap(int status, String code, String error, String description) {

        HashMap<String, Object> map = new HashMap < String, Object>();
        map.put("status", status);
        map.put("code", code);
        map.put("message", error);
        map.put("description", description);

        return map;
    }

    /**
     * JWT 수행시 ERROR 메세지 구현
     * @param status
     * @param error
     * @param description
     * @return
     */
    public static String getErrorMessage(int status, String code, String error, String description) {

        String message = "";
        try {
            HashMap<String, Object> errorMap = JwtUtils.getErrorMessageMap(status, code, error, description);
            message = JsonViewUtils.objectToJson(errorMap);
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);
        }
        return message;
    }

    /**
     * JWT 수행시 ERROR 로그 구현
     * @param exceptionType
     * @param requestUrl
     * @param token
     * @return
     */
    public static String getErrorLog (String exceptionType, String requestUrl, String token) {
        return "JwtTokenError Log\n"
                +"\t exceptionType : " +exceptionType+"\n"
                +"\t uri : " +requestUrl+"\n"
                +"\t token : " +token+"\n";
    }

    /**
     * Token 인증용 sign 생성
     * @param tokenAuthUserInfo
     * @return
     * @throws Exception
     */
    public static String getTokenSign(TokenAuthUserInfo tokenAuthUserInfo) throws Exception{
        return EncryptionUtils.getHmacSHA256(
                JwtUtils.getCode(JwtCode.TOKEN_SIGN_SECURE_KEY),
                JsonViewUtils.objectToJson(tokenAuthUserInfo)
        );
    }

    /**
     * sign 으로 Token 인증
     * @param sign
     * @param tokenAuthUserInfo
     * @return
     */
    public static boolean isValidateTokenSign(String sign, TokenAuthUserInfo tokenAuthUserInfo) {
        return EncryptionUtils.isMatchesHmacSha256Hex(sign,
                JwtUtils.getCode(JwtCode.TOKEN_SIGN_SECURE_KEY),
                JsonViewUtils.objectToJson(tokenAuthUserInfo)
        );
    }

    /**
     * Request Token 인증용 sign 생성
     * @param requestTokenInfo
     * @return
     * @throws Exception
     */
    public static String getRequestTokenSign(RequestTokenInfo requestTokenInfo) throws Exception{
        return EncryptionUtils.getHmacSHA256(
                JwtUtils.getCode(JwtCode.TOKEN_SIGN_SECURE_KEY),
                JsonViewUtils.objectToJson(requestTokenInfo)
        );
    }

    /**
     * sign 으로 Token 인증
     * @param sign
     * @param requestTokenInfo
     * @return
     */
    public static boolean isRequestTokenSign(String sign, RequestTokenInfo requestTokenInfo) {
        return EncryptionUtils.isMatchesHmacSha256Hex(sign,
                JwtUtils.getCode(JwtCode.TOKEN_SIGN_SECURE_KEY),
                JsonViewUtils.objectToJson(requestTokenInfo)
        );
    }

    /**
     * Guest User Token 인증용 sign 생성
     * @param info
     * @return
     * @throws Exception
     */
    public static String getGuestUserTokenSign(TokenAuthGuestUserInfo info) throws Exception{
        return EncryptionUtils.getHmacSHA256(
                JwtUtils.getCode(JwtCode.TOKEN_SIGN_SECURE_KEY),
                JsonViewUtils.objectToJson(info)
        );
    }

    /**
     * sign 으로 Token 인증
     * @param sign
     * @param info
     * @return
     */
    public static boolean isGuestUserTokenSign(String sign, TokenAuthGuestUserInfo info) {
        return EncryptionUtils.isMatchesHmacSha256Hex(sign,
                JwtUtils.getCode(JwtCode.TOKEN_SIGN_SECURE_KEY),
                JsonViewUtils.objectToJson(info)
        );
    }

    /**
     * Https 여부 확인
     * @param request
     * @return
     */
    public static boolean isSecureRequest(HttpServletRequest request) {
        // 로컬 설정
        if (ServiceType.LOCAL) return true;
        return request.isSecure() && JwtUtils.isEmpty(request.getHeader("Location"));
    }

    /**
     * Jwt Token 종료일자
     * @param addDays
     * @return
     */
    public static Date getExpirationDate(int addDays) {

        LocalDateTime localDateTime =  LocalDateTime.now().plusDays(addDays);

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getExpirationDateByAddMinute(long minute) {

        LocalDateTime localDateTime =  LocalDateTime.now().plusMinutes(minute);

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Jwt Jti 생성
     * @return
     */
    public static String generateJti() {
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 토큰에서 Guest User 획득
     * @param token
     * @return
     */
    public static GuestUser getGuestUser(String token) {
        try {
            String encryptionGuestUserString = (String)JwtUtils.getValueByToken(token, JwtUtils.getCode(JwtCode.JWT_CLAIM_GUEST_USER));

            String guestUserString = CipherUtils.decrypt(encryptionGuestUserString);

            return (GuestUser)JsonViewUtils.jsonToObject(guestUserString, new TypeReference<GuestUser>(){});
        } catch (Exception e) {
            log.error("getGuestUserByToken Error", e);
            return null;
        }
    }

    /**
     * request 에서 Guest User 획득
     * @param request
     * @return
     */
    public static GuestUser getGuestUser(HttpServletRequest request) {
        try {

            String token = JwtUtils.getToken(request);

            return JwtUtils.getGuestUser(token);

        } catch (Exception e) {
            log.error("getGuestUserByToken Error", e);
            return null;
        }
    }
}
