package saleson.shop.categoriesfilter;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.CategoriesFilter;
import saleson.model.FilterCode;
import saleson.model.FilterGroup;
import saleson.model.ItemFilter;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categoriesfilter.support.CategoriesFilterDto;
import saleson.shop.categoriesfilter.support.FilterGroupDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface CategoriesFilterService {

    /**
     * 카테고리 필터 목록 조회
     * @param predicate
     * @param pageable
     * @return
     */
    Page<FilterGroup> getFilterGroupList(Predicate predicate, Pageable pageable);

    /**
     * 카테고리 필터 조회
     * @param id
     * @return
     */
    Optional<FilterGroup> getFilterGroupById(Long id);

    /**
     * 카테고리 필터 저장
     * @param filterGroup
     * @param newCodeList
     * @throws Exception
     */
    void saveFilter(FilterGroup filterGroup, List<FilterCode> newCodeList) throws Exception;

    /**
     * 카테고리 필터 삭제
     * @param idArray
     */
    void deleteFilter(String[] idArray) throws Exception;

    /**
     * 해당 카테고리 경로별 필터 그룹 목록 조회
     * 중족 필터 그룹 제거
     * @param categoryId
     * @return
     */
    List<FilterGroup> getBreadcrumbFilterGroupList(int categoryId);

    /**
     * 카테고리별 필터 그룹 목록 조회
     * @param categoryId
     * @return
     */
    List<FilterGroup> getFilterGroupList(int categoryId);

    /**
     * 상품 지정 필터 그룹 조회
     * @param itemId
     * @return
     */
    Iterable<ItemFilter> getItemFilterList(int itemId);

    /**
     * 카테고리별 필터 그룹 등록
     * @param categoryId
     * @param filterGroupIdSet
     */
    void saveCategoriesFilter(int categoryId, Set<Long> filterGroupIdSet) throws Exception;

    /**
     * 상품 지정 필터 등록
     * @param itemId
     * @param filterCodes
     */
    void saveItemFilter(int itemId, Map<String, List<String>> filterCodes) throws Exception;

    /**
     * 카테고리 필터 목록 조회
     * @param dto
     * @return
     */
    Iterable<CategoriesFilter> getCategoriesFilters(CategoriesFilterDto dto);

    /**
     * 카테고리별 필터 초기화
     * @param currentCategories
     * @param childCategories
     */
    void initCategoryFilter(Categories currentCategories,
                           List<Categories> childCategories);
}
