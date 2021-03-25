package saleson.shop.rankingconfig;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.rankingconfig.domain.RankingConfig;

@Mapper("rankingConfigMapper")
public interface RankingConfigMapper {

	/**
	 * 랭킹 산정 조건 CODE 코드에 따른 랭킹 산정 정보 조회
	 * @param rankConfigCode 랭킹 산정 조건 CODE 코드 (해당 CODE가 없을경우 'default' 로 조회)
	 * @return
	 */
	public RankingConfig getRankingConfigByRankConfigCode(String rankConfigCode); 
	
	/**
	 *  랭킹 산정 정보 수정
	 * @param rankingConfig
	 * @return
	 */
	public int updateItemRankingConfig(RankingConfig rankingConfig);
}
