package saleson.shop.restocknotice.support;

import com.onlinepowers.framework.web.domain.SearchParam;

public class RestockNoticeParam extends SearchParam {
    private int itemId;
    private long userId;
    private String sendFlag;

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

}
