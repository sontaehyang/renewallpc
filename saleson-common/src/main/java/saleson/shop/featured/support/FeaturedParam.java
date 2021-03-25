package saleson.shop.featured.support;

import com.onlinepowers.framework.web.domain.SearchParam;
import org.springframework.security.core.SpringSecurityCoreVersion;
import saleson.common.utils.CommonUtils;

import java.util.List;


public class FeaturedParam extends SearchParam{

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	private String featuredType;
	private String featuredName;
	private String featuredSimpleContent;
	
	private int featuredId;
	private int featuredClass;
	private int featuredIds[];
	
	private String featuredCodeChecked;
	private String featuredCode;
	private String featuredUrl;
	private String featuredFlag = "";
	private String displayListFlag;
	private String isEvent = "";
	
	private String progression;
	
	/* fileType = 1,섬네일 / 2,대표이미지 */
	private int fileType;
	
	private String prodState = "";
	
	private int featuredNum;

	// 전용등급 구분
	private List<String> privateTypes;
	private String privateType; 		// 전용등급

	private String isAdult;
	
	public int getFeaturedNum() {
		return featuredNum;
	}
	public void setFeaturedNum(int featuredNum) {
		this.featuredNum = featuredNum;
	}
	public String getFeaturedType() {
		return featuredType;
	}
	public void setFeaturedType(String featuredType) {
		this.featuredType = featuredType;
	}
	public String getFeaturedFlag() {
		return featuredFlag;
	}
	public void setFeaturedFlag(String featuredFlag) {
		this.featuredFlag = featuredFlag;
	}
	public String getFeaturedName() {
		return featuredName;
	}
	public void setFeaturedName(String featuredName) {
		this.featuredName = featuredName;
	}
	public String getFeaturedSimpleContent() {
		return featuredSimpleContent;
	}
	public void setFeaturedSimpleContent(String featuredSimpleContent) {
		this.featuredSimpleContent = featuredSimpleContent;
	}
	public int getFeaturedId() {
		return featuredId;
	}
	public void setFeaturedId(int featuredId) {
		this.featuredId = featuredId;
	}
	public int getFeaturedClass() {
		return featuredClass;
	}
	public void setFeaturedClass(int featuredClass) {
		this.featuredClass = featuredClass;
	}
	public int[] getFeaturedIds() {
		return CommonUtils.copy(featuredIds);
	}
	public void setFeaturedIds(int[] featuredIds) {
		this.featuredIds = CommonUtils.copy(featuredIds);
	}
	
	public String getFeaturedIdResult(){
		String result = "";
		
		if(featuredIds != null){
			
			for(int i = 0; i < featuredIds.length; i++){
				if(i == 0){
					result += "'"+featuredIds[i]+"'";
				}else {
					result += ", '"+featuredIds[i]+"'";
				}
			}
		}
		return result;
	}
	public String getFeaturedCodeChecked() {
		return featuredCodeChecked;
	}
	public void setFeaturedCodeChecked(String featuredCodeChecked) {
		this.featuredCodeChecked = featuredCodeChecked;
	}
	public String getFeaturedCode() {
		return featuredCode;
	}
	public void setFeaturedCode(String featuredCode) {
		this.featuredCode = featuredCode;
	}
	public String getFeaturedUrl() {
		return featuredUrl;
	}
	public void setFeaturedUrl(String featuredUrl) {
		this.featuredUrl = featuredUrl;
	}
	public String getDisplayListFlag() {
		return displayListFlag;
	}
	public void setDisplayListFlag(String displayListFlag) {
		this.displayListFlag = displayListFlag;
	}
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	public String getIsEvent() {
		return isEvent;
	}
	public void setIsEvent(String isEvent) {
		this.isEvent = isEvent;
	}
	public String getProdState() {
		return prodState;
	}
	public void setProdState(String prodState) {
		this.prodState = prodState;
	}
	public String getProgression() {
		return progression;
	}
	public void setProgression(String progression) {
		this.progression = progression;
	}

	public List<String> getPrivateTypes() {
		return privateTypes;
	}

	public void setPrivateTypes(List<String> privateTypes) {
		this.privateTypes = privateTypes;
	}

	public String getPrivateType() {
		return privateType;
	}

	public void setPrivateType(String privateType) {
		this.privateType = privateType;
	}

	public String getIsAdult() {
		return isAdult;
	}

	public void setIsAdult(String isAdult) {
		this.isAdult = isAdult;
	}
}

