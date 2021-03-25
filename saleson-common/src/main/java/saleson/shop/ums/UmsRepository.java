package saleson.shop.ums;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.Ums;

public interface UmsRepository extends JpaRepository<Ums, Long>, QuerydslPredicateExecutor<Ums> {
}
