package saleson.shop.reviewfilter;

import saleson.model.FilterGroup;
import saleson.model.review.ItemReviewFilter;
import saleson.model.review.ReviewFilter;
import saleson.shop.reviewfilter.support.ReviewFilterDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ReviewFilterService {

    /**
     * 카테고리별 리뷰 필터 그룹 목록 조회
     * @param categoryId
     * @return
     */
    List<FilterGroup> getFilterGroupList(int categoryId);

    /**
     * 해당 카테고리 경로별 필터 그룹 목록 조회
     * 중족 필터 그룹 제거
     * @param categoryId
     * @return
    */
    List<FilterGroup> getBreadcrumbFilterGroupList(int categoryId);

    /**
     * 카테고리별 리뷰 필터 그룹 등록
     * @param categoryId
     * @param filterGroupIdSet
     */
    void saveReviewFilter(int categoryId, Set<Long> filterGroupIdSet) throws Exception;

    /**
     * 카테고리 리뷰 필터 목록 조회
     * @param dto
     * @return
     */
    Iterable<ReviewFilter> getReviewFilters(ReviewFilterDto dto);

    /**
     * 상품 리뷰 지정 필터 등록
     * @param itemReviewId
     * @param filterCodes
     */
    void saveItemFilter(int itemReviewId, int itemId, Map<String, List<String>> filterCodes) throws Exception;

    /**
     * 상품 리뷰 필터 목록 조회
     * @param filters
     * @return
     */
    List<FilterGroup> getFilterGroupsByItemReviewFilters(List<ItemReviewFilter> filters);
}
