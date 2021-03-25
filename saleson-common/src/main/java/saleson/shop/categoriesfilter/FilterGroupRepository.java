package saleson.shop.categoriesfilter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.FilterGroup;
import saleson.shop.categoriesfilter.query.FilterGroupQuery;

import java.util.List;

public interface FilterGroupRepository extends JpaRepository<FilterGroup, Long>, QuerydslPredicateExecutor<FilterGroup> {

    @Modifying
    @Query(value = FilterGroupQuery.DELETE_BY_IDS, nativeQuery = true)
    void deleteByIds(@Param("ids") List<Long> ids);

}
