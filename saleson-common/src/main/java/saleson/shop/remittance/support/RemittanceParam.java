package saleson.shop.remittance.support;

import java.util.HashMap;

import org.springframework.util.StringUtils;

import saleson.common.Const;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.SellerUtils;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class RemittanceParam extends SearchParam {
	
	private String startDate;
	private String endDate;
	
	private long sellerId;
	private String viewTarget;
	
	private String[] id;
	
	// 정산 데이터 수정 - 상품
	private HashMap<String, EditItemRemittance> editItemRemittanceMap;
	private HashMap<String, EditShippingRemittance> editShippingRemittanceMap;
	private HashMap<String, EditAddPaymentRemittance> editAddPaymentRemittanceMap;
	// 정산 데이터 수정 - 상품
	
	// 정산 마감용
	private HashMap<String, FinishingRemittance> finishingRemittanceMap;
	private double confirmAmount;
	private int remittanceId;
	// 정산 마감용
	
	private String statusCode;
	
	public int getRemittanceId() {
		return remittanceId;
	}
	public void setRemittanceId(int remittanceId) {
		this.remittanceId = remittanceId;
	}
	public double getConfirmAmount() {
		return confirmAmount;
	}
	public void setConfirmAmount(double confirmAmount) {
		this.confirmAmount = confirmAmount;
	}
	public HashMap<String, EditShippingRemittance> getEditShippingRemittanceMap() {
		return editShippingRemittanceMap;
	}
	public void setEditShippingRemittanceMap(
			HashMap<String, EditShippingRemittance> editShippingRemittanceMap) {
		this.editShippingRemittanceMap = editShippingRemittanceMap;
	}
	public HashMap<String, FinishingRemittance> getFinishingRemittanceMap() {
		return finishingRemittanceMap;
	}
	public void setFinishingRemittanceMap(
			HashMap<String, FinishingRemittance> finishingRemittanceMap) {
		this.finishingRemittanceMap = finishingRemittanceMap;
	}
	public String getStatusCode() {
		
		if (StringUtils.isEmpty(statusCode)) {
			return "1";
		}
		
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getViewTarget() {
		
		if (StringUtils.isEmpty(viewTarget)) {
			return "ITEM";
		}
		
		return viewTarget.toUpperCase();
	}
	public String[] getId() {
		return CommonUtils.copy(id);
	}
	public void setId(String[] id) {
		this.id = CommonUtils.copy(id);
	}
	public HashMap<String, EditItemRemittance> getEditItemRemittanceMap() {
		return editItemRemittanceMap;
	}
	public void setEditItemRemittanceMap(
			HashMap<String, EditItemRemittance> editItemRemittanceMap) {
		this.editItemRemittanceMap = editItemRemittanceMap;
	}
	public void setViewTarget(String viewTarget) {
		this.viewTarget = viewTarget;
	}
	public String getStartDate() {
		
		if (StringUtils.isEmpty(startDate)) {
			return DateUtils.getToday(Const.DATE_FORMAT);
		}
		
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		
		if (StringUtils.isEmpty(startDate)) {
			return DateUtils.getToday(Const.DATE_FORMAT);
		}
		
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public long getDefaultOpmanagerSellerId() {
		return SellerUtils.DEFAULT_OPMANAGER_SELLER_ID;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public HashMap<String, EditAddPaymentRemittance> getEditAddPaymentRemittanceMap() {
		return editAddPaymentRemittanceMap;
	}
	public void setEditAddPaymentRemittanceMap(
			HashMap<String, EditAddPaymentRemittance> editAddPaymentRemittanceMap) {
		this.editAddPaymentRemittanceMap = editAddPaymentRemittanceMap;
	}
}
