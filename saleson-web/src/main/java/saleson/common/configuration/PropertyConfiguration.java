package saleson.common.configuration;

import com.onlinepowers.framework.properties.support.PropertiesFactoryBean;
import com.onlinepowers.framework.properties.support.RefreshableProperies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertyConfiguration {

	@Bean
	public RefreshableProperies property() {
		RefreshableProperies property = new RefreshableProperies();
		property.setAutoStartup(true);
		property.setCheckInterval(0);
		property.setResource(new ClassPathResource("application.properties"));
		return property;
	}

	@Bean
	public PropertiesFactoryBean propertiesFactoryBean() {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setRefreshableProperies(property());
		return propertiesFactoryBean;
	}
}
