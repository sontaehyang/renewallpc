package saleson.shop.policy.support;

import com.onlinepowers.framework.web.domain.SearchParam;

public class PolicyParam extends SearchParam {

    private int policyId;
    private String policyType;
    private String title;
    private String exhibitionStatus;

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExhibitionStatus() {
        return exhibitionStatus;
    }

    public void setExhibitionStatus(String exhibitionStatus) {
        this.exhibitionStatus = exhibitionStatus;
    }

}


