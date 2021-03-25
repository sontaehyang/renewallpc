package saleson.shop.giftitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.GiftItemLog;

public interface GiftItemLogRepository extends JpaRepository<GiftItemLog, Long>, QuerydslPredicateExecutor<GiftItemLog> {
}
