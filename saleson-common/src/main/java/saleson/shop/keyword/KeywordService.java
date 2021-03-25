package saleson.shop.keyword;

import saleson.shop.item.support.ItemParam;

/**
 * @since	2017-05-15
 * @author	seungil.lee
 */

public interface KeywordService {
	// 검색시 키워드 추가(신규일경우) 혹은 weight증가(이미 등록 된 검색어)
	public void mergeItemKeyword(ItemParam itemParam);
	
	// 일일 키워드 설정
	public void setKeywordDaily();
}
