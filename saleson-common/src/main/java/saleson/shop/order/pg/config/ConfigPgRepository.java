package saleson.shop.order.pg.config;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import saleson.model.ConfigPg;

public interface ConfigPgRepository extends JpaRepository<ConfigPg, Long>, QuerydslPredicateExecutor<ConfigPg> {

}
