package saleson.shop.giftgroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.GiftGroup;

public interface GiftGroupRepository extends JpaRepository<GiftGroup, Long>, QuerydslPredicateExecutor<GiftGroup> {
}
