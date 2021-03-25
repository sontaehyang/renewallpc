package saleson.batch.scheduler;

import com.onlinepowers.framework.sequence.service.SequenceService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import saleson.batch.BatchJobMapper;
import saleson.batch.domain.BatchJob;
import saleson.batch.job.JobService;
import saleson.batch.support.BatchJobParam;

import java.io.File;
import java.util.List;


public class SchedulerServiceImpl implements SchedulerService, InitializingBean {
	private static final Logger log = LoggerFactory.getLogger(SchedulerServiceImpl.class);

	private static boolean IS_CHECKER_EXCUTING = false;

	@Autowired
	Scheduler scheduler;

	@Autowired
	JobService jobService;

	@Autowired
	BatchJobMapper batchJobMapper;

	@Autowired
	SequenceService sequenceService;

	@Autowired
	Environment environment;

	@Override
	public void invokeJobChecker() {
		log.debug("Start Job Checker");

		// sequenceService.getId("OP_SCHEDULING_JOB");

		try {

			// 1. Job정보 로드..
			BatchJobParam batchJobParam = new BatchJobParam();

			//sequenceService.getId("OP_SCHEDULING_JOB");

			List<BatchJob> jobs = batchJobMapper.getBatchJobListByParam(batchJobParam);

			/**
			 * 스케줄러 동작 설정.
			 * BATCH_APPLY_FLAG = 1 이면 최종 설정된 값으로 설정된 상태임.
			 *
			 * 스케줄러 설정 정보 변경.
			 * - TRIGGER_TYPE : 트리거 종류 (1:심플, 2:크론)
			 * - TRIGGER_REPEAT_SECONDS / TRIGGER_CRON_EXPRESSION : 트리거 상세 설정
			 * - BATCH_STATUS : 배치 실행 설정 (1: 배치를 실행함, 2: 배치를 실행하지 않음(중지))
			 *
			 * 위 설정 정보 중 변경 사항이 있는 경우 스케줄러가 설정 정보를 재설정 하기 위해서는 BATCH_APPLY_FLAG = 0 으로 변경한다.
			 * <example>
			 * 1. 스케줄러 실행을 중지하겠다. ==> BATCH_STATUS = 2 로 설정 후 BATCH_APPLY_FLAG = 0 으로 변경한다.
			 * 2. 스케줄러의 트리거 형식을 현재 크론설정인데 60초 마다 반복되는 심플로 변경
			 * 		==> TRIGGER_TYPE = 1, TRIGGER_REPEAT_SECONDS = 60 로 설정 후 BATCH_APPLY_FLAG = 0 으로 변경한다.
			 *
			 * </example>
			 */
			for (BatchJob batchJob : jobs) {
				// 스케줄러에 반영 전인 경우에만 처리 (0:적용전, 1:적용완료)
				if ("1".equals(batchJob.getBatchApplyFlag())) {
					continue;
				}


				MethodInvokingJobDetailFactoryBean jobDetail = null;
				try {
					// Job Information
					jobDetail = new MethodInvokingJobDetailFactoryBean();
					jobDetail.setTargetObject(jobService);
					jobDetail.setTargetMethod(batchJob.getJobMethod());
					jobDetail.setName(batchJob.getBatchJobId());
					jobDetail.setConcurrent(false);
					jobDetail.afterPropertiesSet();
				} catch (Exception e) {
					log.error("[ERROR] jobDetail: {}", e.getMessage(), e);
					continue;
				}


				// Trigger (1:심플, 2:크론)
				SimpleTriggerFactoryBean simpleTrigger = null;
				CronTriggerFactoryBean cronTrigger = null;
				if ("1".equals(batchJob.getTriggerType())) {
					simpleTrigger = new SimpleTriggerFactoryBean();
					simpleTrigger.setBeanName("Trigger" + batchJob.getBatchJobId());
					simpleTrigger.setJobDetail((JobDetail) jobDetail.getObject());
					simpleTrigger.setRepeatInterval(Long.parseLong(batchJob.getTriggerRepeatSeconds()));
					simpleTrigger.afterPropertiesSet();

					if (simpleTrigger.getObject() != null) {
						// 트리거 Key
						TriggerKey triggerKey = simpleTrigger.getObject().getKey();

						// 스케줄러 중지..
						if ("2".equals(batchJob.getBatchStatus())) {
							scheduler.unscheduleJob(triggerKey);
							scheduler.deleteJob(((JobDetail) jobDetail.getObject()).getKey());

							// 스케줄러 적용 정보 업데이트
							batchJobMapper.updateBatchApplyFlagInBatchJob(Integer.parseInt(batchJob.getBatchJobId()));

							continue;
						}

						// 시작 /재시작
						try {
							scheduler.scheduleJob((JobDetail) jobDetail.getObject(), (SimpleTrigger) simpleTrigger.getObject());

						} catch (ObjectAlreadyExistsException ex) {	// 재시작
							log.error("ERROR: {}", ex.getMessage(), ex);
							scheduler.rescheduleJob(triggerKey, (SimpleTrigger) simpleTrigger.getObject());

						}
					}

					// 스케줄러 적용 정보 업데이트
					batchJobMapper.updateBatchApplyFlagInBatchJob(Integer.parseInt(batchJob.getBatchJobId()));

				} else if ("2".equals(batchJob.getTriggerType())) {  // cron
					cronTrigger = new CronTriggerFactoryBean();
					cronTrigger.setBeanName("Trigger" + batchJob.getBatchJobId());
					cronTrigger.setJobDetail((JobDetail) jobDetail.getObject());
					cronTrigger.setCronExpression(batchJob.getTriggerCronExpression());
					cronTrigger.afterPropertiesSet();


					if (cronTrigger.getObject() != null) {
						// 트리거 Key.
						TriggerKey triggerKey = cronTrigger.getObject().getKey();

						// 스케줄러 중지..
						if ("2".equals(batchJob.getBatchStatus())) {
							scheduler.unscheduleJob(triggerKey);
							scheduler.deleteJob(((JobDetail) jobDetail.getObject()).getKey());

							// 스케줄러 적용 정보 업데이트
							batchJobMapper.updateBatchApplyFlagInBatchJob(Integer.parseInt(batchJob.getBatchJobId()));

							continue;
						}

						// 시작 /재시작
						try {
							scheduler.scheduleJob((JobDetail) jobDetail.getObject(), (CronTrigger) cronTrigger.getObject());

						} catch (ObjectAlreadyExistsException ex) {
							log.error("ERROR: {}", ex.getMessage(), ex);
							scheduler.rescheduleJob(triggerKey, (CronTrigger) cronTrigger.getObject());

						}

						// 스케줄러 적용 정보 업데이트
						batchJobMapper.updateBatchApplyFlagInBatchJob(Integer.parseInt(batchJob.getBatchJobId()));
					}


					// 스케줄러 적용 정보 업데이트
					batchJobMapper.updateBatchApplyFlagInBatchJob(Integer.parseInt(batchJob.getBatchJobId()));
				}
			}

		} catch (Exception e) {
			log.error("invokeJobChecker() : {}", e.getMessage(), e);
		}
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		try {

			log.debug("START SCHEDULER ---------------------------------->");

			log.info("{}", environment.getProperty("JAVA_HOME"));
			log.info("saleson.url.shoppingmall : {}", environment.getProperty("saleson.url.shoppingmall"));

			// 0. 최초 구동 시 Scheduling Job 초기화.
			batchJobMapper.updateBatchApplyFlagInitialize();

			// 1. Creating Job.
			MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
			jobDetail.setTargetObject(this);
			jobDetail.setTargetMethod("invokeJobChecker");
			jobDetail.setName("JobChecker");
			jobDetail.setConcurrent(false);
			jobDetail.afterPropertiesSet();


			// 2. Creating Trigger.
			SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
			trigger.setBeanName("JobCheckerTrigger");
			trigger.setJobDetail((JobDetail) jobDetail.getObject());
			trigger.setRepeatInterval(5000);
			trigger.afterPropertiesSet();


			// 3. Setting Job in the Scheculer.
			scheduler.scheduleJob((JobDetail) jobDetail.getObject(), (SimpleTrigger) trigger.getObject());


			// 4. Start Scheduler
			scheduler.start();

		} catch (Exception e) {

			log.debug("SCHEDULER is not fire!! ----------------------------------> : {}", e.getMessage(), e);
		}


	}



}
