package saleson.shop.eventcode.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import saleson.common.utils.CommonUtils;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventItemStatistics {

    private String itemName;
    private String itemUserCode;
    private int listViewCount;
    private int itemViewCount;
    private double clickedRate;
    private int orderCount;
    private double orderRate;

    public EventItemStatistics(Map<String, Object> map) {

        if (map != null) {
            setItemName(CommonUtils.dataNvl(map.get("ITEM_NAME")));
            setItemUserCode(CommonUtils.dataNvl(map.get("ITEM_USER_CODE")));
            setListViewCount(CommonUtils.bigDecimalNvl(map.get("LIST_VIEW_COUNT")).intValue());
            setItemViewCount(CommonUtils.bigDecimalNvl(map.get("ITEM_VIEW_COUNT")).intValue());
            setOrderCount(CommonUtils.bigDecimalNvl(map.get("ORDER_COUNT")).intValue());
            setOrderRate(CommonUtils.bigDecimalNvl(map.get("ORDER_RATE")).doubleValue());
            setClickedRate(CommonUtils.bigDecimalNvl(map.get("CLICKED_RATE")).doubleValue());
        }
    }
}
