package saleson.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinepowers.board.bind.BoardContextArgumentResolver;
import com.onlinepowers.board.interceptor.BoardHandlerInterceptor;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.bind.support.RequestContextArgumentResolver;
import com.onlinepowers.framework.web.bind.support.TokenCheckResultArgumentResolver;
import com.onlinepowers.framework.web.interceptor.ManagerAccessPortCheckInterceptor;
import com.onlinepowers.framework.web.interceptor.RequestContextHandlerInterceptor;
import com.onlinepowers.framework.web.interceptor.WebLogInterceptor;
import com.onlinepowers.framework.web.servlet.handler.JsonViewResponseMethodProcessor;
import com.onlinepowers.framework.web.servlet.handler.OpHandlerExceptionResolver;
import com.onlinepowers.framework.web.servlet.view.OpUrlBasedViewResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import saleson.common.interceptor.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.*;

@Slf4j
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {


	@Autowired
	private ConversionService conversionService;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${resource.static.location}")
	private String staticResourceLocation;

	@Value("${resource.storage.location}")
	private String storageResourceLocation;

	@Value("${saleson.api.crossorigins}")
	private String allowedOrigins;


	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/content/**")
				.addResourceLocations(staticResourceLocation + "/content/")
				.setCachePeriod(3600)
				.resourceChain(true)
				.addResolver(new EncodedResourceResolver())
				.addResolver(new PathResourceResolver())
		;
		registry.addResourceHandler("/upload/**")
				.addResourceLocations(storageResourceLocation + "/upload/")
				.setCachePeriod(3600)
				.resourceChain(true)
				.addResolver(new EncodedResourceResolver())
				.addResolver(new PathResourceResolver())


		;

		//super.addResourceHandlers(registry);
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor())
				.addPathPatterns("/**");


		registry.addInterceptor(webLogInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/content/**");

		registry.addInterceptor(requestContextHandlerInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/content/**");

		registry.addInterceptor(shopHandlerInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/content/**");


		registry.addInterceptor(boardHandlerInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/content/**");


		registry.addInterceptor(managerAccessPortCheckInterceptor())
				.addPathPatterns("/opmanager/**")
				.excludePathPatterns("/content/**");


		registry.addInterceptor(opmanagerIpAuthenticationHandlerInterceptor())
				.addPathPatterns("/opmanager/**")
				.excludePathPatterns("/content/**");

        registry.addInterceptor(opmanagerHandlerInterceptor())
                .addPathPatterns("/opmanager/**")
                .excludePathPatterns("/content/**");

        registry.addInterceptor(actionLogHandlerInterceptor())
                .addPathPatterns("/opmanager/**")
                .excludePathPatterns("/content/**");

		registry.addInterceptor(sellerHandlerInterceptor())
				.addPathPatterns("/seller/**")
				.excludePathPatterns("/content/**");

        registry.addInterceptor(actionLogHandlerInterceptor())
                .addPathPatterns("/seller/**")
                .excludePathPatterns("/content/**");

    }

	//우리가 만든 ArgumentResolver를 추가한다.
	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(new RequestContextArgumentResolver());
		argumentResolvers.add(new BoardContextArgumentResolver());
		argumentResolvers.add(new TokenCheckResultArgumentResolver());
		argumentResolvers.add(querydslPredicateArgumentResolver());
		argumentResolvers.add(pageableHandlerMethodArgumentResolver());
	}

	@Override
	protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		returnValueHandlers.add(jsonViewResponseMethodProcessor());
		//super.addReturnValueHandlers(Arrays.asList());
	}


	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		String[] origins;

		if (allowedOrigins != null && !allowedOrigins.isEmpty()) {
			if (allowedOrigins.indexOf(",") > -1) {
				origins = StringUtils.delimitedListToStringArray(allowedOrigins, ",");
				origins = Arrays.stream(origins)
						.map(s -> s.trim()).toArray(String[]::new);
			} else {
				origins = new String[] {allowedOrigins};
			}

			registry.addMapping("/api/**")
					.allowedOrigins(origins)
					.allowedMethods("GET", "HEAD", "POST", "OPTIONS")  // "PUT", "DELETE"
					.maxAge(3600);			// pre-flight의 요청 결과를 저장할 시간 지정. 해당 시간 동안은 pre-flight를 다시 요청하지 않는다.
		}
	}

	// querydsl
	@Bean
	public QuerydslBindingsFactory querydslBindingsFactory() {
		return new QuerydslBindingsFactory(new SimpleEntityPathResolver("INSTANCE"));
	}


	@Bean
	public QuerydslPredicateArgumentResolver querydslPredicateArgumentResolver() {
		return new QuerydslPredicateArgumentResolver(querydslBindingsFactory(), Optional.of(conversionService));
	}

	// JPA
	@Bean
	public PageRequest pageRequest() {
		return PageRequest.of(1, 10);
	}

	@Bean
	public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setOneIndexedParameters(true);
		resolver.setFallbackPageable(pageRequest());

		return resolver;
	}


	// Locale Resolver
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(Locale.KOREAN);
		resolver.setCookieMaxAge(100000000);

		return resolver;
	}


	// Exception Resolver
	@Bean
	public OpHandlerExceptionResolver opHandlerExceptionResolver() {
		return new OpHandlerExceptionResolver();
	}


	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		//objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper;
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jacksonConverter = new
				MappingJackson2HttpMessageConverter();
		jacksonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.valueOf("application/json")));
		jacksonConverter.setObjectMapper(objectMapper);
		return jacksonConverter;
	}

	@Bean
	public JsonViewResponseMethodProcessor jsonViewResponseMethodProcessor() {
		List<HttpMessageConverter<?>> converters = Arrays.asList(mappingJackson2HttpMessageConverter());
		JsonViewResponseMethodProcessor jsonViewResponseMethodProcessor = new JsonViewResponseMethodProcessor(converters);
		return jsonViewResponseMethodProcessor;
	}


	/*@Bean
	public FilterRegistrationBean<ThreadContextInitFilter> getFilterRegistrationBean() {

		FilterRegistrationBean<ThreadContextInitFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new ThreadContextInitFilter());
		registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE - 1);
		registrationBean.addUrlPatterns("/*");

		return registrationBean;

	}*/


	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Bean
	public WebLogInterceptor webLogInterceptor() {
		return new WebLogInterceptor();
	}

	@Bean
	public RequestContextHandlerInterceptor requestContextHandlerInterceptor() {
		return new RequestContextHandlerInterceptor();
	}

	@Bean
	public ShopHandlerInterceptor shopHandlerInterceptor() {
		return new ShopHandlerInterceptor();
	}

	@Bean
	public BoardHandlerInterceptor boardHandlerInterceptor() {
		BoardHandlerInterceptor boardHandlerInterceptor = new BoardHandlerInterceptor();
		boardHandlerInterceptor.setBoardPrefixUri("board");
		return boardHandlerInterceptor;
	}

	@Bean
	public ManagerAccessPortCheckInterceptor managerAccessPortCheckInterceptor() {
		return new ManagerAccessPortCheckInterceptor();
	}

	@Bean
	public OpmanagerIpAuthenticationHandlerInterceptor opmanagerIpAuthenticationHandlerInterceptor() {
		return new OpmanagerIpAuthenticationHandlerInterceptor();
	}

	@Bean
	public OpmanagerHandlerInterceptor opmanagerHandlerInterceptor() {
		return new OpmanagerHandlerInterceptor();
	}

	@Bean
	public SellerHandlerInterceptor sellerHandlerInterceptor() {
		return new SellerHandlerInterceptor();
	}


	/*
	 * Configure ContentNegotiatingViewResolver
	 */
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);

		// Define all possible view resolvers
		List<ViewResolver> resolvers = new ArrayList<>();

		//resolvers.add(csvViewResolver());
		resolvers.add(tilesViewResolver());
		//resolvers.add(pdfViewResolver());

		resolver.setViewResolvers(resolvers);

		List<View> defaultViews = new ArrayList<>();
		defaultViews.add(new MappingJackson2JsonView());
		resolver.setDefaultViews(defaultViews);


		return resolver;
	}

	@Bean
	public OpUrlBasedViewResolver tilesViewResolver() {
		OpUrlBasedViewResolver resolver = new OpUrlBasedViewResolver();
		resolver.setViewClass(TilesView.class);
		resolver.setOrder(1);
		/*InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/"); resolver.setSuffix(".html");*/
		return resolver;
	}


	@Bean(name = "tilesConfigure")
	public TilesConfigurer tilesConfigurer() {

		final TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions(new String[]{"/WEB-INF/views/layouts/layouts.xml"});
		configurer.setCheckRefresh(true);

		return configurer;
	}


	/**
	 * JSP Precompile
	 *
	 * @return
	 */
/*	@Bean
	public ServletContextInitializer preCompileJspsAtStartup() {
		return servletContext -> {
			getDeepResourcePaths(servletContext, "/WEB-INF/views/").forEach(jspPath -> {
				log.info("Registering JSP: {}", jspPath);
				ServletRegistration.Dynamic reg = servletContext.addServlet(jspPath, Constants.JSP_SERVLET_CLASS);
				reg.setInitParameter("jspFile", jspPath);
				reg.setLoadOnStartup(99);
				reg.addMapping(jspPath);
			});
		};
	}

	private static Stream<String> getDeepResourcePaths(ServletContext servletContext, String path) {
		return (path.endsWith("/")) ? servletContext.getResourcePaths(path).stream().flatMap(p -> getDeepResourcePaths(servletContext, p))
				: Stream.of(path);
	}*/

    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setInitParameter("defaultHtmlEscape", "false");
            }
        };
    }

    @Bean
    public ActionLogHandlerInterceptor actionLogHandlerInterceptor() {
        return new ActionLogHandlerInterceptor();
    }

}
