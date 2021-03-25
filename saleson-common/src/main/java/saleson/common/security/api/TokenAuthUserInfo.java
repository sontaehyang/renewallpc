package saleson.common.security.api;

public class TokenAuthUserInfo {

    private String loginType;
    private String loginId;
    private String encodePassword;
    private String ip;
    private String jti;

    public TokenAuthUserInfo(String loginType, String loginId, String encodePassword, String ip, String jti) {
        this.loginType = loginType;
        this.loginId = loginId;
        this.encodePassword = encodePassword;
        this.ip = ip;
        this.jti = jti;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getEncodePassword() {
        return encodePassword;
    }

    public void setEncodePassword(String encodePassword) {
        this.encodePassword = encodePassword;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}
