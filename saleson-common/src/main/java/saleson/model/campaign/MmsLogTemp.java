package saleson.model.campaign;

import com.onlinepowers.framework.util.StringUtils;
import lombok.*;
import saleson.common.notification.domain.CampaignStatistics;
import saleson.common.utils.CommonUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OP_MMS_LOG_TEMP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class MmsLogTemp extends BaseEntity {

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

    @Column(length = 255)
    private String title;

    @Column(length = 4000, nullable = false)
    private String content;

    // 휴대폰 번호
    @Column(length = 20)
    private String phoneNumber;

    @Column
    private Long mmsSent;

    @Column
    private Long mmsSuccess;

    @Column
    private LocalDateTime sendDate;

    public MmsLogTemp(CampaignStatistics statistics) {
        if (statistics != null) {
            setUserId(StringUtils.isEmpty(statistics.getUserId()) ? null : statistics.getUserId());
            setMsgkey(statistics.getMsgkey());
            setCampaignId(!StringUtils.isEmpty(statistics.getKey()) && statistics.getKey() != null ? Long.valueOf(statistics.getKey()) : null);
            setContent(statistics.getContent());
            setSendDate(statistics.getSendDate());
            setPhoneNumber(StringUtils.phoneNumberPattern(statistics.getPhone()));
            setMmsSent(statistics.getSent());
            setMmsSuccess(statistics.getSuccess());
            setTitle(statistics.getTitle());
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
        this.mmsSent = CommonUtils.longNvl(this.mmsSent);
        this.mmsSuccess = CommonUtils.longNvl(this.mmsSuccess);
    }
}
