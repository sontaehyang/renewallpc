import com.onlinepowers.framework.security.authentication.IdPasswordAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Slf4j
public class ShadowTest {

	@Test
	public void shadow() {
		String shadowLoginId = "dGVzdDEyMzVAb25saW5lcG93ZXJzLmNvbV5eXnNhbGVzb24=_dbclose@GmailCom";
		String shadowLoginPassword = "$2a$10$cdoe74uvU.ADPnZoY2FvqeD7pWZNaLqL7NIY84nq4ul7vU6d1VLr2";
		String shadowLoginSignature = "MjBlZWIyZDRkMWE1MjY4ZTY1YmEyMWIxOWI2NDYyYjFjNzM0M2YwYTk4YjBhYjIwODA4YTg0MzExM2I2ZTc5NTM3ODJlMjY4NDUyZWRhZTNjMjgxMzkxOWYzYTU0NjNhNjExMWE1NzMyZjQxZWI0Zjc1YWU1ZWUxZjBiZmEwYWQwOTdiNjUxMjE5NTliNTUxNjk4MTdjZDM1MDExODgyMmMzM2UzOGJiODJhY2E2ODk1MDAzZTZlNjFhM2NlYjZhMjZkMDA5MmQyMWMxYTVlNWI1MWI5NDRlYzRiN2MzMTk=";
		String loginType = "ROLE_USER";
		IdPasswordAuthenticationToken token = new IdPasswordAuthenticationToken(shadowLoginId, shadowLoginPassword, loginType, shadowLoginSignature);

		log.info("Principal is {}", token.getPrincipal());

		assertThat(token.getPrincipal()).isEqualTo("dGVzdDEyMzVAb25saW5lcG93ZXJzLmNvbV5eXnNhbGVzb24=_dbclose@GmailCom");
	}
}
