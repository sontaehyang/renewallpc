package saleson.model.campaign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saleson.common.enumeration.DeviceType;
import saleson.common.utils.CommonUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OP_CAMPAIGN_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignUser extends BaseEntity {

    @EmbeddedId
    private CampaignUserPk pk;

    // 이름
    @Column(length = 50)
    private String userName;

    // 휴대폰 번호
    @Column(length = 20)
    private String phoneNumber;

    // PUSH 토큰
    @Column(length = 4000)
    private String pushToken;

    // 어플리케이션 TOKEN
    @Column(length = 100)
    private String applicationNo;

    // 어플리케이션 버전
    @Column(length = 20)
    private String applicationVersion;

    // 디바이스 고유번호
    @Column(length = 200)
    private String uuid;

    // 디바이스 타입
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private DeviceType deviceType;

    // SMS 수신여부
    @Column(length = 1)
    private String receiveSms;

    // 푸시 수신여부
    @Column(length = 1)
    private String receivePush;

    // 회원 보유 포인트
    @Column
    private Long point;

    @Column
    private LocalDateTime batchDate;

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

    // 링크 진입 건수
    @Column
    private Long redirection;

    // 구매연결 건수
    @Column
    private Long orderCount;

    // 구매연결 금액
    @Column
    private Long orderAmount;

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
    }

    public CampaignUser(long campaignId, CampaignBatch campaignBatch) {

        if (campaignBatch != null) {
            setUserName(campaignBatch.getUserName());

            setPk(new CampaignUserPk(campaignBatch.getUserId(), campaignId));
        }
    }
}
