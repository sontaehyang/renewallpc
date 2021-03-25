package saleson.shop.categoriesfilter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.ItemFilter;
import saleson.shop.categoriesfilter.query.CategoriesFilterQuery;
import saleson.shop.categoriesfilter.query.ItemFilterQuery;

import java.util.List;

public interface ItemFilterRepository extends JpaRepository<ItemFilter, Long>, QuerydslPredicateExecutor<ItemFilter> {

    @Modifying
    @Query(value = ItemFilterQuery.DELETE_BY_CATEGORY_ID, nativeQuery = true)
    void deleteByCategoryId(@Param("categoryIds") List<Integer> categoryIds);

    @Modifying
    @Query(value = ItemFilterQuery.DELETE_BY_CATEGORY_ID + ItemFilterQuery.WHERE_ADD_NOT_IN_FILTER_GROUP_ID, nativeQuery = true)
    void deleteByCategoryId(@Param("categoryIds") List<Integer> categoryIds,
                            @Param("filterGroupIds") List<Long> filterGroupIds);

    @Modifying
    @Query(value = ItemFilterQuery.DELETE_BY_CATEGORY_ID + ItemFilterQuery.WHERE_ADD_IN_FILTER_GROUP_ID, nativeQuery = true)
    void deleteByMoveCategoryId(@Param("categoryIds") List<Integer> categoryIds,
                                @Param("filterGroupIds") List<Long> filterGroupIds);


    @Modifying
    @Query(value = ItemFilterQuery.DELETE_BY_ITEM_ID, nativeQuery = true)
    void deleteByItemId(@Param("itemId") Integer itemId);

    @Modifying
    @Query(value = ItemFilterQuery.DELETE_BY_FILTER_GROUP_IDS, nativeQuery = true)
    void deleteByFilterGroupIds(@Param("filterGroupIds") List<Long> filterGroupIds);

    @Modifying
    @Query(value = ItemFilterQuery.DELETE_BY_FILTER_CODE_IDS, nativeQuery = true)
    void deleteByFilterCodeIds(@Param("filterCodeIds") List<Long> filterCodeIds);
}
