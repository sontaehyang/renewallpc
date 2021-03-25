package saleson.shop.seller;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.CodeUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.utils.UserUtils;
import saleson.seller.main.SellerService;
import saleson.seller.main.domain.Seller;
import saleson.seller.main.support.SellerParam;
import saleson.seller.user.SellerUserService;
import saleson.shop.shadowlogin.ShadowLoginLogService;
import saleson.shop.user.UserService;
import saleson.shop.user.support.UserSearchParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping("/opmanager/seller")
@RequestProperty(title="입첨업체관리", layout="default", template="opmanager")
public class SellerManagerController {
	@Autowired
	private SellerService sellerService;

	@Autowired
	private SellerUserService sellerUserService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShadowLoginLogService shadowLoginLogService;
	
	/**
	 * 판매관리자 메인.
	 * @return
	 */
	@GetMapping("list")
	public String list(SellerParam sellerParam, Model model) {
		
		
		Pagination pagination = Pagination.getInstance(sellerService.getSellerCount(sellerParam));
		
		sellerParam.setPagination(pagination);
		
		
		model.addAttribute("list",sellerService.getSellerListByParam(sellerParam));
		model.addAttribute("totalCount", sellerParam.getPagination().getTotalItems());
		model.addAttribute("sellerSearchParam",sellerParam);
		model.addAttribute("pagination",pagination);
		//return "view:/opmanager/seller/list";
		return "view";
	}
	
	
	/**
	 * 입점업체등록
	 * @param model
	 * @return
	 */
	@GetMapping("create")
	public String create(Model model) {
		
		Seller seller =  new Seller();
		seller.setShippingFlag("N");
		
		model.addAttribute("telCodes", CodeUtils.getCodeList("TEL"));
		model.addAttribute("phoneCodes", CodeUtils.getCodeList("PHONE"));
		model.addAttribute("remittanceTypeCodes", CodeUtils.getCodeList("REMITTANCE_TYPE"));
		model.addAttribute("seller", seller);
		model.addAttribute("mode", "create");
		model.addAttribute("action", "/opmanager/seller/create");
		
		return ViewUtils.view();
	}
	
	
	/**
	 * 입점업체등록 처리
	 * @param seller
	 * @return
	 */
	@PostMapping("create")
	public String createAction(Seller seller) {

		seller.setCreatedUserId(UserUtils.getManagerId());
		sellerService.insertSeller(seller);
		return ViewUtils.redirect("/opmanager/seller/list", MessageUtils.getMessage("M00288"));	// 등록되었습니다. 
	}
	
	/**
	 * 입점업체 수정
	 * @param sellerId
	 * @param model
	 * @return
	 */
	@GetMapping("edit/{sellerId}")
	@RequestProperty(title="입점업체 수정")
	public String updateSeller(@PathVariable("sellerId") long sellerId, Model model) {
		
		Seller seller = sellerService.getSellerById(sellerId);
		
		model.addAttribute("telCodes", CodeUtils.getCodeList("TEL"));
		model.addAttribute("phoneCodes", CodeUtils.getCodeList("PHONE"));
		model.addAttribute("remittanceTypeCodes", CodeUtils.getCodeList("REMITTANCE_TYPE"));
		model.addAttribute("seller", seller);
		model.addAttribute("mode", "edit");
		model.addAttribute("action", "/opmanager/seller/edit/" + sellerId);
		
		return ViewUtils.getView("/seller/form");
	}
	
	/**
	 * 입점업체 수정처리
	 * @param sellerId
	 * @param seller
	 * @param model
	 * @return
	 */
	@PostMapping("edit/{sellerId}")
	public String updateSellerAction(@PathVariable("sellerId") long sellerId, Seller seller, Model model) {
		
		seller.setSellerId(sellerId);
		sellerService.updateSeller(seller);
		
		return ViewUtils.redirect("/opmanager/seller/list", MessageUtils.getMessage("M00289"));	// 수정되었습니다. 
	}
	
	/**
	 * 입점업체 아이디 유무
	 * @param requestContext
	 * @param seller
	 * @return
	 */
	@PostMapping("id-duplicate-Check")
	public JsonView idDuplicateCheck(RequestContext requestContext, Seller seller) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}

		Seller selectSeller = sellerService.getSellerByLoginId(seller.getLoginId());
		boolean isExist = sellerUserService.isDuplicateSellerUserByLoginId(seller.getLoginId());

		if (selectSeller != null) {
			isExist = true;
		}

		//model.addAttribute("isExist",isExist);
		
		//return JsonViewUtils.success(); 
		
		if (!isExist) {
			return JsonViewUtils.success();
		} else {
			return JsonViewUtils.exception(MessageUtils.getMessage("M00161"));

		}
		 
	}
	
	/**
	 * 입점업체 삭제
	 * @param seller
	 * @return
	 */
	@PostMapping("delete")
	public String deleteSeller(Seller seller) {
		
		sellerService.deleteSeller(seller);
		
		return ViewUtils.redirect("/opmanager/seller/list", MessageUtils.getMessage("M00205")); // 삭제되었습니다. 
	}
	
	/**
	 * 판매자 shadow로그인
	 * @param seller
	 * @return
	 */
	@GetMapping("shadow-login")
	public String shadhowLogin(Seller seller) {
		
		HttpSession session = RequestContextUtils.getSession();
		
		Seller loginInfo = sellerService.getSellerById(seller.getSellerId());
		loginInfo.setShadowLoginLogId(shadowLoginLogService.insertShadowLoginLog("seller", loginInfo.getSellerId(), UserUtils.getManagerId()));
		session.setAttribute("SHADOW_SELLER", loginInfo);
		
		return ViewUtils.redirect("/seller");
	}
	
	/*
	 * 회원검색 (팝업창 검색)
	 */
	@GetMapping("find-md")
	@RequestProperty(layout = "base")
	public String searchUser(Model model, UserSearchParam userSearchParam) {
		
		int userCount = 0;
		List<User> userList = new ArrayList<>();
		Pagination pagination = null;
		
		if ("mdId".equals(userSearchParam.getTargetId()) || "managerUserId".equals(userSearchParam.getTargetId())) {
			//userSearchParam.setAuthority("ROLE_OPMANAGER");
			userSearchParam.setConditionType("FIND_MD");	// MD만 검색
			userSearchParam.setAuthority("ROLE_MD");
			userCount = userService.getManagerCount(userSearchParam);
			
			pagination = Pagination.getInstance(userCount);
			userSearchParam.setPagination(pagination);
			
			userList = userService.getManagerList(userSearchParam);
			
		} else {
			userSearchParam.setAuthority("ROLE_USER");
			userCount = userService.getUserCount(userSearchParam);
			
			pagination = Pagination.getInstance(userCount);
			userSearchParam.setPagination(pagination);
			
			userList = userService.getUserList(userSearchParam);
		}
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("userList", userList);
		model.addAttribute("userCount", userCount);
	
		return ViewUtils.view();
	}
	
	/**
	 * (팝업) 판매자정보 변경 로그 목록 조회
	 * @param searchParam
	 * @param sellerId
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="log")
	@GetMapping("popup/log/{sellerId}")
	public String logList(SellerParam searchParam, @PathVariable("sellerId") long sellerId, Model model) {
		/*
		searchParam.setSellerId(sellerId);
		
		int sellerLogCount = sellerService.getSellerLogCount(searchParam);
		
		// 페이징
		Pagination pagination = Pagination.getInstance(sellerLogCount);
		searchParam.setPagination(pagination);
		
		model.addAttribute("list", sellerService.getSellerLogList(searchParam));
		model.addAttribute("pagination", pagination);
		model.addAttribute("sellerLogCount", sellerLogCount);
		model.addAttribute("bankCodes", hspCodeService.getHspSubCodeList("04"));	// 은행코드
		*/
		return ViewUtils.getView("/seller/popup/log-list");
	}
	
	/**
	 * 판매자검색.
	 * @param sellerParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("find")
	public String findSeller(SellerParam sellerParam, Model model) {
		
		Pagination pagination = Pagination.getInstance(sellerService.getSellerCount(sellerParam));
		
		sellerParam.setPagination(pagination);
		
		model.addAttribute("list",sellerService.getSellerListByParam(sellerParam));
		model.addAttribute("totalCount", sellerParam.getPagination().getTotalItems());
		model.addAttribute("sellerSearchParam",sellerParam);
		model.addAttribute("pagination",pagination);
		
		return ViewUtils.getView("/seller/popup/find-seller");
	}
	
	/**
	 * 판매자검색.
	 * @param sellerParam
	 * @param model
	 * @return
	 */
	@RequestProperty(layout="base")
	@GetMapping("find-popup/{targetId}")
	public String findSellerPopup(SellerParam sellerParam,
			@PathVariable("targetId") String targetId,
			Model model) {
		
		Pagination pagination = Pagination.getInstance(sellerService.getSellerCount(sellerParam));
		
		sellerParam.setPagination(pagination);
		
		model.addAttribute("targetId", targetId);
		model.addAttribute("list",sellerService.getSellerListByParam(sellerParam));
		model.addAttribute("totalCount", sellerParam.getPagination().getTotalItems());
		model.addAttribute("sellerSearchParam",sellerParam);
		model.addAttribute("pagination",pagination);
		
		return ViewUtils.getView("/seller/popup/find-seller");
	}
}
