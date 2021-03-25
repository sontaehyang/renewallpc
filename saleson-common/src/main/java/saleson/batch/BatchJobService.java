package saleson.batch;

import saleson.batch.domain.BatchJob;
import saleson.batch.support.BatchJobParam;

import java.util.List;
import java.util.Map;

public interface BatchJobService {

    /**
     * 배치 작업 조회
     * @param batchJobParam
     * @return
     */
    public List<BatchJob> getBatchJobList(BatchJobParam batchJobParam);

    /**
     * 배치 작업 상세 조회
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
     * @param list
     * @return
     */
    public int deleteBatchJob(List<Map<String, Object>> list);


    public void insertBatchJob(BatchJob batchJob);

}
