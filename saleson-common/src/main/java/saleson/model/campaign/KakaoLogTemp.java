package saleson.model.campaign;

import com.onlinepowers.framework.util.StringUtils;
import lombok.*;
import saleson.common.notification.domain.CampaignStatistics;
import saleson.common.utils.CommonUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OP_KAKAO_LOG_TEMP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class KakaoLogTemp extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long userId;

    // msagent log 테이블 key
    @Column
    private Long msgkey;

    @Column
    private Long campaignId;

    @Column(length = 4000, nullable = false)
    private String content;

    // 휴대폰 번호
    @Column(length = 20)
    private String phoneNumber;

    @Column
    private Long kakaoSent;

    @Column
    private Long kakaoSuccess;

    @Column
    private LocalDateTime sendDate;

    public KakaoLogTemp(CampaignStatistics statistics) {
        if (statistics != null) {
            setUserId(StringUtils.isEmpty(statistics.getUserId()) ? null : statistics.getUserId());
            setMsgkey(statistics.getMsgkey());
            setCampaignId(!StringUtils.isEmpty(statistics.getKey()) && statistics.getKey() != null ? Long.valueOf(statistics.getKey()) : null);
            setContent(statistics.getContent());
            setSendDate(statistics.getSendDate());
            setPhoneNumber(StringUtils.phoneNumberPattern(statistics.getPhone()));
            setKakaoSent(statistics.getSent());
            setKakaoSuccess(statistics.getSuccess());
        }
    }

    @PrePersist
    public void prePersist() {
        setDefaultValue();
    }

    @PreUpdate
    public void preUpdate() {
        setDefaultValue();
    }

    public void setDefaultValue() {
        this.kakaoSent = CommonUtils.longNvl(this.kakaoSent);
        this.kakaoSuccess = CommonUtils.longNvl(this.kakaoSuccess);
    }
}
