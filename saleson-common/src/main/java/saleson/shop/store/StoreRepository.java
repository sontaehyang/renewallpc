package saleson.shop.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.Store;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, QuerydslPredicateExecutor<Store> {

    @Modifying
    @Query(value = "DELETE FROM OP_STORE WHERE ID IN (:ids)", nativeQuery = true)
    void deleteByIds(@Param("ids") List<Long> ids);

}
