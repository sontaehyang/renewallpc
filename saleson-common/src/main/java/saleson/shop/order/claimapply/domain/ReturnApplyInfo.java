package saleson.shop.order.claimapply.domain;

import com.onlinepowers.framework.repository.CodeInfo;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.order.claimapply.support.ReturnApply;

import java.util.List;

public class ReturnApplyInfo {

    List<DeliveryCompany> deliveryCompanyList;
    List<CodeInfo> claimReasons;
    ReturnApply returnApply;
    boolean paymentType;

    public List<DeliveryCompany> getDeliveryCompanyList() {
        return deliveryCompanyList;
    }

    public void setDeliveryCompanyList(List<DeliveryCompany> deliveryCompanyList) {
        this.deliveryCompanyList = deliveryCompanyList;
    }

    public List<CodeInfo> getClaimReasons() {
        return claimReasons;
    }

    public void setClaimReasons(List<CodeInfo> claimReasons) {
        this.claimReasons = claimReasons;
    }

    public ReturnApply getReturnApply() {
        return returnApply;
    }

    public void setReturnApply(ReturnApply returnApply) {
        this.returnApply = returnApply;
    }

    public boolean isPaymentType() {
        return paymentType;
    }

    public void setPaymentType(boolean paymentType) {
        this.paymentType = paymentType;
    }
}
