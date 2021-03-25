package saleson.shop.categoriesfilter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.FilterCode;
import saleson.shop.categoriesfilter.query.FilterCodeQuery;

import java.util.List;

public interface FilterCodeRepository extends JpaRepository<FilterCode, Long>, QuerydslPredicateExecutor<FilterCode> {

    @Modifying
    @Query(value = FilterCodeQuery.DELETE_BY_FILTER_GROUP_ID, nativeQuery = true)
    void deleteByFilterGroupId(@Param("filterGroupId") Long filterGroupId);

    @Modifying
    @Query(value = FilterCodeQuery.DELETE_BY_FILTER_GROUP_IDS, nativeQuery = true)
    void deleteByFilterGroupIds(@Param("filterGroupIds") List<Long> filterGroupIds);

    @Modifying
    @Query(value = FilterCodeQuery.DELETE_BY_FILTER_CODE_IDS, nativeQuery = true)
    void deleteByFilterCodeIds(@Param("filterCodeIds") List<Long> filterCodeIds);
}
