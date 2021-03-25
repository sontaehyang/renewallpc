package saleson.batch;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import saleson.common.scheduling.domain.BatchExecution;

@Mapper("batchExecutionMapper")
public interface BatchExecutionMapper {


	/**
	 * 배치 실행 로그를 작성한다.
	 * @param batchExecution
	 */
	void mergeBatchExecution(BatchExecution batchExecution);
	
}
