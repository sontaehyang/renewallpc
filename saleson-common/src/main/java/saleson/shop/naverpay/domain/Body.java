package saleson.shop.naverpay.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class Body {

    private String reserveId;       //	결제 예약 ID
    private String paymentId;       // 네이버페이 결제번호
    private Detail detail;          // 응답결과 상세정보 객체

    // 결제취소 응답
    private int primaryPayCancelAmount;     // 주 결제 수단 취소 금액
    private int primaryPayRestAmount;       // 추가로 취소 가능한 주 결제 수단 잔여 결제 금액
    private int npointCancelAmount;         // 네이버페이 포인트 취소 금액
    private int npointRestAmount;           // 추가로 취소 가능한 네이버페이 포인트 잔여 결제 금액
    private String cancelYmdt;              // 취소 일시(YYYYMMDDHH24MMSS)
    private int totalRestAmount;            // 추가로 취소 가능한 전체 잔여 결제 금액(primaryPayRestAmount + npointRestAmount)

}
