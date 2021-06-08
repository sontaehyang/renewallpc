package saleson.shop.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.campaign.CampaignSendLog;

public interface CampaignSendLogRepository extends JpaRepository<CampaignSendLog, Long>, QuerydslPredicateExecutor<CampaignSendLog> {
}
