package saleson.shop.order.claimapply.domain;

import com.onlinepowers.framework.repository.CodeInfo;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.order.claimapply.support.ExchangeApply;

import java.util.List;

public class CancelApplyInfo {

    String userClickItemStatus;
    List<CodeInfo> claimReasons;
    ClaimApply claimApply;

    public String getUserClickItemStatus() {
        return userClickItemStatus;
    }

    public void setUserClickItemStatus(String userClickItemStatus) {
        this.userClickItemStatus = userClickItemStatus;
    }

    public List<CodeInfo> getClaimReasons() {
        return claimReasons;
    }

    public void setClaimReasons(List<CodeInfo> claimReasons) {
        this.claimReasons = claimReasons;
    }

    public ClaimApply getClaimApply() {
        return claimApply;
    }

    public void setClaimApply(ClaimApply claimApply) {
        this.claimApply = claimApply;
    }
}
