package saleson.shop.order.claimapply.domain;

import com.onlinepowers.framework.repository.CodeInfo;
import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.order.claimapply.support.ExchangeApply;

import java.util.List;

public class ExchangeApplyInfo {

    List<DeliveryCompany> deliveryCompanyList;
    List<CodeInfo> claimReasons;
    ExchangeApply exchangeApply;

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

    public ExchangeApply getExchangeApply() {
        return exchangeApply;
    }

    public void setExchangeApply(ExchangeApply exchangeApply) {
        this.exchangeApply = exchangeApply;
    }
}
