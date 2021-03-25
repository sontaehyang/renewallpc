package saleson.shop.giftgroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.GiftGroupItem;

public interface GiftGroupItemRepository extends JpaRepository<GiftGroupItem, Long>, QuerydslPredicateExecutor<GiftGroupItem> {
}
