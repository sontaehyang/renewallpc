package saleson.shop.eventcode.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import saleson.common.web.Param;
import saleson.model.eventcode.QEventCode;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EventCodeDto extends Param {

    private String eventCode;

    private List<String> eventCodes;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QEventCode eventCode = QEventCode.eventCode1;

        if (!StringUtils.isEmpty(getEventCode())) {
            builder.and(eventCode.eventCode.eq(getEventCode()));
        }

        List<String> eventCodes = getEventCodes();
        if (eventCodes != null && !eventCodes.isEmpty() && StringUtils.isEmpty(getEventCode())) {
            builder.and(eventCode.eventCode.in(eventCodes));
        }

        return builder;
    }
}
