package saleson.common.security.token;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class SmsTokenServiceImplTest {

	@Test
	public void base64() {
		String test = "안녕하세요.123123.absdafaewf!@#!@#$";
		byte[] encBytes = org.springframework.security.crypto.codec.Base64.encode(test.getBytes());
		String enc1 = new String(encBytes);
		String enc2 = java.util.Base64.getEncoder().encodeToString(test.getBytes());

		assertThat(enc1).isEqualTo(enc2);
	}

}