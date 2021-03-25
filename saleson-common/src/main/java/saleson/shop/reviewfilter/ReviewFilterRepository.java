package saleson.shop.reviewfilter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.review.ReviewFilter;
import saleson.shop.reviewfilter.query.ReviewFilterQuery;

import java.util.List;

public interface ReviewFilterRepository extends JpaRepository<ReviewFilter, Long>, QuerydslPredicateExecutor<ReviewFilter> {

    @Query(value = ReviewFilterQuery.GET_FILTER_GROUP_ID_LIST_BY_CATEGORY_IDS, nativeQuery = true)
    List<Long> getFilterGroupIdListByCategoryIds(
            @Param("ids") List<Integer> ids
    );

    @Modifying
    @Query(value = ReviewFilterQuery.DELETE_BY_CATEGORY_IDS, nativeQuery = true)
    void deleteByCategoryIds(@Param("ids") List<Integer> ids);

    @Modifying
    @Query(value = ReviewFilterQuery.DELETE_BY_FILTER_GROUP_IDS, nativeQuery = true)
    void deleteByFilterGroupIds(@Param("filterGroupIds") List<Long> filterGroupIds);

}
