package saleson.common.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import saleson.shop.mall.auction.converter.AuctionXmlConverter;
import saleson.shop.mall.est.converter.EstXmlConverter;

@TestConfiguration
@EnableJpaAuditing
@PropertySource("classpath:/application.properties")
public class TestConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public EstXmlConverter estXmlConverter() {
		return new EstXmlConverter();
	}

	@Bean
	public AuctionXmlConverter auctionXmlConverter() {
		return new AuctionXmlConverter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
