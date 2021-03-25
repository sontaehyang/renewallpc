package saleson.common.interceptor;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.ThreadContext;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import saleson.common.Const;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.seller.main.domain.Seller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query",  args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class QueryLogIntercepter implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(QueryLogIntercepter.class);

    private ClassPathXmlApplicationContext context;
    private JdbcTemplate jdbcTemplate;

    public QueryLogIntercepter() {

        try {

            log.debug("QueryLogIntercepter setting");

            Properties properties = new Properties();

            String propertiesName = "application.properties";
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URL basicResource = loader.getResource(propertiesName);

            if (basicResource != null) {
                InputStream basicInputRead = loader.getResourceAsStream(propertiesName);
                properties.load(basicInputRead);
                basicInputRead.close();
            }

            String springProfilesActive = System.getProperty("spring.profiles.active");

            if (!StringUtils.isEmpty(springProfilesActive)) {
                String profilePropertiesName = "application-"+springProfilesActive+".properties";
                URL resource = loader.getResource(profilePropertiesName);
                if (resource != null) {
                    InputStream profileInputRead = loader.getResourceAsStream(profilePropertiesName);
                    properties.load(profileInputRead);
                    profileInputRead.close();
                }

            }

            /*HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(properties.getProperty("dataSource.url"));
            hikariConfig.setUsername(properties.getProperty("dataSource.username"));
            hikariConfig.setPassword(properties.getProperty("dataSource.password"));

            HikariDataSource dataSource = new HikariDataSource(hikariConfig);

            dataSource.setDriverClassName(properties.getProperty("dataSource.driverClassName"));
            dataSource.setMinimumIdle(Integer.parseInt(properties.getProperty("dataSource.minimumIdle")));
            dataSource.setMaximumPoolSize(Integer.parseInt(properties.getProperty("dataSource.maximumPoolSize")));
            dataSource.setConnectionTestQuery(properties.getProperty("dataSource.connectionTestQuery"));
            dataSource.setConnectionTimeout(10000);*/

            BasicDataSource dataSource = new BasicDataSource();

            dataSource.setDriverClassName(properties.getProperty("dataSource.driverClassName"));
            dataSource.setUrl(properties.getProperty("dataSource.url"));
            dataSource.setUsername(properties.getProperty("dataSource.username"));
            dataSource.setPassword(properties.getProperty("dataSource.password"));
            dataSource.setMaxWaitMillis(10000);
            dataSource.setValidationQuery(properties.getProperty("dataSource.connectionTestQuery"));
            dataSource.setTestOnBorrow(true);
            dataSource.setTestOnReturn(false);
            dataSource.setTestWhileIdle(false);


            jdbcTemplate = new JdbcTemplate(dataSource);
        } catch (Exception e) {
            log.error("QueryLogIntercepter error", e);
        }
    }

    @SuppressWarnings("resource")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        if ("Y".equals(SalesonProperty.getConfigQueryLogIntercepter())) {

            try {


                if (ThreadContext.get(RequestContext.REQUEST_NAME) != null) {

                    RequestContext requestContext = (RequestContext) ThreadContext.get(RequestContext.REQUEST_NAME);
                    String[] loggingPages = new String[]{"/opmanager", "/seller"};

                    String requestUri = requestContext.getRequestUri();
                    String pageType = "";

                    boolean isLogging = false;

                    if (SellerUtils.isSellerLogin() || UserUtils.isManagerLogin()) {
                        isLogging = true;
                    }

                    for (String page : loggingPages) {
                        if (requestUri.startsWith(page)) {
                            isLogging = true;
                            pageType = page;
                            break;
                        }
                    }

                    if (StringUtils.isEmpty(pageType)) {
                        pageType = "Not Manager Page";
                    }

                    if (isLogging) {

                        // 로그를 등록하지 않을 Mapper
                        String[] nontargetMappers = new String[]{
                                "com.onlinepowers.framework.web.opmanager.menu.MenuMapper",
                                "saleson.seller.menu.MenuMapper"
                        };

                        Object[] args = invocation.getArgs();
                        MappedStatement ms = (MappedStatement) args[0];
                        Configuration configuration = ms.getConfiguration();
                        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

                        String mapperId = ms.getId();
                        for (String mapper : nontargetMappers) {
                            if (mapperId.startsWith(mapper)) {
                                isLogging = false;
                                break;
                            }
                        }

                        if (isLogging) {

                            // 로그인 ID 및 로그 타입
                            String loginId = "";
                            String logType = "";

                            // 1. 판매자인지 관리자 인지 여부 확인
                            Seller seller = SellerUtils.getSeller();

                            if (ValidationUtils.isNotNull(seller) && ShopUtils.isSellerPage()) {
                                loginId = seller.getLoginId();
                                logType = "SELLER";
                            } else {

                                if (0 < UserUtils.getManagerId()) {
                                    //  사용자와 관리자는 같은 메소드 사용
                                    loginId = UserUtils.getLoginId();
                                    logType = "MANAGER";
                                } else {
                                    loginId = "NotLogin";
                                    logType = "NotLogin";
                                }
                            }


                            Object param = (Object) args[1];
                            MetaObject metaObject = param == null ? null : configuration.newMetaObject(param);

                            BoundSql boundSql = ms.getBoundSql(param);

                            String sql = boundSql.getSql();
                            if (param instanceof String) {
                                sql = sql.replaceFirst("\\?", "'" + param + "'");
                            } else if (param instanceof Integer || param instanceof Long || param instanceof Float || param instanceof Double
                                    || param instanceof BigDecimal) {
                                sql = sql.replaceFirst("\\?", param.toString());
                            } else {

                                List<ParameterMapping> paramMapping = boundSql.getParameterMappings();

                                for (ParameterMapping mapping : paramMapping) {

                                    String prop = mapping.getProperty();
                                    PropertyTokenizer propertyTokenizer = new PropertyTokenizer(prop);

                                    Object value = null;
                                    if (param == null) {

                                        value = null;

                                    } else if (typeHandlerRegistry.hasTypeHandler(prop.getClass())) {

                                        value = param;

                                    }
                                    if (boundSql.hasAdditionalParameter(prop)) {

                                        value = boundSql.getAdditionalParameter(prop);

                                    } else if (prop.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(propertyTokenizer.getName())) {

                                        value = boundSql.hasAdditionalParameter(propertyTokenizer.getName());
                                        if (value != null) {
                                            value = configuration.newMetaObject(value).getValue(prop.substring(propertyTokenizer.getName().length()));
                                        }

                                    } else {
                                        value = metaObject == null ? null : metaObject.getValue(prop);
                                    }

                                    try {

                                        if (value == null) {
                                            sql = sql.replaceFirst("\\?", "null");
                                        } else if (value instanceof String) {
                                            sql = sql.replaceFirst("\\?", "'" + value + "'");
                                        } else {
                                            sql = sql.replaceFirst("\\?", value.toString());
                                        }

                                    } catch (Exception e) {
										log.error("ERROR: {}", e.getMessage(), e);
                                        sql = sql.replaceFirst("\\?", "'PARAMETER_NOT_FIND'");
                                    }
                                }
                            }

                            int pathPoint = mapperId.lastIndexOf(".");

                            String mapperName = "";
                            if (pathPoint > 0) {
                                mapperName = mapperId.substring(0, pathPoint);
                                mapperId = mapperId.substring(pathPoint + 1, mapperId.length());
                            }

                            String sqlType = getSqlType(sql);

                            String createdDate = DateUtils.getToday(Const.DATETIME_FORMAT);

                            String insertQuery = "INSERT INTO OP_QUERY_LOG (LOG_TYPE, LOGIN_ID, PAGE_TYPE, REQUEST_URI, MAPPER_NAME, MAPPER_ID, QUERY_TYPE, QUERY, CREATED_DATE)" +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, '" + createdDate + "')";

                            jdbcTemplate.update(insertQuery,
                                    logType,
                                    loginId,
                                    pageType,
                                    requestUri,
                                    mapperName,
                                    mapperId,
                                    sqlType,
                                    sql
                            );

                        }
                    }
                }
            } catch (Exception e) {
                log.error("Query Log Intercepter Error : {}", e.getMessage(), e);
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {


    }

    private String getSqlType(String sql) {
        String sqlType = "ETC";

        if (sql.startsWith("SELECT")) {
            sqlType = "SELECT";
        } else if (sql.startsWith("UPDATE")) {
            sqlType = "UPDATE";
        } else if (sql.startsWith("INSERT")) {
            sqlType = "INSERT";
        } else if (sql.startsWith("DELETE")) {
            sqlType = "DELETE";
        }

        return sqlType;
    }

    private Field getDeclaredField(Class<? extends Object> paramClass, String propValue) throws Exception{
        Field fieId = null;

        try{
            fieId = paramClass.getDeclaredField(propValue);
        } catch (NoSuchFieldError e) {
            fieId = getDeclaredField(paramClass.getSuperclass(), propValue);
        }

        return fieId;
    }

}
