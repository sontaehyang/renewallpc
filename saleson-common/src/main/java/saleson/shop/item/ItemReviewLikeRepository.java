package saleson.shop.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.review.ItemReviewLike;

public interface ItemReviewLikeRepository extends JpaRepository<ItemReviewLike, Long>, QuerydslPredicateExecutor<ItemReviewLike> {
}
