package saleson.shop.keyword;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import saleson.common.utils.HangulUtils;
import saleson.shop.item.support.ItemParam;
import saleson.shop.keyword.domain.Keyword;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @since	2017-05-15
 * @author	seungil.lee
 */

@Service("keywordService")
public class KeywordServiceImpl implements KeywordService {
	private static final Logger log = LoggerFactory.getLogger(KeywordServiceImpl.class);
	
	@Autowired
	private KeywordMapper keywordMapper;

	@Autowired
	Environment environment;
	
	@Override
	public void mergeItemKeyword(ItemParam itemParam) {
		Keyword keyword = new Keyword();
		keyword.setKeyword(itemParam.getQuery());
		keyword.setKeywordSeperation(HangulUtils.seperate(itemParam.getQuery()));
		keyword.setKeywordType("2");
		keywordMapper.mergeItemKeyword(keyword);
	}
	
	@Override
	public void setKeywordDaily() {
		List<String> keywordList = keywordMapper.getItemKeywordString();
		
		Set<String> hashSet = new HashSet<String>();
		for (String keywordString : keywordList) {
			
			StringTokenizer st = new StringTokenizer(keywordString, ",");
			List<String> keywords = new ArrayList<>();
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				token = token.trim().toUpperCase();
			
				if (token.length() > 0) {
					keywords.add(token);
				}
			}

			hashSet.addAll(keywords);
		}
		
		// 키워드 중복 제거.
		keywordList = new ArrayList<String>(hashSet);

		if (!keywordList.isEmpty()) {
			keywordMapper.clearDailyKeyword();
			List<Keyword> keywords = new ArrayList<>();
			
			int i = 0;
			for (String keyword : keywordList) {
				keywords.add(new Keyword("1", keyword, HangulUtils.seperate(keyword))); 
				
				// 1회 등록 키워드 200개로 한정 (i % num -> num값 수정으로 keyword 등록 개수 조절 가능) 
				// : 테스트 당시 에러 발생으로 해당 코드 삽입, 추후 에러 없을시 삭제해도 무방 seungil.lee 
				if (i % 200 == 0 && i > 0) {
					if (!keywords.isEmpty()) {
						// 필터 된 키워드 개수가 1개 이상일 경우 등록
						keywordMapper.setDailyKeyword(keywords);
					}
					keywords = new ArrayList<>();
				}
				i++;
			}
	
			if (!keywords.isEmpty()) {
				keywordMapper.setDailyKeyword(keywords);
			}
			
			ObjectMapper om = new ObjectMapper();
	        // keywordList Object 를 JSON 문자열로 변환
	        String jsonStr = "";
			try {
				jsonStr = om.writeValueAsString(keywords);
			} catch (JsonProcessingException e1) {
				log.warn(e1.getMessage());
			}
			
			try{
				// utf-8로 upload.root에 json파일 생성
			    PrintWriter writer = new PrintWriter(environment.getProperty("upload.root")+"/auto-complete.json","utf-8");
			    writer.println(jsonStr);
			    writer.close();
			} catch (IOException e) {
				log.error("[Error] file 생성 오류 -  {}", e.getMessage());
			}
		}
		
	}
}
