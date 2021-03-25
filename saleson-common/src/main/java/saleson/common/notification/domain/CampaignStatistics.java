package saleson.common.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CampaignStatistics {

    private String type;
    private String key;
    private long sent;
    private long success;
    private long pushReceive;

    private Long orderCount;
    private Long orderAmount;

    private Long msgkey;
    private Long userId;
    private String phone;
    private String title;
    private String content;
    private LocalDateTime sendDate;
}
