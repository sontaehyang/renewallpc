package saleson.shop.eventcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.campaign.CampaignQueryConst;
import saleson.model.eventcode.EventCode;

import java.util.List;
import java.util.Map;

public interface EventCodeRepository extends JpaRepository<EventCode, Long>, QuerydslPredicateExecutor<EventCode> {

    @Modifying
    @Query(value = CampaignQueryConst.DELETE_CAMPAIGN_URL, nativeQuery = true)
    void deleteCampaignUrlByCampaignId(
            @Param("campaignId") Long campaignId
    );

    @Query(value = CampaignQueryConst.SUMMARY_EVENT_REDIRECTION, nativeQuery = true)
    List<Map<String, Object>> getSummaryEventRedirection();

    @Modifying
    @Query(value = CampaignQueryConst.DELETE_CAMPAIGN_REGULAR_URL, nativeQuery = true)
    void deleteCampaignRegularUrl(
            @Param("campaignRegularId") Long campaignRegularId
    );

    @Query(value = CampaignQueryConst.SELECT_CAMPAIGN_ID_BY_ID, nativeQuery = true)
    Long getCampaignId(
            @Param("id") Long id
    );
}
