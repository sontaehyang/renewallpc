package saleson.batch;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import saleson.common.scheduling.domain.BatchExecution;
import saleson.batch.domain.BatchJob;
import saleson.batch.support.BatchJobParam;

import java.util.List;

@Mapper("batchJobMapper")
public interface BatchJobMapper {

    /**
     * 배치 작업 조회
     * @param batchJobParam
     * @return
     */
    public List<BatchJob> getBatchJobList(BatchJobParam batchJobParam);

    /**
     * 배치 작업 상세조회
     * @param batchJob
     * @return
     */
    public BatchJob getBatchJobDetailList(BatchJob batchJob);

    /**
     * 배치 작업 수정
     * @param batchJob
     * @return
     */
    public int updateBatchJob(BatchJob batchJob);

    /**
     * 배치 작업 삭제
     * @param object
     * @return
     */
    public int deleteBatchJob(Object object);

    List<BatchJob> getBatchJobListByParam(BatchJobParam batchJobParam);

    void insertBatchJob(BatchJob batchJob);

    void updateBatchApplyFlagInBatchJob(int batchJobId);

    void updateBatchApplyFlagInitialize();

    void mergeBatchExecution(BatchExecution batchExecution);
}
