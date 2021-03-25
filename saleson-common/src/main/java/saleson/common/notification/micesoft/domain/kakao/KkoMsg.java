package saleson.common.notification.micesoft.domain.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KkoMsg {

    /**
     * 고객이 발급한 SubID
     */
    private String id;

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
     * 수신할 핸드폰 번호 동보 일 경우 전화번호 개수입력
     */
    private String phone;

    /**
     * 송신자 전화번호
     */
    private String callback;

    /**
     * 전송할 메시지
     */
    private String msg;

    /**
     * 카카오 알림톡 템플릿 코드
     * 친구톡의 “0000000” 고정
     */
    private String templateCode;

    /**
     * 카카오알림톡 전송 실패 시 전송할 메시지 타입
     * SMS : SMS재전송, LMS : LMS재전송
     * MMS : MMS재전송, NO : 재전송 없음
     */
    private String failedType;

    /**
     * 카카오알림톡 전송 실패 시 전송할 제목(SMS 미사용)
     */
    private String failedSubject;

    /**
     * 카카오알림톡 전송 실패 시 전송할 내용
     */
    private String failedMsg;

    /**
     * @플러스친구 프로파일키
     */
    private String profileKey;

    /**
     * 버튼그룹 데이터 JSON
     */
    private String buttonJson;

    /**
     * 고객사에서 사용하는 옵션필드
     * 주로 하위가맹점/지점명등 관리용 사용
     */
    private String etc3;
}
