package saleson.shop.smsconfig.support;

import java.util.HashMap;
import java.util.LinkedHashMap;

import saleson.shop.smsconfig.domain.SmsConfig;

import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;

public class SmsTemplate {
	private HashMap<String, String> map;
	private SmsConfig smsConfig;
	private HashMap<String, String> codes;
	
	public SmsTemplate() {}
	
	public SmsTemplate(SmsConfig smsConfig) {
		this.smsConfig = smsConfig;
	}
	
	/**
	 * code <templateId, messageCode>
	 * @return
	 */
	public HashMap<String, String> getTemplateCodes() {
		if (codes == null) {
			codes = new LinkedHashMap<>();
			
			codes.put("order_deposit_wait", "입금대기");
			codes.put("order_cready_payment", "결제완료");
			codes.put("order_delivering", "배송중");
			
			codes.put("pwsearch", "PW확인");  // PW확인메일
			
			codes.put("member_join", "회원가입");  // 회원가입안내메일
			codes.put("qna_complete", "문의완료");  // 문의완료
			codes.put("user_sms", "회원SMS발송");
			
		}
		
		return codes;
	}
	
	public String getTemplateCodeTitle(String key) {
		HashMap<String, String> code = this.getTemplateCodes();
		if (ValidationUtils.isNotNull(code.get(key))) {
			//return MessageUtils.getMessage(code.get(key));
			return code.get(key);
		}
		
		return "";
	}
	
	public String getFirstTemplateCodeKey() {
		HashMap<String, String> code = this.getTemplateCodes();
		
		String r = "";
		if (code != null) {
			int i = 0;
			for (String s : code.keySet()) {
				if (i == 0) {
					r = s;
					break;
				}

				i++;
			}
		}
		return r;
	}
	
	public HashMap<String, String> getCodes() {
		
		LinkedHashMap<String, String> code = new LinkedHashMap<>();
		for(String key : this.map.keySet()) {
			code.put(this.getTemplatePattern(key), this.map.get(key));
		}
		
		return code;
	}
	
	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}
	
	/**
	 * 템플릿에 사용된 패턴을 리턴함
	 * @param codeName
	 * @return
	 */
	private String getTemplatePattern(String codeName) {
		String str = "";
		if (StringUtils.isNotEmpty(codeName)) {
			str = "{" + StringUtils.convertToUnderScore(codeName) + "}";
		}
		
		return str;
	}
	
	/**
	 * 최종 전송할 데이터를 만듬
	 * @return
	 */
	public SmsConfig getSmsConfig() {
		
		if (smsConfig == null || map == null) {
			return null;
		}
		
		String buyerTitle = smsConfig.getBuyerTitle();
		String buyerContent = smsConfig.getBuyerContent();
		
		String adminTitle = smsConfig.getAdminTitle();
		String adminContent = smsConfig.getAdminContent();

		if ("Y".equals(smsConfig.getBuyerSendFlag()) || "Y".equals(smsConfig.getAdminSendFlag())) {
			for(String key : map.keySet()) {
				
				String fieldValue = (String) map.get(key);
				fieldValue = fieldValue == null ? "" : fieldValue;
				fieldValue = fieldValue.equals("null") ? "" : fieldValue;
				
				String pattern = this.getTemplatePattern(key);
				
				if ("Y".equals(smsConfig.getBuyerSendFlag())) {
					buyerTitle = buyerTitle.replace(pattern, fieldValue);
					buyerContent = buyerContent.replace(pattern, fieldValue);
				}
				
				if ("Y".equals(smsConfig.getAdminSendFlag())) {
					adminTitle = adminTitle.replace(pattern, fieldValue);
					adminContent = adminContent.replace(pattern, fieldValue);
				}
			}
		}
		
		smsConfig.setBuyerTitle(buyerTitle);
		smsConfig.setBuyerContent(buyerContent);
		
		smsConfig.setAdminTitle(adminTitle);
		smsConfig.setAdminContent(adminContent);
		
		return smsConfig;
	}
}
