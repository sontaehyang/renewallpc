package saleson.common;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import saleson.common.config.*;
import saleson.common.configuration.TestConfig;

@ExtendWith({SpringExtension.class})
//@ComponentScan(basePackages={"com.onlinepowers", "saleson"})
//@ContextConfiguration(AppConfig.class, SalesonInitializer.class, SalesonProperty.class, SecurityConfig.class, SessionListener.class})
@ContextConfiguration
public class SalesonTest {
	@Configuration
	//@ComponentScan(basePackages={"com.onlinepowers", "saleson"})
	@Import({TestConfig.class})
	@PropertySource({"classpath:application.properties"})
	@ImportResource({
			"classpath:spring/context-test.xml",
		"classpath:spring/context-transaction.xml",
		"classpath:spring/context-framework-repository.xml",
		"classpath:spring/context-sqlmapper.xml",
		"classpath:spring/context-jpa.xml",
		"classpath:spring/context-notification.xml",
		"classpath:spring/context-popbill.xml",
		"classpath:spring/context-datasource-erp.xml"
	})
	@ActiveProfiles("local-skc")
	public static class ContextConfig{}
}
