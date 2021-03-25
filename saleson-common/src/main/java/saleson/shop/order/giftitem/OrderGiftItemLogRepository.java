package saleson.shop.order.giftitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.OrderGiftItemLog;

public interface OrderGiftItemLogRepository extends JpaRepository<OrderGiftItemLog, Long>, QuerydslPredicateExecutor<OrderGiftItemLog> {
}
