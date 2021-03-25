package saleson.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

//@EnableSpringHttpSession
@EnableJdbcHttpSession(tableName = "OP_SESSION")
@Configuration
public class SessionConfig {


    /* // EnableSpringHttpSession
    @Bean
    public MapSessionRepository sessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }
    */

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();

        serializer.setCookieName("SALESONID");
        // https 미오픈으로 인한 임시 주석
//        serializer.setUseSecureCookie(true);
//        serializer.setSameSite("None");
        serializer.setSameSite("");
        serializer.setCookiePath("/");
        serializer.setUseHttpOnlyCookie(true);

        return serializer;
    }


}
