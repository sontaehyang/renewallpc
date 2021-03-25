package saleson.shop.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.onlinepowers.framework.sequence.service.SequenceService;

import saleson.shop.search.domain.Search;
import saleson.shop.search.support.SearchRecommendParam;

@Service("searchService")
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private SearchMapper searchMapper;

	@Override
	public int getSearchCount(SearchRecommendParam param) {
		return searchMapper.getSearchCount(param);
	}

	@Override
	public List<Search> getSearchList(SearchRecommendParam param) {
		return searchMapper.getSearchList(param);
	}

	@Override
	public Search getSearch(SearchRecommendParam param) {
		return searchMapper.getSearch(param);
	}

	@Override
	public Search getSearchById(int searchId) {
		return searchMapper.getSearchById(searchId);
	}

	@Override
	public void insertSearch(Search search) {
		search.setSearchId(sequenceService.getId("OP_SEARCH"));
		searchMapper.insertSearch(search);
	}

	@Override
	public void updateSearch(Search search) {
		searchMapper.updateSearch(search);
	}

	@Override
	public void deleteSearchByParam(SearchRecommendParam param) {
		for (String searchId : param.getId()) {
			if (!StringUtils.isEmpty(searchId)) {
				searchMapper.deleteSearchById(Integer.parseInt(searchId));
			}
		}
	}
	
	@Override
	public Search getSearchForFront(SearchRecommendParam param) {
		return searchMapper.getSearchForFront(param);
	}
}
