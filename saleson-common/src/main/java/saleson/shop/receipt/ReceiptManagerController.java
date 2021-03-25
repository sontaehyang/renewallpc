package saleson.shop.receipt;


import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.Const;
import saleson.common.enumeration.CashbillStatus;
import saleson.common.enumeration.CashbillType;
import saleson.common.enumeration.mapper.EnumMapper;
import saleson.common.utils.UserUtils;
import saleson.model.Cashbill;
import saleson.model.CashbillIssue;
import saleson.shop.receipt.dto.CashbillIssueDto;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/opmanager/receipt")
@RequestProperty(title="영수증관리", layout="default", template="opmanager")
public class ReceiptManagerController {
    private static final Logger log = LoggerFactory.getLogger(ReceiptManagerController.class);

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private CashbillRepository cashbillRepository;

	@Autowired
	private EnumMapper enumMapper;

	@Autowired
	private ModelMapper modelMapper;

    /**
     * 현금영수증 목록
     * @param pageable
     * @param model
     * @param cashbillParam
     * @return
     */
    @GetMapping("/cashbill/list")
    public String list(CashbillParam cashbillParam
            , @PageableDefault @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
            , Model model) {


        Page<CashbillIssue> pageContent = receiptService.findCashbillIssueAll(cashbillParam.getPredicate(), pageable);

        String today = DateUtils.getToday(Const.DATE_FORMAT);

        model.addAttribute("cashbillParam", cashbillParam);
        model.addAttribute("pageContent", pageContent);
        model.addAttribute("cashbillStatus", enumMapper.get("CashbillStatus"));

        return "view";
    }

	/**
	 * 현금영수증 수동발급.
	 * @param cashbillIssue
	 * @param model
	 * @return
	 */
	@GetMapping("/cashbill/create")
	public String create(Model model) {
		model.addAttribute("cashbillIssue", new CashbillIssue());
        model.addAttribute("cashbillTypes", enumMapper.get("CashbillType"));
        model.addAttribute("cashbillIssueTypes", enumMapper.get("CashbillIssueType"));
        model.addAttribute("taxTypes", enumMapper.get("TaxType"));
		return "view";
	}


	/**
	 * 현금영수증 수동 발급 신청.
	 * @param cashbillIssue
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/cashbill/create")
	public String create(CashbillIssueDto cashbillIssueDto, BindingResult result, Model model) {

		CashbillIssue cashbillIssue = modelMapper.map(cashbillIssueDto, CashbillIssue.class);
		if (result.hasErrors()) {
			RequestContextUtils.setMessage(result.getAllErrors().get(0).getDefaultMessage());
			model.addAttribute("cashbillIssue", cashbillIssue);
			model.addAttribute("cashbillTypes", enumMapper.get("CashbillType"));
			return "view";
		}

		Cashbill cashbill = cashbillIssue.getCashbill();

		List<Cashbill> cashbills = cashbillRepository.findAllByOrderCode(cashbill.getOrderCode());

		if (!cashbills.isEmpty()) {
            FlashMapUtils.setMessage("주문번호 " + cashbill.getOrderCode() + "로 등록된 현금영수증 정보가 이미 존재합니다.");

            return "redirect:/opmanager/receipt/cashbill/list";
        }

        if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
            cashbill.setCreatedBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
            cashbillIssue.setUpdateBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
        } else {
            cashbill.setCreatedBy("비회원");
            cashbillIssue.setUpdateBy("비회원");
        }

        String cashbillCode = "";

        if (CashbillType.PERSONAL == cashbill.getCashbillType()) {
            cashbillCode = cashbill.getCashbillPhone1() + "-" + cashbill.getCashbillPhone2() + "-" + cashbill.getCashbillPhone3();
        } else {
            cashbillCode = cashbill.getBusinessNumber1() + "-" + cashbill.getBusinessNumber2() + "-" + cashbill.getBusinessNumber3();
        }


        cashbill.setCashbillCode(cashbillCode);
		cashbill.setCreatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
		receiptService.insertCashbillInfo(cashbill);

		cashbillIssue.setCashbillStatus(CashbillStatus.PENDING);
        cashbillIssue.setCreatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
        cashbillIssue.setUpdatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
        receiptService.insertCashbillIssue(cashbillIssue);

		FlashMapUtils.setMessage("등록되었습니다.");

		return "redirect:/opmanager/receipt/cashbill/list";
	}

    @PostMapping("/cashbill/list/issue")
    public JsonView issueCashbillListData(RequestContext requestContext, ListParam listParam) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        if (listParam.getId() == null || listParam.getId().length == 0) {
            return JsonViewUtils.failure("필수 데이터가 전송되지 않았습니다.");
        }

        try {
            receiptService.issueCashbillByListData(listParam);

        } catch (Exception e) {
            return JsonViewUtils.failure("처리 중 에러가 발생하였습니다.");
        }
        return JsonViewUtils.success();
    }

    @PostMapping("/cashbill/list/cancel")
    public JsonView cancelCashbillListData(RequestContext requestContext, ListParam listParam) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        if (listParam.getId() == null || listParam.getId().length == 0) {
            return JsonViewUtils.failure("필수 데이터가 전송되지 않았습니다.");
        }

        try {
            receiptService.cancelCashbillByListData(listParam);

        } catch (Exception e) {
            return JsonViewUtils.failure("처리 중 에러가 발생하였습니다.\n( " + e.getMessage() + " )");
        }
        return JsonViewUtils.success();
    }
}
