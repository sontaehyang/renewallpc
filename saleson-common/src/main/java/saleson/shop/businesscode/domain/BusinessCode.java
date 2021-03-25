package saleson.shop.businesscode.domain;

import com.onlinepowers.framework.util.CommonUtils;

public class BusinessCode {
	private int shopConfigId = 1;
	private int businessCodeId;
	
	private String codeType;
	private String language = CommonUtils.getLanguage();
	
	private String id;
	private int ordering;
	private String useYn;
	
	private int previousOrdering;
	
	
	
	
	public int getPreviousOrdering() {
		return previousOrdering;
	}
	public void setPreviousOrdering(int previousOrdering) {
		this.previousOrdering = previousOrdering;
	}
	public int getBusinessCodeId() {
		return businessCodeId;
	}
	public void setBusinessCodeId(int businessCodeId) {
		this.businessCodeId = businessCodeId;
	}
	public int getShopConfigId() {
		return shopConfigId;
	}
	public void setShopConfigId(int shopConfigId) {
		this.shopConfigId = shopConfigId;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
	
}
