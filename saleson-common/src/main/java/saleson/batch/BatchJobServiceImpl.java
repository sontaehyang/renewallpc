package saleson.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.batch.domain.BatchJob;
import saleson.batch.support.BatchJobParam;

import java.util.List;
import java.util.Map;

@Service("batchJobService")
public class BatchJobServiceImpl implements BatchJobService {
    private static final Logger log = LoggerFactory.getLogger(BatchJobServiceImpl.class);

    @Autowired
    private BatchJobMapper batchJobMapper;

    @Override
    public List<BatchJob> getBatchJobList(BatchJobParam batchJobParam) {
        return batchJobMapper.getBatchJobList(batchJobParam);
    }

    @Override
    public BatchJob getBatchJobDetailList(BatchJob batchJob) {
        return batchJobMapper.getBatchJobDetailList(batchJob);
    }

    @Override
    public int updateBatchJob(BatchJob batchJob) {
        return batchJobMapper.updateBatchJob(batchJob);
    }

    @Override
    public int deleteBatchJob(List<Map<String, Object>> list) {

        for(int i=0; i<list.size(); i++){

            list.get(i).get("batchJobId");
            log.debug("#### batchJobId :  {}", list.get(i).get("batchJobId"));
            batchJobMapper.deleteBatchJob(list.get(i).get("batchJobId"));
        }
        return 0;

    }

    @Override
    public void insertBatchJob(BatchJob batchJob) {
        batchJobMapper.insertBatchJob(batchJob);
    }
}
