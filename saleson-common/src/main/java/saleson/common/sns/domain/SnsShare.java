package saleson.common.sns.domain;

import javax.validation.constraints.NotNull;

public class SnsShare {
	
	private int snsId;
	private String refCode;
	private int refId;
	private String snsType;
	private int snsCount;
	private String creationDate;
	
	public int getSnsId() {
		return snsId;
	}
	public void setSnsId(int snsId) {
		this.snsId = snsId;
	}
	public String getRefCode() {
		return refCode;
	}
	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}
	public int getRefId() {
		return refId;
	}
	public void setRefId(int refId) {
		this.refId = refId;
	}
	public String getSnsType() {
		return snsType;
	}
	public void setSnsType(String snsType) {
		this.snsType = snsType;
	}
	public int getSnsCount() {
		return snsCount;
	}
	public void setSnsCount(int snsCount) {
		this.snsCount = snsCount;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
}
