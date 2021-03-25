package saleson.shop.ranking;

import java.util.List;

import saleson.shop.ranking.support.RankingParam;

import com.onlinepowers.framework.orm.mybatis.annotation.MapperBatch;

@MapperBatch("rankingMapperBatch")
public interface RankingMapperBatch {


	/**
	 * 카테고리 URL에 해당하는 상품 순위 정보를 등록한다.
	 * @param rankingParam
	 */
	void insertRankingByParam(RankingParam rankingParam);

}
