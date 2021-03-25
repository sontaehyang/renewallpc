package saleson.shop.keyword;

import java.util.List;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.keyword.domain.Keyword;

/**
 * @since	2017-05-15
 * @author	seungil.lee
 */

@Mapper("keywordMapper")
public interface KeywordMapper {

	// 검색시 키워드 추가(신규일경우) 혹은 weight증가(이미 등록 된 검색어)
	void mergeItemKeyword(Keyword keyword);
	
	// 아이템 키워드를 String으로 return
	List<String> getItemKeywordString();
	
	// 어제자 자동완성 검색어 삭제 
	void clearDailyKeyword();
	
	// 신규 자동완성 검색어 등록
	void setDailyKeyword(List<Keyword> keywordList);
}
