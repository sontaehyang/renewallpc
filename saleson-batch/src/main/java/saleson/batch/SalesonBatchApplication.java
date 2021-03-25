package saleson.batch;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import saleson.batch.scheduler.SchedulerService;
import saleson.batch.scheduler.SchedulerServiceImpl;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages={"com.onlinepowers", "saleson"})
@ImportResource({"classpath*:spring/context-*.xml"})
@Configuration
public class SalesonBatchApplication {
	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(SalesonBatchApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);

	}


	@Autowired
	Scheduler scheduler;

	@Autowired
	Environment environment;


	@Bean
	public SchedulerService schedulerService() {
		return new SchedulerServiceImpl();
	}
}
