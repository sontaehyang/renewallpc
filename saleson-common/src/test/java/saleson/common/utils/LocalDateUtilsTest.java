package saleson.common.utils;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.Set;

class LocalDateUtilsTest {
	@Test
	void timezoneTest() {
		/*DateTimeFormatter fomatter =  DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss")
					.withLocale(Locale.getDefault())
					.withZone(ZoneId.getAvailableZoneIds());*/


		Set<String> aa = ZoneId.getAvailableZoneIds();
		for (Object o : aa) {
			System.out.println(o);
		}
	}

}