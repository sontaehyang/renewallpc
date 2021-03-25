package saleson.common.configuration;

import com.onlinepowers.framework.context.ThreadContextInitFilter;
import com.onlinepowers.framework.web.security.xss.XSSFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.support.MultipartFilter;

@Slf4j
@Configuration
public class FilterConfiguration {

	@Bean
	public FilterRegistrationBean<MultipartFilter> getMultipartFilter() {

		FilterRegistrationBean<MultipartFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new MultipartFilter());
		registrationBean.setOrder(10);
		registrationBean.addUrlPatterns("/*");

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<XSSFilter> getXSSFilter() {

		FilterRegistrationBean<XSSFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new XSSFilter());
		registrationBean.setOrder(20);
		registrationBean.addUrlPatterns("/*");

		return registrationBean;
	}


	@Bean
	public FilterRegistrationBean<ThreadContextInitFilter> getThreadContextInitFilter() {

		FilterRegistrationBean<ThreadContextInitFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new ThreadContextInitFilter());
		registrationBean.setOrder(30);
		registrationBean.addUrlPatterns("/*");

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<CharacterEncodingFilter> getNicepayFilterRegistrationBean() {

		FilterRegistrationBean registrationBean = new FilterRegistrationBean();

		registrationBean.setFilter(getEuckrFilter());
		registrationBean.addUrlPatterns("/order/nicepay-vacct");
		registrationBean.setOrder(9999);

		return registrationBean;
	}

	public CharacterEncodingFilter getEuckrFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("EUC-KR");
		filter.setForceEncoding(true);
		return filter;
	}
}
