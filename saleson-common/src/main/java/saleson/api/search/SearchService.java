package saleson.api.search;

import saleson.api.search.domain.BestKeywordInfo;

import java.util.List;

public interface SearchService {

    public List<BestKeywordInfo> bestKeyword();

}
