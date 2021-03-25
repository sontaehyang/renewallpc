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

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        factory.setConnectTimeout(5000); // 연결시간초과
        factory.setReadTimeout(10000); // 읽기시간초과

        HttpClient client = HttpClientBuilder.create()
                .setMaxConnTotal(200)
                //.setMaxConnPerRoute(20) // ip & port 당 연결 제한
                .build();

        factory.setHttpClient(client);

        return new RestTemplate(factory);
    }

    @Bean
    public RestTemplate naverPaymentRestTemplate() {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        factory.setConnectTimeout(60000); // 연결시간초과
        factory.setReadTimeout(60000); // 읽기시간초과

        HttpClient client = HttpClientBuilder.create()
                .setMaxConnTotal(200)
                //.setMaxConnPerRoute(20) // ip & port 당 연결 제한
                .build();

        factory.setHttpClient(client);

        return new RestTemplate(factory);
    }

    @Bean
    public RestTemplate naverOtherRestTemplate() {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        factory.setConnectTimeout(10000); // 연결시간초과
        factory.setReadTimeout(10000); // 읽기시간초과

        HttpClient client = HttpClientBuilder.create()
                .setMaxConnTotal(200)
                //.setMaxConnPerRoute(20) // ip & port 당 연결 제한
                .build();

        factory.setHttpClient(client);

        return new RestTemplate(factory);
    }
}
