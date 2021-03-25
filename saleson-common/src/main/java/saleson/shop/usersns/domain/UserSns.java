package saleson.shop.usersns.domain;

public class UserSns {

    public UserSns(){}
    public UserSns(long userId){this.userId = userId;}

    // SNS_USER_ID - OP_USER_SNS(PK)
    private int snsUserId;

    // SNS_ID - SNS 제공 사용자 고유 ID
    private String snsId;

    // USER_ID - 사용자 아이디
    private long userId;

    // SNS_TYPE - SNS 종류 (naver, facebook, kakao)
    private String snsType;

    // SNS_NAME - SNS 제공 사용자명
    private String snsName;

    // EMAIL - SNS 제공 사용자 EMAIL
    private String email;

    // CREATED_DATE - 생성일
    private String createdDate;

    // CERTIFIED_DATE - OP_USER_SNS 정보 입력 완료 시간
    private String certifiedDate;

    // OP_USER.LOGIN_ID - 로그인시 사용되는 아이디
    private String loginId;

    // mypage인지 아닌지 확인
    private Boolean isMypage = false;

    public int getSnsUserId() {
        return snsUserId;
    }

    public void setSnsUserId(int snsUserId) {
        this.snsUserId = snsUserId;
    }

    public String getSnsId() {
        return snsId;
    }

    public void setSnsId(String snsId) {
        this.snsId = snsId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSnsType() {
        return snsType;
    }

    public void setSnsType(String snsType) {
        this.snsType = snsType;
    }

    public String getSnsName() {
        return snsName;
    }

    public void setSnsName(String snsName) {
        this.snsName = snsName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Boolean getIsMypage() {
        return isMypage;
    }

    public void setIsMypage(Boolean isMypage) {
        this.isMypage = isMypage;
    }

    public String getCertifiedDate() {
        return certifiedDate;
    }

    public void setCertifiedDate(String certifiedDate) {
        this.certifiedDate = certifiedDate;
    }

    public String getSnsTypeName() {
        String name = "";
        switch (this.getSnsType()) {
            case "naver":
                name = "네이버";
                break;
            case "facebook":
                name = "페이스북";
                break;
            case "kakao":
                name = "카카오톡";
                break;
            default: break;
        }

        return name;
    }
}
