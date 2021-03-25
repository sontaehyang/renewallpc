package saleson.common.config;


import com.onlinepowers.framework.security.authentication.DefaultIdPasswordAuthenticationProvider;
import com.onlinepowers.framework.security.authentication.IdPasswordSmsAuthenticationProvider;
import com.onlinepowers.framework.security.authentication.filter.IPAuthenticationFilter;
import com.onlinepowers.framework.security.authentication.filter.IdPasswordAuthenticationFilter;
import com.onlinepowers.framework.security.authentication.filter.IdPasswordSmsAuthenticationFilter;
import com.onlinepowers.framework.security.exception.handler.OpAccessDeniedHandlerImpl;
import com.onlinepowers.framework.security.session.OpSessionRegistryImpl;
import com.onlinepowers.framework.security.userdetails.OpUserDetailsService;
import com.onlinepowers.framework.security.userdetails.hierarchicalroles.HierarchyStringsFactoryBean;
import com.onlinepowers.framework.web.filter.HttpsCookieFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.channel.*;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import saleson.common.security.AuthenticationFailureHandlerService;
import saleson.common.security.AuthenticationSuccessHandlerService;
import saleson.common.security.LogoutSuccessHandler;
import saleson.common.security.authentication.filter.JwtTokenAuthenticationFilter;
import saleson.common.security.authentication.filter.RememberMeAuthenticationFilter;
import saleson.common.security.exception.handler.SalesonAccessDeniedExceptionHandler;
import saleson.common.security.matcher.CsrfSecurityRequestMatcher;
import saleson.common.security.web.Http301RedirectStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws  Exception {



        http
				.cors().and()
            .authorizeRequests()
                .antMatchers("/content/**").permitAll()
                .antMatchers("/upload/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/opmanager/accessdenied").permitAll()
        ;

        http
            .addFilterBefore(httpsCookieFilter(), ChannelProcessingFilter.class)
            .addFilterAt(logoutFilter(), LogoutFilter.class)
            .addFilterBefore(concurrentSessionFilter(), ConcurrentSessionFilter.class)
            .addFilterBefore(idPasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(rememberMeFilter(), org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter.class)
            .addFilterAfter(jwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;

        http.requiresChannel()
                .antMatchers("/**")
        ;

        http.headers()
                .contentTypeOptions()
                .and()
                .frameOptions().sameOrigin()
        ;

        http.csrf()
                .requireCsrfProtectionMatcher(csrfMatcher())
        ;

        http.exceptionHandling()
                .accessDeniedHandler(opAccessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        auth.authenticationProvider(defaultIdPasswordAuthenticationProvider());
        auth.authenticationProvider(rememberMeAuthenticationProvider());
    }

    @Bean
    public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter() {
        return new JwtTokenAuthenticationFilter("/api/**");
    }

    @Bean
    public CsrfSecurityRequestMatcher csrfMatcher() {
        return new CsrfSecurityRequestMatcher(
                "/demo/**", // DEMO URL
                "/opmanager/**", // Opmanager Ajax
                "/seller/**", // Opmanager Ajax
                "/api/**",
                "/auth/token",
                "/auth/sns-token",
                "/common/**",
                "/order/pay",   // 이니시스 결제 return url
                "/order/lgdacom/return-url", //엘지 데이콤 결제값 받기 - 최종 승인전
                "/order/lgdacom/note-url", //엘지 데이콤 가상계좌 입금 확인
                "/order/payco/note-url", //페이코 가상계좌 입금 확인
                "/order/payco/return-url", //Payco결제값 받기 (주문완료시 이동하는 URL)
                "/order/cj/callback-url", //CJ 결제 처리
                "/order/ini-cancel", //이니시스 결제 취소
                "/order/ini-vacct", //이니시스 가상계좌 입금 통보
                "/order/ini-mobile-noti-url", //이니시스 모바일 입금 통보 및 가상계좌 채번.
                "/order/kspay/return", //KSPAY 결제값 받기
                "/order/kcp/cardhub", //
                "/order/kcp-vacct", // KCP 가상계좌 입금 통보(DB처리후 성공시 0000 실패시 0000이 아닌 문자열 반환)
                "/order/nicepay-vacct", // 나이스페이 가상계좌 입금 통보
                "/order/easypay/payment-window", // 이지페이 iframe
                "/order/easypay/order_res", // 이지페이 결제창 응답결과 페이지
                "/order/easypay/easypay_request", // 이지페이 결제창 응답결과 페이지
				"/order/recovery", // 주문복구
                "/m/order/pay", // 이니시스 결제 return url
                "/m/order/lgdacom/return-url/**", // 엘지 데이콤 결제값 받기 - 최종 승인전
                "/m/order/cj/redirect-url", // CJ 결제 승인처리
                "/m/order/payco/return-url", // Payco결제값 받기
                "/m/order/ini-cancel", // 이니시스 결제 취소
                "/m/order/ini-next", // 이니시스 결제 취소
                "/m/order/kcp/order_approval", //
                "/m/order/kcp-vacct", // KCP 가상계좌 입금 통보(DB처리후 성공시 0000 실패시 0000이 아닌 문자열 반환)
                "/m/order/easypay/payment-window", // 이지페이 iframe
                "/m/order/easypay/order_res", // 이지페이 결제창 응답결과 페이지
                "/m/order/easypay/easypay_request", // 이지페이 결제창 응답결과 페이지
                "/m/order/recovery", // 주문복구
                "/event-log/**" // 이벤트 로그
        );
    }

    @Bean
    public HttpsCookieFilter httpsCookieFilter() {
        return new HttpsCookieFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        AuthenticationManager manager= super.authenticationManager();

        return manager;
    }

    @Bean
    public ChannelDecisionManagerImpl channelDecisionManager() {

        ChannelDecisionManagerImpl channelDecisionManager = new ChannelDecisionManagerImpl();

        List<Object> list = new ArrayList<>();

        list.add(secureChannelProcessor());
        list.add(insecureChannelProcessor());

        channelDecisionManager.setChannelProcessors(list);

        return channelDecisionManager;
    }

    @Bean
    public SecureChannelProcessor secureChannelProcessor() {

        SecureChannelProcessor channelProcessor = new SecureChannelProcessor();
        channelProcessor.setEntryPoint(retryWithHttpsEntryPoint());

        return channelProcessor;
    }

    @Bean
    public InsecureChannelProcessor insecureChannelProcessor() {

        InsecureChannelProcessor channelProcessor = new InsecureChannelProcessor();

        channelProcessor.setEntryPoint(retryWithHttpEntryPoint());

        return channelProcessor;
    }

    @Bean
    public RetryWithHttpsEntryPoint retryWithHttpsEntryPoint() {

        RetryWithHttpsEntryPoint entryPoint = new RetryWithHttpsEntryPoint();
        entryPoint.setRedirectStrategy(redirectStrategy());
        entryPoint.setPortMapper(portMapper());

        return entryPoint;
    }

    @Bean
    public RetryWithHttpEntryPoint retryWithHttpEntryPoint() {
        RetryWithHttpEntryPoint entryPoint = new RetryWithHttpEntryPoint();

        entryPoint.setRedirectStrategy(redirectStrategy());
        entryPoint.setPortMapper(portMapper());

        return entryPoint;
    }

    @Bean
    public Http301RedirectStrategy redirectStrategy() {
        return new Http301RedirectStrategy();
    }

    @Bean
    public PortMapperImpl portMapper() {
        PortMapperImpl portMapper = new PortMapperImpl();

        Map<String, String> map = new HashMap<>();
        map.put(environment.getProperty("security.port.http"),environment.getProperty("security.port.https"));

        portMapper.setPortMappings(map);

        return portMapper;
    }

    @Bean
    public RememberMeAuthenticationFilter rememberMeFilter() throws Exception {

        RememberMeAuthenticationFilter filter
                = new RememberMeAuthenticationFilter(authenticationManager(), rememberMeServices());

        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());

        return filter;
    }

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {

        TokenBasedRememberMeServices services =  new TokenBasedRememberMeServices("springRock", opUserDetailsService());

        services.setCookieName("OP_REMEMBER_ME_COOKIE");
        services.setParameter("op_remember_me");
        services.setTokenValiditySeconds(90 * 24 * 60 * 60); //90일
        services.setUseSecureCookie(false);

        return services;
    }

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider("springRock");
    }


    @Bean
    public ExceptionTranslationFilter exceptionTranslationFilter() {
        ExceptionTranslationFilter filter = new ExceptionTranslationFilter(authenticationEntryPoint());

        filter.setAccessDeniedHandler(opAccessDeniedHandler());

        return filter;
    }

    @Bean
    public LoginUrlAuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/users/login2");
    }

    @Bean
    public ConcurrentSessionFilter concurrentSessionFilter() {

        ConcurrentSessionFilter filter = new ConcurrentSessionFilter(sessionRegistry());
        return filter;
    }

    public OpSessionRegistryImpl sessionRegistry() {
        return new OpSessionRegistryImpl();
    }

    @Bean
    public IdPasswordAuthenticationFilter idPasswordAuthenticationFilter() throws Exception {
        IdPasswordAuthenticationFilter filter = new IdPasswordAuthenticationFilter();

        filter.setAuthenticationManager(authenticationManager());
        filter.setSuccessHandler(authenticationSuccessHandler());
        filter.setFailureHandler(authenticationFailureHandler());
        filter.setRememberMeServices(rememberMeServices());

        return filter;
    }

    @Bean
    public DefaultIdPasswordAuthenticationProvider defaultIdPasswordAuthenticationProvider() {

        DefaultIdPasswordAuthenticationProvider provider = new DefaultIdPasswordAuthenticationProvider();

        provider.setUserDetailsService(opUserDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public IdPasswordSmsAuthenticationFilter idPasswordSmsAuthenticationFilter() throws Exception {
        IdPasswordSmsAuthenticationFilter filter = new IdPasswordSmsAuthenticationFilter();

        filter.setAuthenticationManager(authenticationManager());
        filter.setSuccessHandler(authenticationSuccessHandler());
        filter.setFailureHandler(authenticationFailureHandler());

        return filter;
    }

    @Bean
    public IdPasswordSmsAuthenticationProvider idPasswordSmsAuthenticationProvider() {
        IdPasswordSmsAuthenticationProvider provider = new IdPasswordSmsAuthenticationProvider();

        // sms 인증로직을 적용할 loginType (여러개인 경우 콤마(,)로 구분함) => "ROLE_OPMANAGER,ROLE_SUPERVISOR"
        provider.setSmsAuthLoginTypes("ROLE_OPMANAGER");

        provider.setUserDetailsService(opUserDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public IPAuthenticationFilter ipAuthenticationFilter() {

        IPAuthenticationFilter filter = new IPAuthenticationFilter();

        filter.setTargetRole("ROLE_OPMANAGER");

        return filter;
    }


    public OpAccessDeniedHandlerImpl opAccessDeniedHandler() {
        OpAccessDeniedHandlerImpl handler = new OpAccessDeniedHandlerImpl();

        handler.setErrorPage("/");
        return handler;
    }

    @Bean
    public AuthenticationSuccessHandlerService authenticationSuccessHandler() {
        AuthenticationSuccessHandlerService handlerService = new AuthenticationSuccessHandlerService();

        handlerService.setDefaultTargetUrl("/");

        return handlerService;
    }

    @Bean
    public AuthenticationFailureHandlerService authenticationFailureHandler() {
        AuthenticationFailureHandlerService handlerService = new AuthenticationFailureHandlerService();

        handlerService.setDefaultFailureUrl("/app/loginFailProcess");

        return handlerService;
    }

    @Bean
    public LogoutFilter logoutFilter() {
        LogoutFilter filter = new LogoutFilter(logoutSuccessHandler(), new SecurityContextLogoutHandler());

        filter.setFilterProcessesUrl("/op_security_logout");

        return filter;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        roleHierarchy.setHierarchy(hierarchyStrings().toString());

        return roleHierarchy;
    }

    @Bean
    public HierarchyStringsFactoryBean hierarchyStrings() {
        return new HierarchyStringsFactoryBean();
    }

    @Bean
    public OpUserDetailsService opUserDetailsService() {
        OpUserDetailsService service = new OpUserDetailsService();

        service.setRoleHierarchy(roleHierarchy());

        return service;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SalesonAccessDeniedExceptionHandler opAccessDeniedExceptionHandler() {
        return new SalesonAccessDeniedExceptionHandler();
    }


	@Bean
	public MultipartResolver multipartResolver(){
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxInMemorySize(10);
		commonsMultipartResolver.setMaxUploadSize(10);
		return commonsMultipartResolver;
	}
}
