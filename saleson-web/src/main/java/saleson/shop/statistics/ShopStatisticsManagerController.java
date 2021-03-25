package saleson.shop.statistics;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.CodeUtils;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.SecurityUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.seller.main.SellerService;
import saleson.seller.main.support.SellerParam;
import saleson.shop.brand.BrandService;
import saleson.shop.brand.support.BrandParam;
import saleson.shop.categoriesteamgroup.CategoriesTeamGroupService;
import saleson.shop.customer.CustomerService;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.order.OrderService;
import saleson.shop.statistics.domain.*;
import saleson.shop.statistics.support.*;
import saleson.shop.user.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/opmanager/shop-statistics")
@RequestProperty(template = "opmanager", layout = "default")
public class ShopStatisticsManagerController {
	private static final Logger log = LoggerFactory.getLogger(ShopStatisticsManagerController.class);

	@Autowired
	ShopStatisticsService shopStatisticsService;

	@Autowired
	CategoriesTeamGroupService categoriesTeamGroupService;

	@Autowired
	ItemService itemService;

	@Autowired
	UserService userService;

	@Autowired
	OrderService orderService;

	@Autowired
	CustomerService customerService;
	
	@Autowired
	SellerService sellerService;
	
	@Autowired
	private BrandService brandService;
	
	private TotalSellStatistics makeTotalSellStatisticsData(TotalSellStatistics total, BaseSellStatistics obj) {
		
		total.setTotalPayCount(total.getTotalPayCount() + obj.getPayCount());
		total.setTotalItemPrice(total.getTotalItemPrice() + obj.getItemPrice());
		//total.setTotalDiscountAmount(total.getTotalDiscountAmount() + obj.getTotalDiscountAmount());
		total.setTotalItemCouponDiscountAmount(total.getTotalItemCouponDiscountAmount() + obj.getItemCouponDiscountAmount());
		total.setTotalSellerDiscountPrice(total.getTotalSellerDiscountPrice() + obj.getSellerDiscountPrice());
		total.setTotalSpotDiscountPrice(total.getTotalSpotDiscountPrice() + obj.getSpotDiscountPrice());
		total.setTotalPayAmount(total.getTotalPayAmount() + obj.getPayTotal());
		
		total.setTotalCancelCount(total.getTotalCancelCount() + obj.getCancelCount());
		total.setTotalCancelItemPrice(total.getTotalCancelItemPrice() + obj.getCancelItemPrice());
		//total.setTotalCancelDiscountAmount(total.getTotalCancelDiscountAmount() + obj.getCancelTotalDiscountAmount());
		total.setTotalCancelItemCouponDiscountAmount(total.getTotalCancelItemCouponDiscountAmount() + obj.getCancelItemCouponDiscountAmount());
		total.setTotalCancelSellerDiscountPrice(total.getTotalCancelSellerDiscountPrice() + obj.getCancelSellerDiscountPrice());
		total.setTotalCancelSpotDiscountPrice(total.getTotalCancelSpotDiscountPrice() + obj.getCancelSpotDiscountPrice());
		total.setTotalCancelAmount(total.getTotalCancelAmount() + obj.getCancelTotal());
		
		total.setTotalRevenueItemPrice(total.getTotalRevenueItemPrice() + obj.getSumItemPrice());
		//total.setTotalRevenueDiscountAmount(total.getTotalRevenueDiscountAmount() + obj.getSumDiscountAmount());
		total.setTotalRevenueItemCouponDiscountAmount(total.getTotalRevenueItemCouponDiscountAmount() + obj.getSumItemCouponDiscountAmount());
		total.setTotalRevenueSellerDiscountPrice(total.getTotalRevenueSellerDiscountPrice() + obj.getSumSellerDiscountPrice());
		total.setTotalRevenueSpotDiscountPrice(total.getTotalRevenueSpotDiscountPrice() + obj.getSumSpotDiscountPrice());
		total.setTotalRevenueAmount(total.getTotalRevenueAmount() + obj.getSumTotalAmount());
		return total;
	}

	/**
	 * 카테고리별 통계 합계
	 * @param list
	 * @return
	 */
	private TotalSellStatistics makeTotalForBrand(List<ShopBrandStatistics> list) {

		if (list == null) {
			return null;
		}
		
		TotalSellStatistics total = new TotalSellStatistics();
		for(ShopBrandStatistics group : list) {
			for(BaseSellStatistics arr : group.getGroupList()) {
				total = makeTotalSellStatisticsData(total, arr);
			}
		}
		
		return total;
	}
	
	/**
	 * 카테고리별 통계 합계
	 * @param list
	 * @return
	 */
	private StatsSummary makeTotalForCategory(List<CategoryStatsSummary> list) {

		if (list == null) {
			return null;
		}
		
		List<BaseStats> baseStats = new ArrayList<>();
		for(CategoryStatsSummary group : list) {
			for(BaseStats arr : group.getGroupStats()) {
				baseStats.add(arr);
			}
		}
		
		return new StatsSummary(baseStats);
	}
	
	private StatsSummary makeTotalForArea(List<AreaStatsSummary> list) {

		if (list == null) {
			return null;
		}
		
		List<BaseStats> baseStats = new ArrayList<>();
		for(AreaStatsSummary group : list) {
			for(BaseStats arr : group.getGroupStats()) {
				baseStats.add(arr);
			}
		}
		
		return new StatsSummary(baseStats);
	}
	
	/**
	 * 월별, 년도별, 일별 매출 통계 합계
	 * @param list
	 * @return
	 */
	private StatsSummary makeTotalForDate(List<DateStatsSummary> list) {

		if (list == null) {
			return null;
		}

		List<BaseStats> baseStats = new ArrayList<>();
		for (DateStatsSummary group : list) {
			for (BaseStats arr : group.getGroupStats()) {
				baseStats.add(arr);
			}
		}

		return new StatsSummary(baseStats);
	}
	
	@GetMapping("/sales/payment")
	public String payment(Model model, StatisticsParam statisticsParam) {

		List<String> days = new ArrayList<>();

		String day = statisticsParam.getEndDate();
		String startDate = statisticsParam.getStartDate();

		try {
			if (day != null && startDate != null) {
				while (Integer.parseInt(day) >= Integer.parseInt(startDate)) {
					days.add(day);
					day = DateUtils.addDay(day, -1);
				}
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		model.addAttribute("days", days);
		model.addAttribute("statisticsParam", statisticsParam);
		model.addAttribute("approvalTypes", CodeUtils.getCodeInfoList("ORDER_PAY_TYPE"));
		model.addAttribute("list", shopStatisticsService.getPaymentStatisticsListByParam(statisticsParam));
		return ViewUtils.view();
	}
	
	/**
	 * 결제타입별 매출 통계 엑셀 다운로드
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/payment/excel-download")
	public ModelAndView paymentExcelDownload(StatisticsParam statisticsParam) {

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new PaymentStatisticsExcelView());

		List<String> days = new ArrayList<>();

		String day = statisticsParam.getEndDate();
		String startDate = statisticsParam.getStartDate();

		try {
			if (day != null && startDate != null) {
				while (Integer.parseInt(day) >= Integer.parseInt(startDate)) {
					days.add(day);
					day = DateUtils.addDay(day, -1);
				}
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}

		mav.addObject("days", days);
		mav.addObject("statisticsParam", statisticsParam);
		mav.addObject("approvalTypes", CodeUtils.getCodeInfoList("ORDER_PAY_TYPE"));
		mav.addObject("list", shopStatisticsService.getPaymentStatisticsListByParam(statisticsParam));

		return mav;
	}
	
	/**
	 * 일별 매출 통계
	 * @param model
	 * @param statisticsParam
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/day")
	public String salesDay(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {

		List<DateStatsSummary> list = shopStatisticsService.getDateStatsList(statisticsParam);
		
		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("statisticsParam", statisticsParam);
		
		model.addAttribute("total", makeTotalForDate(list));
		model.addAttribute("dateList", list);
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		
		return ViewUtils.view();
	}
	
	/**
	 * 일별 매출 통계 엑셀 다운로드
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/day/excel-download")
	public ModelAndView dayExcelDownload(StatisticsParam statisticsParam) {

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new DayStatisticsExcelView());

		List<DateStatsSummary> list = shopStatisticsService.getDateStatsList(statisticsParam);
		
		mav.addObject("total", makeTotalForDate(list));
		mav.addObject("list", list);

		return mav;
	}

	/**
	 * 월별 매출 통계 엑셀 다운로드
	 * @param type
	 * @param param
	 * @return
	 */
	@GetMapping("/sales/{type}/detail-excel-download")
	public ModelAndView detailExcelDownload(@PathVariable("type") String type, StatisticsParam param) {

		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		param.setType("detail");
		String sellerIdForParam = null;
		if (param.getSellerId() > 0) {
			sellerIdForParam = Long.toString(param.getSellerId());
		}
		
		ModelAndView mav = new ModelAndView(new RevenueDetailExcelView(type));
		mav.addObject("sellerIdForParam", sellerIdForParam);
		mav.addObject("list", shopStatisticsService.getRevenueDetailListForDateByParam(param));

		return mav;
	}

	/**
	 * 일자별로 매출 상세 화면을 보여줌.
	 * @param model
	 * @param type
	 * @param param
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/{type}/detail")
	@RequestProperty(layout = "base")
	public String revenueDetail(Model model, @PathVariable("type") String type, StatisticsParam param, RequestContext requestContext) {
		param.setType("detail");
		
		String title = DateUtils.date(param.getStartDate()) + "~" + DateUtils.date(param.getEndDate()) + " 판매 상세";
		/*if ("user".equals(type)) {
			
			String userName = "";
			if (param.getCustomerCode() != null) {
				
				Customer customer = customerService.getCustomerById(param.getCustomerCode());
				if (customer != null) {
					model.addAttribute("userName", customer.getCustomerName());
				}
			} 
			
			param.setUserName(userName);
			title = param.getUserName() + MessageUtils.getMessage("M01401") + "(" + DateUtils.date(param.getStartDate()) + "~" + DateUtils.date(param.getEndDate()) + ")";
		}*/
		String sellerIdForParam = null;
		if (param.getSellerId() > 0) {
			sellerIdForParam = Long.toString(param.getSellerId());
		}
		List<RevenueBaseForDate> statsList = shopStatisticsService.getRevenueDetailListForDateByParam(param);
		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("mode", type);
		model.addAttribute("title", title);
		model.addAttribute("orderList",	statsList);
		model.addAttribute("sellerIdForParam", sellerIdForParam);
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		// 음수 표기 여부
		model.addAttribute("nagativeNumber", ShopUtils.DISPLAY_STATS_NEGATIVE_NUMBER);
		
		return ViewUtils.getManagerView("/shop-statistics/sales/day-detail");
	}
	
	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param model
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/day/order")
	@RequestProperty(title = "매출 통계", layout = "base")
	public String salesDayOrder(Model model, StatisticsParam statisticsParam) {

		model.addAttribute("statisticsParam", statisticsParam);
		model.addAttribute("orderList",	shopStatisticsService.getOrderListByParam(statisticsParam));

		return ViewUtils.getManagerView("/shop-statistics/sales/order");
	}

	/**
	 * 월별 매출 통계 
	 * @param model
	 * @param statisticsParam
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/month")
	public String salesMonth(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {

		statisticsParam.setType("month");

		List<DateStatsSummary> list = shopStatisticsService.getDateStatsList(statisticsParam);

		model.addAttribute("queryString",requestContext.getQueryString());
		model.addAttribute("lastYear", DateUtils.getToday("yyyy"));
		model.addAttribute("statisticsParam", statisticsParam);
		
		model.addAttribute("total", makeTotalForDate(list));
		model.addAttribute("dateList", list);
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		return "view";
	}
	
	/*@GetMapping("/sales/growth")
	public String growth(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {
		
		String nowDate = DateUtils.getToday(Const.DATE_FORMAT);
		if (StringUtils.isNotEmpty(statisticsParam.getSearchYear())) {
			nowDate = statisticsParam.getSearchYear() + DateUtils.getToday("MMdd");
		}
		
		String lastDate = DateUtils.addYear(nowDate, -1);
		
		String nowYear = nowDate.substring(0, 4);
		String lastYear = lastDate.substring(0, 4);
		
		statisticsParam.setStartYear(lastYear);
		statisticsParam.setEndYear(lastYear);
		statisticsParam.setStartMonth("01");
		statisticsParam.setEndMonth("12");
		statisticsParam.setType("month");
		
		List<ShopDateStatistics> lastList = shopStatisticsService.getDateStatisticsListByParam(statisticsParam);
		TotalRevenueStatistics lastYearTotalRevenueStatistics = makeTotalForDate(lastList);
		
		statisticsParam.setStartYear(nowYear);
		statisticsParam.setEndYear(nowYear);
		statisticsParam.setStartMonth("01");
		statisticsParam.setEndMonth("12");
		statisticsParam.setType("month");
		
		List<ShopDateStatistics> nowList = shopStatisticsService.getDateStatisticsListByParam(statisticsParam);
		TotalRevenueStatistics nowYearTotalRevenueStatistics = makeTotalForDate(nowList);
		
		model.addAttribute("queryString",requestContext.getQueryString());
		model.addAttribute("year", DateUtils.getToday("yyyy"));
		model.addAttribute("growth", new Growth(lastYear, nowYear, lastList, nowList));
		return ViewUtils.view();
	}*/
	
	/*@GetMapping("/sales/growth/excel-download")
	public ModelAndView growthExcelDownload(StatisticsParam statisticsParam) {

		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new GrowthExcelView());
		String nowDate = DateUtils.getToday(Const.DATE_FORMAT);
		if (StringUtils.isNotEmpty(statisticsParam.getSearchYear())) {
			nowDate = statisticsParam.getSearchYear() + DateUtils.getToday("MMdd");
		}
		
		String lastDate = DateUtils.addYear(nowDate, -1);
		
		String nowYear = nowDate.substring(0, 4);
		String lastYear = lastDate.substring(0, 4);
		
		statisticsParam.setStartYear(lastYear);
		statisticsParam.setEndYear(lastYear);
		statisticsParam.setStartMonth("01");
		statisticsParam.setEndMonth("12");
		statisticsParam.setType("month");
		
		List<ShopDateStatistics> lastList = shopStatisticsService.getDateStatisticsListByParam(statisticsParam);
		TotalRevenueStatistics lastYearTotalRevenueStatistics = makeTotalForDate(lastList);
		
		statisticsParam.setStartYear(nowYear);
		statisticsParam.setEndYear(nowYear);
		statisticsParam.setStartMonth("01");
		statisticsParam.setEndMonth("12");
		statisticsParam.setType("month");
		
		List<ShopDateStatistics> nowList = shopStatisticsService.getDateStatisticsListByParam(statisticsParam);
		TotalRevenueStatistics nowYearTotalRevenueStatistics = makeTotalForDate(nowList);

		mav.addObject("growth", new Growth(lastYear, nowYear, lastList, nowList));
		
		return mav;
		
	}*/
	
	/**
	 * 월별 매출 통계 엑셀 다운로드
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/month/excel-download")
	public ModelAndView monthExcelDownload(StatisticsParam statisticsParam) {

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		statisticsParam.setType("month");

		List<DateStatsSummary> list = shopStatisticsService.getDateStatsList(statisticsParam);
		
		ModelAndView mav = new ModelAndView(new MonthStatisticsExcelView());
		
		mav.addObject("list", list);
		mav.addObject("total", makeTotalForDate(list));

		return mav;
	}

	/**
	 * 년별 매출 통계
	 * @param model
	 * @param statisticsParam
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/year")
	public String salesYear(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {

		statisticsParam.setType("year");

		List<DateStatsSummary> list = shopStatisticsService.getDateStatsList(statisticsParam);
		
		model.addAttribute("queryString",requestContext.getQueryString());
		model.addAttribute("lastYear", DateUtils.getToday("yyyy"));
		model.addAttribute("statisticsParam", statisticsParam);
		
		model.addAttribute("total", makeTotalForDate(list));
		model.addAttribute("dateList", list);
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		return ViewUtils.view();
	}
	
	/**
	 * 년별 매출 통계 엑셀 다운로드
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/year/excel-download")
	public ModelAndView yearExcelDownload(StatisticsParam statisticsParam) {

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		statisticsParam.setType("year");

		List<DateStatsSummary> list = shopStatisticsService.getDateStatsList(statisticsParam);
		
		ModelAndView mav = new ModelAndView(new YearStatisticsExcelView());

		mav.addObject("list", list);
		mav.addObject("total", makeTotalForDate(list));

		return mav;
	}

	/**
	 * 회원별 매출 통계
	 * @param model
	 * @param statisticsParam
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/user")
	public String salesUser(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {

		if (statisticsParam.getOrderBy() == null) {
			statisticsParam.setOrderBy("PRICE");
		}

		if (statisticsParam.getSort() == null) {
			statisticsParam.setSort("DESC");
		}
		
		int totalCount = shopStatisticsService.getUserStatisticsCountByParam(statisticsParam);

		Pagination pagination = Pagination.getInstance(totalCount, 30);
		statisticsParam.setPagination(pagination);

		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("statisticsParam", statisticsParam);
		model.addAttribute("pagination", pagination);
			
		model.addAttribute("total", shopStatisticsService.getUserTotalRevenueStatisticsByParam(statisticsParam));
		model.addAttribute("userList", shopStatisticsService.getUserStatisticsListByParam(statisticsParam));

		return ViewUtils.view();
	}
	
	/**
	 * 회원별 매출 통계 엑셀 다운로드
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/user/excel-download")
	public ModelAndView userExcelDownload(StatisticsParam statisticsParam) {

		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new UserStatisticsExcelView());
		mav.addObject("list", shopStatisticsService.getUserStatisticsListByParam(statisticsParam));

		return mav;
	}

	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param model
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/user/order-detail")
	@RequestProperty(title = "매출 통계", layout = "base")
	public String salesUserOrderDetail(Model model, StatisticsParam statisticsParam) {

		model.addAttribute("statisticsParam", statisticsParam);
		model.addAttribute("order", shopStatisticsService.getUsetOrderTotalDetailById(statisticsParam));
		model.addAttribute("userOrderItemList", shopStatisticsService.getUserOrderItemListByParam(statisticsParam));

		return ViewUtils.getManagerView("/shop-statistics/sales/order-detail");
	}

	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param model
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/user/user-order")
	@RequestProperty(title = "매출 통계", layout = "base")
	public String salesUserOrder(Model model, StatisticsParam statisticsParam) {

		model.addAttribute("statisticsParam", statisticsParam);
		model.addAttribute("order", shopStatisticsService.getUsetOrderTotalDetailById(statisticsParam));
		model.addAttribute("userOrderList",	shopStatisticsService.getUserOrderListByParam(statisticsParam));

		return ViewUtils.getManagerView("/shop-statistics/sales/user-order");
	}

	@GetMapping("/sales/brand")
	public String salesBrand(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {
		
		if (statisticsParam.getOrderBy() == null) {
			statisticsParam.setOrderBy("PRICE");
		}

		if (statisticsParam.getSort() == null) {
			statisticsParam.setSort("DESC");
		}

		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());
		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("statisticsParam", statisticsParam);
		
		List<ShopBrandStatistics> list = shopStatisticsService.getBrandStatisticsListByParam(statisticsParam);
		
		model.addAttribute("brandList", brandService.getBrandList(new BrandParam()));
		model.addAttribute("total", this.makeTotalForBrand(list));
		model.addAttribute("brandStatisticsList", list);
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		return ViewUtils.getView("/shop-statistics/sales/brand");
		
	}
	
	@GetMapping("/sales/brand/brand-detail")
	@RequestProperty(title = "브랜드 매출 통계", layout = "base")
	public String salesBrandDetail(Model model, StatisticsParam statisticsParam){
		
		model.addAttribute("brandName",statisticsParam.getBrand());
		
		/* 정보없음 비교 수정 2017-02-27 yulsun.yoo
		 if (statisticsParam.getBrand().equals("정보없음")) {
			statisticsParam.setBrand("");
		}*/
		if ("정보없음".equals(statisticsParam.getBrand())) {
			statisticsParam.setBrand("");
		}
		
		model.addAttribute("statisticsParam", statisticsParam);
		model.addAttribute("brandDetailList", shopStatisticsService.getBrandStatisticsDetailByParam(statisticsParam));
		
		
		return ViewUtils.getView("/shop-statistics/sales/brand-detail");
	}
	
	
	/**
	 * 상품별 판매액 엑셀 다운로드
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/brand/excel-download")
	public ModelAndView salesBrandExcelDownload(StatisticsParam statisticsParam) {

		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new BrandStatisticsExcelView());

		if (statisticsParam.getOrderBy() == null) {
			statisticsParam.setOrderBy("PRICE");
		}

		if (statisticsParam.getSort() == null) {
			statisticsParam.setSort("DESC");
		}

		mav.addObject("itemList", shopStatisticsService.getBrandStatisticsListByParam(statisticsParam));

		return mav;
	}
	
	/**
	 * 판매자별 통계
	 * @param model
	 * @param statisticsParam
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/seller")
	public String salesSeller(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {

		StatsSummary statsSummary = shopStatisticsService.getSellerStatsSummary(statisticsParam);

		int totalCount = statsSummary.getTotalRecord();
		Pagination pagination = Pagination.getInstance(totalCount, statisticsParam.getItemsPerPage());
		
		statisticsParam.setPagination(pagination);

		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("pagination", pagination);
		model.addAttribute("statisticsParam", statisticsParam);
		
		model.addAttribute("total", statsSummary);
		model.addAttribute("sellerStatsList", shopStatisticsService.getSellerStatsList(statisticsParam));
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		
		return ViewUtils.view();
	}
	
	/**
	 * 판매자별 통계 엑셀 다운로드
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/seller/excel-download")
	public ModelAndView salesSellerExcelDownload(StatisticsParam statisticsParam) {

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new SellerStatisticsExcelView());

		mav.addObject("statisticsParam", statisticsParam);
		mav.addObject("sellerStatsList", shopStatisticsService.getSellerStatsList(statisticsParam));

		return mav;
	}
	
	/**
	 * 상품별 판매액 통계
	 * @param model
	 * @param statisticsParam
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/item")
	public String salesItem(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {

		if (statisticsParam.getOrderBy() == null) {
			statisticsParam.setOrderBy("PRICE");
		}

		if (statisticsParam.getSort() == null) {
			statisticsParam.setSort("DESC");
		}

		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());

		StatsSummary statsSummary = shopStatisticsService.getItemStatsSummary(statisticsParam);

		int totalCount = statsSummary.getTotalRecord();

		Pagination pagination = Pagination.getInstance(totalCount, statisticsParam.getItemsPerPage());
		
		statisticsParam.setPagination(pagination);

		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("pagination", pagination);
		model.addAttribute("statisticsParam", statisticsParam);
		
		model.addAttribute("total", statsSummary);
		model.addAttribute("itemStatsList", shopStatisticsService.getItemStatsList(statisticsParam));
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		
		return ViewUtils.view();
	}

	/**
	 * 상품별 판매액 엑셀 다운로드
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/item/excel-download")
	public ModelAndView salesItemExcelDownload(StatisticsParam statisticsParam) {

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new ItemStatisticsExcelView());

		if (statisticsParam.getOrderBy() == null) {
			statisticsParam.setOrderBy("PRICE");
		}

		if (statisticsParam.getSort() == null) {
			statisticsParam.setSort("DESC");
		}

		mav.addObject("itemStatsList", shopStatisticsService.getItemStatsList(statisticsParam));

		return mav;
	}

	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param model
	 * @param statisticsParam
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/item/day")
	public String salesItemDay(Model model, StatisticsParam statisticsParam,
			RequestContext requestContext) {

		List<HashMap<String, String>> weekList = getWeekArraySelect();

		model.addAttribute("weekList", weekList);
		model.addAttribute("categoryTeamGroupList",
				categoriesTeamGroupService.getCategoriesTeamGroupList());

		if (statisticsParam == null || statisticsParam.getStartDate() == null || statisticsParam.getType() == null) {
			return ViewUtils.getManagerView("/shop-statistics/sales/item-day");
		}

		if (statisticsParam.getWeekType() == null) {
			statisticsParam.setWeekType("7");
		}
		if (model != null) {
			String dateType = statisticsParam.getType();
			if ("1".equals(dateType)) {
				typeOfSearch(statisticsParam, model);

			} else if ("2".equals(dateType)) {
				typeOfMonth(statisticsParam, model);

			} else if ("3".equals(dateType)) {
				typeOfWeek(statisticsParam, model);

			} else if ("4".equals(dateType)) {
				typeOfDay(statisticsParam, model);

			}
		}


		model.addAttribute("queryString", requestContext.getQueryString());

		return ViewUtils.getManagerView("/shop-statistics/sales/item-day");
	}

	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * 
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/item/day-excel-download")
	public ModelAndView salesItemDayExcelDownload(StatisticsParam statisticsParam) {

		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new ItemDateStatisticsExcelView());

		/*
		 * List<HashMap<String,String>> weekList = getWeekArraySelect();
		 * 
		 * model.addAttribute("weekList", weekList);
		 * model.addAttribute("categoryTeamGroupList",
		 * categoriesTeamGroupService.getCategoriesTeamGroupList());
		 * 
		 * if(statisticsParam.getStartDate() == null ){ return mav; }
		 * 
		 * if(statisticsParam.getWeekType() == null){
		 * statisticsParam.setWeekType("7"); }
		 */

		if (mav != null) {
			String dateType = statisticsParam.getType();
			if ("1".equals(dateType)) {
				typeOfSearch(statisticsParam, mav);

			} else if ("2".equals(dateType)) {
				typeOfMonth(statisticsParam, mav);

			} else if ("3".equals(dateType)) {
				typeOfWeek(statisticsParam, mav);

			} else if ("4".equals(dateType)) {
				typeOfDay(statisticsParam, mav);

			}
		}

		return mav;

	}

	/**
	 * 상품별 판매 상세 엑셀 다운로드
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/item/{itemId}/excel-download")
	public ModelAndView salesItemDetailExcelDownload(StatisticsParam statisticsParam) {

		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		Item item = itemService.getItemById(Integer.parseInt(statisticsParam.getItemId()));
		
		ModelAndView mav = new ModelAndView(new ItemDetailExcelView(item));
		mav.addObject("list", shopStatisticsService.getShopItemDetailList(statisticsParam));

		return mav;
	}

	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param model
	 * @param statisticsParam
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/item/{itemId}")
	@RequestProperty(title = "매출 통계", layout = "base")
	public String salesItemDetail(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {

		model.addAttribute("item", itemService.getItemById(Integer
				.parseInt(statisticsParam.getItemId())));
		model.addAttribute("statisticsParam", statisticsParam);
		model.addAttribute("itemStatisticsList", shopStatisticsService.getShopItemDetailList(statisticsParam));
		model.addAttribute("queryString", requestContext.getQueryString());
		return ViewUtils.getManagerView("/shop-statistics/sales/item-detail");
	}
	
	/**
	 * 카테고리별 통계
	 * @param model
	 * @param statisticsParam
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/category")
	public String salesCategory(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {
		
		List<CategoryStatsSummary> categoryStatsList = shopStatisticsService.getCategoryStatsList(statisticsParam);

		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());
		model.addAttribute("categoryStatsList", categoryStatsList);
		
		model.addAttribute("total", this.makeTotalForCategory(categoryStatsList));
		model.addAttribute("statisticsParam", statisticsParam);
		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));

		return ViewUtils.view();
	}

	/**
	 * 카테고리별 통계 엑셀 다운로드
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/category/excel-download")
	public ModelAndView salesCategoryExcelDownload(StatisticsParam statisticsParam) {

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new CategoryStatisticsExcelView());

		mav.addObject("statisticsParam", statisticsParam);
		mav.addObject("categoryStatsList", shopStatisticsService.getCategoryStatsList(statisticsParam));

		return mav;
	}

	/**
	 * 지역별 통계
	 * @param model
	 * @param statisticsParam
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/area")
	public String salesArea(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {

		if (statisticsParam.getOrderBy() == null) {
			statisticsParam.setOrderBy("PRICE");
		}

		if (statisticsParam.getSort() == null) {
			statisticsParam.setSort("DESC");
		}

		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("statisticsParam", statisticsParam);

		List<AreaStatsSummary> areaStatsList = shopStatisticsService.getAreaStatsList(statisticsParam);
		
		model.addAttribute("total", this.makeTotalForArea(areaStatsList));
		model.addAttribute("areaStatsList", areaStatsList);
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		return ViewUtils.view();
	}
	
	/**
	 * 지역별 통계 엑셀 다운로드
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping(value = "/sales/area/excel-download")
	public ModelAndView downloadExcelArea(StatisticsParam statisticsParam) {

		// 엑셀 권한이 없는 경우
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new AreaStatisticsExcelView(statisticsParam.getConditionType()));
		mav.addObject("areaStatsList", shopStatisticsService.getAreaStatsList(statisticsParam));
		return mav;
	}

	/**
	 * 상품별 판매 상세 엑셀 다운로드
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/sales/area/detail/excel-download")
	public ModelAndView salesAreaDetailExcelDownload(StatisticsParam statisticsParam) {
		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new AreaDetailExcelView(statisticsParam.getDodobuhyun()));
		mav.addObject("list", shopStatisticsService.getAreaDetailList(statisticsParam));

		return mav;
	}

	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param model
	 * @param statisticsParam
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/area/detail")
	@RequestProperty(title = "매출 통계", layout = "base")
	public String salesAreaDetail(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {

		model.addAttribute("statisticsParam", statisticsParam);
		model.addAttribute("areaList", shopStatisticsService.getAreaDetailList(statisticsParam));
		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam("SELLER_LIST_FOR_SELECTBOX")));
		return ViewUtils.getManagerView("/shop-statistics/sales/area-detail");
	}

	/**
	 * 매출 제로 상품 내역
	 * @param model
	 * @param statisticsParam
	 * @param requestContext
	 * @return
	 */
	@GetMapping("/sales/no-sales")
	public String notSales(Model model, StatisticsParam statisticsParam, RequestContext requestContext) {

		int count = shopStatisticsService.getDoNotSellItemCountByParam(statisticsParam);

		Pagination pagination = Pagination.getInstance(count, 15);
		statisticsParam.setPagination(pagination);

		model.addAttribute("queryString", requestContext.getQueryString());
		model.addAttribute("pagination", pagination);
		model.addAttribute("categoryTeamGroupList", categoriesTeamGroupService.getCategoriesTeamGroupList());
		model.addAttribute("statisticsParam", statisticsParam);
		model.addAttribute("itemList", shopStatisticsService.getDoNotSellItemListByParam(statisticsParam));

		return ViewUtils.view();
	}
	
	@GetMapping(value = "/sales/no-sales/excel-download")
	public ModelAndView downloadExcelNoSales(StatisticsParam statisticsParam) {

		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		ModelAndView mav = new ModelAndView(new NoSalesStatisticsExcelView());
		mav.addObject("list", shopStatisticsService.getDoNotSellItemListByParam(statisticsParam));
		return mav;
	}

	/**
	 * 관리자 팀별 그룹 관리 리스트
	 * @param model
	 * @param statisticsParam
	 * @return
	 */
	@GetMapping("/no-user")
	public String noUser(Model model, StatisticsParam statisticsParam) {

		Pagination pagination = Pagination.getInstance(shopStatisticsService.getNotUserCount(statisticsParam), 15);
		statisticsParam.setPagination(pagination);

		model.addAttribute("pagination", pagination);
		model.addAttribute("statisticsParam", statisticsParam);
		model.addAttribute("userList", shopStatisticsService.getNotUserList(statisticsParam));

		return ViewUtils.view();
	}
	
	@GetMapping(value = "/no-user/excel-download")
	public ModelAndView downloadExcelNoUser(StatisticsParam statisticsParam) {

		if(!SecurityUtils.hasRole("ROLE_EXCEL")){
			throw new UserException("엑셀 다운로드 권한이 없습니다.");
		}

		if (statisticsParam.getStartDate() == null) {
			statisticsParam.setStartDate(DateUtils.getToday("yyyyMM") + "01");
			statisticsParam.setEndDate(DateUtils.getToday(Const.DATE_FORMAT));
		}

		Pagination pagination = Pagination.getInstance(10000, 10000);
		statisticsParam.setPagination(pagination);
		
		ModelAndView mav = new ModelAndView(new NoUserStatisticsExcelView());
		mav.addObject("list", shopStatisticsService.getNotUserList(statisticsParam));
		return mav;
	}


	public static List<String> itemDateSearchHeader(String startDate,
			String endDate, String type) {
		List<String> list = new ArrayList<>();

		if ("web".equals(type)) {
			list.add("<th class=\"border_left\">" + DateUtils.date(startDate)
					+ "(" + week(startDate, "0") + ") ~ "
					+ DateUtils.date(endDate) + "(" + week(endDate, "0")
					+ ") </th>");
		} else {
			list.add(DateUtils.date(startDate) + "(" + week(startDate, "0")
					+ ") ~ " + DateUtils.date(endDate) + "("
					+ week(endDate, "0") + ")");
		}

		return list;
	}

	public static List<String> itemDateDayHeader(String startDate,
			String endDate, String type) {
		List<String> list = new ArrayList<>();

		String[] days = DateUtils.getDateArray(startDate, endDate);
		for (int i = 0; i < days.length; i++) {
			if ("web".equals(type)) {
				list.add("<th class=\"border_left\">" + DateUtils.date(days[i])
						+ "(" + week(days[i], "0") + ") </th>");
			} else {
				list.add(DateUtils.date(days[i]) + " (" + week(days[i], "0")
						+ ")");
			}
		}

		return list;
	}

	public static List<String> itemDateMonthHeader(String startDate,
			String endDate, String type) {

		List<String> list = new ArrayList<>();

		String[] days = DateUtils.getDateArray(startDate, endDate);

		String prevMonth = "";
		for (int i = 0; i < days.length; i++) {
			if (i == 0 || !days[i].substring(4, 6).equals(prevMonth)) {
				if ("web".equals(type)) {
					list.add("<th class=\"border_left\">"
							+ DateUtils.dateMonth(days[i]) + "</th>");
				} else {
					list.add(DateUtils.dateMonth(days[i]));
				}
			}

			prevMonth = days[i].substring(4, 6);
		}

		return list;

	}

	public static List<String> itemDateWeekHeader(String startDate,
			String endDate, String weekType, String type) {

		String htmlStart = "";
		String htmlEnd = "";

		if ("web".equals(type)) {
			htmlStart = "<th class=\"border_left\">";
			htmlEnd = "</th>";
		}

		String weekTypePrev = "" + (Integer.parseInt(weekType) - 1);
		if (weekType.equals("1"))
			weekTypePrev = "7";

		List<String> list = new ArrayList<>();

		String[] days = DateUtils.getDateArray(startDate, endDate);

		int j = 0;
		int k = 0;
		String weeks = "";

		for (int i = 0; i < days.length; i++) {

			if ((j == 0 && k == 0)
					|| (week(days[i], "1").equals(weekType) && k > 0)) {
				weeks += htmlStart + DateUtils.date(days[i]) + "("
						+ week(days[i], "0") + ")";
			}

			if (week(days[i], "1").equals(weekTypePrev) || i == days.length - 1) {
				weeks += " ~ " + DateUtils.date(days[i]) + "("
						+ week(days[i], "0") + ")" + htmlEnd;
				list.add(weeks);
				j = -1;
				weeks = "";
				k++;
			}

			j++;
		}
		return list;

	}

	public static String week(String sDate, String type) {
		String dateStr = sDate;

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(Const.DATE_FORMAT, Locale.JAPAN);
		try {
			cal.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format: "
					+ dateStr);
		}

		SimpleDateFormat rsdf = new SimpleDateFormat("E", Locale.JAPAN);
		String week = rsdf.format(cal.getTime());

		if (type.equals("1")) {
			if (week.equals("日")) {
				week = "1";
			} else if (week.equals("月")) {
				week = "2";
			} else if (week.equals("火")) {
				week = "3";
			} else if (week.equals("水")) {
				week = "4";
			} else if (week.equals("木")) {
				week = "5";
			} else if (week.equals("金")) {
				week = "6";
			} else if (week.equals("土")) {
				week = "7";
			}
		}

		return week;
	}

	public static List<HashMap<String, String>> getWeekArraySelect() {

		List<HashMap<String, String>> list = new ArrayList<>();

		String[] weeks = { "日", "月", "火", "水", "木", "金", "土" };

		int j = 1;

		for (String week : weeks) {

			HashMap<String, String> map = new HashMap<>();

			map.put("week", week);
			map.put("value", "" + j);

			list.add(map);

			j++;
		}

		return list;
	}

	private List<ShopItemDateStatistics> getSearchOfItemList(
			StatisticsParam statisticsParam) {

		List<ShopItemDateStatistics> itemDateList = shopStatisticsService.getItemDateListByParam(statisticsParam);

		for (ShopItemDateStatistics shopItemDateStatistics : itemDateList) {
			List<ShopItemPrice> list = new ArrayList<>();

			List<ShopDateStatistics> dateList = shopItemDateStatistics.getDateList();

			for (ShopDateStatistics shopDateStatistics : dateList) {
				ShopItemPrice itemprice = new ShopItemPrice();
				
				/*
				itemprice.setWebPriceTotal(Double.toString(shopDateStatistics.getWebItemPrice()));
				itemprice.setWebPayCount(Double.toString(shopDateStatistics.getWebPayCount()));
				*/
				list.add(itemprice);

				shopItemDateStatistics.setDateList2(list);
			}

		}

		return itemDateList;
	}

	private void typeOfSearch(StatisticsParam statisticsParam, ModelAndView mav) {
		String startDate = statisticsParam.getStartDate();
		String endDate = statisticsParam.getEndDate();

		List<ShopItemDateStatistics> itemDateList = getSearchOfItemList(statisticsParam);

		mav.addObject("itemDateList", itemDateList);
		mav.addObject("hedarList", itemDateSearchHeader(startDate, endDate, "excel"));

	}

	private void typeOfSearch(StatisticsParam statisticsParam, Model model) {

		String startDate = statisticsParam.getStartDate();
		String endDate = statisticsParam.getEndDate();

		List<ShopItemDateStatistics> itemDateList = getSearchOfItemList(statisticsParam);

		model.addAttribute("itemDateList", itemDateList);
		model.addAttribute("hedarList",	itemDateSearchHeader(startDate, endDate, "web"));

	}

	private List<ShopItemDateStatistics> getDayOfItemList(
			StatisticsParam statisticsParam) {

		String[] days = DateUtils.getDateArray(statisticsParam.getStartDate(),
				statisticsParam.getEndDate());
		List<ShopItemDateStatistics> itemDateList = shopStatisticsService.getItemDateListByParam(statisticsParam);

		for (ShopItemDateStatistics shopItemDateStatistics : itemDateList) {
			List<ShopItemPrice> list = new ArrayList<>();
			int price = 0;
			int quantity = 0;

			List<ShopDateStatistics> dateList = shopItemDateStatistics
					.getDateList();
			for (int i = 0; i < days.length; i++) {
				String prevDay = "";
				for (ShopDateStatistics shopDateStatistics : dateList) {

					ShopItemPrice itemprice = new ShopItemPrice();

					/*
					if (shopDateStatistics.getSearchDate().equals(days[i])) {
						price = (int) shopDateStatistics
								.getWebItemPrice();
						quantity = (int) shopDateStatistics
								.getWebPayCount();
					}
					*/
					
					if (!days[i].equals(prevDay)) {

						itemprice.setWebPriceTotal("" + price);
						itemprice.setWebPayCount("" + quantity);
						list.add(itemprice);

						shopItemDateStatistics.setDateList2(list);
						price = 0;
						quantity = 0;

					}

					prevDay = days[i];

				}
			}
		}

		return itemDateList;
	}

	private void typeOfDay(StatisticsParam statisticsParam, ModelAndView mav) {

		String startDate = statisticsParam.getStartDate();
		String endDate = statisticsParam.getEndDate();

		List<ShopItemDateStatistics> itemDateList = getDayOfItemList(statisticsParam);

		mav.addObject("itemDateList", itemDateList);
		mav.addObject("hedarList",
				itemDateDayHeader(startDate, endDate, "excel"));

	}

	private void typeOfDay(StatisticsParam statisticsParam, Model model) {

		String startDate = statisticsParam.getStartDate();
		String endDate = statisticsParam.getEndDate();

		List<ShopItemDateStatistics> itemDateList = getDayOfItemList(statisticsParam);

		model.addAttribute("itemDateList", itemDateList);
		model.addAttribute("hedarList",
				itemDateDayHeader(startDate, endDate, "web"));
	}

	private List<ShopItemDateStatistics> getMonthOfItemList(
			StatisticsParam statisticsParam) {

		List<String> monthList = itemDateMonthHeader(
				statisticsParam.getStartDate(), statisticsParam.getEndDate(),
				"web");
		List<ShopItemDateStatistics> itemDateList = shopStatisticsService.getItemDateListByParam(statisticsParam);

		for (ShopItemDateStatistics shopItemDateStatistics : itemDateList) {
			List<ShopItemPrice> list = new ArrayList<>();

			int price = 0;
			int quantity = 0;

			List<ShopDateStatistics> dateList = shopItemDateStatistics
					.getDateList();
			for (String month : monthList) {
				String prevMonth = "";
				for (ShopDateStatistics shopDateStatistics : dateList) {

					ShopItemPrice itemprice = new ShopItemPrice();

					/*
					if (month.replaceAll("-", "")
							.replaceAll("<th class=\"border_left\">", "")
							.replaceAll("</th>", "")
							.equals(shopDateStatistics.getSearchDate())) {
						price = (int) shopDateStatistics
								.getWebItemPrice();
						quantity = (int) shopDateStatistics
								.getWebPayCount();
					}
					*/
					
					if (!month.equals(prevMonth)) {
						itemprice.setWebPriceTotal("" + price);
						itemprice.setWebPayCount("" + quantity);
						list.add(itemprice);

						shopItemDateStatistics.setDateList2(list);
						price = 0;
						quantity = 0;
					}

					prevMonth = month;

				}

			}

		}

		return itemDateList;

	}

	private void typeOfMonth(StatisticsParam statisticsParam, ModelAndView mav) {

		List<ShopItemDateStatistics> itemDateList = getMonthOfItemList(statisticsParam);

		mav.addObject("itemDateList", itemDateList);
		mav.addObject("hedarList", itemDateMonthHeader(statisticsParam.getStartDate(), statisticsParam.getEndDate(), "excel"));

	}

	private void typeOfMonth(StatisticsParam statisticsParam, Model model) {

		List<ShopItemDateStatistics> itemDateList = getMonthOfItemList(statisticsParam);

		model.addAttribute("itemDateList", itemDateList);
		model.addAttribute("hedarList", itemDateMonthHeader(statisticsParam.getStartDate(), statisticsParam.getEndDate(), "web"));
	}

	private List<ShopItemDateStatistics> getWeekOfItemList(
			StatisticsParam statisticsParam) {

		String weekType = statisticsParam.getWeekType();
		String weekTypePrev = ""
				+ (Integer.parseInt(statisticsParam.getWeekType()) - 1);
		if (weekType.equals("1"))
			weekTypePrev = "7";

		List<ShopItemDateStatistics> itemDateList = shopStatisticsService.getItemDateListByParam(statisticsParam);

		for (ShopItemDateStatistics shopItemDateStatistics : itemDateList) {

			List<ShopItemPrice> list = new ArrayList<>();

			String[] days = DateUtils.getDateArray(statisticsParam.getStartDate(),statisticsParam.getEndDate());

			int j = 0;
			int price = 0;
			int quantity = 0;

			for (int i = 0; i < days.length; i++) {

				List<ShopDateStatistics> dateList = shopItemDateStatistics.getDateList();

				/*
				for (ShopDateStatistics shopDateStatistics : dateList) {
					if (days[i].equals(shopDateStatistics.getSearchDate())) {
						price = price+ (int) shopDateStatistics.getWebItemPrice();
						quantity = quantity	+ (int) shopDateStatistics.getWebPayCount();
					}
				}
*/
				if (week(days[i], "1").equals(weekTypePrev)
						|| i == days.length - 1) {
					ShopItemPrice itemprice = new ShopItemPrice();
					itemprice.setWebPriceTotal("" + price);
					itemprice.setWebPayCount("" + quantity);
					list.add(itemprice);
					shopItemDateStatistics.setDateList2(list);
					j = -1;
					price = 0;
					quantity = 0;
				}

				j++;
			}

		}

		return itemDateList;

	}

	private void typeOfWeek(StatisticsParam statisticsParam, ModelAndView mav) {

		List<ShopItemDateStatistics> itemDateList = getWeekOfItemList(statisticsParam);

		mav.addObject("itemDateList", itemDateList);
		mav.addObject("hedarList", itemDateWeekHeader(statisticsParam.getStartDate(), statisticsParam.getEndDate(), statisticsParam.getWeekType(), "excel"));

	}

	private void typeOfWeek(StatisticsParam statisticsParam, Model model) {

		List<ShopItemDateStatistics> itemDateList = getWeekOfItemList(statisticsParam);

		model.addAttribute("itemDateList", itemDateList);
		model.addAttribute("hedarList",	itemDateWeekHeader(statisticsParam.getStartDate(), statisticsParam.getEndDate(), statisticsParam.getWeekType(), "web"));

	}

}
