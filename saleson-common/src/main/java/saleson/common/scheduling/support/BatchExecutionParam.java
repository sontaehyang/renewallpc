package saleson.common.scheduling.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class BatchExecutionParam extends SearchParam {
	private String batchType;
	private String executionDate;
	
	public BatchExecutionParam() {}
	
	
	public BatchExecutionParam(String batchType, String executionDate) {
		this.batchType = batchType;
		this.executionDate = executionDate;
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
}
