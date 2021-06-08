package saleson.shop.campaign.statistics;

import saleson.common.notification.domain.UmsStatistics;
import saleson.common.notification.domain.UmsStatisticsTable;
import saleson.common.notification.support.StatisticsParam;

import java.util.List;

public interface CampaignStatisticsService {

    void updateCampaignSentBatch(String batchDate) throws Exception;

    UmsStatistics getUserList(StatisticsParam param);

    List<UmsStatisticsTable> getLogTables(String... months);

}
