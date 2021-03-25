package saleson.shop.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.campaign.CampaignRegular;

public interface CampaignRegularRepository
        extends JpaRepository<CampaignRegular, Long>, QuerydslPredicateExecutor<CampaignRegular> {
}
