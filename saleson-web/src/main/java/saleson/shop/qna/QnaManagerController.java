package saleson.shop.qna;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.repository.CodeInfo;
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
import org.springframework.web.servlet.ModelAndView;
import saleson.common.Const;
import saleson.common.notification.ApplicationInfoService;
import saleson.common.notification.UnifiedMessagingService;
import saleson.common.utils.UserUtils;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.seller.main.SellerService;
import saleson.seller.main.support.SellerParam;
import saleson.shop.item.support.ItemParam;
import saleson.shop.mailconfig.MailConfigService;
import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.mailconfig.support.QnaCompleteMail;
import saleson.shop.qna.domain.Qna;
import saleson.shop.qna.domain.QnaAnswer;
import saleson.shop.qna.support.QnaExcelView;
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
@RequestMapping("/opmanager/qna")
@RequestProperty(title = "고객센터", template="opmanager", layout = "default")
public class QnaManagerController {
	
	private static final Logger log = LoggerFactory
			.getLogger(QnaManagerController.class);

	@Autowired
	private QnaService qnaService;

	@Autowired
	private SendMailLogService sendMailLogService;

	@Autowired
	private MailConfigService mailConfigService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SmsConfigService smsConfigService;
	
	@Autowired
	private SendSmsLogService sendSmsLogService;
	
	@Autowired
	private SellerService sellerService;

	@Autowired
	private UmsService umsService;

	@Autowired
	private UnifiedMessagingService unifiedMessagingService;

	@Autowired
	private ApplicationInfoService applicationInfoService;


	/**
	 * 문의 관리 view
	 * 
	 * @param requestContext
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String qnaList(@ModelAttribute QnaParam qnaParam,
			RequestContext requestContext, Model model) {

		qnaParam.setQnaType(Qna.QNA_GROUP_TYPE_INDIVIDUAL);
		qnaService.setQnaListPagination(qnaParam);
		
		List<Qna> qnaList = qnaService.getQnaListByParam(qnaParam);
		
		String today = DateUtils.getToday(Const.DATE_FORMAT);

		List<Code> qnaGroups = CodeUtils.getCodeList("QNA_GROUPS");

		model.addAttribute("qnaGroups", qnaGroups);

		for (Qna qnaCheck : qnaList) {

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
		model.addAttribute("qnaCount", qnaParam.getPagination().getTotalItems());
		model.addAttribute("today", today);
		model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
		model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
		model.addAttribute("month3", DateUtils.addYearMonthDay(today, 0, -3, 0));
		model.addAttribute("qnaList", qnaList);
		model.addAttribute("pagination", qnaParam.getPagination());
		model.addAttribute("qnaTypes", qnaTypes);
		model.addAttribute("qna", qnaParam);
		
		return "view";
	}
	
	/**
	 * 2015.1.18
	 * 리스트 삭제 기능
	 * 
	 * @param requestContext
	 * @param listParam
	 * @return
	 */
	@PostMapping("delete")
	public JsonView deleteListData(RequestContext requestContext, ListParam listParam) {
	  
		 if (!requestContext.isAjaxRequest()) { 
			 throw new NotAjaxRequestException(); 
		 }
		 qnaService.deleteQnaData(listParam);
		 
		 return JsonViewUtils.success(); 
	}
	
	/**
	 * 문의 내역 view
	 * 
	 * @param requestContext
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/view/{qnaId}")
	public String qnaView(@PathVariable("qnaId") int qnaId, Model model, QnaParam qnaParam) {
		Qna qna = qnaService.getQnaByQnaId(qnaId);
		QnaAnswer qnaAnswer = qnaService.getQnaAnswerByQnaId(qnaId);
		
		//User adminUser = userService.getUserByUserId(UserUtils.getManagerId());
		long userId = UserUtils.getManagerId();
		CodeInfo codeInfo = CodeUtils.getCodeInfo("QNA_GROUPS", qna.getQnaGroup());
		
		model.addAttribute("qnaGroups", codeInfo.getLabel());
		model.addAttribute("qnaId", qnaId);
		model.addAttribute("qna", qna);
		model.addAttribute("qnaAnswer", qnaAnswer);
		model.addAttribute("qnaParam", qnaParam);
		//model.addAttribute("adminUser", adminUser);
		model.addAttribute("userId", userId);
		return ViewUtils.getView("/qna/view");
	}

	
	@GetMapping(value = "/answer/{qnaId}")
	public String qnaAnswer(@PathVariable("qnaId") int qnaId, QnaParam qnaParam,
			RequestContext requestContext, Model model) {

		Qna qna = qnaService.getQnaByQnaId(qnaId);
		QnaAnswer qnaAnswer = qna.getQnaAnswer();
		
		//User adminUser = userService.getUserByUserId(UserUtils.getManagerId());
		long userId = UserUtils.getManagerId();
		
		CodeInfo codeInfo = CodeUtils.getCodeInfo("QNA_GROUPS", qna.getQnaGroup());
		model.addAttribute("qnaAnswerTypeLabel", codeInfo.getLabel());
		model.addAttribute("qna", qna);
		model.addAttribute("qnaAnswer", qnaAnswer);
		model.addAttribute("qnaParam", qnaParam);
		
		//model.addAttribute("adminUser", adminUser);
		model.addAttribute("userId", userId);
		model.addAttribute("buttonName", "답글등록");
		return ViewUtils.getView("/qna/form");
	}

	/**
	 * 문의관리
	 * 
	 * @param requestContext
	 * @param model
	 * @return
	 */
	@PostMapping("answer/{qnaId}")
	public String qnaAnswerAction(@PathVariable("qnaId") int qnaId, QnaAnswer qnaAnswer, 
			MailConfig mailConfig, Model model, QnaParam qnaParam) {
			
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
		
		
		return ViewUtils.redirect("/opmanager/qna/list", MessageUtils.getMessage("M00492")); // 답변이 등록되었습니다.
	}
	
	/**
	 * 엑셀 다운로드 팝업
	 * 
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout = "base")
	@GetMapping(value = "/download-excel")
	public String downloadExcel(ItemParam qnaParam, Model model) {
		model.addAttribute("itemParam", qnaParam);
		return ViewUtils.view();
	}

	/**
	 * 엑셀 다운로드
	 * 
	 * @param itemParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout = "base")
	@PostMapping("/download-excel")
	public ModelAndView downloadExcelProcess(QnaParam qnaParam) {
		// Excel
		ModelAndView mav = new ModelAndView(new QnaExcelView());
		mav.addObject("qna", qnaService.getQnaListByParam(qnaParam));

		return mav;
	}

	/**
	 * 엑셀 업로드
	 * 
	 * @param session
	 * @return
	 */
	@RequestProperty(layout = "base")
	@GetMapping(value = "/upload-excel")
	public String uploadExcel(Model model) {

		if (RedirectAttributeUtils.hasRedirectAttributes()) {
			model.addAttribute("result", RedirectAttributeUtils.get("result"));
		}
		return ViewUtils.view();
	}

	/**
	 * 엑셀 업로드 처리
	 * 
	 * @param multipartFile
	 * @param session
	 * @return
	 */
	/*
	@RequestProperty(layout = "base")
	@PostMapping("/upload-excel")
	public String uploadExcelProcess(
			@RequestParam(value = "file", required = false) MultipartFile multipartFile,
			Model model, RedirectAttributes redirectAttribute) {

		String result = qnaService.insertExcelData(multipartFile);

		model.addAttribute("result", result);
		RedirectAttributeUtils.addAttribute("result", result);
		return ViewUtils.redirect("/opmanager/item/upload-excel");
	}
*/
	/**
	 * 2015.1.6 수정 view
	 * 
	 * @param qnaId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/edit/{qnaId}")
	public String qnaUpdate(@PathVariable("qnaId") int qnaId, Model model) {
		Qna qna = qnaService.getQnaByQnaId(qnaId);
		QnaAnswer qnaAnswer = qnaService.getQnaAnswerByQnaId(qnaId);
		
		//User adminUser = userService.getUserByUserId(UserUtils.getManagerId());
		long userId = UserUtils.getManagerId();
		CodeInfo codeInfo = CodeUtils.getCodeInfo("QNA_GROUPS", qna.getQnaGroup());
		
		//model.addAttribute("adminUser", adminUser);
		model.addAttribute("userId", userId);
		model.addAttribute("qnaAnswerTypeLabel", codeInfo.getLabel());
		model.addAttribute("qna", qna);
		model.addAttribute("qnaAnswer", qnaAnswer);
		model.addAttribute("qnaGroups", codeInfo.getLabel());
		model.addAttribute("buttonName", "저장");
		return ViewUtils.getView("/qna/form");
	}
	
	@PostMapping("/edit/{qnaId}")
	public String qnaUpdateAction(@PathVariable("qnaId") int qnaId, Qna qna, QnaAnswer qnaAnswer) {
		qna.setQnaId(qnaId);
		qnaService.updateQnaAnswer(qnaAnswer);
		return ViewUtils.redirect("/opmanager/qna/list", "수정되었습니다.");
	}
	
	/**
	 * 2015.1.7 삭제
	 * 
	 * @param qnaId
	 * @param qna
	 * @return
	 */
	@GetMapping(value = "/delete/{qnaId}")
	public String qnaDelete(@PathVariable("qnaId") int qnaId) {
		Qna qna = new Qna();
		qna.setQnaId(qnaId);
		
		qnaService.deleteQna(qna);
		return ViewUtils.redirect("/opmanager/qna/list");
	}
	
}
