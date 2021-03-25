package saleson.shop.auth.domain;

public class SnsRequestAuth {

    private String snsType;
    private String snsId;
    private String email;
    private String snsName;

    public String getSnsType() {
        return snsType;
    }

    public void setSnsType(String snsType) {
        this.snsType = snsType;
    }

    public String getSnsId() {
        return snsId;
    }

    public void setSnsId(String snsId) {
        this.snsId = snsId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSnsName() {
        return snsName;
    }

    public void setSnsName(String snsName) {
        this.snsName = snsName;
    }

}
