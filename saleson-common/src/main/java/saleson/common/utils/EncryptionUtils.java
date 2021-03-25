package saleson.common.utils;

import com.onlinepowers.framework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtils {
	private EncryptionUtils() {
	}

	/**
     * HMAC SHA256 문자열 생성
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    public static String getHmacSHA256(String key, String value) throws Exception {

        Base64.Encoder encoder = Base64.getEncoder();
        String algorithm = "HmacSHA256";

        Mac sha256HMAC = Mac.getInstance(algorithm);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"),algorithm);

        sha256HMAC.init(secretKey);

        String hash = new String (encoder.encode((sha256HMAC.doFinal(value.getBytes("UTF-8")))));


        return hash;
    }

    /**
     * HMAC SHA256 생성된 문자열 비교
     * @param hexString
     * @param key
     * @param value
     * @return
     */
    public static boolean isMatchesHmacSha256Hex(String hexString, String key, String value) {
        boolean flag = false;

        if (!StringUtils.isEmpty(hexString)) {
            try {
                flag = hexString.equals(getHmacSHA256(key, value));
            } catch (Exception e) {
                flag = false;
            }
        }

        return flag;
    }

}
