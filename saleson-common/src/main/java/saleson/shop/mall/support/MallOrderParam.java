package saleson.shop.mall.support;

import java.util.List;

import org.springframework.util.StringUtils;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.domain.SearchParam;
import saleson.common.utils.CommonUtils;

@SuppressWarnings("serial")
public class MallOrderParam extends SearchParam {
	
	private String searchStartDate;
	private String searchStartDateTime;
	
	private String searchEndDate;
	private String searchEndDateTime;
	
	private int mallOrderId;
	
	// 배송정보 입력
	private String[] id;
	private String[] mallOrderIds;
	private String[] deliveryCompanyCodes;
	private String[] deliveryNumbers;
	
	// 교환 배송용
	private String[] exchangeKeys;
	
	// 상품 검색용
	private String productCode;
	private int mallConfigId;
	
	private String itemUserCode;
	private String matchedOptions;
	
	private String[] itemUserCodes;
	private List<int[]> matchedOptionIds;
	
	private String claimCode;

	public String[] getExchangeKeys() {
		return CommonUtils.copy(exchangeKeys);
	}

	public void setExchangeKeys(String[] exchangeKeys) {
		this.exchangeKeys = CommonUtils.copy(exchangeKeys);
	}

	public String getClaimCode() {
		return claimCode;
	}

	public void setClaimCode(String claimCode) {
		this.claimCode = claimCode;
	}

	public String getMatchedOptions() {
		return matchedOptions;
	}

	public void setMatchedOptions(String matchedOptions) {
		this.matchedOptions = matchedOptions;
	}

	public List<int[]> getMatchedOptionIds() {
		return matchedOptionIds;
	}

	public void setMatchedOptionIds(List<int[]> matchedOptionIds) {
		this.matchedOptionIds = matchedOptionIds;
	}

	public String getItemUserCode() {
		return itemUserCode;
	}

	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}

	public String[] getItemUserCodes() {
		return CommonUtils.copy(itemUserCodes);
	}

	public void setItemUserCodes(String[] itemUserCodes) {
		this.itemUserCodes = CommonUtils.copy(itemUserCodes);
	}

	public int getMallConfigId() {
		return mallConfigId;
	}

	public void setMallConfigId(int mallConfigId) {
		this.mallConfigId = mallConfigId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String[] getMallOrderIds() {
		return CommonUtils.copy(mallOrderIds);
	}

	public void setMallOrderIds(String[] mallOrderIds) {
		this.mallOrderIds = CommonUtils.copy(mallOrderIds);
	}

	public String[] getDeliveryCompanyCodes() {
		return CommonUtils.copy(deliveryCompanyCodes);
	}

	public void setDeliveryCompanyCodes(String[] deliveryCompanyCodes) {
		this.deliveryCompanyCodes = CommonUtils.copy(deliveryCompanyCodes);
	}

	public String[] getDeliveryNumbers() {
		return CommonUtils.copy(deliveryNumbers);
	}

	public void setDeliveryNumbers(String[] deliveryNumbers) {
		this.deliveryNumbers = CommonUtils.copy(deliveryNumbers);
	}

	public int getMallOrderId() {
		return mallOrderId;
	}

	public void setMallOrderId(int mallOrderId) {
		this.mallOrderId = mallOrderId;
	}

	public String[] getId() {
		return CommonUtils.copy(id);
	}

	public void setId(String[] id) {
		this.id = CommonUtils.copy(id);
	}

	public String getSearchStartDate() {
		
		if (StringUtils.isEmpty(searchStartDate)) {
			return DateUtils.addDay(DateUtils.getToday(), -7);
		}
		
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		
		if (StringUtils.isEmpty(searchEndDate)) {
			return DateUtils.getToday();
		}
		
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public String getSearchStartDateTime() {
		if (StringUtils.isEmpty(searchStartDateTime)) {
			return "00";
		}
		
		return searchStartDateTime;
	}

	public void setSearchStartDateTime(String searchStartDateTime) {
		this.searchStartDateTime = searchStartDateTime;
	}

	public String getSearchEndDateTime() {
		if (StringUtils.isEmpty(searchEndDateTime)) {
			return "23";
		}
		
		return searchEndDateTime;
	}

	public void setSearchEndDateTime(String searchEndDateTime) {
		this.searchEndDateTime = searchEndDateTime;
	}
}
