package saleson.model.campaign;

import lombok.*;
import saleson.common.notification.domain.CampaignStatistics;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.LocalDateUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OP_CAMPAIGN_SEND_LOG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class CampaignSendLog extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20)
    private String type;

    @Column
    private String campaignKey;

    @Column
    private Long sent;

    @Column
    private Long success;

    @Column
    private Long pushReceive;

    @Column
    private Long msgkey;

    @Column
    private Long userId;

    @Column(length = 20)
    private String phone;

    @Column
    private String title;

    @Column(length = 4000)
    private String content;

    @Column
    private LocalDateTime sendDate;

    @PrePersist
    public void prePersist() {
        setDefaultValue();
    }

    @PreUpdate
    public void preUpdate() {
        setDefaultValue();
    }

    private void setDefaultValue() {
        this.type = CommonUtils.dataNvl(this.type);
        this.campaignKey = CommonUtils.dataNvl(this.campaignKey);

        this.sent = CommonUtils.longNvl(this.sent);
        this.success = CommonUtils.longNvl(this.success);
        this.pushReceive = CommonUtils.longNvl(this.pushReceive);
    }

    public CampaignSendLog(String type, CampaignStatistics statistics) {
        if (statistics != null) {
            setType(type);
            setCampaignKey(statistics.getKey());
            setSent(statistics.getSent());
            setSuccess(statistics.getSuccess());
            setPushReceive(statistics.getPushReceive());
            setMsgkey(statistics.getMsgkey());
            setUserId(CommonUtils.longNvl(statistics.getUserId()));
            setPhone(statistics.getPhone());
            setTitle(statistics.getTitle());
            setContent(statistics.getContent());
            setSendDate(statistics.getSendDate());
        }
    }

    public String getSendDateText() {
        return LocalDateUtils.getDateTime(getSendDate());
    }

    public String getTypeLabel() {

        String label = getType();
        switch (getType()) {
            case "sms":
            case "kakao-sms":
                label = "SMS";
                break;
            case "mms":
            case "kakao-mms":
                label = "MMS";
                break;
            case "push":
            case "push-batch":
                label = "PUSH";
                break;

            case "kakao":
                label = "KAKAO";
                break;

            default:
        }

        return label;
    }

    public boolean isPushFlag() {
        return "push".equals(getType()) || "push-batch".equals(getType());
    }

}
