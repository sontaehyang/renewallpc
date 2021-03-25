package saleson.common.file.domain;

public class FileParam {
	private String key;
	private String token;
	private String uploadType;
	private String maxUploadCount;
	private String maxUploadSize;
	private String availableUploadCount;
	private String separatorId;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getMaxUploadCount() {
		return maxUploadCount;
	}
	public void setMaxUploadCount(String maxUploadCount) {
		this.maxUploadCount = maxUploadCount;
	}
	
	public String getMaxUploadSize() {
		return maxUploadSize;
	}
	public void setMaxUploadSize(String maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}
	public String getAvailableUploadCount() {
		return availableUploadCount;
	}
	public void setAvailableUploadCount(String availableUploadCount) {
		this.availableUploadCount = availableUploadCount;
	}
	public String getSeparatorId() {
		return separatorId;
	}
	public void setSeparatorId(String separatorId) {
		this.separatorId = separatorId;
	}
	
	
	
	
}
