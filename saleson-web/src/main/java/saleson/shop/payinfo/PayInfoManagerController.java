package saleson.shop.payinfo;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;

import saleson.common.Const;
import saleson.model.ConfigPg;
import saleson.model.OrderCancelFail;
import saleson.shop.inquiry.InquiryManagerController;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.orderCancelFail.OrderCancelFailService;
import saleson.shop.orderCancelFail.support.OrderCancelFailParam;
import saleson.shop.payinfo.support.PayInfoExcelView;
import saleson.shop.payinfo.support.PayInfoParam;

@Controller
@RequestMapping("/opmanager/pay-info")
@RequestProperty(title = "결제 관리", template = "opmanager", layout = "default")
public class PayInfoManagerController {

    @Autowired
    PayInfoService payInfoService;

    @Autowired
    OrderCancelFailService orderCancelFailService;

    @Autowired
    ConfigPgService configPgService;

    private static final Logger logger = LoggerFactory
            .getLogger(InquiryManagerController.class);

    /**
     * 결제현황 리스트
     *
     * @param model
     * @param payInfoParam
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, PayInfoParam payInfoParam) {

        int payInfoCount = payInfoService.getPayInfoListCount(payInfoParam);
        Pagination pagination = Pagination.getInstance(payInfoCount);

        payInfoParam.setPagination(pagination);
        payInfoParam.setConditionType(null);

        String today = DateUtils.getToday("yyyyMMdd");
        model.addAttribute("today", today);
        model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
        model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
        model.addAttribute("month3", DateUtils.addYearMonthDay(today, 0, -3, 0));

        model.addAttribute("payInfoCount", payInfoCount);
        model.addAttribute("pagination", pagination);
        model.addAttribute("payInfoParam", payInfoParam);
        model.addAttribute("payInfoList", payInfoService.getPayInfoList(payInfoParam));

        return ViewUtils.view();
    }

    @RequestMapping("/download-excel")
    public ModelAndView downloadExcel(PayInfoParam payInfoParam) {

        ModelAndView mav = new ModelAndView(new PayInfoExcelView());

        payInfoParam.setPagination(null);
        payInfoParam.setConditionType("EXCEL_DOWNLOAD");

        mav.addObject("payInfoList", payInfoService.getPayInfoList(payInfoParam));

        return mav;
    }

    @GetMapping("/cancel-fail-info/list")
    public String cancelFaillist(OrderCancelFailParam orderCancelFailParam
            , @PageableDefault @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
            , Model model) {


        Page<OrderCancelFail> pageContent = orderCancelFailService.getAllOrderCancelFail(orderCancelFailParam.getPredicate(), pageable);

        String today = DateUtils.getToday(Const.DATE_FORMAT);

        ConfigPg configPg = configPgService.getConfigPg();

        model.addAttribute("configPg", configPg);
        model.addAttribute("orderCancelFailParam", orderCancelFailParam);
        model.addAttribute("pageContent", pageContent);
        model.addAttribute("today", today);
        model.addAttribute("week", DateUtils.addYearMonthDay(today, 0, 0, -7));
        model.addAttribute("month1", DateUtils.addYearMonthDay(today, 0, -1, 0));
        model.addAttribute("month3", DateUtils.addYearMonthDay(today, 0, -3, 0));

        return ViewUtils.view();
    }

}