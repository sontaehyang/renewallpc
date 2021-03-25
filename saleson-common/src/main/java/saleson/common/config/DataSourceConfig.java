package saleson.common.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

//@Configuration
//@EnableJpaRepositories(basePackages = {"saleson","com.onlinepowers"})
public class DataSourceConfig {

	/*@Bean(name = "dataSource")
	@Primary
	@ConfigurationProperties("spring.datasource.hikari")
	public HikariDataSource dataSource() {
		return DataSourceBuilder.create()
				.type(HikariDataSource.class)
				.build();
	}*/

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties firstDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.hikari")
	public HikariDataSource dataSource() {
		return firstDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}



	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setPackagesToScan("saleson,com.onlinepowers");

		HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
		emf.setJpaVendorAdapter(va);

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		jpaProperties.put("hibernate.hbm2ddl.auto", "create");
		emf.setJpaProperties(jpaProperties);
		emf.afterPropertiesSet();
		return emf;
	}
}
