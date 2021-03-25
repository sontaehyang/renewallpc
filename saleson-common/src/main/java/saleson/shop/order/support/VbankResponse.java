package saleson.shop.order.support;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class VbankResponse {
    private boolean isSuccess;
    private String responseMessage;
    private String responseCode;

    private String ResultCode;
    private String ResultMsg;
    private String ErrorCD;
    private String ErrorMsg;
    private String CancelAmt;
    private String MID;
    private String Moid;
    private String Signature;
    private String PayMethod;
    private String TID;
    private String CancelDate;
    private String CancelTime;
    private String CancelNum;
    private String RemainAmt;
}
