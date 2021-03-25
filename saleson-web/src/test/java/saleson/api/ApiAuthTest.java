package saleson.api;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiAuthTest {
	@Test
    void hmacTest() throws InvalidKeyException, NoSuchAlgorithmException {

		String secret = "b18n9591c9y220uw5o82c8vb";

		// 일반회원
		// String message = "{\"loginType\":\"ROLE_USER\",\"loginId\":\"savrina20@naver.com\",\"password\":\"tls0217*\"}";

		// SNS회원
		String message = "{\"snsType\":\"kakao\",\"snsId\":\"1071294425\",\"email\":\"savrina20@naver.com\",\"snsName\":\"권세희\"}";

		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
		sha256_HMAC.init(secret_key);

		String hash = StringUtils.newStringUtf8(Base64.encodeBase64(sha256_HMAC.doFinal(message.getBytes())));
		assertThat(hash).isEqualTo("zO0kXOiwBT3OwwydGHUhTanYEbTevqmxQW47/gd/6LU=");

    }
}