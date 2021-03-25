package saleson.shop.order.giftitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.OrderGiftItem;

public interface OrderGiftItemRepository extends JpaRepository<OrderGiftItem, Long>, QuerydslPredicateExecutor<OrderGiftItem> {
}
