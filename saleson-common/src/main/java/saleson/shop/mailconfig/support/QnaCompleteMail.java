package saleson.shop.mailconfig.support;

import com.onlinepowers.framework.util.DateUtils;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;
import saleson.shop.config.domain.Config;
import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.qna.domain.Qna;

import java.util.HashMap;



public class QnaCompleteMail extends MailTemplate {
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
		map.put("subject", "문의제목");
		map.put("created_date", "문의일자");
		map.put("answer", "답변내용");
		map.put("title", "답변제목");
		map.put("answer_date", "답변일자");
		map.put("siteUrl", "사이트 주소");
		map.put("compName", "회사이름");
		return map;
	}
	
	public QnaCompleteMail() {
		this.setMap(this.getMap());
	}
	
	public QnaCompleteMail(Qna qna, MailConfig mailConfig) {
		
		super(mailConfig);
		
		
		
		
		HashMap<String, String> map = new HashMap<>();
		map.put("userName", qna.getUserName());
		map.put("email", qna.getEmail());
		map.put("orderCode", qna.getOrderCode());
		map.put("itemName", qna.getItemName());
		map.put("qnaGroup", qna.getQnaGroup());
		map.put("qnaType", qna.getQnaType());
		map.put("question", conventNewLine(qna.getQuestion()));
		map.put("subject", qna.getSubject());
		map.put("created_date", DateUtils.date(qna.getCreatedDate()));
		map.put("answer", conventNewLine(qna.getQnaAnswer().getAnswer()));
		map.put("title", qna.getQnaAnswer().getTitle());
		map.put("answer_date", DateUtils.date(qna.getQnaAnswer().getAnswerDate()));
	
		
		Config config = ShopUtils.getConfig();
		map.put("siteName", config.getShopName());
		map.put("siteUrl", SalesonProperty.getSalesonUrlShoppingmall());
		map.put("compName", config.getCompanyName());
		
		this.setMap(map);
	}
	
	private String conventNewLine(String content){
		
		content = content.replace("\n", "<br/>");
		
		return content;
	}
}
