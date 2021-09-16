package saleson.shop.order.domain;

public class OrderRental {

    int orderRentalId;
    String orderCode;
    String rentalItemName;
    String rentalPer;
    int rentalTotAmt;
    int rentalMonthAmt;
    String orderNo;
    String apprKey;
    String resultTimes;
    String createDate;

    public int getOrderRentalId() {
        return orderRentalId;
    }

    public void setOrderRentalId(int orderRentalId) {
        this.orderRentalId = orderRentalId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getRentalItemName() {
        return rentalItemName;
    }

    public void setRentalItemName(String rentalItemName) {
        this.rentalItemName = rentalItemName;
    }

    public String getRentalPer() {
        return rentalPer;
    }

    public void setRentalPer(String rentalPer) {
        this.rentalPer = rentalPer;
    }

    public int getRentalTotAmt() {
        return rentalTotAmt;
    }

    public void setRentalTotAmt(int rentalTotAmt) {
        this.rentalTotAmt = rentalTotAmt;
    }

    public int getRentalMonthAmt() {
        return rentalMonthAmt;
    }

    public void setRentalMonthAmt(int rentalMonthAmt) {
        this.rentalMonthAmt = rentalMonthAmt;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getApprKey() {
        return apprKey;
    }

    public void setApprKey(String apprKey) {
        this.apprKey = apprKey;
    }

    public String getResultTimes() {
        return resultTimes;
    }

    public void setResultTimes(String resultTimes) {
        this.resultTimes = resultTimes;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
