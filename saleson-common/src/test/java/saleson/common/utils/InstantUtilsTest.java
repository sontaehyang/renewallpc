package saleson.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class InstantUtilsTest {

	@Test
	void getDate() {
		String date = InstantUtils.getDate(Instant.now());
		log.debug("[{}]",date);
		assertThat(date).isNotEqualTo("2019. 10. 23");
	}
	@Test
	void getDateNull() {
		String date = InstantUtils.getDate(null);
		log.debug("[{}]",date);
		assertThat(date).isNull();
	}

	@Test
	void getDateTime() {
		String date = InstantUtils.getDateTime(Instant.now());
		log.debug("[{}]",date);
		assertThat(date).isNotEqualTo("2019. 10. 23");
	}
	@Test
	void getDateTimeIsNull() {
		String date = InstantUtils.getDateTime(null);
		log.debug("[{}]",date);
		assertThat(date).isNull();
	}
}