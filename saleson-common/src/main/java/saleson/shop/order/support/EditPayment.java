package saleson.shop.order.support;

import java.util.List;
import saleson.common.utils.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditPayment {

	private String orderCode;
	private int orderSequence;

	private int[] deletePaymentIds;

	private List<ChangePayment> changePayments;
	private List<NewPayment> newPayments;
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getOrderSequence() {
		return orderSequence;
	}
	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}
	public List<ChangePayment> getChangePayments() {
		return changePayments;
	}
	public void setChangePayments(List<ChangePayment> changePayments) {
		this.changePayments = changePayments;
	}
	public List<NewPayment> getNewPayments() {
		return newPayments;
	}
	public void setNewPayments(List<NewPayment> newPayments) {
		this.newPayments = newPayments;
	}
	public int[] getDeletePaymentIds() {
		return CommonUtils.copy(deletePaymentIds);
	}
	public void setDeletePaymentIds(int[] deletePaymentIds) {
		this.deletePaymentIds = CommonUtils.copy(deletePaymentIds);
	}
	
	// 환불 처리 화면에서 필요한 항목들...
	private String returnBankName;
	private String returnBankInName;
	private String returnBankVirtualNo;


	// [SKC] 결제 수단 부분 취소가 아닌 은행입금으로 환불 처리
	private String refundBankName;
	private String refundAccountNumber;
	private String refundAccountName;
	private Integer refundAmount;

	
	//kspay에서 환불처리시 필요
	private String goodName;

	// nicepay request 전달용
	private HttpServletRequest request;
	private HttpServletResponse response;

    private String refundReason;

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }
    public String getReturnBankName() {
		return returnBankName;
	}
	public void setReturnBankName(String returnBankName) {
		this.returnBankName = returnBankName;
	}
	public String getReturnBankInName() {
		return returnBankInName;
	}
	public void setReturnBankInName(String returnBankInName) {
		this.returnBankInName = returnBankInName;
	}
	public String getReturnBankVirtualNo() {
		return returnBankVirtualNo;
	}
	public void setReturnBankVirtualNo(String returnBankVirtualNo) {
		this.returnBankVirtualNo = returnBankVirtualNo;
	}
	// 환불 처리 화면에서 필요한 항목들...
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}


	public String getRefundBankName() {
		return refundBankName;
	}

	public void setRefundBankName(String refundBankName) {
		this.refundBankName = refundBankName;
	}

	public String getRefundAccountNumber() {
		return refundAccountNumber;
	}

	public void setRefundAccountNumber(String refundAccountNumber) {
		this.refundAccountNumber = refundAccountNumber;
	}

	public String getRefundAccountName() {
		return refundAccountName;
	}

	public void setRefundAccountName(String refundAccountName) {
		this.refundAccountName = refundAccountName;
	}

	public Integer getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Integer refundAmount) {
		this.refundAmount = refundAmount;
	}

    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    // 현금영수증 재발급 처리 여부
    private boolean cashbillReissueFlag = false;

    public boolean isCashbillReissueFlag() {
        return cashbillReissueFlag;
    }

    public void setCashbillReissueFlag(boolean cashbillReissueFlag) {
        this.cashbillReissueFlag = cashbillReissueFlag;
    }

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}
