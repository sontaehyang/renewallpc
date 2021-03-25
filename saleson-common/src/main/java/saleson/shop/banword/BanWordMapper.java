package saleson.shop.banword;

import java.util.List;

import saleson.shop.banword.domain.BanWord;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import com.onlinepowers.framework.web.domain.SearchParam;

@Mapper("banWordMapper")
public interface BanWordMapper {
	public List<BanWord> getBanWordListAll();

	/**
	 * 검색 조건에 해당하는 금지어 수를 가져온다.
	 * @param searchParam
	 * @return
	 */
	public int getBanWordCount(SearchParam searchParam);

	
	/**
	 * 검색 조건에 해당하는 금지어 목록을를 가져온다.
	 * @param searchParam
	 * @return
	 */
	public List<BanWord> getBanWordList(SearchParam searchParam);
	
	
	/**
	 * banWordId에 해당하느 금지어를 가져온다. 
	 * @param banWordId
	 * @return
	 */
	public BanWord getBanWordByBanWordId(int banWordId);
	
	
	/**
	 * 금지어를 등록한다.
	 * @param banWord
	 */
	public void insertBanWord(BanWord banWord);
	
	
	
	/**
	 * 금지어를 수정한다.
	 * @param banWord
	 */
	public void updateBanWord(BanWord banWord);
	
	
	
	/**
	 * 금지어를 삭제한다.
	 * @param banWord
	 */
	public void deleteBanWordByBanWordId(int banWordId);
	
	
	
}
