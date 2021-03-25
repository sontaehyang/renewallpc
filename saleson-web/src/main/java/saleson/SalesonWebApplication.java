package saleson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication // @Configuration, @EnableAutoConfiguraion, @ComponentScan
@Configuration()
@EnableAutoConfiguration(exclude = {FreeMarkerAutoConfiguration.class})
@ComponentScan(basePackages = {"com.onlinepowers", "saleson"})
@ImportResource({"classpath*:spring/context-*.xml"})
public class SalesonWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SalesonWebApplication.class);
    }

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(SalesonWebApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);

    }

}
