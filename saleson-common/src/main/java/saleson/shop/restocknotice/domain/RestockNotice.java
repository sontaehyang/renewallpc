package saleson.shop.restocknotice.domain;

public class RestockNotice {
    private int restockNoticeId;
    private int itemId;
    private long userId;
    private String sendFlag;
    private String createdDate;

    private String itemName;
    private String phoneNumber;

    public int getRestockNoticeId() {
        return restockNoticeId;
    }

    public void setRestockNoticeId(int restockNoticeId) {
        this.restockNoticeId = restockNoticeId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
