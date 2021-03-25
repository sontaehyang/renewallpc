package saleson.common.notification.micesoft.domain.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsMsg {

    /**
     * 고객이 발급한 SubID
     */
    private String id;

    /**
     * 제목
     */
    private String subject;

    /**
     * 발송상태
     *  '0'- 전송대기, '1'- 송신 중
     *  '2'- 송신완료, '3'- 결과수신
     * ‘4’ – failback 결과 수신 완료
     *  '98'- MSAgent 송신중
     *  '99'-MSAgent 송신완료
     */
    private String status;

    /**
     * 수신자 번호
     */
    private String phone;

    /**
     * 수신자 번호 List
     */
    private List<String> phoneNumbers;

    /**
     * 송신자 전화번호
     */
    private String callback;

    /**
     * SMS 요청시간, 예약시간
     */
    private String reqdate;

    /**
     * 전송할 메시지
     */
    private String msg;

    /**
     * 전송할 메시지 리스트
     */
    private List<String> msgs;

    /**
     * 고객사에서 사용하는 옵션필드
     * 주로 하위가맹점/지점명등 관리용 사용
     */
    private String etc1;

    /**
     * 고객사에서 사용하는 옵션필드
     * 주로 하위가맹점/지점명등 관리용 사용
     */
    private String etc3;

    /**
     * 고객사에서 사용하는 옵션필드
     * 주로 하위가맹점/지점명등 관리용 사용
     */
    private List<String> etc3List;

}
