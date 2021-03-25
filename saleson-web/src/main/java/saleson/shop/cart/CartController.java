package saleson.shop.cart;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import saleson.common.asp28.analytics.Asp28Analytics;
import saleson.common.utils.UserUtils;
import saleson.shop.cart.domain.Cart;
import saleson.shop.cart.support.CartParam;
import saleson.shop.openmarket.domain.NaverPay;
import saleson.shop.order.domain.Buy;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.order.support.OrderException;
import saleson.shop.ranking.RankingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
@RequestProperty(layout="default")
public class CartController {
    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private RankingService rankingService;

    @Autowired
    private ConfigPgService configPgService;

    @GetMapping
    public String index(RequestContext requestContext, CartParam cartParam, HttpSession session, Model model) {

        long userId = 0;
        if (UserUtils.isUserLogin()) {
            userId = UserUtils.getUserId();
        }

        cartParam.setUserId(userId);
        cartParam.setSessionId(session.getId());

        Buy buy = cartService.getBuyInfoByCart(cartParam);

        model.addAttribute("naverPay", new NaverPay("Web", "/cart", configPgService.getConfigPg()));
        model.addAttribute("asp28Analytics", new Asp28Analytics(buy.getItems(), "Web", "Cart"));
        model.addAttribute("categoriesTeamList", rankingService.getRankingListForMain(5));
        model.addAttribute("buy", buy);
        model.addAttribute("count", buy.getTotalItemCount());
        return ViewUtils.getView("/cart/index");
    }

    /**
     * 장바구니 등록
     * <pre>
     * arrayItemId = 상품 코드
     * arrayQuantitys = 수량
     * arrayRequiredOptions = 옵션 (옵션ID^^^옵션명^^^옵션가격/비회원가격@옵션ID^^^옵션명^^^옵션가격/비회원가격)
     * </pre>
     * @param cart
     * @return
     */
    @PostMapping("add-item-to-cart")
    public JsonView addItemToCart(RequestContext requestContext, Cart cart, HttpSession session) {
        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        long userId = 0;
        if (UserUtils.isUserLogin()) {
            userId = UserUtils.getUserId();
        }

        cart.setUserId(userId);
        cart.setSessionId(session.getId());

        //int count = cartService.getCartAvailableInsertValidationCountForItemType(cart);
        //if (count == 0) {
        int insertCount = cartService.insertCart(cart);
        if (insertCount == 0) {
            return JsonViewUtils.failure(MessageUtils.getMessage("M00486")); // 주문 상품 처리중 오류가 발생 하였습니다. \n주문을 다시 시도해 주세요.
        } else {
            return JsonViewUtils.success();
        }
        //} else {
        //	return JsonViewUtils.failure(MessageUtils.getMessage("M00482")); // 배송방법이 다른상품이 장바구니에 들어 있기 때문에 장바구니내의 상품을 전부 결제하십시오.
        //}

    }


    /**
     * 장바구니 선택 삭제
     * @param requestContext
     * @param cartParam
     * @param session
     * @return
     */
    @PostMapping("list/delete")
    public JsonView deleteListData(RequestContext requestContext, CartParam cartParam, HttpSession session) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        long userId = 0;
        if (UserUtils.isUserLogin()) {
            userId = UserUtils.getUserId();
        }

        cartParam.setUserId(userId);
        cartParam.setSessionId(session.getId());

        cartService.deleteListData(cartParam);
        return JsonViewUtils.success();
    }

    /**
     * 장바구니 수량 변경
     * @param requestContext
     * @param cartParam
     * @param session
     * @return
     */
    @PostMapping("edit-quantity")
    public JsonView editQuantity(RequestContext requestContext, CartParam cartParam, HttpSession session) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        long userId = 0;
        if (UserUtils.isUserLogin()) {
            userId = UserUtils.getUserId();
        }

        cartParam.setUserId(userId);
        cartParam.setSessionId(session.getId());

        cartService.updateQuantity(cartParam);
        return JsonViewUtils.success();
    }


    /**
     * 바로 구매
     * @param requestContext
     * @param cart
     * @param session
     * @return
     */
    @PostMapping("buy-now")
    public JsonView buyNow(RequestContext requestContext, Cart cart, HttpSession session) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }


        long userId = 0;
        if (UserUtils.isUserLogin()) {
            userId = UserUtils.getUserId();
        }

        cart.setUserId(userId);
        cart.setSessionId(session.getId());

        try {
            cartService.immediatelyBuy(cart);
            cart = null;
            return JsonViewUtils.success();
        } catch(Exception e) {
            log.error("바로 구매 처리 시 오류 발생 ", e);
            return JsonViewUtils.failure(e.getMessage());
        }

    }

    /**
     * 장바구니 -> 주문 상품 임시테이블 저장
     * @param requestContext
     * @param cartParam
     * @param session
     * @return
     */
    @PostMapping("save-order-item-temp")
    public JsonView saveOrderItemTemp(RequestContext requestContext, CartParam cartParam, HttpSession session) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        long userId = 0;
        if (UserUtils.isUserLogin()) {
            userId = UserUtils.getUserId();
        }
        cartParam.setUserId(userId);
        cartParam.setSessionId(session.getId());

        try {
            cartService.copyCartToOrderItemTemp(cartParam);
            return JsonViewUtils.success();
        } catch(Exception e) {
            return JsonViewUtils.failure(e.getMessage());
        }
    }

    /**
     *  장바구니 배송비 지불방법 설정
     * @param requestContext
     * @param cartParam
     * @param session
     * @return
     */
    @PostMapping("set-shipping-payment-type")
    public JsonView setShippingPaymentType(RequestContext requestContext, CartParam cartParam, HttpSession session) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        long userId = 0;
        if (UserUtils.isUserLogin()) {
            userId = UserUtils.getUserId();
        }

        cartParam.setUserId(userId);
        cartParam.setSessionId(session.getId());
        cartService.updateShippingPaymentType(cartParam);
        return JsonViewUtils.success();
    }

    /**
     * 주문 관련 관리자 에러 핸들러
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(OrderException.class)
    public String orderExceptionHandler(OrderException ex, HttpServletRequest request) {
		log.error("ERROR: {}", ex.getMessage(), ex);

        if (StringUtils.isEmpty(ex.getJavascript())) {
            return ViewUtils.redirect(ex.getRedirectUrl(), ex.getMessage());
        } else {
            return ViewUtils.redirect(ex.getRedirectUrl(), ex.getMessage(), ex.getJavascript());
        }
    }
}
