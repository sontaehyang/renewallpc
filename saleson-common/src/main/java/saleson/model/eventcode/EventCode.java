package saleson.model.eventcode;

import lombok.*;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.model.base.BaseEntity;
import saleson.model.campaign.CampaignRegularUrl;

import javax.persistence.*;

@Entity
@Table(name = "OP_EVENT_CODE",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"eventCode"}
                )
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class EventCode extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private EventCodeType type = EventCodeType.NONE;

    // 컨텐츠
    @Column(length = 500)
    private String contents;

    // 대체코드
    @Column(length = 100)
    private String changeCode;

    // 이벤트 코드
    @Column(length = 20, updatable = false, nullable = false)
    private String eventCode;

    @Column
    private Long redirection;

    // GA 설정 (Campaign의 GA항목 합침)
    @Column
    private String utmQueryString;

    @Column
    private Long userId;

    @Transient
    private boolean notRedirectionFlag;

    @PrePersist
    public void prePersist() {
        setDefaultValue();
    }

    @PreUpdate
    public void preUpdate() {
        setDefaultValue();
    }

    public void setDefaultValue() {
        setType(EventCodeType.NONE );
        setNotRedirectionFlag(false);
    }

    public EventCode(CampaignRegularUrl regularUrl) {
        if (regularUrl != null) {
            setType(EventCodeType.CAMPAIGN);
            setContents(regularUrl.getContents());
            setChangeCode(regularUrl.getChangeCode());
            setEventCode(regularUrl.getEventCode());
            setUtmQueryString(regularUrl.getUtmQueryString());
        }
    }
}
