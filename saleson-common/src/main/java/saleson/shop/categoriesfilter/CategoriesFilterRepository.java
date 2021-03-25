package saleson.shop.categoriesfilter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.CategoriesFilter;
import saleson.shop.categoriesfilter.query.CategoriesFilterQuery;

import java.util.List;

public interface CategoriesFilterRepository extends JpaRepository<CategoriesFilter, Long>, QuerydslPredicateExecutor<CategoriesFilter> {

    @Query(value = CategoriesFilterQuery.GET_FILTER_GROUP_ID_LIST_BY_CATEGORY_IDS, nativeQuery = true)
    List<Long> getFilterGroupIdListByCategoryIds(
            @Param("ids") List<Integer> ids
    );

    @Modifying
    @Query(value = CategoriesFilterQuery.DELETE_BY_CATEGORY_IDS, nativeQuery = true)
    void deleteByCategoryIds(@Param("ids") List<Integer> ids);

    @Modifying
    @Query(value = CategoriesFilterQuery.DELETE_BY_FILTER_GROUP_IDS, nativeQuery = true)
    void deleteByFilterGroupIds(@Param("filterGroupIds") List<Long> filterGroupIds);
}
