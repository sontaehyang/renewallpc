package saleson.api.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.api.search.domain.BestKeywordInfo;

import java.util.List;

@Service("ApiSearchService")
public class SearchServiceImpl implements SearchService{

    @Autowired
    private SearchMapper searchMapper;

    @Override
    public List<BestKeywordInfo> bestKeyword() {
        return searchMapper.bestKeyword();
    }
}
