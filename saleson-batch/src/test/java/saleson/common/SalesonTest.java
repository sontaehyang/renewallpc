package saleson.common;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import saleson.common.configuration.TestConfig;

@ExtendWith({SpringExtension.class})
@ContextConfiguration
public class SalesonTest {
	@Configuration
	@Import({TestConfig.class})
	@ImportResource({
		"classpath*:spring/context-test.xml",
		"classpath*:spring/context-transaction.xml",
		"classpath*:spring/context-framework-repository.xml",
		"classpath*:spring/context-sqlmapper.xml",
		"classpath*:spring/context-jpa.xml",
		"classpath*:spring/context-notification.xml",
		"classpath*:spring/context-popbill.xml",})
	public static class ContextConfig{}
}
