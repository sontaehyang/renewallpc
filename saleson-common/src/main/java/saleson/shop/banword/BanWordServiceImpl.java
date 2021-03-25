package saleson.shop.banword;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.banword.domain.BanWord;

import com.onlinepowers.framework.exception.BusinessException;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.SecurityUtils;
import com.onlinepowers.framework.web.domain.SearchParam;

@Service("banWordService")
public class BanWordServiceImpl implements BanWordService {
	@Autowired
	private BanWordMapper banWordMapper;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Override
	public int getBanWordCount(SearchParam searchParam) {
		return banWordMapper.getBanWordCount(searchParam);
	}

	@Override
	public List<BanWord> getBanWordList(SearchParam searchParam) {
		return banWordMapper.getBanWordList(searchParam);
	}
	
	@Override
	public List<BanWord> getBanWordListAll() {
		return banWordMapper.getBanWordListAll();
	}

	@Override
	public String checkBanWord(String text) {
		List<BanWord> banWords = banWordMapper.getBanWordListAll();
		String bannedWord = null;
		
		for (BanWord banWord : banWords) {
			if (text.indexOf(banWord.getBanWord()) > -1) {
				bannedWord = banWord.getBanWord();
				break;
			}
		}
		
		return bannedWord;
	}

	@Override
	public void insertBanWord(BanWord banWord) {
		banWord.setBanWordId(sequenceService.getId("OP_BAN_WORD"));
		banWord.setUserId(SecurityUtils.getCurrentUserId());
		banWord.setUserName(SecurityUtils.getCurrentUser().getUserName());
		
		// 중복조회
		SearchParam searchParam = new SearchParam();
		searchParam.setWhere("BAN_WORD");
		searchParam.setQuery(banWord.getBanWord());
		
		int banWordCount = banWordMapper.getBanWordCount(searchParam);
		
		if (banWordCount > 0) {
			throw new BusinessException("'" + banWord.getBanWord() + "'는 이미 등록된 금칙어 입니다.");
		}
		
		banWordMapper.insertBanWord(banWord);
	}

	@Override
	public BanWord getBanWordByBanWordId(int banWordId) {
		return banWordMapper.getBanWordByBanWordId(banWordId);
	}

	@Override
	public void deleteBanWordByBanWordId(int banWordId) {
		banWordMapper.deleteBanWordByBanWordId(banWordId);
	}

	

}
