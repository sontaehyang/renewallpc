package saleson.shop.giftitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.GiftItemRelation;

public interface GiftItemRelationRepository extends JpaRepository<GiftItemRelation, Long>, QuerydslPredicateExecutor<GiftItemRelation> {
}
