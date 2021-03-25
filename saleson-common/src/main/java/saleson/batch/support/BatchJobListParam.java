package saleson.batch.support;

import com.onlinepowers.framework.util.ArrayUtils;
import com.onlinepowers.framework.web.domain.ListParam;
import saleson.common.utils.CommonUtils;
import saleson.batch.domain.BatchJob;

public class BatchJobListParam extends ListParam {

    private String[] batchJobId;

    public String[] getBatchJobId() {
        return CommonUtils.copy(batchJobId);
    }

    public void setBatchJobId(String[] batchJobId) {
        this.batchJobId = CommonUtils.copy(batchJobId);
    }

    public BatchJob getBatchJobId(int index) {

        BatchJob batchJob = new BatchJob();

        batchJob.setBatchJobId(ArrayUtils.get(getBatchJobId(),index));

        return batchJob;
    }
}
