package saleson.shop.order.pg.payco.domain;

import com.onlinepowers.framework.util.JsonViewUtils;
import org.springframework.util.StringUtils;
import saleson.common.config.SalesonProperty;

import java.util.HashMap;

public class ReservationResponse {
	private String code;
	private String message;
	private String reserveOrderNo;
	private String sellerOrderReferenceKey;
	private String totalPaymentAmt;
	private String discountAmt;
	private String pointAmt;
	private String paymentCertifyToken;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getReserveOrderNo() {
		return reserveOrderNo;
	}
	public void setReserveOrderNo(String reserveOrderNo) {
		this.reserveOrderNo = reserveOrderNo;
	}
	public String getSellerOrderReferenceKey() {
		return sellerOrderReferenceKey;
	}
	public void setSellerOrderReferenceKey(String sellerOrderReferenceKey) {
		this.sellerOrderReferenceKey = sellerOrderReferenceKey;
	}
	public String getTotalPaymentAmt() {
		return totalPaymentAmt;
	}
	public void setTotalPaymentAmt(String totalPaymentAmt) {
		this.totalPaymentAmt = totalPaymentAmt;
	}
	public String getDiscountAmt() {
		return discountAmt;
	}
	public void setDiscountAmt(String discountAmt) {
		this.discountAmt = discountAmt;
	}
	public String getPointAmt() {
		return pointAmt;
	}
	public void setPointAmt(String pointAmt) {
		this.pointAmt = pointAmt;
	}
	public String getPaymentCertifyToken() {
		return paymentCertifyToken;
	}
	public void setPaymentCertifyToken(String paymentCertifyToken) {
		this.paymentCertifyToken = paymentCertifyToken;
	}
	
	public String getPayApprovalJsonData() {
		
		if (StringUtils.isEmpty(this.paymentCertifyToken)) {
			return null;
		}
		
		HashMap<String, Object> map =  new HashMap<>();
		
		map.put("sellerKey", SalesonProperty.getPaycoSellerKey());
		map.put("reserveOrderNo", this.reserveOrderNo);
		map.put("sellerOrderReferenceKey", this.sellerOrderReferenceKey);
		map.put("totalPaymentAmt", this.totalPaymentAmt);
		map.put("paymentCertifyToken", this.paymentCertifyToken);
		
		
		return JsonViewUtils.objectToJson(map);
	}
	
	
}
