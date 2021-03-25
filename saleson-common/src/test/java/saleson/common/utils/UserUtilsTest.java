package saleson.common.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserUtilsTest {

	@Test
	void isUserLogin() {
		assertThat(UserUtils.isUserLogin()).isFalse();
	}

	@Test
	void isGuestLogin() {
		assertThat(UserUtils.isGuestLogin()).isFalse();
	}
}