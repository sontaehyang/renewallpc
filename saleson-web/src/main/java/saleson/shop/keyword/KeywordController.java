package saleson.shop.keyword;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import saleson.common.utils.HangulUtils;
import saleson.shop.item.support.ItemParam;
import saleson.shop.keyword.domain.Keyword;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("keyword")
public class KeywordController {
	private static final Logger log = LoggerFactory.getLogger(KeywordController.class);

	@Autowired
	private KeywordService keywordService;

	@Autowired
	Environment environment;
	
	@PostMapping("auto-complete")
	@ResponseBody
	public List<Keyword> autoComplete(ItemParam itemParam) {
		// 배치에 등록 할 서비스 2017-05-15_seungil.lee
		// keywordService.setKeywordDaily();

		List<Keyword> list = new ArrayList<>();
		List<Keyword> result = new ArrayList<>();

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			JSONParser parser = new JSONParser();
			// 저장 된 json파일 불러오기
			fis = new FileInputStream(environment.getProperty("upload.root") + "/auto-complete.json");
			isr = new InputStreamReader(fis,"UTF-8");
			br = new BufferedReader(isr);

			JSONArray ja = (JSONArray)parser.parse(br);
			ObjectMapper om = new ObjectMapper();
			list = om.readValue(ja.toString(), new TypeReference<List<Keyword>>(){});
		} catch (Exception e) {
			log.warn("[Exception] JSONParser : {}", e.getMessage(), e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.warn("fis close", e);
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					log.warn("isr close", e);
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.warn("br close", e);
				}
			}
		}


		if (list != null && !list.isEmpty()) {
			for (Keyword keyword : list) {
				if (keyword.getKeywordSeperation() != null
					&& (StringUtils.isEmpty(itemParam.getQuery()) ||
						(!StringUtils.isEmpty(itemParam.getQuery()) && keyword.getKeywordSeperation().indexOf(HangulUtils.seperate(itemParam.getQuery())) > -1))
						&& result.size() < itemParam.getLimit()) {
					result.add(keyword);
				}
			}
		}
		
		return result;
	}
	
	@GetMapping("keyword-init")
	@ResponseBody
	public String init() {
		try {
			keywordService.setKeywordDaily();
		}
		catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return "error - " + e.getMessage();
		}
		return "ok";
	}
}
