package saleson.shop.campaign.messageLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.campaign.SmsLogTemp;

public interface SmsLogTempRepository extends JpaRepository<SmsLogTemp, Long>, QuerydslPredicateExecutor<SmsLogTemp> {
}
