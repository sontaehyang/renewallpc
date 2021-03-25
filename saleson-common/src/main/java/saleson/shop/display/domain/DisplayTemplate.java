package saleson.shop.display.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisplayTemplate {

	private static final Logger log = LoggerFactory.getLogger(DisplayTemplate.class);

	private String displayTemplateCode;
	private String displaySettingValue;
	
	public String getDisplayTemplateCode() {
		return displayTemplateCode;
	}
	public void setDisplayTemplateCode(String displayTemplateCode) {
		this.displayTemplateCode = displayTemplateCode;
	}
	public String getDisplaySettingValue() {
		return displaySettingValue;
	}
	
	public void setDisplaySettingValue(String displaySettingValue) {
		this.displaySettingValue = displaySettingValue;
	}
	
	@SuppressWarnings("unchecked")
	public List<DisplaySettingValue> getDisplaySettingValueList () {
		
		/*
		 
		List<DisplaySettingValue> -> JSON DATA SAMPLE
		
		[
			{"key":"Item1","type":3,"count":3,"rollingYN":"Y","etc":"상품1"},
			{"key":"Item2","type":3,"count":2,"rollingYN":"N","etc":"상품2"},
			{"key":"Item3","type":3,"count":1,"rollingYN":"N","etc":"상품3"},
			{"key":"Item1","type":2,"count":3,"rollingYN":"Y","etc":"에디터1"},
			{"key":"Item2","type":2,"count":2,"rollingYN":"N","etc":"에디터2"},
			{"key":"Item2","type":1,"count":1,"rollingYN":"N","etc":"이미지1"},
			{"key":"Item2","type":1,"count":3,"rollingYN":"N","etc":"이미지2"}
		] 
		*/
		
		List<DisplaySettingValue> list = null;
		
		if (StringUtils.isEmpty(displaySettingValue)) {
			return null;
		}
		
		try {
		
			list = (List<DisplaySettingValue>) JsonViewUtils.jsonToObject(displaySettingValue, new TypeReference<List<DisplaySettingValue>>() {});
			
		}catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}
		
		return list;
	}
	
}
