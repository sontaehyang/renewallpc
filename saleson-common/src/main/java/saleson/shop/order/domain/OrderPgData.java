package saleson.shop.order.domain;

import java.util.List;

import saleson.shop.order.pg.payco.domain.CancelProduct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class OrderPgData {
	
	private String approvalType;
	private boolean isSuccess;
	private int orderPgDataId;
	private String orderCode;
	private String pgServiceType;
	private String pgServiceMid;
	private String pgServiceKey;
	private String pgPaymentType;
	
	private String pgKey;
	private String pgAuthCode;
	private String pgProcInfo;
	private String errorMessage;
	private String message;
	
	private int pgAmount;
	private String cbtrno;	// 2017-05-25 Jun-Eu Son Kspay 현금영수증 거래번호

    private String payDate;

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	public int getPgAmount() {
		return pgAmount;
	}
	public void setPgAmount(int pgAmount) {
		this.pgAmount = pgAmount;
	}


	// 부분취소 가능여부 (Y:가능)
	private String partCancelFlag;
	private String partCancelDetail;
	
	private boolean paymentCompletion; // 결제 완료 여부 - 페이코에서 사용
	private List<CancelProduct> paycoCancelProducts; // 페이코 주문취소시 사용하는 데이터
	
	// 가상계좌 환불 계좌 정보
	private String returnAccountNo;
	private String returnBankName;
	private String returnName;
	
	private String bankVirtualNo;
	private String bankInName;
	private String bankCode;
	private String bankName;
	private String bankDate;

	private String RcptType;
	
	// 에스크로 진행상태
	private String escrowStatus;

	private HttpServletRequest request;
	private HttpServletResponse response;

	public String getRcptType() {
		return RcptType;
	}
	public void setRcptType(String rcptType) {
		RcptType = rcptType;
	}
	public String getBankVirtualNo() {
		return bankVirtualNo;
	}
	public void setBankVirtualNo(String bankVirtualNo) {
		this.bankVirtualNo = bankVirtualNo;
	}
	public String getBankInName() {
		return bankInName;
	}
	public void setBankInName(String bankInName) {
		this.bankInName = bankInName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankDate() {
		return bankDate;
	}
	public void setBankDate(String bankDate) {
		this.bankDate = bankDate;
	}
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public String getReturnAccountNo() {
		return returnAccountNo;
	}
	public void setReturnAccountNo(String returnAccountNo) {
		this.returnAccountNo = returnAccountNo;
	}
	
	public String getReturnBankName() {
		return returnBankName;
	}
	public void setReturnBankName(String returnBankName) {
		this.returnBankName = returnBankName;
	}
	public List<CancelProduct> getPaycoCancelProducts() {
		return paycoCancelProducts;
	}
	public void setPaycoCancelProducts(List<CancelProduct> paycoCancelProducts) {
		this.paycoCancelProducts = paycoCancelProducts;
	}
	public boolean isPaymentCompletion() {
		return paymentCompletion;
	}
	public void setPaymentCompletion(boolean paymentCompletion) {
		this.paymentCompletion = paymentCompletion;
	}
	private int remainAmount; // 취소전 남은금액
	private int cancelAmount; // 취소 금액
	private int cancelTexFreeAmount; // 면새대상금액
	private String cancelReason; // 취소 사유
    private Boolean amountModification;

    public Boolean isAmountModification() {
        return amountModification;
    }

    public void setAmountModification(Boolean amountModification) {
        this.amountModification = amountModification;
    }

    public int getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(int remainAmount) {
		this.remainAmount = remainAmount;
	}
	public int getCancelAmount() {
		return cancelAmount;
	}
	public void setCancelAmount(int cancelAmount) {
		this.cancelAmount = cancelAmount;
	}
	public int getCancelTexFreeAmount() {
		return cancelTexFreeAmount;
	}
	public void setCancelTexFreeAmount(int cancelTexFreeAmount) {
		this.cancelTexFreeAmount = cancelTexFreeAmount;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getPartCancelFlag() {
		return partCancelFlag;
	}
	public void setPartCancelFlag(String partCancelFlag) {
		this.partCancelFlag = partCancelFlag;
	}
	public String getPartCancelDetail() {
		return partCancelDetail;
	}
	public void setPartCancelDetail(String partCancelDetail) {
		this.partCancelDetail = partCancelDetail;
	}
	public String getPgServiceType() {
		return pgServiceType;
	}
	public void setPgServiceType(String pgServiceType) {
		this.pgServiceType = pgServiceType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public int getOrderPgDataId() {
		return orderPgDataId;
	}
	public void setOrderPgDataId(int orderPgDataId) {
		this.orderPgDataId = orderPgDataId;
	}
	public void setPgProcInfo(String pgProcInfo) {
		this.pgProcInfo = pgProcInfo;
	}
	public String getPgKey() {
		return pgKey;
	}
	public void setPgKey(String pgKey) {
		this.pgKey = pgKey;
	}
	public String getPgAuthCode() {
		return pgAuthCode;
	}
	public void setPgAuthCode(String pgAuthCode) {
		this.pgAuthCode = pgAuthCode;
	}
	public String getPgProcInfo() {
		return pgProcInfo;
	}
	public String getPgServiceMid() {
		return pgServiceMid;
	}
	public void setPgServiceMid(String pgServiceMid) {
		this.pgServiceMid = pgServiceMid;
	}
	public String getPgServiceKey() {
		return pgServiceKey;
	}
	public void setPgServiceKey(String pgServiceKey) {
		this.pgServiceKey = pgServiceKey;
	}
	public String getPgPaymentType() {
		return pgPaymentType;
	}
	public void setPgPaymentType(String pgPaymentType) {
		this.pgPaymentType = pgPaymentType;
	}
	public String getReturnName() {
		return returnName;
	}
	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}
	public String getCbtrno() {
		return cbtrno;
	}
	public void setCbtrno(String cbtrno) {
		this.cbtrno = cbtrno;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getEscrowStatus() {
		return escrowStatus;
	}
	public void setEscrowStatus(String escrowStatus) {
		this.escrowStatus = escrowStatus;
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
