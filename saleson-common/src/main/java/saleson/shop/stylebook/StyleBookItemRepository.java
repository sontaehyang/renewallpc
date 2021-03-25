package saleson.shop.stylebook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.stylebook.StyleBookItem;

public interface StyleBookItemRepository extends JpaRepository<StyleBookItem, Long>, QuerydslPredicateExecutor<StyleBookItem> {
}
