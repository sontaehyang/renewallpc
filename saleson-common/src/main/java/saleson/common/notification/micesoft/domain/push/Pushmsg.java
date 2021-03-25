package saleson.common.notification.micesoft.domain.push;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pushmsg {

    /**
     * 메시지고유번호(유일키값), 고객사 정보성
     */
    private String msgNo;

    /**
     * PUSH 유형(A:APNS, F:FCM)
     */
    private String pushType;

    /**
     * PUSH 토큰
     */
    private String pushToken;

    /**
     * Push 제목
     */
    private String title;

    /**
     * Push 내용
     */
    private String cnts;

    /**
     * 메시지유형(예, 01:일반알림, 02:마케팅알림, 03:이벤트알림)
     */
    private String msgType;

    /**
     * Deep Link 메뉴ID
     */
    private String menuId;

    /**
     * 어플리케이션 번호
     */
    private String appNo;

    /**
     * 어플리케이션버전
     */
    private String appVer;

    /**
     * 고객식별번호(사업자번호,IPIN,고객번호 등)
     */
    private String mbrNo;

    /**
     * 디바이스고유번호
     */
    private String uuid;

    /**
     * 운영체제 1 : iOS, 2 : Android
     */
    private String os;

    /**
     * 발송예약일시(미지정: 즉시 발송)
     */
    private String resvDt;

    /**
     * 테스트 발송 여부 Y : 테스트 발송 , N : 운영 발송
     */
    private String testYn;

    /**
     * 웹뷰 URL
     */
    private String webUrl;

    /**
     * 이미지팝업용 이미지 URL
     */
    private String imgUrl;

    /**
     * BIG PICTURE 용 이미지 URL
     */
    private String stsUrl;

    /**
     * 이미지팝업 사용여부
     */
    private String popupYn;

    /**
     * BIG PICTURE 사용여부
     */
    private String stsYn;

    /**
     * FCM 타입 CCS CCS-US HTTP
     */
    private String kind;

    /**
     * APP 전달용 커스텀 데이터
     */
    private String customData;

    /**
     * 대체발송유형
     *
     * SMS : SMS 재전송, LMS : LMS 재전송, MMS : MMS 재전송,
     * KKO : KAKAO 알림톡 재전송,
     * isNull : 재전송없음
     */
    private String rtType;

    /**
     * 카카오 알림톡 템플릿 코드
     * 친구톡의 “0000000” 고정
     */
    private String templateCode;

    /**
     * 카카오 알림톡 전송 메시지
     */
    private String kkoMsg;

    /**
     * @플러스친구 프로파일키
     */
    private String profileKey;

    /**
     * 카카오 알림톡 버튼 타입 URL
     */
    private String url;

    /**
     * 카카오 알림톡 버튼 타입 버튼 TEXT
     */
    private String urlButtonTxt;

    /**
     * 친구톡 이미지 파일 경로 (모듈 설치 서버 내 파일 경로)
     */
    private String imgPath;

    /**
     * 친구톡 이미지 URL
     */
    private String imgPathUrl;

    /**
     * 버튼그룹 데이터 JSON
     */
    private String buttonJson;

    /**
     * 친구톡 광고 표시여부 (Y/N)
     */
    private String adFlag;

    /**
     * 메시지를 전송할 시간, 미래 시간을 넣으면 예약 발송됨 // SMS,LMS,MMS,KKO 알림톡 에서 사용
     */
    private String reqdate;

    /**
     * 알림톡 실패시 재전송 타입
     *
     * 카카오알림톡 전송 실패 시 전송할 메시지 타입
     * SMS : SMS재전송, LMS : LMS재전송
     * MMS : MMS재전송, NO : 재전송 없음
     */
    private String failedType;

    /**
     * 고객사 ID // SMS,LMS,MMS,KKO 알림톡 에서 사용
     */
    private String id;

    /**
     * 수신할 핸드폰 번호 // SMS,LMS,MMS,KKO 알림톡 에서 사용
     */
    private String phone;

    /**
     * 송신자 전화번호 // SMS,LMS,MMS,KKO 알림톡 에서 사용
     */
    private String callback;

    /**
     * 대체발송시 제목(SMS 미사용) //LMS,MMS 에서 사용
     */
    private String failedSubject;

    /**
     * 대체발송시 내용 //SMS, LMS, MMS에서 사용
     */
    private String failedMsg;

    /**
     * 대체발송시 전송할 이미지 // MMS에서 사용
     */
    private String failedImg;

    /**
     * 기타 필드1 (사용자가 자유롭게 입력하여 사용 가능)
     */
    private String etc1;

    /**
     * 기타 필드2 (사용자가 자유롭게 입력하여 사용 가능)
     */
    private String etc2;

}
