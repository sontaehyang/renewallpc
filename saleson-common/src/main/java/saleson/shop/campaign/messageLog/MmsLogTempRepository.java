package saleson.shop.campaign.messageLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.campaign.MmsLogTemp;

public interface MmsLogTempRepository extends JpaRepository<MmsLogTemp, Long>, QuerydslPredicateExecutor<MmsLogTemp> {
}
