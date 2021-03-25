package saleson.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import saleson.shop.mall.auction.converter.AuctionXmlConverter;
import saleson.shop.mall.est.converter.EstXmlConverter;

//@Configuration
//@ComponentScan(basePackages = {"saleson", "com.onlinepowers"},
//		excludeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION, pattern="org.springframework.stereotype.Controller"))

@Configuration
@EnableJpaAuditing
public class AppConfig {

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
}

