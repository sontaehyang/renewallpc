package saleson.common.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
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

	@Bean
	public RestTemplate customRestTemplate() {

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

		factory.setConnectTimeout(2000); // 연결시간초과
		factory.setReadTimeout(3000); // 읽기시간초과

		HttpClient client = HttpClientBuilder.create()
				.setMaxConnTotal(200)
				//.setMaxConnPerRoute(20) // ip & port 당 연결 제한
				.build();

		factory.setHttpClient(client);

		return new RestTemplate(factory);
	}
}
