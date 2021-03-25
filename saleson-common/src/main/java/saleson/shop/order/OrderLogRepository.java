package saleson.shop.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.shop.order.domain.OrderLog;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long>, QuerydslPredicateExecutor<OrderLog> {
}
