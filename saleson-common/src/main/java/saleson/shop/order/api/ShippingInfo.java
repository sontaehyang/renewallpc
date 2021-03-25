package saleson.shop.order.api;

public class ShippingInfo {


    private String receiveName; // 받으시는분
    private String receiveMobile; // 휴대폰 번호
    private String receivePhone; // 전화번호
    private String memo; // 배송시 요구사항
    private String receiveSido; // 시도
    private String receiveSigungu;  // 시군구
    private String receiveEupmyeondong; // 읍면동
    private String receiveNewZipcode; // 신주소 우편번호
    private String receiveZipcode; // 구주소 우편번호
    private String receiveAddress; // 주소
    private String receiveAddressDetail; // 상세주소
    private String receiveMobile1;
    private String receiveMobile2;
    private String receiveMobile3;
    private int payShipping; // 배송비(0원 일 시 무료)

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getReceiveNewZipcode() {
        return receiveNewZipcode;
    }

    public void setReceiveNewZipcode(String receiveNewZipcode) {
        this.receiveNewZipcode = receiveNewZipcode;
    }

    public String getReceiveZipcode() {
        return receiveZipcode;
    }

    public void setReceiveZipcode(String receiveZipcode) {
        this.receiveZipcode = receiveZipcode;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiveAddressDetail() {
        return receiveAddressDetail;
    }

    public void setReceiveAddressDetail(String receiveAddressDetail) {
        this.receiveAddressDetail = receiveAddressDetail;
    }

    public int getPayShipping() {
        return payShipping;
    }

    public void setPayShipping(int payShipping) {
        this.payShipping = payShipping;
    }

    public String getReceiveSido() {
        return receiveSido;
    }

    public void setReceiveSido(String receiveSido) {
        this.receiveSido = receiveSido;
    }

    public String getReceiveSigungu() {
        return receiveSigungu;
    }

    public void setReceiveSigungu(String receiveSigungu) {
        this.receiveSigungu = receiveSigungu;
    }

    public String getReceiveEupmyeondong() {
        return receiveEupmyeondong;
    }

    public void setReceiveEupmyeondong(String receiveEupmyeondong) {
        this.receiveEupmyeondong = receiveEupmyeondong;
    }

    public String getReceiveMobile1() {
        return receiveMobile1;
    }

    public void setReceiveMobile1(String receiveMobile1) {
        this.receiveMobile1 = receiveMobile1;
    }

    public String getReceiveMobile2() {
        return receiveMobile2;
    }

    public void setReceiveMobile2(String receiveMobile2) {
        this.receiveMobile2 = receiveMobile2;
    }

    public String getReceiveMobile3() {
        return receiveMobile3;
    }

    public void setReceiveMobile3(String receiveMobile3) {
        this.receiveMobile3 = receiveMobile3;
    }
}
