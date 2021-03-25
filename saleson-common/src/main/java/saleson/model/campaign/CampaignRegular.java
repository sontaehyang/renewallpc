package saleson.model.campaign;

import lombok.*;
import saleson.common.hibenate.converter.BooleanYnConverter;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "OP_CAMPAIGN_REGULAR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class CampaignRegular extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    // 정기발송 제목
    @Column(length = 255)
    private String title;

    // 정기발송 문구
    @Column(length = 4000, nullable = false)
    private String content;

    // 메시지유형 (0:일반, 1:광고)
    @Column(length = 1, nullable = false)
    private String messageType;

    // 발송수단 (0:SMS, 1:PUSH)
    @Column(length = 1, nullable = false)
    private String sendType;

    // 대체발송유형 (SMS:SMS 재전송, LMS:LMS 재전송, MMS:MMS 재전송, KKO:KAKAO 알림톡 재전송, isNULL:재전송없음)
    @Column(length = 3)
    private String resendType;

    // 발송주기 (month: 매월, week: 매주, daily: 매일)
    @Column(length = 10)
    private String sendCycle;

    // 정기발송일
    @Column(length = 2)
    private String sendDate;

    // 정기발송요일
    @Column(length = 2)
    private String sendDay;

    // 정기발송시간
    @Column(length = 2)
    private String sendTime;

    // 정기발송 시작일시
    @Column(length = 14)
    private String startSendDate;

    // 정기발송 종료일시
    @Column(length = 14)
    private String endSendDate;

    // MMS용 이미지
    @Column(length = 500)
    private String imageUrl;

    // 쿠폰 ID
    @Column(length = 11)
    private int couponId;

    // 예약발송 배치실행 유무
    @Column(length = 1)
    @Convert(converter = BooleanYnConverter.class)
    private boolean batchFlag;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "campaignRegularId")
    private List<CampaignRegularUrl> urlList;

    // 정기발송 상태 (0:발송전, 1:발송, 2:발송 취소 3:발송 완료)
    @Column(length = 1)
    private String status;

    // 검색 조건
    @Column
    private String searchWhere;

    // 검색어
    @Column
    private String query;

    // 회원 그룹 코드
    @Column(length = 10)
    private String groupCode;

    // 회원 등급 ID
    @Column(length = 14)
    private int levelId;

    // 가입이후 총 구매액
    @Column(length = 10)
    private int startOrderAmount1;
    @Column(length = 10)
    private int endOrderAmount1;

    // 최근 3개월간 총 구매액
    @Column(length = 10)
    private int startOrderAmount2;
    @Column(length = 10)
    private int endOrderAmount2;

    // 장바구니 총액
    @Column(length = 10)
    private int startCartAmount;
    @Column(length = 10)
    private int endCartAmount;

    // 장바구니 상품개수
    @Column(length = 10)
    private int cartCount;

    // 최근 방문일(미접속) 타입 (0: 전체, 1: 1개월 이상, 2: 2개월 이상, 3: 3개월 이상, 6: 6개월 이상, 24: 1년 이상)
    @Column(length = 2)
    private String lastLoginDateType;

    // 최근 방문일(미접속)
    @Column(length = 10)
    private String lastLoginDate;

    // GA 설정 (발송타입)
    @Column
    private String utmSource;

    // GA 설정 (매체 및 광고형태)
    @Column
    private String utmMedium;

    // GA 설정 (목적)
    @Column
    private String utmCampaign;

    // GA 설정 (유료 키워드 설정)
    @Column
    private String utmItem;

    // GA 설정 (유료 광고일 경우 구분 수단)
    @Column
    private String utmContent;

}
