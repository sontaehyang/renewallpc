package saleson.shop.remittance;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.PageNotFoundException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.SecurityUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.ShopUtils;
import saleson.seller.main.SellerService;
import saleson.seller.main.domain.Seller;
import saleson.seller.main.support.SellerParam;
import saleson.shop.remittance.domain.RemittanceConfirm;
import saleson.shop.remittance.support.*;

@Controller
@RequestMapping("/opmanager/remittance")
@RequestProperty(template="opmanager", layout="default")
public class RemittanceManagerController {
	private static final Logger log = LoggerFactory.getLogger(RemittanceManagerController.class);

	@Autowired
	private RemittanceService remittanceService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	SequenceService sequenceService;

	/**
	 * 정산 예정 내역
	 * @return
	 */
	@GetMapping("expected/list")
	public String expectedList(RemittanceParam remittanceParam, Model model, RequestContext requestContext) {

		if (ShopUtils.isSellerPage()) {
			throw new PageNotFoundException();
		}

		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		model.addAttribute("list", remittanceService.getRemittanceExpectedListByParam(remittanceParam));
		model.addAttribute("pagination", remittanceParam.getPagination());
		model.addAttribute("totalCount", remittanceParam.getPagination().getTotalItems());

		return "view:/remittance/expected/list";

	}

	/**
	 * 정산 예정 내역 수정 - 확정 기능
	 * @return
	 */
	@PostMapping("expected/list/update")
	public String expectedListProcess(RemittanceParam remittanceParam, Model model, RequestContext requestContext) {

		if (ShopUtils.isSellerPage()) {
			throw new PageNotFoundException();
		}

		try {

			// 상품 확정
			remittanceService.updateRemittanceItemExpectedForList(remittanceParam);

			// 배송비 확정
			remittanceService.updateRemittanceShippingExpectedForList(remittanceParam);

			// 추가금 확정
			remittanceService.updateRemittanceAddPaymentExpectedForList(remittanceParam);

		} catch(RemittanceException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			return ViewUtils.redirect(e.getRedirectUrl() + "?" + requestContext.getQueryString(), e.getMessage());
		}

		String redirect = "/opmanager/remittance/expected/list";
		if (StringUtils.isEmpty(requestContext.getQueryString()) == false) {
			redirect += "?" + requestContext.getQueryString();
		}

		return ViewUtils.redirect(redirect, "수정 되었습니다.");

	}

	/**
	 * 정산 예정 내역 상세 - Item
	 * @return
	 */
	@GetMapping("expected/detail/item/{sellerId}/{startDate}/{endDate}")
	public String itemExpectedDetail(@PathVariable("sellerId") long sellerId,
		@PathVariable("startDate") String startDate,
		@PathVariable("endDate") String endDate,
		RemittanceParam remittanceParam, Model model, RequestContext requestContext) {

		if (ShopUtils.isSellerPage()) {
			throw new PageNotFoundException();
		}

		Seller seller = sellerService.getSellerById(sellerId);
		if (seller == null) {
			throw new PageNotFoundException();
		}

		remittanceParam.setStartDate(startDate);
		remittanceParam.setEndDate(endDate);
		remittanceParam.setSellerId(sellerId);

		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("seller", seller);
		model.addAttribute("list", remittanceService.getRemittanceItemExpectedDetailListByParam(remittanceParam));
		model.addAttribute("pagination", remittanceParam.getPagination());
		model.addAttribute("totalCount", remittanceParam.getPagination().getTotalItems());

		return "view:/remittance/expected/item-detail";

	}

	/**
	 * 정산 예정 내역 상세 수정 - Item
	 * @return
	 */
	@PostMapping("expected/detail/item/{sellerId}/{startDate}/{endDate}")
	public String itemExpectedProcess(@PathVariable("sellerId") long sellerId,
		@PathVariable("startDate") String startDate,
		@PathVariable("endDate") String endDate,
		RemittanceParam remittanceParam, RequestContext requestContext) {

		if (ShopUtils.isSellerPage()) {
			throw new PageNotFoundException();
		}

		Seller seller = sellerService.getSellerById(sellerId);
		if (seller == null) {
			throw new PageNotFoundException();
		}

		remittanceParam.setStartDate(startDate);
		remittanceParam.setEndDate(endDate);
		remittanceParam.setSellerId(sellerId);

		String[] checkTypes = new String[]{"update", "confirm"};
		if (!ArrayUtils.contains(checkTypes, remittanceParam.getConditionType())) {
			throw new PageNotFoundException();
		}

		try {
			remittanceService.updateRemittanceItemExpected(remittanceParam);
		} catch(RemittanceException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			String redirect = e.getRedirectUrl();
			if (StringUtils.isEmpty(requestContext.getQueryString()) == false) {
				redirect += "?" + requestContext.getQueryString();
			}

			return ViewUtils.redirect(redirect, e.getMessage());
		}

		String redirect = "/opmanager/remittance/expected/detail/item/" + sellerId + "/" + startDate + "/" + endDate;
		if (StringUtils.isEmpty(requestContext.getQueryString()) == false) {
			redirect += "?" + requestContext.getQueryString();
		}

		return ViewUtils.redirect(redirect, "수정 되었습니다.");

	}

	/**
	 * 정산 예정 내역 상세 - Shipping
	 * @return
	 */
	@GetMapping("expected/detail/shipping/{sellerId}/{startDate}/{endDate}")
	public String shippingExpectedDetail(@PathVariable("sellerId") long sellerId,
		@PathVariable("startDate") String startDate,
		@PathVariable("endDate") String endDate,
		RemittanceParam remittanceParam, Model model, RequestContext requestContext) {

		if (ShopUtils.isSellerPage()) {
			throw new PageNotFoundException();
		}

		Seller seller = sellerService.getSellerById(sellerId);
		if (seller == null) {
			throw new PageNotFoundException();
		}

		remittanceParam.setStartDate(startDate);
		remittanceParam.setEndDate(endDate);
		remittanceParam.setSellerId(sellerId);

		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("seller", seller);
		model.addAttribute("list", remittanceService.getRemittanceShippingExpectedDetailListByParam(remittanceParam));
		model.addAttribute("pagination", remittanceParam.getPagination());
		model.addAttribute("totalCount", remittanceParam.getPagination().getTotalItems());

		return "view:/remittance/expected/shipping-detail";

	}

	/**
	 * 정산 예정 내역 상세 수정 - Shipping
	 * @return
	 */
	@PostMapping("expected/detail/shipping/{sellerId}/{startDate}/{endDate}")
	public String shippingExpectedProcess(@PathVariable("sellerId") long sellerId,
		@PathVariable("startDate") String startDate,
		@PathVariable("endDate") String endDate,
		RemittanceParam remittanceParam, RequestContext requestContext) {

		if (ShopUtils.isSellerPage()) {
			throw new PageNotFoundException();
		}

		Seller seller = sellerService.getSellerById(sellerId);
		if (seller == null) {
			throw new PageNotFoundException();
		}

		remittanceParam.setStartDate(startDate);
		remittanceParam.setEndDate(endDate);
		remittanceParam.setSellerId(sellerId);

		String[] checkTypes = new String[]{"update", "confirm"};
		if (!ArrayUtils.contains(checkTypes, remittanceParam.getConditionType())) {
			throw new PageNotFoundException();
		}

		try {
			remittanceService.updateRemittanceShippingExpected(remittanceParam);
		} catch(RemittanceException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			String redirect = e.getRedirectUrl();
			if (StringUtils.isEmpty(requestContext.getQueryString()) == false) {
				redirect += "?" + requestContext.getQueryString();
			}

			return ViewUtils.redirect(redirect, e.getMessage());
		}

		String redirect = "/opmanager/remittance/expected/detail/shipping/" + sellerId + "/" + startDate + "/" + endDate;
		if (StringUtils.isEmpty(requestContext.getQueryString()) == false) {
			redirect += "?" + requestContext.getQueryString();
		}

		return ViewUtils.redirect(redirect, "수정 되었습니다.");

	}

	/**
	 * 정산 예정 내역 상세 - Shipping
	 * @return
	 */
	@GetMapping("expected/detail/add-payment/{sellerId}/{startDate}/{endDate}")
	public String addPaymentExpectedDetail(@PathVariable("sellerId") long sellerId,
		@PathVariable("startDate") String startDate,
		@PathVariable("endDate") String endDate,
		RemittanceParam remittanceParam, Model model, RequestContext requestContext) {

		if (ShopUtils.isSellerPage()) {
			throw new PageNotFoundException();
		}

		Seller seller = sellerService.getSellerById(sellerId);
		if (seller == null) {
			throw new PageNotFoundException();
		}

		remittanceParam.setStartDate(startDate);
		remittanceParam.setEndDate(endDate);
		remittanceParam.setSellerId(sellerId);

		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("seller", seller);
		model.addAttribute("list", remittanceService.getRemittanceAddPaymentExpectedDetailListByParam(remittanceParam));
		model.addAttribute("pagination", remittanceParam.getPagination());
		model.addAttribute("totalCount", remittanceParam.getPagination().getTotalItems());

		return "view:/remittance/expected/add-payment-detail";

	}

	/**
	 * 정산 예정 내역 상세 수정 - Shipping
	 * @return
	 */
	@PostMapping("expected/detail/add-payment/{sellerId}/{startDate}/{endDate}")
	public String addPaymentExpectedProcess(@PathVariable("sellerId") long sellerId,
		@PathVariable("startDate") String startDate,
		@PathVariable("endDate") String endDate,
		RemittanceParam remittanceParam, RequestContext requestContext) {

		if (ShopUtils.isSellerPage()) {
			throw new PageNotFoundException();
		}

		Seller seller = sellerService.getSellerById(sellerId);
		if (seller == null) {
			throw new PageNotFoundException();
		}

		remittanceParam.setStartDate(startDate);
		remittanceParam.setEndDate(endDate);
		remittanceParam.setSellerId(sellerId);

		String[] checkTypes = new String[]{"update", "confirm"};
		if (!ArrayUtils.contains(checkTypes, remittanceParam.getConditionType())) {
			throw new PageNotFoundException();
		}

		try {
			remittanceService.updateRemittanceAddPaymentExpected(remittanceParam);
		} catch(RemittanceException e) {
			log.error("ERROR: {}", e.getMessage(), e);
			String redirect = e.getRedirectUrl();
			if (StringUtils.isEmpty(requestContext.getQueryString()) == false) {
				redirect += "?" + requestContext.getQueryString();
			}

			return ViewUtils.redirect(redirect, e.getMessage());
		}

		String redirect = "/opmanager/remittance/expected/detail/add-payment/" + sellerId + "/" + startDate + "/" + endDate;
		if (StringUtils.isEmpty(requestContext.getQueryString()) == false) {
			redirect += "?" + requestContext.getQueryString();
		}

		return ViewUtils.redirect(redirect, "수정 되었습니다.");

	}

	/**
	 * 정산 확정 내역
	 * @return
	 */
	@GetMapping("confirm/list")
	public String confirmList(RemittanceParam remittanceParam, Model model, RequestContext requestContext) {

		if (ShopUtils.isSellerPage()) {
			remittanceParam.setSellerId(SellerUtils.getSellerId());
		}

		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		model.addAttribute("list", remittanceService.getRemittanceConfirmListByParam(remittanceParam));
		model.addAttribute("pagination", remittanceParam.getPagination());
		model.addAttribute("totalCount", remittanceParam.getPagination().getTotalItems());

		return "view:/remittance/confirm/list";

	}

	/**
	 * 정산 마감
	 * @return
	 */
	@PostMapping("confirm/list/update")
	public String remittanceFinishingProcess(RemittanceParam param, RequestContext requestContext) {

		//if (ShopUtils.isSellerPage()) {
		//throw new PageNotFoundException();
		//}

		if (param.getId() != null) {
			for(String key : param.getId()) {
				String[] temp = StringUtils.delimitedListToStringArray(key, "^^^");
				if (temp.length != 2) {
					continue;
				}

				FinishingRemittance finishingRemittance = param.getFinishingRemittanceMap().get(key);
				if (finishingRemittance == null) {
					continue;
				}

				param.setSellerId(Integer.parseInt(temp[0]));
				param.setStartDate(temp[1]);

				RemittanceConfirm item = remittanceService.getRemittanceConfirmByParam(param);
				if (item == null) {
					continue;
				}

				if (SellerUtils.getSellerId() != param.getSellerId()) {
					throw new PageNotFoundException();
				}

				if (finishingRemittance.getAmount() != item.getItemRemittanceAmount() + item.getShippingTotalAmount() + item.getAddPaymentTotalAmount()) {
					continue;
				}

				param.setConfirmAmount(finishingRemittance.getAmount());

				try {
					remittanceService.remittanceFinishingProcess(param);
				} catch(RemittanceException e) {
					log.error("ERROR: {}", e.getMessage(), e);
				}
			}
		}

		String redirect = "/seller/remittance/confirm/list";
		if (StringUtils.isEmpty(requestContext.getQueryString()) == false) {
			redirect += "?" + requestContext.getQueryString();
		}

		return ViewUtils.redirect(redirect, "수정 되었습니다.");
	}

	/**
	 * 정산 예정 내역 상세 - Item
	 * @return
	 */
	@GetMapping("confirm/detail/{viewType}/{sellerId}/{startDate}")
	public String confirmDetail(@PathVariable("sellerId") long sellerId,
		@PathVariable("viewType") String viewType,
		@PathVariable("startDate") String startDate,
		RemittanceParam remittanceParam, Model model, RequestContext requestContext) {

		if (ShopUtils.isSellerPage()) {
			sellerId = SellerUtils.getSellerId();
		}

		Seller seller = sellerService.getSellerById(sellerId);
		if (seller == null) {
			throw new PageNotFoundException();
		}

		remittanceParam.setViewTarget("NO_CANCEL");
		if ("cancel".equals(viewType)) {
			remittanceParam.setViewTarget("CANCEL");
		}

		remittanceParam.setStartDate(startDate);
		remittanceParam.setSellerId(sellerId);

		model.addAttribute("seller", seller);
		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("list", remittanceService.getRemittanceConfirmDetailListByParam(remittanceParam));
		model.addAttribute("pagination", remittanceParam.getPagination());
		model.addAttribute("totalCount", remittanceParam.getPagination().getTotalItems());

		return "view:/remittance/confirm/detail";

	}

	/**
	 * 정산 마감 내역
	 * @return
	 */
	@GetMapping("finish/list")
	public String finishingList(RemittanceParam remittanceParam, Model model, RequestContext requestContext) {

		if (ShopUtils.isSellerPage()) {
			remittanceParam.setSellerId(SellerUtils.getSellerId());
		}

		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		model.addAttribute("list", remittanceService.getRemittanceFinishingListByParam(remittanceParam));
		model.addAttribute("pagination", remittanceParam.getPagination());
		model.addAttribute("totalCount", remittanceParam.getPagination().getTotalItems());

		return "view:/remittance/finishing/list";

	}

	/**
	 * 정산 마감 상세 내역
	 * @return
	 */
	@GetMapping("finish/detail/{sellerId}/{remittanceId}")
	public String finishingDetail(@PathVariable("remittanceId") int remittanceId,
		@PathVariable("sellerId") long sellerId,
		RemittanceParam remittanceParam, Model model, RequestContext requestContext) {

		if (ShopUtils.isSellerPage()) {
			sellerId = SellerUtils.getSellerId();
		}

		Seller seller = sellerService.getSellerById(sellerId);
		if (seller == null) {
			throw new PageNotFoundException();
		}

		model.addAttribute("seller", seller);
		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("list", remittanceService.getRemittanceFinishingDetailListByParam(remittanceParam));
		model.addAttribute("pagination", remittanceParam.getPagination());
		model.addAttribute("totalCount", remittanceParam.getPagination().getTotalItems());

		return "view:/remittance/finishing/detail";

	}

	/**
	 * 정산 예정 내역 상세 엑셀 다운로드
	 * @return
	 */
	@GetMapping("confirm/detail/{viewType}/{sellerId}/{startDate}/excel-download")
	public ModelAndView confirmDetail_excel(@PathVariable("sellerId") long sellerId,
		@PathVariable("viewType") String viewType,
		@PathVariable("startDate") String startDate,
		RemittanceParam remittanceParam, Model model, RequestContext requestContext) {

		if(!SecurityUtils.hasRole("ROLE_EXCEL") && !SecurityUtils.hasRole("ROLE_SUPERVISOR")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new RemittanceConfirmDetailExeclView());

		remittanceParam.setViewTarget("NO_CANCEL");
		if ("cancel".equals(viewType)) {
			remittanceParam.setViewTarget("CANCEL");
		}

		remittanceParam.setStartDate(startDate);
		remittanceParam.setSellerId(sellerId);


		mav.addObject("remittanceConfirmDetail", remittanceService.getRemittanceConfirmDetailListByParam(remittanceParam));

		return mav;

	}

	/**
	 * 정산 마감 상세 내역 엑셀 다운로드
	 * @return
	 */
	@GetMapping("finish/detail/{sellerId}/{remittanceId}/excel-download")
	public ModelAndView finishingDetail_excel(@PathVariable("remittanceId") int remittanceId,
		@PathVariable("sellerId") long sellerId,
		RemittanceParam remittanceParam, Model model, RequestContext requestContext) {

		if(!SecurityUtils.hasRole("ROLE_EXCEL") && !SecurityUtils.hasRole("ROLE_SUPERVISOR")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new RemittanceFinishDetailExeclView());

		remittanceParam.setRemittanceId(remittanceId);
		remittanceParam.setSellerId(sellerId);

		mav.addObject("remittanceDetail", remittanceService.getRemittanceFinishingDetailListByParam(remittanceParam));


		return mav;

	}

}
