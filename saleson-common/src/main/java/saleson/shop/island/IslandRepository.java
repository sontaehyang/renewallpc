package saleson.shop.island;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import saleson.model.Island;

@Repository
public interface IslandRepository extends JpaRepository<Island, Long>, QuerydslPredicateExecutor<Island> {
}
