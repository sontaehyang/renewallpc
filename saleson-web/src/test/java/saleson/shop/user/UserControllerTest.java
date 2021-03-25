package saleson.shop.user;

import com.onlinepowers.framework.util.StringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {

	@Test
	void stripXSSTest() {
		String uri = "http://localhost:8080/users/login?target=?\"onmouseover%3D%27J4S4%289490%29%27bad%3D\"&popup=?\"asdfasd\"asdfasdf";
		String result = StringUtils.stripXSS(uri);

		assertThat(result).isNotEqualTo(uri);
	}
}