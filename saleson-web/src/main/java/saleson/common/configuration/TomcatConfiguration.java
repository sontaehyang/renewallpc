package saleson.common.configuration;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfiguration {
	@Value("${tomcat.ajp.enabled}")
	boolean ajpEnabled;

	@Value("${tomcat.ajp.protocol}")
	String ajpProtocol;

	@Value("${tomcat.ajp.port}")
	int ajpPort;

	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		if (ajpEnabled) {
			tomcat.addAdditionalTomcatConnectors(createAjpConnector());
		}
		return tomcat;
	}

	private Connector createAjpConnector() {
		Connector ajpConnector = new Connector(ajpProtocol);
		ajpConnector.setPort(ajpPort);
		ajpConnector.setSecure(false);
		ajpConnector.setAllowTrace(false);
		ajpConnector.setScheme("http");

		// SKC-20201015 tomcat 8.5 버전과 9.0 버전은 secretRequired 속성을 사용하여야 합.
		((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecretRequired(false);

		return ajpConnector;
	}
}
