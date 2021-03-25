package saleson.shop.qna;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.util.*;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.notification.ApplicationInfoService;
import saleson.common.notification.UnifiedMessagingService;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.seller.main.SellerService;
import saleson.seller.main.support.SellerParam;
import saleson.shop.mailconfig.MailConfigService;
import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.mailconfig.support.QnaCompleteMail;
import saleson.shop.qna.domain.Qna;
import saleson.shop.qna.domain.QnaAnswer;
import saleson.shop.qna.support.QnaParam;
import saleson.shop.sendmaillog.SendMailLogService;
import saleson.shop.sendmaillog.domain.SendMailLog;
import saleson.shop.sendsmslog.SendSmsLogService;
import saleson.shop.smsconfig.SmsConfigService;
import saleson.shop.ums.UmsService;
import saleson.shop.ums.support.QnaComplete;
import saleson.shop.user.UserService;
import saleson.shop.user.domain.UserDetail;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/opmanager/qna-item")
@RequestProperty(title = "", layout = "default")
public class QnaItemManagerController {
	private static final Logger log = LoggerFactory.getLogger(QnaItemManagerController.class);
	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private SendMailLogService sendMailLogService;

	@Autowired
	private MailConfigService mailConfigService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SendSmsLogService sendSmsLogService;
	
	@Autowired
	private SmsConfigService smsConfigService;
	
	@Autowired
	private SellerService sellerService;

	@Autowired
	private UmsService umsService;

	@Autowired
	private UnifiedMessagingService unifiedMessagingService;

	@Autowired
	private ApplicationInfoService applicationInfoService;
	
	@GetMapping(value="/list")
	public String answerList(QnaParam qnaParam, Model model) {
		
		qnaParam.setQnaType(Qna.QNA_GROUP_TYPE_ITEM);
		qnaService.setQnaListPagination(qnaParam);
		
		List<Qna> itemQnaLists = qnaService.getQnaListByParam(qnaParam);

		List<Code> qnaGroups = CodeUtils.getCodeList("QNA_GROUPS");

		model.addAttribute("qnaGroups", qnaGroups);

		for (Qna qnaCheck : itemQnaLists) {

			for (Code code : qnaGroups) {

				if (qnaCheck.getQnaGroup().equals(code.getId())) {
					qnaCheck.setQnaGroup(code.getLabel());
				}
			}
		}

		List<Code> qnaTypes = new ArrayList<>();
		for (Code code : qnaGroups) {
			List<Code> qnaGroup = CodeUtils.getCodeList("QNA_GROUP_" + code.getValue());
			for (Code qnaType : qnaGroup) {
				qnaTypes.add(qnaType);
			}
		}

		SellerParam sellerParam = new SellerParam();
		sellerParam.setStatusCode("2");
		model.addAttribute("sellerList", sellerService.getSellerListByParam(sellerParam));
		model.addAttribute("itemListCount", qnaParam.getPagination().getTotalItems());
		model.addAttribute("itemQnaLists", itemQnaLists);
		model.addAttribute("qna", qnaParam);
		model.addAttribute("pagination", qnaParam.getPagination());
		model.addAttribute("qnaTypes", qnaTypes);
		
		return ViewUtils.getView("/qna-item/list");
	}
	
	@PostMapping("delete")
	public JsonView deleteListData(RequestContext requestContext, ListParam listParam) {
	  
		 if (!requestContext.isAjaxRequest()) { 
			 throw new NotAjaxRequestException(); 
		 }
		 qnaService.deleteQnaData(listParam);
		 
		 return JsonViewUtils.success(); 
	}
	
	@GetMapping(value="/view/{qnaId}")
	public String answerView(@ModelAttribute("qna") Qna qnaParam, @PathVariable("qnaId") int qnaId, Model model) {
		
		Qna qna = qnaService.getQnaByQnaId(qnaId);
		if (qna == null) {
			return ViewUtils.redirect(RequestContextUtils.getPreviousUri(), "Q&A 대상 상품이 삭제된 게시글입니다.");
		}

		if (ShopUtils.isSellerPage() && qna.getSellerId() != SellerUtils.getSellerId()) {
			throw new PageNotFoundException();
		}

		QnaAnswer qnaAnswer = qnaService.getQnaAnswerByQnaId(qnaId);
		//User adminUser = userService.getUserByUserId(UserUtils.getManagerId());
		model.addAttribute("qna", qna);
		model.addAttribute("qnaParam", qnaParam);
		model.addAttribute("qnaAnswer", qnaAnswer);
		//model.addAttribute("adminUser", adminUser);
		return ViewUtils.getView("/qna-item/view");
	}
	
	@GetMapping(value="/delete/{qnaId}")
	public String answerDelete(@PathVariable("qnaId") int qnaId) {
		Qna qna = new Qna();
		qna.setQnaId(qnaId);
		qnaService.deleteQna(qna);
		return ViewUtils.redirect("/opmanager/qna-item/list");
	}
	
	@GetMapping("/answer/{qnaId}")
	public String answerInsert(Qna qnaParam, @PathVariable("qnaId") int qnaId, Model model, RequestContext requestContext) {
		Qna qna = qnaService.getQnaByQnaId(qnaId);
		QnaAnswer qnaAnswer = qnaService.getQnaAnswerByQnaId(qnaId);
		
		model.addAttribute("qnaParam", qnaParam);
		//User adminUser = userService.getUserByUserId(UserUtils.getManagerId());
		
		long userId = UserUtils.getManagerId();

        if (ShopUtils.isSellerPage() && qna.getSellerId() != SellerUtils.getSellerId()) {
            throw new PageNotFoundException();
        }
		
		
		if( qnaAnswer == null ){
			qnaAnswer = new QnaAnswer();
		}
		
		model.addAttribute("qna", qna);
		model.addAttribute("qnaAnswer", qnaAnswer);
		
		
		String requestUri = requestContext.getRequestUri();
		
		if(ShopUtils.isSellerPage(requestUri)){
			model.addAttribute("seller", SellerUtils.getSeller());
		}else{
			model.addAttribute("userId", userId);
		}

		
		return ViewUtils.getView("/qna-item/form");
	}
	
	@PostMapping("/answer/{qnaId}")
	public String qnaAnswerAction(@PathVariable("qnaId") int qnaId, QnaAnswer qnaAnswer, 
			MailConfig mailConfig, Model model, QnaParam qnaParam, RequestContext requestContext) {
			
		Qna qna = null;
		qnaAnswer.setSendMailFlag(qnaAnswer.getSendMailFlag() == null ? "N": "Y");
		qnaAnswer.setSendSmsFlag(qnaAnswer.getSendSmsFlag() == null ? "N": "Y");
		
		if (qnaAnswer.getQnaAnswerId() > 0) {
			qnaService.updateQnaAnswer(qnaAnswer);
		} else {
			qnaService.insertQnaAnswer(qnaAnswer);
		}
		
		qnaService.updateQnaAnswerCount(qnaId);
		
		if ("Y".equals(qnaAnswer.getSendMailFlag())) {
			
			qna = qnaService.getQnaByQnaId(qnaId);

			mailConfig.setTemplateId("qna_complete");
			QnaCompleteMail qnaComplete = new QnaCompleteMail(qna, mailConfigService.getMailConfigByTemplateId(mailConfig.getTemplateId()));
			
			SendMailLog sendMailLog = new SendMailLog();
			//sendMailLog.setVendorId(UserUtils.getVendorId());
			sendMailLog.setSendType("qna_complete");
			
			sendMailLogService.sendMail(qnaComplete.getMailConfig(), sendMailLog, qna.getEmail(), qna.getUserName());
			
		}

		if ("Y".equals(qnaAnswer.getSendSmsFlag())) {
			
			String phoneNumber = "";
			
			if (qna == null) {
				qna = qnaService.getQnaByQnaId(qnaId);
			}
			
			// 회원 번호가 있는경우
			if (qna.getUserId() > 0) {
				
				UserDetail userDetail = userService.getUserDetail(qna.getUserId()); //문의한 사람 폰넘버
				if (userDetail != null) {
					phoneNumber = userDetail.getPhoneNumber();
				}
			}
			
			if (StringUtils.isNotEmpty(phoneNumber)) {
                String templateCode = "qna_complete";

                Ums ums = umsService.getUms(templateCode);
				ApplicationInfo applicationInfo = applicationInfoService.getApplicationInfo(qna.getUserId());

                unifiedMessagingService.sendMessage(new QnaComplete(ums, phoneNumber, qna, applicationInfo));
			}
		}

		String requestUri = requestContext.getRequestUri();
		String redirectUri = "/opmanager/qna-item/list";
		if(ShopUtils.isSellerPage(requestUri)){
			redirectUri = "/seller/qna-item/list";
		}
		return ViewUtils.redirect(redirectUri, MessageUtils.getMessage("M00492")); // 답변이 등록되었습니다.
	}

}
