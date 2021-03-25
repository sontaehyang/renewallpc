package saleson.shop.eventcode.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventLogParam{

    private String id;
    private List<String> items;

    private String eventCode;
    private String uid;
    private Long sourceUserId;
    private String channel;
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private String utmItem;
    private String utmContent;
}
