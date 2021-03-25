package saleson.shop.order.admin.support;

import java.util.HashMap;

import org.springframework.util.StringUtils;

import com.onlinepowers.framework.web.domain.SearchParam;
import saleson.common.utils.CommonUtils;

@SuppressWarnings("serial")
public class OrderAdminParam extends SearchParam {

	private String startDate;
	private String endDate;
	
	private String workSequence;
	private String dataStatusCode; // Detail쪽 상태 - 마스터쪽 상태는 그냥 삭제!!

	// 리스트 등록 처리용
	private String[] id;
	private HashMap<String, OrderAdminData> orderAdminMap;
	private String shippingPaymentType;
	// 리스트 등록 처리용	
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getWorkSequence() {
		return workSequence;
	}
	public void setWorkSequence(String workSequence) {
		this.workSequence = workSequence;
	}
	public String getDataStatusCode() {
		
		if (StringUtils.isEmpty(dataStatusCode)) {
			return "1";
		}
		
		return dataStatusCode;
	}
	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}
	public String[] getId() {
		return CommonUtils.copy(id);
	}
	public void setId(String[] id) {
		this.id = CommonUtils.copy(id);
	}
	public HashMap<String, OrderAdminData> getOrderAdminMap() {
		return orderAdminMap;
	}
	public void setOrderAdminMap(HashMap<String, OrderAdminData> orderAdminMap) {
		this.orderAdminMap = orderAdminMap;
	}
	public String getShippingPaymentType() {
		return shippingPaymentType;
	}
	public void setShippingPaymentType(String shippingPaymentType) {
		this.shippingPaymentType = shippingPaymentType;
	}
	
}
