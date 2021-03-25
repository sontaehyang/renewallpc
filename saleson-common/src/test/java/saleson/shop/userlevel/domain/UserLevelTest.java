package saleson.shop.userlevel.domain;


import org.junit.Test;
import saleson.common.utils.LocalDateUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserLevelTest {

	@Test
	public void localDate() {
		String expirationDate = LocalDateUtils.localDateToString(LocalDate.now().minusDays(1L));
		assertThat(expirationDate).isNotEqualTo("20181216");

	}
	@Test
	public void userLevel() {
		UserLevel userLevel = new UserLevel();
		userLevel.setExpirationDate("20181215");
		userLevel.setReferencePeriod(3);
		userLevel.setExceptReferencePeriod(10);

		assertThat(userLevel.getNewExpirationDate()).isEqualTo("20190315");
		assertThat(userLevel.getStartPaydate()).isEqualTo("20180905");
		assertThat(userLevel.getEndPaydate()).isEqualTo("20181205");
	}

	@Test
	public void userLevel_expireDate_is_null() {
		UserLevel userLevel = new UserLevel();
		userLevel.setExpirationDate(null);
		userLevel.setReferencePeriod(3);
		userLevel.setExceptReferencePeriod(10);

		assertThat(userLevel.getStartPaydate()).isEmpty();
		assertThat(userLevel.getEndPaydate()).isEmpty();
	}

	@Test
	public void userLevel_reference_is_zero() {
		UserLevel userLevel = new UserLevel();
		userLevel.setExpirationDate("20181215");
		userLevel.setReferencePeriod(0);
		userLevel.setExceptReferencePeriod(0);

		assertThat(userLevel.getStartPaydate()).isEqualTo("20181215");
		assertThat(userLevel.getEndPaydate()).isEqualTo("20181215");
	}
}