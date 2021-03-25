package saleson.shop.item.support;

import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serializable;

public class AsyncReport implements Serializable {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public static final String NOTHING = "NOTHING";
	public static final String WORKING = "WORKING";
	public static final String COMPLETE = "COMPLETE";
	
	
	private String type;
	private String message;
	private String status = NOTHING;
	
	public AsyncReport() {}
	public AsyncReport(String type) {
		this.type = type;
	}
	
	public AsyncReport(String type, String status) {
		this.type = type;
		this.status = status;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
