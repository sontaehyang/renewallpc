package saleson.shop.giftitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.GiftItem;

public interface GiftItemRepository extends JpaRepository<GiftItem, Long>, QuerydslPredicateExecutor<GiftItem> {
}
