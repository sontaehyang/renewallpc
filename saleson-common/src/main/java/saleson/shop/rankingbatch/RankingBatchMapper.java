package saleson.shop.rankingbatch;

import saleson.shop.rankingbatch.support.RankingBatchParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("rankingBatchMapper")
public interface RankingBatchMapper {

	/**
	 * 랭킹을 일배치로 저장한다.
	 * @param rankingBatchParam
	 */
	void insertItemRankingBatch(RankingBatchParam rankingBatchParam);
	
	/**
	 * 생성된 데이터를 활성화
	 * @param rankingBatchParam
	 */
	void updateDataStatusByParam(RankingBatchParam rankingBatchParam);
	
	/**
	 * 사용중인 데이터를 삭제
	 * @param rankingBatchParam
	 */
	void deleteDataStatusByParam(RankingBatchParam rankingBatchParam);
}
