package saleson.shop.rankingbatch;

import saleson.shop.rankingbatch.support.RankingBatchParam;


public interface RankingBatchService {

	/**
	 * 랭킹을 일배치로 저장한다.
	 * 
	 * 1 : TOP_100 
	 * 2 : CATEGORY GROUP MAIN
	 * 3 : CATEGORY
	 * 
	 * @param rankingType
	 */
	public void insertItemRankingBatch(RankingBatchParam param);
	
}
