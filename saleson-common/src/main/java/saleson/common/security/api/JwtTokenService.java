package saleson.common.security.api;

import com.onlinepowers.framework.util.CipherUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.JwtUtils;
import saleson.shop.user.domain.GuestUser;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service("jwtTokenService")
public class JwtTokenService {

	@Autowired
	Environment environment;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private Logger log = LoggerFactory.getLogger(JwtTokenService.class);

	public String getJwtToken(HashMap<String, Object> claims) {

		/**
		 * Hashing 알고리즘은 HS256
		 * 생성일 기준 만료기간은 1일임.
		 */

		String token = "";

		try {

			Date expirationDate = JwtUtils.getExpirationDate(1);

			SecretKey key = JwtUtils.getSecretKey();

			token = Jwts.builder()
					.setClaims(claims)
					.setId(JwtUtils.generateJti())
					.setIssuer(this.getIssuer())
					.setExpiration(expirationDate)
					.signWith(key)
					.compact();

		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}

		return token;

	}

	public String getJwtToken(String loginType, String loginId, String encodePassword, String ip) {
		/**
		 * Hashing 알고리즘은 HS256
		 * 생성일 기준 만료기간은 1일.
		 */

		String token = "";

		try {

			Date expirationDate = JwtUtils.getExpirationDate(1);

			SecretKey key = JwtUtils.getSecretKey();

			LinkedHashMap<String, Object> claims = new LinkedHashMap<>();

			String jti = JwtUtils.generateJti();

			TokenAuthUserInfo tokenAuthUserInfo
					= new TokenAuthUserInfo(
					loginType,
					loginId,
					encodePassword,
					ip,
					jti
			);

			String sign = JwtUtils.getTokenSign(tokenAuthUserInfo);

			claims.put(JwtUtils.getCode(JwtCode.JWT_CLAIM_ID), loginId);
			claims.put(JwtUtils.getCode(JwtCode.JWT_CLAIM_LOGIN_TYPE), loginType);
			claims.put(JwtUtils.getCode(JwtCode.JWT_CLAIM_SIGN), sign);

			token = Jwts.builder()
					.setClaims(claims)
					.setId(jti)
					.setIssuer(this.getIssuer())
					.setExpiration(expirationDate)
					.signWith(key)
					.compact();

		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}

		return token;
	}

	public String getJwtRequestToken(long userId, String requestToken, long minute) {

		String token = "";

		try {

			// 5분
			Date expirationDate = JwtUtils.getExpirationDateByAddMinute(minute);

			SecretKey key = JwtUtils.getSecretKey();

			LinkedHashMap<String, Object> claims = new LinkedHashMap<>();

			String jti = JwtUtils.generateJti();

			String sign = JwtUtils.getRequestTokenSign(new RequestTokenInfo(userId, requestToken, jti));

			claims.put(JwtUtils.getCode(JwtCode.JWT_CLAIM_ID), userId);
			claims.put(JwtUtils.getCode(JwtCode.JWT_CLAIM_SIGN), sign);

			token = Jwts.builder()
					.setClaims(claims)
					.setId(jti)
					.setIssuer(this.getIssuer())
					.setExpiration(expirationDate)
					.signWith(key)
					.compact();

		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}

		return token;

	}

	public String getJwtGuestToken(String userName, String phoneNumber) {
		String token = "";

		try {

			long minute = 30L;

			// 5분
			Date expirationDate = JwtUtils.getExpirationDateByAddMinute(minute);

			SecretKey key = JwtUtils.getSecretKey();

			GuestUser user = new GuestUser(userName, phoneNumber);
			LinkedHashMap<String, Object> claims = new LinkedHashMap<>();

			String jti = JwtUtils.generateJti();

			String sign = JwtUtils.getGuestUserTokenSign(new TokenAuthGuestUserInfo(user, jti));
			String encryptionGuestUserString = CipherUtils.encrypt(JsonViewUtils.objectToJson(user));

			claims.put(JwtUtils.getCode(JwtCode.JWT_CLAIM_LOGIN_TYPE), "ROLE_GUEST");
			claims.put(JwtUtils.getCode(JwtCode.JWT_CLAIM_GUEST_USER), encryptionGuestUserString);
			claims.put(JwtUtils.getCode(JwtCode.JWT_CLAIM_SIGN), sign);

			token = Jwts.builder()
					.setClaims(claims)
					.setId(jti)
					.setIssuer(this.getIssuer())
					.setExpiration(expirationDate)
					.signWith(key)
					.compact();

		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}

		return token;

	}

	public Jws<Claims> getClaimsByToken(String token) throws Exception{

		SecretKey key = JwtUtils.getSecretKey();

		return  Jwts.parser().setSigningKey(key).parseClaimsJws(token);
	}

	public String getIssuer() {
		return SalesonProperty.getSalesonUrlShoppingmall();
	}

	public boolean isTokenAuthentication(String token, Jws<Claims> claims) {

		// 발행자 체크
		if (!this.getIssuer().equals(claims.getBody().getIssuer())) {
			log.error("Invalid Issuer");
			return false;
		}
		return true;
	}

	public boolean isRequestTokenAuthentication(String jwtToken, String reqeustToken, Jws<Claims> claims) {

		try {

			if (!isTokenAuthentication(jwtToken, claims)) {
				return false;
			}

			String sign = (String)JwtUtils.getValueByToken(jwtToken, JwtUtils.getCode(JwtCode.JWT_CLAIM_SIGN));
			String userId = String.valueOf(JwtUtils.getValueByToken(jwtToken, JwtUtils.getCode(JwtCode.JWT_CLAIM_ID)));

			String jti = claims.getBody().getId();

			if (!JwtUtils.isRequestTokenSign(sign, new RequestTokenInfo(StringUtils.string2long(userId), reqeustToken, jti))) {
				return false;
			}

		} catch (Exception igroe) {
			return false;
		}

		return true;
	}

	public boolean isJwtException(Exception e) {

		if (e instanceof CompressionException) {
			return true;
		}

		if (e instanceof IncorrectClaimException) {
			return true;
		}

		if (e instanceof ExpiredJwtException) {
			return true;
		}

		if (e instanceof MalformedJwtException) {
			return true;
		}

		if (e instanceof MissingClaimException) {
			return true;
		}

		if (e instanceof PrematureJwtException) {
			return true;
		}

		if (e instanceof RequiredTypeException) {
			return true;
		}

		if (e instanceof io.jsonwebtoken.security.SignatureException) {
			return true;
		}

		if (e instanceof InvalidClaimException) {
			return true;
		}

		if (e instanceof ClaimJwtException) {
			return true;
		}

		if (e instanceof UnsupportedJwtException) {
			return true;
		}

		if (e instanceof JwtException) {
			return true;
		}

		return false;
	}

	public boolean validateToken(String token, TokenAuthUserInfo tokenAuthUserInfo) throws Exception{
		String sign = (String)JwtUtils.getValueByToken(token, JwtUtils.getCode(JwtCode.JWT_CLAIM_SIGN));
		return JwtUtils.isValidateTokenSign(sign, tokenAuthUserInfo);
	}

	public boolean validateGuestToken(String token, TokenAuthGuestUserInfo tokenAuthGuestUserInfo) throws Exception{
		String sign = (String)JwtUtils.getValueByToken(token, JwtUtils.getCode(JwtCode.JWT_CLAIM_SIGN));
		return JwtUtils.isGuestUserTokenSign(sign, tokenAuthGuestUserInfo);
	}
}
