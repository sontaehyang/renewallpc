package saleson.model.campaign;

import com.onlinepowers.framework.util.StringUtils;
import lombok.*;
import saleson.common.notification.domain.CampaignStatistics;
import saleson.common.utils.CommonUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OP_SMS_LOG_TEMP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class SmsLogTemp extends BaseEntity {

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
    private Long smsSent;

    @Column
    private Long smsSuccess;

    @Column
    private LocalDateTime sendDate;

    public SmsLogTemp(CampaignStatistics statistics) {
        if (statistics != null) {
            setUserId(StringUtils.isEmpty(statistics.getUserId()) ? null : statistics.getUserId());
            setMsgkey(statistics.getMsgkey());
            setCampaignId(!StringUtils.isEmpty(statistics.getKey()) && statistics.getKey() != null ? Long.valueOf(statistics.getKey()) : null);
            setContent(statistics.getContent());
            setSendDate(statistics.getSendDate());
            setPhoneNumber(StringUtils.phoneNumberPattern(statistics.getPhone()));
            setSmsSent(statistics.getSent());
            setSmsSuccess(statistics.getSuccess());
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
        this.smsSent = CommonUtils.longNvl(this.smsSent);
        this.smsSuccess = CommonUtils.longNvl(this.smsSuccess);
    }
}
