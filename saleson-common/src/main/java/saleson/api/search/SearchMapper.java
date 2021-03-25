package saleson.api.search;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import saleson.api.search.domain.BestKeywordInfo;

import java.util.List;

@Mapper("ApiSearchMapper")
public interface SearchMapper {
    public List<BestKeywordInfo> bestKeyword();
}
