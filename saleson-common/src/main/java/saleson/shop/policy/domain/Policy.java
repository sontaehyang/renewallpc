package saleson.shop.policy.domain;

import com.onlinepowers.framework.util.StringUtils;

public class Policy {
    /**
     * 약관
     */
    public final static String POLICY_TYPE_AGREEMENT = "0";
    /**
     * 개인정보취급방침
     */
    public final static String POLICY_TYPE_PROTECT_POLICY = "1";
    /**
     * 특정상거래법
     */
    public final static String POLICY_TYPE_TRADER_RAW = "2";
    /**
     * 마케팅이용약관
     */
    public final static String POLICY_TYPE_MARKETING_AGREEMENT = "3";

    private int policyId;
    private String policyType;
    private String content;
    private String createdDate;
    private long createdUserId;

    private String title;
    private String exhibitionStatus;

    private String updatedDate;
    private String updatedLoginId;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(long createdUserId) {
        this.createdUserId = createdUserId;
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

    public String getPolicyTypeLabel() {
        if (StringUtils.isEmpty(this.policyType)) {
            return "";
        }

        String fileTypeName = "";

        if ("0".equals(this.policyType)) {
            fileTypeName = "약관";
        } else if ("1".equals(this.policyType)) {
            fileTypeName = "개인정보취급방침";
        } else if ("2".equals(this.policyType)) {
            fileTypeName = "특정상거래법";
        } else if ("3".equals(this.policyType)) {
            fileTypeName = "마케팅이용약관";
        }

        return fileTypeName;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedLoginId() {
        return updatedLoginId;
    }

    public void setUpdatedLoginId(String updatedLoginId) {
        this.updatedLoginId = updatedLoginId;
    }

}
