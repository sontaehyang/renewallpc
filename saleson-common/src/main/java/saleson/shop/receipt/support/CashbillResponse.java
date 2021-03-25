package saleson.shop.receipt.support;

import saleson.common.enumeration.CashbillStatusCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter @Setter @NoArgsConstructor @ToString
public class CashbillResponse {

    private String mgtKey;
    private String orgConfirmNum;                   // 현금영수증 국세청 승인번호
    private String orgTradeDate;                    // 현금영수증 거래일자
    private boolean isSuccess;
    private String responseMessage;                 // popbill 통신 응답메시지
    private String responseCode;                    // popbill 통신 응답코드
    private CashbillStatusCode statusCode;          // 현금영수증 상태코드
    private String cashbillIssueDate;

    private int retCode;
    private String retMsg;
    private String redirectUrl;

    private String ResultCode;
    private String ResultMsg;
    private String TID;
    private String Moid;
    private String AuthCode;
    private String AuthDate;
    private String ErrorCd;
    private String ErrorMsg;
    private String CancelAmt;
    private String MID;
    private String PayMethod;
    private String CancelDate;
    private String CancelTime;
    private String CancelNum;
    private String RemainAmt;


}
