package saleson.shop.ranking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import saleson.shop.categories.domain.Group;
import saleson.shop.categoriesteamgroup.domain.CategoriesGroup;
import saleson.shop.categoriesteamgroup.domain.CategoriesTeam;
import saleson.shop.item.domain.Item;
import saleson.shop.ranking.domain.Ranking;
import saleson.shop.ranking.support.RankingParam;

import com.onlinepowers.framework.exception.BusinessException;
import com.onlinepowers.framework.sequence.service.SequenceService;

@Service("rankingService")
public class RankingServiceImpl implements RankingService {

	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private RankingMapper rankingMapper;
	
	@Autowired
	private RankingMapperBatch rankingMapperBatch;
	
	@Override
	public List<Ranking> getRankingList(RankingParam rankingParam) {
		return rankingMapper.getRankingList(rankingParam);
	}

	@Override
	public List<Item> getSaleRankingList(RankingParam rankingParam) {
		return rankingMapper.getSaleRankingList(rankingParam);
	}

	@Override
	public void save(RankingParam rankingParam) {
		if (!rankingParam.hasParamValue()) {
			throw new BusinessException("카테고리 정보가 없습니다.");
		}
		
		
		// 1. 해당 카테고리에 순위 상품 삭제
		rankingMapper.deleteRankingByParam(rankingParam);
				
		
		if (!(rankingParam.getItemIds() == null || rankingParam.getItemIds().length == 0)) {
			// 2. 해당 카테고리에 상품 순위 정보 등록.
			int index = 0;
			for (int itemId : rankingParam.getItemIds()) {
				
				rankingParam.setItemId(itemId);
				rankingParam.setOrdering(index);
				rankingParam.setRankingId(sequenceService.getId("OP_RANKING"));
				
				rankingMapperBatch.insertRankingByParam(rankingParam);
				
				index++;
			}
		}
		
	}

	@Override
	public List<Item> getRankingListForFront(RankingParam rankingParam) {
		
		int limit = rankingParam.getLimit();
		
		List<Item> list = rankingMapper.getBestItemListByParamTargetDisplayItem(rankingParam);
		if (list.size() >= limit) {
			return list;
		}
		
		rankingParam.setLimit(limit - list.size());
		list.addAll(rankingMapper.getBestItemListByParamTargetRankingBatch(rankingParam));
		
		return list;
	}
	
	@Override
	public List<Group> getBestItemsForFrontByGroups(
			RankingParam rankingParam) {
		
		List<Group> groupItems = rankingMapper.getBestItemsForFrontByGroups(rankingParam);
		
		for (Group group : groupItems) {
			// Limit 이상 데이터는 제
			List<Item> newItems = new ArrayList<>();
			int i = 1;
			for (Item item : group.getItems()) {
				if (i >= rankingParam.getLimit()) {
					break;
				}
				
				newItems.add(item);
				i++;
			}
			group.setItems(newItems);
		}
		
		return groupItems;
	}

	@Override
	public List<CategoriesTeam> getRankingListForMain(int limit) {
		return rankingMapper.getRankingListForMain(limit);
	}

	@Override
	public List<CategoriesGroup> getGroupRankingList(String code) {
		return rankingMapper.getGroupRankingList(code);
	}
	
	@Override
	public List<Item> getRankingListForGroupAndCategory(RankingParam rankingParam) {
		List<Item> rankingItemList = new ArrayList<>();
		
		List<Ranking> rankingList = rankingMapper.getRankingList(rankingParam);
		if (rankingList.isEmpty()) {
			return rankingMapper.getSaleRankingList(rankingParam);
		}
		
		for (Ranking ranking : rankingList) {
			rankingItemList.add(ranking.getItem());
		}
		
		return rankingItemList;
	}
	
	@Override
	public List<Item> getRankingItemListByParam(RankingParam rankingParam) {
		return rankingMapper.getRankingItemListByParam(rankingParam);
	}

	
	
}
