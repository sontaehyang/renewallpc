package saleson.batch.domain;

public class BatchJob {

    private String id;
    private String batchJobId;
    private String jobName;
    private String jobMethod;
    private String triggerType;
    private String triggerRepeatSeconds;
    private String triggerCronExpression;
    private String batchStatus;
    private String batchExcuteDate;
    private String batchApplyFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchJobId() {
        return batchJobId;
    }

    public void setBatchJobId(String batchJobId) {
        this.batchJobId = batchJobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobMethod() {
        return jobMethod;
    }

    public void setJobMethod(String jobMethod) {
        this.jobMethod = jobMethod;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getTriggerRepeatSeconds() {
        return triggerRepeatSeconds;
    }

    public void setTriggerRepeatSeconds(String triggerRepeatSeconds) {
        this.triggerRepeatSeconds = triggerRepeatSeconds;
    }

    public String getTriggerCronExpression() {
        return triggerCronExpression;
    }

    public void setTriggerCronExpression(String triggerCronExpression) {
        this.triggerCronExpression = triggerCronExpression;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getBatchExcuteDate() {
        return batchExcuteDate;
    }

    public void setBatchExcuteDate(String batchExcuteDate) {
        this.batchExcuteDate = batchExcuteDate;
    }

    public String getBatchApplyFlag() {
        return batchApplyFlag;
    }

    public void setBatchApplyFlag(String batchApplyFlag) {
        this.batchApplyFlag = batchApplyFlag;
    }
}
