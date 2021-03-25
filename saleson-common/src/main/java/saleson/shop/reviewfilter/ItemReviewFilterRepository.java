package saleson.shop.reviewfilter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.review.ItemReviewFilter;

public interface ItemReviewFilterRepository extends JpaRepository<ItemReviewFilter, Long>, QuerydslPredicateExecutor<ItemReviewFilter> {
}
