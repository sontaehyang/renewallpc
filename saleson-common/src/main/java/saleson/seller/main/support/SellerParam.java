package saleson.seller.main.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class SellerParam extends SearchParam {
	private String statusCode;
	private long sellerId;
	
	public SellerParam() {};
	
	public String getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public long getSellerId() {
		return sellerId;
	}
	
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	
	public SellerParam(String conditionType) {
		
		setConditionType(conditionType);
	}

	
	
}
