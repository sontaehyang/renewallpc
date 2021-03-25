package saleson.shop.inquiry.domain;

import org.springframework.web.multipart.MultipartFile;

public class Inquiry {

    private int inquiryId;
    private String userName;

    private String userEmail;
    private String userEmailId;
    private String userEmailAddr;

    private String inquiryType;
    private String itemCode;
    private String itemName;
    private String mode = "";
    private String redirect = "";

    private String telNumber;
    private String telNumber1;
    private String telNumber2;
    private String telNumber3;

    private String inquiryAnswer;
    // 문의 이미지
    private MultipartFile inquiryImg;
    private String inquiryImgName;

    private int answerFlag;

    private String inquirySubject;
    private String inquiryContent;

    private String createdDate;
    private String smsTitle;

    public String getSmsTitle() {
        return smsTitle;
    }

    public void setSmsTitle(String smsTitle) {
        this.smsTitle = smsTitle;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getInquiryAnswer() {
        return inquiryAnswer;
    }

    public void setInquiryAnswer(String inquiryAnswer) {
        this.inquiryAnswer = inquiryAnswer;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getInquiryImgName() {
        return inquiryImgName;
    }

    public void setInquiryImgName(String inquiryImgName) {
        this.inquiryImgName = inquiryImgName;
    }

    public MultipartFile getInquiryImg() {
        return inquiryImg;
    }

    public void setInquiryImg(MultipartFile inquiryImg) {
        this.inquiryImg = inquiryImg;
    }

    public int getAnswerFlag() {
        return answerFlag;
    }

    public void setAnswerFlag(int answerFlag) {
        this.answerFlag = answerFlag;
    }

    public String getCreatedDate() {
        return createdDate;
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
            return telNumber1 + "-" + telNumber2 + "-" + telNumber3;
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