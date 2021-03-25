package saleson.shop.code.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class CodeParam extends SearchParam {

	private String codeType;
	private String language;
	private String id;
	private String label;
	private String detail;
	private Integer ordering;
	private String useYn;
	
	private String upId;
	private String codeValue;
	private String extentionCode;
	private String mappingCode;
	
	private String whereCodeType;
	private String whereId;
	
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
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getOrdering() {
		return ordering;
	}
	public void setOrdering(Integer ordering) {
		this.ordering = ordering;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getUpId() {
		return upId;
	}
	public void setUpId(String upId) {
		this.upId = upId;
	}
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public String getExtentionCode() {
		return extentionCode;
	}
	public void setExtentionCode(String extentionCode) {
		this.extentionCode = extentionCode;
	}
	public String getMappingCode() {
		return mappingCode;
	}
	public void setMappingCode(String mappingCode) {
		this.mappingCode = mappingCode;
	}
	public String getWhereCodeType() {
		return whereCodeType;
	}
	public void setWhereCodeType(String whereCodeType) {
		this.whereCodeType = whereCodeType;
	}
	public String getWhereId() {
		return whereId;
	}
	public void setWhereId(String whereId) {
		this.whereId = whereId;
	}
	
	
}
