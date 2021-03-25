package saleson.batch;

import com.onlinepowers.framework.context.ThreadContext;
import com.onlinepowers.framework.util.DateUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import saleson.common.Const;
import saleson.common.scheduling.domain.BatchExecution;

public class BatchExecutionAspect {
	
	@Autowired
	BatchExecutionMapper batchExecutionMapper;

	final String BATCH_EXECUTION = "BATCH_EXECUTION";
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public void before(JoinPoint joinPoint) {
		String executionDate = DateUtils.getToday(Const.DATE_FORMAT);
		BatchExecution batchExecution = new BatchExecution(joinPoint.getSignature().toShortString(), executionDate);


		ThreadContext.put(BATCH_EXECUTION, batchExecution);
		
		batchExecutionMapper.mergeBatchExecution(batchExecution);
		
	}
	
	
	public void afterReturning() {
			BatchExecution batchExecution = (BatchExecution) ThreadContext.get(BATCH_EXECUTION);
			
			// 로그 기록
			batchExecution.setResult("1");
			batchExecution.setEndTime(DateUtils.getToday("HH:mm:ss"));
			//batchExecution.setMessage(getBatchTtitle() + " 배치 작업 성공");
			
			batchExecutionMapper.mergeBatchExecution(batchExecution);
	}
	
	
	
	public void afterThrowing(Throwable cause) {
		
			BatchExecution batchExecution = (BatchExecution) ThreadContext.get(BATCH_EXECUTION);
			log.error("{} 배치 작업 중 오류 발생", batchExecution.getBatchType());
			
	
			batchExecution.setMessage(batchExecution.getBatchType() + " 배치 작업 중 오류 발생 - " + cause.getMessage());
			batchExecution.setResult("2");
			batchExecution.setEndTime(DateUtils.getToday("HH:mm:ss"));		// 오류 발생 시간
	
			batchExecutionMapper.mergeBatchExecution(batchExecution);
	}
	
}
