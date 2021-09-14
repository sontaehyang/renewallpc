package saleson.shop.order.pg;

public class RentalPay {

    // 렌탈페이용 파라미터
    public long userId;
    public String sessionId;
    public String buyRentalPay = "N";
    public String rentalTotAmt;
    public String rentalMonthAmt;
    public String rentalPartnershipAmt;
    public String rentalPer;
    private String createdDate;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getBuyRentalPay() {
        return buyRentalPay;
    }
    public void setBuyRentalPay(String buyRentalPay) {
        this.buyRentalPay = buyRentalPay;
    }
    public String getRentalTotAmt() {
        return rentalTotAmt;
    }
    public void setRentalTotAmt(String rentalTotAmt) {
        this.rentalTotAmt = rentalTotAmt;
    }
    public String getRentalMonthAmt() {
        return rentalMonthAmt;
    }
    public void setRentalMonthAmt(String rentalMonthAmt) {
        this.rentalMonthAmt = rentalMonthAmt;
    }
    public String getRentalPartnershipAmt() {
        return rentalPartnershipAmt;
    }
    public void setRentalPartnershipAmt(String rentalPartnershipAmt) {
        this.rentalPartnershipAmt = rentalPartnershipAmt;
    }
    public String getRentalPer() {
        return rentalPer;
    }
    public void setRentalPer(String rentalPer) {
        this.rentalPer = rentalPer;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
