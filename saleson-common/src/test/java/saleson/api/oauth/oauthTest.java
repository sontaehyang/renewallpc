package saleson.api.oauth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.util.ConvertUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import saleson.common.SalesonTest;
import saleson.common.config.HttpConnectionConfig;
import saleson.shop.order.pg.NicepayServiceImpl;
import saleson.shop.receipt.support.CashbillResponse;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class oauthTest extends SalesonTest{

	private RestTemplate restTemplate;

	private final String LOGIN_URL = "https://login.microsoftonline.com/6d6e5e0b-0033-43d2-bc45-b590f106e2f6/oauth2/token";

	@BeforeEach
	void setup() {
//		MockEnvironment environment = new MockEnvironment();
        HttpConnectionConfig connectionConfig = new HttpConnectionConfig();
        this.restTemplate = connectionConfig.customRestTemplate();

	}

    private HttpEntity<MultiValueMap<String, Object>> getMultiValueMapHttpEntity(MultiValueMap<String, Object> parameters) {
        return new HttpEntity<>(parameters, getHttpHeaders());
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    @Test
    void getToken() {
        String result = "";

        MultiValueMap<String, Object> parametersMap = new LinkedMultiValueMap<String, Object>();

        parametersMap.add("grant_type", "client_credentials");
        parametersMap.add("client_id", "ef15e692-0fea-482c-a1dc-c1d474b698ea");
        parametersMap.add("client_secret", "ba627aa9-30d5-4656-8b55-31221ce114ab");
        parametersMap.add("resource", "https://management.azure.com");

        ResponseEntity<String> response = restTemplate.postForEntity(LOGIN_URL,
                getMultiValueMapHttpEntity(parametersMap), String.class);

        HashMap<String,Object> responseMap = new HashMap<String,Object>();

        if (200 == response.getStatusCode().value()) {
            // API에서 200이 아닌 경우에도 응답이 오는 지 확인하여 로직을 구현함.
            responseMap = (HashMap<String,Object>) JsonViewUtils.jsonToObject(response.getBody(),new TypeReference<HashMap<String,Object>>(){});

            String token_type = responseMap.get("token_type").toString();
            String expires_in = responseMap.get("expires_in").toString();
            String expires_on = responseMap.get("expires_on").toString();
            String not_before = responseMap.get("not_before").toString();
            String ext_expires_in = responseMap.get("ext_expires_in").toString();
            String resource = responseMap.get("resource").toString();
            String access_token = responseMap.get("access_token").toString();

            result = "token_type : " + token_type
                    + "expires_in : " + expires_in
                    + "expires_on : " + expires_on
                    + "not_before : " + not_before
                    + "ext_expires_in : " + ext_expires_in
                    + "resource : " + resource
                    + "access_token : " + access_token;


        }

        log.debug(result);
    }

}