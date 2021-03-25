package saleson.shop.banword;

import java.util.List;

import saleson.shop.banword.domain.BanWord;

import com.onlinepowers.framework.web.domain.SearchParam;

public interface BanWordService {
	public List<BanWord> getBanWordListAll();

	public String checkBanWord(String text);

	
	/**
	 * 검색 조건에 해당 하는 금지어 수를 가져온다.
	 * @param searchParam
	 * @return
	 */
	public int getBanWordCount(SearchParam searchParam);

	
	/**
	 * 검색 조건에 해당 하는 금지어 목록을 가져온다.
	 * @param searchParam
	 * @return
	 */
	public List<BanWord> getBanWordList(SearchParam searchParam);

	
	/**
	 * 금지어를 등록한다.
	 * @param banWord
	 */
	public void insertBanWord(BanWord banWord);

	
	/**
	 * 금지어를 조회.
	 * @param banWordId
	 * @return
	 */
	public BanWord getBanWordByBanWordId(int banWordId);

	
	/**
	 * 금지어 삭제
	 * @param banWordId
	 */
	public void deleteBanWordByBanWordId(int banWordId);
}
