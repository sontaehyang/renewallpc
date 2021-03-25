package saleson.shop.display.support;

import org.springframework.util.StringUtils;

import com.onlinepowers.framework.web.domain.SearchParam;

import java.util.List;

@SuppressWarnings("serial")
public class DisplayParam extends SearchParam {

	private String viewType;
	private String displayGroupCode;
	private String displaySubCode;
	private String viewTarget = "ALL";
	private int ordering;

	private List<String> displayGroupCodes;
	private List<String> displaySubCodes;

	public String getQueryString() {
		
		String s = "?viewType=" + getViewType();
		s += "&displayGroupCode=" + getDisplayGroupCode();
		s += "&displaySubCode=" + getDisplaySubCode();
		s += "&viewTarget=" + getViewTarget();
		
		return s;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getDisplayGroupCode() {
		return displayGroupCode;
	}

	public void setDisplayGroupCode(String displayGroupCode) {
		this.displayGroupCode = displayGroupCode;
	}

	public String getDisplaySubCode() {
		return displaySubCode;
	}

	public void setDisplaySubCode(String displaySubCode) {
		this.displaySubCode = displaySubCode;
	}

	public String getViewTarget() {
		return viewTarget;
	}

	public void setViewTarget(String viewTarget) {
		this.viewTarget = viewTarget;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public List<String> getDisplayGroupCodes() {
		return displayGroupCodes;
	}

	public void setDisplayGroupCodes(List<String> displayGroupCodes) {
		this.displayGroupCodes = displayGroupCodes;
	}

	public List<String> getDisplaySubCodes() {
		return displaySubCodes;
	}

	public void setDisplaySubCodes(List<String> displaySubCodes) {
		this.displaySubCodes = displaySubCodes;
	}
}
