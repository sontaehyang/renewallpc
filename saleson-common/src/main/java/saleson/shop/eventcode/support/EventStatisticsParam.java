package saleson.shop.eventcode.support;

import com.onlinepowers.framework.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import saleson.common.Const;
import saleson.common.utils.LocalDateUtils;
import saleson.common.web.Param;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EventStatisticsParam extends Param {

    private String beginDate;
    private String endDate;

    public LocalDateTime getBeginDateTime() {

        if (StringUtils.isEmpty(getBeginDate())) {
            return LocalDateTime.now();
        }

        return LocalDateUtils.getLocalDateTime(getBeginDate()+"000000", Const.DATETIME_FORMAT);
    }

    public LocalDateTime getEndDateTime() {

        if (StringUtils.isEmpty(getEndDate())) {
            return LocalDateTime.now();
        }

        return LocalDateUtils.getLocalDateTime(getEndDate()+"235959", Const.DATETIME_FORMAT);
    }
}
