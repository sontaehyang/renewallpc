package saleson.shop.smsconfig.support;

import java.util.HashMap;

import saleson.shop.qna.domain.Qna;
import saleson.shop.smsconfig.domain.SmsConfig;

public class QnaCompleteSms extends SmsTemplate {
	public HashMap<String, String> getMap() {
		HashMap<String, String> map = new HashMap<>();

		map.put("siteName", "상점명");
		map.put("userName", "이름");
		map.put("email", "이메일");
		map.put("orderCode", "주문번호");
		map.put("itemName", "상품명");
		map.put("qnaGroup", "QNA-GROUP");
		map.put("qnaType", "QNA-TYPE");
		map.put("question", "문의내용");
		
		return map;
	}
	
	public QnaCompleteSms() {
		this.setMap(this.getMap());
	}
	
	public QnaCompleteSms(Qna qna, SmsConfig smsConfig) {
		
		super(smsConfig);
		
		HashMap<String, String> map = new HashMap<>();
		map.put("userName", qna.getUserName());
		map.put("email", qna.getEmail());
		map.put("orderCode", qna.getOrderCode());
		map.put("itemName", qna.getItemName());
		map.put("qnaGroup", qna.getQnaGroup());
		map.put("qnaType", qna.getQnaType());
		map.put("question", qna.getQuestion());
	
		this.setMap(map);
	}
}
