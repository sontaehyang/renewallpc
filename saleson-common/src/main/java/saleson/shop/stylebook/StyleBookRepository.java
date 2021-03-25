package saleson.shop.stylebook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.stylebook.StyleBook;

public interface StyleBookRepository extends JpaRepository<StyleBook, Long>, QuerydslPredicateExecutor<StyleBook> {
}
