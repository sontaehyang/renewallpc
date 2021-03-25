package saleson.shop.naverpay.domain;

import lombok.*;

@Getter @Setter @NoArgsConstructor @ToString
public class NaverApiResponse {

    // 공용
    private String code;      //	결제 결과(Fail: 실패)
    private String message;   //	실패 사유 메시지
    private Body body;        //    응답 내용

}
