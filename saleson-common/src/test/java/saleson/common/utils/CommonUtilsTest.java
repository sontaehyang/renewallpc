package saleson.common.utils;





import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonUtilsTest {

	@Test
	public void getClientIp() {
		String ip = CommonUtils.getClientIp();
		String ip2 = CommonUtils.getClientIp(null);

		assertThat(ip).isEqualTo("");
		assertThat(ip2).isEqualTo("");
	}

}