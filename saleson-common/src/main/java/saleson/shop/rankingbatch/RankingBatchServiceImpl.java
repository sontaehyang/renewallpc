package saleson.shop.rankingbatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.rankingbatch.support.RankingBatchParam;

@Service("rankingBatchService")
public class RankingBatchServiceImpl implements RankingBatchService{

	@Autowired
	private RankingBatchMapper rankingBatchMapper;
	
	@Override
	public void insertItemRankingBatch(RankingBatchParam param) {

		/**
		 * 상품 랭킹 등록
		 */
		rankingBatchMapper.insertItemRankingBatch(param);
		
		/**
		 * DATA_STATUS_CODE가 1인것을 지움
		 */
		rankingBatchMapper.deleteDataStatusByParam(param);
		
		/**
		 * DATA_STATUS_CODE가 0인것을 1로 수정
		 */
		rankingBatchMapper.updateDataStatusByParam(param);
		
	}
	
}
