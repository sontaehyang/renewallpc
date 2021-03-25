package saleson.shop.mall.auction.domain;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import com.onlinepowers.framework.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.shop.mall.domain.MallBase;

@XmlRootElement(name="CancelApproval")
@XmlAccessorType (XmlAccessType.FIELD)
public class CancelApproval extends MallBase {

	private static final Logger log = LoggerFactory.getLogger(CancelApproval.class);

	@XmlAttribute(name="RequestDate")
	private String requestDate; // 요청일
	
	@XmlAttribute(name="ItemNo")
	private String itemNo; // 상품 번호
	
	@XmlAttribute(name="OrderNo")
	private int orderNo; // 주문 번호
	
	@XmlAttribute(name="ItemName")
	private String itemName; // 상품명
	
	@XmlAttribute(name="AwardQty")
	private int awardQty; // 수량
	
	@XmlAttribute(name="AwardAmount")
	private BigDecimal awardAmount; // 금액
	
	@XmlAttribute(name="BuyerId")
	private String buyerId; // 구매자 아이디
	
	@XmlAttribute(name="BuyerName")
	private String buyerName; // 구매자명
	
	@XmlAttribute(name="CancReason")
	private String cancReason; // 취소 사유
	
	@XmlAttribute(name="CancReasonDetail")
	private String cancReasonDetail; // 취소 사유 상세
	
	@XmlAttribute(name="BuyerMobile")
	private String buyerMobile; // 전화번호
	
	@XmlAttribute(name="BuyerPhone")
	private String buyerPhone; // 전화번호
	
	@XmlAttribute(name="RecpDate")
	private String recpDate; // 결제 확인일
	
	public String getRequestDate() {
		try {
			
			if (StringUtils.isNotEmpty(requestDate)) {
				if (requestDate.indexOf(".") != -1) {
					requestDate = requestDate.substring(0, requestDate.indexOf("."));
					return requestDate.replaceAll("T", " ");
				}
			}
			
			return DateUtils.convertDate(requestDate, "yyyy-MM-dd a hh:mm:ss", "yyyy-MM-dd HH:mm:ss", "");
		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getAwardQty() {
		return awardQty;
	}

	public void setAwardQty(int awardQty) {
		this.awardQty = awardQty;
	}

	public BigDecimal getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getCancReason() {
		return cancReason;
	}

	public void setCancReason(String cancReason) {
		this.cancReason = cancReason;
	}

	public String getCancReasonDetail() {
		return cancReasonDetail;
	}

	public void setCancReasonDetail(String cancReasonDetail) {
		this.cancReasonDetail = cancReasonDetail;
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public String getRecpDate() {
		return recpDate;
	}

	public void setRecpDate(String recpDate) {
		this.recpDate = recpDate;
	}

}
