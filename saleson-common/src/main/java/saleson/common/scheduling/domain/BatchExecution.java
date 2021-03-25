package saleson.common.scheduling.domain;

import com.onlinepowers.framework.util.DateUtils;

public class BatchExecution {

	private String batchType;
	private String executionDate;
	private String startTime;
	private String endTime = "";
	private String result = "0";
	private String message = "";
	
	public BatchExecution() {}
	public BatchExecution(String batchType, String executionDate) {
		this.batchType = batchType;
		this.executionDate = executionDate;
		this.startTime = DateUtils.getToday("HH:mm:ss");
	}
	
	public String getBatchType() {
		return batchType;
	}
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	public String getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
