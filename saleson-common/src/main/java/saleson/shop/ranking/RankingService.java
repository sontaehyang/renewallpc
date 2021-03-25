package saleson.shop.ranking;

import java.util.List;

import saleson.shop.categories.domain.Group;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.item.domain.Item;
import saleson.shop.ranking.domain.Ranking;
import saleson.shop.ranking.support.RankingParam;
import saleson.shop.rankingbatch.support.RankingBatchParam;

public interface RankingService {

	/**
	 * 현재 카테고리에 등록된 랭킹정보를 가져옴.
	 * @param rankingParam
	 * @return
	 */
	public List<Ranking> getRankingList(RankingParam rankingParam);

	
	/**
	 * 해당 카테고리에 실제 랭킹 상품을 조회함.
	 * 매출액 순위 + 판매수량 순위 = 랭킹순위
	 * @param rankingParam
	 * @return
	 */
	public List<Item> getSaleRankingList(RankingParam rankingParam);


	/**
	 * 카테고리별 상품 순위를 저장한다.
	 * @param rankingParam
	 */
	public void save(RankingParam rankingParam);


	/**
	 * 프론트에 뿌려질 상품 랭킹 목록
	 * (팀, 그룹, 카테고리)
	 * @param rankingParam
	 * @return
	 */
	public List<Item> getRankingListForFront(RankingParam rankingParam);
	
	
	/**
	 * 팀별 랭킹 상품 목록 - 메인 and 장바구니
	 * 
	 * @param rankingParam
	 * @return
	 */
	public List<CategoriesTeam> getRankingListForMain(int limit);
	
	
	public List<CategoriesGroup> getGroupRankingList(String code);
	
	/**
	 * 프론트에 뿌려질 상품 랭킹 목록
	 * (팀, 그룹, 카테고리)
	 * @param rankingParam
	 * @return
	 */
	public List<Item> getRankingListForGroupAndCategory(RankingParam rankingParam);

	/**
	 * 일배치로 저장된 랭킹을 조회
	 * @param rankingParam
	 * @return
	 */
	public List<Item> getRankingItemListByParam(RankingParam rankingParam);

	/**
	 * 베스트(랭킹) 상품 그룹별 한방조회 
	 * @param rankingParam
	 * @return
	 */
	public List<Group> getBestItemsForFrontByGroups(RankingParam rankingParam);
	
}
