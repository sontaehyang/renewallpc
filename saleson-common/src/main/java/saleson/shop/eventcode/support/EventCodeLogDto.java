package saleson.shop.eventcode.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import saleson.common.enumeration.eventcode.EventCodeLogType;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.common.web.Param;
import saleson.model.eventcode.QEventCodeLog;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class EventCodeLogDto extends Param {

    private EventCodeType codeType;
    private EventCodeLogType logType;
    private String eventCode;
    private String uid;
    private long userId;
    private String itemUserCode;
    private String orderCode;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QEventCodeLog eventCodeLog = QEventCodeLog.eventCodeLog;

        if (!StringUtils.isEmpty(getEventCode())) {
            builder.and(eventCodeLog.eventCode.eq(getEventCode()));
        }

        if (getCodeType() != null) {
            builder.and(eventCodeLog.codeType.eq(getCodeType()));
        }

        if (getLogType() != null) {
            builder.and(eventCodeLog.logType.eq(getLogType()));
        }

        if (!StringUtils.isEmpty(getUid())) {
            builder.and(eventCodeLog.uid.eq(getUid()));
        }

        if (getUserId() > 0) {
            builder.and(eventCodeLog.userId.eq(getUserId()));
        }

        if (!StringUtils.isEmpty(getItemUserCode())) {
            builder.and(eventCodeLog.itemUserCode.eq(getItemUserCode()));
        }

        if (!StringUtils.isEmpty(getOrderCode())) {
            builder.and(eventCodeLog.orderCode.eq(getOrderCode()));
        }

        return builder;
    }
}
