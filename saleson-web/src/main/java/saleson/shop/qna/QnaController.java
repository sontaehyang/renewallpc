package saleson.shop.qna;

import com.onlinepowers.framework.annotation.handler.Authorize;
import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.util.CodeUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import saleson.common.utils.EmojiUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.qna.domain.Qna;

import java.util.List;

@Controller
@RequestMapping({"/qna", "/inquiry"})
@RequestProperty(layout="customer")
public class QnaController {
	private static final Logger log = LoggerFactory.getLogger(QnaController.class);
	
	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private ItemService itemService;
	
	
	/**
	 * 문의하기
	 * @param itemUserCode
	 * @param qna
	 * @param model
	 * @param requestContext
	 * @return
	 */
	@GetMapping
	@Authorize("hasRole('ROLE_USER')")
	public String qnaIndex(@RequestParam(value="usercode", required=false, defaultValue="") String itemUserCode, @ModelAttribute("qna") Qna qna, Model model, RequestContext requestContext) {
		
		//상품정보조회
		if (!"".equals(itemUserCode)) {
			Item item = itemService.getItemByItemUserCode(itemUserCode);
			qna.setItemName(item.getItemName());
		}
		
		// 회원 이름, 이메일 설정
		if (UserUtils.isUserLogin()) {
			qna.setUserName(UserUtils.getUser().getUserName());

			// 이메일 형식이 맞지 않을 경우 빈칸으로 설정
			String email = UserUtils.getUser().getEmail();
			if (StringUtils.isNotEmpty(email)) {
				String[] temp = StringUtils.delimitedListToStringArray(email, "@");
				if (temp.length != 2) {
					email = "";
				}
			}

			qna.setEmail(email);
		}	
		
		List<Code> groups = CodeUtils.getCodeList("QNA_GROUPS");
		
		model.addAttribute("groups", groups);
		model.addAttribute("qna", qna);
		model.addAttribute("buttonName", "문의하기");
		model.addAttribute("itemUserCode", itemUserCode);
		
		return ViewUtils.getView("/qna/inquiry");
	}
	
	
	/**
	 * 문의하기 실행
	 * @param itemUserCode
	 * @param qna
	 * @param result
	 * @param requestContext
	 * @return
	 */
	@PostMapping
	@Authorize("hasRole('ROLE_USER')")
	public String qnaInsert(@RequestParam(value="usercode", required=false, defaultValue="") String itemUserCode, MailConfig mailConfig, @ModelAttribute("qna") Qna qna, BindingResult result, RequestContext requestContext) {

		// 입력 값에 포함되어 있는 이모티콘 제거
		qna.setSubject(EmojiUtils.removeEmoticon(qna.getSubject()));
		qna.setQuestion(EmojiUtils.removeEmoticon(qna.getQuestion()));

		qna.setQnaType(Qna.QNA_GROUP_TYPE_INDIVIDUAL);
		qnaService.insertQna(qna);
		
		return ViewUtils.redirect("/qna", "회원님의 문의가 정상적으로 등록되었습니다.");
	} 
}
