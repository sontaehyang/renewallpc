package saleson.shop.ranking;

import java.util.List;

import saleson.shop.categories.domain.Group;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.item.domain.Item;
import saleson.shop.ranking.domain.Ranking;
import saleson.shop.ranking.support.RankingParam;
import saleson.shop.rankingbatch.support.RankingBatchParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("rankingMapper")
public interface RankingMapper {

	/**
	 * 카테고리별 랭킹을 조회한다.
	 * @param rankingParam
	 * @return
	 */
	List<Ranking> getRankingList(RankingParam rankingParam);
	
	
	/**
	 * 해당 카테고리에 실제 랭킹 상품을 조회함.
	 * 매출액 순위 + 판매수량 순위 = 랭킹순위
	 * @param rankingParam
	 * @return
	 */
	List<Item> getSaleRankingList(RankingParam rankingParam);


	/**
	 * 카테고리 URL에 해당하는 모든 랭킹 상품을 삭제한다.
	 * @param rankingParam
	 */
	void deleteRankingByParam(RankingParam rankingParam);


	/**
	 * 카테고리 URL에 해당하는 상품 순위 정보를 등록한다.
	 * @param rankingParam
	 */
	void insertRankingByParam(RankingParam rankingParam);


	/**
	 * 팀별 랭킹 상품 목록 - 메인
	 * 
	 * @param rankingParam
	 * @return
	 */
	List<CategoriesTeam> getRankingListForMain(int limit);
	
	List<CategoriesGroup> getGroupRankingList(String code);
	
	/**
	 * 해당 카테고리에 실제 랭킹 상품을 조회함.
	 * 매출액 순위 + 판매수량 순위 = 랭킹순위
	 * @param rankingParam
	 * @return
	 */
	List<Item> getSaleRankingListForGroupAndCategory(RankingParam rankingParam);
	
	/**
	 * 일배치로 저장된 랭킹을 조회
	 * @param rankingParam
	 * @return
	 */
	public List<Item> getRankingItemListByParam(RankingParam rankingParam);
	
	/**
	 * 관리자가 지정한 랭킹을 조회
	 * @param rankingParam
	 * @return
	 */
	public List<Item> getBestItemListByParamTargetDisplayItem(RankingParam rankingParam);

	/**
	 * 일배치로 지정된 랭킹을 조회 - 단. 관리자가 지정한 랭킹 상품은 재외 시킴
	 * @param rankingParam
	 * @return
	 */
	public List<Item> getBestItemListByParamTargetRankingBatch(RankingParam rankingParam);


	List<Group> getBestItemsForFrontByGroups(RankingParam rankingParam);
}
