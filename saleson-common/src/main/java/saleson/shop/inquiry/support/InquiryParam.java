package saleson.shop.inquiry.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class InquiryParam extends SearchParam {

    private int inquiryId;
    private String inquiryType;

    private String userName;

    private String userEmail;
    private String userEmailId;
    private String userEmailAddr;

    private String telNumber;
    private String telNumber1;
    private String telNumber2;
    private String telNumber3;

    private String inquirySubject;
    private String inquiryContent;

    private int answerFlag;            // 답변 대기, 완료 여부

    private String createdDate;
    private String searchStartDate;
    private String searchEndDate;

    private String[] inquiryIds = null;

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public String[] getInquiryIds() {
        return inquiryIds;
    }

    public void setInquiryIds(String[] inquiryIds) {
        this.inquiryIds = inquiryIds;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getSearchStartDate() {
        return searchStartDate;
    }

    public void setSearchStartDate(String searchStartDate) {
        this.searchStartDate = searchStartDate;
    }

    public String getSearchEndDate() {
        return searchEndDate;
    }

    public void setSearchEndDate(String searchEndDate) {
        this.searchEndDate = searchEndDate;
    }

    public int getAnswerFlag() {
        return answerFlag;
    }

    public void setAnswerFlag(int answerFlag) {
        this.answerFlag = answerFlag;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(int inquiryId) {
        this.inquiryId = inquiryId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        if (userEmailId != null)
            return userEmailId + "@" + userEmailAddr;
        else
            return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getUserEmailAddr() {
        return userEmailAddr;
    }

    public void setUserEmailAddr(String userEmailAddr) {
        this.userEmailAddr = userEmailAddr;
    }

    public String getTelNumber() {
        if (telNumber1 != null)
            return this.telNumber1 + "-" + this.telNumber2 + "-" + this.telNumber3;
        else
            return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getTelNumber1() {
        return telNumber1;
    }

    public void setTelNumber1(String telNumber1) {
        this.telNumber1 = telNumber1;
    }

    public String getTelNumber2() {
        return telNumber2;
    }

    public void setTelNumber2(String telNumber2) {
        this.telNumber2 = telNumber2;
    }

    public String getTelNumber3() {
        return telNumber3;
    }

    public void setTelNumber3(String telNumber3) {
        this.telNumber3 = telNumber3;
    }

    public String getInquirySubject() {
        return inquirySubject;
    }

    public void setInquirySubject(String inquirySubject) {
        this.inquirySubject = inquirySubject;
    }

    public String getInquiryContent() {
        return inquiryContent;
    }

    public void setInquiryContent(String inquiryContent) {
        this.inquiryContent = inquiryContent;
    }

}