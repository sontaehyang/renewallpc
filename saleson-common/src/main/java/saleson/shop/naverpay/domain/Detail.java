package saleson.shop.naverpay.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Detail {

    private String paymentId;               // 네이버페이 결제번호
    private String payHistId;               // 네이버페이 결제 이력 번호
    private String merchantId;              // 가맹점 아이디 (가맹점센터 로그인 아이디)
    private String merchantName;            // 가맹점명
    private String merchantPayKey;          // 가맹점의 결제번호
    private String merchantUserKey;         // 가맹점의 사용자 키
    private String admissionTypeCode;       // 결제승인 유형 (01:원결제 승인건, 03:전체취소 건, 04:부분취소 건)
    private String admissionYmdt;           // 결제/취소 일시(YYYYMMDDHH24MMSS)
    private String tradeConfirmYmdt;        // 거래완료 일시(정산기준날짜, YYYYMMDDHH24MMSS)
    private String admissionState;          // 결제/취소 시도에 대한 최종결과 (SUCCESS:완료, FAIL:실패)
    private int totalPayAmount;             // 총 결제/취소 금액
    private int primaryPayAmount;           // 주 결제 수단 결제/취소 금액
    private int npointPayAmount;            // 네이버페이 포인트 결제/취소 금액
    private String primaryPayMeans;         // 주 결제 수단 (CARD:신용카드, BANK:계좌이체)
    private String cardCorpCode;            // 카드사 코드
    private String cardNo;                  // 일부 마스킹 된 신용카드 번호
    private String cardAuthNo;              // 카드승인번호
    private int cardInstCount;              // 할부 개월 수 (일시불은 0)
    private String bankCorpCode;            // 은행 코드
    private String bankAccountNo;           // 일부 마스킹 된 계좌 번호
    private String productName;             // 상품명
    private Boolean settleExpected;         // true/false. 정산 예정 금액과 결제 수수료 금액이 계산되었는지 여부 (이 값이 false이면 아래 두 필드의 값은 무의미)
    private int settleExpectAmount;         // 정산 예정 금액 (결제 후 약 1시간 후에 데이터가 생성되며, 그 전까지는 0값이 반환)
    private int payCommissionAmount;        // 결제 수수료 금액 (결제 후 약 1시간 후에 데이터가 생성되며, 그 전까지는 0값이 반환)
    private Boolean extraDeduction;         // 도서 / 공연 소득공제 여부
    private String useCfmYmdt;              // 이용완료일(yyyymmdd)
}
