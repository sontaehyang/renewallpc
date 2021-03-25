package saleson.shop.faq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.Faq;


public interface FaqRepository extends JpaRepository<Faq, Long>, QuerydslPredicateExecutor<Faq> {

}
