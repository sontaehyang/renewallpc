package saleson.common.notification;

import com.onlinepowers.framework.util.JsonViewUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import saleson.common.notification.micesoft.MicePushSender;

import java.nio.charset.Charset;

@Service("umsApiService")
public class UmsApiService {

    private static final Logger log = LoggerFactory.getLogger(MicePushSender.class);

    @Autowired
    Environment environment;

    @Autowired
    RestTemplate customRestTemplate;

    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("ClientId", environment.getProperty("ums.sub.id"));
        headers.add("ClientSecret", environment.getProperty("ums.api.secret"));

        return headers;
    }

    public void umsApiRestTemplate(String body, String url) {
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        customRestTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseEntity<String> response = customRestTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (HttpStatus.OK != response.getStatusCode()) {
            log.error(JsonViewUtils.objectToJson(response));
        }
    }

}
