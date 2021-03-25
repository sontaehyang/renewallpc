package saleson.model.campaign;

import com.onlinepowers.framework.util.DateUtils;
import lombok.*;
import saleson.common.Const;
import saleson.common.hibenate.converter.BooleanYnConverter;
import saleson.common.utils.CommonUtils;
import saleson.model.base.BaseEntity;
import saleson.model.eventcode.EventCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "OP_CAMPAIGN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Campaign extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    // 캠페인 제목
    @Column(length = 255)
    private String title;

    // 캠페인 문구
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

    // 예약발송일시
    @Column(length = 14)
    private String sendDate;

    // MMS용 이미지
    @Column(length = 500)
    private String imageUrl;

    // 쿠폰 ID
    @Column(length = 11)
    private int couponId;

    // Form 전송용 예약발송 시간
    @Transient
    private String sendTime;

    // Form 전송용 발송 시간 (0:즉시, 1:예약발송)
    @Transient
    private String reserve;

    // 정기발송여부 (Y:정기발송, N:즉시,예약발송)
    @Column(length = 1)
    @Convert(converter = BooleanYnConverter.class)
    private boolean regularFlag;

    // 예약발송 배치실행 유무
    @Column(length = 1)
    @Convert(converter = BooleanYnConverter.class)
    private boolean batchFlag;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "campaignId")
    private List<EventCode> urlList;

    @Column(length = 6, updatable = false)
    private String autoMonth;

    @Column
    private Long smsSent;

    @Column
    private Long smsSuccess;

    @Column
    private Long mmsSent;

    @Column
    private Long mmsSuccess;

    @Column
    private Long kakaoSent;

    @Column
    private Long kakaoSuccess;

    @Column
    private Long pushSent;

    @Column
    private Long pushSuccess;

    @Column
    private Long pushReceive;

    // 통계 배치 실행 날짜
    @Column
    private String statisticsDate;

    // 발송일시
    @Column
    private String sentDate;

    // 발송 상태 (0:예약, 1:발송, 2:예약발송 취소)
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

    // 링크 진입 건수
    @Column
    private Long redirection;

    // 구매연결 건수
    @Column
    private Long orderCount;

    // 구매연결 금액
    @Column
    private Long orderAmount;

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

    // 정기발송 발송주기 (month: 매월, week: 매주, daily: 매일)
    @Column(length = 10)
    private String sendCycle;

    @PrePersist
    public void prePersist() {
        setDefaultValue();
    }

    @PreUpdate
    public void preUpdate() {
        setDefaultValue();
    }

    public void setDefaultValue() {
        this.smsSent = CommonUtils.longNvl(this.smsSent);
        this.smsSuccess = CommonUtils.longNvl(this.smsSuccess);
        this.mmsSent = CommonUtils.longNvl(this.mmsSent);
        this.mmsSuccess = CommonUtils.longNvl(this.mmsSuccess);
        this.kakaoSent = CommonUtils.longNvl(this.kakaoSent);
        this.kakaoSuccess = CommonUtils.longNvl(this.kakaoSuccess);
        this.pushSent = CommonUtils.longNvl(this.pushSent);
        this.pushSuccess = CommonUtils.longNvl(this.pushSuccess);
        this.pushReceive = CommonUtils.longNvl(this.pushReceive);
        this.redirection = CommonUtils.longNvl(this.redirection);
        this.orderCount = CommonUtils.longNvl(this.orderCount);
        this.orderAmount = CommonUtils.longNvl(this.orderAmount);
        this.lastLoginDateType = this.lastLoginDateType == null ? "0" : this.lastLoginDateType;
    }

    public Campaign(CampaignRegular campaignRegular) {

        if (campaignRegular != null) {
            setTitle(campaignRegular.getTitle());
            setContent(campaignRegular.getContent());
            setCouponId(campaignRegular.getCouponId());
            setImageUrl(campaignRegular.getImageUrl());
            setMessageType(campaignRegular.getMessageType());
            setResendType(campaignRegular.getResendType());
            setSendType(campaignRegular.getSendType());
            setGroupCode(campaignRegular.getGroupCode());
            setLevelId(campaignRegular.getLevelId());
            setSearchWhere(campaignRegular.getSearchWhere());
            setQuery(campaignRegular.getQuery());
            setGroupCode(campaignRegular.getGroupCode());
            setLevelId(campaignRegular.getLevelId());
            setStartOrderAmount1(campaignRegular.getStartOrderAmount1());
            setEndOrderAmount1(campaignRegular.getEndOrderAmount1());
            setStartOrderAmount2(campaignRegular.getStartOrderAmount2());
            setEndOrderAmount2(campaignRegular.getEndOrderAmount2());
            setLastLoginDate(campaignRegular.getLastLoginDate());
            setLastLoginDateType(campaignRegular.getLastLoginDateType());
            setCartCount(campaignRegular.getCartCount());
            setStartCartAmount(campaignRegular.getStartCartAmount());
            setUrlList(getUrlList(campaignRegular.getUrlList()));
            setUtmCampaign(campaignRegular.getUtmCampaign());
            setUtmContent(campaignRegular.getUtmContent());
            setUtmItem(campaignRegular.getUtmItem());
            setUtmMedium(campaignRegular.getUtmMedium());
            setUtmSource(campaignRegular.getUtmSource());
            setSendDate(DateUtils.getToday(Const.DATEHOUR_FORMAT) + "0000");
            setSendCycle(campaignRegular.getSendCycle());
            setStatus("0");
            setBatchFlag(false);
            setRegularFlag(true);
        }
    }

    private List<EventCode> getUrlList(List<CampaignRegularUrl> regularUrls) {
        List<EventCode> urlList = new ArrayList<>();

        for (CampaignRegularUrl cr : regularUrls) {
            urlList.add(new EventCode(cr));
        }

        return urlList;
    }
}
