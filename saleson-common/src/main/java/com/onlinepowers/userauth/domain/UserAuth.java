package com.onlinepowers.userauth.domain;

public class UserAuth {
    private String appKey;
    private String serviceType;
    private String serviceMode;
    private String serviceTarget;
    private String userIp;
    private String authKey;
    private String authName;
    private String authSex;
    private String authBirthDay;
    private String dataStatusCode;
    private String createdDate;

    public UserAuth() {
    }

    public String getServiceTarget() {
        return this.serviceTarget;
    }

    public void setServiceTarget(String serviceTarget) {
        this.serviceTarget = serviceTarget;
    }

    public String getDataStatusCode() {
        return this.dataStatusCode;
    }

    public void setDataStatusCode(String dataStatusCode) {
        this.dataStatusCode = dataStatusCode;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceMode() {
        return this.serviceMode;
    }

    public void setServiceMode(String serviceMode) {
        this.serviceMode = serviceMode;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUserIp() {
        return this.userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getAuthName() {
        return this.authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthSex() {
        return this.authSex;
    }

    public void setAuthSex(String authSex) {
        this.authSex = authSex;
    }

    public String getAuthBirthDay() {
        return this.authBirthDay;
    }

    public void setAuthBirthDay(String authBirthDay) {
        this.authBirthDay = authBirthDay;
    }

    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
