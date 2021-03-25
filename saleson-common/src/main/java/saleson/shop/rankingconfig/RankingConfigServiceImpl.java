package saleson.shop.rankingconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.rankingconfig.domain.RankingConfig;

@Service("rankingConfigService")
public class RankingConfigServiceImpl implements RankingConfigService{

	@Autowired
	private RankingConfigMapper rankingConfigMapper;
	
	@Override
	public RankingConfig getRankingConfigByRankConfigCode(String rankConfigCode) {
		return rankingConfigMapper.getRankingConfigByRankConfigCode(rankConfigCode);
	}

	@Override
	public int updateItemRankingConfig(RankingConfig rankingConfig) {
		return rankingConfigMapper.updateItemRankingConfig(rankingConfig);
	}

	
	
}
