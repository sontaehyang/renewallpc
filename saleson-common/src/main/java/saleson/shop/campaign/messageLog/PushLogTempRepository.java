package saleson.shop.campaign.messageLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.campaign.PushLogTemp;

public interface PushLogTempRepository extends JpaRepository<PushLogTemp, Long>, QuerydslPredicateExecutor<PushLogTemp> {
}
