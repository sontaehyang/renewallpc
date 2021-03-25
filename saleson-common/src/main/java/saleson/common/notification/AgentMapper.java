package saleson.common.notification;

import saleson.common.notification.domain.CampaignStatistics;
import saleson.common.notification.domain.Table;
import saleson.common.notification.support.StatisticsParam;

import java.util.List;

public interface AgentMapper {

    /**
     * 테이블 유효성 체크
     * @param table
     * @return
     */
    int getValidTableCount(Table table);

    /**
     * 캠페인별 통계 리스트 조회
     * @param statisticsParam
     * @return
     */
    List<CampaignStatistics> getCampaignStatisticsListByParam(StatisticsParam statisticsParam);

}
