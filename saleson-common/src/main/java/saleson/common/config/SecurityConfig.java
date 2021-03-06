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
                "/order/pay",   // ???????????? ?????? return url
                "/order/lgdacom/return-url", //?????? ????????? ????????? ?????? - ?????? ?????????
                "/order/lgdacom/note-url", //?????? ????????? ???????????? ?????? ??????
                "/order/payco/note-url", //????????? ???????????? ?????? ??????
                "/order/payco/return-url", //Payco????????? ?????? (??????????????? ???????????? URL)
                "/order/cj/callback-url", //CJ ?????? ??????
                "/order/ini-cancel", //???????????? ?????? ??????
                "/order/ini-vacct", //???????????? ???????????? ?????? ??????
                "/order/ini-mobile-noti-url", //???????????? ????????? ?????? ?????? ??? ???????????? ??????.
                "/order/kspay/return", //KSPAY ????????? ??????
                "/order/kcp/cardhub", //
                "/order/kcp-vacct", // KCP ???????????? ?????? ??????(DB????????? ????????? 0000 ????????? 0000??? ?????? ????????? ??????)
                "/order/nicepay-vacct", // ??????????????? ???????????? ?????? ??????
                "/order/easypay/payment-window", // ???????????? iframe
                "/order/easypay/order_res", // ???????????? ????????? ???????????? ?????????
                "/order/easypay/easypay_request", // ???????????? ????????? ???????????? ?????????
				"/order/recovery", // ????????????
                "/m/order/pay", // ???????????? ?????? return url
                "/m/order/lgdacom/return-url/**", // ?????? ????????? ????????? ?????? - ?????? ?????????
                "/m/order/cj/redirect-url", // CJ ?????? ????????????
                "/m/order/payco/return-url", // Payco????????? ??????
                "/m/order/ini-cancel", // ???????????? ?????? ??????
                "/m/order/ini-next", // ???????????? ?????? ??????
                "/m/order/kcp/order_approval", //
                "/m/order/kcp-vacct", // KCP ???????????? ?????? ??????(DB????????? ????????? 0000 ????????? 0000??? ?????? ????????? ??????)
                "/m/order/easypay/payment-window", // ???????????? iframe
                "/m/order/easypay/order_res", // ???????????? ????????? ???????????? ?????????
                "/m/order/easypay/easypay_request", // ???????????? ????????? ???????????? ?????????
                "/m/order/recovery", // ????????????
                "/event-log/**" // ????????? ??????
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
        services.setTokenValiditySeconds(90 * 24 * 60 * 60); //90???
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

        // sms ??????????????? ????????? loginType (???????????? ?????? ??????(,)??? ?????????) => "ROLE_OPMANAGER,ROLE_SUPERVISOR"
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
