package saleson.model.eventcode;

import com.onlinepowers.framework.util.StringUtils;
import lombok.*;
import saleson.common.enumeration.eventcode.EventCodeLogType;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.common.utils.EventViewUtils;
import saleson.common.utils.UserUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name = "OP_EVENT_CODE_LOG")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class EventCodeLog extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private EventCodeType codeType;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private EventCodeLogType logType;

    @Column(length = 100)
    private String logDetail;

    @Column(length = 20)
    private String eventCode;

    @Column(length = 100, nullable = false)
    private String uid;

    @Column
    private Long sourceUserId;

    @Column
    private Long userId;

    @Column(length = 50)
    private String orderCode;

    @Column(length = 50)
    private String itemUserCode;

    @Column
    private String channel;

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

    @Transient
    private String queryString;

    public EventCodeLog() {
        init();
    }

    public EventCodeLog(EventCodeType codeType, EventCodeLogType logType) {
        this();
        setQueryString("");
        setCodeType(codeType != null ? codeType: EventCodeType.NONE);
        setLogType(logType != null ? logType : EventCodeLogType.NONE);
    }

    public EventCodeLog(EventCodeType codeType, EventCodeLogType logType, String queryString) {
        this(codeType, logType);

        setQueryString(queryString);
        Map<String, String> map = EventViewUtils.getParamMap(queryString);

        if (map != null && map.keySet().size() > 0) {

            for (String key : map.keySet()) {
                setParam(key, map);
            }
        }
    }

    private void init() {

        setUserId(0L);
        if (UserUtils.isUserLogin()) {
            setUserId(UserUtils.getUserId());
        }
        setUid(EventViewUtils.getUid());
        setCodeType(EventCodeType.NONE);
        setLogType(EventCodeLogType.NONE);
    }

    private void setParam(String key, Map<String, String> map) {

        String value = map.get(key);

        if (!StringUtils.isEmpty(value)) {
            switch (key) {
                case "ec": setEventCode(value); break;
                case "uid": setUid(value); break;
                case "utm_source": setUtmSource(value); break;
                case "utm_medium": setUtmMedium(value); break;
                case "utm_campaign": setUtmCampaign(value); break;
                case "utm_content": setUtmContent(value); break;
                case "utm_item": setUtmItem(value); break;
                case "source_user_id":
                    long sourceUserId = 0;
                    try {
                        sourceUserId = Long.parseLong(value);
                    } catch (Exception ignore){}
                    setSourceUserId(sourceUserId);
                    break;
                default:
            }
        }
    }
}
