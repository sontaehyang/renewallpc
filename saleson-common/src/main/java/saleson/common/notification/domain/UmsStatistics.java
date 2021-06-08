package saleson.common.notification.domain;

import lombok.Data;

import java.util.List;

@Data
public class UmsStatistics {
    private List<CampaignStatistics> list;
    private UmsStatisticsPage page;
}
