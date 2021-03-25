package saleson.shop.order.api;

public class PaymentInfo {

    private String approvalType; // 결제수단
    private String approvalTypeLabel;
    private String paymentType; // 결제구분 (1:결제, 2:취소)
    private int amount; // 금액
    private int remainingAmount; // 잔여액
    private int cancelAmount; // 취소금액
    private String payDate; // 결제일
    private String bankVirtualNo; // 계좌번호
    private String bankInName; // 입금예정자
    private String payInfo; // 결제정보

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    private String bankDate; // 입금예정일(만료일)

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(int remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public int getCancelAmount() {
        return cancelAmount;
    }

    public void setCancelAmount(int cancelAmount) {
        this.cancelAmount = cancelAmount;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
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

    public String getApprovalTypeLabel() {
        return approvalTypeLabel;
    }

    public void setApprovalTypeLabel(String approvalTypeLabel) {
        this.approvalTypeLabel = approvalTypeLabel;
    }

    public String getBankDate() { return bankDate; }

    public void setBankDate(String bankDate) { this.bankDate = bankDate; }
}
