package saleson.common.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConnectionConfig {

    @Bean
    public RestTemplate customRestTemplate() {
        return new RestTemplate(getFactory(5000, 10000, 200));
    }

    @Bean
    public RestTemplate naverPaymentRestTemplate() {
        return new RestTemplate(getFactory(60000, 60000, 200));
    }

    @Bean
    public RestTemplate naverOtherRestTemplate() {
        return new RestTemplate(getFactory(10000, 10000, 200));
    }

    @Bean
    public RestTemplate umsAgentRestTemplate() {
        return new RestTemplate(getFactory(60000, 60000, 200));
    }

    private HttpComponentsClientHttpRequestFactory getFactory (int connectTimeout, int readTimeout, int maxConnTotal) {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        factory.setConnectTimeout(connectTimeout); // 연결시간초과
        factory.setReadTimeout(readTimeout); // 읽기시간초과

        HttpClient client = HttpClientBuilder.create()
                .setMaxConnTotal(maxConnTotal)
                //.setMaxConnPerRoute(20) // ip & port 당 연결 제한
                .build();

        factory.setHttpClient(client);

        return factory;
    }
}
