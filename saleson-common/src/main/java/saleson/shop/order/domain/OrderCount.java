package saleson.shop.order.domain;

import com.onlinepowers.framework.util.NumberUtils;
import com.onlinepowers.framework.util.StringUtils;

public class OrderCount {
	private String key;
	private String label;
	private String count;
	
	/* 주문상태별 개수 */
	private int waitingDeposit; //입금대기
	private int pay; //결제완료
	private int waitingShipping; //배송준비중
	private int shipping; //배송중
	private int confirm; //배송완료,구매확정
	private int exchange; //교환
	private int cancel; //취소
	private int refund; //반품

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCount() {
		if (StringUtils.isNotEmpty(count)) {
			count = NumberUtils.formatNumber(Integer.parseInt(count), "#,##0");
		}
		
		return count;
	}
	
	public void setCount(String count) {
		this.count = count;
	}
	
	public int getWaitingDeposit() {
		return waitingDeposit;
	}
	public void setWaitingDeposit(int waitingDeposit) {
		this.waitingDeposit = waitingDeposit;
	}
	public int getPay() {
		return pay;
	}
	public void setPay(int pay) {
		this.pay = pay;
	}
	public int getWaitingShipping() {
		return waitingShipping;
	}
	public void setWaitingShipping(int waitingShipping) {
		this.waitingShipping = waitingShipping;
	}
	public int getShipping() {
		return shipping;
	}
	public void setShipping(int shipping) {
		this.shipping = shipping;
	}
	public int getConfirm() {
		return confirm;
	}
	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}
	public int getExchange() {
		return exchange;
	}
	public void setExchange(int exchange) {
		this.exchange = exchange;
	}
	public int getCancel() {
		return cancel;
	}
	public void setCancel(int cancel) {
		this.cancel = cancel;
	}
	public int getRefund() {
		return refund;
	}
	public void setRefund(int refund) {
		this.refund = refund;
	}
}
