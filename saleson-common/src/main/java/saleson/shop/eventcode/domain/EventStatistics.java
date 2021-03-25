package saleson.shop.eventcode.domain;

import lombok.Data;
import saleson.common.enumeration.eventcode.EventCodeType;
import saleson.common.utils.CommonUtils;

import java.util.Map;

@Data
public class EventStatistics {

    private String eventCode;
    private String contents;
    private int visitCount;
    private int orderCount;
    private double orderRate;
    private int joinCount;
    private double joinRate;

    public EventStatistics(Map<String, Object> map) {

        if (map != null) {
            setEventCode(CommonUtils.dataNvl(map.get("EVENT_CODE")));
            setVisitCount(CommonUtils.bigDecimalNvl(map.get("VISIT_COUNT")).intValue());
            setOrderCount(CommonUtils.bigDecimalNvl(map.get("ORDER_COUNT")).intValue());
            setOrderRate(CommonUtils.bigDecimalNvl(map.get("ORDER_RATE")).doubleValue());
            setJoinCount(CommonUtils.bigDecimalNvl(map.get("JOIN_COUNT")).intValue());
            setJoinRate(CommonUtils.bigDecimalNvl(map.get("JOIN_RATE")).doubleValue());
        }
    }
}

