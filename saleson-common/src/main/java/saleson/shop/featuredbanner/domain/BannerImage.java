package saleson.shop.featuredbanner.domain;

import org.springframework.web.multipart.MultipartFile;

public class BannerImage {
	private String code;
	private MultipartFile uploadFile;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
}
