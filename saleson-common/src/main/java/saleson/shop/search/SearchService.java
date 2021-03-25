package saleson.shop.search;

import java.util.List;

import saleson.shop.search.domain.Search;
import saleson.shop.search.support.SearchRecommendParam;

public interface SearchService {
	
	/**
	 * 조건에 해당하는 데이터 수
	 * @param param
	 * @return
	 */
	int getSearchCount(SearchRecommendParam param);

	/**
	 * 조건에 해당하는 데이터 목록
	 * @param param
	 * @return
	 */
	List<Search> getSearchList(SearchRecommendParam param);
	
	/**
	 * 조건에 해당하는 데이터 조회
	 * @param param
	 * @return
	 */
	Search getSearch(SearchRecommendParam param);
	
	/**
	 * ID로 데이터 조회
	 * @param searchId
	 * @return
	 */
	Search getSearchById(int searchId);
	
	/**
	 * 데이터 등록
	 * @param search
	 */
	void insertSearch(Search search);
	
	/**
	 * 데이터 수정
	 * @param search
	 */
	void updateSearch(Search search);
	
	/**
	 * ID로 데이터 삭제
	 * @param searchId
	 */
	void deleteSearchByParam(SearchRecommendParam param);
	
	/**
	 * 메인페이지 추천검색어 조회
	 * @param param
	 * @return
	 */
	Search getSearchForFront(SearchRecommendParam param);
}
